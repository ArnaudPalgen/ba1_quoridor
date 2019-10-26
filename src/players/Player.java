package players;

import game.Box;
import game.GameBoard;
import game.Piece;

import java.io.Serializable;

public class Player implements Serializable {
	protected String name;
	protected int nbrJoueur, nbrMur,nbrDeplacement;
	protected boolean IA;
	protected Piece pion;
	protected static int joueurStatic=0;
	protected static int nbrMurDepart=10;
	
	/**
	 * 
	 */
	public Player(String name) {
		this.name=name;
		this.nbrMur=nbrMurDepart;
		this.pion=null;
		joueurStatic++;
		this.nbrJoueur=joueurStatic;
		this.IA=false;
		this.nbrDeplacement=0;
	}
	public Piece getPiece(){
		return this.pion;
	}
	public int getNombreMur(){
		return this.nbrMur;
	}
	public void setNombreMur(int nbrMur){
		this.nbrMur=nbrMur;
	}
	public String getNom(){
		return this.name;
	}
	public int getNbrJoueur(){
		return this.nbrJoueur;
	}
	public void setPion(Piece pion){
		if(pion !=null){
			this.pion=pion;
			pion.getBox().setPion(pion);
		}
		else{
			this.pion=null;
		}
	}
	public static void resetJoueur(){
		joueurStatic=0;
	}
	public int play(GameBoard gameBoard, Player[] listeJoueurs, Box caseDest, Box[][] mur){
		return 21;//on ne va jamais passer par cette méthode car elle est surchargee dans toute les classes 
			//enfants donc on retourne l'entier qu'on veut
	}
	public String toString(){
		String isIA;
		if(this.IA){
			isIA="IA ";
		}
		else{
			isIA="HU ";
		}
		return isIA+this.name+" ";
	}
	public void setNbrJoueur(int nbrJoueur) {
		this.nbrJoueur = nbrJoueur;
	}
	public static int getNbrMurDepart() {
		return nbrMurDepart;
	}
	public int getNbrDeplacement() {
		return nbrDeplacement;
	}
	public void setNbrDeplacement(int nbrDeplacement) {
		this.nbrDeplacement = nbrDeplacement;
	}
	public boolean isIA() {
		return IA;
	}
}
