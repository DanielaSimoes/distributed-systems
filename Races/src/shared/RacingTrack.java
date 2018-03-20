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
    
    private boolean startTheRace = false, proceedToStartLine = false, makeAMove = false;
    
    
    @Override
    public synchronized void startTheRace(){
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.SUPERVISING_THE_RACE);
        
        this.startTheRace = true;
        notifyAll();
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
        
        this.proceedToStartLine = true;
    };
    
    @Override
    public synchronized void waitForProceedToStartLine(){
    while(!this.proceedToStartLine){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
        this.proceedToStartLine = false;
    };
    
    @Override
    public synchronized boolean hasFinishLineBeenCrossed(){
        // will do something
        return false;
    
    };
    
    @Override
    public synchronized void makeAMove(){
        if(this.hasFinishLineBeenCrossed()){
            ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_FINISH_LINE);
        }else{
            ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.RUNNNING);
        }
        
        this.makeAMove = true;
        notifyAll();
    };
    
}
