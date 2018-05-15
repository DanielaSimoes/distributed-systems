/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generalRepository;

/**
 *
 * @author Daniela
 */
public class Bet {
    
    private final int horseId;
    private final int amount;
    private final int spectatorId;
    private final double odd;
    
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