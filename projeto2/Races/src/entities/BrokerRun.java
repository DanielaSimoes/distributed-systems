/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import shared.RacingTrackProxy;
import shared.PaddockProxy;
import shared.ControlCentreProxy;
import shared.BettingCentreProxy;
import shared.StableProxy;
import GeneralRepository.RacesProxy;
import GeneralRepository.LogProxy;
import communication.Proxy.Proxy;
import communication.message.Message;
import communication.message.MessageType;
import settings.NodeSettsProxy;


/**
 *
 * @author Daniela
 */
public class BrokerRun {
    
    private static StableProxy stable = new StableProxy();
    private static RacingTrackProxy racingTrack = new RacingTrackProxy();
    private static PaddockProxy paddock = new PaddockProxy();
    private static ControlCentreProxy controlCentre = new ControlCentreProxy();
    private static BettingCentreProxy bettingCentre = new BettingCentreProxy();
    private static RacesProxy races = new RacesProxy();
    
    private static Broker broker;
    
    public static void main(String [] args) {  
        LogProxy log = new LogProxy();
        NodeSettsProxy proxy = new NodeSettsProxy();
        
        broker = new Broker((shared.IStable) stable, (shared.IControlCentre) controlCentre, (shared.IBettingCentre) bettingCentre, (shared.IRacingTrack) racingTrack, (shared.IPaddock) paddock, log, races);
        
        broker.start();
        
        try {
            System.out.println("Broker started!\n");
            broker.join();
            System.err.println("Broker Died");
        } catch (InterruptedException ex) {
            // do something in the future
        }
        
        /* SEND TO LOG THAT BROKER HAS FINISHED */
        Proxy.connect(proxy.SERVER_HOSTS().get("Log"), 
        proxy.SERVER_PORTS().get("Log"), 
        new Message(MessageType.TERMINATE));
    }

}
