package SCG; 



public class BitboardADT {

    private long board;

    public BitboardADT(long Long){
        board = Long;
    }

    public long shift(long bits, int exponent){
        long shifted = bits;
        for(int i=0; i < exponent; i++){
            shifted = shifted * 2;
        }
        return shifted;
    }

    public void setPosition(int row, int col) {
        if(inBounds(row, col)){
            board = board + shift(1, row*8 + col);
        }
    }

    public void clearPosition(int row, int col) {
        if(inBounds(row, col)){
            board = board - shift(1, row*8 + col);
        }
    }


    public boolean inBounds(int row, int col) {
        // Should not exceed the 8 x 5 size of the board
        return row >= 0 && row < 5 && col >= 0 && col < 8;
    }

    public long get(){
        return board;
    }

    public void getByte(){
        long temp = board;
        for(int i = 0; i < 40; i++){
            if(i%8 == 0){
                System.out.println("--");
            }
            System.out.println(temp%2);
            temp = temp/2;
        }
    }


public static void main(String[] args) {
        BitboardADT board = new BitboardADT(12);
        long a = 8L;
        long b = 01L;
        long c = a^b;
        System.out.println(board.get());
        board.getByte();
        System.out.println(b);
        System.out.println(c);
    }
}