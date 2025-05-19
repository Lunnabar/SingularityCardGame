package SCG; 

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.util.*;

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
    private BitboardADT Player1_Banishment =    new BitboardADT(128*256);

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
    private BitboardADT Player2_Banishment =    new BitboardADT(256*256*128*256);


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
    public User Player1;
    public User Player2;

    // Playing board render and mapping
    public BufferedImage bf = new BufferedImage(DISPLAY_WIDTH, DISPLAY_LENGTH, BufferedImage.TYPE_INT_RGB);
    private final Map<String, ArrayList<CardSquare>> boardZones = new HashMap<>();

    public GameBoard() {        
        setTitle("Welcome to the Singularity Game Board");
        setSize(DISPLAY_WIDTH, DISPLAY_LENGTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize user objects
        this.Player1 = new User();
        this.Player2 = new User();
        this.board = new BitboardADT(0);

        this.Player1.Player_Deck = Player1_Deck;
        this.Player1.Resource = Player1_Resource;
        this.Player1.SpellTrap = Player1_SpellTrap;
        this.Player1.Monster = Player1_Monster;
        this.Player1.Graveyard = Player1_Graveyard;
        this.Player1.Banishment = Player1_Banishment;

        this.Player2.Player_Deck = Player2_Deck;
        this.Player2.Resource = Player2_Resource;
        this.Player2.SpellTrap = Player2_SpellTrap;
        this.Player2.Monster = Player2_Monster;
        this.Player2.Graveyard = Player2_Graveyard;
        this.Player2.Banishment = Player2_Banishment;

        CardMouseListener listener = new CardMouseListener();
        addMouseListener(listener);
        addMouseMotionListener(listener);

        setVisible(true);
        
    }

    private void initZones(){
        
    } 

    public boolean gameIsOver(User current_player) {
        // Game is over if the player has no life points
        if (current_player.LifePoints <= 0) {
            return true;
        }

        // Game is over if the player has no poker chips
        if (current_player.PokerChips <= 0) {
            return true;
        }

        // Game is over if the player's deck is empty, check if the bitboard spot is active
        if (current_player.Player_Deck.get() == 0L) {
            return true;
        }

        // Otherwise, the game is not over
        return false;
    }

    // Class to respond to mouse events
    private class CardMouseListener implements MouseListener, MouseMotionListener{
        
        private int origin; // the index of the square when a Card is picked up
        
        public void mousePressed(MouseEvent event) {
            int newX = event.getX();
            int newY = event.getY();
            
            if (clickedOnCard(newX, newY)) {
                int squareIndex = getCardSquareIndex(newX, newY);
                playing_card = board.get(squareIndex).release();
                playing_card.moveTo(newX, newY);
                origin = squareIndex;   
            } 
            else {
                playing_card = null;
            }
        }

        private boolean clickedOnCard(int x, int y) {
            return board.get(getCardSquareIndex(x,y)).isOccupied(); 
        }

        private boolean clickedOnDeck(int x, int y) {
            return board.get(getCardSquareIndex(x,y)).isDeck(); 
        }

        private boolean isValidCardDrop(int x, int y) {
           // NEEDS TO BE UPDATED TO DETERMINE IF IN PLAYERS AREA
        }
        
        /**
         * Method mouseReleased is called when the mouse
         * button is released.
         * 
         * If a Card is released in a square, and that square
         * is not already occupied and the move satisfies
         * the rules of the game, then the Card is placed
         * into the square. Otherwise, the Card is returned
         * to its original square. 
         */
        public void mouseReleased(MouseEvent event) {
            int newX = event.getX();
            int newY = event.getY();
                        
            if (isValidCardDrop(newX, newY)) {
                int squareIndex = getCardSquareIndex(newX, newY);
                board.get(squareIndex).setCard(playing_card);
            }

            // Give up the Card, if any, that was dragged; has been moved to some square
            playing_card = null;
        }

        public void mouseDragged(MouseEvent event) {
            if (playing_card != null){
                playing_card.moveTo(event.getX(), event.getY());
            }
            repaint();
        }

        
        // Methods are required by the interfaces
        public void mouseClicked(MouseEvent event) {}
        public void mouseEntered(MouseEvent event) {}
        public void mouseExited(MouseEvent event) {}
        public void mouseMoved(MouseEvent event) {}
    

    public static void main(String[] args) {
        GameBoard f = new GameBoard();
    }
}

