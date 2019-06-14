import java.io.*;
import java.util.*;
import java.net.*;


public class P2PTwitter {
	
	private static int messages_sent = 0;
	private static long last_change_time=-1;
	private static String last_message="";

	public static void main(String[] args) throws IOException {

		FileReader reader = new FileReader("participants.properties");
		Properties properties= new Properties();	
		properties.load(reader);
		
		currentUserProp props = new currentUserProp(properties, args[0]);
		
		String pName = props.currentPeer();
		Integer port= props.findPort(pName);
		InetAddress ip = props.findIP(pName);
		DatagramSocket dSocket = new DatagramSocket(port, ip);
		
		activePeers peerList = new activePeers(props);

		Thread	server	=	new	Thread(new peerServer(dSocket,peerList));
		server.start();
		
		Thread peer= new Thread(new p2pPeer(dSocket,peerList,props));
		peer.start();
		
		Thread periodicClient = new Thread( new periodicPeerClient(dSocket,props));
		periodicClient.start();

	}
	
	public static void incrementMsgCount(){
		messages_sent++;
	}
	
	public static int getMsgCount(){
		return messages_sent;
	}
	
	public static void lastTimeToNow(){
		last_change_time=System.currentTimeMillis();
	}
	
	public static long lastTime(){
		return last_change_time;
	}
	
	public static String getLastMessage(){
		return last_message;
	}
	
	public static void updateLastMessage(String str){
		last_message= str;
	}
}