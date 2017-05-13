import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketImpl;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Server extends JFrame implements ActionListener {
	private int portNumber;
	private ServerSocket socket;
	private Players players;
	private JFrame frame;
	private JTextField txtServerPort;
	private JButton btnStart;
	
	Server(){
		System.out.println("Server init");
		this.players = new Players();
		showGUI();
	}
	
	private void showGUI(){
		//Create and set up the window.
        frame = new JFrame();
        frame.setTitle("Chess-Server");
		frame.setLayout(null);
		frame.setSize(210,130);
		frame.setResizable(false);
		
	    JLabel lblserverPort = new JLabel("Server Port:");
	    lblserverPort.setBounds(30,20,130,20);
		frame.add(lblserverPort);
	    
		txtServerPort = new JTextField("");
	    txtServerPort.setBounds(110,20,60,20);
	    frame.add(txtServerPort);
	    
	    btnStart = new JButton("Start");
	    btnStart.setBounds(50,55,100,30);
	    frame.add(btnStart);
	    btnStart.addActionListener(this);
	    
        //Display the window.
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  	frame.setLocationRelativeTo(null);
	  	frame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnStart){
			if ( txtServerPort.getText().length() == 0 || 
				 txtServerPort.getText().replaceAll(" ", "").length() == 0) {
				JOptionPane.showMessageDialog(null,"You must fill the port", 
						"warning",JOptionPane.WARNING_MESSAGE);
			}else{
				try {
					portNumber = Integer.parseInt(txtServerPort.getText());
					socket = new ServerSocket(portNumber);
					Socket clientSocket;
					frame.setVisible(false);
					while ( (clientSocket = socket.accept()) != null)
						(new ServerThread(clientSocket, players)).start();
					
				}catch (IOException e1) {
					JOptionPane.showMessageDialog(null,"Socket error", 
							"warning",JOptionPane.WARNING_MESSAGE);
					e1.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		new Server();
	    
	}

}
