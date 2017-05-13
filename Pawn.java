
public class Pawn extends Piece{
	
	public Pawn(String color, int x, int y, Board b){
		super(color, x, y, b);
		if (color.equalsIgnoreCase("white"))
			this.name = "P";
		else
			this.name = "p";
	}
	
	public Boolean isValidMovement(int x, int y){
		//out of the board
		if ( x < 0 || x > 7 || y < 0 || y > 7)
			return false;
		
		//White pawn
		String opponentColor = "black";
		int direction = -1;
		int initialPoint = 6;
		int endPoint = 4;
			
		//Black pawn
		if (this.color.equalsIgnoreCase("black")){
			opponentColor = "white";
			direction = 1;
			initialPoint = 1;
			endPoint = 3;
		}
		
		// move straight forward one square
		if (this.posY == y && this.posX + direction == x
				&& this.board.getPiece(x, y).isEmpty())
			return true;
			
		// move two squares straight forward
		else if (this.posY == y && this.posX == initialPoint 
				&& x == endPoint && this.board.getPiece(x, y).isEmpty())
			return true;
		
		// capture an enemy piece in the square diagonally in front
		else if (this.posY + 1 == y && this.posX + direction == x 
				&& !this.board.getPiece(x, y).isEmpty() 
				&& this.board.getPiece(x, y).getColor().equalsIgnoreCase(opponentColor))
			return true;
		
		// capture an enemy piece in the square diagonally in front
		else if (this.posY - 1 == y && this.posX + direction == x  
				&& !this.board.getPiece(x, y).isEmpty() 
				&& this.board.getPiece(x, y).getColor().equalsIgnoreCase(opponentColor))
			return true;
		
		return false;
	}
	
	public Boolean isPawn(){
		return true;
	}
}
