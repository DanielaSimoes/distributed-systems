package GeneralRepository;

import java.util.LinkedList;

/**
 * This file contains the races interface.
 * @author Daniela Simões, 76771
 */
public interface IRaces {
    public Bet chooseBet(int raceNumber, int spectatorId, int initialBet, int moneyToBet);
    public boolean horseHasBeenSelectedToRace(int horseJockeyID, int horseStepSize, int raceNumber);
    public void setHorseJockeyStepSize(int id, int stepSize);
    public int getHorseJockeyStepSize(int id);
    public boolean areThereAnyWinners(int raceNumber);
    public boolean haveIWon(int raceNumber, int spectatorId);
    public LinkedList<Integer> getWinner(int raceNumber);
    public boolean hasMoreRaces();
    public void makeAMove(int horseId, int raceNumber);
    public int getHorseIteration(int horseId, int raceNumber);
    public int getStandingPosition(int horseId, int raceNumber);
    public boolean nextMovingHorse(int horseJockeyId, int raceNumber);
    public boolean horseFinished(int horseId, int raceNumber);
    public boolean horsesFinished(int raceNumber);
    public int getNRunningHorses(int raceNumber);
    public int getCurrentRaceDistance(int raceNumber);
    public boolean getStartTheRace(int raceNumber);
    public void setStartTheRace(boolean startTheRace, int raceNumber);
    public int getWakedHorsesToPaddock(int raceNumber);
    public void addWakedHorsesToPaddock(int raceNumber);
    public boolean getAnnouncedNextRace(int raceNumber);
    public void setAnnouncedNextRace(boolean annuncedNextRace, int raceNumber);
    public boolean allSpectatorsArrivedAtPaddock(int raceNumber);
    public void addNSpectatorsArrivedAtPaddock(int raceNumber);
    public boolean allHorseJockeyLeftThePadock(int raceNumber);
    public void addNHorseJockeyLeftThePadock(int raceNumber);
    public void setReportResults(boolean set, int raceNumber);
    public boolean getReportResults(int raceNumber);
    public void setProceedToPaddock(boolean set, int raceNumber);
    public boolean getProceedToPaddock(int raceNumber);
    public boolean allNHorsesInPaddock(int raceNumber);
    public void addNHorsesInPaddock(int raceNumber);
    public Integer waitAddedBet(int raceNumber);
    public boolean allSpectatorsBettsAceppted(int raceNumber);
    public void addBetOfSpectator(Bet bet, int raceNumber, int spectatorId);
    public boolean allSpectatorsBetted(int raceNumber);
    public void waitAcceptedTheBet(int raceNumber, int spectatorId);
    public void acceptBet(int i, int raceNumber);
    public Integer poolWaitingToBePaidSpectators(int raceNumber);
    public void addWaitingToBePaidSpectator(int i, int raceNumber);
    public boolean allSpectatorsPaid(int raceNumber);
    public int getPaidSpectators(int i, int raceNumber);
    public void setPaidSpectators(int i, int raceNumber);
    public Bet getSpectatorBet(int spectatorId, int raceNumber);
    public double getHorseOdd(int horseId, int raceNumber);
    public int getHorsePosition(int horseId, int raceNumber);
    public int selectedHorseId(int raceId, int horseRaceId);
}
