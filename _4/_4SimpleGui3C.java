import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class _4SimpleGui3C implements ActionListener {
	JFrame frame;

	public static void main (String[] args) {
		_4SimpleGui3C gui = new _4SimpleGui3C();
		gui.go();
	}

	public void go() {

		frame = new JFrame();                                           // Get a window frame.

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);           // Add close operation for program as the click of cross button.

		JButton button = new JButton("Click here to change colors");    // Get a event source (here its a button).

		button.addActionListener(this);                                 // Tell event source (button) to add this class to list of its active listeners (classes that implements 'ActionListener' interface).

		Color btnColor = new Color(80, 170, 235);			// A new colour that is going to be used for button background.

		button.setForeground(Color.WHITE);				// Text Colour of button.

		button.setBackground(btnColor);					// Background Colour of button.
	
		MyDrawPanel drawPanel = new MyDrawPanel();                      // A custom Drawing widget class that extends JPanel.

		frame.getContentPane().add(BorderLayout.SOUTH, button);         // Add this button to the bottom.

		frame.getContentPane().add(BorderLayout.CENTER, drawPanel);     // Add Drawing widget to the center.

		frame.setSize(400,400);                                         // Set size of window frame.

		frame.setVisible(true);                                         // Set window visibility to true.
		
	}

	@Override
	public void actionPerformed(ActionEvent event) {                    // A method in ActionListener interface that needs to be implemented when event occurs (button click). ActionEvent argument carry data about the event & is returned by source (button) when it gets clicked. 
		
		frame.repaint();                                                // Like a refresh repaint() associated with frame just calls paintComponent() again ont this frame. If you have done anything to change the look of the component, but not its size ( like changing color, animating, etc. ) then call this method.

	}
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