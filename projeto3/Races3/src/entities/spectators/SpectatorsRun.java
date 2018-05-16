package entities.spectators;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import structures.constants.Constants;
import structures.constants.RegistryConfigs;

/**
 *
 * @author Daniela
 */
public class SpectatorsRun {
    
    
    public static void main(String [] args) {   
        
        ArrayList<Spectators> spectators = new ArrayList<>(Constants.N_OF_SPECTATORS);
        
        // nome do sistema onde esta localizado o servico de registos RMI
        String rmiRegHostName;
        // port de escuta do servico
        int rmiRegPortNumb;

        RegistryConfigs rc = new RegistryConfigs("config.ini");
        rmiRegHostName = rc.registryHost();
        rmiRegPortNumb = rc.registryPort();
        
        interfaces.IControlCentre cci = null;
        interfaces.IPaddock pi = null;
        interfaces.IBettingCentre bci = null;
        interfaces.ILog li = null;
        interfaces.IRaces ri = null;
        
        try
        { 
            Registry registry = LocateRegistry.getRegistry (rmiRegHostName, rmiRegPortNumb);
            li = (interfaces.ILog) registry.lookup (RegistryConfigs.logNameEntry);
        }
        catch (RemoteException e)
        { 
            System.out.println("Exception thrown while locating log: " + e.getMessage () + "!");
            System.exit (1);
        }
        catch (NotBoundException e)
        { 
            System.out.println("Log is not registered: " + e.getMessage () + "!");
            System.exit(1);
        }
        
        try
        { 
            Registry registry = LocateRegistry.getRegistry (rmiRegHostName, rmiRegPortNumb);
            bci = (interfaces.IBettingCentre) registry.lookup (RegistryConfigs.bettingCentreNameEntry);
        }
        catch (RemoteException e)
        { 
            System.out.println("Exception thrown while locating betting centre: " + e.getMessage () + "!");
            System.exit (1);
        }
        catch (NotBoundException e)
        { 
            System.out.println("Betting centre is not registered: " + e.getMessage () + "!");
            System.exit(1);
        }
        
        try
        { 
            Registry registry = LocateRegistry.getRegistry (rmiRegHostName, rmiRegPortNumb);
            cci = (interfaces.IControlCentre) registry.lookup (RegistryConfigs.controlCentreNameEntry);
        }
        catch (RemoteException e)
        { 
            System.out.println("Exception thrown while locating control centre: " + e.getMessage () + "!");
            System.exit (1);
        }
        catch (NotBoundException e)
        { 
            System.out.println("Control centre is not registered: " + e.getMessage () + "!");
            System.exit(1);
        }
        
        try
        { 
            Registry registry = LocateRegistry.getRegistry (rmiRegHostName, rmiRegPortNumb);
            pi = (interfaces.IPaddock) registry.lookup (RegistryConfigs.paddockNameEntry);
        }
        catch (RemoteException e)
        { 
            System.out.println("Exception thrown while locating paddock: " + e.getMessage () + "!");
            System.exit (1);
        }
        catch (NotBoundException e)
        { 
            System.out.println("Paddock is not registered: " + e.getMessage () + "!");
            System.exit(1);
        }
        
        try
        { 
            Registry registry = LocateRegistry.getRegistry (rmiRegHostName, rmiRegPortNumb);
            ri = (interfaces.IRaces) registry.lookup (RegistryConfigs.racesNameEntry);
        }
        catch (RemoteException e)
        { 
            System.out.println("Exception thrown while locating races: " + e.getMessage () + "!");
            System.exit (1);
        }
        catch (NotBoundException e)
        { 
            System.out.println("Races is not registered: " + e.getMessage () + "!");
            System.exit(1);
        }
         
        for (int i = 0; i < Constants.N_OF_SPECTATORS; i++){
            spectators.add(new Spectators(cci, bci, pi, (int) (Math.random() * (Constants.MAX_SPECTATOR_BET - 200)) + 200, i, ri, li));
        }
                
        System.out.println("Number of spectators: " + spectators.size());
        
        for (Spectators spectator : spectators)
            spectator.start();
        
        try { 
            for (Spectators spectator : spectators)
                spectator.join();
        } catch (InterruptedException e) {}
        
        
        System.out.println("Say to log that I have finished!");
        
        try {
            li.finished();
        } catch (RemoteException ex) {
            Logger.getLogger(SpectatorsRun.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Done!");
    }
}