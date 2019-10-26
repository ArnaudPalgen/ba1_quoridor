package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class ButtonInfo extends JButton
{
	/**Construit le bouton d'info
	 * @author Fabio
	 */
	 private Image img;

	    
	 public ButtonInfo()
	 {	   
		 try 
		 {
	        img = ImageIO.read(new File("pictures/info.png"));
	        //info: https://upload.wikimedia.org/wikipedia/en/5/54/Information.png
	        
		 }
		 catch (IOException e)
	      {
			 JOptionPane.showMessageDialog(this,"Impossible de charger l'image du bouton , il se peut que le jeu rencontre des problèmes, relancer le jeu", "Erreur de lecture de fichier",JOptionPane.ERROR_MESSAGE);		
	      }
	    
	    this.setPreferredSize(new Dimension(img.getWidth(this),img.getHeight(this)));
	    
	  }
	 
	 @Override
	 public void paintComponent(Graphics g)
	    {
		 	super.paintComponent(g);
	    	g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
	    	
	    }
}