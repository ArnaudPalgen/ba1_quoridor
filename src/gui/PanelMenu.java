package gui;

import game.Game;
import players.EasyAI;
import players.HumanPlayer;
import players.Player;
import players.RandomAI;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**Classe pour cree le menu de jeu
 * @author Fabio
 *
 */
public class PanelMenu extends JPanel implements ActionListener {

	private JPanel placement;

	private JLabel label;


	private JRadioButton check1;
	private JRadioButton check2;
	private JRadioButton check3;
	private JRadioButton check4; 
	
	private Game jeu;
	private Panel pannelJeu;
	private Window frame;
	private Player joueur1;
	private Player joueur2;
	
	private ButtonMenu boutonJoueur1;
	private ButtonMenu boutonJoueur2;
	private ButtonMenu boutonOrdi;
	private ButtonMenu boutonRegle;
	
	private ButtonGroup checkGroup;
		
	private Image background;
	
/**Construit le menu
 * @param frame qui va permettre le changement de JPanel
 */
	public PanelMenu(Window frame) {
		
		this.frame=frame;
		
		placement = new JPanel();	
		label = new JLabel("Quoridor");
		
		boutonJoueur1 = new ButtonMenu("1 joueur", frame);
		boutonJoueur2 = new ButtonMenu("2 joueurs", frame);
		boutonOrdi = new ButtonMenu("Ordi. VS  Ordi.", frame);
		boutonRegle = new ButtonMenu("Règles du jeux", frame);
		
		check1 = new JRadioButton("Aléatoire VS Aléatoire");
		check2 = new JRadioButton("Normal vs Normal");
		check3 = new JRadioButton("Aléatoire vs Normal");
		check4 = new JRadioButton("Normal vs Aléatoire");
		
		frame.getImporte().setEnabled(true); //je peux charger
		frame.getExporte().setEnabled(true); //je peux pas sauvegarder
		
		
		checkGroup = new ButtonGroup(); //on ajoute tous les JRadioButton dans un ButtonGroup pour pouvoir n'en séléctionne qu'un
		this.setLayout(new BorderLayout()); //on définit le layout

		Font police = new Font("Titre", Font.PLAIN, 65);
	
		/* 
		on définit la police, la couleur du label et on le place
		 */
		label.setFont(police);
		label.setForeground(Color.BLACK);
		label.setHorizontalAlignment(JLabel.CENTER);
		this.add(label, BorderLayout.NORTH);

		placement.setLayout(new GridBagLayout());
		placement.setOpaque(false); //on rend le JPanel transparent
		
		GridBagConstraints gbc = new GridBagConstraints(); //L'objet servant à positionner les composants

		gbc.fill = GridBagConstraints.HORIZONTAL; // Adapte tous les bouton horizontalement
		gbc.insets = new Insets(20, 20, 20, 20); // définit les décalages entre les boutons

		gbc.gridx = -130;
		gbc.gridy = 0;

		placement.add(boutonJoueur1, gbc);

		gbc.gridx = 130;
		gbc.gridy = 0;

		placement.add(boutonJoueur2, gbc);

		gbc.gridx = -130;
		gbc.gridy = 130;

		placement.add(boutonOrdi, gbc);

		gbc.gridx = 130;
		gbc.gridy = 130;

		placement.add(boutonRegle, gbc);

		this.add(placement, BorderLayout.CENTER);//placement contient les boutons et on les ajoutes au centre

		boutonJoueur1.addActionListener(this);
		boutonJoueur2.addActionListener(this);
		boutonOrdi.addActionListener(this);
		boutonRegle.addActionListener(this);
		
		try {                                       
			background = ImageIO.read(new File("pictures/fond.jpg"));  //on charge l'image de fond
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this,"Impossible de charger l'image de fond, il se peut que le jeu rencontre des problèmes, relancer le jeu", "Erreur de lecture de fichier",JOptionPane.ERROR_MESSAGE);		
		}
		
		this.repaint();


	}

	/**
	 * On gere les evenements pour chaque mode de jeu
	 * @author Fabio
	 * @param  event la source de l evenement
	 */
	
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == boutonJoueur1) { //si on a selectionne mode 1 joueur
			String[] choixIa = { "Aléatoire", "Normal" };
			JOptionPane jop = new JOptionPane();

			int choix = jop.showOptionDialog(null, "Séléctionner le niveau de difficluté: ", "Niveau de difficulté",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, choixIa, choixIa[1]);       //on fait apparaitre une boite de dialogue

			if (choix == 0) // ia facile
			{

				joueur1 = new HumanPlayer("Vous");
				joueur2 = new RandomAI("Ordi.");
				Player[] listeJoueur = { joueur1, joueur2 };

				setJeu(new Game(listeJoueur, null, null));
				pannelJeu = new Panel(getJeu(), listeJoueur,frame);

				label.setVisible(false);              //on passe au pannel du jeu
				placement.setVisible(false);
				this.add(pannelJeu);
			}

			else if (choix == 1) // ia difficile
			{
				
				joueur1 = new HumanPlayer("Vous");
				joueur2 = new EasyAI("Ordi.");
				Player[] listeJoueur = { joueur1, joueur2 };
				setJeu(new Game(listeJoueur, null, null));
				pannelJeu = new Panel(getJeu(), listeJoueur,frame);

				label.setVisible(false);
				placement.setVisible(false);
				this.add(pannelJeu);
			}
		}

		if (event.getSource() == boutonJoueur2) { //si on a selectionne mode 2 joueurs

			joueur1 = new HumanPlayer("Joueur 1");
			joueur2 = new HumanPlayer("Joueur 2");
			Player[] listeJoueur = { joueur1, joueur2 };
			setJeu(new Game(listeJoueur, null, null));
			pannelJeu = new Panel(getJeu(), listeJoueur, frame);

			label.setVisible(false);
			placement.setVisible(false);
			this.add(pannelJeu);
		}

		if (event.getSource() == boutonOrdi) { // si on a selectionne mode Ordi. vs Ordi.
	
			checkGroup.add(check1);         //on ajoute dans le groupe de bouton
			checkGroup.add(check2);
			checkGroup.add(check3);
			checkGroup.add(check4);

			Object[] radioButtonArray = { check1, check2, check3, check4 }; 
			JOptionPane jop = new JOptionPane();

			int option = jop.showConfirmDialog(null, radioButtonArray, "Selectionne une option",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if (check1.isSelected()) { //si on a selectionne aleatoire vs aleatoire
				if (option == JOptionPane.OK_OPTION) {
	
					joueur1 = new RandomAI("Ordi. 1");
					joueur2 = new RandomAI("Ordi. 2");

					Player[] listeJoueur = { joueur1, joueur2 };
					setJeu(new Game(listeJoueur, null, null));
					pannelJeu = new Panel(getJeu(), listeJoueur, frame);

					label.setVisible(false);
					placement.setVisible(false);
					this.add(pannelJeu);
				}

			}

			if (check2.isSelected()) { //si on a selectionne facile vs facile
				
				if (option == JOptionPane.OK_OPTION) {
					
					joueur1 = new EasyAI("Ordi. 1");
					joueur2 = new EasyAI("Ordi. 2");

					Player[] listeJoueur = { joueur1, joueur2 };
					setJeu(new Game(listeJoueur, null, null));
					pannelJeu = new Panel(getJeu(), listeJoueur, frame);

					label.setVisible(false);
					placement.setVisible(false);
					this.add(pannelJeu);

				}
			}

			if (check3.isSelected()) { //si on a selectionne aleatoire vs facile
				if (option == JOptionPane.OK_OPTION) {
	
					joueur1 = new RandomAI("Ordi. 1");
					joueur2 = new EasyAI("Ordi. 2");

					Player[] listeJoueur = { joueur1, joueur2 };
					setJeu(new Game(listeJoueur, null, null));
					pannelJeu = new Panel(getJeu(), listeJoueur,frame);

					label.setVisible(false);
					placement.setVisible(false);
					this.add(pannelJeu);

				}
			}
			if (check4.isSelected()) { // si on a selectionne facile vs aleatoire
				if (option == JOptionPane.OK_OPTION) {

					joueur1 = new EasyAI("Ordi. 1");
					joueur2 = new RandomAI("Ordi. 2");

					Player[] listeJoueur = { joueur1, joueur2 };
					setJeu(new Game(listeJoueur, null, null));
					pannelJeu = new Panel(getJeu(), listeJoueur,frame);

					label.setVisible(false);
					placement.setVisible(false);
					this.add(pannelJeu);
				}
			}
		}

		if (event.getSource() == boutonRegle) { //si on a selectionne le bouton pour afficher les r�gles
			label.setVisible(false);
			placement.setVisible(false);

			frame.setContentPane(new PanelRules(frame));
			this.repaint();
		}
		
	}
	
	/**
	 * @author Nono21
	 * @return le jeu
	 */
	public Game getJeu()
	{
		return jeu;	
	}
	
	
	
	/**le paintComponent du menu va dessiner l'image de fond
	 * @author Fabio
	 * @param g, l'object Graphics qui contient des méthodes pour pouvoir dessiner,..
	 */

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);		
		
	}

	private void setJeu(Game jeu) {
		this.jeu = jeu;
	}	
	

}