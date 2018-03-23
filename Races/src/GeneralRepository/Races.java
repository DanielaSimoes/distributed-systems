package GeneralRepository;

/**
 *
 * @author Daniela
 */

import java.util.HashMap;

import entities.BrokerState;
import entities.HorseJockeyState;
import entities.SpectatorsState;
import entities.HorseJockey;

public class Races {
    
    public static final int N_OF_RACES = 1;
    public static final int N_OF_HORSES = 5;
    public static final int N_OF_SPECTATORS = 4;
    public static final int SIZE_OF_RACING_TRACK = 14;
        
    private static Races instance = null;
    
    private final HashMap<Integer, SpectatorsState> spectatorsState;
    private final HashMap<Integer, Integer> spectatorAmmount;
    private final HashMap<Integer, HorseJockeyState> horseJockeysState;
    private BrokerState brokerState;
    public Race[] races;
    private int raceNumber = -1;
    
    /*
    double BS[] = new double[4];
    double BA[] = new double[4];
    double ODD[] = new double[4];
    int N[] = new int[4];
    double Ps[] = new double[4];
    int SD[] = new int[4];
    */
    
    private Races(){
        this.spectatorsState = new HashMap<>();
        this.horseJockeysState = new HashMap<>();
        this.spectatorAmmount = new HashMap<>();
        this.races = new Race[N_OF_RACES];
        
        // create new Race to save states
        this.newRace();
    }
    
    public static Races getInstace(){
        if (instance == null){
            instance = new Races();
        }
        
        return instance;
    }
    
    public void newRace(){
        assert raceNumber < this.races.length;
        this.races[this.raceNumber+1] = new Race(raceNumber);
        this.raceNumber += 1;
        System.out.println("New race " + this.raceNumber);
    }
    
    public boolean horseHasBeenSelectedToRace(HorseJockey horseJockey){
        return this.races[raceNumber].horseHasBeenSelectedToRace(horseJockey);
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
    
    public int getWinner(){
        return races[this.raceNumber].getWinner();
    }
    
    public Race getRace(){
        return this.races[this.raceNumber];
    }
    
    public Race getRace(int n){
        if(this.raceNumber==-1){
            return null;
        }
        return this.races[n];
    }
  
    public boolean hasMoreRaces(){
        boolean hasMore = !((this.raceNumber == Races.N_OF_RACES - 1) && this.races[this.raceNumber].horsesFinished());
        //System.out.println("HasMoreRaces: " + hasMore + " | running horses: " + this.races[this.raceNumber].getNRunningHorses());
        return hasMore;
    }
}
