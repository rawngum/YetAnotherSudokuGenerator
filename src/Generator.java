public class Generator {
    private  Board board ;
    public  Generator(){
        board = new Board();
    }
    public  Generator(Generator generator){
        board = new Board(generator.board);
    }

    public static void main(String[] args) {

    }
}

