import java.net.DatagramSocket;

public class periodicPeerClient implements Runnable{
	
	private DatagramSocket dSocket;
	private currentUserProp props;
	
	public periodicPeerClient(DatagramSocket ds, currentUserProp p){
		dSocket=ds;
		props=p;	
	}
	
	public void run(){
			
		while (true){
			
			if(System.currentTimeMillis()>P2PTwitter.lastTime()+1200 && P2PTwitter.lastTime()!=-1){
				
				P2PTwitter.incrementMsgCount();
				Thread client	=	new	Thread( new peerClient(dSocket,P2PTwitter.getLastMessage(),props,P2PTwitter.getMsgCount()));
				client.start();
				P2PTwitter.lastTimeToNow();
				
				try {
					Thread.sleep(900);
				} catch (InterruptedException e) {}			
			}	
			
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {}

		}
	}
}