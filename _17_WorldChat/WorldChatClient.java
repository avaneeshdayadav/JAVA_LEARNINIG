import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;			// Class Socket is in java.net
import java.io.*;			// for all type of input & output streams

public class WorldChatClient implements ActionListener {

	JTextArea textArea;
	JTextField input;
	PrintWriter writer;
	BufferedReader reader;
	Socket sock;
	String clientName;


	WorldChatClient(String name)
	{
		clientName = name;
	}

	public void go(){

		JFrame frame = new JFrame();

		JPanel panel = new JPanel();
		JPanel pane2 = new JPanel();

		panel.setBackground(Color.gray);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		pane2.setBackground(Color.darkGray);

		JButton button = new JButton("Send");
		button.addActionListener(this);
		button.setBackground(Color.GREEN);
		button.setOpaque(true);

		input = new JTextField(30);
		input.requestFocus();

		pane2.add(input);
		pane2.add(button);

		Font font = new Font("Verdana", Font.BOLD, 14);

		textArea = new JTextArea();
		textArea.setFont(font);
		textArea.setEditable(false);
		textArea.setForeground(Color.WHITE);
		textArea.setBackground(new Color(113, 193, 222));
		textArea.setRows(12);
		textArea.setLineWrap(true);

		// 
		JScrollPane scroller = new JScrollPane(textArea);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel.add(scroller);
		panel.add(pane2);

		setupNetwork();

		// Make a thread that has task to keep reading incoming msgs from the server while the main method will keep sending the msgs to server.
		Thread task2 = new Thread(new IncomingReaderStack());
		task2.start();

		frame.getContentPane().add(BorderLayout.NORTH,panel);
		// frame.getContentPane().add(BorderLayout.SOUTH, pane2);
		frame.setSize(410,300);
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent ev) {

		// Client code goes here.

		String message = input.getText();
		//System.out.println(message);
		try
		{
			writer.println(clientName+" : "+message);
			writer.flush();
		}
		catch(Exception ex){ex.printStackTrace();}

		input.setText("");
		input.requestFocus();

	}

	public void setupNetwork(){

		try
		{
			sock = new Socket("127.0.0.1", 4242);											// Connect with server with this ip address (localhost) and at this port.
			writer = new PrintWriter(sock.getOutputStream());								// Get output stream of Socket
			reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));		// Get input stream of Socket.
		}
		catch(IOException ex){ex.printStackTrace();}

	}


	public static void main (String[] args) {

		if(args.length == 0)
		{
			System.out.println("You should provide ur name. Try again !");
			return;
		}
		WorldChatClient chat = new WorldChatClient(args[0]);
		chat.go();
	}

	public class IncomingReaderStack implements Runnable{

		public void run(){

			String receivedMsg;
			try
			{
				// Keep reading incoming messages from server.
				while((receivedMsg = reader.readLine()) != null)
				{
					System.out.print("Incoming msg "+receivedMsg);
					textArea.append(receivedMsg+"\n");
				}
			}
			catch(IOException ex){ex.printStackTrace();}
		}
	}

}

