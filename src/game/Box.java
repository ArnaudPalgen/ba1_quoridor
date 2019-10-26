package game;

import java.io.Serializable;

/**
 * Case du plateau de jeu logique
 * 
 * @author Arnaud.P
 *
 */
public class Box implements Serializable {
	private Wall murH, murB, murG, murD;// références vers le mur se trouvant en
										// haut, en bas, ... de la case null
										// s'il n'y en a pas
	private Piece pion;// pion sur la case null s'il n'y en a pas
	private boolean canMur_h, canMur_b, canMur_g,canMur_d;
		/*
		 * utilisée pour savoir si la case est a une extrémité
		 * par ex: si la case est tout en bas, canMur_b sera
		 * false
		 */
	private int i, j;// coordonnées de la case

	public Box(Piece isPion, boolean canMur_h, boolean canMur_b, boolean canMur_g, boolean canMur_d, int i, int j) {
		this.murB = null;
		this.murD = null;
		this.murG = null;
		this.murH = null;
		this.pion = isPion;
		this.canMur_h = canMur_h;
		this.canMur_b = canMur_b;
		this.canMur_g = canMur_g;
		this.canMur_d = canMur_d;
		this.i = i;
		this.j = j;
	}

	/**
	 * @author Arnaud.P
	 * @return mur à gauche de la case
	 */
	public Wall getMur_h() {
		return murH;
	}

	/**
	 * @author Arnaud.P
	 * @param Mur_h mur en haut de la case
	 */
	public void setMur_h(Wall Mur_h) {
		this.murH = Mur_h;
	}

	/**
	 * @author Arnaud.P
	 * @return mur en bas de la case
	 */
	public Wall getMur_b() {
		return murB;
	}

	/**
	 * @author Arnaud.P
	 * @param Mur_b mur en bas de la case
	 */
	public void setMur_b(Wall Mur_b) {
		this.murB = Mur_b;
	}

	/**
	 * @author Arnaud.P
	 * @return mur à gauche de la case
	 */
	public Wall getMur_g() {
		return murG;
	}

	/**
	 * @author Arnaud.P
	 * @param Mur_g mur à gauche de la case
	 */
	public void setMur_g(Wall Mur_g) {
		this.murG = Mur_g;
	}

	/**
	 * @author Arnaud.P
	 * @return mur à droite de la case
	 */
	public Wall getMur_d() {
		return murD;
	}

	/**
	 * @author Arnaud.P
	 * @param isMur_d
	 */
	public void setMur_d(Wall isMur_d) {
		this.murD = isMur_d;
	}

	/**
	 * @author Arnaud.P
	 * @return le pion sur la case
	 */
	public Piece getPion() {
		return pion;
	}

	/**
	 * @author Arnaud.P
	 * @param pion
	 */
	public void setPion(Piece pion) {
		this.pion = pion;
	}

	/**
	 * @author Arnaud.P
	 * @return canMur_h
	 */
	public boolean isCanMur_h() {
		return canMur_h;
	}

	/**
	 * @author Arnaud.P
	 * @return canMur_b
	 */
	public boolean isCanMur_b() {
		return canMur_b;
	}

	/**
	 * @author Arnaud.P
	 * @return canMur_g
	 */
	public boolean isCanMur_g() {
		return canMur_g;
	}

	/**
	 * 
	 * @author Arnaud.P
	 * @return canMur_d
	 */
	public boolean isCanMur_d() {
		return canMur_d;
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
	 * @param i
	 */
	public void setI(int i) {
		this.i = i;
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
	 * @param j
	 */
	public void setJ(int j) {
		this.j = j;
	}

	@Override
	public String toString() {
		return "Case: (" + this.i + "," + this.j + ")";
	}

	@Override
	public boolean equals(Object obj) {
		Box myBox = (Box) obj;
		if (this.i == myBox.getI() && this.j == myBox.getJ()) {
			return true;
		} else {
			return false;
		}
	}

}
