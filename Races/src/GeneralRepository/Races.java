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
        
        for(int i=0; i<N_OF_RACES; i++){
            this.races[i] = new Race(i);
        }
        
    }
    
    public static Races getInstace(){
        if (instance == null){
            instance = new Races();
        }
        
        return instance;
    }
    
    public void newRaceContext(){
        assert raceNumber < this.races.length;
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
    
    public void makeAMove(int horseId){
        this.races[this.raceNumber].makeAMove(horseId);
    }
    
    public boolean nextMovingHorse(int horseJockeyId){
        return this.races[this.raceNumber].nextMovingHorse(horseJockeyId);
    }
    
    public boolean horseFinished(int horseId){
        return this.races[this.raceNumber].horseFinished(horseId);
    }
    
    public boolean horsesFinished(){
        return this.races[this.raceNumber].horsesFinished();
    }
    
    
    public int getNRunningHorses(){
        return this.races[this.raceNumber].getNRunningHorses();
    }
    
    /* condition states */
    /* Racing Track */
    public synchronized boolean getStartTheRace(){
        if(this.raceNumber==-1){
            return false;
        }
        return this.races[this.raceNumber].getStartTheRace();
    }
    
    public synchronized void setStartTheRace(boolean startTheRace){
        this.races[this.raceNumber].setStartTheRace(startTheRace);
    }
    /* Stable */
    public synchronized int getWakedHorsesToPaddock(){
        if(this.raceNumber==-1){
            return 0;
        }
        
        return this.races[this.raceNumber].getWakedHorsesToPaddock();
    }
    
    public synchronized void addWakedHorsesToPaddock(){
        this.races[this.raceNumber].addWakedHorsesToPaddock();
    }
    
    public synchronized boolean getWakeEntertainTheGuests(){
        if(this.raceNumber==-1){
            return false;
        }
        return this.races[this.raceNumber].getWakeEntertainTheGuests();
    }
    
    public synchronized void setWakeEntertainTheGuests(boolean wakeEntertainTheGuests){
        this.races[this.raceNumber].setWakeEntertainTheGuests(wakeEntertainTheGuests);
    }
    
    public synchronized boolean getAnnuncedNextRace(){
        if (this.raceNumber==-1){
            return false;
        }
        return this.races[this.raceNumber].getAnnuncedNextRace();
    }
    
    public synchronized void setAnnuncedNextRace(boolean annuncedNextRace){
        this.races[this.raceNumber].setAnnuncedNextRace(annuncedNextRace);
    }
    /* Paddock */
    public synchronized boolean allSpectatorsArrivedAtPaddock(){
        if(this.raceNumber==-1){
            return false;
        }
        return this.races[this.raceNumber].allSpectatorsArrivedAtPaddock();
    }
    
    public synchronized void addNSpectatorsArrivedAtPaddock(){
        this.races[this.raceNumber].addNSpectatorsArrivedAtPaddock();
    }
    
    public synchronized boolean allHorseJockeyLeftThePadock(){
        if(this.raceNumber==-1){
            return false;
        }
        return this.races[this.raceNumber].allHorseJockeyLeftThePadock();
    }
    
    public synchronized void addNHorseJockeyLeftThePadock(){
        this.races[this.raceNumber].addNHorseJockeyLeftThePadock();
    }
    
    /* Control Centre */
    public synchronized void setReportResults(boolean set){
        this.races[this.raceNumber].setReportResults(set);
    }
    
    public synchronized boolean getReportResults(){
        if(this.raceNumber==-1){
            return false;
        }
        
        return this.races[this.raceNumber].getReportResults();
    }
    
    public synchronized void setProceedToPaddock(boolean set){
        this.races[this.raceNumber].setProceedToPaddock(set);
    }
    
    public synchronized boolean getProceedToPaddock(){
        if(this.raceNumber==-1){
            return false;
        }
        
        return this.races[this.raceNumber].getProceedToPaddock();
    }
    
    public synchronized boolean allNHorsesInPaddock(){
        if(this.raceNumber==-1){
            return false;
        }
        
        return this.races[this.raceNumber].allNHorsesInPaddock();
    }
    
    public synchronized void addNHorsesInPaddock(){
        this.races[this.raceNumber].addNHorsesInPaddock();
    }
    
    /* Betting Centre */
    public synchronized boolean getBetsOfSpectator(int i){
        if(this.raceNumber==-1){
            return false;
        }
        
        return this.races[this.raceNumber].getBetsOfSpectator(i);
    }
    
    public synchronized void setBetsOfSpectator(int i, boolean set){
        this.races[this.raceNumber].setBetsOfSpectator(i, set);
    }
    
    public synchronized boolean getAcceptedTheBet(int i){
        if(this.raceNumber==-1){
            return false;
        }
        
        return this.races[this.raceNumber].getAcceptedTheBet(i);
    }
    
    public synchronized void setAcceptedTheBet(int i, boolean set){
        this.races[this.raceNumber].setAcceptedTheBet(i, set);
    }
    
    public synchronized boolean getWaitingToBePaidSpectators(int i){
        if(this.raceNumber==-1){
            return false;
        }
        
        return this.races[this.raceNumber].getWaitingToBePaidSpectators(i);
    }
    
    public synchronized void setWaitingToBePaidSpectators(int i, boolean set){
        this.races[this.raceNumber].setWaitingToBePaidSpectators(i, set);
    }
    
    public synchronized boolean getPaidSpectators(int i){
        if(this.raceNumber==-1){
            return false;
        }
        
        return this.races[this.raceNumber].getPaidSpectators(i);
    }
    
    public synchronized void setPaidSpectators(int i, boolean set){
        this.races[this.raceNumber].setPaidSpectators(i, set);
    }
    /* end condition states */
}
