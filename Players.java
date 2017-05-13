import java.awt.List;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class Players {
	private Hashtable<String, ServerThread> listOfPlayers;
	
	Players(){
		this.listOfPlayers = new Hashtable<String, ServerThread>();
	}
	
	public synchronized boolean addPlayer(String username, ServerThread thread){
		if (!isPlayer(username)){
			this.listOfPlayers.put(username, thread);
			return true;
		}
		return false;
	}
	
	public boolean isPlayer(String username){
		return this.listOfPlayers.containsKey(username);
	}
	
	public synchronized void removePlayer(String username){
		this.listOfPlayers.remove(username);
	}
	
	
	public synchronized Enumeration<String> getListOfPlayers(){
		 return this.listOfPlayers.keys();	
	}
	
	public synchronized ServerThread getPlayerServer(String username){
		return this.listOfPlayers.get(username);
	}
}
