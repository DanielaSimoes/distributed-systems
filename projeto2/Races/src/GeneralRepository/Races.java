package GeneralRepository;

/**
 * This file describes the set of the Races.
 * @author Daniela Simões, 76771
 */

import java.util.HashMap;

import java.util.LinkedList;
import settings.NodeSetts;

/**
 *
 * @author Daniela
 */
public class Races {
    
    private static Races instance = null;
    
    private final HashMap<Integer, Integer> spectatorAmmount;
    
    
    private final HashMap<Integer, Integer> horseJockeyStepSize;
    
    private final LinkedList<Integer> horseJockeySelected;
    private final boolean allInitStatesRegistered = false;
    
    /**
     * Race array.
     */
    public Race[] races;
    
    /**
    *
    * Races Constructor
    */
    public Races(){
        this.spectatorAmmount = new HashMap<>();
        this.horseJockeyStepSize = new HashMap<>();
        this.races = new Race[NodeSetts.N_OF_RACES];
        
        this.horseJockeySelected = new LinkedList<>();
        
        for(int i=0; i<NodeSetts.N_OF_RACES; i++){
            this.races[i] = new Race(i, this.horseJockeySelected);
        }
    }
    
    /**
    *
    * Method to retrieve an instance of Races
     * @return 
    */
    public static Races getInstace(){
        if (instance == null){
            instance = new Races();
        }
        
        return instance;
    }
   
   
    /**
     *
     * @param raceNumber
     * @param spectatorId
     * @param initialBet
     * @param moneyToBet
     * @return
     */
    public synchronized Bet chooseBet(int raceNumber, int spectatorId, int initialBet, int moneyToBet){        
        return this.races[raceNumber].chooseBet(spectatorId, initialBet, moneyToBet);
    }

    /**
    *
    * Method to verify if a given horse was selected to a race.
     * @param horseJockeyID
     * @param horseStepSize
     * @param raceNumber
     * @return 
    */
    public boolean horseHasBeenSelectedToRace(int horseJockeyID, int horseStepSize, int raceNumber){    
        if(horseStepSize==-1){
            return this.races[raceNumber].horseHasBeenSelectedToRace(horseJockeyID);
        }else{
            return this.races[raceNumber].horseHasBeenSelectedToRace(horseJockeyID, horseStepSize);
        }
    }
    
    /**
     *
     * @param id
     * @param stepSize
     */
    public void setHorseJockeyStepSize(int id, int stepSize){
        this.horseJockeyStepSize.put(id, stepSize);
        
        if(this.horseJockeyStepSize.size()==NodeSetts.N_OF_HORSES){
            for (Race race : this.races) {
                race.generateOdds(this.horseJockeyStepSize);
            }
        }
    }
    
    /**
     *
     * @param id
     * @return
     */
    public int getHorseJockeyStepSize(int id){
        return this.horseJockeyStepSize.get(id);
    }
    
    /**
     *
     * @param raceNumber
     * @return
     */
    public synchronized boolean areThereAnyWinners(int raceNumber){
        return this.races[raceNumber].areThereAnyWinners();
    };
    
    /**
     *
     * @param raceNumber
     * @param spectatorId
     * @return
     */
    public synchronized boolean haveIWon(int raceNumber, int spectatorId){
        return this.races[raceNumber].haveIWon(spectatorId);
    };
    
    /**
    *
    * Method to get the winner of the race.
     * @param raceNumber
     * @return 
    */
    public LinkedList<Integer> getWinner(int raceNumber){        
        return races[raceNumber].getWinner();
    }
    
    /**
    *
    * Method to verify if has more races to happen.
     * @return 
    */
    public boolean hasMoreRaces(){
        return !this.races[NodeSetts.N_OF_RACES-1].horsesFinished();
    }
    
    /**
    *
    * Method to allow the HorseJockey to make a move in the race.
    * @param horseId The ID of the HorseJockey.
     * @param raceNumber
    */
    public void makeAMove(int horseId, int raceNumber){        
        this.races[raceNumber].makeAMove(horseId);
    }
    
    /**
     *
     * @param horseId
     * @param raceNumber
     * @return
     */
    public int getHorseIteration(int horseId, int raceNumber){        
        return this.races[raceNumber].getHorseIteration(horseId);
    }
    
    /**
     *
     * @param horseId
     * @param raceNumber
     * @return
     */
    public int getStandingPosition(int horseId, int raceNumber){
        return this.races[raceNumber].getStandingPosition(horseId);
    }
    
    /**
     *
     * @param horseJockeyId
     * @param raceNumber
     * @return
     */
    public boolean nextMovingHorse(int horseJockeyId, int raceNumber){        
        return this.races[raceNumber].nextMovingHorse(horseJockeyId);
    }
    
    /**
     *
     * @param horseId
     * @param raceNumber
     * @return
     */
    public boolean horseFinished(int horseId, int raceNumber){        
        return this.races[raceNumber].horseFinished(horseId);
    }
   
    /**
    *
    * Method to verify if all horses finished the race.
     * @param raceNumber
     * @return 
    */
    public boolean horsesFinished(int raceNumber){     
        return this.races[raceNumber].horsesFinished();
    }
    
    /**
     *
     * @param raceNumber
     * @return
     */
    public int getNRunningHorses(int raceNumber){        
        return this.races[raceNumber].getNRunningHorses();
    }
    
    /**
     *
     * @param raceNumber
     * @return
     */
    public synchronized int getCurrentRaceDistance(int raceNumber){
        return this.races[raceNumber].getCurrentRaceDistance();
    }
    
    /* condition states */
    /* Racing Track */

    /**
     *
     * @param raceNumber
     * @return
     */

    public synchronized boolean getStartTheRace(int raceNumber){        
        return this.races[raceNumber].getStartTheRace();
    }
    
    /**
     *
     * @param startTheRace
     * @param raceNumber
     */
    public synchronized void setStartTheRace(boolean startTheRace, int raceNumber){        
        this.races[raceNumber].setStartTheRace(startTheRace);
    }
    /* Stable */

    /**
     *
     * @param raceNumber
     * @return
     */

    public synchronized int getWakedHorsesToPaddock(int raceNumber){        
        return this.races[raceNumber].getWakedHorsesToPaddock();
    }
    
    /**
     *
     * @param raceNumber
     */
    public synchronized void addWakedHorsesToPaddock(int raceNumber){        
        this.races[raceNumber].addWakedHorsesToPaddock();
    }
    
    /**
     *
     * @param raceNumber
     * @return
     */
    public synchronized boolean getAnnouncedNextRace(int raceNumber){        
        if(raceNumber>=NodeSetts.N_OF_RACES){
            return false;
        }
        
        return this.races[raceNumber].getAnnuncedNextRace();
    }
    
    /**
     *
     * @param annuncedNextRace
     * @param raceNumber
     */
    public synchronized void setAnnouncedNextRace(boolean annuncedNextRace, int raceNumber){        
        this.races[raceNumber].setAnnuncedNextRace(annuncedNextRace);
    }
    /* Paddock */

    /**
     *
     * @param raceNumber
     * @return
     */

    public boolean allSpectatorsArrivedAtPaddock(int raceNumber){        
        return this.races[raceNumber].allSpectatorsArrivedAtPaddock();
    }
    
    /**
     *
     * @param raceNumber
     */
    public void addNSpectatorsArrivedAtPaddock(int raceNumber){        
        this.races[raceNumber].addNSpectatorsArrivedAtPaddock();
    }
    
    /**
     *
     * @param raceNumber
     * @return
     */
    public boolean allHorseJockeyLeftThePadock(int raceNumber){        
        return this.races[raceNumber].allHorseJockeyLeftThePadock();
    }
    
    /**
     *
     * @param raceNumber
     */
    public void addNHorseJockeyLeftThePadock(int raceNumber){        
        this.races[raceNumber].addNHorseJockeyLeftThePadock();
    }
    
    /* Control Centre */

    /**
     *
     * @param set
     * @param raceNumber
     */

    public synchronized void setReportResults(boolean set, int raceNumber){        
        this.races[raceNumber].setReportResults(set);
    }
    
    /**
     *
     * @param raceNumber
     * @return
     */
    public synchronized boolean getReportResults(int raceNumber){        
        return this.races[raceNumber].getReportResults();
    }
    
    /**
     *
     * @param set
     * @param raceNumber
     */
    public synchronized void setProceedToPaddock(boolean set, int raceNumber){        
        this.races[raceNumber].setProceedToPaddock(set);
    }
    
    /**
     *
     * @param raceNumber
     * @return
     */
    public synchronized boolean getProceedToPaddock(int raceNumber){        
        return this.races[raceNumber].getProceedToPaddock();
    }
    
    /**
     *
     * @param raceNumber
     * @return
     */
    public synchronized boolean allNHorsesInPaddock(int raceNumber){        
        return this.races[raceNumber].allNHorsesInPaddock();
    }
    
    /**
     *
     * @param raceNumber
     */
    public synchronized void addNHorsesInPaddock(int raceNumber){        
        this.races[raceNumber].addNHorsesInPaddock();
    }
    
    /* Betting Centre */

    /**
     *
     * @param raceNumber
     * @return
     */

    public Integer waitAddedBet(int raceNumber){        
        return this.races[raceNumber].waitAddedBet();
    }
    
    /**
     *
     * @param raceNumber
     * @return
     */
    public boolean allSpectatorsBettsAceppted(int raceNumber){        
        return this.races[raceNumber].allSpectatorsBettsAceppted();
    }
    
    /**
     *
     * @param bet
     * @param raceNumber
     * @param spectatorId
     */
    public void addBetOfSpectator(Bet bet, int raceNumber, int spectatorId){        
        this.races[raceNumber].addBetOfSpectator(bet, spectatorId);
    }
    
    /**
     *
     * @param raceNumber
     * @return
     */
    public boolean allSpectatorsBetted(int raceNumber){        
        return this.races[raceNumber].allSpectatorsBetted();
    }
    
    /**
     *
     * @param raceNumber
     * @param spectatorId
     */
    public void waitAcceptedTheBet(int raceNumber, int spectatorId){
        this.races[raceNumber].waitAcceptedTheBet(spectatorId);
    }
   
    /**
     *
     * @param i
     * @param raceNumber
     */
    public void acceptBet(int i, int raceNumber){
        this.races[raceNumber].acceptBet(i);
    }
    
    /**
     *
     * @param raceNumber
     * @return
     */
    public Integer poolWaitingToBePaidSpectators(int raceNumber){
        return this.races[raceNumber].poolWaitingToBePaidSpectators();
    }
    
    /**
     *
     * @param i
     * @param raceNumber
     */
    public void addWaitingToBePaidSpectator(int i, int raceNumber){
        this.races[raceNumber].addWaitingToBePaidSpectator(i);
    }
    
    /**
     *
     * @param raceNumber
     * @return
     */
    public boolean allSpectatorsPaid(int raceNumber){
        return this.races[raceNumber].allSpectatorsPaid();
    }
    
    /**
     *
     * @param i
     * @param raceNumber
     * @return
     */
    public synchronized Integer getPaidSpectators(int i, int raceNumber){        
        return this.races[raceNumber].getPaidSpectators(i);
    }
    
    /**
     *
     * @param i
     * @param set
     * @param raceNumber
     */
    public synchronized void setPaidSpectators(int i, boolean set, int raceNumber){        
        this.races[raceNumber].setPaidSpectators(i, set);
    }
    
    /**
     *
     * @param spectatorId
     * @param raceNumber
     * @return
     */
    public Bet getSpectatorBet(int spectatorId, int raceNumber){        
        return this.races[raceNumber].getSpectatorBet(spectatorId);
    }
    
    /**
     *
     * @param horseId
     * @param raceNumber
     * @return
     */
    public double getHorseOdd(int horseId, int raceNumber){        
        return this.races[raceNumber].getHorseOdd(horseId);
    }
    
    /**
     *
     * @param horseId
     * @param raceNumber
     * @return
     */
    public int getHorsePosition(int horseId, int raceNumber){        
        return this.races[raceNumber].getHorsePosition(horseId);
    }
    /* end condition states */
}
