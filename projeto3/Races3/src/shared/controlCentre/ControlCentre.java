package shared.controlCentre;

import interfaces.IControlCentre;
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
 * This file contains the shared memory region Control Centre.
 * @author Daniela Simes, 76771
 */
public class ControlCentre implements IControlCentre {
    
    private final interfaces.IRaces races;
    
    public ControlCentre(interfaces.IRaces races){
        this.races = races;
    }
    
    /**
    *
    * Method to report the bet results to the spectators.
     * @param raceNumber
    */
    @Override
    public synchronized void reportResults(int raceNumber) throws RemoteException{
        this.races.setReportResults(true, raceNumber);
        notifyAll();
    };
    
    /**
    *
    * Method to move the horses to paddock.
     * @param raceNumber
    */
    @Override
    public synchronized void proceedToPaddock(int raceNumber) throws RemoteException{
        this.races.addNHorsesInPaddock(raceNumber);
        
        if (this.races.allNHorsesInPaddock(raceNumber)){
            this.races.setProceedToPaddock(true, raceNumber);
            notifyAll();
        }
    };
  
    
    /**
    *
    * Method to wait for the next race.
     * @param raceNumber
    */
    @Override
    public synchronized void waitForNextRace(int raceNumber) throws RemoteException{
        while(!this.races.getProceedToPaddock(raceNumber) || this.races.horsesFinished(raceNumber)){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
    };
    
    /**
    *
    * Method to send the spectators watch the race.
     * @param raceNumber
    */
    @Override
    public synchronized void goWatchTheRace(int raceNumber) throws RemoteException{
        while(!this.races.getReportResults(raceNumber)){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
    };
    
    /**
    *
    * Method to verify if a spectator has won the bet.
     * @param raceNumber
     * @param spectatorId
     * @return 
    */
    @Override
    public synchronized boolean haveIWon(int raceNumber, int spectatorId) throws RemoteException{
        return this.races.haveIWon(raceNumber, spectatorId);
    };
   
    /**
    *
    * Method to get the spectator to relax a bit - death state.
    */
    @Override
    public synchronized void relaxABit(){};

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
            Logger.getLogger(ControlCentre.class.getName()).log(Level.SEVERE, null, ex);
        }

        String nameEntryBase = RegistryConfigs.registerHandler;
        String nameEntryObject = RegistryConfigs.controlCentreNameEntry;

        try {
            reg = (RegisterInterface) registry.lookup(nameEntryBase);
        } catch (RemoteException e) {
            System.out.println("RegisterRemoteObject lookup exception: " + e.getMessage());
            Logger.getLogger(ControlCentre.class.getName()).log(Level.SEVERE, null, e);
        } catch (NotBoundException e) {
            System.out.println("RegisterRemoteObject not bound exception: " + e.getMessage());
            Logger.getLogger(ControlCentre.class.getName()).log(Level.SEVERE, null, e);
        }
        
        try {
            // Unregister ourself
            reg.unbind(nameEntryObject);
        } catch (RemoteException e) {
            System.out.println("ControlCentre registration exception: " + e.getMessage());
            Logger.getLogger(ControlCentre.class.getName()).log(Level.SEVERE, null, e);
        } catch (NotBoundException e) {
            System.out.println("ControlCentre not bound exception: " + e.getMessage());
            Logger.getLogger(ControlCentre.class.getName()).log(Level.SEVERE, null, e);
        }

        try {
            // Unexport; this will also remove us from the RMI runtime
            UnicastRemoteObject.unexportObject(this, true);
        } catch (NoSuchObjectException ex) {
            Logger.getLogger(ControlCentre.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("ControlCentre closed.");
    }
}
