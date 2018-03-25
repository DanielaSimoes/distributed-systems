/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GeneralRepository;

/**
 *
 * @author Daniela
 */
public class Bet {
    
    private int horseId;
    private double amount;
    private int spectatorId;
    private double odd;
    
    public Bet(int horseId, double amount, int spectatorId, double odd){
        this.horseId = horseId;
        this.amount = amount;
        this.spectatorId = spectatorId;
        this.odd = odd;
    }
    
    public int getHorseId(){
        return this.horseId;
    }
    
    public double getAmount(){
        return this.amount;
    }
    
    public int getSpectatorId(){
        return this.spectatorId;
    }
    
    public double getOdd(){
        return this.odd;
    }
    
}
