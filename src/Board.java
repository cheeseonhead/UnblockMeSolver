import java.awt.Color;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JPanel;

public class Board {

	private int maxPiece = 25;
	private int horizontal = 1;
	private int vertical = 0;

	private int[][] boardGrid = new int[8][8];
	public Piece[] pieceList = new Piece[maxPiece];
	private int pieceCount = 1;
	public Queue<board> solvingSteps = new LinkedList<board>();

	public Board(JPanel[][] boardCells) {

		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {

				if (boardCells[i][j].getBackground() == Color.WHITE) {
					boardGrid[i + 1][j + 1] = 0;
				} else if (boardCells[i][j].getBackground() == Color.red) {
					boardGrid[i + 1][j + 1] = 1;
					pieceList[1] = new Piece(i, j, 2, horizontal, Color.red);
				} else {
					if (i - 1 >= 0
							&& boardCells[i][j].getBackground() == boardCells[i - 1][j]
									.getBackground()) {
						boardGrid[i + 1][j + 1] = boardGrid[i][j + 1];
						pieceList[boardGrid[i + 1][j + 1]].addOneCell();
					} else if (j - 1 >= 0
							&& boardCells[i][j].getBackground() == boardCells[i][j - 1]
									.getBackground()) {
						boardGrid[i + 1][j + 1] = boardGrid[i + 1][j];
						pieceList[boardGrid[i + 1][j + 1]].addOneCell();
					} else {
						pieceCount++;
						pieceList[pieceCount] = new Piece(i, j, 1, vertical,
								boardCells[i][j].getBackground());
						boardGrid[i + 1][j + 1] = pieceCount;
					}
				}

			}
		}
	}

	public void printBoard() {

		for (int i = 1; i <= 6; i++) {
			for (int j = 1; j <= 6; j++) {
				if (boardGrid[i][j] == 'r' - '0') {
					System.out.print("r");
				} else if (boardGrid[i][j] >= 10) {
					System.out.print((char) (boardGrid[i][j] + 'A' - 10));
				} else {
					System.out.print(boardGrid[i][j]);
				}
			}
			System.out.println("");
		}

	}

	private int maxn = 100000;
	private int maxp = 20;

	int pid, bid;

	public class board {
		int fa, mid;
		int[][] bcur = new int[8][8];
		int[] curx = new int[maxp];
		int[] cury = new int[maxp];
		int[][] pdir = new int[maxp][2];
		int[] plen = new int[maxp];
		
		public board(){}
		
		public board(board mother){
			this.fa = mother.fa;
			this.mid = mother.mid;
			for(int i=0;i<8;i++){
				for(int j=0;j<8;j++){
					this.bcur[i][j] = mother.bcur[i][j];
				}
			}
			for(int i=0;i<maxp;i++){
				this.curx[i] = mother.curx[i];
				this.cury[i] = mother.cury[i];
				this.pdir[i][0] = mother.pdir[i][0];
				this.pdir[i][1] = mother.pdir[i][1];
				this.plen[i] = mother.plen[i];
				
			}
		}

		void setBorder() {
			for (int i = 0; i < 8; i++) {
				bcur[0][i] = bcur[i][0] = bcur[7][i] = bcur[i][7] = -1;
			}
		}

		void printb(int p) {
			//System.out.println("--------------");
			for (int i = 1; i <= 6; i++) {
				for (int j = 1; j <= 6; j++) {
					if (bcur[i][j] < 10 && bcur[i][j] != 0)
						System.out.format("%d", bcur[i][j]);
					else if (bcur[i][j] >= 10)
						System.out.format("%c", bcur[i][j] + 'A' - 10);
					else
						System.out.format("-");
				}
				System.out.println("");
			}
			//System.out.println("--------------");

			if (p == 0)
				return;

			for (int k = 1; k <= pid; k++) {
				// System.out.format("pid %d curx %d cury %d pdirup %d pdirrt %d plen %d\n",k,curx[k],cury[k],pdir[k][0],pdir[k][1],plen[k]);
			}
		}

		void pieceMove(int id, int shift) {
			int mc, xc;

			// clean track
			if (pdir[id][1] != 0) {
				mc = cury[id];
				mc += (shift < 0) ? shift : 0;
				xc = cury[id] + plen[id] - 1;
				xc += (shift > 0) ? shift : 0;
				for (int i = mc; i <= xc; i++) {
					bcur[curx[id]][i] = 0;
				}
			} else {
				mc = curx[id];
				mc += (shift > 0) ? 0 : shift;
				xc = curx[id] + plen[id] - 1;
				xc += (shift > 0) ? shift : 0;
				for (int i = mc; i <= xc; i++) {
					bcur[i][cury[id]] = 0;
				}
			}

			// place piece
			for (int i = 0; i < plen[id]; i++) {
				bcur[curx[id] + pdir[id][0] * (i + shift)][cury[id]
						+ pdir[id][1] * (i + shift)] = id;
			}
			curx[id] += pdir[id][0] * (shift);
			cury[id] += pdir[id][1] * (shift);
		}

		int mshifting(int id) {
			int cnt = 0;
			for (int i = 1; i <= 6; i++) {
				int xd = (curx[id] - i) * pdir[id][1], yd = (cury[id] - i)
						* pdir[id][0];
				if ((bcur[i + xd][i + yd]) == 0) {
					cnt++;
				} else if (bcur[i + xd][i + yd] == id)
					break;
				else
					cnt = 0;
			}
			return -1 * cnt;
		}

		int xshifting(int id) {
			int cnt = 0;
			for (int i = 6; i >= 1; i--) {
				int xd = (curx[id] - i) * pdir[id][1], yd = (cury[id] - i)
						* pdir[id][0];
				if ((bcur[i + xd][i + yd]) == 0) {
					cnt++;
				} else if (bcur[i + xd][i + yd] == id)
					break;
				else
					cnt = 0;
			}
			return cnt;
		}

		boolean comp(board he) {
			for (int i = 1; i <= 6; i++) {
				for (int j = 1; j <= 6; j++) {
					if (he.bcur[i][j] != this.bcur[i][j])
						return true;
				}
			}
			return false;
		}

	}

	private board[] bmem = new board[maxn];
	private board braw = new board();

	Queue<board> q = new LinkedList<board>();

	public void puzzleIn() {
		// initialize global para
		pid = bid = 1;

		// set red car as id 1
		bmem[0] = new board();
		bmem[0].curx[1] = 3;
		bmem[0].pdir[1][0] = 0;
		bmem[0].pdir[1][1] = 1;
		bmem[0].plen[1] = 2;

		bmem[0].setBorder();

		// read puzzle
		for (int i = 1; i <= 6; i++) {
			for (int j = 1; j <= 6; j++) {
				int c = boardGrid[i][j];
				braw.bcur[i][j] = c;
				if (c <= 0)
					continue;
				if (c == 1) {
					if (braw.bcur[i][j - 1] != 1) {
						bmem[0].cury[1] = j;
						bmem[0].bcur[3][j] = 1;
						bmem[0].bcur[3][j + 1] = 1;
					}
					continue;
				}
				if (braw.bcur[i - 1][j] == c) {
					int tid = bmem[0].bcur[i - 1][j];
					bmem[0].plen[tid]++;
					bmem[0].pdir[tid][0] = 1;
					bmem[0].pdir[tid][1] = 0;
					bmem[0].bcur[i][j] = bmem[0].bcur[i - 1][j];
				} else if (braw.bcur[i][j - 1] == c) {
					int tid = bmem[0].bcur[i][j - 1];
					bmem[0].plen[tid]++;
					bmem[0].pdir[tid][0] = 0;
					bmem[0].pdir[tid][1] = 1;
					bmem[0].bcur[i][j] = bmem[0].bcur[i][j - 1];
				} else {
					pid++;
					bmem[0].curx[pid] = i;
					bmem[0].cury[pid] = j;
					bmem[0].plen[pid] = 1;
					bmem[0].bcur[i][j] = pid;
				}
			}
		}

		bmem[0].fa = bmem[0].mid = 0;
		q.add(new board(bmem[0]));

		// bmem[0].printb(1);
	}

	boolean hasSeen(board me) {
		// check memory, dif=1 not seen, dif=0 seen
		for (int k = bid - 1; k >= 0; k--) {
			if (!me.comp(bmem[k])) {
				System.out.println(bid - k);
				return true;
			}
		}
		return false;
	}

	boolean isSolved(board me) {
		if (me.bcur[3][5] == 1 && me.bcur[3][6] == 1)
			return true;
		return false;
	}

	void solvePrint(int me) {
		if (bmem[me].mid != 0)
			solvePrint(bmem[me].fa);
		solvingSteps.add(new board(bmem[me]));

	}

	void intab(int j) {
		for (int i = 0; i < j; i++)
			System.out.format("\t");
	}

	public void puzzleRun() {
		while (q.peek() != null) {
			braw = new board(q.peek());
			for (int id = 1; id <= pid; id++) {
				for (int dir = braw.mshifting(id); dir <= braw.xshifting(id); dir++) {
					if (dir == 0)
						continue;
					braw.pieceMove(id, dir);
					braw.fa = q.peek().mid;
					if (hasSeen(braw)) {
						braw = new board(q.peek());
						continue;
					}
					bmem[bid] = braw;
					bmem[bid].mid = bid;
					if (isSolved(bmem[bid])) {
						solvePrint(bid);
						return;
					}
					q.add(bmem[bid]);
					bid++;
					System.out.println(bid);
					braw = new board(q.peek());
				}
			}
			q.remove();
		}
	}
}
