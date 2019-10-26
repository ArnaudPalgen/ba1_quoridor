package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class ButtonMenu extends JButton
{
	private String name;
    private Image img;
    private Window frame;

    
    public ButtonMenu(String str, Window frame)
  {
    super(str);
    this.name = str;
    this.frame=frame;
    
    try 
    {
        img = ImageIO.read(new File("pictures/case2.png"));
        //img = ImageIO.read(ResourceLoader.load("case2.png"));

    }
    catch (IOException e)
      {
    	JOptionPane.showMessageDialog(this,"Impossible de charger les images des boutons, impossible de lancer le jeu", "Erreur de lecture de fichier",JOptionPane.DEFAULT_OPTION);
    	frame.dispose();
    	System.exit(0);
      }
    
    this.setPreferredSize(new Dimension(img.getWidth(this),img.getHeight(this)));
    
  }
    
    public void paintComponent(Graphics g)
    {
    	super.paintComponent(g);
    	g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
    	g.setColor(Color.darkGray); //pour le string
    	if (name.length()<=15 && name.length()>=13)
    	{
    		g.drawString(this.name, this.getWidth() / 8, (this.getHeight() / 2)+5);
    	}
    	
    	else
    	{
    		g.drawString(this.name, this.getWidth() / 2- (this.getWidth()/4), (this.getHeight() / 2)+5);
    	}
    	
    }    
}
 
