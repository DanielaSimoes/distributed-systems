package entities;

import shared.PaddockStub;
import shared.ControlCentreStub;
import shared.BettingCentreStub;
import GeneralRepository.RacesStub;
import GeneralRepository.LogStub;
import communication.stub.Stub;
import communication.message.Message;
import communication.message.MessageType;
import java.util.ArrayList;
import settings.NodeSettsStub;

/**
 * This class implements the main of the entity spectator.
 * @author Daniela Simões, 76771
 */
public class SpectatorsRun {
    private static PaddockStub paddockStub = new PaddockStub();
    private static ControlCentreStub controlCentreStub = new ControlCentreStub();
    private static BettingCentreStub bettingCentreStub = new BettingCentreStub();
    private static RacesStub racesStub = new RacesStub();
    
    private static int N_OF_SPECTATORS;

    public static void main(String [] args) {
        LogStub logStub = new LogStub();
        
        /* init proxies */
        paddockStub = new PaddockStub();
        controlCentreStub = new ControlCentreStub();
        bettingCentreStub = new BettingCentreStub();
        /* end init proxies */
        
        NodeSettsStub nodeSettsStub = new NodeSettsStub(); 
        N_OF_SPECTATORS = nodeSettsStub.N_OF_SPECTATORS();
        
        ArrayList<Spectators> spectators = new ArrayList<>(N_OF_SPECTATORS);

        for(int i = 0; i < N_OF_SPECTATORS; i++){
            spectators.add(new Spectators((shared.IControlCentre) controlCentreStub, (shared.IBettingCentre) bettingCentreStub , (shared.IPaddock) paddockStub, (int) (Math.random() * (nodeSettsStub.MAX_SPECTATOR_BET() - 200)) + 200, i, racesStub, logStub));
        }
        
        for (Spectators spectator : spectators)
            spectator.start();
        
        for (Spectators spectator : spectators) { 
            try { 
                spectator.join ();
                System.err.printf("Spectator %d died!\n", spectator.getSpectatorId()); 
            } catch (InterruptedException e) {}
        }
                
        /* SEND TO LOG THAT SPECTATOR HAS FINISHED */
        Stub.connect(nodeSettsStub.SERVER_HOSTS().get("Log"), 
                nodeSettsStub.SERVER_PORTS().get("Log"), 
                new Message(MessageType.TERMINATE));
        
    }
}
