
public class King extends Piece{
	
	public King(String color, int x, int y, Board b){
		super(color, x, y, b);
		if (color.equalsIgnoreCase("white"))
			this.name = "K";
		else
			this.name = "k";
	}
	
	public Boolean isValidMovement(int x, int y){
		//out of the board
		if ( x < 0 || x > 7 || y < 0 || y > 7 
				|| this.board.getPiece(x, y).getColor().equalsIgnoreCase(this.color))
			return false;
		
		if (((this.posX + 1 == x && this.posY == y)			//move to bottom
				|| (this.posX - 1 == x && this.posY == y)	//move to top
				|| (this.posX == x && this.posY + 1 == y)	//move to right 
				|| (this.posX == x && this.posY - 1 == y))	//move to left
				&& !isUnderAttack(x,y))
			return true;
		
		if (((this.posX + 1 == x && this.posY + 1 == y)
				|| (this.posX + 1 == x && this.posY - 1 == y)
				|| (this.posX - 1 == x && this.posY + 1 == y)
				|| (this.posX - 1 == x && this.posY - 1 == y))
			&& !isUnderAttack(x,y))
			return true;
		
		return false;
	}
	
	private Boolean isUnderAttack(int x, int y){
		this.board.removePiece(this.posX, this.posY);
		
		if (canAttackPawn(x,y)){
			this.board.putPiece(this.posX, this.posY, this);
			return true;
		}
		
		if (canAttackKnight(x,y)){
			this.board.putPiece(this.posX, this.posY, this);
			return true;
		}
		
		if (canAttackRook(x,y)){
			this.board.putPiece(this.posX, this.posY, this);
			return true;
		}
					
		if (canAttackBishop(x, y)){
			this.board.putPiece(this.posX, this.posY, this);
			return true;
		}
		
		if (canAttackQueen(x, y)){
			this.board.putPiece(this.posX, this.posY, this);
			return true;
		}
		
		if (canAttackKing(x, y)){
			this.board.putPiece(this.posX, this.posY, this);
			return true;
		}
		
		this.board.putPiece(this.posX, this.posY, this);
		return false;
	}
	
	private boolean canAttackPawn(int x, int y){
		//Check black pawn
		if (this.color.equalsIgnoreCase("white") && this.board.getPiece(x - 1, y + 1) != null &&
			this.board.getPiece(x - 1, y + 1).isPawn() &&
			this.board.getPiece(x - 1, y + 1).getColor().equalsIgnoreCase("black")){
			return true;
		}
		
		//Check black pawn
		if (this.color.equalsIgnoreCase("white") &&  this.board.getPiece(x - 1, y - 1) != null &&
			this.board.getPiece(x - 1, y - 1).isPawn() &&
			this.board.getPiece(x - 1, y - 1).getColor().equalsIgnoreCase("black"))
				return true;
		
		//Check white pawn
		if (this.color.equalsIgnoreCase("black") &&  this.board.getPiece(x + 1, y + 1) != null &&
			this.board.getPiece(x + 1, y + 1).isPawn() &&
			this.board.getPiece(x + 1, y + 1).getColor().equalsIgnoreCase("white"))
				return true;
		
		//Check white pawn
		if (this.color.equalsIgnoreCase("black") && this.board.getPiece(x + 1, y - 1) != null &&
			this.board.getPiece(x + 1, y - 1).isPawn() &&
			this.board.getPiece(x + 1, y - 1).getColor().equalsIgnoreCase("white"))
				return true;
		
		return false;
	}
	
	private boolean canAttackKnight(int x, int y){
		
		if (this.board.getPiece(x + 1, y + 2) != null && 
			this.board.getPiece(x + 1, y + 2).isKnight() &&
			!this.board.getPiece(x + 1, y + 2).getColor().equalsIgnoreCase(this.color))
			return true;
		
		if (this.board.getPiece(x + 1, y - 2) != null && 
			this.board.getPiece(x + 1, y - 2).isKnight() &&
			!this.board.getPiece(x + 1, y - 2).getColor().equalsIgnoreCase(this.color))
			return true;
		
		if (this.board.getPiece(x - 1, y + 2) != null && 
			this.board.getPiece(x - 1, y + 2).isKnight() &&
			!this.board.getPiece(x - 1, y + 2).getColor().equalsIgnoreCase(this.color))
			return true;
					
		if (this.board.getPiece(x - 1, y - 2) != null && 
			this.board.getPiece(x - 1, y - 2).isKnight() &&
			!this.board.getPiece(x - 1, y - 2).getColor().equalsIgnoreCase(this.color))
			return true;
		
		if (this.board.getPiece(x + 2, y + 1) != null && 
			this.board.getPiece(x + 2, y + 1).isKnight() &&
			!this.board.getPiece(x + 2, y + 1).getColor().equalsIgnoreCase(this.color))
			return true;
		
		if (this.board.getPiece(x + 2, y - 1) != null && 
			this.board.getPiece(x + 2, y - 1).isKnight() &&
			!this.board.getPiece(x + 2, y - 1).getColor().equalsIgnoreCase(this.color))
			return true;
		
		if (this.board.getPiece(x - 2, y + 1) != null && 
			this.board.getPiece(x - 2, y + 1).isKnight() &&
			!this.board.getPiece(x - 2, y + 1).getColor().equalsIgnoreCase(this.color))
			return true;
			
		if (this.board.getPiece(x - 2, y - 1) != null && 
			this.board.getPiece(x - 2, y - 1).isKnight() &&
			!this.board.getPiece(x - 2, y - 1).getColor().equalsIgnoreCase(this.color))
			return true;

		return false;
	}
	
	private boolean canAttackRook(int x, int y){
		
		//Rook in right side
		for (int i = y + 1; i < 8; i++)
			if (this.board.getPiece(x, i).isRook() &&
				!this.board.getPiece(x, i).getColor().equalsIgnoreCase(this.color))
				return true;
			else if (this.board.getPiece(x, i).getColor().equalsIgnoreCase(this.color))
				break;
		
		//Rook in left side
		for (int i = y - 1; i >= 0 ; i--)
			if (this.board.getPiece(x, i).isRook() &&
				!this.board.getPiece(x, i).getColor().equalsIgnoreCase(this.color))
				return true;
			else if (this.board.getPiece(x, i).getColor().equalsIgnoreCase(this.color))
				break;
		
		//Rook in top side
		for (int i = x - 1; i >= 0 ; i--)
			if (this.board.getPiece(i, y).isRook() &&
				!this.board.getPiece(i, y).getColor().equalsIgnoreCase(this.color))
				return true;
			else if (this.board.getPiece(i, y).getColor().equalsIgnoreCase(this.color))
				break;
		
		//Rook in bottom side
		for (int i = x + 1; i < 8 ; i++)
			if (this.board.getPiece(i, y).isRook() &&
				!this.board.getPiece(i, y).getColor().equalsIgnoreCase(this.color))
				return true;
			else if (this.board.getPiece(i, y).getColor().equalsIgnoreCase(this.color))
				break;
		
		return false;
	}
	
	private boolean canAttackBishop(int x, int y){
		
		for (int i = x - 1, j = y - 1;  i >= 0 && j >= 0 ; i--, j--)
			if (this.board.getPiece(i, j).isBishop() &&
				!this.board.getPiece(i, j).getColor().equalsIgnoreCase(this.color))
				return true;
			else if (this.board.getPiece(i, j).getColor().equalsIgnoreCase(this.color))
				break;
		
		for (int i = x - 1, j = y + 1;  i >= 0 && j < 8 ; i--, j++)
			if (this.board.getPiece(i, j).isBishop() &&
				!this.board.getPiece(i, j).getColor().equalsIgnoreCase(this.color))
				return true;
			else if (this.board.getPiece(i, j).getColor().equalsIgnoreCase(this.color))
				break;
			
		for (int i = x + 1, j = y + 1;  i < 8 && j < 8 ; i++, j++)
			if (this.board.getPiece(i, j).isBishop() &&
				!this.board.getPiece(i, j).getColor().equalsIgnoreCase(this.color))
				return true;
			else if (this.board.getPiece(i, j).getColor().equalsIgnoreCase(this.color))
				break;
		
		for (int i = x + 1, j = y - 1; i < 8 && j >= 0 ; i++, j--)
			if (this.board.getPiece(i, j).isBishop() &&
				!this.board.getPiece(i, j).getColor().equalsIgnoreCase(this.color))
				return true;
			else if (this.board.getPiece(i, j).getColor().equalsIgnoreCase(this.color))
				break;
		
		return false;
	}
	
	private boolean canAttackQueen(int x, int y){
		//Queen in right side
		for (int i = y + 1; i < 8; i++)
			if (this.board.getPiece(x, i).isQueen() &&
				!this.board.getPiece(x, i).getColor().equalsIgnoreCase(this.color))
				return true;
			else if (this.board.getPiece(x, i).getColor().equalsIgnoreCase(this.color))
				break;
		
		//Queen in left side
		for (int i = y - 1; i >= 0 ; i--)
			if (this.board.getPiece(x, i).isQueen() &&
				!this.board.getPiece(x, i).getColor().equalsIgnoreCase(this.color))
				return true;
			else if (this.board.getPiece(x, i).getColor().equalsIgnoreCase(this.color))
				break;
		
		//Queen in top side
		for (int i = x - 1; i >= 0 ; i--)
			if (this.board.getPiece(i, y).isQueen() &&
				!this.board.getPiece(i, y).getColor().equalsIgnoreCase(this.color))
				return true;
			else if (this.board.getPiece(i, y).getColor().equalsIgnoreCase(this.color))
				break;
		
		//Queen in bottom side
		for (int i = x + 1; i < 8 ; i++)
			if (this.board.getPiece(i, y).isQueen() &&
				!this.board.getPiece(i, y).getColor().equalsIgnoreCase(this.color))
				return true;
			else if (this.board.getPiece(i, y).getColor().equalsIgnoreCase(this.color))
				break;
		
		for (int i = x - 1, j = y - 1;  i >= 0 && j >= 0 ; i--, j--)
			if (this.board.getPiece(i, j).isQueen() &&
				!this.board.getPiece(i, j).getColor().equalsIgnoreCase(this.color))
				return true;
			else if (this.board.getPiece(i, j).getColor().equalsIgnoreCase(this.color))
				break;
		
		for (int i = x - 1, j = y + 1;  i >= 0 && j < 8 ; i--, j++)
			if (this.board.getPiece(i, j).isQueen() &&
				!this.board.getPiece(i, j).getColor().equalsIgnoreCase(this.color))
				return true;
			else if (this.board.getPiece(i, j).getColor().equalsIgnoreCase(this.color))
				break;
			
		for (int i = x + 1, j = y + 1;  i < 8 && j < 8 ; i++, j++)
			if (this.board.getPiece(i, j).isQueen() &&
				!this.board.getPiece(i, j).getColor().equalsIgnoreCase(this.color))
				return true;
			else if (this.board.getPiece(i, j).getColor().equalsIgnoreCase(this.color))
				break;
		
		for (int i = x + 1, j = y - 1; i < 8 && j >= 0 ; i++, j--)
			if (this.board.getPiece(i, j).isQueen() &&
				!this.board.getPiece(i, j).getColor().equalsIgnoreCase(this.color))
				return true;
			else if (this.board.getPiece(i, j).getColor().equalsIgnoreCase(this.color))
				break;

		return false;
	}
	
	private boolean canAttackKing(int x, int y){
		if (this.board.getPiece(x - 1, y) != null && 
			this.board.getPiece(x - 1, y).isKing() && 
			!this.board.getPiece(x - 1, y).getColor().equals(this.color))
			return true;
		
		if (this.board.getPiece(x - 1, y + 1) != null && 
			this.board.getPiece(x - 1, y + 1).isKing() && 
			!this.board.getPiece(x - 1, y + 1).getColor().equals(this.color))
			return true;
		
		if (this.board.getPiece(x, y + 1) != null && 
			this.board.getPiece(x, y + 1).isKing() && 
			!this.board.getPiece(x, y + 1).getColor().equals(this.color))
			return true;
		
		if (this.board.getPiece(x + 1, y + 1) != null && 
			this.board.getPiece(x + 1, y + 1).isKing() && 
			!this.board.getPiece(x + 1, y + 1).getColor().equals(this.color))
			return true;
		
		if (this.board.getPiece(x + 1, y) != null && 
			this.board.getPiece(x + 1, y).isKing() && 
			!this.board.getPiece(x + 1, y).getColor().equals(this.color))
			return true;
		
		if (this.board.getPiece(x + 1, y - 1) != null && 
			this.board.getPiece(x + 1, y - 1).isKing() && 
			!this.board.getPiece(x + 1, y - 1).getColor().equals(this.color))
			return true;
		
		if (this.board.getPiece(x, y - 1) != null && 
			this.board.getPiece(x, y - 1).isKing() && 
			!this.board.getPiece(x, y - 1).getColor().equals(this.color))
			return true;
		
		if (this.board.getPiece(x - 1, y - 1) != null && 
			this.board.getPiece(x - 1, y - 1).isKing() && 
			!this.board.getPiece(x - 1, y - 1).getColor().equals(this.color))
			return true;
		
		return false;
	}
	
	public Boolean canMove(){
		if (isValidMovement(this.posX - 1, this.posY)) return true;
		if (isValidMovement(this.posX - 1, this.posY + 1)) return true;
		if (isValidMovement(this.posX, this.posY + 1)) return true;
		if (isValidMovement(this.posX + 1, this.posY + 1)) return true;
		if (isValidMovement(this.posX + 1, this.posY)) return true;
		if (isValidMovement(this.posX + 1, this.posY - 1)) return true;
		if (isValidMovement(this.posX, this.posY - 1)) return true;
		if (isValidMovement(this.posX - 1, this.posY - 1)) return true;
		return false;
	}
	
	public Boolean isBeingAttack(){
		if (canAttackPawn(this.posX, this.posY))
			return true;
		
		if (canAttackKnight(this.posX, this.posY))
			return true;
		
		if (canAttackRook(this.posX, this.posY))
			return true;
					
		if (canAttackBishop(this.posX, this.posY))
			return true;
		
		if (canAttackQueen(this.posX, this.posY))
			return true;
		
		return false;
	}
	
	public Boolean isKing(){
		return false;
	}
}
