package gui;


import game.Box;

public class Intersection extends AContents {
	/**Definis les intersections grahiquement
	 * @author Arnaud.P
	 * 
	 */
	private char orientation;
	/**Construit les intersections
	 * @param i
	 * @param j
	 */
	public Intersection(int i, int j, Panel pan) {
		super(i, j);

	}

	@Override
	public void setBusy(Panel pan) {
		AContents[][] plateau=pan.getImgPlateau();
		
			ImgBox caseHGauche=(ImgBox)plateau[i-1][j-1];// case en haut a gauche de l'intersection
			Box logiqueHGauche=ImgBoard.gameTableau[caseHGauche.getILogique()][caseHGauche.getJLogique()];//case logique correspondante
			
			ImgBox caseBDroite=(ImgBox)plateau[i+1][j+1];//case en bas a droite de l'intersection
			Box logiqueBDroite=ImgBoard.gameTableau[caseBDroite.getILogique()][caseBDroite.getJLogique()];//case logique correspondante

			
			if(plateau[i][j-1].busy && plateau[i][j+1].busy && logiqueHGauche.getMur_b()==logiqueBDroite.getMur_h()){// si les deux sont true ET QUE C EST LE MEME MUR alors inter est horizontal
				
				this.Height=5;
				this.Width=11;
				this.busy=true;
				this.image=interH;
				this.orientation='h';
			}
			
			else if(plateau[i+1][j].busy && plateau[i-1][j].busy && logiqueHGauche.getMur_d()==logiqueBDroite.getMur_g()){// si les deux sont true alors inter est vertical

					//System.out.println("je suis un inter verticale !!");
					this.Height=11;//hauteur
					this.Width=5;//largeur
					this.busy=true;
					this.image=interV;
					this.orientation='v';	
			}
			else{//vide 
				//System.out.println("je suis un inter vide");
				this.Height=5;
				this.Width=5;
				this.busy=false;
				this.image=inter_transparant;
				this.orientation='o';//pas d'orientation (carré)
			}
		
		defMarge(plateau);

	}

	/**definis si il faut mettre une marge pas
	 * @author Arnaud.P
	 * @param tableau
	 */
	public void defMarge(AContents[][] tableau){
		if(orientation=='o' || orientation=='h'){
			this.marge=true;//on doit en mettre une 
		}
		else{
			if(this.busy){
				this.marge=false;
			}
			else{
				this.marge=true;
			}
		}
	}

	@Override
	public String toString() {
		return " INTE"+busy+" ";
	}
	
	@Override
	public int getSizeX(Panel pan){
		if(this.orientation=='v' || this.orientation=='o'){
			return (this.Width+pan.getMarge());
		}
		else{//mur honrizontal
			return this.Width;
		}
	}
	@Override
	public int getSizeY(Panel pan){
		if(this.orientation=='o' || this.orientation=='h'){
			return (this.Height+pan.getMarge());
		}
		else{
			return this.Height;
		}
	}
	/**
	 * 
	 * @return orientation
	 */
	public char getOrientation() {
		return orientation;
	}

}
