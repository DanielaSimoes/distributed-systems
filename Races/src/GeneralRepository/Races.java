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
    
    private final HashMap<Integer, SpectatorsState> spectatorsState;
    private final HashMap<Integer, Integer> spectatorAmmount;
    private final HashMap<Integer, HorseJockeyState> horseJockeysState;
    private BrokerState brokerState;
    private final Race[] races;
    private int raceNumber;
    
    /*
    int raceNumber;
    int lengthStep[] = new int[4];
    int raceNumber2;
    int distance;
    double BS[] = new double[4];
    double BA[] = new double[4];
    double ODD[] = new double[4];
    int N[] = new int[4];
    double Ps[] = new double[4];
    int SD[] = new int[4];
    */
    
    private Races(){
        this.horsesPosition = new HashMap<>();
        this.spectatorsState = new HashMap<>();
        this.horseJockeysState = new HashMap<>();
        this.spectatorAmmount = new HashMap<>();
        this.races = new Race[N_OF_RACES];
    }
    
    public static Races getInstace(){
        if (instance == null){
            instance = new Races();
        }
        
        return instance;
    }
    
    public void newRace(){
        assert raceNumber < this.races.length;
        
        this.races[raceNumber] = new Race(raceNumber++);
    }
    
    public void setHorseJockeyState(int id, HorseJockeyState state){
        if(this.horseJockeysState.containsKey(id)){
            this.horseJockeysState.replace(id, state);
        }else{
            this.horseJockeysState.put(id, state);
        }
    }
    
    public HorseJockeyState getHorseJockeyState(int id){
        return this.horseJockeysState.get(id);
    }

    public void setBrokerState(BrokerState state){
        this.brokerState = state;
    }
    
    public BrokerState getBrokerState(){
        return this.brokerState;
    }
    
    public void setSpectatorState(int id, SpectatorsState state){
        if(this.spectatorsState.containsKey(id)){
            this.spectatorsState.replace(id, state);
        }else{
            this.spectatorsState.put(id, state);
        }
    }
    
    public SpectatorsState getSpectatorsState(int id){
        return this.spectatorsState.get(id);
    }
    
    public int getRaceNumber(){
        return this.raceNumber;
    }
  
}
