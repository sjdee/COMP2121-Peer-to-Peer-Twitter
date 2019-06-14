import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class peerClient implements Runnable {
	
	private DatagramSocket dds;
	private String data_out;
	private currentUserProp pp;
	private int messageNum;
	
	
	public peerClient(DatagramSocket ds, String info, currentUserProp p, int m){
		dds=ds;
		data_out=info;
		pp=p;
		messageNum= m; 
		
	}
	
	public void run(){
		
		try{	
			byte[] buf = new byte[1024];
			data_out.replaceAll(":","\\\\:");
			data_out="["+pp.currentPeerUnikey()+"]:["+data_out+"]:["+messageNum+"]";
			buf = data_out.getBytes();
			
		
			//Send packet out too all the connected peers
			for(String entry : pp.getConnectedPeerNames(pp.currentPeer())){
				Integer port = pp.findPort(entry);
				InetAddress ip= pp.findIP(entry);
				DatagramPacket out = new DatagramPacket(buf, buf.length, ip, port);
				dds.send(out);
			}
		}
		
		catch(IOException e){
		}
	}
	
}
