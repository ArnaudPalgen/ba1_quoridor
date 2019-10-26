package gui;

import game.Box;
import game.Game;

public class ImgBoard {
	
	/**Tableau utilisé pour la représentation graphique
	 * @author Arnaud.P
	 */
	public static Box[][] gameTableau;//tableau logique
	private AContents[][] imgBoard;
	private static int gamTab_lengthI, gamTab_lengthJ;
	private static int sizeX=0,sizeY=0;
	
	/**
	 * construit le tableau graphique
	 * @param pan
	 * @param jeu
	 */
	public ImgBoard(Panel pan, Game jeu) {
		gameTableau=jeu.getTableau();
		gamTab_lengthI=gameTableau.length;
		gamTab_lengthJ=gameTableau[0].length;
		
		AContents.loadImg();// va charger les images
		int ligne=(gamTab_lengthI*2)+1;//nbr de ligne du tableau de l'interface graphique
		int colonne=(gamTab_lengthJ*2)+1;
		
		imgBoard=new AContents[ligne][colonne];		
		
		int I_indice=1;
		for(int i=0;i<gamTab_lengthI;i++){//boucle pour placer les cases
			int J_Indice=1;
			
			for(int j=0;j<gamTab_lengthJ;j++){
				if((i+I_indice)<ligne && (j+J_Indice)<colonne){//on vérifie qu'on ne sort pas tu tableau
					imgBoard[i+I_indice][j+J_Indice]=new ImgBox(i+I_indice,j+J_Indice,i,j, pan);// on place les cases
					sizeX+=50; sizeY+=50;
					
				}
				
				if((i+I_indice-1)<ligne &&(j+J_Indice)<colonne && gameTableau[i][j].isCanMur_h()==true && imgBoard[i+I_indice-1]
						[j+J_Indice]==null){/*si ma case logique n'est pas
				tout en haut alors on va placer un espace ImgWall en haut idem pour les autres mais bas, gauche, droite */
					imgBoard[i+I_indice-1][j+J_Indice]=new ImgWall(i+I_indice-1,j+J_Indice);
				}
				if((i+I_indice+1)<ligne && (j+J_Indice)<colonne && gameTableau[i][j].isCanMur_b()==true && imgBoard[i+I_indice+1]
						[j+J_Indice]==null){
					imgBoard[i+I_indice+1][j+J_Indice]=new ImgWall(i+I_indice+1,j+J_Indice);//bas
				}
				if((i+I_indice)<ligne && (j+J_Indice-1)<colonne && gameTableau[i][j].isCanMur_g()==true && imgBoard[i+I_indice]
						[j+J_Indice-1]==null){
					imgBoard[i+I_indice][j+J_Indice-1]=new ImgWall(i+I_indice,j+J_Indice-1);//gauche 
				}
				if((i+I_indice)<ligne && (j+J_Indice+1)<colonne && gameTableau[i][j].isCanMur_d()==true && imgBoard[i+I_indice]
						[j+J_Indice+1]==null){
					imgBoard[i+I_indice][j+J_Indice+1]=new ImgWall(i+I_indice,j+J_Indice+1);//droite
				}
				
				J_Indice++;
			}
			I_indice++;
		}
		for (int i = 0; i < ligne; i++) {
			for (int j = 0; j < colonne; j++) {
				if(((i%2)==0) && ((j%2)==0) && i!=0 && i!=18 && j!=0 && j!=18){
					imgBoard[i][j]=new Intersection(i, j, pan);
				}
			}
		}
				
	}
	


	/**
	 * @return imgBoard le tableau
	 */
	public AContents[][] getImgBoard() {
		return imgBoard;
	}
}
