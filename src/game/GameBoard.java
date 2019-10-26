package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import pathFinder.Finder;
import players.Player;
/**
 * Plateau de jeu
 * @author Nono21
 *
 */
public class GameBoard implements Serializable {
	private int lengthX,lengthY; //longueur du plateau de jeu
	private Box plateau [][];
	public static final char VERTICAL='v',BAS='b',HORIZONTAL='h', HAUT='o',GAUCHE='g',DROITE='d';
	public static final int MurBloque=2,OK=1,Impossible=0,AucunMurDispo=3;
	/*
	 * MurBloque: signifie que si on place un mur il va fermer tous les chemins
	 * pour un joueur OK signifie: que l'action s'est bien deroulee ( placer un
	 * mur, bouger un pion, ...) Impossible: siginife que l'action n'a pas pu
	 * etre realisee AucunMurDispo: signifie que aucun mur n'est dispo pour un
	 * joueur
	 */

	public GameBoard(int lengthX, int lengthY) {
		this.lengthX = lengthX;
		this.lengthY = lengthY;
		if ((lengthX % 2 != 0) && (lengthY % 2 != 0)) {
			/*
			 * on verifie que la longueur du plateau de jeu est bien un
			 * nombre impair
			 */
			plateau = new Box[this.lengthX][this.lengthY];
			newBoard();
		} else {
			System.out.println("La longueur doit etre impaire");
		}
	}
	
	/**
	 * Crée un nouveau tableau
	 * @author Arnaud.P
	 */
	private void newBoard(){
		boolean canMur_h, canMur_b, canMur_g, canMur_d;
		Piece isPion;
		isPion=null;
		for (int i=0;i<plateau.length;i++){
			
			for(int j=0;j<plateau[0].length;j++){
				if (i==0) // haut
					canMur_h=false;
				else
					canMur_h=true;
				if(i==plateau.length-1)//bas
					canMur_b=false;
				else
					canMur_b=true;
				if(j==0)
					canMur_g=false;
				else
					canMur_g=true;
				if(j==plateau[0].length-1)
					canMur_d=false;
				else
					canMur_d=true;
				
				plateau[i][j]=new Box(isPion, canMur_h, canMur_b, canMur_g, canMur_d,i,j);
			}
		}
	}
		
	/**
	 * @param cases
	 * @return Box[] liste de cases vers lesquelles peut se déplacer un pion
	 * @author Arnaud.P
	 */
	public Box[] whereMovePiece(Box cases){

		int casesI=cases.getI();

		int casesJ=cases.getJ();
		
		char[]mouvements=generateDirectionRandom();

		ArrayList<Box>where=new ArrayList<Box>(0); //va contenir les cases vers lesquelles peut se déplacer un pion

		
		for(int mouv=0; mouv<mouvements.length;mouv++){
			if(mouvements[mouv]==BAS){//bas
				if(cases.isCanMur_b()==true){// si une case en bas de la case courante existe
					Box casesBas=plateau[casesI+1][casesJ]; // on la crée
					
					if(cases.getMur_b() ==null && casesBas.getPion()==null){// si je peux aller à la case en dessous

						where.add(casesBas); //on l'ajoute
						continue;
					}
					else if(cases.getMur_b()==null && casesBas.getPion()!=null){ // si pas de mur en bas de la case et que la case en dessous a un pion
						if(casesBas.isCanMur_b()==true && casesBas.getMur_b() == null){// si une case a i+2 existe et que la case du bas n'as pas de mur

							where.add(plateau[casesI+2][casesJ]);// alors j'ajoute la case i+2
							continue;
						}
						else if(casesBas.getMur_b() != null || casesBas.isCanMur_b()==false){//si il y a un mur sur la case du bas ou que la case du bas et tout en bas du jeu (i=8)
							if(casesBas.getMur_d()==null && casesBas.isCanMur_d()==true ){//on regarde si on peut aller au sud-ouest
								where.add(plateau[casesI+1][casesJ+1]);
							}
							if(casesBas.isCanMur_g()==true &&casesBas.getMur_g() == null){//on regarde si on peut aller au sud-est

								where.add(plateau[casesI+1][casesJ-1]);//si on peut, on ajoute la caase
							}
						}
						continue;
						
					}
				}
			} //meme principe pour les autres directions
			else if(mouvements[mouv]==HAUT){//haut
				if (cases.isCanMur_h()==true && cases.getMur_h() ==null){
					Box casesHaut=plateau[casesI-1][casesJ];
					if(casesHaut.getPion()==null){
						where.add(casesHaut);
						continue;
					}
					else{
						if(casesHaut.getMur_h()==null && casesHaut.isCanMur_h()==true){
							where.add(plateau[casesI-2][casesJ]);
							continue;
						}
						if(casesHaut.getMur_h() != null|| casesHaut.isCanMur_h()==false){
							if(casesHaut.isCanMur_d()==true && casesHaut.getMur_d() ==null){
								where.add(plateau[casesI-1][casesJ+1]);
							}
							if(casesHaut.isCanMur_g()==true && casesHaut.getMur_g()==null){
								where.add(plateau[casesI-1][casesJ-1]);
							}
						}
						continue;
					}
				}
				else{
					continue;
				}
			}
			else if(mouvements[mouv]==DROITE){//droite
				if(cases.isCanMur_d()==true && cases.getMur_d() ==null){
					Box casesDroite=plateau[casesI][casesJ+1];
					if(casesDroite.getPion()==null){
						where.add(casesDroite);
						continue;
					}
					else if(casesDroite.getPion()!=null && casesDroite.isCanMur_d()==true && casesDroite.getMur_d()==null){
						where.add(plateau[casesI][casesJ+2]);
						continue;
					}
					else{
						if(casesDroite.isCanMur_h() && casesDroite.getMur_h()==null){
							where.add(plateau[casesDroite.getI()-1][casesDroite.getJ()]);
						}
						if(casesDroite.isCanMur_b() && casesDroite.getMur_b()==null){
							where.add(plateau[casesDroite.getI()+1][casesDroite.getJ()]);
						}
					}
					continue;
				}
				
			}
			else{//gauche
				if(cases.isCanMur_g()==true && cases.getMur_g() ==null){
					Box casesGauche=plateau[casesI][casesJ-1];
					if(casesGauche.getPion()==null){
						where.add(casesGauche);
						continue;
					}
					else if(casesGauche.getPion()!=null && casesGauche.isCanMur_g()==true && casesGauche.getMur_g()==null){
						where.add(plateau[casesI][casesJ-2]);
						continue;
					}
					else{
						if(cases.isCanMur_h() && casesGauche.getMur_h()==null){
							where.add(plateau[casesI-1][casesJ-1]);
						}
						if(cases.isCanMur_b() && casesGauche.getMur_b()==null){
							where.add(plateau[casesI+1][casesJ-1]);
						}
					}
					continue;
				}
			}
		}
		Box[] newWhere = where.toArray(new Box[where.size()]);
		return newWhere;
		
	}
	
	/**
	 * @author Arnaud.P
	 * @return tableau de directions dans un ordre aléatoire
	 */
	private char[] generateDirectionRandom(){
		char[] directionResult=new char[4];
		int indexDirection=0;
		
		Random generateur=new Random();
		char[]mouvements={HAUT,GAUCHE,BAS,DROITE};
		while(indexDirection<directionResult.length){
			int indexMouvements=generateur.nextInt(4);
			
			char mouvement=mouvements[indexMouvements];
			
			boolean isIndirectionResult=false;
			int i=0;
			while(isIndirectionResult ==false && i<directionResult.length){
				if(directionResult[i]==mouvement){
					isIndirectionResult=true;
				}
				i++;
			}
			if(isIndirectionResult==false){
				directionResult[indexDirection]=mouvement;
				indexDirection++;
			}
		}
		
		return directionResult;
		
	}

	/**
	 * @author Arnaud.P
	 * @return les emplacements des cases de départ
	 */
	public Box getStartBox(){
		if ((plateau[plateau.length-1][plateau[0].length/2].getPion())!=null)//y'a un pion sur celle du bas milieu
		{
			return plateau[0][plateau[0].length/2];//alors on retourne celle du haut milieu
		}else{
			return plateau[plateau.length-1][plateau[0].length/2];
		}
		
	}
	
	/**
	 * Bouge un pion vers une case
	 * @author Arnaud.P
	 * @param joueur
	 * @param casesDest (de destination)
	 */
	public int movePiece(Player joueur, Box casesDest){///
		Box casesJoueur = joueur.getPiece().getBox();
		Box[] where=whereMovePiece(casesJoueur);
		Piece pion=joueur.getPiece();
		Box oldCases=pion.getBox();//case sur laquelle se trouve le joueur actuellement

		for(int i=0;i<where.length;i++){
			if(casesDest==where[i]){//si la case sur laquelle on veut bouger est dans la liste
				pion.setCases(casesDest);
				oldCases.setPion(null);
				casesDest.setPion(joueur.getPiece());
				return OK;
			}
		}
		return Impossible;
		
	}

	/**
	 * Affiche la grille de jeu dans la console
	 * @author Arnaud.P
	 */
	public void affiche(){
		for (int i=0;i<plateau.length;i++){
			
			for (int j = 0; j < plateau[0].length; j++) {
				Box cases=plateau[i][j];
				if (cases.getPion()!=null)
					System.out.print(1);
				else if(cases.getMur_d()!=null || cases.getMur_b()!=null || cases.getMur_g()!=null || cases.getMur_h()!=null)
					System.out.print(7);
				else
					System.out.print(0);
			}
			System.out.println("");
		}
	}
	
	/**
	 * @author Arnaud.P
	 * @param listCases
	 * @return orientation du mur
	 */
	private char murOrientation(Box[][] listCases){
		if (listCases[0][0].getI()==listCases[0][1].getI())
			return GameBoard.VERTICAL;//vertical
		else
			return GameBoard.HORIZONTAL;
	}
	
	/**
	 * 
	 * @author Arnaud.P
	 * @param mur
	 * @param orientation du mur
	 * @return null si l'ordre des cases dans mur n'est pas correct si pas les cases bien ordonnées
	 */
	private Box[][] tcheckAlign(Box[][] mur,char orientation){
		if(orientation==VERTICAL){
			if(mur[0][0].getI()==mur[0][1].getI() && mur[0][0].getJ()+1==mur[0][1].getJ() && mur[1][0].getI()==mur[0][0].getI()+1 && 
					mur[1][0].getI()==mur[1][1].getI() && mur[1][0].getJ()+1==mur[1][1].getJ()){
				return mur;
			}
			else if(mur[0][0].getI()==mur[0][1].getI() && mur[0][0].getJ()+1==mur[0][1].getJ() && mur[1][0].getI()+1==mur[0][0].getI() && 
					mur[1][0].getI()==mur[1][1].getI() && mur[1][0].getJ()+1==mur[1][1].getJ()){
				Box[][]murReturn={mur[1],mur[0]};
				return murReturn;
				
			}else{
				return null;
			}
		}else{
			if(mur[0][0].getJ()==mur[0][1].getJ() && mur[1][0].getJ()==mur[1][1].getJ() && mur[1][0].getJ()==mur[0][0].getJ()+1 &&
					mur[0][0].getI()+1==mur[0][1].getI() && mur[0][0].getI()==mur[1][0].getI() && mur[1][0].getI()+1==mur[1][1].getI()){
				return mur;
			}
			else if(mur[0][0].getJ()==mur[0][1].getJ() && mur[1][0].getJ()==mur[1][1].getJ() && mur[1][0].getJ()+1==mur[0][0].getJ() && 
						mur[1][0].getI()==mur[0][0].getI() && mur[1][1].getI()==mur[0][1].getI() && mur[1][0].getI()+1==mur[1][1].getI()){
				Box[][]murReturn={mur[1],mur[0]};
				return murReturn;
			}else{
				return null;
			}
		}
	}
	
	/**
	 * 
	 * @param listCases
	 * @return true si on peut placer un mur, false si non
	 */
	private boolean canPut_wall(Box[][] listCases){
		
		char orientation =murOrientation(listCases);
		listCases=tcheckAlign(listCases,orientation);
		if(listCases==null){//si les cases ne sont pas dans un bon ordre ou ne sont pas l'une à coté de l'autres
			return false;
		}
		boolean statut=false;
		
		if(orientation==GameBoard.HORIZONTAL){
			for(int i=0;i<listCases.length;i++){
				if(listCases[i][0].getMur_b()==null ){
					statut =true;
				}
				else{
					return false;// le statut est faux on le retourne direct
				}
			}
			for (int i=0;i<listCases.length-1;i++){
				if ((listCases[i][0].getMur_d() != null) && (listCases[i][1].getMur_d() != null)){//pour chaque paire verticale de case, on regarde s'il y a 
					//un mur a droite
					if(listCases[i][0].getMur_d() == listCases[i][1].getMur_d()){//on regarde si c'est le même mur
						return false;
					}
					else{
						statut =true;
					}
				}
				else{
					statut=true;
				}
			}
		}
		else if (orientation==GameBoard.VERTICAL){
			for (int i=0;i<listCases.length;i++){
				if (listCases[i][0].getMur_d()==null){//y'a pas de mur
					statut=true;
				}
				else{
					return false;
				}
			}
			for (int i=0;i<listCases.length-1;i++ ){
				if ((listCases[i][0].getMur_b() != null) && (listCases[i][1].getMur_b() != null)){//y'a un possible un mur qui croise faut tcheck si c'est un mur ou deux !=
					if (listCases[i][0].getMur_b() == listCases[i][1].getMur_b()){
						return false;
					}
					else{
						statut=true;
					}
				}
				else{
					statut=true;
				}
			}
		}
		return statut;
	}
	
	/**
	 * Retire un mur qui a été posé
	 * @author Arnaud.P
	 * @param listCases
	 * @param joueur
	 */
	private void removeWall(Box[][] listCases,Player joueur){
		joueur.setNombreMur(joueur.getNombreMur()+1);
		if(murOrientation(listCases)==GameBoard.VERTICAL){// si on a un mur vertical
			int j=0;
			for(int i=0;i<listCases.length;i++){//on le place
				listCases[i][j].setMur_d(null);
				listCases[i][j+1].setMur_g(null);
			}
		}
		else{// si pas (on a un mur horizontal)
			int j=0;
			for(int i=0;i<listCases.length;i++){//on le place
				listCases[i][j].setMur_b(null);
				listCases[i][j+1].setMur_h(null);
			}
		}
	}
	
	/**
	 * Place un mur entre 4 cases
	 * @param joueur
	 * @param listCases
	 * @return true si a placé le mur, false si non
	 */
	public int putWall(Player joueur, Box[][] listCases, Player[] listeJoueurs){//

		Finder finder=new Finder(this);
		int statut;
		if ((canPut_wall(listCases)) && (joueur.getNombreMur()>0)){// si on peut mettre un mur et qu'il y a encore des murs 
			Wall mur=new Wall(joueur,murOrientation(listCases));// on crée le mur
			joueur.setNombreMur(joueur.getNombreMur()-1);// on retire un mur au joueur
			
			if(murOrientation(listCases)==GameBoard.VERTICAL){// si on a un mur vertical
				for(int i=0;i<listCases.length;i++){//on le place
					int j=0;
					listCases[i][j].setMur_d(mur);
					listCases[i][j+1].setMur_g(mur);
				}
				statut=OK;
			}
			else{// si pas (on a un mur horizontal)
				for(int i=0;i<listCases.length;i++){//on le place
					int j=0;
					listCases[i][j].setMur_b(mur);
					listCases[i][j+1].setMur_h(mur);
				}
				statut=OK;
			}
			for (int i = 0; i < listeJoueurs.length; i++) {
				//appelr pathfinder pour chaque joueur
				//des qu'il retourne null, supprimer le mur et indiquer que cela va bloquer un joueur
				Box[] chemin=finder.callFindPath(listeJoueurs[i]);
				if(chemin==null){
					removeWall(listCases, joueur);
					return MurBloque;
				}
				else{
					statut=OK;
				}
			}
		}
		else{// si on peut pas mettre de mur
			if(joueur.getNombreMur()<=0){
				return AucunMurDispo;
			}
			else{
				return Impossible;
			}
		}
		return statut;
	}
	
	/**
	 * 
	 * @author Arnaud.P
	 * @return le plateau
	 */
	public Box[][] getPlateau() {
		return plateau;
	}

}
