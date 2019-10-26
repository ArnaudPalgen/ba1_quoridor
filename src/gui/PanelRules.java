package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**le panel pour afficher les règles du jeu
 * @author Fabio
 *
 */
public class PanelRules extends JPanel implements ActionListener
{

	private JPanel placement;
	
	private Window frame;
	
	
	private ButtonHome boutonRetour;	
	private JLabel labelTitre;
	private JLabel labelRegles;
	
	private Image background;
	
	/**Construit le Jpanel des règles
	 * @author Fabio
	 * @param frame, la fenetre pour actualiser le jpanel
	 */
	public PanelRules(Window frame)
	{
		this.frame=frame;
		try {
			background = ImageIO.read(new File("pictures/fond.jpg"));
			//fond: http://img11.hostingpics.net/pics/424547parquet.png
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this,"Impossible de charger l'image de fond , il se peut que le jeu rencontre des problèmes, relancer le jeu", "Erreur de lecture de fichier",JOptionPane.ERROR_MESSAGE);		
			frame.dispose();
		}
	
		

		placement= new JPanel();
		boutonRetour= new ButtonHome();
		labelTitre = new JLabel("Les règles de jeu");
		labelRegles = new JLabel("<html> Le but du jeu est d'atteindre la ligne opposée à la ligne de départ. Le jeu se joue à deux joueurs"
				+ " <br>       Chaque joueur dispose d'un pion et de 10 murs. Chacun leur tour, les joueurs ont le choix entre deux actions :  déplacer son pion ou placer un mur."
				+ " <br>       Lorsqu'un joueur n'a plus de mur, il déplace forcément son pion."
				+ " <br>       Les pions se déplacent d'une seule case, en avant, en arrière, à droite ou à gauche. Un mur doit forcément être contournée. "
				+ " <br>       Quand deux pions sont face à face, le joueur dont c'est le tour, peut, si il n'y a pas de mur derrière, sauter le pion . Si un mur ou un pion est derrière, il peut, et uniquement dans ce cas,se déplacer en 'diagonale' ."
				+ " <br>       Une barrière est toujours posée exactement le long de deux cases. On doit toujours laisser une solution à chaque joueur. Il est donc interdit de se bloquer ou de bloquer complètement un adversaire."
				+ " <br> <br> <br> "
				+ " <br> <br> <br> Bon amusement et bon jeu ! </html>");
		/**
		 * texte de: http://sandjivy.math.free.fr/jeux/quoridor/quoridor-regles.pdf
		 */
		
		frame.getImporte().setEnabled(false);
		frame.getExporte().setEnabled(false);


		this.setLayout(new BorderLayout());
		
		
		Font policeRegle = new Font("Tahoma", Font.BOLD, 20);//on definit la police,couleur du label et on le place
		labelRegles.setFont(policeRegle);
		labelRegles.setForeground(Color.orange);
		labelRegles.setHorizontalAlignment(JLabel.CENTER);
		
		Font policeTitre = new Font("Tahoma", Font.BOLD, 65);//on definit la police,couleur du label et on le place
		labelTitre.setFont(policeTitre);
		labelTitre.setForeground(Color.BLACK);
		labelTitre.setHorizontalAlignment(JLabel.CENTER);
		
		this.add(labelTitre, BorderLayout.NORTH);
				

		this.add(labelRegles, BorderLayout.CENTER);
		
		placement.setOpaque(false); //on ajoute le bouton dans un autre jpanel qu'on ajoute lui-meme au jpanel principal pour ne pas qu'il prenne toute la largeur
		placement.setPreferredSize(new Dimension(100, 100));
		placement.add(boutonRetour);
		this.add(placement, BorderLayout.SOUTH);
		
		boutonRetour.addActionListener(this);

		
		
	}
	
	/**Dessine l'image de fond
	 * @author Fabio
	 * @param g, l'object Graphics qui contient des méthodes pour pouvoir dessiner,..
	 */
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
}



	/**on gere l'evenement si on clique sur le bouton retour
	 * @author Fabio
	 * @param e, la source de l'evenement
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==boutonRetour)
		{
			labelTitre.setVisible(false);	/*on rend tout invisible et met à jour le pannel*/
			labelRegles.setVisible(false);
			placement.setVisible(false);	
			
			frame.setContentPane(new PanelMenu(frame));

		}
		
	}
}
