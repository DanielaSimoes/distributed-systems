package entities;

import shared.PaddockStub;
import shared.ControlCentreStub;
import shared.BettingCentreStub;
import GeneralRepository.RacesStub;
import GeneralRepository.LogStub;
import communication.Proxy.Proxy;
import communication.message.Message;
import communication.message.MessageType;
import java.util.ArrayList;
import settings.NodeSettsStub;

/**
 * This class implements the main of the entity spectator.
 * @author Daniela Simões, 76771
 */
public class SpectatorsRun {
    private static PaddockStub paddock = new PaddockStub();
    private static ControlCentreStub controlCentre = new ControlCentreStub();
    private static BettingCentreStub bettingCentre = new BettingCentreStub();
    private static RacesStub races = new RacesStub();
    
    private static int N_OF_SPECTATORS;

    public static void main(String [] args) {
        LogStub log = new LogStub();
        
        /* init proxies */
        paddock = new PaddockStub();
        controlCentre = new ControlCentreStub();
        bettingCentre = new BettingCentreStub();
        /* end init proxies */
        
        NodeSettsStub proxy = new NodeSettsStub(); 
        N_OF_SPECTATORS = proxy.N_OF_SPECTATORS();
        
        ArrayList<Spectators> spectators = new ArrayList<>(N_OF_SPECTATORS);

        for(int i = 0; i < N_OF_SPECTATORS; i++){
            spectators.add(new Spectators((shared.IControlCentre) controlCentre, (shared.IBettingCentre) bettingCentre , (shared.IPaddock) paddock, (int) (Math.random() * (proxy.MAX_SPECTATOR_BET() - 200)) + 200, i, races, log));
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
        Proxy.connect(proxy.SERVER_HOSTS().get("Log"), 
                proxy.SERVER_PORTS().get("Log"), 
                new Message(MessageType.TERMINATE));
        
    }
}
