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
public class Races {
    
    public static final int N_OF_RACES = 4;
    public static final int N_OF_HORSES = 4;
    public static int actual_race = 0;
    
    private static Races instance = null;
    
    public static Races getInstace(){
        if (instance == null){
            instance = new Races();
        }
        
        return instance;
    }
    
    
}
