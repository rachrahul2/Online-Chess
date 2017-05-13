import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JOptionPane;

public class ListenerClient extends Thread{
	private BufferedReader in;
	private boolean haveMessage = false;
	private String message = "";
	private Client client;

	ListenerClient(BufferedReader input, Client c){
		in = input;
		this.client = c;
	}
	
	public void run(){
		String input;
		try {
			while ((input = in.readLine()) != null) {
				String[] array = input.split(" ");
				if (array[0].equalsIgnoreCase("invitationFrom")){
					System.out.println("Invitation from "+ array[1]);
					int response = JOptionPane.showConfirmDialog(null,"Do you want to play with "+ array[1]+ "?", 
							"Checks",JOptionPane.YES_NO_OPTION);
					if (response == 0){
						this.client.accept(array[1]);
					}else{
						this.client.refuse(array[1]);
					}
				}else if (array[0].equalsIgnoreCase("list")){
					String sms = "";
					if (array.length == 1)
						sms = "There are no other players";
					else
						for (int i = 1; i < array.length; i++)
							sms += array[i] + "\n";
						
					setMessage(sms);
					
				}else
					setMessage(input);
			    
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public synchronized String getMessage(){
		while(!this.haveMessage){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.haveMessage = false;
		notifyAll();
		return this.message;
	}
	
	public synchronized void setMessage(String sms){
		while(this.haveMessage){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.haveMessage = true;
		this.message = sms;
		notifyAll();
	}
}
