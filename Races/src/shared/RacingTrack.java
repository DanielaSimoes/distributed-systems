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
 *
 * @author Daniela
 */
public class RacingTrack implements IRacingTrack {
    
    private boolean startTheRace = false, makeAMove = false;
    private Races races = Races.getInstace();
    
    @Override
    public synchronized void startTheRace(){
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.SUPERVISING_THE_RACE);
       
        this.startTheRace = true;
        notifyAll();
        
        while(!races.getRace().horsesFinished()){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }

    };
    
    @Override
    public synchronized void proceedToStartLine(){
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_START_LINE);
        
        // not sure
        while(!(this.startTheRace || this.makeAMove)){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
        
        this.makeAMove = true;
    };
    
    @Override
    public synchronized boolean hasFinishLineBeenCrossed(){  
        return races.getRace().horseFinished(((HorseJockey)Thread.currentThread()).getHorseId());
    };
    
    @Override
    public synchronized void makeAMove(){
        while(!this.races.getRace().nextMovingHorse(((HorseJockey)Thread.currentThread()).getHorseId())){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
        
        races.getRace().makeAMove(((HorseJockey)Thread.currentThread()).getHorseId());
        
        if(this.hasFinishLineBeenCrossed()){
            ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_FINISH_LINE);
        }else{
            ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.RUNNNING);
        }
        
        if(!races.getRace().horsesFinished()){
            notifyAll();
        }   
    };
    
}
