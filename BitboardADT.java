package sortbooks; 

public class Bitboard {

    private long board;

    public Bitboard(){
        board = 0L;
    }

    public setPosition(int row, int col) {

    }

    public clearPosition(int row, int col) {

    }


    public boolean inBounds(int row, int col) {
        // Should not exceed the 8 x 5 size of the board
        return row >= 0 && row < 5 && col >= 0 && col < 8;
    }
}

public static void main(String[] args) {
        Bitboard board = new Bitboard();