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
    private final Races races = Races.getInstace();
    
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
        this.setName("HorseJockey " + id);
    }
    
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
                    System.out.println("Horse "+id+" out of running");
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
