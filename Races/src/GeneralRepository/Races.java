package GeneralRepository;

/**
 * This file describes the set of the Races.
 * @author Daniela Simões, 76771
 */

import java.util.HashMap;

import entities.BrokerState;
import entities.HorseJockeyState;
import entities.SpectatorsState;
import entities.HorseJockey;
import entities.IEntity;


public class Races {
    
    public static final int N_OF_RACES = 5;
    public static final int N_OF_HORSES = 5;
    public static final int N_OF_SPECTATORS = 4;
    public static final int SIZE_OF_RACING_TRACK = 14;
        
    private static Races instance = null;
    
    private final HashMap<Integer, SpectatorsState> spectatorsState;
    private final HashMap<Integer, Integer> spectatorAmmount;
    private final HashMap<Integer, HorseJockeyState> horseJockeysState;
    private BrokerState brokerState;
    public Race[] races;
    
    /*
    double BS[] = new double[4];
    double BA[] = new double[4];
    double ODD[] = new double[4];
    int N[] = new int[4];
    double Ps[] = new double[4];
    int SD[] = new int[4];
    */
    
    /**
    *
    * Races Constructor
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
    
    /**
    *
    * Method to retrieve an instance of Races
    */
    public static Races getInstace(){
        if (instance == null){
            instance = new Races();
        }
        
        return instance;
    }
   
   /**
    *
    * Method to verify if a given horse was selected to a race.
    * @param horseJockey The HorseJockey object.
    */
    public boolean horseHasBeenSelectedToRace(HorseJockey horseJockey){
        int raceNumber = ((IEntity)Thread.currentThread()).getCurrentRace();
        
        return this.races[raceNumber].horseHasBeenSelectedToRace(horseJockey);
    }
    
    /**
    *
    * Method to set the state of a HorseJockey.
    * @param ID The HorseJockey ID.
    * @param state The state to be assigned.
    */
    public void setHorseJockeyState(int id, HorseJockeyState state){
        if(this.horseJockeysState.containsKey(id)){
            this.horseJockeysState.replace(id, state);
        }else{
            this.horseJockeysState.put(id, state);
        }
    }
    
    /**
    *
    * Method to get the state of a HorseJockey.
    * @param ID The HorseJockey ID.
    */
    public HorseJockeyState getHorseJockeyState(int id){
        return this.horseJockeysState.get(id);
    }

    /**
    *
    * Method to set the state of the Broker.
    * @param state The state to be assigned.
    */
    public void setBrokerState(BrokerState state){
        this.brokerState = state;
    }
    
    /**
    *
    * Method to get the state of the Broker.
    */
    public BrokerState getBrokerState(){
        return this.brokerState;
    }
    
    /**
    *
    * Method to set the state of a Spectator.
    * @param state The state to be assigned.
    * @param ID The ID of the Spectator.
    */
    public void setSpectatorState(int id, SpectatorsState state){
        if(this.spectatorsState.containsKey(id)){
            this.spectatorsState.replace(id, state);
        }else{
            this.spectatorsState.put(id, state);
        }
    }
    
    /**
    *
    * Method to get the state of a Spectator.
    * @param ID The ID of the Spectator.
    */
    public SpectatorsState getSpectatorsState(int id){
        return this.spectatorsState.get(id);
    }
    
    /**
    *
    * Method to get the winner of the race.
    */
    public int getWinner(){
        int raceNumber = ((IEntity)Thread.currentThread()).getCurrentRace();
        
        return races[raceNumber].getWinner();
    }
    
    /**
    *
    * Method to verify if has more races to happen.
    */
    public boolean hasMoreRaces(){
        //System.out.println("HasMoreRaces: " + hasMore + " | running horses: " + this.races[raceNumber].getNRunningHorses());
        return !this.races[Races.N_OF_RACES-1].horsesFinished();
    }
    
    /**
    *
    * Method to allow the HorseJockey to make a move in the race.
    * @param horseId The ID of the HorseJockey.
    */
    public void makeAMove(int horseId){
        int raceNumber = ((IEntity)Thread.currentThread()).getCurrentRace();
        
        this.races[raceNumber].makeAMove(horseId);
    }
    
    public boolean nextMovingHorse(int horseJockeyId){
        int raceNumber = ((IEntity)Thread.currentThread()).getCurrentRace();
        
        return this.races[raceNumber].nextMovingHorse(horseJockeyId);
    }
    
    public boolean horseFinished(int horseId){
        int raceNumber = ((IEntity)Thread.currentThread()).getCurrentRace();
        
        return this.races[raceNumber].horseFinished(horseId);
    }
    
    public boolean horsesFinished(){
        int raceNumber = ((IEntity)Thread.currentThread()).getCurrentRace();
        
        return this.races[raceNumber].horsesFinished();
    }
    
    
    public int getNRunningHorses(){
        int raceNumber = ((IEntity)Thread.currentThread()).getCurrentRace();
        
        return this.races[raceNumber].getNRunningHorses();
    }
    
    /* condition states */
    /* Racing Track */
    public synchronized boolean getStartTheRace(){
        int raceNumber = ((IEntity)Thread.currentThread()).getCurrentRace();
        
        return this.races[raceNumber].getStartTheRace();
    }
    
    public synchronized void setStartTheRace(boolean startTheRace){
        int raceNumber = ((IEntity)Thread.currentThread()).getCurrentRace();
        
        this.races[raceNumber].setStartTheRace(startTheRace);
    }
    /* Stable */
    public synchronized int getWakedHorsesToPaddock(){
        int raceNumber = ((IEntity)Thread.currentThread()).getCurrentRace();
        
        return this.races[raceNumber].getWakedHorsesToPaddock();
    }
    
    public synchronized void addWakedHorsesToPaddock(){
        int raceNumber = ((IEntity)Thread.currentThread()).getCurrentRace();
        
        this.races[raceNumber].addWakedHorsesToPaddock();
    }
    
    public synchronized boolean getAnnuncedNextRace(){
        int raceNumber = ((IEntity)Thread.currentThread()).getCurrentRace();
        
        if(raceNumber>=N_OF_RACES){
            return false;
        }
        
        return this.races[raceNumber].getAnnuncedNextRace();
    }
    
    public synchronized void setAnnuncedNextRace(boolean annuncedNextRace){
        int raceNumber = ((IEntity)Thread.currentThread()).getCurrentRace();
        
        this.races[raceNumber].setAnnuncedNextRace(annuncedNextRace);
    }
    /* Paddock */
    public synchronized boolean allSpectatorsArrivedAtPaddock(){
        int raceNumber = ((IEntity)Thread.currentThread()).getCurrentRace();
        
        return this.races[raceNumber].allSpectatorsArrivedAtPaddock();
    }
    
    public synchronized void addNSpectatorsArrivedAtPaddock(){
        int raceNumber = ((IEntity)Thread.currentThread()).getCurrentRace();
        
        this.races[raceNumber].addNSpectatorsArrivedAtPaddock();
    }
    
    public synchronized boolean allHorseJockeyLeftThePadock(){
        int raceNumber = ((IEntity)Thread.currentThread()).getCurrentRace();
        
        return this.races[raceNumber].allHorseJockeyLeftThePadock();
    }
    
    public synchronized void addNHorseJockeyLeftThePadock(){
        int raceNumber = ((IEntity)Thread.currentThread()).getCurrentRace();
        
        this.races[raceNumber].addNHorseJockeyLeftThePadock();
    }
    
    /* Control Centre */
    public synchronized void setReportResults(boolean set){
        int raceNumber = ((IEntity)Thread.currentThread()).getCurrentRace();
        
        this.races[raceNumber].setReportResults(set);
    }
    
    public synchronized boolean getReportResults(){
        int raceNumber = ((IEntity)Thread.currentThread()).getCurrentRace();
        
        return this.races[raceNumber].getReportResults();
    }
    
    public synchronized void setProceedToPaddock(boolean set){
        int raceNumber = ((IEntity)Thread.currentThread()).getCurrentRace();
        
        this.races[raceNumber].setProceedToPaddock(set);
    }
    
    public synchronized boolean getProceedToPaddock(){
        int raceNumber = ((IEntity)Thread.currentThread()).getCurrentRace();
        
        return this.races[raceNumber].getProceedToPaddock();
    }
    
    public synchronized boolean allNHorsesInPaddock(){
        int raceNumber = ((IEntity)Thread.currentThread()).getCurrentRace();
        
        return this.races[raceNumber].allNHorsesInPaddock();
    }
    
    public synchronized void addNHorsesInPaddock(){
        int raceNumber = ((IEntity)Thread.currentThread()).getCurrentRace();
        
        this.races[raceNumber].addNHorsesInPaddock();
    }
    
    /* Betting Centre */
    public synchronized boolean getBetsOfSpectator(int i){
        int raceNumber = ((IEntity)Thread.currentThread()).getCurrentRace();
        
        return this.races[raceNumber].getBetsOfSpectator(i);
    }
    
    public synchronized void setBetsOfSpectator(int i, boolean set){
        int raceNumber = ((IEntity)Thread.currentThread()).getCurrentRace();
        
        this.races[raceNumber].setBetsOfSpectator(i, set);
    }
    
    public synchronized boolean getAcceptedTheBet(int i){
        int raceNumber = ((IEntity)Thread.currentThread()).getCurrentRace();
        
        return this.races[raceNumber].getAcceptedTheBet(i);
    }
    
    public synchronized void setAcceptedTheBet(int i, boolean set){
        int raceNumber = ((IEntity)Thread.currentThread()).getCurrentRace();
        
        this.races[raceNumber].setAcceptedTheBet(i, set);
    }
    
    public synchronized boolean getWaitingToBePaidSpectators(int i){
        int raceNumber = ((IEntity)Thread.currentThread()).getCurrentRace();
        
        return this.races[raceNumber].getWaitingToBePaidSpectators(i);
    }
    
    public synchronized void setWaitingToBePaidSpectators(int i, boolean set){
        int raceNumber = ((IEntity)Thread.currentThread()).getCurrentRace();
        
        this.races[raceNumber].setWaitingToBePaidSpectators(i, set);
    }
    
    public synchronized boolean getPaidSpectators(int i){
        int raceNumber = ((IEntity)Thread.currentThread()).getCurrentRace();
        
        return this.races[raceNumber].getPaidSpectators(i);
    }
    
    public synchronized void setPaidSpectators(int i, boolean set){
        int raceNumber = ((IEntity)Thread.currentThread()).getCurrentRace();
        
        this.races[raceNumber].setPaidSpectators(i, set);
    }
    /* end condition states */
}
