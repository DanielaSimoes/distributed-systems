package entities.horseJockey;

import structures.enumerates.HorseJockeyState;
import interfaces.IEntity;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import structures.constants.Constants;

/**
 * This file contains the code that represents the HorseJockey lifecycle.
 * @author Daniela Simes, 76771
 */
public class HorseJockey extends Thread implements IEntity{
    
    private HorseJockeyState state;
    
    /**
     *   Shared zones in which horsejockey has actions
     */
    private final int id;
    private final interfaces.IStable stable;
    private final interfaces.IControlCentre cc;
    private final interfaces.IPaddock paddock;
    private final interfaces.IRacingTrack rt;
    private final interfaces.ILog log;
    private boolean entertainTheGuests;
    private final interfaces.IRaces races;
    private int raceId = 0;
    private final int stepSize;
    
    /**
    *
    * HorseJockey Constructor
    * @param s The Stable is a shared memory region where the HorseJockey will perform actions.
    * @param cc The Control Centre is a shared memory region where the HorseJockey will perform actions.
    * @param rt The Racing Track is a shared memory region where the HorseJockey will perform actions.
    * @param paddock The Paddock is a shared memory region where the HorseJockey will perform actions.
     * @param races
    * @param stepSize The step size of the horse.
    * @param id The ID of the horse.
     * @param log
    */
    public HorseJockey(interfaces.IStable s, interfaces.IControlCentre cc, interfaces.IPaddock paddock, interfaces.IRacingTrack rt, int stepSize, int id, interfaces.IRaces races, interfaces.ILog log) throws RemoteException{
        this.stable = s;
        this.cc = cc;
        this.paddock = paddock;
        this.rt = rt;
        this.stepSize = stepSize;
        this.id = id;
        this.log = log;
        this.setName("HorseJockey " + id);
        this.entertainTheGuests = false;
        this.races = races;
        this.setHorseJockeyState(HorseJockeyState.AT_THE_STABLE);
    }
    
    /**
    *
    * The run method of the HorseJockey executes its entire life cycle, making the transitions between
    * the states: At the stable - At the paddock -  At the start line - Running - At the finish line.
    * This method runs until there are no more races to happen.
    */
    @Override
    public void run(){ 
        try{
            System.out.printf("Horse Jockey %d started!\n", this.id);

            this.setHorseJockeyState(HorseJockeyState.AT_THE_STABLE);
            this.raceId = stable.proceedToStable(raceId, this.id, this.stepSize);

            while(!this.entertainTheGuests){
                switch(this.state){
                    case AT_THE_STABLE:
                        if(races.hasMoreRaces()){
                            this.setHorseJockeyState(HorseJockeyState.AT_THE_PADDOCK);
                            cc.proceedToPaddock(raceId);

                            this.setHorseJockeyState(HorseJockeyState.AT_THE_PADDOCK);
                            paddock.proceedToPaddock(raceId);
                        }else{
                            this.entertainTheGuests = true;
                        }
                        break;
                    case AT_THE_PADDOCK:
                        while(races.horsesFinished(raceId)){
                            this.nextRace();

                            if(!races.hasMoreRaces() && races.horsesFinished(raceId)){
                                break;
                            }
                        }

                        this.setHorseJockeyState(HorseJockeyState.AT_THE_START_LINE);
                        paddock.proceedToStartLine(raceId);

                        this.setHorseJockeyState(HorseJockeyState.AT_THE_START_LINE);
                        rt.proceedToStartLine(raceId, this.id);
                        break;
                    case AT_THE_START_LINE:
                        rt.makeAMove(raceId, this.id);

                        if(rt.hasFinishLineBeenCrossed(this.id, raceId)){
                            this.setHorseJockeyState(HorseJockeyState.AT_THE_FINISH_LINE);
                        }else{
                            this.setHorseJockeyState(HorseJockeyState.RUNNNING);
                        }

                        this.log.makeAMove(this.raceId);
                        break;
                    case RUNNNING:
                        while(!rt.hasFinishLineBeenCrossed(this.id, raceId)){
                            rt.makeAMove(raceId, this.id);

                            if(rt.hasFinishLineBeenCrossed(this.id, raceId)){
                                this.setHorseJockeyState(HorseJockeyState.AT_THE_FINISH_LINE);
                            }else{
                                this.setHorseJockeyState(HorseJockeyState.RUNNNING);
                            }
                            this.log.makeAMove(this.raceId);
                        }
                        break;
                    case AT_THE_FINISH_LINE:
                        if(races.hasMoreRaces()){
                            this.nextRace();
                            this.setHorseJockeyState(HorseJockeyState.AT_THE_STABLE);
                            this.raceId = stable.proceedToStable(raceId, this.id, this.stepSize);
                        }else{
                            this.setHorseJockeyState(HorseJockeyState.AT_THE_STABLE);
                            this.raceId = stable.proceedToStable(raceId, this.id, this.stepSize);
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
        if(this.raceId==Constants.N_OF_RACES-1){
            return;
        }
        this.raceId++;
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
    public void setHorseJockeyState(HorseJockeyState state) throws RemoteException{
        if(state==this.state){
            return;
        }
        
        this.state = state;
        this.log.setHorseJockeyState(id, state, this.raceId);
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
