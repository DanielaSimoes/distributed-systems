package shared.stable;

import interfaces.IStable;
import interfaces.RegisterInterface;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import structures.constants.Constants;
import structures.constants.RegistryConfigs;

/**
 * This file contains the shared memory region Stable.
 * @author Daniela Simes, 76771
 */
public class Stable implements IStable {
    
    private boolean wakeEntertainTheGuests = false;
    
    private final interfaces.IRaces races;
    
    public Stable(interfaces.IRaces races){
        this.races = races;
    }
     
    /**
    *
    * Method to get the broker to announce the next race.
     * @param raceNumber
    */
    @Override
    public synchronized void summonHorsesToPaddock(int raceNumber){
        this.races.setAnnouncedNextRace(true, raceNumber);
        notifyAll();
    };
    
    /**
    *
    * Method to get the horses to proceed to stable.
     * @param raceNumber
     * @param horseID
     * @param horseStepSize
     * @return 
    */
    @Override
    public synchronized int proceedToStable(int raceNumber, int horseID, int horseStepSize){
        while(!((races.getAnnouncedNextRace(raceNumber) && this.races.getWakedHorsesToPaddock(raceNumber)!=races.getNRunningHorses(raceNumber) && races.horseHasBeenSelectedToRace(horseID, horseStepSize, raceNumber)) || wakeEntertainTheGuests)){
            try{
                wait();
            }catch (InterruptedException ex){
                    // do something in the future
            } 
            
            if(raceNumber<Constants.N_OF_RACES && races.horsesFinished(raceNumber) && races.hasMoreRaces()){
                raceNumber++;
            }
        }
        
        if(!wakeEntertainTheGuests){
            this.races.addWakedHorsesToPaddock(raceNumber);
        }
        
        return raceNumber;
    };
    
    /**
    *
    * Method to get the broker to entertain the guests - death state.
    */
    @Override
    public synchronized void entertainTheGuests(){
        wakeEntertainTheGuests = true;
        notifyAll();
    }

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
            Logger.getLogger(Stable.class.getName()).log(Level.SEVERE, null, ex);
        }

        String nameEntryBase = RegistryConfigs.registerHandler;
        String nameEntryObject = RegistryConfigs.stableNameEntry;

        try {
            reg = (RegisterInterface) registry.lookup(nameEntryBase);
        } catch (RemoteException e) {
            System.out.println("RegisterRemoteObject lookup exception: " + e.getMessage());
            Logger.getLogger(Stable.class.getName()).log(Level.SEVERE, null, e);
        } catch (NotBoundException e) {
            System.out.println("RegisterRemoteObject not bound exception: " + e.getMessage());
            Logger.getLogger(Stable.class.getName()).log(Level.SEVERE, null, e);
        }
        
        try {
            // Unregister ourself
            reg.unbind(nameEntryObject);
        } catch (RemoteException e) {
            System.out.println("Stable registration exception: " + e.getMessage());
            Logger.getLogger(Stable.class.getName()).log(Level.SEVERE, null, e);
        } catch (NotBoundException e) {
            System.out.println("Stable not bound exception: " + e.getMessage());
            Logger.getLogger(Stable.class.getName()).log(Level.SEVERE, null, e);
        }

        try {
            // Unexport; this will also remove us from the RMI runtime
            UnicastRemoteObject.unexportObject(this, true);
        } catch (NoSuchObjectException ex) {
            Logger.getLogger(Stable.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Stable closed.");
    }

}
