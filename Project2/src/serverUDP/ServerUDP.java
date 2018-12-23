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
	private volatile TreeSet<Integer> set = new TreeSet<>();

	public ServerUDP(int[] p) {
		ports = p;
		for (int i = 0; i < ports.length; i++) {
			set.add(p[i]);
		}

	}

	public void listen() {
		for (int i = 0; i < ports.length; i++) {
			try {
				if (ports[i] == ports[0]) {
					(new ServerThread(ports[i], map, true, false)).start();// first

					//Thread.sleep(500);

				} else if (ports[i] != ports[ports.length - 1] && ports[i] != ports[0]) {
					(new ServerThread(ports[i], map, false, false)).start();
					//Thread.sleep(500);
				} else {
					(new ServerThread(ports[i], map, false, true)).start();// last
					Thread.sleep(500);
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
}
