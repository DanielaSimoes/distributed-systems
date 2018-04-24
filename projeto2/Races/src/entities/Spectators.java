package entities;

import GeneralRepository.Bet;
import GeneralRepository.Log;
import GeneralRepository.Races;
import GeneralRepository.RacesProxy;

/**
 * This file contains the code that represents the Spectator lifecycle.
 * @author Daniela Simões, 76771
 */
public class Spectators extends Thread implements IEntity{
    
    private SpectatorsState state;
    private int moneyToBet;
    private int initialMoney;
    private final RacesProxy races;
    
    /**
     *   Shared zones in which Spectators has actions
     */
    private final shared.IControlCentre cc;
    private final shared.IBettingCentre bc;
    private final shared.IPaddock paddock;
    private final LogProxy log;
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
    * @param id The ID of the spectator.
     * @param log
    */
    public Spectators(shared.IControlCentre cc, shared.IBettingCentre bc, shared.IPaddock paddock, int moneyToBet, int id, RacesProxy races, LogProxy log){
        this.id = id;
        this.cc = cc;
        this.bc = bc;
        this.paddock = paddock;
        this.moneyToBet = moneyToBet;
        this.initialMoney = moneyToBet;
        this.log = log;
        this.log.setSpectatorAmount(id, moneyToBet);
        this.relaxABit = false;
        this.races = races;
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
        this.setSpectatorsState(SpectatorsState.WAITING_FOR_A_RACE_TO_START);
        
        while(!this.relaxABit){
            switch(this.state){

                case WAITING_FOR_A_RACE_TO_START:
                    cc.waitForNextRace(raceId);
                    paddock.goCheckHorses(raceId);
                    break;

                case APPRAISING_THE_HORSES:
                    Bet bet = bc.placeABet(raceId, this.id, this.initialMoney, this.moneyToBet);
                    this.subtractMoneyToBet(bet.getAmount());
                    break;

                case PLACING_A_BET:
                    cc.goWatchTheRace(raceId);
                    break;

                case WATCHING_A_RACE:
                    if(cc.haveIWon(raceId, this.id)){
                        int gains = bc.goCollectTheGains(raceId, this.id);
                        this.addMoneyToBet(gains);
                    }
                    
                    if(races.hasMoreRaces()){
                        this.nextRace();
                        cc.waitForNextRace(raceId);
                        paddock.goCheckHorses(raceId);
                    }else{
                        this.relaxABit = true;
                    }
                    break;

                case COLLECTING_THE_GAINS:
                    if(races.hasMoreRaces()){
                        this.nextRace();
                        cc.waitForNextRace(raceId);
                        paddock.goCheckHorses(raceId);
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
        if(this.raceId==Races.N_OF_RACES-1){
            return;
        }
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
        if(state==this.state){
            return;
        }
        this.state = state;
        this.log.setSpectatorState(id, state);
    } 
    
    /**
    *
    * Method to get the state of the Spectator.
    * @return
    */
    public SpectatorsState getSpectatorsState(){
        return this.state;
    }
    
    /**
    *
    * Method to retrieve initial ammount of money to bet
    * @return
    */
    public double getInitialBet(){
        return this.initialMoney;
    }
   
    /**
    *
    * Method to get the money that the Spectator has to bet.
    * @return
    */
    public double getMoneyToBet(){
        return this.moneyToBet;
    }
   
    /**
    *
    * Method to subtract spectators money in case of loss.
    */
    public void subtractMoneyToBet(int money){
        this.moneyToBet -= money;
        this.log.setSpectatorAmount(id, moneyToBet);
    }
    
    /**
    *
    * Method to add spectators money in case of winning.
    */
    public void addMoneyToBet(int money){
        this.moneyToBet += money;
        this.log.setSpectatorAmount(id, moneyToBet);
    }
    
    /**
    *
    * Method to get the Spectator ID.
    * @return 
    */
    public int getSpectatorId(){
        return this.id;
    }
}
