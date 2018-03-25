package entities;

import GeneralRepository.Log;
import GeneralRepository.Races;

/**
 * This file contains the code that represents the Spectator lifecycle.
 * @author Daniela Simões, 76771
 */
public class Spectators extends Thread implements IEntity{
    
    private SpectatorsState state;
    private double moneyToBet;
    private final Log log;
    private final Races races = Races.getInstace();
    
    /**
     *   Shared zones in which Spectators has actions
     */
    private final shared.IControlCentre cc;
    private final shared.IBettingCentre bc;
    private final shared.IPaddock paddock;
    private final int id;
    private boolean relaxABit;
    private int raceId = 0;
    
    /**
    *
    * Spectators Constructor
    * @param cc The Control Centre is a shared memory region where the Spectator will perform actions.
    * @param bc The Betting Centre is a shared memory region where the Spectator will perform actions.
    * @param paddock The Paddock is a shared memory region where the Spectator will perform actions.
    * @param moneyToBet The money the spectator has to bet.
    * @param ID The ID of the spectator.
    */
    public Spectators(shared.IControlCentre cc, shared.IBettingCentre bc, shared.IPaddock paddock, double moneyToBet, int id){
        this.id = id;
        this.cc = cc;
        this.bc = bc;
        this.paddock = paddock;
        this.moneyToBet = moneyToBet;
        this.log = Log.getInstance();
        this.state = SpectatorsState.WAITING_FOR_A_RACE_TO_START;
        this.relaxABit = false;
        this.setName("Spectator " + id);
    }
    
    /**
    *
    * The run method of the Spectator executes its entire life cycle, making the transitions between
    * the states: Waiting for a race to start - Appraising the horses -  Placing a bet - Watching a race - Collecting the gains - Celebrating.
    * This method runs until it's time to relax a bit - death state.
    */
    @Override
    public void run(){ 
        while(!this.relaxABit){
            switch(this.state){

                case WAITING_FOR_A_RACE_TO_START:
                    cc.waitForNextRace();
                    paddock.goCheckHorses();
                    break;

                case APPRAISING_THE_HORSES:
                    bc.placeABet();
                    break;

                case PLACING_A_BET:
                    cc.goWatchTheRace();
                    break;

                case WATCHING_A_RACE:
                    if(cc.haveIWon()){
                        bc.goCollectTheGains();
                    }else if(races.hasMoreRaces()){
                        this.nextRace();
                        cc.waitForNextRace();
                        paddock.goCheckHorses();
                    }else{
                        this.relaxABit = true;
                    }
                    break;

                case COLLECTING_THE_GAINS:
                    if(races.hasMoreRaces()){
                        this.nextRace();
                        cc.waitForNextRace();
                        paddock.goCheckHorses();
                    }else{
                        this.relaxABit = true;
                    }
                    break;
            }   
        }
        
        cc.relaxABit();
    }
    
    /**
    *
    * Method to increment the race ID.
    */
    @Override
    public void nextRace(){
        this.raceId++;
    }
    
    /**
    *
    * Method to get the current race ID.
    */
    @Override
    public int getCurrentRace(){
        return this.raceId;
    }
    
    /**
    *
    * Method to set the state of the Spectator.
    * @param state The state to be assigned to the Spectator.
    */
    public void setSpectatorsState(SpectatorsState state){
        this.state = state;
        this.log.setSpectatorState(id, state);
    } 
    
    /**
    *
    * Method to get the state of the Spectator.
    */
    public SpectatorsState getSpectatorsState(){
        return this.state;
    }
    
    /**
    *
    * Method to get the money that the Spectator has to bet.
    */
    public double getMoneyToBet(){
        return this.moneyToBet;
    }
    
    /**
    *
    * Method to get the Spectator ID.
    */
    public int getSpectatorId(){
        return this.id;
    }
}
