import  java.lang.StringBuilder;
import java.util.Arrays;

public class Board {

    protected Cell[][] board = new Cell[9][9] ;
    static final int EMPTY_CELL = 0;
    static final int BOARD_SIZE = 9;
    static final int REGION_SIZE = 3;
    private char[] line;


    protected Board(){
        Cell nextCell = null;
        Cell prevCell = null;
        for (int i = board.length -1 ; i >=0 ; i--){ // Initialize references in both directions
            for (int j = board[0].length  -1; j >= 0 ; j--) {
                board[i][j] = new Cell(i,j);
                board[i][j].setNextCell(nextCell);
                nextCell = board[i][j];
            }
        }
        for (int i = 0  ;  i<board.length ; i++) {
            for (int j = 0 ; j < board[0].length ; j++){
                board[i][j].setPrevCell(prevCell);
                prevCell = board[i][j];
            }
        }
    }

    protected Board(Board other){
        Cell nextCell = null;
        Cell prevCell = null;
        for (int i = board.length -1 ; i >=0 ; i--){ // Initialize references in both directions
            for (int j = board[0].length  -1; j >= 0 ; j--) {
                board[i][j] = new Cell(other.getCell(i,j));
                board[i][j].setNextCell(nextCell);
                nextCell = board[i][j];
            }
        }
        for (int i = 0  ;  i<board.length ; i++) {
            for (int j = 0 ; j < board[0].length ; j++){
                board[i][j].setPrevCell(prevCell);
                prevCell = board[i][j];
            }
        }
    }


    protected Cell getCell(int row, int col){
        return this.board[row][col];
    }

    protected Cell[][] getBoard() {
        return board;
    }



    protected void  resetBoardVisited(){
        for(Cell[] row : this.board){
            for(Cell element : row){
                element.getVisited().clear();
            }
        }
    }
    protected   void  resetBoardValue(){
        for(Cell[] row : this.board){
            for(Cell element : row){
                element.setValue(0);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        boolean res = true;
        Board b = (Board ) o;
        for (int i = this.board.length -1 ; i >=0 ; i--){ // Initialize references in both directions
            for (int j = this.board[0].length  -1; j >= 0 ; j--) {
                res = res && this.board[i][j].equals(b.board[i][j]);
            }
        }
        return  res;
    }

    @Override
    public  String  toString (){
        // Exact size of the generated string for the buffer (values + spacers)
        final int size = (BOARD_SIZE*2+1+((REGION_SIZE+1)*2))*(BOARD_SIZE+REGION_SIZE+1);
        final String verticalSpace = " |";
        final StringBuilder buffer = new StringBuilder(size);
        // Row/Column traversal
        for (int i=0; i < BOARD_SIZE; i++) {
            Cell[] row = board[i];
            if (i % REGION_SIZE == 0) {
                appendLine(buffer);
            }
            for (int j = 0; j < BOARD_SIZE; j++) {
                Cell cell = row[j];
                if (j % REGION_SIZE == 0) {
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
        if (cell.getValue() != EMPTY_CELL) {
            buffer.append(cell.getValue());
        } else {
            buffer.append('.');
        }
    }

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
    public  int howManyNumbers(){
        int counter = 0;
        for (Cell[] row :
                board) {
            for (Cell element :
                    row) {
                if(element.getValue() != 0) {
                    counter++;
                }
            }
        }
        return  counter;

    }
    public static void main(String[] args) {

//        Board myBoard = new Board();
//        myBoard.fillBoard();
//        System.out.println(myBoard);
//        System.out.println(myBoard.makeHoles(80));
//        System.out.println(myBoard);

//        System.out.println("Il y a " + counter + " Trous");
//        solveBoard(myBoard);
//        System.out.println(myBoard);
    }

}
