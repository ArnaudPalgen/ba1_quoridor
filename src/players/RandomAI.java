package players;

import game.Box;
import game.GameBoard;

import java.util.Random;

public class RandomAI extends Player {

	/**
	 * @param name
	 */
	public RandomAI(String name) {
		super(name);
		this.IA=true;
	}
	public int play(GameBoard gameBoard, Player[] listeJoueurs, Box caseDest, Box[][] mur){
		int retour=GameBoard.Impossible;
		Box[][] tableau=gameBoard.getPlateau();
		Random generateur=new Random();
		while(retour !=GameBoard.OK){
			int choiceAction=generateur.nextInt(2);//si le choix est 0 alors on place un mur, si le choix est 1 alors on bouge le pion
			
			if(choiceAction==0){
				int choicePut=generateur.nextInt(2);//si c'est 1 on place un horizontal, so c'est 2 alors on place un vertical
				
				if(choicePut==0){
					int i=generateur.nextInt(8);/* le i et le j choisis sont les coordonnees de la case de départ càd que le mur va etre 
					en dessous de cette case et va continuer en dessous de la case à l'indice i+1,j+1 */
					int j=generateur.nextInt(8);
					Box[][] murH={{tableau[i][j],tableau[i+1][j]},{tableau[i][j+1],tableau[i+1][j+1]}};
					retour=gameBoard.putWall(this, murH, listeJoueurs);
				}else{
					
					int i=generateur.nextInt(8);
					int j=generateur.nextInt(8);
					Box[][] murV={{tableau[i][j],tableau[i][j+1]},{tableau[i+1][j],tableau[i+1][j+1]}};
					retour=gameBoard.putWall(this, murV, listeJoueurs);
					
				}
			}else{
				
				Box[] where=gameBoard.whereMovePiece(this.pion.getBox());//liste de cases ou on peut aller
				int index=generateur.nextInt(where.length);//on choisit un index au hasard dans la liste
				retour=gameBoard.movePiece(this, where[index]);
				if(retour==GameBoard.OK){
					nbrDeplacement++;
				}
			}
		}
		
		return retour;
	}
}