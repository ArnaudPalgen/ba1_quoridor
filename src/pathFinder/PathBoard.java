package pathFinder;

import game.Box;

/**
 * Réplique du plateau du gameBoard mais avec des pathCases
 * @author Nono21
 *
 */
public class PathBoard {
	PathCase[][] tableauRecherche;

	public PathBoard(Box[][] plateau) {
		tableauRecherche=new PathCase [plateau.length][plateau[0].length];
		
		for(int i=0;i<tableauRecherche.length;i++){
			for(int j=0;j<tableauRecherche[0].length;j++){
				
				tableauRecherche[i][j]=new PathCase(i, j, false);//false correspond à l'attribut passe
			}
		}
	}
	
	/**
	 * Marque les cases à la ligne i comme cases de destination
	 * @param i
	 * @author Arnaud.P
	 */
	public void defDestination(int i){
		//TODO assert correct ??
		assert(i==0 || i==8):
			"erreur avec i (PathBoard l39)";
		for(int j=0;j<tableauRecherche.length;j++){
			tableauRecherche[i][j].setDestination(true);
		}
	}
	/**
	 * 
	 * @author Arnaud.P
	 * @return le tableau de Recherche
	 */
	public PathCase[][] getTableauRecherche() {
		return tableauRecherche;
	}

}
