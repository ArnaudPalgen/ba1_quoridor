package gui;

public class ImgWall extends AContents {
	/**Represente les murs graphiquement
	 * @author Arnaud.P
	 */
	private char orientation;//'v' pour vertical et 'h' pour horizontal
	/**Construit les murs graphiquement
	 * @author Arnaud.P
	 * @param i
	 * @param j
	 */
	public ImgWall(int i,int j) {
		super(i,j);
		if(this.j%2==0){
			this.orientation='v';
			this.Height=50;
			this.Width=5;
		}
		else{
			this.orientation='h';
			this.Height=5;//hauteur
			this.Width=50;///////
		}
	}
	
	@Override
	public void setBusy(Panel pan){
		AContents[][] plateau=pan.getImgPlateau();
			if(this.orientation=='h' && (this.i-1)>=0 && plateau[this.i-1][this.j]!=null){
				//System.out.println("ImgWall l33");
				AContents cases=plateau[this.i-1][this.j];//on va chercher la case du dessus 
				ImgBox newCase=(ImgBox)cases;//convertis Contents to ImgBox
				if(ImgBoard.gameTableau[newCase.getILogique()][newCase.getJLogique()].getMur_b() !=null){//on regarde si la case logique
					//correspondante à un mur en bas
					//System.out.println("ImgWall l37");
					this.busy=true;
					this.image=murH_rempli;
				}
				else{
					this.image=murH_transparant;
					this.busy=false;
				}
			}
			else if(this.orientation=='v' && (this.j-1)>=0 && plateau[this.i][this.j-1]!=null){

				AContents cases=plateau[this.i][this.j-1];//on va chercher la case de gauche
				ImgBox newCase=(ImgBox)cases;//convertis Contents to ImgBox
				if(ImgBoard.gameTableau[newCase.getILogique()][newCase.getJLogique()].getMur_d() !=null){//on regarde si la case logique
					//correspondate à un mur a droite
					this.busy=true;
					this.image=murV_rempli;
					/*if(this.i==9 && this.j==12){
						//System.out.println("hello");
						System.out.println(ImgBoard.gameTableau[newCase.getILogique()][newCase.getJLogique()].isMur_d());
					}*/
				}
				else{
					this.image=murV_transparant;
					this.busy=false;
				}
			}
			defMarge(plateau);
		 

		//System.out.println(i+" "+j+" busy: "+busy);

	}
	@Override
	public int getSizeX(Panel pan){
		AContents[][] plateau=pan.getImgPlateau();
		if (this.orientation=='h'){//HORIZONTALE
			if(this.busy && (j+1)<plateau[0].length-1 &&plateau[i][j+1].busy && (j+2)<plateau[0].length-1 && plateau[i][j+2].busy){
				Intersection inter=(Intersection)plateau[i][j+1];
				if( inter.getOrientation()=='v'  ){
					return this.Width+pan.getMarge();
				}
				return this.Width;
			}
			else{
				return (Width+pan.getMarge());
			}
		}
		else{//VERTICALE
			return (this.Width+pan.getMarge());
		}
	}
	
	@Override
	public int getSizeY(Panel pan){

		return (this.Height+pan.getMarge());
	}
	
	@Override
	public String toString(){
		return " Wall"+this.busy+" ";
	}
	/**
	 * definis si il faut mettre une marge ou pas
	 * @param plateau
	 */
	public void defMarge(AContents[][] plateau) {
		if(orientation=='h'){
			this.marge=true;
		}
		else{
			if(this.busy && plateau[i-1][j] != null && plateau[i-1][j].busy){
				this.marge=true;//false
			}
			else{
				this.marge=true;
			}
		}
	}
	

}
