package shared;

/**
 *
 * @author Daniela
 */
public class ControlCentre implements IControlCentre {
    
    private boolean wakeHorsesToPaddock = false, startTheRace = false, entertainTheGuests=false, reportResults=false, proceedToPaddock = false, goWatchTheRace=false, goCheckHorses = false;
    
    @Override
    public synchronized void goCheckHorses(){
        this.goCheckHorses = true;
        notifyAll();
    };
    
    @Override
    public synchronized void waitForGoCheckHorses(){
        while(!this.goCheckHorses){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
        this.goCheckHorses = false;  
    };
    
    @Override
    public synchronized void summonHorsesToPaddock(){
        this.wakeHorsesToPaddock = true;
        notifyAll();
    };
    
    @Override
    public synchronized void waitForSummonHorsesToPaddock(){
        while(!this.wakeHorsesToPaddock){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
        this.wakeHorsesToPaddock = false;  
    };
    
    @Override
    public synchronized void startTheRace(){
        this.startTheRace = true;
        notifyAll();
    };
    
    @Override
    public synchronized void waitForStartTheRace(){
        while(!this.startTheRace){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
        this.startTheRace = false;
    };
    
    @Override
    public synchronized void entertainTheGuests(){
        this.entertainTheGuests = true;
        notifyAll();
    };
    
    @Override
    public synchronized void reportResults(){
        this.reportResults = true;
        notifyAll();
    };
    
    @Override
    public synchronized void proceedToPaddock(){
        this.proceedToPaddock = true;
        notifyAll();
    };
    
    @Override
    public synchronized void waitForProceedToPaddock(){
        while(!this.proceedToPaddock){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
        this.proceedToPaddock = false;
    };
    
    @Override
    public synchronized boolean waitForNextRace(){
       return this.proceedToPaddock;
    };
    
    @Override
    public synchronized void goWatchTheRace(){
        this.goWatchTheRace = true;
    };
    
    @Override
    public synchronized void waitForGoWatchTheRace(){
         while(!this.goWatchTheRace){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
        this.goWatchTheRace = false;
    };
    
    @Override
    public synchronized boolean haveIWon(){
        return false;
    };
   
    
    @Override
    public synchronized void relaxABit(){
    
    };
}
