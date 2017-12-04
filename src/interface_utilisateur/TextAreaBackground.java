package interface_utilisateur;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JTextArea;

public class TextAreaBackground extends JTextArea {
	
	private Image img;
	private int imgSize;
	
	public TextAreaBackground(String imagePath){
		super("\nVeuillez parametrer le Système expert et lancer l'analyse avec le bouton\n\"Diagnostiquer\"\n");
		setImage(imagePath);
		imgSize = 128;
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (img != null){
	        Graphics2D g2d = (Graphics2D) g;
	        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 0.5);
	        g2d.setComposite(ac);
	        g2d.drawImage(img, getWidth()-imgSize, getHeight()-imgSize, imgSize, imgSize, null);
        }
    }
	
	public void setImage(String imagePath){
		try{
            //img = ImageIO.read(new File("icons/"+imagePath));
			URL url = getClass().getResource("/resources/icons/"+imagePath);
			
			img = ImageIO.read(url);
        } catch(IOException e) {
        	img = null;
            System.out.println(e.toString());
        }
	}

}
