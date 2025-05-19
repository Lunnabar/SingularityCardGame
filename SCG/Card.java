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
    public Card(JFrame Gameboard, int width, int row, int column) {
        super(column*width+ width/4,row*width+ width*5/6, width/2, width*2/3);
        board = Gameboard;
    }

    // moves card to row and column position
    public void moveTo(int row, int column) {
        setFrame(column*width+ width/4,row*width+ width*5/6, width/2, width*2/3);
        board.repaint();
    }

    // sets card to postion in hand
    public void setTo(int position){
        setFrame(position,6*width,width/2, width*2/3);
        board.repaint();
    }
}