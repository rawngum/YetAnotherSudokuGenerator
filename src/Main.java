
public class Main {

	public static void main(String[] args) {
	    Generator sudoku = new Generator();
	    sudoku.makeBoard(40);
		System.out.println(sudoku);
		System.out.println(sudoku.howManyNumbers());
	    sudoku.solveBoard(sudoku);
		System.out.println(sudoku);
	}

}
