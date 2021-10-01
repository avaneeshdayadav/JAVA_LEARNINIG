import java.io.*;
import java.net.*;
import java.util.*;

public class WorldChatServer2{

	
	ArrayList<ObjectOutputStream> clientOutputStreams;

	public static void main(String[] args) {
		WorldChatServer2 server = new WorldChatServer2();
		server.go();
	}

	void go()
	{
		clientOutputStreams = new ArrayList<ObjectOutputStream>();
		try
		{
			ServerSocket serverSock = new ServerSocket(5000);

			while(true)
			{
				Socket clientSock = serverSock.accept();
				ObjectOutputStream out = new ObjectOutputStream(clientSock.getOutputStream());
				clientOutputStreams.add(out);

				Thread thisClientThread = new Thread(new ClientHandeler(clientSock));
				thisClientThread.start();
				System.out.println("Got Connection");
			}
		}
		catch(Exception ex){ex.printStackTrace();}
	}

	class ClientHandeler implements Runnable{

		ObjectInputStream inp;

		// Constructor
		public ClientHandeler(Socket clientSock)
		{
			try
			{
				inp = new ObjectInputStream(clientSock.getInputStream());
			}
			catch(Exception ex){ex.printStackTrace();}
		}

		public void run()
		{
			try
			{
				Object recievedClientName=null;
				while((recievedClientName = inp.readObject()) != null)
				{
					String recievedMsg = (String)inp.readObject();
					tellEveryone(recievedClientName,recievedMsg);
				}
			}
			catch(Exception ex){ex.printStackTrace();}
		}
	} // innerclass closed

	public void tellEveryone(Object recievedClientName, String recievedMsg)
	{
		Iterator it = clientOutputStreams.iterator();

		while(it.hasNext())
		{
			try
			{
				ObjectOutputStream out = (ObjectOutputStream) it.next();
				//System.out.println("Sending to everyone "+recievedMsg);
				out.writeObject(recievedClientName);
				out.writeObject(recievedMsg);
			}
			catch(Exception ex){ex.printStackTrace();}
		}
	}


}

