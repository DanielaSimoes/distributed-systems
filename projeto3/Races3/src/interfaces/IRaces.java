package interfaces;

import generalRepository.races.Bet;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;

/**
 * This file contains the races interface.
 * @author Daniela Simes, 76771
 */
public interface IRaces extends Remote{
    public Bet chooseBet(int raceNumber, int spectatorId, int initialBet, int moneyToBet)  throws RemoteException;
    public boolean horseHasBeenSelectedToRace(int horseJockeyID, int horseStepSize, int raceNumber)  throws RemoteException;
    public void setHorseJockeyStepSize(int id, int stepSize)  throws RemoteException;
    public int getHorseJockeyStepSize(int id)  throws RemoteException;
    public boolean areThereAnyWinners(int raceNumber)  throws RemoteException;
    public boolean haveIWon(int raceNumber, int spectatorId)  throws RemoteException;
    public LinkedList<Integer> getWinner(int raceNumber)  throws RemoteException;
    public boolean hasMoreRaces()  throws RemoteException;
    public void makeAMove(int horseId, int raceNumber)  throws RemoteException;
    public int getHorseIteration(int horseId, int raceNumber)  throws RemoteException;
    public int getStandingPosition(int horseId, int raceNumber)  throws RemoteException;
    public boolean nextMovingHorse(int horseJockeyId, int raceNumber)  throws RemoteException;
    public boolean horseFinished(int horseId, int raceNumber)  throws RemoteException;
    public boolean horsesFinished(int raceNumber)  throws RemoteException;
    public int getNRunningHorses(int raceNumber)  throws RemoteException;
    public int getCurrentRaceDistance(int raceNumber)  throws RemoteException;
    public boolean getStartTheRace(int raceNumber)  throws RemoteException;
    public void setStartTheRace(boolean startTheRace, int raceNumber)  throws RemoteException;
    public int getWakedHorsesToPaddock(int raceNumber)  throws RemoteException;
    public void addWakedHorsesToPaddock(int raceNumber)  throws RemoteException;
    public boolean getAnnouncedNextRace(int raceNumber)  throws RemoteException;
    public void setAnnouncedNextRace(boolean annuncedNextRace, int raceNumber)  throws RemoteException;
    public boolean allSpectatorsArrivedAtPaddock(int raceNumber)  throws RemoteException;
    public void addNSpectatorsArrivedAtPaddock(int raceNumber)  throws RemoteException;
    public boolean allHorseJockeyLeftThePadock(int raceNumber)  throws RemoteException;
    public void addNHorseJockeyLeftThePadock(int raceNumber)  throws RemoteException;
    public void setReportResults(boolean set, int raceNumber)  throws RemoteException;
    public boolean getReportResults(int raceNumber)  throws RemoteException;
    public void setProceedToPaddock(boolean set, int raceNumber)  throws RemoteException;
    public boolean getProceedToPaddock(int raceNumber)  throws RemoteException;
    public boolean allNHorsesInPaddock(int raceNumber)  throws RemoteException;
    public void addNHorsesInPaddock(int raceNumber)  throws RemoteException;
    public Integer waitAddedBet(int raceNumber)  throws RemoteException;
    public boolean allSpectatorsBettsAceppted(int raceNumber)  throws RemoteException;
    public void addBetOfSpectator(Bet bet, int raceNumber, int spectatorId)  throws RemoteException;
    public boolean allSpectatorsBetted(int raceNumber)  throws RemoteException;
    public void waitAcceptedTheBet(int raceNumber, int spectatorId)  throws RemoteException;
    public void acceptBet(int i, int raceNumber)  throws RemoteException;
    public Integer poolWaitingToBePaidSpectators(int raceNumber)  throws RemoteException;
    public void addWaitingToBePaidSpectator(int i, int raceNumber)  throws RemoteException;
    public boolean allSpectatorsPaid(int raceNumber)  throws RemoteException;
    public int getPaidSpectators(int i, int raceNumber)  throws RemoteException;
    public void setPaidSpectators(int i, int raceNumber)  throws RemoteException;
    public Bet getSpectatorBet(int spectatorId, int raceNumber)  throws RemoteException;
    public double getHorseOdd(int horseId, int raceNumber)  throws RemoteException;
    public int getHorsePosition(int horseId, int raceNumber)  throws RemoteException;
    public int selectedHorseId(int raceId, int horseRaceId)  throws RemoteException;
    public void signalShutdown() throws RemoteException;
}
