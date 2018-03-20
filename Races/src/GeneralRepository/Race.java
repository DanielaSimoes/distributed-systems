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
    
    private final String winner;
    private final int id;
    
    public Race(int id){
        this.id = id;
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
