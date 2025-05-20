package SCG;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

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
    public static final int   CARD_SIZE = SQUARE_SIZE;
    public static final Color BACKGROUND_COLOR = Color.LIGHT_GRAY;
    public static final Color BOUNDARY_COLOR = Color.BLACK;
    public static final Color PLAYING_COLOR = Color.GREEN;
    public static final Color CARD_COLOR = Color.RED;
    public static final int DISPLAY_WIDTH = SQUARE_SIZE * 8;
    public static final int DISPLAY_LENGTH = SQUARE_SIZE * 6;

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
    public User Current_User = Player1;
    public Hand player_hand;



    // Buffered image creation
    public BufferedImage bf = new BufferedImage(DISPLAY_WIDTH, DISPLAY_LENGTH, BufferedImage.TYPE_INT_RGB);

    public GameBoard() {        
        setTitle("Welcome to the Singularity Game Board");
        setSize(DISPLAY_WIDTH, DISPLAY_LENGTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initSquares();

        CardMouseListener listener = new CardMouseListener();
        addMouseListener(listener);
        addMouseMotionListener(listener);

        setVisible(true);
    }

    private void initSquares() {
        // initiallizes board
        for (int i = 0; i < 40; i++) {
            if(i == 0 || i == 7 || i == 15 || i == 8 || i == 16 || i == 18 || i == 22 || i == 32 || i == 24 || i == 31 || i == 39){
                BoardSquare deck = new Deck(i, SQUARE_SIZE);
                for(int j = 0; j < 75; j++){
                    Card card = new Card(this, CARD_SIZE);
                    deck.setCard(card);
                }
                squares.add(deck);
            }
            else{
                BoardSquare square = new BoardSquare(i, SQUARE_SIZE);
                Card card = new Card(this, CARD_SIZE);
                square.setCard(card);
                squares.add(square);
            }
        }

        //initalizes Users
        Player1.User_hand = new ArrayList<Card>();
        Player1.Player_Deck = Player1_Deck;
        Player1.Resource = Player1_Resource;
        Player1.SpellTrap = Player1_SpellTrap;
        Player1.Monster = Player1_Monster;
        Player1.Graveyard = Player1_Graveyard;
        Player1.Banishment = Player1_Banishment;

        Player2.User_hand = new ArrayList<Card>();
        Player2.Player_Deck = Player2_Deck;
        Player2.Resource = Player2_Resource;
        Player2.SpellTrap = Player2_SpellTrap;
        Player2.Monster = Player2_Monster;
        Player2.Graveyard = Player2_Graveyard;
        Player2.Banishment = Player2_Banishment;

        player_hand = new Hand(Current_User.User_hand, SQUARE_SIZE);

        // Lets start by having a card on Player1's Deck that can be moved
        Card sampleCard = new Card(this, SQUARE_SIZE);
        squares.get(0).setCard(sampleCard);
    }

    // Helper method to create zone lists where index is and apply color
    private ArrayList<BoardSquare> setZone(List<Integer> indices, Color color) {
        ArrayList<BoardSquare> zone = new ArrayList<>();
        Graphics2D g2 = (Graphics2D) bf.getGraphics();
        g2.setPaint(color);
        for (int index : indices) {
            BoardSquare square = squares.get(index);
            g2.fill(square);
            zone.add(square);
        }
        return zone;
    }

    
    private int getCardSquareIndex(int x, int y) {
        for (int i = 0; i < squares.size(); i++) {
            if (squares.get(i).contains(x,y)) {
                return i;
            }
        }
        return -1;
    }

    private boolean isValidCardDrop(int x, int y, User player) {
        int index = getCardSquareIndex(x, y);

        if(player_hand.contains(x,y)){
            return true;
        }

        if (index == -1){
            return false;
        }

        // can't have multiple squares in one tile
        if(squares.get(index).isOccupied()){
            return false;
        }


        // checks the bitboards to see if board space is playable
        BitboardADT CardDrop = new BitboardADT(0);
        CardDrop.setPosition(index / 8, index % 8);

        if((CardDrop.get() & player.Player_Deck.get()) >0){
            return true;
        }
        if((CardDrop.get() & player.Resource.get()) >0){
            return true;
        }
        if((CardDrop.get() & player.SpellTrap.get()) > 0){
            return true;
        }
        if((CardDrop.get() & player.Monster.get()) > 0){
            return true;
        }
        if((CardDrop.get() & player.Graveyard.get()) > 0){
            return true;
        }
        if((CardDrop.get() & player.Banishment.get()) > 0){
            return true;
        }
        if((CardDrop.get() & Poker_Deck.get()) > 0){
            return true;
        }
        if((CardDrop.get() & Poker_Zone.get()) > 0){
            return true;
        }
        if((CardDrop.get() & Uno_Zone.get()) > 0){
            return true;
        }

        // special case for extra monster zone
        if((CardDrop.get() & ExtraMonster_Zone.get()) > 0){
            if((player.Monster.get() & ExtraMonster_Zone.get()) > 0){
                return false;
            }
            else{
                player.Monster = new BitboardADT(player.Monster.get() | CardDrop.get());
                return true;
            }
        }

        return false;
    }

    // Class to respond to mouse events
    private class CardMouseListener implements MouseListener, MouseMotionListener {

        public void mousePressed(MouseEvent event) {
            int x = event.getX();
            int y = event.getY();

            if(player_hand.contains(x,y)){
            playingCard = player_hand.getCard();
            Current_User.User_hand = player_hand.hand;
            originIndex = -2;
            }

            int index = getCardSquareIndex(x, y);
            if(index != -1 && squares.get(index).isOccupied()) {
                //System.out.println("is boardZones");

                // Extra case for extra monster zone
                BitboardADT CardZone = new BitboardADT(0);
                CardZone.setPosition(index / 8, index % 8);
                if((CardZone.get() & ExtraMonster_Zone.get()) > 0){
                    Current_User.Monster.clearPosition(index / 8, index%8);
                }

                playingCard = squares.get(index).release();
                originIndex = index;
                playingCard.moveTo(x, y);
            }
            if(index != -1 && squares.get(index).isDeck()) {
                //System.out.println("is deck");
                playingCard = squares.get(index).release();
                originIndex = index;
                playingCard.moveTo(x, y);
            }
            if(index == 23){
                Current_User.User_hand = player_hand.hand;
                if(Current_User == Player1){
                    Current_User = Player2;
                }
                if(Current_User == Player2){
                    Current_User = Player1;
                }
                player_hand = new Hand(Current_User.User_hand, SQUARE_SIZE);
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
            if (isValidCardDrop(x, y, Current_User)) {
                int index = getCardSquareIndex(x, y);
                squares.get(index).setCard(playingCard);
            }
            else if (originIndex > -1) {
                // Return card to the original square
                squares.get(originIndex).setCard(playingCard);
            }
            else if(originIndex == -2){
                Current_User.User_hand = player_hand.hand;
                Current_User.User_hand.add(playingCard);
                player_hand = new Hand(Current_User.User_hand, SQUARE_SIZE);
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


        // Making it clear where zones are and color coding them
        // Player 1 Zones
        boardZones.put("Player1_Deck", setZone(Arrays.asList(0), Color.RED));           // row 0, col 0
        boardZones.put("Player1_Resource", setZone(Arrays.asList(8), Color.ORANGE));    // row 1, col 0
        boardZones.put("Player1_SpellTrap", setZone(Arrays.asList(2, 3, 4, 5, 6), Color.YELLOW)); // row 0, col 2-6
        boardZones.put("Player1_Monster", setZone(Arrays.asList(10, 11, 12, 13, 14), Color.GREEN)); // row 1, col 2-6
        boardZones.put("Player1_Graveyard", setZone(Arrays.asList(7), Color.DARK_GRAY)); // row 0, col 7
        boardZones.put("Player1_Banishment", setZone(Arrays.asList(15), Color.LIGHT_GRAY)); // row 1, col 7

        // Poker, Uno, Monopoly Community Chest, and Extras
        boardZones.put("Poker_Deck", setZone(Arrays.asList(16), Color.BLUE)); // row 2, col 0
        boardZones.put("Poker_Zone", setZone(Arrays.asList(1, 9, 17, 25, 33), Color.CYAN)); // col 1, rows 0–4

        boardZones.put("Uno_Deck", setZone(Arrays.asList(18), Color.MAGENTA)); // row 2, col 2
        boardZones.put("ExtraMonster", setZone(Arrays.asList(19, 21), Color.PINK)); // row 2, col 3 & 5
        boardZones.put("Uno_Zone", setZone(Arrays.asList(20), Color.LIGHT_GRAY)); // row 2, col 4
        boardZones.put("CommunityChest", setZone(Arrays.asList(22), Color.ORANGE)); // row 2, col 6

        // Player 2 Zones
        boardZones.put("Player2_Deck", setZone(Arrays.asList(32), Color.RED));           // row 4, col 0
        boardZones.put("Player2_Resource", setZone(Arrays.asList(24), Color.ORANGE));    // row 3, col 0
        boardZones.put("Player2_SpellTrap", setZone(Arrays.asList(34, 35, 36, 37, 38), Color.YELLOW)); // row 4, col 2-6
        boardZones.put("Player2_Monster", setZone(Arrays.asList(26, 27, 28, 29, 30), Color.GREEN)); // row 3, col 2-6
        boardZones.put("Player2_Graveyard", setZone(Arrays.asList(39), Color.DARK_GRAY)); // row 4, col 7
        boardZones.put("Player2_Banishment", setZone(Arrays.asList(31), Color.LIGHT_GRAY)); // row 3, col 7


        // Draw squares
        for (BoardSquare s : squares) {
            g2.setColor(BOUNDARY_COLOR);
            g2.draw(s);
            if (s.isOccupied()) {
                g2.draw(s.getCard());
            }
        }

        // Draw dragged card on top
        if (playingCard != null) {
            g2.setColor(PLAYING_COLOR);
            g2.fill(playingCard);
        }

        for (int i = 0; i < squares.size(); i++) {
            g2.setColor(PLAYING_COLOR);
            if (squares.get(i).isOccupied()) {
                System.out.println("colored");
                g2.fill(squares.get(i).getCard());
            }
        }

        // Draw buffer to screen
        g.drawImage(bf, 0, 0, null);
    }
    
    public static void main(String[] args) {

        GameBoard f = new GameBoard();

    }
}

