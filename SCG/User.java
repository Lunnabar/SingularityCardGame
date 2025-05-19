package SCG; 

import java.util.ArrayList;

public class User{
    public BitboardADT Player_Deck;
    public BitboardADT Resource;
    public BitboardADT SpellTrap;
    public BitboardADT Monster;
    public BitboardADT Graveyard;
    public BitboardADT Banishment;
    public int LifePoints = 8000;
    public int PokerChips = 1000;
    public ArrayList<Card> User_hand;

}