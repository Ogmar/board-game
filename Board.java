
//Board Class acting as the playing board in our game

public class Board {

	// Board instance variables

	private int board[][][];
	private static int MIN_LEVEL = 3;
	private static int MIN_SIZE = 3;
	private int level;
	private int size;

	// Constructors

	public Board() {
		this.level = 3;
		this.size = 4;
		createBoard(3, 4);
	}

	public Board(int l, int x) {
		this.level = l;
		this.size = x;
		if (l < MIN_LEVEL || x < MIN_SIZE) {
			System.exit(0);
		}
		createBoard(l, x);
	}

	// Method assigning energy value to each slot on the board

	private void createBoard(int level, int size) {
		this.board = new int[level][size][size];
		this.board[0][0][0] = 0;
		for (int l = 0; l < level; l++) {

			for (int x = 0; x < size; x++) {

				for (int y = 0; y < size; y++) {

					if (x == 0 && y == 0 && l == 0) {
						this.board[l][x][y] = 0;
					}

					else if ((x + y + l) % 3 == 0) {
						this.board[l][x][y] = -3;
					}

					else if ((x + y + l) % 5 == 0) {
						this.board[l][x][y] = -2;
					}

					else if ((x + y + l) % 7 == 0) {
						this.board[l][x][y] = 2;
					}

					else {
						this.board[l][x][y] = 0;
					}
				}
			}
		}
	}

	// Accessors method for level , size and energy on a specific place on the board

	public int getLevel() {
		return level;
	}

	public int getSize() {
		return size;
	}

	public int getEnergyAdj(int l, int x, int y) {
		return this.board[l][x][y];
	}

	// toString method that returns the board

	public String toString() {

		String Value = "";
		String Board = "";
		for (int l = 0; l < level; l++) {

			System.out.println("Level " + l + "\n --------");

			for (int x = 0; x < size; x++) {

				for (int y = 0; y < size; y++) {
					Value = String.valueOf(board[l][x][y]);

					System.out.print("\t" + Value);
					if (y == size - 1) {
						System.out.println();
					}
				}
			}
		}
		return Board;

	}

}
