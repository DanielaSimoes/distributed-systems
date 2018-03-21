package entities;

import GeneralRepository.Log;
import GeneralRepository.Races;

/**
 *
 * @author Daniela
 */
public class HorseJockey extends Thread{
    
    private HorseJockeyState state;
    
    /*
        Shared zones in which HorseJockey has actions
    */
    
    private final Log log;
    private final int id;
    private final shared.IStable stable;
    private final shared.IControlCentre cc;
    private final shared.IPaddock paddock;
    private final shared.IRacingTrack rt;
    
    private final double stepSize;
    
    public HorseJockey(shared.IStable s, shared.IControlCentre cc, shared.IPaddock paddock, shared.IRacingTrack rt, double stepSize, int id){
        this.stable = s;
        this.cc = cc;
        this.paddock = paddock;
        this.rt = rt;
        this.stepSize = stepSize;
        this.id = id;
        this.log = Log.getInstance();
        this.state = HorseJockeyState.AT_THE_STABLE;
    }
    
    @Override
    public void run(){  
        stable.proceedToStable();
                    
        while(Races.actual_race < Races.N_OF_RACES){
            switch(this.state){
                case AT_THE_STABLE:
                    cc.proceedToPaddock();
                    paddock.proceedToPaddock();
                    System.out.println("Out of paddock");
                    break;
                case AT_THE_PADDOCK:
                    paddock.proceedToStartLine();
                    rt.proceedToStartLine();
                    System.out.println("Out of proceedToStartLine");
                    break;
                case AT_THE_START_LINE:
                    rt.makeAMove();
                    System.out.println("Out of makeAMove1");
                    break;
                case RUNNNING:
                    while(rt.hasFinishLineBeenCrossed() == false) rt.makeAMove();
                    break;
                case AT_THE_FINISH_LINE:
                    stable.proceedToStable();
                    break;

            }
        }
    }
    
    public int getHorseId(){
        return this.id;
    }
    
    public void setHorseJockeyState(HorseJockeyState state){
        this.state = state;
        this.log.setHorseJockeyState(id, state);
    } 
    
    public HorseJockeyState getHorseJockeyState(){
        return this.state;
    }
    
    public double getStepSize(){
        return this.stepSize;
    }
}
