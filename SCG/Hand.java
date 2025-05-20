package SCG; 

import java.awt.geom.Rectangle2D;
import javax.swing.JFrame;
import java.util.ArrayList;
import java.util.Scanner;

// Creating a player card

public class Hand extends Rectangle2D.Double {
	public ArrayList<Card> hand;
	private BitboardADT index;
	private int width;

// Init
	public Hand(ArrayList<Card> hand,int width) {
		// sets up an area above the gameboard for hand
    	super(0, 5*width, 8*width, width);
        this.hand = hand;
        this.width = width;
        int stretch = width;

        // if too many cards, squeezes the cards to fit the hand space
        if(hand.size() > 16){
        	stretch = stretch*16/hand.size();
        }
        for(int i = 0; i < hand.size(); i++){
        	hand.get(i).setTo(stretch*i);
        }
    }

    // gets card from the hand
    public Card getCard(){
    	Scanner userInput = new Scanner(System.in);
    	System.out.println("\nWhich card to play?");
    	int card_number = Integer.parseInt(userInput.nextLine());
    	Card play = hand.get(card_number%hand.size());
    	hand.remove(card_number);
    	setFrame(0, 0, 8 * width, width);
        int stretch = width;

        if(hand.size() > 16){
        	stretch = stretch*16/hand.size();
        }

        for(int i = 0; i < hand.size(); i++){
        	hand.get(i).setTo(stretch*i);
        }
    	return play;
    }

}