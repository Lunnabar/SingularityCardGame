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

    // Player 2 deck:           row 4, col 0
    // Player 2 resource zone:  row 3, col 0
    // Player 2 spell/trap:     row 4, col 2-6
    // Player 2 monster zone:   row 3, col 2-6
    // Player 2 graveyard:      row 4, col 7
    // Player 2 banishment:     row 3, col 7    

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

    // Render the rows of squares and map out the board
    private ArrayList<BoardSquare> squares = new ArrayList<>();
    private Map<String, ArrayList<BoardSquare>> boardZones = new HashMap<>();

    // Sets up board and the card in motion
    public BitboardADT board;
    public Card playingCard = null;
    public int originIndex = -1;

    // Create users to complete code
    public User Player1 = new User();
    public User Player2 = new User();

    // Buffered image creation
    public BufferedImage bf = new BufferedImage(DISPLAY_WIDTH, DISPLAY_LENGTH, BufferedImage.TYPE_INT_RGB);

    public GameBoard() {        
        setTitle("Welcome to the Singularity Game Board");
        setSize(DISPLAY_WIDTH, DISPLAY_LENGTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initSquares();
        labeledZones();

        CardMouseListener listener = new CardMouseListener();
        addMouseListener(listener);
        addMouseMotionListener(listener);

        setVisible(true);

        private void initSquares() {
            for (int i = 0; i < 40; i++) {
                squares.add(new BoardSquare(i, SQUARE_SIZE));
            }

            // Lets start by having a card on Player1's Deck that can be moved
            Card sampleCard = new Card();
            squares.get(0).setCard(sampleCard);
        }

        private void labeledZones() {

            // For Player 1
            boardZones.put("Player1_Deck", new ArrayList<>());
            boardZones.get("Player1_Deck").add(squares.get(0));


            // For Player 2
            boardZones.put("Player2_Deck", new ArrayList<>());
            boardZones.get("Player2_Deck").add(squares.get(33));

           
        }

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


        private int getCardSquareIndex(int x, int y) {
            for (int i = 0; i < squares.size(), i++) {
                if (squares.get(i).contains(x,y)) {
                    return i;
                }
            }
            return -1;
        }

        private boolean isValidCardDrop(int x, int y) {
           int index = getCardSquareIndex(x,y)
           if (index == -1){
            return false;
           }
           return !squares.get(index).isOccupied();
        }

        // Class to respond to mouse events
        private class CardMouseListener implements MouseListener, MouseMotionListener {
        
            public void mousePressed(MouseEvent event) {
                int x = event.getX();
                int y = event.getY();
                int index = getCardSquareIndex(x, y);
                if (index != -1 && squares.get(index).isOccupied()) {
                    playingCard = squares.get(index).release();
                    originIndex = index;
                    playingCard.moveTo(x, y);
                }
                else {
                    playingCard = null;
                    originIndex = -1;
                }
                repaint();
            }

            public void mouseReleased(MouseEvent event) {
                if (playingCard == null){
                    return;
                }

                int x = event.getX();
                int y = event.getY();
                if (isValidCardDrop(x, y)) {
                    int index = getCardSquareIndex(x, y);
                    squares.get(index).setCard(playingCard);
                }
                else if (originIndex != -1) {
                    // Return card to the original square
                    squares.get(originIndex).setCard(playingCard);
                }
                playingCard = null;
                originIndex = -1;
                repaint();
            }

            public void mouseDragged(MouseEvent event) {
                if (playingCard != null){
                    playingCard.moveTo(event.getX(), event.getY());
                }
                repaint();
            }
        }

        // Methods are required by the interfaces
        public void mouseClicked(MouseEvent event) {}
        public void mouseEntered(MouseEvent event) {}
        public void mouseExited(MouseEvent event) {}
        public void mouseMoved(MouseEvent event) {}
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = bf.createGraphics();

        // Clear background
        g2.setColor(BACKGROUND_COLOR);
        g2.fillRect(0, 0, DISPLAY_WIDTH, DISPLAY_LENGTH);

        // Draw squares
        for (BoardSquare s : squares) {
            g2.setColor(BOUNDARY_COLOR);
            g2.draw(s);
            if (s.isOccupied()) {
                s.getCard().draw(g2);
            }
        }

        // Draw dragged card on top
        if (playing_card != null) {
            playing_card.draw(g2);
        }

        // Draw buffer to screen
        g.drawImage(bf, 0, 0, null);
        g2.dispose();
    }
    
    public static void main(String[] args) {
        GameBoard f = new GameBoard();
    }
}

