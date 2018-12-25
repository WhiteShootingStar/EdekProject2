package serverUDP;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPThread extends Thread {

	private ServerSocket server;
	private Socket client;
boolean isOK =true;
	public TCPThread(ServerSocket s, Socket cl) {
		server = s;
		client = cl;
	}
	@Override
	public void run() {
		while(isOK) {
			try {
				client = server.accept();
				PrintWriter write = new PrintWriter(client.getOutputStream(),true);
				write.println("Norm chuvak");
				
				isOK=false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			server.close();
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
