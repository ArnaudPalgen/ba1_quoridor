package pathFinder;

/**
 * Cases du pathBoard
 * @author Nono21
 *
 */
public class PathCase {
	private int i,j;//coordnnées d'une case
	private boolean passe, destination;//passe est true si le finder est deja passe sur cette case
	//destination est true si la case est une case de destination
	private PathCase parent;//case qui à permis d'arriver à cette case
	
	/**
	 * @author Arnaud.P
	 * @param i
	 * @param j
	 * @param passe
	 */
	public PathCase(int i,int j,boolean passe) {
		this.i=i;
		this.j=j;
		this.parent=null;
		this.passe=passe;
		this.destination=false;
	}
	
	/**
	 * @author Arnaud.P
	 * @return passe
	 */
	public boolean isPasse() {
		return passe;
	}
	
	/**
	 * @author Arnaud.P
	 * @param passe
	 */
	public void setPasse(boolean passe) {
		this.passe = passe;
	}
	
	/**
	 * @author Arnaud.P
	 * @return parent
	 */
	public PathCase getParent() {
		return parent;
	}
	
	/**
	 * @author Arnaud.P
	 * @param parent
	 */
	public void setParent(PathCase parent) {
		this.parent = parent;
	}
	/**
	 * @author Arnaud.P
	 * @return i
	 */
	public int getI() {
		return i;
	}
	
	/**
	 * @author Arnaud.P
	 * @return j
	 */
	public int getJ() {
		return j;
	}
	
	/**
	 * @author Arnaud.P
	 * @return destination
	 */
	public boolean isDestination() {
		return destination;
	}
	
	/**
	 * @author Arnaud.P
	 * @param destination
	 */
	public void setDestination(boolean destination) {
		this.destination = destination;
	}
	
	@Override
	public String toString(){
		return "pathCase "+destination+" "+i+" , "+j;
	}

}
