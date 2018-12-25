import clientUDP.ClientUDP;

public class MainCl1 {

	public static void main(String[] args) {
		int[] ports = { 1500, 1501, 1502, 1503, 1504, 1505, 1506, 1507, 1508 };
		ClientUDP client1 = new ClientUDP("192.168.10.24", ports);
		client1.knock();

		
	}

}
