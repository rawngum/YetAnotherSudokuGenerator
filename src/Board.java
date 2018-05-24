import javax.xml.bind.Element;
import  java.lang.StringBuilder;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class Board {

	private Cell[][] board = new Cell[9][9] ;
    static final int EMPTY_CELL = 0;
    static final int BOARD_SIZE = 9;
    static final int REGION_SIZE = 3;
    private char[] line;


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

    public  void  resetBoardVisited(){
        for(Cell[] row : this.board){
            for(Cell element : row){
                element.getVisited().clear();
            }
        }
    }
    public  void  resetBoardValue(){
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
    /* This method  generate a valid full sudoku This should not be used in the main */

private Boolean fill(Cell cell){

    LinkedList<Integer> tmpCandidateList = this.candidate(cell);
    for (int element :
            tmpCandidateList) {
            cell.setValue(element);
            cell.setVisited(element);
        if(cell.getNextCell() == null){
            return true;
        }
            return fill(cell.getNextCell());
        }

    if(tmpCandidateList.isEmpty()){ //Backtracking
        cell.getVisited().clear();
        cell.setValue(0);
        return  fill(cell.getPrevCell());
    }

  return  false;
}

// This method mask the recursive call annd reset the board before the call
    public  Boolean fillBoard(){
    this.resetBoardValue();
    Boolean res =this.fill(this.getCell(0,0));
    this.resetBoardVisited();
    return  res;
    }

    //Take a 2D array as an arg and return it in a LinkedList
    public  LinkedList<Cell> asLinkedList(){
    LinkedList<Cell> res = new LinkedList<Cell>();
        for (Cell[] row :
                this.getBoard()) {
            for (Cell element :
                    row) {
              res.add(element);
            }
        }
        return  res;
    }

// This method makes holes in a  Board that is already fill with valid number
//    it makes sure that there is only one solution
// TODO: 24/05/18 Comment savoir que l'on a visité toute les cases ?
// TODO: 24/05/18 Tenir une liste des cases disonibles, cela devient complique de trouver une case non visité quand il n'y en a plus beaucoup, cela suppose beaucoup d'essai pour rien dans la boucle do-while
// TODO: 24/05/18 Faire une liste de toute les cellules et juste une cellule de la liste, quand la liste est vide toute les cellules ont été visité
// TODO: 24/05/18 Traiter le cas ou on e peut pas faire tout les trous demandé.
// TODO: 24/05/18 Thow Exception si holes > BOARDSIZE² 
    public  Boolean makeHoles(int holes){
        Boolean res = true;
        Random rand = new Random();
        Cell currentCell = null;
        LinkedList<Cell>   unvisitedCell = this.asLinkedList();
        int tmpCellValue = -1;
        int currentIndex = -1;
        for (int i = 0; i < holes; i++) {
            currentIndex = rand.nextInt(unvisitedCell.size());
            currentCell = unvisitedCell.get(currentIndex);
            unvisitedCell.remove(currentIndex);
            
            tmpCellValue = currentCell.getValue();
            currentCell.setValue(0);
//            if(!this.solve()){
//                currentCell.setValue(tmpCellValue);
//                i--;      // Reset the index to make sure we get the right number of holes
//            }
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
//        long startTime = System.currentTimeMillis();
//        for (int i = 0; i < 10000; i++) {

            myBoard.fillBoard();
            System.out.println(myBoard);
            myBoard.makeHoles(80);
        System.out.println("After 1 Hole");
        System.out.println( myBoard);
//        }
//        long stopTime = System.currentTimeMillis();
//        long elapsedTime = stopTime - startTime;
//        System.out.println(elapsedTime);
	}

}
