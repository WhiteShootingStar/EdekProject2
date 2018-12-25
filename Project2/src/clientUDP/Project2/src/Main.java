import clientUDP.ClientUDP;
import serverUDP.ServerUDP;

public class Main {
public static void main(String[] args) {
	int[] ports = {1501,1500,1502,1696,2434,2345,3456,6565,1501,1500,1502,1696,2434,2345,3456,6565};
	ServerUDP serv = new ServerUDP(ports);
	serv.listen();
//	ClientUDP client1 = new ClientUDP("192.168.10.24",ports);
	//client1.knock();
}
}
