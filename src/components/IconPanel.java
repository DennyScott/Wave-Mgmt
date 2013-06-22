package components;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import data.CompletedSingleton;

import aurelienribon.slidinglayout.demo.TheFrame;

public class IconPanel extends JPanel implements MouseListener{
	
	ImagePanel folder;
	TheFrame frame;
	JLabel words;
	Border blackBorder = BorderFactory.createLineBorder(Color.BLACK);
    Border redBorder = BorderFactory.createLineBorder(Color.WHITE);
    int id;

	
	public IconPanel(String imagePath, String label, int id, TheFrame frame){
		this.id = id;
		this.frame = frame;
		folder = new ImagePanel(imagePath);
		
		addMouseListener(this);
        setBorder(blackBorder);
        setFocusable(true);
		
		
		setLayout(new GridBagLayout());
		
		GridBagConstraints in = new GridBagConstraints();
		in.fill = GridBagConstraints.VERTICAL;
		in.weightx = .3;
		in.anchor = GridBagConstraints.WEST;
		in.gridx = 0;
		in.gridy = 0;
		in.ipady = 10;
		in.ipadx = 10;
		
		add(folder, in);
		
		words = new JLabel(label);
		in.gridx = 1;
		in.weightx = .7;
		in.ipadx = 0;
		add(words,in);
		
		
	}
	
	@Override public void mouseClicked(MouseEvent e){
		frame.runMove();
		frame.runCompile(id, CompletedSingleton.getInstance());
	}
    @Override public void mousePressed(MouseEvent e){}
    @Override public void mouseReleased(MouseEvent e){}

    @Override
    public void mouseEntered(MouseEvent e)
    {
        setBorder(redBorder);
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        setBorder(blackBorder);
    }


}
