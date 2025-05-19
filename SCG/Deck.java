package SCG; 

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.awt.geom.Rectangle2D;

// Creating decks


public class Deck extends BoardSquare {
	public ArrayList<Card> deck = new ArrayList<Card>();
	private int index;
	private int width;

// Init
	public Deck(int index, int width) {
    	super(index, width);
    	deck = new ArrayList<Card>();
        this.index = index;
        this.width = width;

    }

    // checks if boardspace is Deck
    @ Override public boolean isDeck() {
    	return true;
    }

    // checks if deck is empty
    public boolean isEmpty() {
        return deck.size() != 0;
    }

    // moves card into the deck
    @Override
    public void setCard(Card play) {
        deck.add(play);
    }

    // draws a card from the deck
    
    @Override public Card release() {
    	System.out.println("got card");
    	Random rand = new Random();
    	int index = rand.nextInt(deck.size());
        Card play = deck.get(index);
        deck.remove(index);
        return play;
    }
}