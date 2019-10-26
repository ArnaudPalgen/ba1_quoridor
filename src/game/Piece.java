package game;

import players.Player;

import java.io.Serializable;

//import quoridor.players.Player;
/**
 * 
 * @author Nono21
 *
 */
public class Piece implements Serializable {
	private Box maCase;//case sur laquelle se trouve le pion
	private Player joueur;//joueur à qui appartient le pion
	
	/**
	 * 
	 * @param maCase du pion
	 * @param joueur à qui appartient le pion
	 */
	public Piece( Box maCase,Player joueur) {
		this.maCase=maCase;
		this.joueur=joueur;
		
	}
	
	/**
	 * @author Arnaud.P
	 * @return maCase
	 */
	public Box getBox(){
		return maCase;
	}
	
	/**
	 * @author Arnaud.P
	 * @param nouvCase
	 */
	public void setCases(Box nouvCase){
		this.maCase=nouvCase;
	}
	
	/**
	 * @author Arnaud.P
	 * @return joueur à qui appartient le pion
	 */
	public Player getJoueur() {
		return joueur;
	}

}
