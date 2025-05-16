package SCG; 

import java.awt.geom.Rectangle2D;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import java.util.Random;
import java.util.ArrayList;

public class GameBoard extends JFrame{
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

    private BitboardADT Player1_Deck =          new BitboardADT(1);
    private BitboardADT Player1_Resource =      new BitboardADT(256);
    private BitboardADT Player1_SpellTrap =     new BitboardADT(4+8+16+32+64);
    private BitboardADT Player1_Monster =       new BitboardADT((4+8+16+32+64)*256);
    private BitboardADT Player1_Graveyard =     new BitboardADT(128);
    private BitboardADT Player1_Banishmemt =    new BitboardADT(128*256);

    private BitboardADT Poker_Deck =            new BitboardADT(256*256);
    private BitboardADT Poker_Zone =            new BitboardADT(2 + 256*2 + 256*256*2 + 256*256*256*2 + 256*256*256*256*2);

    private BitboardADT Uno_Deck =              new BitboardADT(4*256*256);
    private BitboardADT Uno_Zone =              new BitboardADT(16*256*256);

    private BitboardADT ExtraMonster_Zone =     new BitboardADT(256*256*(8+32));

    private BitboardADT CommunityChest =        new BitboardADT(256*256*64);

    private BitboardADT Player2_Deck =          new BitboardADT(256*256*256*256*1);
    private BitboardADT Player2_Resource =      new BitboardADT(256*256*256*1);
    private BitboardADT Player2_SpellTrap =     new BitboardADT(256*256*256*256*(4+8+16+32+64));
    private BitboardADT Player2_Monster =       new BitboardADT(256*256*256*(4+8+16+32+64));
    private BitboardADT Player2_Graveyard =     new BitboardADT(256*256*256*256*128);
    private BitboardADT Player2_Banishmemt =    new BitboardADT(256*256*128*256);



    public static final int   SQUARE_SIZE = 120;
    public static final int   CARD_SIZE = SQUARE_SIZE / 2;
    public static final Color BACKGROUND_COLOR = Color.LIGHT_GRAY;
    public static final Color BOUNDARY_COLOR = Color.BLACK;
    public static final Color CARD_COLOR = Color.RED;
    public static final int DISPLAY_WIDTH = SQUARE_SIZE * 8;
    public static final int DISPLAY_LENGTH = SQUARE_SIZE * 5;

    // Sets up board and the card in motion (only moving one card at a time)
    public BitboardADT board;
    public Card playing_card;

    // Playing board render
    public BufferedImage bf = new BufferedImage(DISPLAY_WIDTH, DISPLAY_LENGTH, 
            BufferedImage.TYPE_INT_RGB);

    public static void main(String[] args) {
        BitboardADT board = new BitboardADT(124);
        board.getByte();
    }



}

