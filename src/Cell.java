
public class Cell {

private  int row;
private  int col;
private  int value;
private  int region;

    // TODO: 16/05/18 Rajouter la definition de la region au constructeur 
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

    public static void main(String[] args) {

	}

}
