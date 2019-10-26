package players;

import game.Box;
import game.GameBoard;

public class HumanPlayer extends Player {

	public HumanPlayer(String name) {
		super(name);
		name.toCharArray();
		
		this.IA=false;
	}
	
	public int play(GameBoard gameBoard, Player[] listeJoueurs, Box caseDest, Box[][] mur){
		int retour=21;
		if(caseDest==null){
			retour=gameBoard.putWall(this, mur, listeJoueurs);
		}
		else{
			retour=gameBoard.movePiece(this, caseDest);
			if(retour==GameBoard.OK){
				nbrDeplacement++;
			}
		}
		
		return retour;
	}
}
