package SCG; 
import java.awt.geom.Rectangle2D;
import javax.swing.JFrame;

public class Card extends Rectangle2D.Double{

	private JFrame board;
	private int width;

    // inits card
	public Card(JFrame Gameboard, int width) {
		super(width/4, width*5/6, width/2, width*2/3);
        board = Gameboard;
    }

    // inits card in row and column position
    public Card(JFrame Gameboard, int width,int x, int y) {
        super(x- width/4,y + width*1/3, width/2, width*2/3);
        board = Gameboard;
    }

    // moves card
    public void moveTo(int x, int y) {
        setFrame(x- width/4,y + width*1/3, width/2, width*2/3);
        board.repaint();
    }

    // sets card to postion in hand
    public void setTo(int position){
        setFrame(position,6*width,width/2, width*2/3);
        board.repaint();
    }
}