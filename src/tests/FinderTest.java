package tests;

import game.Box;
import game.Game;
import game.GameBoard;
import org.junit.*;

import pathFinder.Finder;
import players.Player;

import static org.junit.Assert.*;

/**
 * Classe de test pour le pathFinder
 * 
 * @author Arnaud.P
 *
 */
public class FinderTest {
	Box[][] plateau; // plateau du gameBoard
	GameBoard gameboard;// un gameBoard
	Player joueur;// un joueur
	Finder finder;// un finder
	Game jeu;

	/**
	 * céer un nouveau jeu et modifie le nombre de mur du joueur à 100
	 * 
	 * @author Arnaud.P
	 */
	@Before
	public void setUp() {
		joueur = new Player("joueur test");

		Player[] joueurs = { joueur };
		jeu = new Game(joueurs, null, null);// CREER UN NOUVEAU JEU

		gameboard = jeu.getGameboard();// recuperer le gameBoard
		plateau = gameboard.getPlateau();// recuperer le plateau du gameBoard
		joueur.setNombreMur(100);// modifier ce nombre de mur a 100
		finder = new Finder(gameboard);
	}

	/**
	 * réinitialise le pathBoard, le plateau, le gameboard, le joueur et le
	 * finder
	 * 
	 * @author Arnaud.P
	 */
	@After
	public void tearDown() {
		for (int i = 0; i < plateau.length; i++) {
			for (int j = 0; j < plateau[0].length; j++) {
				Box cases = plateau[i][j];
				cases.setMur_b(null);
				cases.setMur_d(null);
				cases.setMur_g(null);
				cases.setMur_h(null);
			}
		}
		Player.resetJoueur();
		plateau = null;
		gameboard = null;
		joueur = null;
		finder = null;
	}

	/**
	 * Test le finder avec un seul chemin possible
	 * 
	 * @author Arnaud.P
	 */
	@Test
	public void test_cheminUnique() {
		// créer une liste pour chaque mur
		Box[][] mur1 = { { plateau[0][0], plateau[1][0] }, { plateau[0][1], plateau[1][1] } };//
		Box[][] mur2 = { { plateau[1][0], plateau[1][1] }, { plateau[2][0], plateau[2][1] } };// v
		Box[][] mur3 = { { plateau[3][0], plateau[4][0] }, { plateau[3][1], plateau[4][1] } };// h
		Box[][] mur4 = { { plateau[2][1], plateau[3][1] }, { plateau[2][2], plateau[3][2] } };// h
		Box[][] mur5 = { { plateau[3][2], plateau[4][2] }, { plateau[3][3], plateau[4][3] } };// h

		Box[][] mur6 = { { plateau[4][6], plateau[4][7] }, { plateau[5][6], plateau[5][7] } };// V
		Box[][] mur7 = { { plateau[4][5], plateau[4][6] }, { plateau[5][5], plateau[5][6] } };// V
		Box[][] mur8 = { { plateau[6][6], plateau[6][7] }, { plateau[7][6], plateau[7][7] } };// V
		// Box [][]mur9={{plateau[6][5],plateau[7][5]},
		// {plateau[6][6],plateau[7][6]}};//H
		Box[][] mur10 = { { plateau[7][5], plateau[8][5] }, { plateau[7][6], plateau[8][6] } };// H

		Box[][] mur11 = { { plateau[2][3], plateau[3][3] }, { plateau[2][4], plateau[3][4] } };// H
		Box[][] mur12 = { { plateau[3][4], plateau[4][4] }, { plateau[3][5], plateau[4][5] } };// H
		Box[][] mur13 = { { plateau[2][5], plateau[3][5] }, { plateau[2][6], plateau[3][6] } };// H
		Box[][] mur14 = { { plateau[0][7], plateau[1][7] }, { plateau[0][8], plateau[1][8] } };// H
		Box[][] mur15 = { { plateau[2][6], plateau[2][7] }, { plateau[3][6], plateau[3][7] } };// V

		Box[][] mur16 = { { plateau[6][3], plateau[6][4] }, { plateau[7][3], plateau[7][4] } };// V
		Box[][] mur17 = { { plateau[5][4], plateau[6][4] }, { plateau[5][5], plateau[6][5] } };// H
		int retour;
		// placer les murs
		retour = gameboard.putWall(joueur, mur1, jeu.getListeJoueurs());
		assertTrue("n'a pas pus placer le mur", retour == 1);

		retour = gameboard.putWall(joueur, mur2, jeu.getListeJoueurs());
		assertTrue("n'a pas pus placer le mur", retour == 1);

		retour = gameboard.putWall(joueur, mur3, jeu.getListeJoueurs());
		assertTrue("n'a pas pus placer le mur", retour == 1);

		retour = gameboard.putWall(joueur, mur4, jeu.getListeJoueurs());
		assertTrue("n'a pas pus placer le mur", retour == 1);

		retour = gameboard.putWall(joueur, mur5, jeu.getListeJoueurs());
		assertTrue("Il n'ya a pas de chemin (null)", retour == 1);

		retour = gameboard.putWall(joueur, mur6, jeu.getListeJoueurs());
		assertTrue("Il n'ya a pas de chemin (null)", retour == 1);

		retour = gameboard.putWall(joueur, mur7, jeu.getListeJoueurs());
		assertTrue("Il n'ya a pas de chemin (null)", retour == 1);

		retour = gameboard.putWall(joueur, mur8, jeu.getListeJoueurs());
		assertTrue("Il n'ya a pas de chemin (null)", retour == 1);

		retour = gameboard.putWall(joueur, mur10, jeu.getListeJoueurs());
		assertTrue("Il n'ya a pas de chemin (null)", retour == 1);

		retour = gameboard.putWall(joueur, mur11, jeu.getListeJoueurs());
		assertTrue("Il n'ya a pas de chemin (null)", retour == 1);

		retour = gameboard.putWall(joueur, mur12, jeu.getListeJoueurs());
		assertTrue("Il n'ya a pas de chemin (null)", retour == 1);

		retour = gameboard.putWall(joueur, mur13, jeu.getListeJoueurs());
		assertTrue("Il n'ya a pas de chemin (null)", retour == 1);

		retour = gameboard.putWall(joueur, mur14, jeu.getListeJoueurs());
		assertTrue("Il n'ya a pas de chemin (null)", retour == 1);

		retour = gameboard.putWall(joueur, mur15, jeu.getListeJoueurs());
		assertTrue("Il n'ya a pas de chemin (null)", retour == 1);

		retour = gameboard.putWall(joueur, mur16, jeu.getListeJoueurs());
		assertTrue("Il n'ya a pas de chemin (null)", retour == 1);

		retour = gameboard.putWall(joueur, mur17, jeu.getListeJoueurs());
		assertTrue("Il n'ya a pas de chemin (null)", retour == 1);

	}

	/**
	 * test le finder sur un plateau où il n'y a aucun chemin possible
	 * @author Arnaud.P
	 */
	@Test
	public void pasChemin() {
		// créer une liste pour chaque mur
		Box[][] mur1 = { { plateau[0][0], plateau[1][0] }, { plateau[0][1], plateau[1][1] } };// h
		Box[][] mur2 = { { plateau[1][0], plateau[1][1] }, { plateau[2][0], plateau[2][1] } };// v
		Box[][] mur3 = { { plateau[3][0], plateau[4][0] }, { plateau[3][1], plateau[4][1] } };// h
		Box[][] mur4 = { { plateau[2][1], plateau[3][1] }, { plateau[2][2], plateau[3][2] } };// h
		Box[][] mur5 = { { plateau[3][2], plateau[4][2] }, { plateau[3][3], plateau[4][3] } };// h

		Box[][] mur6 = { { plateau[4][6], plateau[4][7] }, { plateau[5][6], plateau[5][7] } };// V
		Box[][] mur7 = { { plateau[4][5], plateau[4][6] }, { plateau[5][5], plateau[5][6] } };// V
		Box[][] mur8 = { { plateau[6][6], plateau[6][7] }, { plateau[7][6], plateau[7][7] } };// V

		Box[][] mur10 = { { plateau[7][5], plateau[8][5] }, { plateau[7][6], plateau[8][6] } };// H

		Box[][] mur11 = { { plateau[2][3], plateau[3][3] }, { plateau[2][4], plateau[3][4] } };// H
		Box[][] mur12 = { { plateau[3][4], plateau[4][4] }, { plateau[3][5], plateau[4][5] } };// H
		Box[][] mur13 = { { plateau[2][5], plateau[3][5] }, { plateau[2][6], plateau[3][6] } };// H
		Box[][] mur14 = { { plateau[0][7], plateau[1][7] }, { plateau[0][8], plateau[1][8] } };// H
		Box[][] mur15 = { { plateau[2][6], plateau[2][7] }, { plateau[3][6], plateau[3][7] } };// V

		Box[][] mur16 = { { plateau[6][3], plateau[6][4] }, { plateau[7][3], plateau[7][4] } };// V
		Box[][] mur17 = { { plateau[5][4], plateau[6][4] }, { plateau[5][5], plateau[6][5] } };// H

		Box[][] mur18 = { { plateau[0][6], plateau[0][7] }, { plateau[1][6], plateau[1][7] } };// V

		// placer les murs
		gameboard.putWall(joueur, mur1, jeu.getListeJoueurs());
		gameboard.putWall(joueur, mur2, jeu.getListeJoueurs());
		gameboard.putWall(joueur, mur3, jeu.getListeJoueurs());
		gameboard.putWall(joueur, mur4, jeu.getListeJoueurs());
		gameboard.putWall(joueur, mur5, jeu.getListeJoueurs());

		gameboard.putWall(joueur, mur6, jeu.getListeJoueurs());
		gameboard.putWall(joueur, mur7, jeu.getListeJoueurs());
		gameboard.putWall(joueur, mur8, jeu.getListeJoueurs());
		gameboard.putWall(joueur, mur10, jeu.getListeJoueurs());

		gameboard.putWall(joueur, mur11, jeu.getListeJoueurs());
		gameboard.putWall(joueur, mur12, jeu.getListeJoueurs());
		gameboard.putWall(joueur, mur13, jeu.getListeJoueurs());
		gameboard.putWall(joueur, mur14, jeu.getListeJoueurs());
		gameboard.putWall(joueur, mur15, jeu.getListeJoueurs());

		gameboard.putWall(joueur, mur16, jeu.getListeJoueurs());
		gameboard.putWall(joueur, mur17, jeu.getListeJoueurs());
		int retour = gameboard.putWall(joueur, mur18, jeu.getListeJoueurs());

		assertTrue("probleme1b", retour != 1);
	}
	
	/**
	 * test un chemin avec lequel on a eu une erreur
	 * @author Arnaud.P
	 */
	@Test
	public void test() {
		joueur.getPiece().setCases(plateau[6][8]);
		plateau[6][8].setPion(joueur.getPiece());

		Box[][] mur1 = { { plateau[4][7], plateau[4][8] }, { plateau[5][7], plateau[5][8] } };
		Box[][] mur2 = { { plateau[6][6], plateau[7][6] }, { plateau[6][7], plateau[7][7] } };
		Box[][] mur3 = { { plateau[7][6], plateau[7][7] }, { plateau[8][6], plateau[8][7] } };
		Box[][] mur4 = { { plateau[7][7], plateau[8][7] }, { plateau[8][7], plateau[8][8] } };

		gameboard.putWall(joueur, mur1, jeu.getListeJoueurs());
		gameboard.putWall(joueur, mur2, jeu.getListeJoueurs());
		gameboard.putWall(joueur, mur3, jeu.getListeJoueurs());
		gameboard.putWall(joueur, mur4, jeu.getListeJoueurs());
		
	}

}
