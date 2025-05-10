package SCG; 
import java.awt.geom.Rectangle2D;
import javax.swing.JFrame;

public class Card extends Rectangle2D.Double{

	private JFrame board;
	private int diameter;

	public Card(JFrame Gameboard, int diam) {
		super(diam / 2, diam / 2, diam, diam * 1.414);
        board = Gameboard;
        diameter = diam;
    }

    public Card(JFrame Gameboard, int diam, int xCoord, int yCoord) {
        super(xCoord - diam / 2, yCoord - diam / 2, diam, diam * 1.414);
        board = Gameboard;
        diameter = diam;
    }

    public void moveTo(int xCoord, int yCoord) {
        setFrame(xCoord - diameter / 2, yCoord - diameter / 2, diameter, diameter * 1.414);
        board.repaint();
    }

}