package shared.racingTrack;

import interfaces.IRacingTrack;
import interfaces.RegisterInterface;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import structures.constants.RegistryConfigs;

/**
 * This file contains the shared memory region Racing Track.
 * @author Daniela Simões, 76771
 */
public class RacingTrack implements IRacingTrack {
    
    private final interfaces.IRaces races;
    
    public RacingTrack(interfaces.IRaces races){
        this.races = races;
    }
    
    /**
    *
    * Method to start the race.
     * @param raceNumber
    */
    @Override
    public synchronized void startTheRace(int raceNumber){
        races.setStartTheRace(true, raceNumber);
        notifyAll();
        
        while(!races.horsesFinished(raceNumber)){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
        
        races.setStartTheRace(false, raceNumber);
    };
    
    /**
    *
    * Method to get the horses to proceed to the start line.
     * @param raceNumber
     * @param horseId
    */
    @Override
    public synchronized void proceedToStartLine(int raceNumber, int horseId){
        while(!(races.getStartTheRace(raceNumber) || !this.races.nextMovingHorse(horseId, raceNumber))){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
        
        notifyAll();
    };
    
    /**
    *
    * Method to verify if a given horse crossed the finish line.
    * @param horseId The HorseJockey ID.
     * @param raceNumber
     * @return 
    */
    @Override
    public synchronized boolean hasFinishLineBeenCrossed(int horseId, int raceNumber){  
        return races.horseFinished(horseId, raceNumber);
    };
    
    /**
    *
    * Method to make a move - an iteration in the racing track.
     * @param raceNumber
     * @param horseId
    */
    @Override
    public synchronized void makeAMove(int raceNumber, int horseId){
        while(!this.races.nextMovingHorse(horseId, raceNumber)){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
        
        races.makeAMove(horseId, raceNumber);
        
        notifyAll();
    };   

    @Override
    public void signalShutdown() throws RemoteException {
        RegisterInterface reg = null;
        Registry registry = null;

        String rmiRegHostName;
        int rmiRegPortNumb;

        RegistryConfigs rc = new RegistryConfigs("config.ini");
        rmiRegHostName = rc.registryHost();
        rmiRegPortNumb = rc.registryPort();

        try {
            registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
        } catch (RemoteException ex) {
            Logger.getLogger(RacingTrack.class.getName()).log(Level.SEVERE, null, ex);
        }

        String nameEntryBase = RegistryConfigs.registerHandler;
        String nameEntryObject = RegistryConfigs.racingTrackNameEntry;

        try {
            reg = (RegisterInterface) registry.lookup(nameEntryBase);
        } catch (RemoteException e) {
            System.out.println("RegisterRemoteObject lookup exception: " + e.getMessage());
            Logger.getLogger(RacingTrack.class.getName()).log(Level.SEVERE, null, e);
        } catch (NotBoundException e) {
            System.out.println("RegisterRemoteObject not bound exception: " + e.getMessage());
            Logger.getLogger(RacingTrack.class.getName()).log(Level.SEVERE, null, e);
        }
        
        try {
            // Unregister ourself
            reg.unbind(nameEntryObject);
        } catch (RemoteException e) {
            System.out.println("RacingTrack registration exception: " + e.getMessage());
            Logger.getLogger(RacingTrack.class.getName()).log(Level.SEVERE, null, e);
        } catch (NotBoundException e) {
            System.out.println("RacingTrack not bound exception: " + e.getMessage());
            Logger.getLogger(RacingTrack.class.getName()).log(Level.SEVERE, null, e);
        }

        try {
            // Unexport; this will also remove us from the RMI runtime
            UnicastRemoteObject.unexportObject(this, true);
        } catch (NoSuchObjectException ex) {
            Logger.getLogger(RacingTrack.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("RacingTrack closed.");
    }

}
