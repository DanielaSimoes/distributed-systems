package shared;

/**
 *
 * @author Daniela
 */
public class BettingCentre implements IBettingCentre {
    
    private boolean betsOfSpectators[], paidSpectators[] = new boolean[4];
    private boolean waitingInQueueToBet, waitingInQueueToCollectTheGains = true;
    
    @Override
    public synchronized void acceptTheBets(){
        for(int i = 0; i < 4; i++){
            if (!betsOfSpectators[i]) {
                try{
                    wait();
                }catch (InterruptedException ex){
                    // do something in the future
                }
            }
        }
    };
    
    @Override
    public synchronized void honourTheBets(){
        for(int i = 0; i < 4; i++){
            if (!paidSpectators[i]) {
                try{
                    wait();
                }catch (InterruptedException ex){
                    // do something in the future
                }
            }
        }
    };
    
    @Override
    public synchronized boolean areThereAnyWinners(){
        // verify something and return
        return false;
    };
    
    @Override
    public synchronized void placeABet(){
        while(waitingInQueueToBet){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
    };
    
    @Override
    public synchronized void goCollectTheGains(){
        while(waitingInQueueToCollectTheGains){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
    };
}
