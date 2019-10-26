package players;


import game.Box;
import game.GameBoard;
import pathFinder.Finder;

/**
 * Intelligence artificielle facile
 * @author Arnaud.P
 *
 */
public class EasyAI extends Player {
	private Finder pathFinder;//finder utilisé pour chercher son chemin et le chemin de l'adversaire
	
	/**
	 * @author Arnaud.P
	 * @param name
	 */
	public EasyAI(String name) {
		super(name);
		this.IA=true;
		
	}
	/**
	 * Fait jouer l'IA
	 * @author Arnaud.P
	 * @return un entier qui indique si l'ia a reussi à joueur
	 * @param gameBoard
	 * @param listeJoueurs
	 * @param caseDest inutilisé ici
	 * @param mur inutilisé ici
	 */
	public int play(GameBoard gameBoard, Player[] listeJoueurs, Box caseDest, Box[][] mur){
		this.pathFinder=new Finder(gameBoard);
		
		Player adversaire=null;
		for (Player joueur : listeJoueurs) {//on détermine l'adversaire
			if(joueur !=this){
				adversaire=joueur;
			}
		}

		Box[] cheminAdverse=pathFinder.callFindPath(adversaire);
		
		Box[] monChemin=pathFinder.callFindPath(this);

		try{
			if(cheminAdverse.length>=monChemin.length){
				Box caseAJouer=monChemin[1];//case suivant celle sur laquelle on se trouve dans le chemin
				gameBoard.movePiece(this, caseAJouer);
				nbrDeplacement++;
			}
			else if(cheminAdverse.length<monChemin.length){
				int value=GameBoard.Impossible;
				for (int i=0;i<cheminAdverse.length-1;i++) {//-1 car ça ne sert à rien de mettre des murs sur la derniere case du chemin car c'est la case d'arrivée
					Box caseCourante=cheminAdverse[i];
					int caseCouranteI=caseCourante.getI(),caseCouranteJ=caseCourante.getJ();
						
						if(cheminAdverse[i+1].getJ()>caseCouranteJ){//le joueur va se diriger vers la droite
							 value=putEastWall(gameBoard, listeJoueurs, caseCourante);
							 if(value==GameBoard.OK){
								 break;
							 }else{
								 continue;
							 }
						}
						else if(cheminAdverse[i+1].getJ()<caseCouranteJ){//le joueur vas aller vers la gauche
							 value=putWestWall(gameBoard, listeJoueurs, caseCourante);
							 if(value==GameBoard.OK){
								 break;
							 }else{
								 continue;
							 }
							 
						}
						else if(cheminAdverse[i+1].getI()<caseCouranteI){// le joueur veut aller en haut
							value=putNorthWall(gameBoard, listeJoueurs, caseCourante);
							if(value==GameBoard.OK){
								 break;
							 }else{
								 continue;
							 }
						}
						else if(cheminAdverse[i+1].getI()>caseCouranteI){//le joueur veut aller en bas
							value=putSouthWall(gameBoard, listeJoueurs, caseCourante);
							if(value==GameBoard.OK){
								 break;
							 }else{
								 continue;
							 }
						}
				}
				//on a pas pus mettre de mur donc on va bouger le pion
				if(value !=GameBoard.OK){
					Box caseAJouer=monChemin[1];//case suivant celle sur laquelle on se trouve dans le chemin
					gameBoard.movePiece(this, caseAJouer);
					nbrDeplacement++;
				}
			}
		}
		/**
		 * il peut arriver que le chemin soit nul car un pion de joueur bloque le seul chemin possible comme ce pion bougera par apres c'est normal
		 * pour eviter un nullPointerException on met un try catch 
		 * dans le catch l'ia va bouger son pion sur une des cases voisines
		 */
		catch(NullPointerException e){
			Box[] whereMoove=gameBoard.whereMovePiece(this.pion.getBox());
			
			if(this.nbrJoueur==1){
				int i=10;//on met 10 car on est sur qu'il y aura un i <10 donc maBox ne sera jamais null;
				Box maBox=null;
				for (Box box : whereMoove) {
					if(box.getI()<=i){
						i=box.getI();
						maBox=box;
					}
				}
				gameBoard.movePiece(this, maBox);
				nbrDeplacement++;
			}
			
			else{
				int i=-4;//on met -4 car on est sur qu'il y aura une case dont i>-4 donc maBox ne sera jamais null;
				Box maBox=null;
				for (Box box : whereMoove) {
					if(box.getI()>=i){
						i=box.getI();
						maBox=box;
					}
				}
				gameBoard.movePiece(this, maBox);
			}
			
			
		}
		return GameBoard.OK;
	}
	
	/**
	 * essaye de placer un mur au Sud de la case caseCourante
	 * @param gameBoard
	 * @param listeJoueurs
	 * @param caseCourante
	 * @return un entier servant à savoir si elle a pu placer le mur ou pas
	 */
	private int putSouthWall(GameBoard gameBoard,Player[] listeJoueurs,Box caseCourante){
		int value=GameBoard.Impossible;
		int caseCouranteI=caseCourante.getI();
		int caseCouranteJ=caseCourante.getJ();
		Box[][] plateau=gameBoard.getPlateau();
		if(caseCouranteI+1<plateau.length && caseCouranteJ+1<plateau[0].length){
			Box[][] murAPlacerH1={{plateau[caseCouranteI][caseCouranteJ], plateau[caseCouranteI+1][caseCouranteJ]},
					{plateau[caseCouranteI][caseCouranteJ+1], plateau[caseCouranteI+1][caseCouranteJ+1]}};
			value=gameBoard.putWall(this, murAPlacerH1, listeJoueurs);
			if(value==GameBoard.OK){return value;}
		}
		else if(value!=GameBoard.OK && caseCouranteJ-1>=0 && caseCouranteI+1<plateau.length){
			Box[][] murAPlacerH2={{plateau[caseCouranteI][caseCouranteJ-1], plateau[caseCouranteI+1][caseCouranteJ-1]},
					{plateau[caseCouranteI][caseCouranteJ], plateau[caseCouranteI+1][caseCouranteJ]}};
			value=gameBoard.putWall(this, murAPlacerH2, listeJoueurs);
		}
		return value;
	}
	
	/**
	 * essaye de placer un mur au Nord de la case caseCourante
	 * @param gameBoard
	 * @param listeJoueurs
	 * @param caseCourante
	 * @return un entier servant à savoir si elle a pu placer le mur ou pas
	 */
	private int putNorthWall(GameBoard gameBoard,Player[] listeJoueurs,Box caseCourante){
		int value=GameBoard.Impossible;
		int caseCouranteI=caseCourante.getI();
		int caseCouranteJ=caseCourante.getJ();
		Box[][] plateau=gameBoard.getPlateau();
		if(caseCouranteI-1>=0 && caseCouranteJ+1<plateau[0].length){
			Box[][] murAPlacerH1={{plateau[caseCouranteI-1][caseCouranteJ], plateau[caseCouranteI][caseCouranteJ]},
					{plateau[caseCouranteI-1][caseCouranteJ+1], plateau[caseCouranteI][caseCouranteJ+1]}};
			value=gameBoard.putWall(this, murAPlacerH1, listeJoueurs);
			if(value==GameBoard.OK){return value;}
		}
		else if(value!=GameBoard.OK && caseCouranteI-1>=0 && caseCouranteJ-1>=0){
			Box[][] murAPlacerH2={{plateau[caseCouranteI-1][caseCouranteJ-1], plateau[caseCouranteI][caseCouranteJ-1]},
					{plateau[caseCouranteI-1][caseCouranteJ], plateau[caseCouranteI][caseCouranteJ]}};
			value=gameBoard.putWall(this, murAPlacerH2, listeJoueurs);
		}
		return value;
	}
	
	/**
	 * essaye de placer un mur à l'est de la case caseCourante
	 * @param gameBoard
	 * @param listeJoueurs
	 * @param caseCourante
	 * @return un entier servant à savoir si elle a pu placer le mur ou pas
	 */
	private int putEastWall(GameBoard gameBoard,Player[] listeJoueurs,Box caseCourante){//droite
		int value=GameBoard.Impossible;
		int caseCouranteI=caseCourante.getI();
		int caseCouranteJ=caseCourante.getJ();
		Box[][] plateau=gameBoard.getPlateau();
		
		if(caseCouranteJ+1<plateau[0].length && caseCouranteI+1<plateau.length){
			Box[][] murAPlacerV1={{plateau[caseCouranteI][caseCouranteJ],plateau[caseCouranteI][caseCouranteJ+1]},
					{plateau[caseCouranteI+1][caseCouranteJ], plateau[caseCouranteI+1][caseCouranteJ+1]}};
			value=gameBoard.putWall(this, murAPlacerV1, listeJoueurs);
			if(value==GameBoard.OK){return value;}
		}
		else if(value!=GameBoard.OK && caseCouranteI-1>=0 && caseCouranteJ+1<plateau[0].length ){
			Box[][] murAPlacerV2={{plateau[caseCouranteI-1][caseCouranteJ],plateau[caseCouranteI-1][caseCouranteJ+1]},
					{plateau[caseCouranteI][caseCouranteJ],plateau[caseCouranteI][caseCouranteJ+1]}};
			value=gameBoard.putWall(this, murAPlacerV2, listeJoueurs);
		}
		return value;
	}
	
	/**
	 * essaye de placer un mur à l'ouest de la case caseCourante
	 * @param gameBoard
	 * @param listeJoueurs
	 * @param caseCourante
	 * @return un entier servant à savoir si elle a pu placer le mur ou pas
	 */
	private int putWestWall(GameBoard gameBoard,Player[] listeJoueurs,Box caseCourante){
		int value=GameBoard.Impossible;
		int caseCouranteI=caseCourante.getI();
		int caseCouranteJ=caseCourante.getJ();
		Box[][] plateau=gameBoard.getPlateau();
		
		if(caseCouranteI-1>=0 && caseCouranteJ-1>=0){
			Box[][] murAPlacerV1={{plateau[caseCouranteI-1][caseCouranteJ-1], plateau[caseCouranteI-1][caseCouranteJ]},
					{plateau[caseCouranteI][caseCouranteJ-1], plateau[caseCouranteI][caseCouranteJ]}};
			value=gameBoard.putWall(this, murAPlacerV1, listeJoueurs);
			
			if(value==GameBoard.OK){return value;}
		}
		
		else if(value!=GameBoard.OK && caseCouranteJ-1>=0 && caseCouranteI+1>=0){
			Box[][] murAPlacerV2={{plateau[caseCouranteI][caseCouranteJ-1], plateau[caseCouranteI][caseCouranteJ]},
					{plateau[caseCouranteI+1][caseCouranteJ-1], plateau[caseCouranteI+1][caseCouranteJ]}};
			value=gameBoard.putWall(this, murAPlacerV2, listeJoueurs);
		}
		
		return value;
	}
}