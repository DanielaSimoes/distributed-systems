package entities.spectators;

import structures.enumerates.SpectatorsState;
import generalRepository.races.Bet;
import interfaces.IEntity;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import structures.constants.Constants;

/**
 * This file contains the code that represents the Spectator lifecycle.
 * @author Daniela Simes, 76771
 */
public class Spectators extends Thread implements IEntity{
    
    private SpectatorsState state;
    private int moneyToBet;
    private int initialMoney;
    private final interfaces.IRaces races;
    
    /**
     *   Shared zones in which Spectators has actions
     */
    private final interfaces.IControlCentre cc;
    private final interfaces.IBettingCentre bc;
    private final interfaces.IPaddock paddock;
    private final interfaces.ILog log;
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
    public Spectators(interfaces.IControlCentre cc, interfaces.IBettingCentre bc, interfaces.IPaddock paddock, int moneyToBet, int id, interfaces.IRaces races, interfaces.ILog log) throws RemoteException{
        this.id = id;
        this.cc = cc;
        this.bc = bc;
        this.paddock = paddock;
        this.moneyToBet = moneyToBet;
        this.initialMoney = moneyToBet;
        this.log = log;
        this.setSpectatorsState(SpectatorsState.WAITING_FOR_A_RACE_TO_START);
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
        try{
            System.out.printf("Spectator %d started!\n", this.id); 

            while(!this.relaxABit){
                switch(this.state){

                    case WAITING_FOR_A_RACE_TO_START:
                        this.setSpectatorsState(SpectatorsState.WAITING_FOR_A_RACE_TO_START);
                        cc.waitForNextRace(raceId);

                        this.setSpectatorsState(SpectatorsState.APPRAISING_THE_HORSES);
                        paddock.goCheckHorses(raceId);
                        break;

                    case APPRAISING_THE_HORSES:
                        this.setSpectatorsState(SpectatorsState.PLACING_A_BET);
                        Bet bet = bc.placeABet(raceId, this.id, this.initialMoney, this.moneyToBet);
                        this.subtractMoneyToBet(bet.getAmount());
                        break;

                    case PLACING_A_BET:
                        this.setSpectatorsState(SpectatorsState.WATCHING_A_RACE);
                        cc.goWatchTheRace(raceId);
                        break;

                    case WATCHING_A_RACE:
                        this.setSpectatorsState(SpectatorsState.WATCHING_A_RACE);
                        if(cc.haveIWon(raceId, this.id)){
                            this.setSpectatorsState(SpectatorsState.COLLECTING_THE_GAINS);
                            int gains = bc.goCollectTheGains(raceId, this.id);
                            this.addMoneyToBet(gains);
                        }

                        if(races.hasMoreRaces()){
                            this.nextRace();
                            this.setSpectatorsState(SpectatorsState.WAITING_FOR_A_RACE_TO_START);
                            cc.waitForNextRace(raceId);

                            this.setSpectatorsState(SpectatorsState.APPRAISING_THE_HORSES);
                            paddock.goCheckHorses(raceId);
                        }else{
                            this.relaxABit = true;
                        }
                        break;

                    case COLLECTING_THE_GAINS:
                        if(races.hasMoreRaces()){
                            this.nextRace();
                            this.setSpectatorsState(SpectatorsState.WAITING_FOR_A_RACE_TO_START);
                            cc.waitForNextRace(raceId);

                            this.setSpectatorsState(SpectatorsState.APPRAISING_THE_HORSES);
                            paddock.goCheckHorses(raceId);
                        }else{
                            this.relaxABit = true;
                        }
                        break;
                }
            }

            this.setSpectatorsState(SpectatorsState.CELEBRATING);
            cc.relaxABit();
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
        if(this.raceId==Constants.N_OF_RACES-1){
            return;
        }
        this.raceId++;
    }
    
    /**
    *
    * Method to set the state of the Spectator.
    * @param state The state to be assigned to the Spectator.
    */
    public void setSpectatorsState(SpectatorsState state) throws RemoteException{
        if(state==this.state){
            return;
        }
        this.state = state;
        this.setName("Spectator " + id + " - " + this.state.toString());
        this.log.setSpectatorState(id, state, this.raceId);
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
    public void subtractMoneyToBet(int money) throws RemoteException{
        this.moneyToBet -= money;
        this.log.setSpectatorAmount(id, moneyToBet);
    }
    
    /**
    *
    * Method to add spectators money in case of winning.
    */
    public void addMoneyToBet(int money) throws RemoteException{
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
