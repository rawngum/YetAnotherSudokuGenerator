import  java.lang.StringBuilder;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import  java.util.Collection;

public class Board {

	private Cell[][] board = new Cell[9][9] ;
    static final int EMPTY_CELL = 0;
    static final int BOARD_SIZE = 9;
    static final int REGION_SIZE = 3;
    private char[] line;

//	public Board() {
//	   for (int i = 0  ;  i<board.length ; i++) {
//	       for (int j = 0 ; j < board[0].length ; j++)
//	           board[i][j] = new Cell(i,j);
//       }
//	}
    public  Board(){
        Cell nextCell = null;
        Cell prevCell = null;
        for (int i = board.length -1 ; i >=0 ; i--){
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

public Cell getCell(int row, int col){
	    return this.board[row][col];
}

public Cell[][] getBoard() {
        return board;
}

/* This method return true if value is'nt  in  the row, the column and the region
* It return false if the value is in the row, the column, the region or if the Cell is already  full*/
    public boolean isValid(int row, int col, int value){
        Boolean res=true;
//        Checking the row
        for (int i = 0 ; i < BOARD_SIZE ; i++){
            if (board[i][col].getValue() == value){
                res = false;
            }
        }
//        Checking the column
        for (int i = 0; i < BOARD_SIZE ; i++){
            if (board[row][i].getValue() == value){
                res = false;
            }
        }
//        Checking the region
        int rowStart = row /REGION_SIZE * REGION_SIZE;
        int colStart = col /REGION_SIZE * REGION_SIZE;

        for (int i =rowStart; i <rowStart + REGION_SIZE && i < BOARD_SIZE ; i++){
            for (int j = colStart ; j < colStart + REGION_SIZE  && j < BOARD_SIZE ; j++){
               if (board[i][j].getValue() == value){
                   res = false;
               }
            }
        }
//        Checking the cell
        if (board[row][col].getValue() == value){
            res = false;
        }
return res;
    }

    public  void  resetBoard(){
        for(Cell[] row : this.board){
            for(Cell element : row){
                element.setValue(0);
            }
        }
    }
/*This method generate a list of  valid candidate to the sudoku board.
* Return the reference of the candidateList*/
    public  LinkedList<Integer> candidate(Cell cell){
        LinkedList<Integer> candidate = cell.getCandidateList();
        candidate.clear();
        for (int i = 1; i < 10; i++) {
                if (this.isValid(cell.getRow(), cell.getCol(),i) &&  !cell.getVisited().contains(i)){
                    candidate.add(i);
                }
        }
        Collections.shuffle(candidate);
        return  candidate;
    }
    /* This method  generate a valid full sudoku*/
//// TODO: 16/05/18 En fait récursion obligatoire
//    public void fillBoard() {
//        int value;
//        LinkedList<Integer> tmpCandidateList;
//        this.resetBoard();
//        for (int i = 0; i < BOARD_SIZE; i++) {
//            for (int j = 0; j < BOARD_SIZE; j++) {
//                tmpCandidateList = this.candidate(i,j);
//                do {
//                    value =  tmpCandidateList.pop();
//                }while (!this.isValid(i,j,value )  tmpCandidateList.isEmpty());
//
//                this.getCell(i,j).setValue(value);
//            }
//        }
//    }

//    public Boolean fillBoard(Cell cell){
//
//        LinkedList<Integer> tmpCandidateList = this.candidate(cell);
//
//        if (tmpCandidateList.isEmpty()) { // No more candidate --> Backtracking
//            return fillBoard(cell.getPrevCell());
//        }
//        if(cell.getNextCell() == null){ // It has passed through all cells
//            return  true;
//        }
//        cell.setValue(tmpCandidateList.pop());
//        if (this.isValid(cell.getRow(), cell.getCol(),cell.getValue()){
//            return fillBoard(cell.getNextCell());
//        }else {
//            cell.setValue(0);
//            return  fillBoard(cell.getPrevCell());}
//        return  false;
//    }
//    }
public Boolean fillBoard(Cell cell){

    LinkedList<Integer> tmpCandidateList = this.candidate(cell);
    for (int element :
            tmpCandidateList) {
            cell.setValue(element);
            cell.setVisited(element);
        if(cell.getNextCell() == null){
            return true;
        }
            return fillBoard(cell.getNextCell());
        }

    if(tmpCandidateList.isEmpty()){
        cell.getVisited().clear();
        cell.setValue(0);
        return  fillBoard(cell.getPrevCell());
    }

  return  false;
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
    public static void main(String[] args) {

	    Board myBoard = new Board();
        myBoard.getCell(1,5).setValue(8);
	    System.out.print(myBoard);
        System.out.printf("Should be false Board.isValid(1,2,8) = %b%n",myBoard.isValid(1,2,8));
        System.out.printf("Should be false Board.isValid(6,5,8) = %b%n",myBoard.isValid(6,5,8));
        System.out.printf("Should be false Board.isValid(2,4,8) = %b%n",myBoard.isValid(2,4,8));
        System.out.printf("Should be false Board.isValid(1,5,6) = %b%n",myBoard.isValid(1,5,6));
        System.out.printf("Should be true Board.isValid(7,4,8) = %b%n",myBoard.isValid(7,4,8));
        System.out.printf("Should be true Board.isValid(4,4,8) = %b%n",myBoard.isValid(4,4,8));
        System.out.printf("Should be true Board.isValid(1,2,6) = %b%n",myBoard.isValid(1,2,6));
        System.out.printf("Should be true Board.isValid(6,5,6) = %b%n",myBoard.isValid(6,5,6));
        System.out.printf("Should be true Board.isValid(2,4,6) = %b%n",myBoard.isValid(2,4,6));
//        myBoard.resetBoard();
//        myBoard.fillBoard();
//        myBoard.candidate(1,1);
//        LinkedList<Integer> candidate = myBoard.getCell(1,1).getCandidateList();
//        for (int element : candidate){
//            System.out.println(element);
//        }
        for(Cell[] row : myBoard.getBoard()){
            for(Cell element : row){
                System.out.println(element);
            }
        }
        myBoard.fillBoard(myBoard.getCell(0,0));
        System.out.println(myBoard);

	}

}
