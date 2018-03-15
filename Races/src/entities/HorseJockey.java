package entities;

/**
 *
 * @author Daniela
 */
public class HorseJockey extends Thread{
    
    private HorseJockeyState state;
    
    /*
        Shared zones in which HorseJockey has actions
    */
    
    private final shared.IStable stable;
    private final shared.IControlCentre cc;
    private final shared.IPaddock paddock;
    private final shared.IRacingTrack rt;
    
    public HorseJockey(shared.IStable s, shared.IControlCentre cc, shared.IPaddock paddock, shared.IRacingTrack rt){
        this.stable = s;
        this.cc = cc;
        this.paddock = paddock;
        this.rt = rt;
    }
    
    @Override
    public void run(){  
    
        switch(this.state){
            case AT_THE_STABLE:
                stable.proceedToStable();
                break;
            case AT_THE_PADDOCK:
                cc.proceedToPaddock();
                paddock.proceedToPaddock();
                break;
            case AT_THE_START_LINE:
                paddock.proceedToStartLine();
                rt.proceedToStartLine();
                break;
            case RUNNNING:
                while(!rt.hasFinishLineBeenCrossed()) rt.makeAMove();
                break;
            case AT_THE_FINISH_LINE:
                stable.proceedToStable();
                break;
        
        }
    }
}
