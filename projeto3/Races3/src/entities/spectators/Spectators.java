package entities.spectators;

import generalRepository.Log;
import generalRepository.Races;
import interfaces.entity.EntityInterface;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import structures.enumerates.SpectatorsState;
import structures.vectorClock.VectorTimestamp;

/**
 * This file contains the code that represents the Spectator lifecycle.
 * @author Daniela Simões, 76771
 */
public class Spectators extends Thread implements EntityInterface{
    
    private SpectatorsState state;
    private int moneyToBet;
    private int initialMoney;
    private final Log log;
    private final Races races = Races.getInstace();
    
    /**
     *   Shared zones in which Spectators has actions
     */
    private final interfaces.controlCentre.ISpectators cc;
    private final interfaces.bettingCentre.ISpectators bc;
    private final interfaces.paddock.ISpectators paddock;
    private final int id;
    private boolean relaxABit;
    private int raceId = 0;
    private final VectorTimestamp myClock;
    private VectorTimestamp receivedClock;
    
    /**
    *
    * Spectators Constructor
    * @param cc The Control Centre is a shared memory region where the Spectator will perform actions.
    * @param bc The Betting Centre is a shared memory region where the Spectator will perform actions.
    * @param paddock The Paddock is a shared memory region where the Spectator will perform actions.
    * @param moneyToBet The money the spectator has to bet.
    * @param id The ID of the spectator.
    */
    public Spectators(interfaces.controlCentre.ISpectators cc, interfaces.bettingCentre.ISpectators bc, interfaces.paddock.ISpectators paddock, int moneyToBet, int id){
        this.id = id;
        this.cc = cc;
        this.bc = bc;
        this.paddock = paddock;
        this.moneyToBet = moneyToBet;
        this.initialMoney = moneyToBet;
        this.log = Log.getInstance();
        this.log.setSpectatorAmount(id, moneyToBet);
        this.relaxABit = false;
        this.setName("Spectator " + id);
        this.myClock = new VectorTimestamp(4,4); // ????????????????????
    }
    
    /**
    *
    * The run method of the Spectator executes its entire life cycle, making the transitions between
    * the states: Waiting for a race to start - Appraising the horses -  Placing a bet - Watching a race - Collecting the gains - Celebrating.
    * This method runs until it's time to relax a bit - death state.
    */
    @Override
    public void run(){ 
        try {
            this.setSpectatorsState(SpectatorsState.WAITING_FOR_A_RACE_TO_START);

            while(!this.relaxABit){
                switch(this.state){

                    case WAITING_FOR_A_RACE_TO_START:
                        this.myClock.increment();
                        this.receivedClock = cc.waitForNextRace(this.myClock.clone());
                        this.myClock.update(this.receivedClock);

                        this.myClock.increment();
                        this.receivedClock =paddock.goCheckHorses(this.myClock.clone());
                        this.myClock.update(this.receivedClock);

                        break;

                    case APPRAISING_THE_HORSES:
                        bc.placeABet();
                        break;

                    case PLACING_A_BET:
                        this.myClock.increment();
                        this.receivedClock = cc.goWatchTheRace(this.myClock.clone());
                        this.myClock.update(this.receivedClock);

                        break;

                    case WATCHING_A_RACE:
                        if(cc.haveIWon()){
                            bc.goCollectTheGains();
                        }

                        if(races.hasMoreRaces()){
                            this.nextRace();
                            this.myClock.increment();
                            this.receivedClock = cc.waitForNextRace(this.myClock.clone());
                            this.myClock.update(this.receivedClock);

                            this.myClock.increment();
                            this.receivedClock = paddock.goCheckHorses(this.myClock.clone());
                            this.myClock.update(this.receivedClock);
                        }else{
                            this.relaxABit = true;
                        }
                        break;

                    case COLLECTING_THE_GAINS:
                        if(races.hasMoreRaces()){
                            this.nextRace();

                            this.myClock.increment();
                            this.receivedClock = cc.waitForNextRace(this.myClock.clone());
                            this.myClock.update(this.receivedClock);

                            this.myClock.increment();
                            this.receivedClock = paddock.goCheckHorses(this.myClock.clone());
                            this.myClock.update(this.receivedClock);
                        }else{
                            this.relaxABit = true;
                        }
                        break;
                }   
            }

            this.myClock.increment();
            this.receivedClock = cc.relaxABit(this.myClock.clone());
            this.myClock.update(this.receivedClock);
        } catch (RemoteException ex) {
            Logger.getLogger(Spectators.class.getName()).log(Level.SEVERE, null, ex);
        }
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
