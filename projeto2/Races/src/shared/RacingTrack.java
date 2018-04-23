/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shared;

import GeneralRepository.RacesProxy;
import entities.Broker;
import entities.BrokerState;
import entities.HorseJockey;
import entities.HorseJockeyState;

/**
 * This file contains the shared memory region Racing Track.
 * @author Daniela Simões, 76771
 */
public class RacingTrack implements IRacingTrack {
    
    private final RacesProxy races;
    
    public RacingTrack(RacesProxy races){
        this.races = races;
    }
    
    /**
    *
    * Method to start the race.
     * @param raceNumber
    */
    @Override
    public synchronized void startTheRace(int raceNumber){
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.SUPERVISING_THE_RACE);
       
        races.setStartTheRace(true, raceNumber);
        notifyAll();
        
        while(!races.horsesFinished(raceNumber)){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
        
        races.setStartTheRace(false, raceNumber);
    };
    
    /**
    *
    * Method to get the horses to proceed to the start line.
     * @param raceNumber
    */
    @Override
    public synchronized void proceedToStartLine(int raceNumber){
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_START_LINE);
        
        while(!(races.getStartTheRace(raceNumber) || !this.races.nextMovingHorse(((HorseJockey)Thread.currentThread()).getHorseId(), raceNumber))){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
        
        notifyAll();
    };
    
    /**
    *
    * Method to verify if a given horse crossed the finish line.
    * @param horseId The HorseJockey ID.
     * @param raceNumber
     * @return 
    */
    @Override
    public synchronized boolean hasFinishLineBeenCrossed(int horseId, int raceNumber){  
        return races.horseFinished(horseId, raceNumber);
    };
    
    /**
    *
    * Method to make a move - an iteration in the racing track.
     * @param raceNumber
    */
    @Override
    public synchronized void makeAMove(int raceNumber){
        int horseId = ((HorseJockey)Thread.currentThread()).getHorseId();
        
        while(!this.races.nextMovingHorse(horseId, raceNumber)){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
        
        races.makeAMove(horseId, raceNumber);
        
        if(this.hasFinishLineBeenCrossed(horseId, raceNumber)){
            ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_FINISH_LINE);
        }else{
            ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.RUNNNING);
        }
        
        notifyAll();
    };   
}
