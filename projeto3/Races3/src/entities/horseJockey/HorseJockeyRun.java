package entities.horseJockey;

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
public class HorseJockeyRun {
    
    private static int N_HORSEJOKEY;
    
    public static void main(String [] args) {   
        N_HORSEJOKEY = Constants.N_OF_HORSES;
        
        ArrayList<HorseJockey> horses = new ArrayList<>(N_HORSEJOKEY);
        
        // nome do sistema onde está localizado o serviço de registos RMI
        String rmiRegHostName;
        // port de escuta do serviço
        int rmiRegPortNumb;

        RegistryConfigs rc = new RegistryConfigs("config.ini");
        rmiRegHostName = rc.registryHost();
        rmiRegPortNumb = rc.registryPort();
        
        interfaces.IStable si = null;
        interfaces.IControlCentre cci = null;
        interfaces.IRacingTrack rti = null;
        interfaces.IPaddock pi = null;
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
            si = (interfaces.IStable) registry.lookup (RegistryConfigs.stableNameEntry);
        }
        catch (RemoteException e)
        { 
            System.out.println("Exception thrown while locating stable: " + e.getMessage () + "!");
            System.exit (1);
        }
        catch (NotBoundException e)
        { 
            System.out.println("Stable is not registered: " + e.getMessage () + "!");
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
            rti = (interfaces.IRacingTrack) registry.lookup (RegistryConfigs.racingTrackNameEntry);
        }
        catch (RemoteException e)
        { 
            System.out.println("Exception thrown while locating racing track: " + e.getMessage () + "!");
            System.exit (1);
        }
        catch (NotBoundException e)
        { 
            System.out.println("Racing track is not registered: " + e.getMessage () + "!");
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
         
        for (int i = 0; i < N_HORSEJOKEY; i++){
            horses.add(new HorseJockey(si, cci, pi, rti, (int) (Math.random() * (Constants.HORSE_MAX_STEP_SIZE - 1)) + 1, i, ri, li));
        }
                
        System.out.println("Number of horses: " + horses.size());
        
        for (HorseJockey horse : horses)
            horse.start();
        
        try { 
            for (HorseJockey horse : horses)
                horse.join();
        } catch (InterruptedException e) {}
        
        
        System.out.println("Say to log that I have finished!");
        
        try {
            li.finished();
        } catch (RemoteException ex) {
            Logger.getLogger(HorseJockeyRun.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Done!");
    }
}