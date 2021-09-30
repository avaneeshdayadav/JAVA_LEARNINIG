import java.io.*;
import java.net.*;

public class DailyAdviceServer {

	String[] adviceList = {"Take smaller bites", "Go for the tight jeans. No they do NOT make you look fat.", "One word: inappropriate",
	"Just for today, be honest. Tell yourboss what you *really* think", "You might want to rethink that haircut."};

	public void go() {
		try
		{
			ServerSocket serverSock = new ServerSocket(4242);					// This starts the server application listening for client requests coming in for port 4242.

			while(true)															// The server goes into a permanent loop, waiting for (and servicing) client requests
			{
				Socket sock = serverSock.accept();								//request comes in, and then the method returns a Socket (on some anonymous port) for communicating with the client
				PrintWriter writer = new PrintWriter(sock.getOutputStream());
				String advice = getAdvice();
				writer.println(advice);
				writer.close();
				System.out.println(advice);
			}
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	} // close go

	private String getAdvice() {
		int random = (int) (Math.random() * adviceList.length);
		return adviceList[random];
	}
	public static void main(String[] args) {
		DailyAdviceServer server = new DailyAdviceServer();
		server.go();
	}
}