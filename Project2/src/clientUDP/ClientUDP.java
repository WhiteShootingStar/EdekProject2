package clientUDP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import misc.UDP;

public class ClientUDP {

	final String join = "JOIN";
	final byte[] comm = join.getBytes();
	String adr = null;
	int[] ports = null;

	public ClientUDP(String IP, int[] p) {

		adr = IP;
		ports = p;

	}

	public void knock() {
		DatagramSocket soc = null;
		try {
			soc = new DatagramSocket();
			for (int i = 0; i < ports.length; i++) {

				DatagramPacket query = new DatagramPacket(comm, comm.length, InetAddress.getByName(adr), ports[i]);

				System.out.println("Will send to " + adr + " " + ports[i]);
				soc.send(query);
				soc.send(query);
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		byte[] temp = new byte[UDP.MAX_DATAGRAM_SIZE];
		DatagramPacket pac = new DatagramPacket(temp, temp.length);
		try {
			soc.receive(pac);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String resp = new String(pac.getData(), 0, pac.getLength()).trim();
		soc.close();
		try {
			Socket s = new Socket(adr, Integer.parseInt(resp));
			BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
