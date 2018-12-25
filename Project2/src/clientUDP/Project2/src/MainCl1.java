import clientUDP.ClientUDP;

public class MainCl1 {

	public static void main(String[] args) {
		int[] ports = { 1501,1500,1502,1696,2434,2345,3456,6565 };
		ClientUDP client1 = new ClientUDP("192.168.10.24", ports);
		client1.knock();

		
	}

}
