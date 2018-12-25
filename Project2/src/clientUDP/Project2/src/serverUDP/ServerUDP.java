package serverUDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListMap;

import javax.swing.plaf.SliderUI;

public class ServerUDP {
	private volatile ConcurrentSkipListMap<String, ArrayList<Integer>> map = new ConcurrentSkipListMap<>();
	private int[] ports;
	private ArrayList<Integer> set = new ArrayList<>();

	public ServerUDP(int[] p) {
		ports = p;

		for (int i = 0; i < ports.length; i++) {
			if (!set.contains(ports[i])) {
				set.add(ports[i]);
			}
		}

	}

	public void listen() {
		for (int i : set) {

			try {
				if (i == ports[0]) {

					(new ServerThread(i, map, true, false,set)).start();// first

					// Thread.sleep(500);

				} else if (i != ports[ports.length - 1] && i != ports[0]) {
					(new ServerThread(i, map, false, false,set)).start();
					// Thread.sleep(500);
				} else {
					(new ServerThread(i, map, false, true,set)).start();// last
					Thread.sleep(500);
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
}
