import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class Generator extends Solver{
    public  Generator(){
        super();
    }
    public  Generator(Generator generator){
        super(generator);
    }
    /*This method generate a list of  valid candidate to the sudoku board.
     * Return the reference of the candidateList*/
    public LinkedList<Integer> candidate(Cell cell){
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

    // This method check if the  original Value of the cell is the  beginning of a solution, and it the unique solution
    public Boolean hasUniqueSolution(Cell cell , int originalValue){
        Boolean res = true;
        if (!solveBoard(new Board(this))){
            res = false;
        }
        for (int i = 1; i < 10; i++) {
            if(i!= originalValue && isValid(cell.getRow(), cell.getCol(),i,this.getBoard())){
                cell.setValue(i);
                if (solveBoard(new Board(this))){
                    cell.setValue(originalValue);
                    res = false;
                }
            }
        }
        cell.setValue(0);
        return res;
    }

    // This method makes holes in a  Board that is already fill with valid number
//    it makes sure that there is only one solution
    public  int makeHoles(int holes){
        int res = -1;
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
            if(this.hasUniqueSolution(currentCell,originalValue)){
                i++;
            }else {
                currentCell.setValue(originalValue);
            }
            if (unvisitedCell.isEmpty()){
                res=i;
                i = holes;
            }
        }
        return  res;
    }
    /* This method take the remaining  numbers wanted as an argument, and return the effective remaining number
    *  */
    public  int makeBoard(int remainingNumbers){
        int res = -1;
        this.fillBoard();
        res = BOARD_SIZE * BOARD_SIZE - this.makeHoles(BOARD_SIZE * BOARD_SIZE - remainingNumbers);
        return  res;
    }
}

