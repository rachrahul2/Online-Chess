import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Hashtable;

public class ServerThread extends Thread{
	private Socket socket;
	private Players players;
	private PrintWriter out;
	private BufferedReader in;
	private ServerThread opponent;
	private Board board;
	private String color;
	private String myUsername = "";
	private Hashtable<String, ServerThread> listOfInvitations;
	private boolean otherAccept;
	private boolean waiting;
	private boolean startGame;
	private boolean colorSended;
	
	ServerThread(Socket clientSocket, Players players){
		this.socket = clientSocket;
		this.players = players;
		this.opponent = null;
		this.board = null;
		this.listOfInvitations = new Hashtable<String, ServerThread>();
		this.otherAccept = false;
		this.waiting = false; 
		this.startGame = false;
		this.color = "";
		this.colorSended = false;
		
		try {
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		System.out.println("Hello from a thread!");
		
		String inputLine, outputLine;
        
        try {
			while ((inputLine = in.readLine()) != null) {
			    String[] input = inputLine.split(" ");
			    if (input[0].equalsIgnoreCase("username"))
			    	sendRegister(input[1]);
			    
			    else if (input[0].equalsIgnoreCase("getPlayers"))
			    	listPlayers();
				
			    else if (input[0].equalsIgnoreCase("invite"))
			    	invite(input[1]);
			    
			    else if (input[0].equalsIgnoreCase("accept"))
			    	 accept(input[1]);
			    
			    else if (input[0].equalsIgnoreCase("refuse"))
					refuse(input[1]);
			    
			    else if (input[0].equalsIgnoreCase("color"))
					chooseColor(input[1]);
			    
			    else if (input[0].equalsIgnoreCase("move"))
			    	move(input[1], input[2], input[3], input[4]);
			    
			    else
			    	System.out.println("Invalid input: "+ inputLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	public String getUserName(){
		return this.myUsername;
	}
	
	private void sendRegister(String username){
		String answer;
		if (players.addPlayer(username, this)){
			answer = "successful registration";
			this.myUsername = username; 
		}else
			answer = "invalid username";
	    
		out.println(answer);
	}
	
	private void listPlayers(){
		String answer = "list";
		
		for (Enumeration<String> e = players.getListOfPlayers(); e.hasMoreElements();){
			String next = e.nextElement();
			if (!next.equalsIgnoreCase(this.myUsername))
				answer += " " + next;
		}
		out.println(answer);
	}
	
	private synchronized void invite(String username){
		if (username.equalsIgnoreCase(this.myUsername))
			out.println("invalid username");
		
		else if ((this.opponent = this.players.getPlayerServer(username)) != null){
			this.opponent.sendInvitation(this.myUsername, this);
			this.waiting = true;
			//Wait one minute
			(new Wait(this, username)).start();
		}else
			out.println("invalid username");
	}
	
	private synchronized void accept(String username){
		if (username.equalsIgnoreCase(this.myUsername))
			out.println("invalid username");
		
		else if ((this.opponent = this.listOfInvitations.get(username)) != null){
			if (!this.opponent.invitationAccepted(this.myUsername))
				out.println("timeout "+username);
		
		}else
			out.println("invalid username");
	}
	
	private synchronized void refuse(String username){
		if (username.equalsIgnoreCase(this.myUsername))
			out.println("invalid username");
		
		else if ((this.opponent = this.listOfInvitations.get(username)) != null){
			this.opponent.invitationRefused(this.myUsername);
			out.println("ok");
		
		}else
			out.println("invalid username");
	}
		
	/* Metodo al que llama otro ServerThread para invitar a jugar a este ServerThread */
	public synchronized void sendInvitation(String username, ServerThread otherServer){
		if (!this.waiting && !this.startGame){
			this.listOfInvitations.put(otherServer.getUserName(), otherServer);
			out.println("invitationFrom " + otherServer.getUserName());
		}
	}
	
	/* Metodo al qe llama otro ServerThread para aceptar la invitación a jugar */
	public synchronized boolean invitationAccepted(String username){
		if (!this.startGame && this.opponent != null &&
				this.opponent.getUserName().equalsIgnoreCase(username)){
			this.otherAccept = true;
			out.println("accept " + username);
			this.opponent.confirm(this.myUsername);
			this.players.removePlayer(this.myUsername);
			this.waiting = false;
			return true;
		}
		return false;
	}
	
	/* Metodo al que llama otro ServerThread para rechazar la invitación a jugar */
	public synchronized void invitationRefused(String username){
		if (!this.startGame && this.opponent != null &&
				this.opponent.getUserName().equalsIgnoreCase(username)){
			this.opponent = null;
			out.println("refuse "+ username);
			this.waiting = false;
		}
	}
	
	private synchronized void confirm(String username){
		if (!this.startGame && this.opponent != null &&
				this.opponent.getUserName().equalsIgnoreCase(username)){
			this.players.removePlayer(this.myUsername);
			out.println("confirm");
		}
	}
	
	private void chooseColor(String color){
		this.color = color;
		this.colorSended = false;
		this.opponent.colorChoosed(color);
	}
	
	
	public void colorChoosed(String opponentColor){
		String aux = "white";
		while(this.color.equals("")){
			try {
				Thread.sleep(1000);
				aux = "black";
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (opponentColor.equalsIgnoreCase("whatever") && this.color.equalsIgnoreCase("whatever")){
			out.println(aux);
			this.color = aux;
		}else if (opponentColor.equalsIgnoreCase(this.color)){
			out.println("the other player choosed the same color");
			this.color = "";
		}else if (opponentColor.equalsIgnoreCase("white") && this.color.equalsIgnoreCase("whatever")){
			out.println("black");
			this.color = "black";
		}else if (opponentColor.equalsIgnoreCase("black") && this.color.equalsIgnoreCase("whatever")){
			out.println("white");
			this.color = "white";
		}else if (opponentColor.equalsIgnoreCase("whatever")){
			out.println(this.color);
		}else
			out.println(this.color);
		
		this.colorSended = true;
		if (this.color.equalsIgnoreCase("white")){
			this.board = new Board();
			out.println(this.board.toString(this.color));
			this.opponent.setBoard(this.board);
		}
	}
	
	public void setBoard(Board b){
		this.board = b;
		while(!this.colorSended){
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		out.println(this.board.toString(this.color));
	}
	
	private void move(String x1, String y1, String x2, String y2){
		int row1, row2, col1, col2;
		row1 = Integer.parseInt(x1);
		col1 = Integer.parseInt(y1);
		row2 = Integer.parseInt(x2);
		col2 = Integer.parseInt(y2);
		
		if (this.board.isValidMovement(row1, col1, row2, col2, this.color)){
			this.board.move(row1, col1, row2, col2);
			String state = this.board.getGameState(this.color);
			
			if (state.equalsIgnoreCase("youwin")){
				out.println(state);
				out.println(this.board.toString(this.color));
				try {
					in.close();
					out.close();
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.opponent.yourTurn("youlose");
			}else{
				out.println(this.board.toString(this.color));
				this.opponent.yourTurn(state);
			}
				
		}else
			out.println("invalid movement");
	}
	
	public void yourTurn(String state){
		
		if (state.equalsIgnoreCase("youlose")){
			out.println(state);
			out.println(this.board.toString(this.color));
			try {
				in.close();
				out.close();
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else
			out.println(this.board.toString(this.color));
		
	}
	
	public boolean otherAccept(){
		return this.otherAccept;
	}
	
	public void setNullOpponent(){
		this.opponent = null;
	}
	
	public void stopToWait(){
		this.waiting = false;
	}

	private class Wait extends Thread {
		private ServerThread server;
		private String username;
		
		Wait(ServerThread server, String user){
			this.server = server;
			this.username = user;
		}

		public void run() {
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (!this.server.otherAccept()){
				this.server.invitationRefused(this.username);
				this.server.setNullOpponent();
			}
			this.server.stopToWait();
    	}

	}
}


