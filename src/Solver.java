public class Solver {
    protected Board board;
    protected  Solver(){
        this.board = new Board();
    }
    public  Solver(Board board){
        this.board = board;
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

}
