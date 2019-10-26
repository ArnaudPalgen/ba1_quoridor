package gui;

import game.Game;
import game.GameBoard;
import game.Save;
import players.Player;
import sun.reflect.annotation.ExceptionProxy;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;





/** On cree la fenêtre avec une barre de menu pour pouvoir sauvegarder
 * @author Fabio
 */

public class Window extends JFrame implements ActionListener
{	
	private JMenuBar barreMenu;  //la barre de menu
	private JMenu sauvegarde; //  option dans la barre de menu		
	private JMenuItem importe; // Charger une partie
	private JMenuItem exporte; // Sauvegarder une partie
	private JFileChooser fc; //Mon sélecteur de fichier
	private PanelMenu panneauMenu;	
	
	/**Construit la fenêtre + barre de menu
	 * @author Fabio
	 * 
	 */
	
	public Window()
	{	
		super("Quoridor"); //on surcharge pour mettre un titre
		barreMenu = new JMenuBar(); //on initialise la barre de menu
		sauvegarde = new JMenu("Fichier");
		importe = new JMenuItem("Importer une sauvegarde");
		exporte = new JMenuItem("Exporter ma partie");
		fc = new JFileChooser();
		panneauMenu= new PanelMenu(this);
		
		this.setJMenuBar(barreMenu);
		barreMenu.add(sauvegarde);
		sauvegarde.add(importe);
		sauvegarde.add(exporte);			
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		//this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		this.setSize(1280,800);
		this.setLocationRelativeTo(null);
		this.setContentPane(panneauMenu);
		this.setVisible(true);
		this.setResizable(true);//empeche le redimensionnemtn de la fenetre
		
		importe.addActionListener(this);
		exporte.addActionListener(this);
	}
	
	public static void main(String[] args)
	{
		new Window();
	}
	
	
	/**On cree les evenements adequat si on clique sur "Importer" et "Exporter"
	 * @author Fabio
	 * @param e qui est la source de l evenement
	 * 
	 */

	@Override
	public void actionPerformed(ActionEvent e)
	{
		
		
		if (e.getSource()==exporte) //Si on clique sur "Exporter"
		{
			
			fc.setDialogTitle("Enregistrez votre partie"); //titre du selecteur de fichier
			int option = fc.showSaveDialog(null); //stoque l entier correspondant au choix de l utilisateur
			
			if (option == JFileChooser.APPROVE_OPTION) //si on a confirmer
			{
				File file = fc.getSelectedFile(); //on stoque le chemin choisi
				String chemin=file.getPath();
				chemin+=".quor";
				file=new File(chemin);

					try {
						panneauMenu.getJeu().Save(file);
					} catch (NullPointerException | IOException e1) {
						JOptionPane.showMessageDialog(this,"Impossible de sauvegarder votre partie ici", "Erreur de lecture de fichier",JOptionPane.ERROR_MESSAGE);

					} 	
			}
			
		}
		
		
			if (e.getSource()==importe) //si on clique sur "Importer"
			{
				//if(e.getSource() instanceof PanneauMenu){
					
				fc.setDialogTitle("Chargez votre partie");
				
				int option = fc.showOpenDialog(null); 
				
				if (option == JFileChooser.APPROVE_OPTION)
				{
					
					File file = fc.getSelectedFile();

					InputStream fis;												/*
																						On deserialise pour pouvoir reprendre 
																						le plateau,l indice du joueur, et la 
																						liste des joueurs
																					*/
					try {                                                     
						fis = new FileInputStream(file);
						ObjectInputStream ois= new ObjectInputStream(fis);
						
							Save maSave = (Save) ois.readObject();
							GameBoard monGameBoard = maSave.getGameboard(); //on recupere le plateau
							Integer monIndex = maSave.getIndexJoueurAJouer();//on recupere l indice du joueur
							Player[] maListe = maSave.getListeJouers(); // on recupere la liste des joueurs
							
							Game monGame = new Game(maListe, monGameBoard, monIndex); //on recree un jeu avec les parametres correspondant a la sauvegarde
							Panel monPanneau = new Panel(monGame, maListe, this);//on recree le plateau avec les parametres correspondant a la sauvegarde
							
							
							
															

							panneauMenu.setVisible(false); //on rend invisible le menu
							
							
							this.setContentPane(monPanneau); // on met a jour le Jpanel a afficher
							
							this.revalidate();//on charge tout avant de repaint
							monPanneau.repaint();

							
							ois.close(); 
						} catch (ClassNotFoundException | IOException e1) {
							JOptionPane.showMessageDialog(this,"Impossible de charger la sauvegarde ici", "Erreur de lecture de fichier",JOptionPane.ERROR_MESSAGE);		
							
						}		

						
					}
				}									
		
		
	}
	
	
	/*public void setImporte(boolean statut)
	{
		importe.setEnabled(statut);
	}
	*/
	
	
	/**On recupere le bouton importe
	 * @author Fabio
	 * @return importe
	 */
	public JMenuItem getImporte() 
	{

		return importe;
	}
	
	
	/*public void setExporte(boolean statut)
	{
		exporte.setEnabled(statut);
	}
	*/
	
	/**On recupere le bouton exporte
	 * @author Fabio
	 * @return exporte
	 */
	public JMenuItem getExporte()
	{
		return exporte;
	}
	
	
}
