/**
 * 
 */
package gui;

import game.Box;
import game.Game;
import game.GameBoard;
import game.Piece;
import players.Player;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


/**Classe qui affiche le JPanel du jeu
 * @author Nono21/Fabio
 */

public class Panel extends JPanel implements MouseListener, ActionListener {
	private Window frame;
	private PanelMenu panelMenu;
	private ButtonInfo buttonInfo;
	private JPanel placement;

	// private static ImgBoard board_img=new ImgBoard();
	private ImgBoard board_img;
	
	private Image background;
	private Image backgroundAide;
	private Image pion1;
	private Image pion2;
	private Image pionTransparent;
	
	// public static Contents[][] imgPlateau=board_img.getImgBoard();
	private AContents[][] imgPlateau;
	private int marge;
	private static int startX, startY;// startX=350,startY=30;
	
	private Point position;

	private int tailleCase;
	private int inter;
	private int hauteur;// =Game.getTableau().length;///hauteur
	private int largeur;// =Game.getTableau()[0].length;
	private Player playerAjouer;
	private Game jeu;
	
	private boolean gagne;
	private boolean statut;

	private JLabel labelNbMur1;
	private JLabel labelNbMur2;
	private JLabel labelWin;

	private int nbMurInt;
	private String nbMurString;

	private ButtonHome boutonRetour;

	private Box[] cliqueEnfonce;
	private Box[] cliqueRelache;

	private Box[][] plateauLogique;
	
/** Construit le panel du jeu
 * @author Nono21/Fabio
 * @param jeu
 * @param listeJoueur 
 * @param frame 
 * 
 */
	
	
	
	public Panel(Game jeu, Player[] listeJoueur,Window frame) {
		plateauLogique = jeu.getTableau(); //on recupere le plateau logique
		this.frame=frame;
		this.jeu = jeu;
		statut=false;  //pour savoir si on affiche le background aide ou non
		
		try {
			background = ImageIO.read(new File("pictures/fond.jpg"));
			backgroundAide = ImageIO.read(new File("pictures/aide.png"));
			pion1 = ImageIO.read(new File("pictures/pion.png"));
			pion2 = ImageIO.read(new File("pictures/pion2.png"));
			pionTransparent = ImageIO.read(new File("pictures/transparPion.png"));
			// transparPion: https://upload.wikimedia.org/wikipedia/commons/thumb/6/63/Cercle_noir_50%25.svg/2000px-Cercle_noir_50%25.svg.png
			//pion: http://www.icône.com/images/icones/2/0/button-red.png
			//fond: http://img11.hostingpics.net/pics/424547parquet.png


			
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(this,"Impossible de charger une ou plusieurs images, il se peut que le jeu rencontre des probl�mes, relancer le jeu", "Erreur de lecture de fichier",JOptionPane.ERROR_MESSAGE);		
			frame.dispose();
		}
		
		panelMenu = new PanelMenu(frame); 

		int wCenter = 1280 / 2;
		int hCenter = 800 / 2;
		startX = wCenter - 269; //le debut ou l on commence à dessiner en x
		startY = hCenter - 320; // le début ou l on commence à dessiner en y
		
		tailleCase = 50;
		inter = 11;
		hauteur = jeu.getTableau().length;
		largeur = jeu.getTableau()[0].length;
		
		gagne=false;
		playerAjouer = jeu.whichPlayer();  //stoque le joueur à qui c'est le tour
		
		buttonInfo =new ButtonInfo();
		boutonRetour = new ButtonHome();
		
		this.setLayout(new BorderLayout());                        /*
																		On definit police,taille et couleur pour chaque 
																		label et on les place à l'ouest et l'est
																	*/

		labelNbMur1 = new JLabel("Murs restants pour ");
		labelNbMur2 = new JLabel("Murs restants pour ");
		labelWin = new JLabel("");

		Font police = new Font("police", Font.ROMAN_BASELINE, 20);
		labelNbMur1.setFont(police);
		labelNbMur1.setForeground(Color.BLACK);
		labelNbMur1.setHorizontalAlignment(JLabel.CENTER);

		labelNbMur2.setFont(police);
		labelNbMur2.setForeground(Color.BLACK);
		labelNbMur2.setHorizontalAlignment(JLabel.CENTER);

		Font policeWin = new Font("Win", Font.ROMAN_BASELINE, 65);
		labelWin.setFont(policeWin);
		labelWin.setForeground(Color.BLACK);
		labelWin.setHorizontalAlignment(JLabel.CENTER);


		this.add(labelNbMur1, BorderLayout.WEST);
		this.add(labelNbMur2, BorderLayout.EAST);

		board_img = new ImgBoard(this, jeu);
		imgPlateau = board_img.getImgBoard();
		
		placement = new JPanel(); //on ajoute les 
		placement.setOpaque(false);
		placement.add(boutonRetour);
		placement.add(buttonInfo);

		this.add(placement, BorderLayout.SOUTH);

		boutonRetour.addActionListener(this);
		buttonInfo.addActionListener(this);

		marge = 3;// 5
		

		addMouseListener(this);
		
		
		if(jeu.getListeJoueurs()[0].isIA() && jeu.getListeJoueurs()[1].isIA())
		{
			playAgame();
			this.repaint();
		}
	}
	
	
	public void playAgame()
	{
		
		Timer t = new Timer();
		
			t.schedule(new TimerPlay(this),0,500);
			this.repaint();
			gagne=jeu.win(playerAjouer);
			this.repaint();
		
	}
	
	private class TimerPlay extends TimerTask
	{
		private Panel pan;
		public TimerPlay(Panel pan)
		{
			super();
			this.pan=pan;
			
		}

		public void run() 
		{
			if(!gagne)
			{
				Integer play = null;
				play = playerAjouer.play(jeu.getGameboard(), jeu.getListeJoueurs(), null, null);
				gagne = jeu.win(playerAjouer);
			    pan.repaint();
			


				
				
				if (play != GameBoard.OK)
				{
					play = playerAjouer.play(jeu.getGameboard(), jeu.getListeJoueurs(), null, null);
					gagne = jeu.win(playerAjouer);

					pan.repaint();
	
				}
				
				playerAjouer=jeu.whichPlayer();
				pan.repaint();
							
			}
			
			else
			{
				playerAjouer = jeu.whichPlayer();
				pan.add(labelWin,BorderLayout.NORTH);
				this.cancel();
				pan.repaint();
			}
		}
		
	}
	
	

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);

			
		for (Player joueur : jeu.getListeJoueurs()) {
			nbMurInt = joueur.getNombreMur();
			nbMurString = "" + nbMurInt;

			String nomJoueur = joueur.getNom();

			if (nomJoueur == jeu.getListeJoueurs()[0].getNom()) {
				labelNbMur1.setText("Murs restants pour " + nomJoueur + " :" + nbMurString);
			}

			else if (nomJoueur == jeu.getListeJoueurs()[1].getNom()) {
				labelNbMur2.setText("Murs restants pour " + nomJoueur + " :" + nbMurString);
			}

			// gagne=jeu.win(playerAjouer);

			if (gagne) {
				labelWin.setText(playerAjouer.getNom() +" a gagné !");

				
			}

		}

		for (int i = 0; i < imgPlateau.length; i++) {   //busy des murs
														// avant busy des
														// intersections
			for (int j = 0; j < imgPlateau[0].length; j++) {

				if (imgPlateau[i][j] != null && ((i % 2) != 0 || (j % 2) != 0)) { /* on definis des busy de tout sauf les
																					 intersections car les busy des inter dependent
																					 des mur aux alentours*/
					imgPlateau[i][j].setBusy(this);
				}
			}
		}
		for (int i = 0; i < imgPlateau.length; i++) {
			for (int j = 0; j < imgPlateau[0].length; j++) {
				if (imgPlateau[i][j] != null && ((i % 2) == 0 && (j % 2) == 0)) {
					imgPlateau[i][j].setBusy(this);
				}
			}
		}

		dessine(g);
		
		if(statut)
		{
			g.drawImage(backgroundAide, startX, startY,this);

		}

	}

	/** Dessine le plateau de jeu
	 * @author Arnaud.P
	 * @param g, l'object Graphics qui contient des methodes pour pouvoir dessiner,..
	 */
	public void dessine(Graphics g) {
		int posX = startX;
		int posY = startY;
		int lastHeight = 0;
		Box[] whereMove = jeu.getGameboard().whereMovePiece(playerAjouer.getPiece().getBox());
		for (int i = 0; i < imgPlateau.length; i++) {
			// lastHeight=0;
			posX = startX;
			for (int j = 0; j < imgPlateau[0].length; j++) {
				if (imgPlateau[i][j] != null) {
					AContents element = imgPlateau[i][j];
					if (element.isMarge() == false) {
						g.drawImage(element.getImage(), posX, posY - marge, this);
					} else {
						g.drawImage(element.getImage(), posX, posY, this);

						if (element.getHeight() == 50 && element.getWidth() == 50) {
							ImgBox cases = (ImgBox) element; // case image
							if (element.busy) {

								Box[][] tableau = jeu.getTableau();
								Box caseLogique = tableau[cases.getILogique()][cases.getJLogique()];
								Piece pion = caseLogique.getPion();
								Player joueur = pion.getJoueur();
								int numJoueur = joueur.getNbrJoueur();

								if (numJoueur == 1) {
									g.drawImage(pion1, posX + 2, posY + 2, this);
								} else {
									g.drawImage(pion2, posX + 2, posY + 2, this);
								}
							}

							for (Box box : whereMove) {
	
									int iB = box.getI();
									int jB = box.getJ();
									if (iB == cases.getILogique() && jB == cases.getJLogique() && !gagne) {
										g.drawImage(pionTransparent, posX + 2, posY + 2, this);
									}		
							}

						}
					}

					posX += element.getSizeX(this);
					lastHeight = element.getSizeY(this);
					// si on a un inter vertical on doit dessinier a posX-marge

				}
			}
			posY += lastHeight;
		}
	}
	
	/** Affiche le tableau dans la console
	 * @author Arnaud.P
	 */

	public void affiche() {

		for (int i = 0; i < imgPlateau.length; i++) {
			for (int j = 0; j < imgPlateau[0].length; j++) {
				System.out.print(imgPlateau[i][j] + " ");
			}
			System.out.println("");
		}
	}
	
	/**
	 * 
	 * @return board_img
	 */

	public ImgBoard getBoard_img() {
		return board_img;
	}
	
	/**
	 * 
	 * @param board_img
	 */

	public void setBoard_img(ImgBoard board_img) {
		this.board_img = board_img;
	}

	/**
	 * 
	 * @return  imgPlateau, tableau à 2D de AContents
	 */
	public AContents[][] getImgPlateau() {
		return imgPlateau;
	}

	public int getMarge() {
		return marge;
	}
	
	/** Evenement gere si on clique sur une case
	 * @author Fabio
	 * @param event, la source de l'evenement
	 */

	public void mouseClicked(MouseEvent event) 											
	{
		position = event.getPoint(); //la position du clique
		if (event.getButton() == MouseEvent.BUTTON1) // Si il y a un clique
														// gauche
		{
			for (int i = 0; i < hauteur; i++)         //TODOOOOOOOOOOOO
				for (int j = 0; j < largeur; j++) {

					if (position.x <= (startX) + (tailleCase * (j + 1)) + inter * (j)
							&& position.x >= (startX) + (tailleCase * j) + inter * j
							&& position.y <= (startY) + tailleCase * (i + 1) + inter * i
							&& position.y >= (startY) + tailleCase * i + (inter * i)) 
					{

						int retour = GameBoard.Impossible;
						if (gagne == false) {
							retour = playerAjouer.play(jeu.getGameboard(), jeu.getListeJoueurs(),
									jeu.getTableau()[i][j], null);

						}

						if (retour == GameBoard.OK) {
							gagne = jeu.win(playerAjouer);
							
							if (gagne) {
								playerAjouer = jeu.whichPlayer();
								
								this.add(labelWin,BorderLayout.NORTH);
							}


							playerAjouer = jeu.whichPlayer();
							
							if (playerAjouer.isIA() && gagne == false) {
								playerAjouer.play(jeu.getGameboard(), jeu.getListeJoueurs(), null, null);
								gagne = jeu.win(playerAjouer);
								
								if (gagne) {
									playerAjouer = jeu.whichPlayer();
									
									this.add(labelWin,BorderLayout.NORTH);
								}
								
								playerAjouer = jeu.whichPlayer();
							}
						}

						this.repaint();

					}
				}
		}
	}

	/** Evenement gere si on presse une intersection
	 * @author Fabio
	 * @param event2, la source de l'evenement
	 */
	public void mousePressed(MouseEvent event2) {

		 position = event2.getPoint();

		// Mur Horizontal
		for (int i = 0; i < largeur-1; i++) {// ICI j=les lignes
			
			for (int j = 0; j < hauteur; j++) {
				if (position.x < startX + (tailleCase * (j +1)) + (inter * (j))
						&& position.x > startX + (tailleCase * (j)) + inter * (j)
						&& position.y < startY + (tailleCase * (i+1)) + (inter * (i + 1))
						&& position.y > startY + (tailleCase * (i+1)) + (inter * (i))) 
				{
					// System.out.println("mur Honrizontal sur la colonne
					// "+(j+1)+ " et entre la ligne "+(i+1)+" "+(i+2));
					cliqueEnfonce = new Box[2];
					cliqueEnfonce[0] = plateauLogique[i][j];
					cliqueEnfonce[1] = plateauLogique[i + 1][j];
				}
			}
		}


		for (int k = 0; k < hauteur - 1; k++) // on parcoure d'abord les
			// colonnes
		{
			for (int l = 0; l < largeur ; l++) // on descend en fonction
				// des lignes
			{
				if (position.x <= (startX) + (tailleCase * (k + 1)) + (inter * (k + 1))
						&& position.x >= startX + (tailleCase * (k + 1)) + (inter * (k))
						&& position.y < startY + (tailleCase * (l + 1)) + (inter * (l))
						&& position.y > startY + (tailleCase * (l)) + (inter) * (l))
				{
					cliqueEnfonce = new Box[2];
					cliqueEnfonce[0] = plateauLogique[l][k];
					cliqueEnfonce[1] = plateauLogique[l][k + 1];
				}
			}
		}
	}

	/** Evenement gere si on relache sur une intersection
	 * @author Fabio
	 * @param event3, la source de l'evenement
	 */
	public void mouseReleased(MouseEvent event3) {

		position = event3.getPoint();
		
		// Mur Horizontal
		for (int i = 0; i < largeur-1; i++) {// ICI j=les lignes
			
			for (int j = 0; j < hauteur; j++) {
				if (position.x < startX + (tailleCase * (j +1)) + (inter * (j))
						&& position.x > startX + (tailleCase * (j)) + inter * (j)
						&& position.y < startY + (tailleCase * (i+1)) + (inter * (i + 1))
						&& position.y > startY + (tailleCase * (i+1)) + (inter * (i))) {

					cliqueRelache = new Box[2];
					cliqueRelache[0] = plateauLogique[i][j];
					cliqueRelache[1] = plateauLogique[i + 1][j];

					if (cliqueEnfonce != null && cliqueRelache != null) {
						if ((cliqueEnfonce[0].getJ() + 1 == cliqueRelache[0].getJ()
								|| cliqueRelache[0].getJ() + 1 == cliqueEnfonce[0].getJ())
								&& cliqueEnfonce[0].getI() == cliqueRelache[0].getI()) {// on
																						// donne
																						// d'abord
																						// le
																						// enfonce
							Box[][] mur = { { cliqueEnfonce[0], cliqueEnfonce[1] },
									{ cliqueRelache[0], cliqueRelache[1] } };
							int retour = GameBoard.Impossible;

							if (gagne == false) {
								retour = playerAjouer.play(jeu.getGameboard(), jeu.getListeJoueurs(), null, mur);
							}

							if (retour == GameBoard.OK) {
								gagne = jeu.win(playerAjouer);
								
								playerAjouer = jeu.whichPlayer();
								if (playerAjouer.isIA() && gagne == false) {
									playerAjouer.play(jeu.getGameboard(), jeu.getListeJoueurs(), null, null);
									gagne = jeu.win(playerAjouer);
									playerAjouer = jeu.whichPlayer();
								}

							}

							this.repaint();
						}

					}

				}
			}
		}

		// Mur Vertical

		for (int k = 0; k < hauteur - 1; k++) // on parcoure d'abord les
		{
			for (int l = 0; l < largeur ; l++) // on avance en fonction des colonnes
			{
				if (position.x <= (startX) + (tailleCase * (k + 1)) + (inter * (k + 1))
						&& position.x >= startX + (tailleCase * (k + 1)) + (inter * (k))
						&& position.y < startY + (tailleCase * (l + 1)) + (inter * (l))
						&& position.y > startY + (tailleCase * (l)) + (inter) * (l))
				{
					cliqueRelache = new Box[2];
					cliqueRelache[0] = plateauLogique[l][k];
					cliqueRelache[1] = plateauLogique[l][k + 1];
				}
			}

			if (cliqueEnfonce != null && cliqueRelache != null) {
				if ((cliqueEnfonce[0].getI() + 1 == cliqueRelache[0].getI()
						|| cliqueRelache[0].getI() + 1 == cliqueEnfonce[0].getI())
						&& cliqueEnfonce[0].getJ() == cliqueRelache[0].getJ()) {
					Box[][] mur = { { cliqueEnfonce[0], cliqueEnfonce[1] }, { cliqueRelache[0], cliqueRelache[1] } };
					int retour = GameBoard.Impossible;
					if (gagne == false) {
						retour = playerAjouer.play(jeu.getGameboard(), jeu.getListeJoueurs(), null, mur);
					} 
					if (retour == GameBoard.OK) {
						gagne = jeu.win(playerAjouer);
						playerAjouer = jeu.whichPlayer();

					}

					this.repaint();
				}
			}

		}

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// useless
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// useless
	}
	
	/**on gere l'evenement si on clique sur le bouton retour menu ou info
	 * @author Fabio
	 *  @param e, la source de l'evenement 
	 */

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == boutonRetour) {


			labelNbMur1.setVisible(false);                /*on  retire les label, met a jour le container et on reset les joueurs*/
			labelNbMur2.setVisible(false);
			labelWin.setVisible(false);
			frame.setContentPane(new PanelMenu(frame));
			Player.resetJoueur();
			boutonRetour.setVisible(false);
			frame.repaint();
		}
		
		if(e.getSource()==buttonInfo)
		{
			if(statut)
			{
				statut=false;
			}
			
			else
			{
				statut=true;
			}
			this.repaint();

		}
		
	}	

}