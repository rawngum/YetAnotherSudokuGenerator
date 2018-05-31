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

    public  Board(Board other){
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

    private  void findEmptyCell(){
        Cell nextEmptyCell = null;
        Cell prevEmptyCell = null;
        for (int i = board.length -1 ; i >=0 ; i--){ // Initialize references in both directions
            for (int j = board[0].length  -1; j >= 0 ; j--) {
                this.board[i][j].setNextEmptyCell(nextEmptyCell);
                if (this.board[i][j].getValue() == 0){
                    nextEmptyCell = this.board[i][j];
                }
            }
        }
        for (int i = 0  ;  i<board.length ; i++) {
            for (int j = 0 ; j < board[0].length ; j++){
                this.board[i][j].setPrevEmptyCell(prevEmptyCell);
                if (this.board[i][j].getValue() == 0){
                    prevEmptyCell = this.board[i][j];
                }
            }
        }
    }

    public Cell getCell(int row, int col){
        return this.board[row][col];
    }

    public Cell[][] getBoard() {
        return board;
    }

    public static boolean isValid(int row, int col, int value, Cell[][] board){
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

    // Cell passed in parameter MUST be the first empty cell of the board
    public  static boolean solve(Cell cell, Board board){
        for (int i = 0; i < 10; i++) {
            if (isValid(cell.getRow(),cell.getCol(), i,board.getBoard()) &&  !cell.getVisited().contains(i)) {
                cell.setValue(i);
                cell.setVisited(i);
                if (cell.getNextEmptyCell() == null) { // Sucess
                    return true;
                }
                return solve(cell.getNextEmptyCell(), board);
            }
        }

        if(cell.getPrevEmptyCell() == null){ // Failure
            return  false;
        }else {
                cell.setValue(0);
                return  solve(cell.getPrevEmptyCell(), board);
            }
    }
// Mask the recursive call, reset the visited propertie  and make sure the  cell in parameter for the first
    // recursive call is the first empty Cell
    public  static   boolean solveBoard (Board board){
        boolean res;
        board.findEmptyCell();
        board.resetBoardVisited();
        if(board.getCell(0,0).getValue() == 0){
           res =solve(board.getCell(0,0),board);
        }else  {
            res = solve(board .getCell(0,0).getNextEmptyCell(), board);
        }
        return  res;
    }


    // This method take the Cell from which to start, and the original value of this cell. We know that this original value is a
    // Solution, and we are looking for other values that would be a solution. It return true if it doesnt find other solution
    //When this method is called the value of the cell given in parameter should be 0

    public Boolean hasUniqueSolution(Cell cell, int originalValue ) {
        this.resetBoardVisited();
        LinkedList<Integer> tmpCandidateList = this.candidate(cell);
        if(tmpCandidateList.size() > 1){

            tmpCandidateList.remove((Integer) originalValue); // Must cast originalValue. If not remove() treat it as an index
            for (int element :
                    tmpCandidateList) {
                cell.setValue(element);
                if(solveBoard(new Board(this))){ // TODO: 28/05/18 Solve devrai se faire sur un objet temporaire, pour le moment il remplis au fur a mesure que je fais les trous. Faire une class generator
                    System.out.println("hasUNiqueSolution return false");
                    cell.setValue(originalValue);
                    return  false;
                }
            }
        }
        cell.setValue(0);
        return true ;
    }
    // This method makes holes in a  Board that is already fill with valid number
//    it makes sure that there is only one solution
// TODO: 24/05/18 Traiter le cas ou on e peut pas faire tout les trous demandé.
// TODO: 24/05/18 Thow Exception si holes > BOARDSIZE²
    /*Parfois Lors de deuxieme resolution après makeHoles la première case est changé à un autre chiffre ?!*/
    public  Boolean makeHoles(int holes){
        Boolean res = true;
        Random rand = new Random();
        Cell currentCell = null;
        LinkedList<Cell>   unvisitedCell = this.asLinkedList();
        int originalValue = -1;
        int currentIndex = -1;
        int i = 0;
        while (i<holes){
            currentIndex = rand.nextInt(unvisitedCell.size());
            currentCell = unvisitedCell.get(currentIndex);
            unvisitedCell.remove( currentIndex);
            originalValue = currentCell.getValue();
            currentCell.setValue(0);
            if(solveBoard(new Board(this))){
                i++;
            }else {
                currentCell.setValue(originalValue);
            }
            if (unvisitedCell.isEmpty()){
                System.out.println("Abort");
                System.out.println(this);
                res=false;
                i = holes;
            }
        }
        return  res;
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
    public static void main(String[] args) {

        Board myBoard = new Board();
//        myBoard.getCell(1,5).setValue(8);
//	    System.out.print(myBoard);
//        System.out.printf("Should be false Board.isValid(1,2,8) = %b%n",myBoard.isValid(1,2,8));
//        System.out.printf("Should be false Board.isValid(6,5,8) = %b%n",myBoard.isValid(6,5,8));
//        System.out.printf("Should be false Board.isValid(2,4,8) = %b%n",myBoard.isValid(2,4,8));
//        System.out.printf("Should be false Board.isValid(1,5,6) = %b%n",myBoard.isValid(1,5,6));
//        System.out.printf("Should be true Board.isValid(7,4,8) = %b%n",myBoard.isValid(7,4,8));
//        System.out.printf("Should be true Board.isValid(4,4,8) = %b%n",myBoard.isValid(4,4,8));
//        System.out.printf("Should be true Board.isValid(1,2,6) = %b%n",myBoard.isValid(1,2,6));
//        System.out.printf("Should be true Board.isValid(6,5,6) = %b%n",myBoard.isValid(6,5,6));
//        System.out.printf("Should be true Board.isValid(2,4,6) = %b%n",myBoard.isValid(2,4,6));
//        myBoard.resetBoard();
//        myBoard.fillBoard();
//        myBoard.candidate(1,1);
//        LinkedList<Integer> candidate = myBoard.getCell(1,1).getCandidateList();
//        for (int element : candidate){
//            System.out.println(element);
//        }
//        long startTime = System.currentTimeMillis();
//        for (int i = 0; i < 10000; i++) {
//Test pour la methode solve()
        myBoard.fillBoard();
        System.out.println(myBoard);
        myBoard.makeHoles(80);
        System.out.println(myBoard);
        int counter = 0;
        for (Cell[] row :
                myBoard.getBoard()) {
            for (Cell element :
                    row) {
                if(element.getValue() == 0) {
                    counter++;
                }
            }
        }
        System.out.println("Il y a " + counter + " Trous");
        solveBoard(myBoard);
        System.out.println(myBoard);
//        }
//        long stopTime = System.currentTimeMillis();
//        long elapsedTime = stopTime - startTime;
//        System.out.println(elapsedTime);
    }

}
