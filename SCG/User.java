package SCG; 

public class User{
    public BitboardADT Player_Deck;
    public BitboardADT Resource;
    public BitboardADT SpellTrap;
    public BitboardADT Monster;
    public BitboardADT Graveyard;
    public BitboardADT Banishmemt;
    public int LifePoints = 8000;
    public int PokerChips = 1000;
    public Hand hand = new Hand();

}