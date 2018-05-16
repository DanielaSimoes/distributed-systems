package entities.horseJockey;

import generalRepository.Log;
import generalRepository.Races;
import structures.enumerates.HorseJockeyState;
import interfaces.entity.EntityInterface;
import interfaces.stable.IBroker;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import structures.vectorClock.VectorTimestamp;

/**
 * This file contains the code that represents the HorseJockey lifecycle.
 * @author Daniela Simões, 76771
 */
public class HorseJockey extends Thread implements EntityInterface{
    
    private HorseJockeyState state;
    
    /**
     *   Shared zones in which horsejockey has actions
     */
    private final Log log;
    private final int id;
    private final interfaces.stable.IHorseJockey stable;
    private final interfaces.controlCentre.IHorseJockey cc;
    private final interfaces.paddock.IHorseJockey paddock;
    private final interfaces.racingTrack.IHorseJockey rt;
    private final Races races = Races.getInstace();
    private boolean entertainTheGuests;
    
    private int raceId = 0;
    private final int stepSize;
    private final VectorTimestamp myClock;
    private VectorTimestamp receivedClock;
    
    /**
    *
    * HorseJockey Constructor
    * @param s The Stable is a shared memory region where the HorseJockey will perform actions.
    * @param cc The Control Centre is a shared memory region where the HorseJockey will perform actions.
    * @param rt The Racing Track is a shared memory region where the HorseJockey will perform actions.
    * @param paddock The Paddock is a shared memory region where the HorseJockey will perform actions.
    * @param stepSize The step size of the horse.
    * @param id The ID of the horse.
    */
    public HorseJockey(interfaces.stable.IHorseJockey s, interfaces.controlCentre.IHorseJockey cc, interfaces.paddock.IHorseJockey paddock, interfaces.racingTrack.IHorseJockey rt, int stepSize, int id){
        this.stable = s;
        this.cc = cc;
        this.paddock = paddock;
        this.rt = rt;
        this.stepSize = stepSize;
        this.id = id;
        this.log = Log.getInstance();
        this.setName("HorseJockey " + id);
        this.entertainTheGuests = false;
        this.myClock = new VectorTimestamp(4,4);////////////////???????????????????????????
    }
    
    /**
    *
    * The run method of the HorseJockey executes its entire life cycle, making the transitions between
    * the states: At the stable - At the paddock -  At the start line - Running - At the finish line.
    * This method runs until there are no more races to happen.
    */
    @Override
    public void run(){  
        try {
            this.races.setHorseJockeyStepSize(id, stepSize);
            this.setHorseJockeyState(HorseJockeyState.AT_THE_STABLE);
            this.receivedClock = stable.proceedToStable(this.myClock.clone());

            while(!this.entertainTheGuests){
                switch(this.state){
                    case AT_THE_STABLE:
                        if(races.hasMoreRaces()){
                            this.myClock.increment();
                            this.receivedClock = cc.proceedToPaddock(this.myClock.clone());
                            this.myClock.update(this.receivedClock);

                            this.myClock.increment();
                            this.receivedClock = paddock.proceedToPaddock(this.myClock.clone());
                            this.myClock.update(this.receivedClock);
                        }else{
                            this.entertainTheGuests = true;
                        }
                        break;
                    case AT_THE_PADDOCK:
                        this.myClock.increment();
                        this.receivedClock = paddock.proceedToStartLine(this.myClock.clone());
                        this.myClock.update(this.receivedClock);

                        this.myClock.increment();
                        this.receivedClock = rt.proceedToStartLine(this.myClock.clone());
                        this.myClock.update(this.receivedClock);
                        break;
                    case AT_THE_START_LINE:
                        this.myClock.increment();
                        this.receivedClock = rt.makeAMove(this.myClock.clone());
                        this.myClock.update(this.receivedClock);

                        this.log.makeAMove();
                        break;
                    case RUNNNING:
                        while(!rt.hasFinishLineBeenCrossed(this.id)){
                            this.myClock.increment();
                            this.receivedClock = rt.makeAMove(this.myClock.clone());
                            this.myClock.update(this.receivedClock);

                            this.log.makeAMove();
                        }
                        break;
                    case AT_THE_FINISH_LINE:
                        if(races.hasMoreRaces()){
                            this.nextRace();

                            this.myClock.increment();
                            this.receivedClock = stable.proceedToStable(this.myClock.clone());
                            this.myClock.update(this.receivedClock);

                        }else{
                            this.myClock.increment();
                            this.receivedClock = stable.proceedToStable(this.myClock.clone());
                            this.myClock.update(this.receivedClock);
                        }

                        if(!races.hasMoreRaces()){
                            this.entertainTheGuests = true;
                        }
                        break;

                }
            }
        } catch (RemoteException ex) {
            Logger.getLogger(HorseJockey.class.getName()).log(Level.SEVERE, null, ex);
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
    * Method to get the HorseJockey ID.
    * @return 
    */
    public int getHorseId(){
        return this.id;
    }
    
    /**
    *
    * Method to set the state of the HorseJockey.
    * @param state The state to be assigned to the HorseJockey.
    */
    public void setHorseJockeyState(HorseJockeyState state){
        if(state==this.state){
            return;
        }
        this.state = state;
        this.log.setHorseJockeyState(id, state);
    } 
    
    /**
    *
    * Method to get the state of the HorseJockey.
    * @return 
    */
    public HorseJockeyState getHorseJockeyState(){
        return this.state;
    }
    
    /**
    *
    * Method to get the HorseJockey step size.
    * @return 
    */
    public int getStepSize(){
        return this.stepSize;
    }
}
