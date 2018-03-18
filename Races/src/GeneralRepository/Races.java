package GeneralRepository;

/**
 *
 * @author Daniela
 */

import java.util.HashMap;

import entities.BrokerState;
import entities.HorseJockeyState;
import entities.SpectatorsState;

public class Races {
    
    public static final int N_OF_RACES = 4;
    public static final int N_OF_HORSES = 5;
    public static final int N_OF_SPECTATORS = 4;
    public static final int SIZE_OF_RACING_TRACK = 21;
    public static int actual_race = 0;
    
    private final HashMap<Integer, Integer> horsesPosition;
    
    private static Races instance = null;
    
    
    private Races(){
        this.horsesPosition = new HashMap<>();
        
    }
    
    public static Races getInstace(){
        if (instance == null){
            instance = new Races();
        }
        
        return instance;
    }
    
    public void updateHorsePosition(int horse){
        
    }
}
