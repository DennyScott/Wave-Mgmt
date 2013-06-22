package components;

//import com.sun.awt.AWTUtilities;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class MyDialog extends JDialog {
	JPanel panel;
	public MyDialog(JFrame parent, String title, Dimension dim) {
		super(parent, true);

		setUndecorated(true);

		setTitle(title);


		panel = new JPanel(new GridBagLayout());
		panel.setBackground(new Color(0x1E2C45));
		panel.setLayout(new GridBagLayout());
		panel.setPreferredSize(dim);

		// the following two lines are only needed because there is no
		// focusable component in here, and the "hit espace to close" requires
		// the focus to be in the dialog. If you have a button, a textfield,
		// or any focusable stuff, you don't need these lines.
		panel.setFocusable(true);
		panel.requestFocusInWindow();

		getContentPane().add(panel);

		SwingUtils.createDialogBackPanel(this, parent.getContentPane());
		SwingUtils.addEscapeToCloseSupport(this, true);
	}
	
	public JPanel getPanel(){
		return panel;
	}
}

