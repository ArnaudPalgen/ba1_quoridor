package game;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import players.Player;
//import quoridor.players.Player;

/**
 * Classe Jeu
 * 
 * @author Arnaud.P
 *
 */
public class Game implements Serializable {
	private GameBoard gameboard;// un GameBoard
	private Box[][] tableau;// le plateau de jeu
	private Player[] listeJoueurs;// liste des joueurs
	private int nbrWitchPlayer;// compteur utilisé dans wichPlayer

	/**
	 * @author Arnaud.P
	 * @param gameBoard
	 *            le plateau de jeu
	 * @param nbrWichPlayer
	 *            utilsé pour savoir quel joueur doit joueur
	 * @param joueurs
	 *            liste des joueurs
	 */
	public Game(Player[] joueurs, GameBoard gameBoard, Integer nbrWichPlayer) {
		if (gameBoard == null && nbrWichPlayer == null) {
			/*
			 * les variables sont null c'est à dire qu'on ne reprend pas à
			 * partir d'une sauvegarde 
			 */			
			gameboard = new GameBoard(9, 9);
			tableau = gameboard.getPlateau();
			this.nbrWitchPlayer = 0;

		} else {// si on reprend à partir d'une sauvegarde
			this.gameboard = gameBoard;
			this.tableau = this.gameboard.getPlateau();
			this.nbrWitchPlayer = nbrWichPlayer;
		}

		listeJoueurs = new Player[joueurs.length];
		/*
		 * on rempli la liste de joueurs et si un joueur n'a pas de pion on lui
		 * en attribue un
		 */
		for (int i = 0; i < joueurs.length; i++) {
			if (joueurs[i].getPiece() == null) {
				Piece pion = new Piece(gameboard.getStartBox(), joueurs[i]);
				joueurs[i].setPion(pion);
			}
			listeJoueurs[i] = joueurs[i];
		}
	}

	/**
	 * @author Arnaud.P
	 * @return Le joueur dont c'est le tour de jouer
	 * ce base sur l'odre dans la liste
	 */
	public Player whichPlayer() {
		int index = nbrWitchPlayer;
		nbrWitchPlayer++;
		if (nbrWitchPlayer == listeJoueurs.length) {
			nbrWitchPlayer = 0;
		}
		return listeJoueurs[index];
	}

	/**
	 * @author Arnaud.P
	 * @param joueur
	 *            pour lequel on vérifie s'il a gangé ou non
	 * @return true si le joueur a gagné, false si non
	 * utilise le numero du joueur
	 */
	public boolean win(Player joueur) {
		int nbrJoueur = joueur.getNbrJoueur();
		if (nbrJoueur == 1 && joueur.getPiece().getBox().getI() == 0) {
			return true;
		} else if (nbrJoueur == 2 && joueur.getPiece().getBox().getI() == tableau.length - 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Sauvegarde une partie
	 * 
	 * @author Arnaud.P
	 * @param saveFile le fichier dans lequel on va sauvegarder la partie
	 * @throws IOException
	 */
	public void Save(File saveFile) throws IOException {
		OutputStream fos = new FileOutputStream(saveFile);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		Save sauvegarde = new Save(this);
		oos.writeObject(sauvegarde);
		oos.close();

	}

	/**
	 * @author Arnaud.P
	 * @return gameBoard du jeu
	 */
	public GameBoard getGameboard() {
		return gameboard;
	}

	/**
	 * @author Arnaud.P
	 * @return tableau de jeu (Box[][])
	 */
	public Box[][] getTableau() {
		return tableau;
	}

	/**
	 * @author Arnaud.P
	 * @return liste des joueurs
	 */
	public Player[] getListeJoueurs() {
		return listeJoueurs;
	}

	/**
	 * @author Arnaud.P
	 * @return nbrWitchPlayer
	 */
	public int getNbrWitchPlayer() {
		return nbrWitchPlayer;
	}

}
