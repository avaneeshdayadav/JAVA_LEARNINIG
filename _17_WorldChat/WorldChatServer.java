import java.io.*;
import java.net.*;
import java.util.*;

public class WorldChatServer{

	
	ArrayList<PrintWriter> clientOutputStreams;

	public static void main(String[] args) {
		WorldChatServer server = new WorldChatServer();
		server.go();
	}

	void go()
	{
		clientOutputStreams = new ArrayList<PrintWriter>();
		try
		{
			ServerSocket serverSock = new ServerSocket(4242);

			while(true)
			{
				Socket clientSock = serverSock.accept();
				PrintWriter out = new PrintWriter(clientSock.getOutputStream());
				clientOutputStreams.add(out);

				Thread thisClientThread = new Thread(new ClientHandeler(clientSock));
				thisClientThread.start();
				System.out.println("Got Connection");
			}
		}
		catch(Exception ex){ex.printStackTrace();}
	}

	class ClientHandeler implements Runnable{

		BufferedReader inp;

		// Constructor
		public ClientHandeler(Socket clientSock)
		{
			try
			{
				inp = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));
			}
			catch(Exception ex){ex.printStackTrace();}
		}

		public void run()
		{
			try
			{
				String recievedMsg;
				while((recievedMsg = inp.readLine()) != null)
				{
					System.out.println(recievedMsg);
					tellEveryone(recievedMsg);
				}
			}
			catch(Exception ex){ex.printStackTrace();}
		}
	} // innerclass closed

	public void tellEveryone(String recievedMsg)
	{
		Iterator it = clientOutputStreams.iterator();

		while(it.hasNext())
		{
			try
			{
				PrintWriter out = (PrintWriter) it.next();
				System.out.println("Sending to everyone "+recievedMsg);
				out.println(recievedMsg);
				out.flush();
			}
			catch(Exception ex){ex.printStackTrace();}
		}
	}


}

