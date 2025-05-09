package SCG; 


public class GameBoard {
    // Player 1 deck:           row 0, col 0
    // Player 1 resource zone:  row 1, col 0
    // Player 1 spell/trap:     row 0, col 2-6
    // Player 1 monster zone:   row 1, col 2-6
    // Player 1 graveyard:      row 0, col 7
    // Player 1 banishment:     row 1, col 7

    // Poker deck:              row 2, col 0
    // Poker zone:              row 0-4, col 1

    // Uno deck:                row 2, col 2
    // Extra monster zone       row 2, col 3 & 5
    // Uno play zone            row 2, col 4
    // Community chest          row 2, col 6

    // Player 1 deck:           row 4, col 0
    // Player 1 resource zone:  row 3, col 0
    // Player 1 spell/trap:     row 4, col 2-6
    // Player 1 monster zone:   row 3, col 2-6
    // Player 1 graveyard:      row 4, col 7
    // Player 1 banishment:     row 3, col 7    

    private BitboardADT Player1_Deck = new BitboardADT(0);
    private BitboardADT Player1_Resource = new BitboardADT(256);
    private BitboardADT Player1_SpellTrap = new BitboardADT(124);
}