import java.util.LinkedList;

public class Cell {

    private  int row;
    private  int col;
    private  int value;
    private LinkedList<Integer> candidate= new LinkedList<Integer>() ;
    private  Cell nextEmptyCell;
    private  Cell nextCell;
    private  Cell prevCell;
    private  Cell prevEmptyCell;
    private  LinkedList<Integer> visited = new LinkedList<Integer>();
    private  int isVisited = 0;

    public  Cell(int row , int col){
        this.row = row;
        this.col = col;
        this.value = 0;
    }
    public  Cell(Cell other){
        this.row = other.row;
        this.col = other.col;
        this.value = other.value;
    }
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Cell getNextCell() {
        return nextCell;
    }

    public void setNextCell(Cell nextCell) {
        this.nextCell = nextCell;
    }

    public void setPrevCell(Cell prevCell) {
        this.prevCell = prevCell;
    }

    public Cell getPrevCell() {
        return prevCell;
    }

    public Cell getNextEmptyCell() {
        return nextEmptyCell;
    }

    public Cell getPrevEmptyCell() {
        return prevEmptyCell;
    }

    public void setNextEmptyCell(Cell nextEmptyCell) {
        this.nextEmptyCell = nextEmptyCell;
    }

    public void setPrevEmptyCell(Cell prevEmptyCell) {
        this.prevEmptyCell = prevEmptyCell;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public LinkedList<Integer> getCandidateList() {
        return this.candidate;
    }

    public void setVisited(int visited) {
        this.visited.add(visited);
    }

    public LinkedList<Integer> getVisited() {
        return  visited;
    }

    public int getIsVisited() {
        return isVisited;
    }

    public  int isVisited(){
        return isVisited++;
    }

    @Override
    public boolean equals(Object o) {
        boolean res = true;
        Cell cell = (Cell) o;
        res= res && this.row == cell.row;
        res= res && this.col == cell.col;
        res = res && this.value == cell.value;
        return  res;
    }

    @Override
    public String toString() {
        return  " Value : " + this.value + "\t Column : " + this.col + "\t Row : " + this.row  +
                "\t Next Cell 's reference : " +   Integer.toHexString(System.identityHashCode(this.getNextCell()))+ "\tCurrent cell's reference : " + Integer.toHexString(System.identityHashCode(this));
    }

    public static void main(String[] args) {
        Cell cell1 = new Cell(2,3);
        Cell cell2 = cell1;
        Cell cell3 =  new Cell(4,3);
        System.out.println(cell1.equals(cell2));
        System.out.println(cell1.equals(cell3));
    }

}
