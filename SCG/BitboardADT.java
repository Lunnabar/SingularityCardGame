package SCG; 



public class BitboardADT {

    private long board;

    public BitboardADT(long Long){
        board = Long;
    }

    public long shift(long bits, int exponent){
        return bits << exponent; // Bit Shifting in Java
    }

    public void setPosition(int row, int col) {
        if(inBounds(row, col)){
            //board = board + shift(1, row*8 + col);
            board |= shift(1L, row * 8 + col); // Or Operator to Set Bit
        }
    }

    public void clearPosition(int row, int col) {
        if(inBounds(row, col)){
            // board = board - shift(1, row*8 + col);
            board &= ~shift(1L, row * 8 + col); // And Not Operator to Clear Bit
        }
    }

    public boolean inBounds(int row, int col) {
        // Should not exceed the 5row x 8column size of the board
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
        System.out.println("Bitboard object:");
        System.out.println(board);
        System.out.println("b:" + b);
        System.out.println("c" + c);

        board.getByte();

        System.out.println("\nBoard View!");
        viewBoard(board.get());
    }

    public static void viewBoard(long boardValue) {
        // Start from the top row and go down
        for (int row = 4; row >= 0; row--) {
            // For each column going left to right
            for (int col = 0; col < 8; col++) {
                // Calculate the bit index for this row and column position
                int bitIndex = row * 8 + col;
                // Create a mask where only this bitIndex is set to 1
                long mask = 1L << bitIndex;

                // Use bitwise checking if this position is set in boardValue
                if ((boardValue & mask) != 0) {
                    System.out.print("1 ");  // Bit is ON
                }   
                else {
                    System.out.print(". ");  // Bit is OFF
                }
            }
            // Print line by line
            System.out.println();
        }
    }
}