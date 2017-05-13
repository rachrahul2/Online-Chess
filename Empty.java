
public class Empty extends Piece{
		
		public Empty(int x, int y, Board b){
			super("none", x, y, b);
			this.name = ".";
		}
		
		public Boolean isValidMovement(int x, int y){
			return false;
		}
		
		public Boolean isEmpty(){
			return true;
		}
}