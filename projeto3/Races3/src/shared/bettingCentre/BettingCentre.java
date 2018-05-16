package shared.bettingCentre;

import generalRepository.log.Log;
import generalRepository.races.Bet;
import interfaces.IBettingCentre;
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
 * This file contains the shared memory region Betting Centre.
 * @author Daniela Simes, 76771
 */
public class BettingCentre implements IBettingCentre {
    
    private final interfaces.IRaces races;
    
    public BettingCentre(interfaces.IRaces races){
        this.races = races;
    }
    
    /**
    *
    * Method to the broker accept the bets of the spectators.
     * @param raceNumber
    */
    @Override
    public void acceptTheBets(int raceNumber) throws RemoteException{
        while(true){
            Integer spectatorId = this.races.waitAddedBet(raceNumber);

            if(spectatorId!=null){
                this.races.acceptBet(spectatorId, raceNumber);
            }
        
            if(this.races.allSpectatorsBetted(raceNumber) && this.races.allSpectatorsBettsAceppted(raceNumber)){
                break;
            }
        }
    };
    
    /**
    *
    * Method to get the broker to honour the winners of the bets.
     * @param raceNumber
    */
    @Override
    public synchronized void honourTheBets(int raceNumber) throws RemoteException{
        Integer spectatorId;
        
        while(true){
            spectatorId = this.races.poolWaitingToBePaidSpectators(raceNumber);
            
            if(this.races.allSpectatorsPaid(raceNumber) && spectatorId == null){
                break;
            }else if(this.races.allSpectatorsPaid(raceNumber) && spectatorId != null){
                this.races.setPaidSpectators(spectatorId, raceNumber);
                notifyAll();
            }else if(!this.races.allSpectatorsPaid(raceNumber) && spectatorId != null){
                this.races.setPaidSpectators(spectatorId, raceNumber);
                notifyAll();
            }else if(!this.races.allSpectatorsPaid(raceNumber) && spectatorId == null){
                try{
                    wait();
                }catch (InterruptedException ex){
                    // do something in the future
                }
            }
        }
    };
    
    /**
    *
    * Method to verify if are spectators who have won the bets.
     * @param raceNumber
     * @return 
    */
    @Override
    public synchronized boolean areThereAnyWinners(int raceNumber) throws RemoteException{
        return this.races.areThereAnyWinners(raceNumber);
    };
    
    /**
    *
    * Method to allow the spectator to place a bet.
     * @param raceNumber
     * @param spectatorId
     * @param initialBet
     * @param moneyToBet
     * @return 
    */
    @Override
    public Bet placeABet(int raceNumber, int spectatorId, int initialBet, int moneyToBet) throws RemoteException{
        Bet spectator_bet = this.races.chooseBet(raceNumber, spectatorId, initialBet, moneyToBet);
        this.races.addBetOfSpectator(spectator_bet, raceNumber, spectatorId);

        this.races.waitAcceptedTheBet(raceNumber, spectatorId);
        return spectator_bet;
    };
    
    /**
    *
    * Method to the spectator go collect the gains and increase the bank.
     * @param raceNumber
     * @param spectatorId
     * @return 
    */
    @Override
    public synchronized int goCollectTheGains(int raceNumber, int spectatorId) throws RemoteException{
        this.races.addWaitingToBePaidSpectator(spectatorId, raceNumber);
        
        // wake broker because spectator is waiting to be Paid
        // and broker must pay the honours
        notifyAll();
        
        int gains = this.races.getPaidSpectators(spectatorId, raceNumber);
        
        while(gains == -1){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
            
            gains = this.races.getPaidSpectators(spectatorId, raceNumber);
        }
        
        return gains;
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
            Logger.getLogger(BettingCentre.class.getName()).log(Level.SEVERE, null, ex);
        }

        String nameEntryBase = RegistryConfigs.registerHandler;
        String nameEntryObject = RegistryConfigs.bettingCentreNameEntry;

        try {
            reg = (RegisterInterface) registry.lookup(nameEntryBase);
        } catch (RemoteException e) {
            System.out.println("RegisterRemoteObject lookup exception: " + e.getMessage());
            Logger.getLogger(BettingCentre.class.getName()).log(Level.SEVERE, null, e);
        } catch (NotBoundException e) {
            System.out.println("RegisterRemoteObject not bound exception: " + e.getMessage());
            Logger.getLogger(BettingCentre.class.getName()).log(Level.SEVERE, null, e);
        }
        
        try {
            // Unregister ourself
            reg.unbind(nameEntryObject);
        } catch (RemoteException e) {
            System.out.println("BettingCentre registration exception: " + e.getMessage());
            Logger.getLogger(BettingCentre.class.getName()).log(Level.SEVERE, null, e);
        } catch (NotBoundException e) {
            System.out.println("BettingCentre not bound exception: " + e.getMessage());
            Logger.getLogger(BettingCentre.class.getName()).log(Level.SEVERE, null, e);
        }

        try {
            // Unexport; this will also remove us from the RMI runtime
            UnicastRemoteObject.unexportObject(this, true);
        } catch (NoSuchObjectException ex) {
            Logger.getLogger(BettingCentre.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Betting Centre closed.");
    }
}
