import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;
import java.util.Properties;

public class currentUserProp {	
	
	private Properties properties;
	private String pName;
	
	//by insterting this we are making a separate cUP object for each user earlier just one object which was passed around.
	public currentUserProp(Properties props, String unikey) throws IOException{
		properties = props;
		pName= this.findPeerName(unikey);
	}
	
	public String currentPeer(){
		return pName;
	}
	
	public String currentPeerUnikey(){
		return properties.getProperty( pName+".unikey");
	}
	
	private String findPeerName(String unikey) throws IOException{
		
		String peerName= null;
		for (Map.Entry<Object, Object> entry : properties.entrySet()) {
			if(entry.getValue().equals(unikey)) {
				   peerName = entry.getKey().toString().split("\\.")[0];
			}
		}
		return peerName;
	} 
	
	public int findPort(String peerName) throws IOException{
		return Integer.parseInt(properties.getProperty( peerName + ".port"));
	} 
	
	public InetAddress findIP(String peerName) throws IOException{
		return InetAddress.getByName(properties.getProperty( peerName + ".ip"));
	} 
	
	public String findPseudo(String peerName) throws IOException{
		String p=properties.getProperty( peerName + ".pseudo");
		if(p.contains("\\")){
			p.replace("\\", "\\\\\\");
		}
		return p;
	}
	
	public String findUnikey(String peerName){
		return properties.getProperty( peerName + ".unikey");
	}
	
	public String[] getConnectedPeerNames(String peerName){
		
		String peers = properties.getProperty("participants");
		String[] peersArr= peers.trim().split(","); 
		
		String[] finalPeersArr = new String[peersArr.length-1];
		
		int i=0;
		for(String peer: peersArr){
			if(!peer.equals(peerName)){
				finalPeersArr[i]=peer;
				i++;
			}
		}

		return finalPeersArr;
	}
}
