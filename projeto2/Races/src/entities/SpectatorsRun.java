/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import communication.Proxy.ClientProxy;
import communication.message.Message;
import communication.message.MessageType;
import java.util.ArrayList;
import settings.NodeSettsProxy;

/**
 *
 * @author Daniela
 */
public class SpectatorsRun {
    private static PaddockProxy paddock;
    private static ControlCentreProxy controlCentre;
    private static BettingCentreProxy bettingCentre;
    
    private static int N_OF_SPECTATORS;

    public static void main(String [] args) {
        
        NodeSettsProxy proxy = new NodeSettsProxy(); 
        N_OF_SPECTATORS = proxy.N_OF_SPECTATORS();
        ArrayList<Spectators> spectators = new ArrayList<>(N_OF_SPECTATORS);

        for(int i = 0; i < N_OF_SPECTATORS; i++){
            spectators.add(new Spectators((shared.IControlCentre) controlCentre, (shared.IBettingCentre) bettingCentre , (shared.IPaddock) paddock, (int) (Math.random() * (proxy.MAX_SPECTATOR_BET() - 200)) + 200, i));
        }
        
        for (Spectators spectator : spectators)
            spectator.start();
        
        for (Spectators spectator : spectators) { 
            try { 
                spectator.join ();
            } catch (InterruptedException e) {}
        }
                
        /* SEND TO LOG THAT SPECTATOR HAS FINISHED */
        ClientProxy.connect(proxy.SERVER_HOSTS().get("Log"), 
                proxy.SERVER_PORTS().get("Log"), 
                new Message(MessageType.TERMINATE));
        
    }
}