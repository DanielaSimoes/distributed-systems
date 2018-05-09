package entities;

import shared.RacingTrackStub;
import shared.PaddockStub;
import shared.ControlCentreStub;
import shared.BettingCentreStub;
import shared.StableStub;
import GeneralRepository.RacesStub;
import GeneralRepository.LogStub;
import communication.stub.Stub;
import communication.message.Message;
import communication.message.MessageType;
import settings.NodeSettsStub;


/**
 * This class implements the main of the entity broker.
 * @author Daniela Simões, 76771
 */
public class BrokerRun {
    
    private static StableStub stableStub = new StableStub();
    private static RacingTrackStub racingTrackStub = new RacingTrackStub();
    private static PaddockStub paddockStub = new PaddockStub();
    private static ControlCentreStub controlCentreStub = new ControlCentreStub();
    private static BettingCentreStub bettingCentreStub = new BettingCentreStub();
    private static RacesStub racesStub = new RacesStub();
    
    private static Broker broker;
    
    public static void main(String [] args) {  
        LogStub logStub = new LogStub();
        NodeSettsStub nodeSettsStub = new NodeSettsStub();
        
        broker = new Broker((shared.IStable) stableStub, (shared.IControlCentre) controlCentreStub, (shared.IBettingCentre) bettingCentreStub, (shared.IRacingTrack) racingTrackStub, (shared.IPaddock) paddockStub, logStub, racesStub);
        
        broker.start();
        
        try {
            System.out.println("Broker started!\n");
            broker.join();
            System.err.println("Broker Died");
        } catch (InterruptedException ex) {
            // do something in the future
        }
        
        /* SEND TO LOG THAT BROKER HAS FINISHED */
        Stub.connect(nodeSettsStub.SERVER_HOSTS().get("Log"),  
                nodeSettsStub.SERVER_PORTS().get("Log"),  
                new Message(MessageType.TERMINATE));
    }

}
