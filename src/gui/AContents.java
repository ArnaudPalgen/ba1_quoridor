package gui;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public abstract class AContents {
	protected int i,j;// coordonnées de l'element
	protected Image image;// image qui sera dessinee pour representer l'element
	protected boolean busy;// boolean qui est true si l'element est occupe 
	protected int Width,Height;// tailles de l'element
	protected boolean marge;// bollean pour savoir s'il faut tracer l'element avec une marge(true) ou sans (false)
	
	protected static Image murH_transparant,murV_transparant, murH_rempli,murV_rempli,Case,pion,inter_transparant,interH,interV,
	interRempli;//interRempli utilisé pour le test
	
	/**
	 * @author Arnaud.P
	 * @param i ligne
	 * @param j colonne
	 */
	public AContents(int i,int j) {
		this.i=i;
		this.j=j;
				
	}
	
	/**
	 * @author Arnaud.P
	 * definis la valeur de busy pour l'element ansi que la valeur de image
	 * @param pan panneau
	 */
	public abstract void setBusy(Panel pan);

	/**
	 * @author Arnaud.P
	 * @return image
	 */
	public Image getImage() {
		return image;
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
	
	@Override
	public abstract String toString();
	
	/**
	 * @author Arnaud.P
	 * @param pan
	 * @return retourne la largeur rélle de l'element
	 */
	public abstract int getSizeX(Panel pan);
	
	/**
	 * @author Arnaud.P
	 * @param pan
	 * @return retourne la hauteur réelle de l'element
	 */
	public abstract int getSizeY(Panel pan);
	/**
	 * @author Arnaud.P
	 * @return la largeur
	 */
	public int getWidth(){//largeur
		return this.Width;
	}
	/**
	 * @author Arnaud.P
	 * @return la hauteur
	 */
	public int getHeight(){//hauteur
		return this.Height;
	}
	
	/**Charge les images
	 * @author Arnaud.P
	 */
	public static void loadImg(){
		/**
		 * case http://cdn.instructables.com/FJU/Q6DX/GLWQVY1K/FJUQ6DXGLWQVY1K.MEDIUM.jpg
		 */
		
		try{
			murH_transparant=ImageIO.read(new File("pictures/wallTransparentH.png"));
			murV_transparant=ImageIO.read(new File("pictures/wallTransparentV.png"));
			murH_rempli=ImageIO.read(new File("pictures/wallH.png"));
			murV_rempli=ImageIO.read(new File("pictures/wallV.png"));
			Case=ImageIO.read(new File("pictures/case.png"));
			inter_transparant=ImageIO.read(new File("pictures/interTransparent.png"));
			interH=ImageIO.read(new File("pictures/interH.png"));
			interV=ImageIO.read(new File("pictures/interV.png"));
			interRempli=ImageIO.read(new File("pictures/interRempli.png"));
			
		}
		catch (IOException e) {
			JOptionPane.showMessageDialog(null,"Impossible de charger une ou plusieurs images, il se peut que le jeu rencontre des probl�mes, relancer le jeu", "Erreur de lecture de fichier",JOptionPane.ERROR_MESSAGE);		
		} 
	}
	
	/**
	 * @author Arnaud.P
	 * @return marge
	 */

	public boolean isMarge() {
		return marge;
	}
}
