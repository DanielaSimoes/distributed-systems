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
    
    private int raceId = 0;
    private final double stepSize;
    
    /**
    *
    * HorseJockey Constructor
    * @param s The Stable is a shared memory region where the HorseJockey will perform actions.
    * @param cc The Control Centre is a shared memory region where the HorseJockey will perform actions.
    * @param rt The Racing Track is a shared memory region where the HorseJockey will perform actions.
    * @param paddock The Paddock is a shared memory region where the HorseJockey will perform actions.
    * @param stepSize The step size of the horse.
    * @param ID The ID of the horse.
    */
    public HorseJockey(shared.IStable s, shared.IControlCentre cc, shared.IPaddock paddock, shared.IRacingTrack rt, double stepSize, int id){
        this.stable = s;
        this.cc = cc;
        this.paddock = paddock;
        this.rt = rt;
        this.stepSize = stepSize;
        this.id = id;
        this.log = Log.getInstance();
        this.state = HorseJockeyState.AT_THE_STABLE;
        this.setName("HorseJockey " + id);
    }
    
    /**
    *
    * The run method of the HorseJockey executes its entire life cycle, making the transitions between
    * the states: At the stable - At the paddock -  At the start line - Running - At the finish line.
    * This method runs until there are no more races to happen.
    */
    @Override
    public void run(){  
        stable.proceedToStable();
                    
        while(races.hasMoreRaces()){
            switch(this.state){
                case AT_THE_STABLE:
                    cc.proceedToPaddock();
                    paddock.proceedToPaddock();
                    break;
                case AT_THE_PADDOCK:
                    paddock.proceedToStartLine();
                    rt.proceedToStartLine();
                    break;
                case AT_THE_START_LINE:
                    rt.makeAMove();
                    break;
                case RUNNNING:
                    while(!rt.hasFinishLineBeenCrossed(this.id)){
                        rt.makeAMove();
                    }
                    //System.out.println("Horse "+id+" out of running");
                    break;
                case AT_THE_FINISH_LINE:
                    this.nextRace();
                    stable.proceedToStable();
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
        this.state = state;
        this.log.setHorseJockeyState(id, state);
    } 
    
    /**
    *
    * Method to get the state of the HorseJockey.
    */
    public HorseJockeyState getHorseJockeyState(){
        return this.state;
    }
    
    /**
    *
    * Method to get the HorseJockey step size.
    */
    public double getStepSize(){
        return this.stepSize;
    }
}
