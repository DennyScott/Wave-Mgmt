package components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import aurelienribon.slidinglayout.demo.ThePanel;

public class TopRoundPanel extends ThePanel {
	

		
		 /** Stroke size. it is recommended to set it to 1 for better view */
	    protected int strokeSize = 1;
	    /** Color of shadow */
	    protected Color shadowColor = Color.black;
	    /** Sets if it drops shadow */
	    protected boolean shady = true;
	    /** Sets if it has an High Quality view */
	    protected boolean highQuality = true;
	    /** Double values for Horizontal and Vertical radius of corner arcs */
	    protected Dimension arcs = new Dimension(20, 20);
	    /** Distance between shadow border and opaque panel border */
	    protected int shadowGap = 5;
	    /** The offset of shadow.  */
	    protected int shadowOffset = 4;
	    /** The transparency value of shadow. ( 0 - 255) */
	    protected int shadowAlpha = 150;
	    
	    private Color color1 = new Color(0x57637B);
		
		 @Override
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        Graphics2D g2d = (Graphics2D) g;
		        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		        int w = getWidth();
		        int h = getHeight();       
		        
		        int shadowGap = this.shadowGap;
		        Color shadowColorA = new Color(shadowColor.getRed(), 
			shadowColor.getGreen(), shadowColor.getBlue(), shadowAlpha);
		        Graphics2D graphics = (Graphics2D) g;

		        //Sets antialiasing if HQ.
		        if (highQuality) {
		            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
					RenderingHints.VALUE_ANTIALIAS_ON);
		        }

		        //Draws shadow borders if any.
		        if (shady) {
		            graphics.setColor(shadowColorA);
		            graphics.fillRoundRect(
		                    shadowOffset,// X position
		                    shadowOffset,// Y position
		                    w - strokeSize - shadowOffset, // width
		                    h - strokeSize - shadowOffset, // height
		                    arcs.width, arcs.height);// arc Dimension
		        } else {
		            shadowGap = 1;
		        }

		        //Draws the rounded opaque panel with borders.
		        graphics.setColor(getBackground());
		        graphics.fillRoundRect(0, 0, w - shadowGap, 
				h - shadowGap, arcs.width, arcs.height);
		        graphics.setColor(getForeground());
		        graphics.setStroke(new BasicStroke(strokeSize));
		        graphics.drawRoundRect(0, 0, w - shadowGap, 
				h - shadowGap, arcs.width, arcs.height);

		        //Sets strokes to default, is better.
		        graphics.setStroke(new BasicStroke());
		        
		        
		        
		        

		        GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color1);
		        g2d.setPaint(gp);
		        g2d.fillRoundRect(0, 0, w-shadowGap, h - shadowGap, arcs.width, arcs.height);

		    }
		 
		 public void setColor(int red, int green, int blue, int alpha){
			 color1 = new Color(red,green,blue,alpha);
		 }

		    public static void main(String[] args) {
		        SwingUtilities.invokeLater(new Runnable() {
		            @Override
		            public void run() {
		                JFrame frame = new JFrame();
		                TopRoundPanel panel = new TopRoundPanel();
		                frame.add(panel);
		                frame.setSize(200, 200);
		                frame.setLocationRelativeTo(null);
		                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		                frame.setVisible(true);
		            }
		        });
		    }




	}



