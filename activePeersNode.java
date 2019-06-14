
public class activePeersNode {
	private String unikey;
	private String pseudo;
	private String status;
	private long time;
	private int seqnum;
	
	public activePeersNode(String u,String p, String s,long t){
		unikey=u;
		pseudo=p;
		status=s;
		time=t;	
		seqnum=0;
	}

	public String getUnikey(){
		return unikey;
	}
	
	public String getPseudo(){
		return pseudo;
	}
	
	public String getStatus(){
		return status;
	}
	
	public long getTime(){
		return time; 
	}
	
	public int getSeqNumber(){
		return seqnum;
	}
	public void setStatus(String s){
		status=s;
	}
	
	public void setTime(long t){
		time=t;
	}
	
	public void setSeqNumber(int s){
		seqnum=s;
	}
}
