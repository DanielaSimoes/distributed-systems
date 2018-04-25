/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import shared.RacingTrackProxy;
import shared.PaddockProxy;
import shared.ControlCentreProxy;
import shared.StableProxy;
import GeneralRepository.RacesProxy;
import communication.message.Message;
import communication.message.MessageType;
import communication.Proxy.ClientProxy;
import java.util.ArrayList;
import settings.NodeSettsProxy;

/**
 *
 * @author Daniela
 */
public class HorseJockeyRun {
    private static StableProxy stable = new StableProxy();
    private static RacingTrackProxy racingTrack = new RacingTrackProxy();
    private static PaddockProxy paddock = new PaddockProxy();
    private static ControlCentreProxy controlCentre = new ControlCentreProxy();
    private static RacesProxy races = new RacesProxy();
    
    private static int N_OF_HORSES;

    public static void main(String [] args) {
        LogProxy log = new LogProxy();
        NodeSettsProxy proxy = new NodeSettsProxy(); 
        
        N_OF_HORSES = proxy.N_OF_HORSES();
        ArrayList<HorseJockey> horseJockey = new ArrayList<>(N_OF_HORSES);

        for(int i = 0; i < N_OF_HORSES; i++){
            horseJockey.add(new HorseJockey((shared.IStable) stable, (shared.IControlCentre) controlCentre, (shared.IPaddock) paddock, (shared.IRacingTrack) racingTrack, (int) (Math.random() * (proxy.HORSE_MAX_STEP_SIZE() - 1)) + 1, i, races, log));
        }
        
        for (HorseJockey horse : horseJockey)
            horse.start();
        
        for (HorseJockey horse : horseJockey) { 
            try { 
                horse.join ();
                System.out.printf("Horse Jockey %d died!\n", horse.getHorseId()); 
            } catch (InterruptedException e) {}
        }
                
        /* SEND TO LOG THAT HORSEJOCKEY HAS FINISHED */
        ClientProxy.connect(proxy.SERVER_HOSTS().get("Log"), 
                proxy.SERVER_PORTS().get("Log"), 
                new Message(MessageType.TERMINATE));
        
    }
}
