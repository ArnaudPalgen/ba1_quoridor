package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;

/**Permet d'avoir un bouton personnalisé
 * @author Fabio
 *
 */

public class ButtonHome extends JButton
{
	 private Image img;
	 /**Construit le bouton personnalisé
	  * @author Fabio
	  *  
	  */

	    
	 public ButtonHome()
	 {	   
		 try 
		 {
	        img = ImageIO.read(new File("pictures/home.png"));
	        // home: http://res.freestockphotos.biz/pictures/14/14484-illustration-of-a-house-pv.png
	        
		 }
		 catch (IOException e)
	      {
	        e.printStackTrace();
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
