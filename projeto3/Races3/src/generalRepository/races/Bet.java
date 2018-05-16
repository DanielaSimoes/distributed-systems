package generalRepository.races;

import java.io.Serializable;

/**
 * This file describes the bet.
 * @author Daniela Simes, 76771
 */
public class Bet implements Serializable  {
    
    private static final long serialVersionUID = 1001L;
    private int horseId;
    private int horseRaceId;
    private int amount;
    private int spectatorId;
    private double odd;
    
    /**
    * Bet constructor
    * @param horseId
    * @param amount
    * @param spectatorId
    * @param odd
     * @param horseRaceId
    */
    public Bet(int horseId, int amount, int spectatorId, double odd, int horseRaceId){
        this.horseId = horseId;
        this.horseRaceId = horseRaceId;
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
    * Method to return horse ID
    * @return
    */
    public int getHorseRaceId(){
        return this.horseRaceId;
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
