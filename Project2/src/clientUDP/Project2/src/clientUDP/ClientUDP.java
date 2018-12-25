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
import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import misc.UDP;

public class ClientUDP {

	final String join = "JOIN";
	final byte[] comm = join.getBytes();
	String adr = null;
	int[] ports = null;
	DatagramSocket soc=null;
	byte[] temp = new byte[UDP.MAX_DATAGRAM_SIZE];
	DatagramPacket pac = new DatagramPacket(temp, temp.length);
	public ClientUDP(String IP, int[] p) {

		adr = IP;
		ports = p;
		try {
			soc = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void knock() {
		
		try {

			for (int i = 0; i < ports.length; i++) {

				DatagramPacket query = new DatagramPacket(comm, comm.length, InetAddress.getByName(adr), ports[i]);

				System.out.println("Will send to " + adr + " " + ports[i]);
				soc.send(query);
				Thread.sleep(10);
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		try {
			// soc.receive(pac);

			final Duration timeout = Duration.ofSeconds(5);
			ExecutorService exec = Executors.newSingleThreadExecutor();

			final Future<DatagramPacket> handler = exec.submit(new Callable() {
				@Override
				public DatagramPacket call() throws Exception {
					soc.receive(pac);
					return pac;
				}
			});

			pac = handler.get(timeout.toMillis(), TimeUnit.MILLISECONDS);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
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

			s.close();

		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	String read(BufferedReader i) {
		String line = null;
		try {
			if ((line = i.readLine()) != null) {
				return  i.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return line;
	}
}
