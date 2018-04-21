/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import GeneralRepository.RacesProxy;
import communication.message.Message;
import communication.message.MessageType;
import communication.Proxy.ClientProxy;
import settings.NodeSettsProxy;


/**
 *
 * @author Daniela
 */
public class BrokerRun {
    
    private static StableProxy stable;
    private static RacingTrackProxy racingTrack;
    private static PaddockProxy paddock;
    private static ControlCentreProxy controlCentre;
    private static BettingCentreProxy bettingCentre;
    private static RacesProxy races;
    
    private static Broker broker;
    
    public static void main(String [] args) {  
        LogProxy log = new LogProxy();
        NodeSettsProxy proxy = new NodeSettsProxy();
        
        broker = new Broker((shared.IStable) stable, (shared.IControlCentre) controlCentre, (shared.IBettingCentre) bettingCentre, (shared.IRacingTrack) racingTrack, (shared.IPaddock) paddock, log, races);
        
        broker.start();
        
        try {
            broker.join();
            System.err.println("Broker Died");
        } catch (InterruptedException ex) {
            // do something in the future
        }
        
        /* SEND TO LOG THAT BROKER HAS FINISHED */
        ClientProxy.connect(proxy.SERVER_HOSTS().get("Log"), 
        proxy.SERVER_PORTS().get("Log"), 
        new Message(MessageType.TERMINATE));
    }

}
