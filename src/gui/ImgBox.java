package gui;

public class ImgBox extends AContents {
	/**Represente les cases graphiquement
	 * @author Arnaud.P
	 */
	
	private int ILogique,JLogique;//position i et j de la case que represente l'instance
	
	
	/**Construit les cases graphiques
	 * @author Arnaud.P
	 * @param i
	 * @param j
	 * @param ILogique
	 * @param JLogique
	 * @param pan
	 */
	public ImgBox(int i,int j,int ILogique, int JLogique, Panel pan) {
			super(i,j);
			this.ILogique=ILogique;
			this.JLogique=JLogique;
			this.Height=50;
			this.Width=50;
			this.marge=true;
	}
	
	@Override
	public void setBusy(Panel pan){
		this.image=Case;
		if(ImgBoard.gameTableau[ILogique][JLogique].getPion()!=null){//si y'a un pion sur la caseLogique correspondante
			
			this.busy=true;
		}
		else{
			this.busy=false;
		}
	}
	
	@Override
	public int getSizeX(Panel pan){
		return (Width+pan.getMarge());
	}
	@Override
	public int getSizeY(Panel pan){
		return (Height+pan.getMarge());
	}
	@Override
	public String toString(){
		return " case"+busy+" ";
	}
	
	/**
	 * @author Arnaud.P
	 * @return iLogique
	 */
	public int getILogique() {
		return ILogique;
	}
	
	/**
	 * @author Arnaud.P
	 * @return JLogique
	 */
	public int getJLogique() {
		return JLogique;
	}
	

}
