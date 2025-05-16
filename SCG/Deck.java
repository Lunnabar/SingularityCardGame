package SCG; 

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.awt.geom.Rectangle2D;

// Creating decks


public class Deck extends Rectangle2D.Double {
	private ArrayList<Card> deck;
	private int index;
	private int width;

// Init
	public Deck(int index, int width) {
    	super(index * width, 0, width, width);
    	deck = new ArrayList<Card>();
        this.index = index;
        this.width = width;
    }

    public boolean isEmpty() {
        return deck.size() != 0;
    }

    public void setCard(Card play) {
        deck.add(play);
    }

    public Card release() {
    	Random rand = new Random();
    	int index = rand.nextInt(deck.size());
        Card play = deck.get(index);
        deck.remove(index);
        return play;
    }
}