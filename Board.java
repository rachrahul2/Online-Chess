
public class Board {
	private Piece[][] board;
	private King whiteKing;
	private King blackKing;
	
	Board(){
		this.board = new Piece[8][8];
		initBoard();
	}
	
	private void initBoard(){
		//put the rooks
		this.board[0][0] = new Rook("black", 0, 0, this);
		this.board[0][7] = new Rook("black", 0, 7, this);
		this.board[7][0] = new Rook("white", 7, 0, this);
		this.board[7][7] = new Rook("white", 7, 7, this);
		
		//put the knights
		this.board[0][1] = new Knight("black", 0, 1, this);
		this.board[0][6] = new Knight("black", 0, 6, this);
		this.board[7][1] = new Knight("white", 7, 1, this);
		this.board[7][6] = new Knight("white", 7, 6, this);
		
		//put the bishops
		this.board[0][2] = new Bishop("black", 0, 2, this);
		this.board[0][5] = new Bishop("black", 0, 5, this);
		this.board[7][2] = new Bishop("white", 7, 2, this);
		this.board[7][5] = new Bishop("white", 7, 5, this);
		
		//put the queens
		this.board[0][3] = new Queen("black", 0, 3, this);
		this.board[7][3] = new Queen("white", 7, 3, this);
		
		//put the kings
		this.blackKing = new King("black", 0, 4, this);
		this.board[0][4] = this.blackKing;
		this.whiteKing = new King("white", 7, 4, this);
		this.board[7][4] = this.whiteKing;
		
		//put the pawns
		for (int i = 0; i < 8 ; i++){
			this.board[1][i] = new Pawn("black", 1, i, this);
			this.board[6][i] = new Pawn("white", 6, i, this);
		}
		
		//put the empty
		for (int i = 2; i < 6; i ++)
			for (int j = 0; j < 8; j++)
				this.board[i][j] = new Empty(i,j, this); 
		
	}
	
	public Boolean isValidMovement(int x1, int y1, int x2, int y2, String playerColor){
		if (!this.board[x1][y1].getColor().equalsIgnoreCase(playerColor)){
			System.out.println("It's not your piece");
			return false;
		}
		
		return this.board[x1][y1].isValidMovement(x2, y2);
	}
	
	public void move(int x1, int y1, int x2, int y2){	
		Piece p = this.board[x1][y1];
		p.move(x2, y2);
		this.board[x2][y2] = p;
		this.board[x1][y1] = new Empty(x1, y1, this); 
	}
	
	public String getGameState(String color){
		King k;
		String result = "";
		if (color.equalsIgnoreCase("white"))
			k = this.blackKing;
		else
			k = this.whiteKing;
		
		if (!k.canMove() && k.isBeingAttack())
			result = "You win";
		else
			result = "continue";
			
		return result;
		
	}
	
	public Piece getPiece(int x, int y){
		if ( x < 0 || x > 7 || y < 0 || y > 7 )
			return null;
		
		return board[x][y];
	}
	
	public void removePiece(int x, int y){
		board[x][y] = new Empty(x, y, this);
	}
	
	public void putPiece(int x, int y, Piece p){
		board[x][y] = p;
		
	}
	
	public String toString(String color){
		String result;
		if (color.equalsIgnoreCase("white")){
			result = "  a b c d e f g h-";
			
			for (int i = 0; i < 8; i++){
				result += (8 - i) + " ";
				for (int j = 0; j < 8; j++)
					result += this.board[i][j].toString() + " ";
				
				result += (8 - i) + "-";
				
			} 
			result += "  a b c d e f g h";
		}else{
			result = "  h g f e d c b a-";
			for (int i = 7; i >= 0; i--){
				result += (8 - i) + " ";
				for (int j = 7; j >= 0; j--)
					result += this.board[i][j].toString() + " ";
				
				result += (8 - i) + "-";
			}
			result += "  h g f e d c b a";
		}
		return result;
	}
	
	public static void main (String[] argv){
		new Board().toString("black");
		
	}
}
