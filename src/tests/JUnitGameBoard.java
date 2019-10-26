package tests;

import static org.junit.Assert.*;

import game.Box;
import game.Game;
import game.GameBoard;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pathFinder.Finder;
import players.HumanPlayer;
import players.Player;


/**
 * Classe de test pour les méthodes qui pourraient poser probleème dans le gameBoard
 * @author Nono21
 *
 */
public class JUnitGameBoard {
	
	Box[][] plateau; // plateau du gameBoard
	GameBoard gameboard;// un gameBoard
	Player joueur1,joueur2;//un joueur
	Finder finder;//un finder
	Game jeu; // le jeu
	Player[] joueurs; // la liste des joueurs
	/**
	 * Crée un nouveau jeu avec deux joueurs humains et modifie le nombre de murs d'un 
	 * joueur à 100 pour les tests concernant les placements de murs
	 * @author Arnaud.P
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		joueur1=new HumanPlayer("joueur test1");
		joueur2=new HumanPlayer("joueur test2");
		joueurs=new Player[2];
		joueurs[0]=joueur1;
		joueurs[1]=joueur2;
		jeu=new Game(joueurs, null,null );//CREER UN NOUVEAU JEU

		gameboard=jeu.getGameboard();//créer un nouveau gameBoard
		plateau=jeu.getTableau();//recuperer le plateau du gameBoard
		joueur1.setNombreMur(100);//modifier ce nombre de mur a 100
	}
	
	/**
	 * Enlève les murs de toutes les cases et réinitialise le compteur qui attribue le numéro d'un joueur
	 * @author Arnaud.P
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		for(int i=0;i<plateau.length;i++){//enlever tous les murs
			for(int j=0;j<plateau[0].length;j++){
				plateau[i][j].setMur_b(null);
				plateau[i][j].setMur_h(null);
				plateau[i][j].setMur_d(null);
				plateau[i][j].setMur_g(null);
			}
		}
		Player.resetJoueur();
	}
	
	/**
	 * tester de se faire croiser deux murs ( ce qui est impossible)
	 * @author Arnaud.P
	 */
	@Test
	public void testPutWall1() {
		Box [][]mur1={{plateau[5][1],plateau[5][2]}, {plateau[6][1],plateau[6][2]}};//v
		Box [][]mur2={{plateau[5][1],plateau[6][1]}, {plateau[5][2],plateau[6][2]}};//H
		
		assertTrue ("Impossible de placer mur 1 dans test1",gameboard.putWall(joueur1, mur1,jeu.getListeJoueurs())==1);
		assertTrue(" Un mur peut en couper un autre",gameboard.putWall(joueur1, mur2,jeu.getListeJoueurs())==0);
	}
	
	/**
	 * tester un mur entre deux autres(ce qui est possible)
	 * @author Arnaud.P
	 */
	@Test
	public void testPutWall2(){
		Box [][]mur1={{plateau[4][1],plateau[4][2]}, {plateau[5][1],plateau[5][2]}};//V
		Box [][]mur2={{plateau[6][1],plateau[6][2]}, {plateau[7][1],plateau[7][2]}};//V
		Box [][]mur3={{plateau[5][1],plateau[6][1]}, {plateau[5][2],plateau[6][2]}};//H
		
		assertTrue ("",gameboard.putWall(joueur1, mur1,jeu.getListeJoueurs())==1);
		//gameboard.affiche();
		assertTrue ("Impossible de placer mur 1 dans test2",gameboard.putWall(joueur1, mur2,jeu.getListeJoueurs())==1);
		assertTrue ("Impossible de placer mur 2 dans test2",gameboard.putWall(joueur1, mur3,jeu.getListeJoueurs())==1);
	}
	
	/**
	 * tester si il ne met pas un mur là où il y en a deja un (verticalement)
	 * @author Arnaud.P
	 */
	@Test
	public void testPutWall3(){
		Box [][]mur1={{plateau[4][1],plateau[4][2]}, {plateau[5][1],plateau[5][2]}};//V
		Box [][]mur1b={{plateau[4][1],plateau[4][2]}, {plateau[5][1],plateau[5][2]}};//V
		
		
		
		assertTrue ("Impossible de placer mur 1 dans test3",gameboard.putWall(joueur1, mur1,jeu.getListeJoueurs())==1);
		assertTrue ("mur1B dans test 3 est autorisé et il ne devrait pas l'etre",gameboard.putWall(joueur1, mur1b,jeu.getListeJoueurs())==0);
		
	}
	
	/**
	 * tester si il ne met pas un mur là où il y en a deja un(horizontalement)
	 * @author Arnaud.P
	 */
	@Test
	public void testPutWall4(){
		Box [][]mur2={{plateau[5][1],plateau[6][1]}, {plateau[5][2],plateau[6][2]}};//H
		Box [][]mur2b={{plateau[5][1],plateau[6][1]}, {plateau[5][2],plateau[6][2]}};//H
		
		assertTrue ("Impossible de placer mur 2 dans test4",gameboard.putWall(joueur1, mur2,jeu.getListeJoueurs())==1);
		assertTrue ("mur2B dans test 4 est autorisé et il ne devrait pas l'etre",gameboard.putWall(joueur1, mur2b,jeu.getListeJoueurs())==0);
	}
	
	/**
	 * Test face à face vers le haut pour les pions
	 * @author Arnaud.P
	 */
	@Test
	public void testFaceAFaceBasHaut() {
		//on place le joueur 1 en case 4,4
		plateau[4][4].setPion(joueur1.getPiece());
		joueur1.getPiece().setCases(plateau[4][4]);
		
		//on place le joueur 2 en case 3,4
		plateau[3][4].setPion(joueur2.getPiece());
		joueur2.getPiece().setCases(plateau[3][4]);
		
		//ce que l'on devrait obtenir
		Box[] whereTrueB={plateau[2][4],plateau[3][5],plateau[3][3],plateau[5][4]};
		Box[] whereTrueH={plateau[2][4],plateau[4][3], plateau[4][5], plateau[5][4]};
		
		//ce que l'on obtient
		Box[] whereResultB=gameboard.whereMovePiece(joueur2.getPiece().getBox());
		Box[] whereResultH=gameboard.whereMovePiece(joueur1.getPiece().getBox());
		
		assertTrue("WhereMoovePiece ne prend pas en compte les faces à faces vers le bas", equals(whereResultB,whereTrueB));
		assertTrue("WhereMoovePiece ne prend pas en compte les faces à faces vers le haut",equals(whereResultH,whereTrueH));

	}
	
	/**
	 * test Face A Face pour les pions Gauche Droite
	 * @author Arnaud.P
	 */
	@Test
	public void testFaceAFaceGaucheDroite(){
		//on place le joueur 1 en case 4,4
		plateau[4][3].setPion(joueur1.getPiece());
		joueur1.getPiece().setCases(plateau[4][3]);
		
		//on place le joueur 2 en case 3,4
		plateau[4][4].setPion(joueur2.getPiece());
		joueur2.getPiece().setCases(plateau[4][4]);
		
		//ce que l'on devrai obtenir
		Box[] whereTrueG={plateau[4][2], plateau[3][4], plateau[5][4], plateau[4][5]};
		Box[] whereTrueD={plateau[4][5], plateau[3][3], plateau[5][3], plateau[4][2]};
		
		//ce que l'on obtient
		Box[] whereResultG=gameboard.whereMovePiece(joueur2.getPiece().getBox());
		Box[] whereResultD=gameboard.whereMovePiece(joueur1.getPiece().getBox());
		
		assertTrue("WhereMoovePiece ne prend pas en compte les faces à faces vers la gauche", equals(whereResultG, whereTrueG));
		//affiche(whereResultG);
		//affiche(whereTrueG);
		assertTrue("WhereMoovePiece ne prend pas en compte les faces à faces vers la droite", equals(whereTrueD, whereResultD));
		//affiche(whereResultD);
		//affiche(whereTrueD);
	}
	
	/**
	 * test Face A Face Bas Haut avec Mur
	 * @author Arnaud.P
	 */
	@Test
	public void testFaceAFaceBasHautMur(){
		//on place le joueur 1 en case 4,4
		plateau[4][4].setPion(joueur1.getPiece());
		joueur1.getPiece().setCases(plateau[4][4]);
		
		//on place le joueur 2 en case 3,4
		plateau[3][4].setPion(joueur2.getPiece());
		joueur2.getPiece().setCases(plateau[3][4]);
		
		//on place les murs
		Box[][] mur1={{plateau[4][4], plateau[5][4]},{plateau[4][5], plateau[5][5]}};
		Box[][] mur2={{plateau[2][4], plateau[3][4]},{plateau[2][5], plateau[3][5]}};
		gameboard.putWall(joueur1, mur1,joueurs );
		gameboard.putWall(joueur1, mur2,joueurs );
		
		//ce que l'on devrait obtenir
		Box[] whereTrueB={plateau[4][3], plateau[3][3], plateau[3][5], plateau[4][5]};
		Box[] whereTrueH={plateau[4][3],plateau[4][5],plateau[3][3],plateau[3][5]};
		
		//ce que l'on obtient
		Box[] whereResultB=gameboard.whereMovePiece(joueur2.getPiece().getBox());
		Box[] whereResultH=gameboard.whereMovePiece(joueur1.getPiece().getBox());

//		affiche(whereResultB);
//		affiche(whereTrueB);
		assertTrue("toto1", equals(whereResultB, whereTrueB));

		assertTrue("toto2", equals(whereTrueH, whereResultH));
//		affiche(whereResultH);
//		affiche(whereTrueH);
	}
	
	/**
	 * test Face A Face Gauche Droite avec Mur
	 * @author Arnaud.P
	 */
	@Test
	public void testFaceAFaceGaucheDroiteMur(){
		//on place le joueur 1 en case 4,4
		plateau[4][3].setPion(joueur1.getPiece());
		joueur1.getPiece().setCases(plateau[4][3]);
		
		//on place le joueur 2 en case 3,4
		plateau[4][4].setPion(joueur2.getPiece());
		joueur2.getPiece().setCases(plateau[4][4]);
		
		//On place les murs
		Box[][] mur1={{plateau[4][2],plateau[4][3]},{plateau[5][2],plateau[5][3]}};
		Box[][] mur2={{plateau[4][4],plateau[4][5]},{plateau[5][4],plateau[5][5]}};
		gameboard.putWall(joueur1, mur1, joueurs);
		gameboard.putWall(joueur1, mur2, joueurs);
		//plateau[][],
		//ce que l'on doit obtenir
		Box[] whereTrue={plateau[3][3],plateau[3][4],plateau[5][3],plateau[5][4]};//besoins d'un seul résultat car les deux sont les mêmes
		
		//ce que l'on obtient
		Box[] whereResultG=gameboard.whereMovePiece(joueur2.getPiece().getBox());
		Box[] whereResultD=gameboard.whereMovePiece(joueur1.getPiece().getBox());
		//affiche(whereTrue);
		//affiche(whereResultD);
		assertTrue("toto3", equals(whereResultG, whereTrue));
		assertTrue("toto4", equals(whereResultD, whereTrue));
	}

	/**
	 * 
	 * @param tabA
	 * @param tabB
	 * @return retourne True si les deux tableau de Box sont égaux
	 */
	public boolean equals(Box[] tabA,Box[] tabB){
		if(tabA.length !=tabB.length){
			return false;
		}
		else{
			for (int i = 0; i < tabB.length; i++) {
				boolean statut=false;
				for (int j = 0; j < tabA.length; j++) {
					if(tabB[i].equals(tabA[j])){
						statut=true;
						break;
					}
				}
				if(statut==false){
					return false;
				}
			}
			return true;
		}
	}
	public void affiche(Box[] tab){
		for (Box box : tab) {
			System.out.print(box);
		}
		System.out.println("\n");
	}

}
