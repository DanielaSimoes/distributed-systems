package GeneralRepository;

import java.io.Serializable;

/**
 * This file describes the bet.
 * @author Daniela Simões, 76771
 */
public class Bet implements Serializable  {
    
    private static final long serialVersionUID = 1001L;
    private int horseId;
    private int amount;
    private int spectatorId;
    private double odd;
    
    /**
    * Bet constructor
    * @param horseId
    * @param amount
    * @param spectatorId
    * @param odd
    */
    public Bet(int horseId, int amount, int spectatorId, double odd){
        this.horseId = horseId;
        this.amount = amount;
        this.spectatorId = spectatorId;
        this.odd = odd;
    }
    
    /**
    * Method to return horse ID
    * @return
    */
    public int getHorseId(){
        return this.horseId;
    }
    
    /**
    * Method to return the amount of the bet.
    * @return
    */
    public int getAmount(){
        return this.amount;
    }
    
    /**
    * Method to retrieve de spectator ID.
    * @return
    */
    public int getSpectatorId(){
        return this.spectatorId;
    }
    
    /**
    *  Method to retrieve the odd of the bet.
    * @return
    */
    public double getOdd(){
        return this.odd;
    }
    
}
