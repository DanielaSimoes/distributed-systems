package shared;

/**
 *
 * @author Daniela
 */
public class Stable implements IStable {
    
    private boolean wakeHorsesToPaddock = false, proceedToStable = false;
     
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
    public synchronized void proceedToStable(){
        // incomplete - waken up by summonHorsesToPaddock AND entertainTheGuests
        
        try{
            wait();
        }catch (InterruptedException ex){
                // do something in the future
        }
        
        this.proceedToStable = true;
    };
}
