import java.awt.Color;


public class Piece {
	private int x, y;
	private int length;
	private int direction;
	public Color pieceColor;
	
	public Piece(int x, int y, int len, int direction, Color pColor){
		this.x = x;
		this.y = y;
		this.length = len;
		this.direction = direction;
		this.pieceColor = pColor;
	}
	
	public void addOneCell(){
		this.length++;
	}
	
}