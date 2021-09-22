import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class _5TwoButtons {

	JFrame frame;
	JLabel label;

	public static void main (String[] args){
		_5TwoButtons gui = new _5TwoButtons ();
		gui.go();
	}

	public void go() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JButton labelButton = new JButton("Change Label");
		labelButton.addActionListener(new LabelListener());

		JButton colorButton = new JButton("Change Circle");
		colorButton.addActionListener(new ColorListener());

		label = new JLabel("I am a label");

		MyDrawPanel drawPanel = new MyDrawPanel();

		frame.getContentPane().add(BorderLayout.SOUTH, colorButton);
		frame.getContentPane().add(BorderLayout.CENTER, drawPanel);
		frame.getContentPane().add(BorderLayout.EAST, labelButton);
		frame.getContentPane().add(BorderLayout.WEST, label);

		frame.setSize(500,500);
		frame.setVisible(true);
	}

	// Making two inner classes.

	/*
		NOTE : Not to make this classes outside of this class and make them extend this class thinking that these classes will get access of frame &
				label variables after extending this class. Bcoz in that case although frame & label variable will be part of both these classes but they are
				different for different instances of these classes and not the same frame, label variables as in this class which is holding our frame.
				
	*/
		class LabelListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				label.setText("Ouch!");
			}
		} // close  1st inner class

		class ColorListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				frame.repaint();
			}
		} // close 2nd inner class

	// Making two inner classes end.

}


class MyDrawPanel extends JPanel {

	/*
		When the frame holding your drawing panel is displayed, paintComponent() is called and your circle appears. If the user
		iconifies/minimizes the window, the JVM knows the frame needs 'repair' when it gets de-iconified, so it calls 
		paintComponent() again. Anytime the JVM thinks the display needs refreshing, your paintComponent() method will be called.

		One more thing, you never call this method yourself! The argument to this method (a Graphics object) is the actual drawing canvas
		that gets slapped onto the real display. You can’t get this by yourself; it must be handed to you by the system.
	*/

	public void paintComponent(Graphics g) {				

		// Code to fill the oval with a random color

		// cast Graphics to one of its subclass Graphics2D to use Graphics2D methods. It is declared like "public abstract class Graphics2D extends Graphics".
		Graphics2D g2d = (Graphics2D) g;                                                    
		
		
		// Fill the full background (i.e whole panel with default black color. this.getWidth() & this.getHeight() returns width & height of whole panel.
		g2d.fillRect(0,0,this.getWidth(), this.getHeight());


		// Generate random RGB values
		int red = (int) (Math.random() * 256);
		int green = (int) (Math.random() * 256);
		int blue = (int) (Math.random() * 256);


        // Get colour for the generated RGB values.
		Color startColor = new Color(red, green, blue);
		
		
		// generated again a different RGB values for another colour.
		red = (int) (Math.random() * 256);
		green = (int) (Math.random() * 256);
		blue = (int) (Math.random() * 256);
		
		
		// Get colour for the generated RGB values.
		Color endColor = new Color(red, green, blue);
		
		
		// Generate gradient color out of two made colours.
		GradientPaint gradient = new GradientPaint(70,70,startColor, 250,250, endColor);
		
		
		// Fill the widget with generated gradient colour.
		g2d.setPaint(gradient);
		
		
		// Draw a oval at specific x,y cordinates with width & height (i.e width = 200 & height 200).
		g2d.fillOval(70,70,200,200);
		
			
		// Draw and fill a rectangle.
		g2d.fillRect(20,50,100,100);

		// Draw and fill one more rectangle.
		g2d.fillRect(220,50,100,100);
		

	}
}