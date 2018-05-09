package entities;

import shared.RacingTrackStub;
import shared.PaddockStub;
import shared.ControlCentreStub;
import shared.StableStub;
import GeneralRepository.RacesStub;
import GeneralRepository.LogStub;
import communication.stub.Stub;
import communication.message.Message;
import communication.message.MessageType;
import java.util.ArrayList;
import settings.NodeSettsStub;
/**
 * This class implements the main of the entity horse jockey.
 * @author Daniela Simões, 76771
 */
public class HorseJockeyRun {
    private static StableStub stableStub = new StableStub();
    private static RacingTrackStub racingTrackStub = new RacingTrackStub();
    private static PaddockStub paddockStub = new PaddockStub();
    private static ControlCentreStub controlCentreStub = new ControlCentreStub();
    private static RacesStub racesStub = new RacesStub();
    
    private static int N_OF_HORSES;

    public static void main(String [] args) {
        LogStub logStub = new LogStub();
        NodeSettsStub nodeSettsStub = new NodeSettsStub(); 
        
        N_OF_HORSES = nodeSettsStub.N_OF_HORSES();
        ArrayList<HorseJockey> horseJockey = new ArrayList<>(N_OF_HORSES);

        for(int i = 0; i < N_OF_HORSES; i++){
            int stepSize = (int) (Math.random() * (nodeSettsStub.HORSE_MAX_STEP_SIZE() - 1)) + 1;
            racesStub.setHorseJockeyStepSize(i, stepSize);
            horseJockey.add(new HorseJockey((shared.IStable) stableStub, (shared.IControlCentre) controlCentreStub, (shared.IPaddock) paddockStub, (shared.IRacingTrack) racingTrackStub, stepSize, i, racesStub, logStub));
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
        Stub.connect(nodeSettsStub.SERVER_HOSTS().get("Log"), 
                nodeSettsStub.SERVER_PORTS().get("Log"), 
                new Message(MessageType.TERMINATE));
        
    }
}
