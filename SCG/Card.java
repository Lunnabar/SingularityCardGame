package SCG; 
import java.awt.geom.Rectangle2D;
import javax.swing.JFrame;

public class Card extends Rectangle2D.Double{

	private JFrame board;
	private int width;

	public Card(JFrame Gameboard, int width) {
		super(width/4, width*5/6, width/2, width*2/3);
        board = Gameboard;
    }

    public Card(JFrame Gameboard, int width, int row, int column) {
        super(column*width+ width/4,row*width+ width*5/6, width/2, width*2/3);
        board = Gameboard;
    }

    public void moveTo(int row, int column) {
        setFrame(column*width+ width/4,row*width+ width*5/6, width/2, width*2/3);
        board.repaint();
    }
}