package main;

/**
 *
 * @author Daniela
 */

import entities.Broker;
import entities.HorseJockey;
import entities.Spectators;

import shared.BettingCentre;
import shared.ControlCentre;
import shared.Paddock;
import shared.RacingTrack;
import shared.Stable;

import GeneralRepository.Log;
import GeneralRepository.Races;

public class Main {
    
    private static Stable stable;
    private static RacingTrack racingTrack;
    private static Paddock paddock;
    private static ControlCentre controlCentre;
    private static BettingCentre bettingCentre;
    
    private static Broker broker;
    private static HorseJockey horseJockey[];
    private static Spectators spectators[];
    
    private static Log lg;
    private static Races races; 
    
    /**
    *    Main method.
    *
    *    @param args runtime arguments
    */
    public static void main(String[] args){
    
        int nRaces = Races.N_OF_RACES;
        int nHorses = Races.N_OF_HORSES;
        int nSpectators = Races.N_OF_SPECTATORS;
                
        stable = new Stable();
        racingTrack = new RacingTrack();
        paddock = new Paddock();
        controlCentre = new ControlCentre();
        bettingCentre = new BettingCentre();
        
        lg = Log.getInstance();
        
        horseJockey = new HorseJockey[nHorses];
        for(int i = 0; i < nHorses; i++){
            horseJockey[i] = new HorseJockey((shared.IStable) stable, (shared.IControlCentre) controlCentre, (shared.IPaddock) paddock, (shared.IRacingTrack) racingTrack, (int) (Math.random() * (Races.HORSE_MAX_STEP_SIZE - 1)) + 1, i);
        }
        
        spectators = new Spectators[nSpectators];
        for(int i = 0; i < nSpectators; i++){
            spectators[i] = new Spectators((shared.IControlCentre) controlCentre, (shared.IBettingCentre) bettingCentre , (shared.IPaddock) paddock, (double) (Math.random() * (Races.MAX_SPECTATOR_BET - 200)) + 200, i);
        }
        
        broker = new Broker((shared.IStable) stable, (shared.IControlCentre) controlCentre, (shared.IBettingCentre) bettingCentre, (shared.IRacingTrack) racingTrack, (shared.IPaddock) paddock);
        
        for(int k = 0; k < nHorses; k++){
            horseJockey[k].start();
        }
       
        for(int k = 0; k < nSpectators; k++){
            spectators[k].start();
        }
        
        broker.start();
        
        for(int j = 0; j < nHorses; j++){
            try {
                horseJockey[j].join();
                System.err.println("HorseJockey " + j + " Died ");
            } catch (InterruptedException ex) {
                //Escrever para o log
            }
        }
        
        for(int j = 0; j < nSpectators; j++){
            try {
                spectators[j].join();
                System.err.println("Spectator " + j + " Died ");
            } catch (InterruptedException ex) {
                //Escrever para o log
            }
        }
        
        try {
            broker.join();
            System.err.println("Broker Died");

        } catch (InterruptedException ex) {
            //Escrever para o log
        }
    
        //lg.writeEnd();
    }
    
}
