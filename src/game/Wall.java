package game;

import players.Player;

import java.io.Serializable;


/**
 * Classe des murs
 * @author Arnaud.P
 *
 */
public class Wall implements Serializable {
	private int wallLength;//longueur d'un mur
	private Player joueur;//joueur à qui apprtient le mur
	private char orientation;//horientation du mur
	
	/**
	 * @author Arnaud.P
	 * @param joueur à qui appartient le mur
	 * @param orientation du mur
	 */
	public Wall(Player joueur, char orientation) {
		
		this.wallLength=2;
		this.joueur=joueur;
		this.orientation=orientation;
	}
	
	@Override
	public String toString(){
		return "je suis un mur de longueur: "+wallLength+", d'orientation: "+orientation+" et j'appartient à: "+ joueur.getNom()+" "+this.hashCode();
	}
}
