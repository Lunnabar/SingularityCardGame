package SCG; 

import java.awt.geom.Rectangle2D;
import javax.swing.JFrame;
import java.util.ArrayList;
import java.util.Scanner;

// Creating a player card

public class Hand extends Rectangle2D.Double {
	private ArrayList<Card> hand;
	private BitboardADT index;
	private int width;

// Init
	public Hand(ArrayList<Card> hand,int width) {
    	super(0, 0, 8*width, width);
        this.hand = hand;
        this.width = width;
        int stretch = width;

        if(hand.size() > 16){
        	stretch = stretch*16/hand.size();
        }

        for(int i = 0; i < hand.size(); i++){
        	hand.get(i).setTo(stretch*i);
        }
    }

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