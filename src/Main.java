
public class Main {

	public static void main(String[] args) {
	    Generator sudoku = new Generator();
	    sudoku.makeBoard(1);
		System.out.println(sudoku);
		System.out.println(sudoku.howManyNumbers());
	    Solver.solveBoard(sudoku);
		System.out.println(sudoku);
	}

}
