import java.util.LinkedList;

public class Cell {

private  int row;
private  int col;
private  int value;
private LinkedList<Integer> candidate= new LinkedList<Integer>() ;
private  Cell nextCell;

public  Cell(int row , int col){
    this.row = row;
    this.col = col;
    this.value = 0;
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

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public LinkedList<Integer> getCandidateList() {
        return this.candidate;
    }

    public static void main(String[] args) {

	}

    @Override
    public String toString() {
        return  " Value : " + this.value + "\t Column : " + this.col + "\t Row : " + this.row  +
                "\t Next Cell 's reference : " +   Integer.toHexString(System.identityHashCode(this.getNextCell()))+ "\tCurrent cell's reference : " + Integer.toHexString(System.identityHashCode(this));
    }
}
