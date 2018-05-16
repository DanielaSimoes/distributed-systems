package shared.paddock;

import interfaces.IPaddock;
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
 * This file contains the shared memory region Paddock.
 * @author Daniela Simes, 76771
 */
public class Paddock implements IPaddock {
    
    private interfaces.IRaces races;
    
    public Paddock(interfaces.IRaces races){
        this.races = races;
    }
    
    /**
    *
    * Method to send the horses to paddock.
     * @param raceNumber
    */
    @Override
    public synchronized void proceedToPaddock(int raceNumber){
        while(!this.races.allSpectatorsArrivedAtPaddock(raceNumber)){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
    };
    
    /**
    *
    * Method to send the horses to the start line.
     * @param raceNumber
    */
    @Override
    public synchronized void proceedToStartLine(int raceNumber){
        this.races.addNHorseJockeyLeftThePadock(raceNumber);
        
        if(this.races.allHorseJockeyLeftThePadock(raceNumber)){
            notifyAll();
        }
    };
    
    /**
    *
    * Method to get the broker to announce the next race.
     * @param raceNumber
    */
    @Override
    public synchronized void summonHorsesToPaddock(int raceNumber){
        while(!this.races.allSpectatorsArrivedAtPaddock(raceNumber)){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
    };
    
    /**
    *
    * Method to get spectators to go check the horses.
     * @param raceNumber
    */
    @Override
    public synchronized void goCheckHorses(int raceNumber){
        this.races.addNSpectatorsArrivedAtPaddock(raceNumber);
        notifyAll();
    
        while(!this.races.allHorseJockeyLeftThePadock(raceNumber)){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
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
            Logger.getLogger(Paddock.class.getName()).log(Level.SEVERE, null, ex);
        }

        String nameEntryBase = RegistryConfigs.registerHandler;
        String nameEntryObject = RegistryConfigs.paddockNameEntry;

        try {
            reg = (RegisterInterface) registry.lookup(nameEntryBase);
        } catch (RemoteException e) {
            System.out.println("RegisterRemoteObject lookup exception: " + e.getMessage());
            Logger.getLogger(Paddock.class.getName()).log(Level.SEVERE, null, e);
        } catch (NotBoundException e) {
            System.out.println("RegisterRemoteObject not bound exception: " + e.getMessage());
            Logger.getLogger(Paddock.class.getName()).log(Level.SEVERE, null, e);
        }
        
        try {
            // Unregister ourself
            reg.unbind(nameEntryObject);
        } catch (RemoteException e) {
            System.out.println("Paddock registration exception: " + e.getMessage());
            Logger.getLogger(Paddock.class.getName()).log(Level.SEVERE, null, e);
        } catch (NotBoundException e) {
            System.out.println("Paddock not bound exception: " + e.getMessage());
            Logger.getLogger(Paddock.class.getName()).log(Level.SEVERE, null, e);
        }

        try {
            // Unexport; this will also remove us from the RMI runtime
            UnicastRemoteObject.unexportObject(this, true);
        } catch (NoSuchObjectException ex) {
            Logger.getLogger(Paddock.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Paddock closed.");
    }
    
}
