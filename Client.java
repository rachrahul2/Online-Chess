import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;

public class Client extends JFrame implements ActionListener {
	private static Socket socket;
	private static String serverAddres;
	private static int serverPort;
	private static String color;
	private static String opponent;
	private PrintWriter out;
	private BufferedReader in;
	private BufferedReader stdIn;
	private ListenerClient listener;
	private Boolean myTurn = false;
	private Boolean firstTime = true;
	private String myUsername;
	private Time timer;
	
	private JTextField txtServerAddres, txtServerPort, txtUserName;
	private JTextField txtX1, txtY1, txtX2, txtY2;
	private JLabel lblMyTime, lblMyTotalTime, lblOpponentTime, lblOpponentTotalTime;
	private JButton btnconnect, btnSendUsername, btnSendInvitation, btnMove;
	private JFrame frame1, frame2, frame3, frame4;
	private JList list;
	private JLabel lblTurn;
	private DefaultListModel model;
	private ArrayList<JLabel> pieces = new ArrayList<JLabel>();
	private ArrayList<Rectangle> squareList = new ArrayList<Rectangle>();
	
	
	Client(){
		this.opponent = "";
		this.color = "";
		this.timer = new Time();
		showGUI();
	}
	
	private void showGUI(){
		//Create and set up the window.
        frame1 = new JFrame();
        frame1.setTitle("Chess");
		frame1.setLayout(null);
		frame1.setSize(300,130);
		frame1.setResizable(false);
		
		JLabel lblserverAddres = new JLabel("Server address:");
		lblserverAddres.setBounds(20,20,130,20);
		frame1.add(lblserverAddres);
	    
	    txtServerAddres = new JTextField("");
	    txtServerAddres.setBounds(120,20,155,20);
	    frame1.add(txtServerAddres);
	    
		
	    JLabel lblserverPort = new JLabel("Server port:");
	    lblserverPort.setBounds(20,60,130,20);
		frame1.add(lblserverPort);
	    
		txtServerPort = new JTextField("");
	    txtServerPort.setBounds(95,60,60,20);
	    frame1.add(txtServerPort);
	    
	    btnconnect = new JButton("Connect");
	    btnconnect.setBounds(170,55,100,30);
	    frame1.add(btnconnect);
	    btnconnect.addActionListener(this);
	    
        //Display the window.
	    frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  	frame1.setLocationRelativeTo(null);
	  	frame1.setVisible(true);
	}
	
	private void showGUIUserName(){
		//Create and set up the window.
        frame2 = new JFrame();
        frame2.setTitle("Chess");
		frame2.setLayout(null);
		frame2.setSize(300,130);
		frame2.setResizable(false);
		
		JLabel lbluser = new JLabel("Username:");
		lbluser.setBounds(20,20,130,20);
		frame2.add(lbluser);
	    
	    txtUserName = new JTextField("");
	    txtUserName.setBounds(120,20,155,20);
	    frame2.add(txtUserName);
	    
	    
	    btnSendUsername = new JButton("Send");
	    btnSendUsername.setBounds(170,55,100,30);
	    frame2.add(btnSendUsername);
	    btnSendUsername.addActionListener(this);
	    
        //Display the window.
	    frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  	frame2.setLocationRelativeTo(null);
	  	frame2.setVisible(true);
	}
	
	private void showGUIOpponent(){
		//Create and set up the window.
        frame3 = new JFrame();
        frame3.setTitle("Chess");
		frame3.setLayout(null);
		frame3.setSize(250,320);
		frame3.setResizable(false);
	    
		JLabel lblPlayers = new JLabel("List of players:");
		lblPlayers.setBounds(20,15,200,20);
		frame3.add(lblPlayers);
		
		model = new DefaultListModel();
		list = new JList(model);
		
		updateList();
			
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setBounds(20, 40, 200, 200);
		frame3.add(listScroller);
	    
	    btnSendInvitation = new JButton("Invite");
	    btnSendInvitation.setBounds(150,250,70,30);
	    frame3.add(btnSendInvitation);
	    btnSendInvitation.addActionListener(this);
	    
        //Display the window.
	    frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  	frame3.setLocationRelativeTo(null);
	  	frame3.setVisible(true);
	}
	
	private JLabel getPiece(int i, int j, String url){
		
		ImageIcon imagen = new ImageIcon(url);
		JLabel Img = new JLabel();
		Img.setBounds(j*60+15 ,i*60+19,30,40);
		Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(Img.getWidth(), Img.getHeight(), Image.SCALE_DEFAULT));
		Img.setIcon(icono);
		
		return Img;
	}
	
	private JLabel getImg(int i, int j, String piece){
		switch(piece){
			case "p":
				return getPiece(i,j,"img/blackPawn.jpg");
			case "P":
				return getPiece(i,j,"img/whitePawn.jpg");
			
			case "r":
				return getPiece(i,j,"img/blackRook.jpg");
			case "R":
				return getPiece(i,j,"img/whiteRook.jpg");
			
			case "n":
				return getPiece(i,j,"img/blackKnight.jpg");
			case "N":
				return getPiece(i,j,"img/whiteKnight.jpg");
				
			case "b":
				return getPiece(i,j,"img/blackBishop.jpg");
			case "B":
				return getPiece(i,j,"img/whiteBishop.jpg");
				
			case "q":
				return getPiece(i,j,"img/blackQueen.jpg");
			case "Q":
				return getPiece(i,j,"img/whitequeen.jpg");
				
			case "k":
				return getPiece(i,j,"img/blackKing.jpg");
			case "K":
				return getPiece(i,j,"img/whiteKing.jpg");
		}
		
		return null;
	}

	
	private void showGUIBoard(String myColor, String brd){
		//Create and set up the window.
        frame4 = new JFrame();
        frame4.setTitle("Chess "+ this.myUsername);
		frame4.setLayout(null);
		frame4.setSize(820,650);
		String up;
		
		if (myColor.equalsIgnoreCase("white")){
			up = "a \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t b";
			up += " \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t c";
			up += " \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t  d";
			up += " \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t  e";
			up += " \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t  f";
			up += " \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t  g";
			up += " \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t  h";
		}else{
			up = "h \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t g";
			up += " \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t f";
			up += " \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t e";
			up += " \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t d";
			up += " \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t c";
			up += " \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t b";
			up += " \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t a";
		}
		
		JLabel lbl1 = new JLabel(up);
		lbl1.setBounds(90,30,500,20);
		frame4.add(lbl1);
		
		JLabel lbl2 = new JLabel(up);
		lbl2.setBounds(90,550,500,20);
		frame4.add(lbl2);
		
		for (int i = 1; i < 9; i ++){
			JLabel lbl3;
			if (myColor.equalsIgnoreCase("white"))
				lbl3 = new JLabel(String.valueOf(9-i));
			else
				lbl3 = new JLabel(String.valueOf(i));
			lbl3.setBounds(35,i*60+20,20,20);
			frame4.add(lbl3);
			
			JLabel lbl4;
			if (myColor.equalsIgnoreCase("white"))
				lbl4 = new JLabel(String.valueOf(9-i));
			else
				lbl4 = new JLabel(String.valueOf(i));
			lbl4.setBounds(560,i*60+20,20,20);
			frame4.add(lbl4);
		
		}
		
		System.out.println(brd);
		String[] board = brd.split(" ");
		Color c = Color.white;
		for (int i = 1; i < 9; i++){
			for (int j = 1; j < 9; j++){
				
				Rectangle d = new Rectangle(c);
				d.setBounds(j * 60, i * 60, 60, 60);
				
				if (!board[(i-1)*8+j-1].equalsIgnoreCase(".")){
					JLabel Img = getImg(i,j,board[(i-1)*8+j-1]);
					this.pieces.add(Img);
					frame4.add(Img);
				}
			
				this.squareList.add(d);
				frame4.add(d);
				
				if (c.equals(Color.white))
					c = Color.black;
				else
					c = Color.white;
			}
			if (c.equals(Color.white))
				c = Color.black;
			else
				c = Color.white;
		}
		 
		
		JLabel lbl5 = new JLabel("Initial position");
		lbl5.setBounds(600,50,130,20);
		frame4.add(lbl5);
		
		JLabel lbl6 = new JLabel("Letter");
		lbl6.setBounds(600,80,40,20);
		frame4.add(lbl6);
	    
	    txtX1 = new JTextField("");
	    txtX1.setBounds(640,80,20,20);
	    frame4.add(txtX1);
		
	    JLabel lbl7 = new JLabel("Number");
		lbl7.setBounds(690,80,60,20);
		frame4.add(lbl7);
	    
	    txtY1 = new JTextField("");
	    txtY1.setBounds(740,80,20,20);
	    frame4.add(txtY1);
	    
	    JLabel lbl8 = new JLabel("Final position");
		lbl8.setBounds(600,130,130,20);
		frame4.add(lbl8);
	    
	    JLabel lbl9 = new JLabel("Letter");
		lbl9.setBounds(600,160,40,20);
		frame4.add(lbl9);
	    
	    txtX2 = new JTextField("");
	    txtX2.setBounds(640,160,20,20);
	    frame4.add(txtX2);
		
	    JLabel lbl10 = new JLabel("Number");
		lbl10.setBounds(690,160,60,20);
		frame4.add(lbl10);
	    
	    txtY2 = new JTextField("");
	    txtY2.setBounds(740,160,20,20);
	    frame4.add(txtY2);
	    
	    btnMove = new JButton("Move");
	    btnMove.setBounds(690,220,70,30);
	    frame4.add(btnMove);
	    btnMove.addActionListener(this);
	    
	    lblTurn = new JLabel("Wait your turn ...");
	    lblTurn.setBounds(680,250, 100, 30);
	    lblTurn.setVisible(false);
	    frame4.add(lblTurn);
	    
	    JLabel lblt1 = new JLabel("My time");
	    lblt1.setBounds(600, 300, 200, 20);
	    frame4.add(lblt1);
	    
	    lblMyTime = new JLabel("Time of the last movement: 0:0:0");
	    lblMyTime.setBounds(600, 330, 200, 20);
	    frame4.add(lblMyTime);
	    
	    lblMyTotalTime = new JLabel("Total time: 0:0:0");
	    lblMyTotalTime.setBounds(600, 360, 200, 20);
	    frame4.add(lblMyTotalTime);
	    
	    JLabel lblt2 = new JLabel("Time of "+ opponent);
	    lblt2.setBounds(600, 400, 200, 20);
	    frame4.add(lblt2);
	    	
	    lblOpponentTime = new JLabel("Time since the last movement: 0:0:0");
	    lblOpponentTime.setBounds(600, 430, 200, 20);
	    frame4.add(lblOpponentTime);
	    
	    lblOpponentTotalTime = new JLabel("Total time: 0:0:0");
	    lblOpponentTotalTime.setBounds(600, 460, 200, 20);
	    frame4.add(lblOpponentTotalTime);
	    
	    if (!this.myTurn){
	    	txtX1.setEnabled(false);
	    	txtY1.setEnabled(false);
	    	txtX2.setEnabled(false);
	    	txtY2.setEnabled(false);
	    	lblTurn.setVisible(true);
	    	btnMove.setEnabled(false);
	    }
	    
		//Display the window.
	    frame4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  	frame4.setLocationRelativeTo(null);
	  	frame4.setVisible(true);
	}
	
	
	private void updateList(){
		out.println("getPlayers");
		String answer = listener.getMessage();
		model.removeAllElements();
		if(!answer.equalsIgnoreCase("There are no other players")){
			String[] array = answer.split("\n");
			for (int i = 0; i < array.length; i++)
				model.addElement(array[i]);
		}
	} 
	
	public void updateBoard(String brd){
		String[] board = brd.split(" ");
		
		Iterator<JLabel> iterator = this.pieces.iterator();
		while (iterator.hasNext())
			frame4.remove(iterator.next());
		
		Iterator<Rectangle> iter = this.squareList.iterator();
		while (iter.hasNext())
			frame4.remove(iter.next());
		
		this.pieces.clear();
		this.squareList.clear();
		
		Color c = Color.white;
		for (int i = 1; i < 9; i++){
			for (int j = 1; j < 9; j++){
				Rectangle d = new Rectangle(c);
				d.setBounds(j * 60, i * 60, 60, 60);
				
				if (!board[(i-1)*8+j-1].equalsIgnoreCase(".")){
					JLabel Img = getImg(i,j,board[(i-1)*8+j-1]);
					this.pieces.add(Img);
					frame4.add(Img);
				}
				this.squareList.add(d);
				frame4.add(d);
				
				if (c.equals(Color.white))
					c = Color.black;
				else
					c = Color.white;
			}
			if (c.equals(Color.white))
				c = Color.black;
			else
				c = Color.white;
		}
		frame4.repaint(); 
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnconnect){
			if ( txtServerAddres.getText().length() == 0 || 
				 txtServerPort.getText().length() == 0 ||
				 txtServerAddres.getText().replaceAll(" ", "").length() == 0 ||
				 txtServerPort.getText().replaceAll(" ", "").length() == 0){
				JOptionPane.showMessageDialog(null,"You must fill all fields", 
						"warning",JOptionPane.WARNING_MESSAGE);
			}else{
				
				serverAddres = txtServerAddres.getText().replaceAll(" ", "");
				serverPort = Integer.parseInt(txtServerPort.getText().replaceAll(" ", ""));
				try {
					socket = new Socket(serverAddres, serverPort);
					out = new PrintWriter(socket.getOutputStream(), true);
					in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					stdIn = new BufferedReader(new InputStreamReader(System.in));
					frame1.setVisible(false);
					showGUIUserName();
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null,"Invalid server address or port", 
							"warning",JOptionPane.WARNING_MESSAGE);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null,"Socket error", 
							"warning",JOptionPane.WARNING_MESSAGE);
					e1.printStackTrace();
				}
				
			}
		}else if (e.getSource() == btnSendUsername){
			if ( txtUserName.getText().length() == 0 || 
				 txtUserName.getText().replaceAll(" ", "").length() == 0 ){
					JOptionPane.showMessageDialog(null,"Fill your username", 
							"warning",JOptionPane.WARNING_MESSAGE);
				}else{
					chooseUserName(txtUserName.getText().replaceAll(" ", ""));
				}
		
		}else if (e.getSource() == btnSendInvitation){
			if (list.getSelectedIndex() == -1){
				JOptionPane.showMessageDialog(null,"Choose a player", 
						"warning",JOptionPane.WARNING_MESSAGE);
			}else{
				chooseOpponent(list.getSelectedValue().toString());
			}
		}else if (e.getSource() == btnMove){
			if (txtX1.getText().length() == 0 || txtX1.getText().replaceAll(" ", "").length() == 0 ||
				txtY1.getText().length() == 0 || txtY1.getText().replaceAll(" ", "").length() == 0 ||
				txtX2.getText().length() == 0 || txtX2.getText().replaceAll(" ", "").length() == 0 ||
				txtY2.getText().length() == 0 || txtY2.getText().replaceAll(" ", "").length() == 0 ){
						JOptionPane.showMessageDialog(null,"Fill all the fields", 
								"warning",JOptionPane.WARNING_MESSAGE);
			}else{
			
				int x1, y1, x2, y2;
				y1 = convertColumn(txtX1.getText());
				x1 = convertRow(txtY1.getText());
				y2 = convertColumn(txtX2.getText());
				x2 = convertRow(txtY2.getText());
				if ( x1 < 0 || x1 > 7 || y1 < 0 || y1 > 7 
					||  x2 < 0 || x2 > 7 || y2 < 0 || y2 > 7 )
					JOptionPane.showMessageDialog(null,"Invalid input", 
							"warning",JOptionPane.WARNING_MESSAGE);
				else{
					txtX1.setText("");
					txtY1.setText("");
					txtX2.setText("");
					txtY2.setText("");
					
					
					out.println("move " + x1 + " " + y1 + " " + x2 + " " + y2);
					String answer = listener.getMessage();
					if (answer.equalsIgnoreCase("youwin")){
						timer.endMyTime();
						refreshMyTime();
						timer.startOpponentTime();
						disableBoard();
						String msj = "Congratulations! You win!";
						JOptionPane.showMessageDialog(null,msj, 
								"Congratulations",JOptionPane.INFORMATION_MESSAGE);
						
						System.out.println(answer);
						answer = listener.getMessage();
						updateBoard(processBoard(answer));
						close();
					
					}else if (answer.equalsIgnoreCase("invalid movement")){
						JOptionPane.showMessageDialog(null,"Invalid movement", 
								"warning",JOptionPane.WARNING_MESSAGE);
					}else{
						timer.endMyTime();
						refreshMyTime();
						timer.startOpponentTime();
						updateBoard(processBoard(answer));
						disableBoard();
						lblTurn.setVisible(true);
						new ListenerOtherPlayer(listener, this, timer).start();;
						
					}
					
					
				}
			}
		}
	}
	
	public void close(){
		try {
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void disableBoard(){
		txtX1.setEnabled(false);
    	txtY1.setEnabled(false);
    	txtX2.setEnabled(false);
    	txtY2.setEnabled(false);
    	//lblTurn.setVisible(true);
    	btnMove.setEnabled(false);
    	frame4.repaint();
	}
	
	public void enableBoard(){
		txtX1.setEnabled(true);
    	txtY1.setEnabled(true);
    	txtX2.setEnabled(true);
    	txtY2.setEnabled(true);
    	lblTurn.setVisible(false);
    	btnMove.setEnabled(true);
    	frame4.repaint();
	}
	
	public void refreshMyTime(){
		lblMyTime.setText("Time of the last movement: "+timer.getMyTime());
		lblMyTotalTime.setText("Total time: "+timer.getMyTotalTime());
	}
	
	public void refreshOpponentTime(){
		lblOpponentTime.setText("Time of the last movement: "+timer.getOpponetTime());
		lblOpponentTotalTime.setText("Total time: "+timer.getOpponentTotalTime());
	}
	
	private void chooseUserName(String userInput){
		String answer;
		this.myUsername = userInput;
			
		try {
			out.println("username "+userInput);
			answer = in.readLine();
			if (answer.equalsIgnoreCase("invalid username")){
				JOptionPane.showMessageDialog(null,"Invalid username", 
						"warning",JOptionPane.WARNING_MESSAGE);
			
			}else{
				frame2.setVisible(false);
				this.listener = new ListenerClient(this.in, this);
				this.listener.start();
				showGUIOpponent();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void chooseOpponent(String opponent){
		String userInput;
		String answer;
				
		out.println("invite " + opponent);
		answer = listener.getMessage();
		System.out.println(answer);
		
		if (answer.equalsIgnoreCase("invalid username") ||
				answer.equalsIgnoreCase("wait the answer of the other invitation")){
			JOptionPane.showMessageDialog(null,"Invalid username", 
				"warning",JOptionPane.WARNING_MESSAGE);
			String[] array = listener.getMessage().split("\n");
			updateList();

		}else {
			
			String[] arrayServer = answer.split(" ");
			if (arrayServer[0].equalsIgnoreCase("refuse")){
				JOptionPane.showMessageDialog(null,opponent + " refuse your invitation", 
						"Refuse",JOptionPane.INFORMATION_MESSAGE);
				list.clearSelection();
				updateList();
				
			}else if (arrayServer[0].equalsIgnoreCase("accept")){
				JOptionPane.showMessageDialog(null,opponent + " accept your invitation", 
						"Accept",JOptionPane.INFORMATION_MESSAGE);
				this.opponent = arrayServer[1];
				frame3.setVisible(false);
				chooseColor();
				
			}
		}
	}
	
	public void accept(String user){
		out.println("accept " + user);
		String answer;
		try {
			answer = in.readLine();
			System.out.println(answer);
			if (answer.equalsIgnoreCase("confirm")){
				this.opponent = user;
				frame3.setVisible(false);
				new ColorThread(this.listener, this).start();
			}else{
				JOptionPane.showMessageDialog(null,opponent + "You responded too late, choose another player", 
						"Ops",JOptionPane.INFORMATION_MESSAGE);
				refreshList();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void refreshList(){
		out.println("getPlayers");
		try {
			String answer = in.readLine();
			String[] array = answer.split(" ");
			model.removeAllElements();
			if (array[0].equalsIgnoreCase("list"))
				for (int i = 1; i < array.length; i++)
					model.addElement(array[i]);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void refuse(String user){
		System.out.println("Acepté la invitación de ");
		out.println("refuse " + user);
		String answer;
		try {
			answer = in.readLine();
			System.out.println(answer);
			refreshList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void chooseColor(){
		String userColor = "";
		
		Object[] options = {"White", "Black", "Toss a coin"};
		int n = JOptionPane.showOptionDialog(null, "Choose color white, black or whatever",
				"Choose color "+this.myUsername, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, options, 	options[2]);

		switch (n){
		case 0:
			userColor = "white";
			break;
		case 1:
			userColor = "black";
			break;
		case 2:
			userColor = "whatever";
			break;
		}
		
		this.out.println("color " + userColor);
		String answer = listener.getMessage();
		System.out.println(answer);
		if (answer.equalsIgnoreCase("the other player choosed the same color")){
			JOptionPane.showMessageDialog(null,"The other player choosed the same color", 
					"Ops "+this.myUsername,JOptionPane.INFORMATION_MESSAGE);
			chooseColor();
		}else{
			this.color = answer;
			System.out.println("My Color "+this.color);
			if (this.color.equalsIgnoreCase("white"))
				this.myTurn = true;
			
			answer = listener.getMessage();
			showGUIBoard(this.color, processBoard(answer));
			if (this.myTurn)
				timer.startMyTime();
			else{
				timer.startOpponentTime();
				new ListenerOtherPlayer(this.listener, this, timer).start();
			}
		}
	}
	
	private int convertColumn(String column){
		switch(column){
			case "a":
			case "A":
				return 0;
			case "b":
			case "B":
				return 1;
			case "c":
			case "C":
				return 2;
			case "d":
			case "D":
				return 3;
			case "e":
			case "E":
				return 4;
			case "f":
			case "F":
				return 5;
			case "g":
			case "G":
				return 6;
			case "h":
			case "H":
				return 7;
			default:
				return -1;
		}
	}
	
	private int convertRow(String row){
		switch(row){
			case "1":
				return 7;
			case "2":
				return 6;
			case "3":
				return 5;
			case "4":
				return 4;
			case "5":
				return 3;
			case "6":
				return 2;
			case "7":
				return 1;
			case "8":
				return 0;
			default:
				return -1;
		}
	}
	
	private void sendMovement(){
		String userInput;
		System.out.println("Your turn:");
		int x1 = -1, x2, y1, y2;
		
		while( x1 == -1 ){
			try {
				userInput = stdIn.readLine();
				String[] args = userInput.split(" ");
				if (args.length != 4){
					System.out.println("Invalid input");
				
				}else{
					y1 = convertColumn(args[0]);
					x1 = convertRow(args[1]);
					y2 = convertColumn(args[2]);
					x2 = convertRow(args[3]);
					if ( x1 < 0 || x1 > 7 || y1 < 0 || y1 > 7 
						||  x2 < 0 || x2 > 7 || y2 < 0 || y2 > 7 ){
						System.out.println("Invalid input");
						x1 = -1;
					}else{
						out.println("move " + x1 + " " + y1 + " " + x2 + " " + y2);
					}
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void print(String answer){
		String result = answer;
		if (!answer.equalsIgnoreCase("invalid movement")){
			result = "";
			String[] array = answer.split("-");
			for (int i = 1; i < array.length - 1 ; i++)
				result += array[i].substring(2, 11); 
		}

		System.out.println(result);		
	}
	
	public String processBoard(String answer){
		String result = "";
		String[] array = answer.split("-");
		for (int i = 1; i < array.length - 1 ; i++){
			result += array[i].substring(2, 18);
		}
		
		return result;
	}
	
	private void startGame(){
		System.out.println("Start the game.");
		String answer;
		Boolean valid = false;
		
		
		if (this.color.equalsIgnoreCase("white"))
			myTurn = true;
		
		while(true){
			if (myTurn){
				answer = listener.getMessage();
				if (answer.equalsIgnoreCase("youlose") || answer.equalsIgnoreCase("youwin")){
					System.out.println(answer);
					answer = listener.getMessage();
					print(answer);
					return;
				}
				print(answer);
				while (!valid){
					sendMovement();
					answer = listener.getMessage();
					if (answer.equalsIgnoreCase("youlose") || answer.equalsIgnoreCase("youwin")){
						System.out.println(answer);
						answer = listener.getMessage();
						print(answer);
						return;
					}
					print(answer);
					if (!answer.equalsIgnoreCase("invalid movement")){
						myTurn = false;
						valid = true;
					}
				}
				valid = false;
				firstTime = false;
			}else{
				System.out.println("Wait");
				if (firstTime){
					answer = listener.getMessage();
					print(answer);
					firstTime = false;
				}
				myTurn = true;
			}
		}
	}
	
	public static void main(String[] args) {
		new Client();
	}
	
}
