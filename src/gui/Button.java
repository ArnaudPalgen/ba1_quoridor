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

public class Button extends JButton
{
	private String name;
    private Image img;

    
    public Button(String str)
  {
    super(str);
    this.name = str;
    
    try 
    {
        img = ImageIO.read(ResourceLoader.load("case2.png"));
        
    }
    catch (IOException e)
      {
        e.printStackTrace();
      }
    
    this.setPreferredSize(new Dimension(img.getWidth(this),img.getHeight(this)));
    
  }
    
    public void paintComponent(Graphics g)
    {
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
 
