
public class Rook extends Piece{
	
	public Rook(String color, int x, int y, Board b){
		super(color, x, y, b);
		if (color.equalsIgnoreCase("white"))
			this.name = "R";
		else
			this.name = "r";
	}
	
	public Boolean isValidMovement(int x, int y){
		//out of the board
		if ( x < 0 || x > 7 || y < 0 || y > 7 
				|| this.board.getPiece(x, y).getColor().equalsIgnoreCase(this.color))
			return false;
		
		//move to bottom
		if (this.posX < x && this.posY == y){
			for (int i = 1; i < x - this.posX; i++){
				if (!board.getPiece(this.posX + i, y).isEmpty())
					return false;
			}
			return true;
		}
		
		//move to top
		if (this.posX > x && this.posY == y){
			for (int i = 1; i < this.posX - x; i++){
				if (!board.getPiece(this.posX - i, y).isEmpty())
					return false;
			}
			return true;
		}
		
		//move to right
		if (this.posX == x && this.posY < y){
			for (int i = 1; i < y - this.posY; i++){
				if (!board.getPiece(x,this.posY + i).isEmpty())
					return false;
			}
			return true;
		}
		
		//move to left
		if (this.posX == x && this.posY > y){
			for (int i = 1; i < this.posY - y ; i++){
				if (!board.getPiece(x, this.posY - i).isEmpty())
					return false;
			}
			return true;
		}
		
		return false;
	}
	
	public Boolean isRook(){
		return true;
	}

}
