import  java.lang.StringBuilder;
import java.util.Arrays;

public class Board {

	private Cell[][] board = new Cell[9][9] ;
    static final Cell EMPTY_CELL =  new Cell();
    static final int BOARD_SIZE = 9;
    static final int REGION_SIZE = 3;
    private char[] line;

	public Board() {
	   for (int i = 0  ;  i<board.length ; i++) {
	       for (int j = 0 ; j < board[0].length ; j++)
	           board[i][j] = new Cell(i,j);
       }
	}
@Override

public  String  toString (){
		// Exact size of the generated string for the buffer (values + spacers)
		final int size = (BOARD_SIZE*2+1+((REGION_SIZE+1)*2))*(BOARD_SIZE+REGION_SIZE+1);
		final String verticalSpace = " |";
		// A StringBuilder is absolutely needed here
		// use of String concatenation (+) would have really bad performance
		final StringBuilder buffer = new StringBuilder(size);
		// Row/Column traversal
		for (int a=0; a < BOARD_SIZE; a++) {
			Cell[] row = board[a];
			if (a % REGION_SIZE == 0) {
				appendLine(buffer);
			}
			for (int b=0; b < BOARD_SIZE; b++) {
				Cell cell = row[b];
				if (b % REGION_SIZE == 0) {
					buffer.append(verticalSpace);
				}
				appendValue(buffer, cell);
			}
			buffer.append(verticalSpace);
			buffer.append('\n');
		}
		appendLine(buffer);
		return buffer.toString();
}
private void appendValue(StringBuilder buffer, Cell cell) {
		buffer.append(' ');
    // TODO: 16/05/18 comparer avec cell.value faire un getter ?
		if (cell != EMPTY_CELL) {
			buffer.append(cell);
		} else {
			buffer.append('_');
		}
	}
	/**
	 * Append a line (separator between region)
	 */
	private void appendLine(StringBuilder buffer) {
		// Only create the line once
		if (line == null) {
		  line = new char[BOARD_SIZE*2+((REGION_SIZE+1)*2)];
		  Arrays.fill(line, '-');
		  //first char as space
		  line[0] = ' ';
		}
		buffer.append(line);
		buffer.append('\n');
	}
    public static void main(String[] args) {

	    Board myBoard = new Board();
	    System.out.print(myBoard);

	}

}
