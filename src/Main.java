
public class Main {

	public static void main(String[] args) {
	    Generator sudoku = new Generator();
	    sudoku.makeBoard(1);
		System.out.println(sudoku);
		System.out.println(sudoku.howManyNumbers() + " holes were made");
	    Solver.solveBoard(sudoku);
		System.out.println(sudoku);
	}

}
