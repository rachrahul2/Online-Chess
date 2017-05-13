
public class Bishop extends Piece{
	
	public Bishop(String color, int x, int y, Board b){
		super(color, x, y, b);
		if (color.equalsIgnoreCase("white"))
			this.name = "B";
		else
			this.name = "b";
	}
	
	public Boolean isValidMovement(int x, int y){
		//out of the board
		if ( x < 0 || x > 7 || y < 0 || y > 7 
				|| this.board.getPiece(x, y).getColor().equalsIgnoreCase(this.color))
			return false;
		
		int distX = Math.abs(this.posX - x);
		int distY = Math.abs(this.posY - y);
		
		if (distX != distY)
			return false;
		
		if (this.posX > x && this.posY > y){
			for (int i = 1; i < distX ; i ++){
				if (!this.board.getPiece(this.posX - i, this.posY - i).isEmpty())
					return false;
			}
			return true;
		}
		
		if (this.posX < x && this.posY > y){
			for (int i = 1; i < distX ; i ++){
				if (!this.board.getPiece(this.posX + i, this.posY - i).isEmpty())
					return false;
			}
			return true;
		}
		
		if (this.posX > x && this.posY < y){
			for (int i = 1; i < distX ; i ++){
				if (!this.board.getPiece(this.posX - i, this.posY + i).isEmpty())
					return false;
			}
			return true;
		}
		
		if (this.posX < x && this.posY < y){
			for (int i = 1; i < distX ; i ++){
				if (!this.board.getPiece(this.posX + i, this.posY + i).isEmpty())
					return false;
			}
			return true;
		}
		
		return false;
	}
	
	public Boolean isBishop(){
		return true;
	}
	
}
