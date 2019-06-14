import java.io.IOException;
import java.util.ArrayList;

public class activePeers {
	
	private ArrayList<activePeersNode> aPeersList;
	
	public activePeers(currentUserProp props){
		try{
			aPeersList = new ArrayList<activePeersNode>();
			
			//initializing the values
			aPeersList.add( new activePeersNode(props.currentPeerUnikey(),props.findPseudo(props.currentPeer()),"not yet initialized",System.currentTimeMillis()));
			
			for(String peer:props.getConnectedPeerNames(props.currentPeer())){
				activePeersNode a = new activePeersNode(props.findUnikey(peer), props.findPseudo(peer),"not yet initialized",System.currentTimeMillis());
				aPeersList.add(a);	
			}
		}
		catch(IOException e){
		}
	}
	
	public activePeersNode findNode(String unikey){
		
		activePeersNode node=null;
		for(activePeersNode aPeer: aPeersList){
			if(aPeer.getUnikey().equals(unikey)){
				aPeer = node;
				break;
			}
		}
		return node;
	}
	
	public void updateStatus(String unikey, String message, long time){
		
		for(activePeersNode aPeer: aPeersList){
			if(aPeer.getUnikey().equals(unikey)){
				aPeer.setStatus(message);
				aPeer.setTime(time);
				break;
			}
		}
	}
	
	public void updateStatus(String unikey, String message, long time, int s){
		
		for(activePeersNode aPeer: aPeersList){
			//TODO might have to take in statuses with seqNum 0
			if(aPeer.getUnikey().equals(unikey) && s>=aPeer.getSeqNumber()){
				aPeer.setStatus(message);
				aPeer.setTime(time);
				aPeer.setSeqNumber(s);
				break;
			}
		}
	}
	
	public String printinString(){
		String pform="### P2P tweets ###\n";
		
		for(activePeersNode aPeer: aPeersList){
			
			if(System.currentTimeMillis()-aPeer.getTime()>=20000)
				pform +="";
			else if(System.currentTimeMillis()-aPeer.getTime()>=10000)
				pform += "# ["+aPeer.getPseudo()+" ("+aPeer.getUnikey()+") : idle]"+"\n";
			else if(aPeer.getStatus().equals("not yet initialized"))
				pform += "# ["+aPeer.getPseudo()+" ("+aPeer.getUnikey()+") : "+aPeer.getStatus()+"]"+"\n";
			else
				pform += "# "+aPeer.getPseudo()+" ("+aPeer.getUnikey()+") : "+aPeer.getStatus()+"\n";
		}
		
		pform+="### End tweets ###";
		
		return pform;
	}
}
