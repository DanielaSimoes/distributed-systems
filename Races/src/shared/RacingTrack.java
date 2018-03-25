/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shared;

import GeneralRepository.Races;
import entities.Broker;
import entities.BrokerState;
import entities.HorseJockey;
import entities.HorseJockeyState;

/**
 * This file contains the shared memory region Racing Track.
 * @author Daniela Simões, 76771
 */
public class RacingTrack implements IRacingTrack {
    
    private Races races = Races.getInstace();
    
    /**
    *
    * Method to start the race.
    */
    @Override
    public synchronized void startTheRace(){
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.SUPERVISING_THE_RACE);
       
        races.setStartTheRace(true);
        notifyAll();
        
        while(!races.horsesFinished()){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
        
        races.setStartTheRace(false);
    };
    
    /**
    *
    * Method to get the horses to proceed to the start line.
    */
    @Override
    public synchronized void proceedToStartLine(){
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_START_LINE);
        
        while(!(races.getStartTheRace() || !this.races.nextMovingHorse(((HorseJockey)Thread.currentThread()).getHorseId()))){
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
    */
    @Override
    public synchronized boolean hasFinishLineBeenCrossed(int horseId){  
        return races.horseFinished(horseId);
    };
    
    /**
    *
    * Method to make a move - an iteration in the racing track.
    */
    @Override
    public synchronized void makeAMove(){
        int horseId = ((HorseJockey)Thread.currentThread()).getHorseId();
        
        while(!this.races.nextMovingHorse(horseId)){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
        
        races.makeAMove(horseId);
        
        if(this.hasFinishLineBeenCrossed(horseId)){
            ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_FINISH_LINE);
            //System.out.println("Horse "+ horseId + " at the finish line");
        }else{
            ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.RUNNNING);
            //System.out.println("Horse "+ horseId + " running");
        }
        
        notifyAll();
    };   
}
