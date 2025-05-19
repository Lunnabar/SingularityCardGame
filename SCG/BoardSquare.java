package SCG; 

import java.util.ArrayList;
import java.awt.geom.Rectangle2D;

// Creating each squares on the board

public class BoardSquare extends Rectangle2D.Double {
	private Card card;
	private int index;
	private int width;

// Init
	public BoardSquare(int index, int width) {
    	super((index%8) * width, (index/8)*width, width, width);
    	card = null;
        this.index = index;
        this.width = width;
    }

// Checks for card in square
    public boolean isOccupied() {
        return card != null;
    }

// Sets card in square
    public void setCard(Card play) {
        card = play;
        card.moveTo(index%8,index/8);
    }

// Removes card from square
    public Card release() {
        Card play = card;
        card = null;
        return play;
    }

// Tells what card is in the square
    public Card getCard() {
        return card;
    }
}