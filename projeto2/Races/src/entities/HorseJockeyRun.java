package entities;

import shared.RacingTrackStub;
import shared.PaddockStub;
import shared.ControlCentreStub;
import shared.StableStub;
import GeneralRepository.RacesStub;
import GeneralRepository.LogStub;
import communication.Proxy.Stub;
import communication.message.Message;
import communication.message.MessageType;
import java.util.ArrayList;
import settings.NodeSettsStub;
/**
 * This class implements the main of the entity horse jockey.
 * @author Daniela Simões, 76771
 */
public class HorseJockeyRun {
    private static StableStub stable = new StableStub();
    private static RacingTrackStub racingTrack = new RacingTrackStub();
    private static PaddockStub paddock = new PaddockStub();
    private static ControlCentreStub controlCentre = new ControlCentreStub();
    private static RacesStub races = new RacesStub();
    
    private static int N_OF_HORSES;

    public static void main(String [] args) {
        LogStub log = new LogStub();
        NodeSettsStub proxy = new NodeSettsStub(); 
        
        N_OF_HORSES = proxy.N_OF_HORSES();
        ArrayList<HorseJockey> horseJockey = new ArrayList<>(N_OF_HORSES);

        for(int i = 0; i < N_OF_HORSES; i++){
            int stepSize = (int) (Math.random() * (proxy.HORSE_MAX_STEP_SIZE() - 1)) + 1;
            races.setHorseJockeyStepSize(i, stepSize);
            horseJockey.add(new HorseJockey((shared.IStable) stable, (shared.IControlCentre) controlCentre, (shared.IPaddock) paddock, (shared.IRacingTrack) racingTrack, stepSize, i, races, log));
        }
        
        for (HorseJockey horse : horseJockey)
            horse.start();
        
        for (HorseJockey horse : horseJockey) { 
            try { 
                horse.join ();
                System.err.printf("Horse Jockey %d died!\n", horse.getHorseId()); 
            } catch (InterruptedException e) {}
        }
                
        /* SEND TO LOG THAT HORSEJOCKEY HAS FINISHED */
        Stub.connect(proxy.SERVER_HOSTS().get("Log"), 
                proxy.SERVER_PORTS().get("Log"), 
                new Message(MessageType.TERMINATE));
        
    }
}
