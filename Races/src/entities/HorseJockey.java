package entities;

import GeneralRepository.Log;
import GeneralRepository.Races;

/**
 * This file contains the code that represents the HorseJockey lifecycle.
 * @author Daniela Simões, 76771
 */
public class HorseJockey extends Thread implements IEntity{
    
    private HorseJockeyState state;
    
    /**
     *   Shared zones in which horsejockey has actions
     */
    private final Log log;
    private final int id;
    private final shared.IStable stable;
    private final shared.IControlCentre cc;
    private final shared.IPaddock paddock;
    private final shared.IRacingTrack rt;
    private final Races races = Races.getInstace();
    private boolean entertainTheGuests;
    
    private int raceId = 0;
    private final int stepSize;
    
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
    public HorseJockey(shared.IStable s, shared.IControlCentre cc, shared.IPaddock paddock, shared.IRacingTrack rt, int stepSize, int id){
        this.stable = s;
        this.cc = cc;
        this.paddock = paddock;
        this.rt = rt;
        this.stepSize = stepSize;
        this.id = id;
        this.log = Log.getInstance();
        this.setName("HorseJockey " + id);
        this.entertainTheGuests = false;
    }
    
    /**
    *
    * The run method of the HorseJockey executes its entire life cycle, making the transitions between
    * the states: At the stable - At the paddock -  At the start line - Running - At the finish line.
    * This method runs until there are no more races to happen.
    */
    @Override
    public void run(){  
        this.races.setHorseJockeyStepSize(id, stepSize);
        this.setHorseJockeyState(HorseJockeyState.AT_THE_STABLE);
        stable.proceedToStable();
                    
        while(!this.entertainTheGuests){
            switch(this.state){
                case AT_THE_STABLE:
                    if(races.hasMoreRaces()){
                        cc.proceedToPaddock();
                        paddock.proceedToPaddock();
                    }else{
                        this.entertainTheGuests = true;
                    }
                    break;
                case AT_THE_PADDOCK:
                    paddock.proceedToStartLine();
                    rt.proceedToStartLine();
                    break;
                case AT_THE_START_LINE:
                    rt.makeAMove();
                    this.log.makeAMove();
                    break;
                case RUNNNING:
                    while(!rt.hasFinishLineBeenCrossed(this.id)){
                        rt.makeAMove();
                        this.log.makeAMove();
                    }
                    break;
                case AT_THE_FINISH_LINE:
                    if(races.hasMoreRaces()){
                        this.nextRace();
                        stable.proceedToStable();
                    }else{
                        stable.proceedToStable();
                    }
                    
                    if(!races.hasMoreRaces()){
                        this.entertainTheGuests = true;
                    }
                    break;

            }
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
