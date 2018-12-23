import clientUDP.ClientUDP;
import serverUDP.ServerUDP;

public class Main {
public static void main(String[] args) {
	int[] ports = {1500,1501,1502};
	ServerUDP serv = new ServerUDP(ports);
	serv.listen();
	ClientUDP client1 = new ClientUDP("192.168.10.24",ports);
	client1.knock();
}
}
