import javax.swing.JPanel;


public class BoardCell extends JPanel {
	private int x,y;
	private BoardCell[] adjacentCells;
	public BoardCell(int x, int y) {
		super();
		this.x=x;
		this.y=y;
		adjacentCells=new BoardCell[4];
	}

	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}


	public void getAdjacentRedPanels() {
		
	}

}
