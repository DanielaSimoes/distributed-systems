/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import shared.PaddockProxy;
import shared.ControlCentreProxy;
import shared.BettingCentreProxy;
import GeneralRepository.RacesProxy;
import GeneralRepository.LogProxy;
import communication.Proxy.Proxy;
import communication.message.Message;
import communication.message.MessageType;
import java.util.ArrayList;
import settings.NodeSettsProxy;

/**
 *
 * @author Daniela
 */
public class SpectatorsRun {
    private static PaddockProxy paddock = new PaddockProxy();
    private static ControlCentreProxy controlCentre = new ControlCentreProxy();
    private static BettingCentreProxy bettingCentre = new BettingCentreProxy();
    private static RacesProxy races = new RacesProxy();
    
    private static int N_OF_SPECTATORS;

    public static void main(String [] args) {
        LogProxy log = new LogProxy();
        
        /* init proxies */
        paddock = new PaddockProxy();
        controlCentre = new ControlCentreProxy();
        bettingCentre = new BettingCentreProxy();
        /* end init proxies */
        
        NodeSettsProxy proxy = new NodeSettsProxy(); 
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
