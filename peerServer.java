import java.io.IOException;
import java.net.DatagramSocket;
import java.net.DatagramPacket;

public class peerServer implements Runnable{
	private DatagramSocket dSocket;
	private activePeers peer_list;
	
	public peerServer(DatagramSocket ds, activePeers list){
		dSocket=ds;
		peer_list=list;
	}
	
	public void run(){
		try {
			while(true){
				byte[] buf = new byte[1024];
				DatagramPacket p = new DatagramPacket(buf, buf.length);
				dSocket.receive(p);
				
				//of the format abcd1234:i\:love\:melons
				String data=new String(p.getData(), 0, p.getLength());
				//System.out.println(data);
				String status= data.substring(12).replace("\\:", ":");
				//System.out.println(status);
				String unikey= data.substring(1, 9);
				//System.out.println(unikey);
				//System.out.println("]:[");
				long time= System.currentTimeMillis();
				String seqNum = data.substring(data.lastIndexOf(":")+2,data.length());
				//System.out.println(seqNum);
				//System.out.println(status.substring(0,status.lastIndexOf(":")-1));
				
				if(isNumeric(seqNum))
					peer_list.updateStatus(unikey, status.substring(0,status.lastIndexOf(":")-1), time,Integer.parseInt(seqNum));
				else	
					//update status in the Data Structure
					peer_list.updateStatus(unikey, status, time);
			}
		}
		catch (IOException e) {

		}
	}
	
	public static boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    double d = Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
	
}
