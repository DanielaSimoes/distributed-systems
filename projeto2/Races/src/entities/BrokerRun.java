package entities;

import shared.RacingTrackProxy;
import shared.PaddockProxy;
import shared.ControlCentreProxy;
import shared.BettingCentreStub;
import shared.StableProxy;
import GeneralRepository.RacesProxy;
import GeneralRepository.LogProxy;
import communication.Proxy.Proxy;
import communication.message.Message;
import communication.message.MessageType;
import settings.NodeSettsProxy;


/**
 * This class implements the main of the entity broker.
 * @author Daniela Simões, 76771
 */
public class BrokerRun {
    
    private static StableProxy stable = new StableProxy();
    private static RacingTrackProxy racingTrack = new RacingTrackProxy();
    private static PaddockProxy paddock = new PaddockProxy();
    private static ControlCentreProxy controlCentre = new ControlCentreProxy();
    private static BettingCentreStub bettingCentre = new BettingCentreStub();
    private static RacesProxy races = new RacesProxy();
    
    private static Broker broker;
    
    public static void main(String [] args) {  
        LogProxy logProxy = new LogProxy();
        NodeSettsProxy nodeSettsProxy = new NodeSettsProxy();
        
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
