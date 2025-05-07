import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class Deck<T> extends ArrayList<T>{

	public Deck(){
		super(); 
	}

	public void Shuffle(){
		Random random = new Random();
		int index = 0;

		deckNum = this.size();
		Deck<T> tempDeck = new Deck<T>;

		for(int i = deckNum; i > 0; i--){
			index = random.nextInt(i);
			tempDeck.add(this.get(i));
			this.remove(i);
		}

		this = tempDeck;
	}
}