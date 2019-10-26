/**
 * 
 */
package game;

import players.Player;

import java.io.Serializable;


/**
 * Rassemble les données qui doivent etre sauvegardées( gameBoard, indexJoueurAJouer,listeJouers)
 * @author Arnaud.P
 */
public class Save implements Serializable {
	
	private GameBoard gameboard;
	private Integer indexJoueurAJouer;
	private Player[] listeJouers;
	
	/**
	 * @author Arnaud.P
	 * @param jeu
	 */
	public Save(Game jeu) {
		this.gameboard=jeu.getGameboard();
		this.indexJoueurAJouer=jeu.getNbrWitchPlayer();
		this.listeJouers=jeu.getListeJoueurs();
	}
	
	/**
	 * 
	 * @author Arnaud.P
	 * @return gameboard
	 */
	public GameBoard getGameboard() {
		return gameboard;
	}
	
	/**
	 * 
	 * @author Arnaud.P
	 * @return indexJoueurAJouer
	 */
	public Integer getIndexJoueurAJouer() {
		return indexJoueurAJouer;
	}
	
	/**
	 * 
	 * @author Arnaud.P
	 * @return listeJouers
	 */
	public Player[] getListeJouers() {
		return listeJouers;
	}

}
