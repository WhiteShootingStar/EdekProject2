package serverUDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;

import misc.UDP;

public class ServerThread extends Thread {

	int port;
	private DatagramSocket server;
	private volatile ConcurrentSkipListMap<String, ArrayList<Integer>> map;
	private boolean isFirst;
	private boolean isLast;
	private volatile ArrayList<Integer> list;
	int index ;

	public ServerThread(int p, ConcurrentSkipListMap<String, ArrayList<Integer>> list, boolean a, boolean b,
			ArrayList<Integer> l) {
		port = p;
		isFirst = a;
		map = list;
		isLast = b;
		System.out.println("Work on" + port);
		this.list = l;
		index=this.list.indexOf(port);
	}

	@Override
	public void run() {
		try {
			server = new DatagramSocket(port, InetAddress.getByName("192.168.10.24"));

			while (true) {
				service();
			}

		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private synchronized void service() throws IOException {
		byte[] buff = new byte[UDP.MAX_DATAGRAM_SIZE];
		DatagramPacket datagram = new DatagramPacket(buff, buff.length);

		server.receive(datagram);
		if (isFirst) {
			// System.out.println(1);
			if (map.containsKey(datagram.getAddress().toString())) {
				// map.get(datagram.getAddress().toString()).add(port);
				System.out.println(datagram.getAddress().toString() + " tried to acsecc wrong sequence");
			} else {
				ArrayList<Integer> temp = new ArrayList<>();
				temp.add(port);
				map.put(datagram.getAddress().toString(), temp);

				System.out.println(port);
				// notifyAll();
			}

		} else if (!isFirst && !isLast) {
			// System.out.println(2);
			if (map.containsKey(datagram.getAddress().toString())) {
				ArrayList<Integer> temp = map.get(datagram.getAddress().toString());
				if (temp.get(temp.size() - 1).equals(list.get(index-1))) {
					temp.add(port);
					map.put(datagram.getAddress().toString(), temp);
					System.out.println(port);
				} else {
					System.out.println(datagram.getAddress().toString() + " tried to acsecc wrong sequence");
				}
			}
			// notifyAll();
		}

		else {
			// System.out.println(3);
			if (map.containsKey(datagram.getAddress().toString())) {
				ArrayList<Integer> temp = map.get(datagram.getAddress().toString());
				// System.out.println(map);
				if (temp.get(temp.size() - 1).equals(list.get(index-1))) {
					temp.add(port);
					map.put(datagram.getAddress().toString(), temp);
					System.out.println(port);
					startCommunicate(datagram);
				} else {
					System.out.println(datagram.getAddress().toString() + " tried to acsecc wrong sequence");
				}

			}
			// notifyAll();
		}
	}

	void startCommunicate(DatagramPacket data) {
		System.out.println(map);
		int portSend = data.getPort();
		InetAddress clientAddress = data.getAddress();

		ServerSocket serv = null;
		Socket client = null;

		try {
			serv = new ServerSocket(0);
			byte[] arr = ("" + serv.getLocalPort()).getBytes();
			DatagramPacket responce = new DatagramPacket(arr, arr.length, clientAddress, portSend);

			server.send(responce);
			(new TCPThread(serv, client)).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
}