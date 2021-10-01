import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;			// Class Socket is in java.net
import java.io.*;			// for all type of input & output streams

public class WorldChatClient2 implements ActionListener {

	JTextArea textArea;
	JTextField input;
	ObjectOutputStream writer;
	ObjectInputStream reader;
	Socket sock;
	String clientName;
	int flag=0;

	public WorldChatClient2(String name)
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
			writer.writeObject(clientName);					// println() adds newLine at end of message.
			writer.writeObject((String)input.getText());
			//writer.close();
		}
		catch(Exception ex){ex.printStackTrace();}

		input.setText("");
		input.requestFocus();

	}

	public void setupNetwork(){

		try
		{
			sock = new Socket("127.0.0.1", 5000);
			writer = new ObjectOutputStream(sock.getOutputStream());
			reader = new ObjectInputStream(sock.getInputStream());
		}
		catch(IOException ex){ex.printStackTrace();}

	}


	public static void main (String[] args) throws Exception {

		if(args.length == 0)
		{
			System.out.println("You should provide ur name. Try again !");
			return;
		}

		WorldChatClient2 chat = new WorldChatClient2(args[0]);

		chat.go();
	}

	public class IncomingReaderStack implements Runnable{

		public void run(){

			Object obj = null;
			try
			{
				// Keep reading incoming messages from server.
				while((obj = reader.readObject()) != null)
				{
					String recievedClientName = (String)obj;
					String recievedMsg = (String)reader.readObject();
					// System.out.print("Incoming msg "+recievedMsg);

					System.out.println("Recieved client name is "+(String)recievedClientName);
					System.out.println("This client name is "+(String)clientName+"\n");

					if(flag == 0)
					{
						System.out.println("normal");
						textArea.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
						flag=1;
					}
					else if(flag == 1)
					{
						System.out.println("abnormal");
						textArea.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
						flag=0;
					}
					textArea.append(recievedClientName+" :\n"+recievedMsg+"\n\n");
					
				}
			}
			catch(IOException ex){ex.printStackTrace();}
			catch(ClassNotFoundException cex){cex.printStackTrace();}
		}
	}

}

