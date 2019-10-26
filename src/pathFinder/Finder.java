package pathFinder;

import game.Box;
import game.GameBoard;
import players.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;


/**
 * Classe utilisée pour chercher un chemin
 * @author Arnaud.P
 *
 */
public class Finder implements Serializable {
	private PathBoard pathBoard; // un pathBoard
	private Box[][] plateauGame;// le plateau du GameBoard
	private PathCase[][] plateauPath;// le plateau du PatBoard
	private GameBoard gameBoard;// un gameBoard

	private static PathCase caseDepart;// la case sur laquelle se trouve le pion
	// du joueur

	/**
	 * @author Arnaud.P
	 * @param gameBoard
	 */
	public Finder(GameBoard gameBoard) {
		pathBoard = new PathBoard(gameBoard.getPlateau());// créer un nouveau pathBoard
			//réplique du tableau de jeu avec des cases PathCase
		plateauPath = pathBoard.getTableauRecherche();// tableau de recherche
		plateauGame = gameBoard.getPlateau();// plateau dans GameBoard TO DO peut
		this.gameBoard =gameBoard;// le GameBoard

	}

	/**
	 * Il faut utiliser cette méthode pour activer la recherche de chemin cette
	 * méthode est "le chef d'orchestre " du pathFinder
	 * 
	 * @param joueur pour lequel on doit regarder s'il existe un chemin
	 * @return null si aucun chemin n'est trouvé. Si pa retourne le chemin
	 */
	public Box[] callFindPath(Player joueur) {
		int i = -1;
		if (joueur.getNbrJoueur() == 1) {
			i = 0;
		} else if (joueur.getNbrJoueur() == 2) {
			int longueur=plateauPath.length;
			i = longueur-1;
		}
		/**
		 * convention: la case d'arrivée est le dernier élément de la liste 
		 * 				et le premier élément est la case sur laquelle on se trouve
		 */
		
		pathBoard.defDestination(i);// on marque les cases d'indice i comme cases de destination
		Box boxe = joueur.getPiece().getBox();//on recupere la case du joueur
		caseDepart = plateauPath[boxe.getI()][boxe.getJ()];

		ArrayList<PathCase> forCall=new  ArrayList<PathCase>();
		forCall.add(caseDepart);
		ArrayList<PathCase> findRetour=findPath(forCall);//resultat du pathFinder (methode pathFind)

		if (findRetour!=null) {
			findRetour.trimToSize();//supprime les null
			Collections.reverse(findRetour);
			
	
		}
		Box[]retour=toArrayBox(findRetour);
		resetPlateauPath();// on reinitialise le plateau du pathfinder pour qu'il puisse etre reutilise

		return retour;
	}
	
	/**
	 * Convertis une ArrayList de PathCase en tableau de Box
	 * @param findRetour<PathCase> findRetour (à convertir)
	 * @return tableau de Box Box[]
	 */
	private Box[] toArrayBox(ArrayList<PathCase> findRetour) {
		if(findRetour==null){
			return null;
		}
		else{
			ArrayList<Box> listBox=new ArrayList<>(0);
			for (PathCase pathCase : findRetour) {
				listBox.add(plateauGame[pathCase.getI()][pathCase.getJ()]);
			}
			Box[] tabBox=listBox.toArray(new Box[0]);
			return tabBox;
		}
	}
	
	/**
	 * Rénitialise le Pathboard en vue de chercher un nouveau chemin
	 * @author Arnaud.P
	 */
	public void resetPlateauPath() {
		for (int i = 0; i < plateauPath.length; i++) {
			for (int j = 0; j < plateauPath[0].length; j++) {
				plateauPath[i][j].setPasse(false);
				plateauPath[i][j].setParent(null);
				plateauPath[i][j].setDestination(false);
				caseDepart = null;
			}
		}
	}
	
	/**
	 * Cherche un chemin
	 * @author Arnaud.P
	 * @param cases
	 * @return Un chemin, null si aucun chemin n'existe 
	 */
	public ArrayList<PathCase> findPath( ArrayList<PathCase> cases){
		ArrayList<PathCase> retour=new ArrayList<PathCase>();

		for (PathCase maCase :cases ) { /// on regarde si c'est la case de destination
			if(maCase.isDestination()){
				while (maCase!=null) {// si c'est le cas on ajoute tous ses parents à la liste et on 
					//retourne la liste
					retour.add(maCase);
					maCase=maCase.getParent();
				}
				return retour;
			}
		}
		if(cases.size()==0){// il n'y a pas de voisins donc on retourne null
			return null;
		}

		
		for (PathCase box : cases ) {
			box.setPasse(true); 
			PathCase[] voisins=getVoisins(box);
			if(voisins.length>0){
				for (PathCase voisin :voisins ) {
					voisin.setPasse(true);
					voisin.setParent(box);
					retour.add(voisin);
				}

			}
		}
		ArrayList<PathCase> result=findPath(retour);

		return result;
	}

	/**
	 * 
	 * @param maCase PathCase
	 * @return retourne une liste PathCase de voisins de la case cases qui n'ont pas été visitées. 
	 */
	public PathCase[] getVoisins(PathCase maCase) {
		Box casesLogique = plateauGame[maCase.getI()][maCase.getJ()];// on va chercher la case equivalente a cases dans le gameBoard
		Box[] voisinsBoard = gameBoard.whereMovePiece(casesLogique);// liste des voisins d'une case Box 

		ArrayList<PathCase> voisinsPath = new ArrayList<PathCase>(0);// liste qui va contenir les voisins au format PathCase

		for (int i = 0; i < voisinsBoard.length; i++) {// on remplit la liste
			Box casesBox = voisinsBoard[i];
			PathCase casesPath= plateauPath[casesBox.getI()][casesBox.getJ()];
			if(casesPath.isPasse()==false){
				voisinsPath.add(casesPath);
			}
			else{
				continue;
			}
		}
		PathCase[] voisinsPathTab= voisinsPath.toArray(new PathCase[voisinsPath.size()]);

		return voisinsPathTab;
	}

}
