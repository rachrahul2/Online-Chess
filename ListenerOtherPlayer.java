import javax.swing.JOptionPane;

public class ListenerOtherPlayer extends Thread{
	private ListenerClient listener;
	private Client client;
	private Time timer;
	
	ListenerOtherPlayer(ListenerClient listen, Client clt, Time timer){
		this.listener = listen;
		this.client = clt;
		this.timer = timer;
	}
	
	public void run(){
		String answer = listener.getMessage();
		timer.endOpponentTime();
		this.client.refreshOpponentTime();
		if (answer.equalsIgnoreCase("youlose")){
			this.client.disableBoard();
			String msj = "Sorry! You lost!";
			JOptionPane.showMessageDialog(null,msj, 
					"Oops",JOptionPane.INFORMATION_MESSAGE);
			
			System.out.println(answer);
			answer = listener.getMessage();
			this.client.updateBoard(this.client.processBoard(answer));
			this.client.close();
		
		}else{
			this.client.updateBoard(this.client.processBoard(answer));
			this.client.enableBoard();
		}
		timer.startMyTime();
	}
}
