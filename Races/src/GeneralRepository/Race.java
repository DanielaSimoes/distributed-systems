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
public class Race {
    
    private String winner;
    private String[] horses;
    private int ID;
    
    public Race(int id, String[] horses){
        this.ID = id;
        this.horses = horses;
        this.winner = null;
    }
    
    
    /* this method will be the race itself and choose the winner*/
    public String competition(){
        return "";
    }
    
    public String getWinner(){
        return this.winner;
    }
    
}
