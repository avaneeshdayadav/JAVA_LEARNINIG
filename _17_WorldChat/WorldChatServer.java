import java.io.*;
import java.net.*;
import java.util.*;

public class WorldChatServer{

	
	// This array will store all the output streams of incoming socket from client.
	ArrayList<PrintWriter> clientOutputStreams;

	public static void main(String[] args)
	{
		WorldChatServer server = new WorldChatServer();
		server.go();
	}

	void go()
	{

		clientOutputStreams = new ArrayList<PrintWriter>();
		try
		{
			// This starts the server application listening for client requests coming in for port 4242.
			ServerSocket serverSock = new ServerSocket(4242);

			// Keep Listening always.
			while(true)
			{
				/*
					The accept method blocks (just sits there) while it's waiting for a client socket connection.
					When a client finally tries to connect, the method returns a plain old Socket (on a different port)
					that knows to communicate with the client. (i,e knows the client Ip address and port num.) Socket is
					on a different port than on serverSocket. So that serverSocket can go back to waiting for other clients.
				*/
				Socket clientSock = serverSock.accept();

				// get output stream of the socket at which the client was assigned by accept() and chain it with PrintWriter.
				PrintWriter out = new PrintWriter(clientSock.getOutputStream());

				// Add this client socket output stream to the array.
				clientOutputStreams.add(out);

				// Make a new thread and assign a task of getting keep reading msgs coming from the client on server.
				// Pass a object that implements interface 'Runnable' which has a func run() that should be overriden.
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

				// This is very imp as we are using PrintWriter. It is like a buffer and won't send msg till buffer is full.
				// So thats why we flush it ourselves to send the msg as soon as we get it.
				out.flush();
			}
			catch(Exception ex){ex.printStackTrace();}
		}
	}


}

