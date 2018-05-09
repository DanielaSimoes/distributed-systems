package entities;

import shared.RacingTrackStub;
import shared.PaddockStub;
import shared.ControlCentreStub;
import shared.BettingCentreStub;
import shared.StableStub;
import GeneralRepository.RacesStub;
import GeneralRepository.LogStub;
import communication.Proxy.Proxy;
import communication.message.Message;
import communication.message.MessageType;
import settings.NodeSettsStub;


/**
 * This class implements the main of the entity broker.
 * @author Daniela Simões, 76771
 */
public class BrokerRun {
    
    private static StableStub stable = new StableStub();
    private static RacingTrackStub racingTrack = new RacingTrackStub();
    private static PaddockStub paddock = new PaddockStub();
    private static ControlCentreStub controlCentre = new ControlCentreStub();
    private static BettingCentreStub bettingCentre = new BettingCentreStub();
    private static RacesStub races = new RacesStub();
    
    private static Broker broker;
    
    public static void main(String [] args) {  
        LogStub logProxy = new LogStub();
        NodeSettsStub nodeSettsProxy = new NodeSettsStub();
        
        broker = new Broker((shared.IStable) stable, (shared.IControlCentre) controlCentre, (shared.IBettingCentre) bettingCentre, (shared.IRacingTrack) racingTrack, (shared.IPaddock) paddock, logProxy, races);
        
        broker.start();
        
        try {
            System.out.println("Broker started!\n");
            broker.join();
            System.err.println("Broker Died");
        } catch (InterruptedException ex) {
            // do something in the future
        }
        
        /* SEND TO LOG THAT BROKER HAS FINISHED */
        Proxy.connect(nodeSettsProxy.SERVER_HOSTS().get("Log"), 
        nodeSettsProxy.SERVER_PORTS().get("Log"), 
        new Message(MessageType.TERMINATE));
    }

}
