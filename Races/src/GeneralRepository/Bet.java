package GeneralRepository;

/**
 * This file describes the bet.
 * @author Daniela Simões, 76771
 */
public class Bet {
    
    private int horseId;
    private double amount;
    private int spectatorId;
    private double odd;
    
    /**
     * Bet constructor
     * @param horseId
     * @param amount
     * @param spectatorId
     * @param odd
     */
    public Bet(int horseId, double amount, int spectatorId, double odd){
        this.horseId = horseId;
        this.amount = amount;
        this.spectatorId = spectatorId;
        this.odd = odd;
    }
    
    /**
     *  Method to return horse ID
     * @return
     */
    public int getHorseId(){
        return this.horseId;
    }
    
    /**
     * Method to return the amount of the bet.
     * @return
     */
    public double getAmount(){
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
