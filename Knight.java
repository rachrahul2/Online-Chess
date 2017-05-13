
public class Knight extends Piece{
	
	public Knight(String color, int x, int y, Board b){
		super(color, x, y, b);
		if (color.equalsIgnoreCase("white"))
			this.name = "N";
		else
			this.name = "n";
	}
	
	public Boolean isValidMovement(int x, int y){
		//out of the board
		if ( x < 0 || x > 7 || y < 0 || y > 7 
				|| this.board.getPiece(x, y).getColor().equalsIgnoreCase(this.color))
			return false;
		
			if (this.posX + 2 == x && (this.posY - 1 == y || this.posY + 1 == y))
				return true;
			
			if (this.posX - 2 == x && (this.posY - 1 == y || this.posY + 1 == y))
				return true;
			
			if (this.posX + 1 == x && (this.posY - 2 == y || this.posY + 2 == y))
				return true;
			
			if (this.posX - 1 == x && (this.posY - 2 == y || this.posY + 2 == y))
				return true;
			
		return false;
	}
	
	public Boolean isKnight(){
		return true;
	}

}
