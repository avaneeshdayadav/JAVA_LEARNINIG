/*
	AWT is Java's original platform dependent windowing, graphics and user interface widget toolkit while Swing is a GUI widget
	toolkit for Java that is an extension of AWT.
*/

import javax.swing.*;
import java.awt.*;

public class _6SimpleAnimation {

	int x = 70;
	int y = 70;

	public static void main (String[] args) {
		_6SimpleAnimation gui = new _6SimpleAnimation ();
		gui.go();
	}

	public void go() {

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MyDrawPanel drawPanel = new MyDrawPanel();
		frame.getContentPane().add(drawPanel);
		frame.setSize(300,300);
		frame.setVisible(true);

		for (int i = 0; i < 130; i++) {
			x++;
			y++;

			// We can't call PaintComponent() method directly so we use this method which tells the system to call it.
			// Note here we have used drawpanel.repaint() & not frame.repaint()
			// We can have multiple panels on a frame but here we are dealing with one panel on frame.
			drawPanel.repaint();

			try
			{
				// To add some delay.
				Thread.sleep(50);	
			}
			catch(Exception ex) { }
		}

	}// close go() method



	/*
		We have made MyDrawPanel this time inner class so that it can use instance variables x,y easily so we don't have the
		burden of passing x,y reference from this class to other class.
	 */

	class MyDrawPanel extends JPanel {

		public void paintComponent(Graphics g) {

			// Tor erase prev drawing on drawing panel
			g.setColor(Color.white);
			g.fillRect(0,0,this.getWidth(), this.getHeight());

			// To paint current drawing panel.
			g.setColor(Color.blue);
			g.fillOval(x,y,40,40);

		}
	} // close inner class


}