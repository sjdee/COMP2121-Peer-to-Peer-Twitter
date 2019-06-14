import java.net.DatagramSocket;
import java.util.Scanner;

public class p2pPeer implements Runnable{
	
	private DatagramSocket dSocket;
	private activePeers peerList;
	private currentUserProp props;
	
	public p2pPeer(DatagramSocket ds, activePeers list,currentUserProp p){
		dSocket=ds;
		peerList=list;
		props=p;
		
	}
	
	public void run(){
		while(true){
			System.out.println("Status:");
			//TODO helper methods to check for valid input 140 lines
			Scanner in= new Scanner(System.in);
			String data = in.nextLine();
			//System.out.println(data);
			
			if(data.length()>140){
				System.out.println("Status is too long, 140 characters max. Retry.");
			}
			else if(data.equals("")){
				System.out.println("Status is empty. Retry.");
			}
			else{
				//print only after new status has been entered therefore server doesn't print but updates the database
				peerList.updateStatus(props.currentPeerUnikey(), data, System.currentTimeMillis());
				System.out.println(peerList.printinString());
				
				P2PTwitter.updateLastMessage(data);
				P2PTwitter.incrementMsgCount();
				Thread client	=	new	Thread( new peerClient(dSocket,data,props,P2PTwitter.getMsgCount()));
				client.start();
				P2PTwitter.lastTimeToNow();
			}
		}
	}
}
