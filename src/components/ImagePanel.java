package components;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	


	    private BufferedImage image;

	    public ImagePanel(String imagePath) {
	       try {                
	          image = ImageIO.read(new File(imagePath));
	       } catch (IOException ex) {
	            System.out.println("Couldn't find Image");
	       }
	    }

	    @Override
	    protected void paintComponent(Graphics g) {
	    	 super.paintComponent(g);

	        g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters   
	       
	    }

	}

