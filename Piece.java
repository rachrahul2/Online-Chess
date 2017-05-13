
public abstract class Piece {

	protected int posX, posY;
	protected String color;
	protected String name;
	protected Board board;
	
	public Piece(String color, int x, int y, Board b){
		this.color = color;
		this.posX = x;
		this.posY = y;
		this.board = b;
	}
	
	abstract public Boolean isValidMovement(int x, int y);
	
	public Boolean isPawn(){
		return false;
	}
	
	public Boolean isBishop(){
		return false;
	}
	
	public Boolean isKnight(){
		return false;
	}
	
	public Boolean isRook(){
		return false;
	}
	
	public Boolean isQueen(){
		return false;
	}
	
	public Boolean isKing(){
		return false;
	}
	
	public Boolean isEmpty(){
		return false;
	}
	
	public void move(int x, int y){
		this.posX = x;
		this.posY = y;
	}
	
	public String getColor(){
		return color;
	}
	
	public String toString(){
		return name;
	}
}
