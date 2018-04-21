package GeneralRepository;

/**
 * This file describes the set of the Races.
 * @author Daniela Simões, 76771
 */

import java.util.HashMap;

import entities.BrokerState;
import entities.HorseJockeyState;
import entities.SpectatorsState;
import entities.HorseJockey;
import entities.IEntity;
import java.util.LinkedList;

/**
 *
 * @author Daniela
 */
public class Races {
    
    /**
     * Number of races.
     */
    public static final int N_OF_RACES = 2;

    /**
     * Number of horses.
     */
    public static final int N_OF_HORSES = 8;
    
    /**
     * Number of horses.
     */
    public static final int N_OF_HORSES_TO_RUN = 4;

    /**
     * Number os spectators.
     */
    public static final int N_OF_SPECTATORS = 4;

    /**
     * Size of racing track.
     */
    public static final int SIZE_OF_RACING_TRACK = 20;

    /**
     * Horse maxium step size.
     */
    public static final int HORSE_MAX_STEP_SIZE = 8;

    /**
     * Maximum ammount each spectator can bet.
     */
    public static final int MAX_SPECTATOR_BET = 2000;
        
    private static Races instance = null;
    
    private final HashMap<Integer, Integer> spectatorAmmount;
    
    
    private final HashMap<Integer, Integer> horseJockeyStepSize;
    
    private final LinkedList<Integer> horseJockeySelected;
    private boolean allInitStatesRegistered = false;
    
    /**
     * Race array.
     */
    public Race[] races;
    
    /**
    *
    * Races Constructor
    */
    private Races(){
        this.spectatorAmmount = new HashMap<>();
        this.horseJockeyStepSize = new HashMap<>();
        this.races = new Race[N_OF_RACES];
        
        this.horseJockeySelected = new LinkedList<>();
        
        for(int i=0; i<N_OF_RACES; i++){
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
     * @return
     */
    public synchronized Bet chooseBet(int raceNumber){        
        return this.races[raceNumber].chooseBet();
    }

    /**
    *
    * Method to verify if a given horse was selected to a race.
    * @param horseJockey The HorseJockey object.
     * @return 
    */
    public boolean horseHasBeenSelectedToRace(int horseJockeyID, int horseStepSize, int raceNumber){        
        return this.races[raceNumber].horseHasBeenSelectedToRace(horseJockeyID, horseStepSize);
    }
    
    
    public boolean horseHasBeenSelectedToRace(int horseJockey, int raceNumber){        
        return this.races[raceNumber].horseHasBeenSelectedToRace(horseJockey);
    }
    
    /**
     *
     * @param id
     * @param stepSize
     */
    public void setHorseJockeyStepSize(int id, int stepSize){
        this.horseJockeyStepSize.put(id, stepSize);
        
        if(this.horseJockeyStepSize.size()==Races.N_OF_HORSES){
            for(int i=0; i<this.races.length; i++){
                this.races[i].generateOdds(this.horseJockeyStepSize);
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
     * @return
     */
    public synchronized boolean areThereAnyWinners(int raceNumber){
        return this.races[raceNumber].areThereAnyWinners();
    };
    
    /**
     *
     * @return
     */
    public synchronized boolean haveIWon(int raceNumber){
        return this.races[raceNumber].haveIWon();
    };
    
    /**
    *
    * Method to get the winner of the race.
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
        return !this.races[Races.N_OF_RACES-1].horsesFinished();
    }
    
    /**
    *
    * Method to allow the HorseJockey to make a move in the race.
    * @param horseId The ID of the HorseJockey.
    */
    public void makeAMove(int horseId, int raceNumber){        
        this.races[raceNumber].makeAMove(horseId);
    }
    
    /**
     *
     * @param horseId
     * @return
     */
    public int getHorseIteration(int horseId, int raceNumber){        
        return this.races[raceNumber].getHorseIteration(horseId);
    }
    
    /**
     *
     * @param horseId
     * @return
     */
    public int getStandingPosition(int horseId, int raceNumber){
        return this.races[raceNumber].getStandingPosition(horseId);
    }
    
    /**
     *
     * @param horseJockeyId
     * @return
     */
    public boolean nextMovingHorse(int horseJockeyId, int raceNumber){        
        return this.races[raceNumber].nextMovingHorse(horseJockeyId);
    }
    
    /**
     *
     * @param horseId
     * @return
     */
    public boolean horseFinished(int horseId, int raceNumber){        
        return this.races[raceNumber].horseFinished(horseId);
    }
    
    /**
     *
     * @return
     */
    public boolean horsesFinished(int raceNumber){        
        return this.races[raceNumber].horsesFinished();
    }
    
    /**
     *
     * @return
     */
    public int getNRunningHorses(int raceNumber){        
        return this.races[raceNumber].getNRunningHorses();
    }
    
    /**
     *
     * @return
     */
    public synchronized int getCurrentRaceDistance(int raceNumber){
        return this.races[raceNumber].getCurrentRaceDistance();
    }
    
    /* condition states */
    /* Racing Track */

    /**
     *
     * @return
     */

    public synchronized boolean getStartTheRace(int raceNumber){        
        return this.races[raceNumber].getStartTheRace();
    }
    
    /**
     *
     * @param startTheRace
     */
    public synchronized void setStartTheRace(boolean startTheRace, int raceNumber){        
        this.races[raceNumber].setStartTheRace(startTheRace);
    }
    /* Stable */

    /**
     *
     * @return
     */

    public synchronized int getWakedHorsesToPaddock(int raceNumber){        
        return this.races[raceNumber].getWakedHorsesToPaddock();
    }
    
    /**
     *
     */
    public synchronized void addWakedHorsesToPaddock(int raceNumber){        
        this.races[raceNumber].addWakedHorsesToPaddock();
    }
    
    /**
     *
     * @return
     */
    public synchronized boolean getAnnouncedNextRace(int raceNumber){        
        if(raceNumber>=N_OF_RACES){
            return false;
        }
        
        return this.races[raceNumber].getAnnuncedNextRace();
    }
    
    /**
     *
     * @param annuncedNextRace
     */
    public synchronized void setAnnouncedNextRace(boolean annuncedNextRace, int raceNumber){        
        this.races[raceNumber].setAnnuncedNextRace(annuncedNextRace);
    }
    /* Paddock */

    /**
     *
     * @return
     */

    public boolean allSpectatorsArrivedAtPaddock(int raceNumber){        
        return this.races[raceNumber].allSpectatorsArrivedAtPaddock();
    }
    
    /**
     *
     */
    public void addNSpectatorsArrivedAtPaddock(int raceNumber){        
        this.races[raceNumber].addNSpectatorsArrivedAtPaddock();
    }
    
    /**
     *
     * @return
     */
    public boolean allHorseJockeyLeftThePadock(int raceNumber){        
        return this.races[raceNumber].allHorseJockeyLeftThePadock();
    }
    
    /**
     *
     */
    public void addNHorseJockeyLeftThePadock(int raceNumber){        
        this.races[raceNumber].addNHorseJockeyLeftThePadock();
    }
    
    /* Control Centre */

    /**
     *
     * @param set
     */

    public synchronized void setReportResults(boolean set, int raceNumber){        
        this.races[raceNumber].setReportResults(set);
    }
    
    /**
     *
     * @return
     */
    public synchronized boolean getReportResults(int raceNumber){        
        return this.races[raceNumber].getReportResults();
    }
    
    /**
     *
     * @param set
     */
    public synchronized void setProceedToPaddock(boolean set, int raceNumber){        
        this.races[raceNumber].setProceedToPaddock(set);
    }
    
    /**
     *
     * @return
     */
    public synchronized boolean getProceedToPaddock(int raceNumber){        
        return this.races[raceNumber].getProceedToPaddock();
    }
    
    /**
     *
     * @return
     */
    public synchronized boolean allNHorsesInPaddock(int raceNumber){        
        return this.races[raceNumber].allNHorsesInPaddock();
    }
    
    /**
     *
     */
    public synchronized void addNHorsesInPaddock(int raceNumber){        
        this.races[raceNumber].addNHorsesInPaddock();
    }
    
    /* Betting Centre */

    /**
     *
     * @return
     */

    public Integer waitAddedBet(int raceNumber){        
        return this.races[raceNumber].waitAddedBet();
    }
    
    /**
     *
     * @return
     */
    public boolean allSpectatorsBettsAceppted(int raceNumber){        
        return this.races[raceNumber].allSpectatorsBettsAceppted();
    }
    
    /**
     *
     * @param bet
     */
    public void addBetOfSpectator(Bet bet, int raceNumber){        
        this.races[raceNumber].addBetOfSpectator(bet);
    }
    
    /**
     *
     * @return
     */
    public boolean allSpectatorsBetted(int raceNumber){        
        return this.races[raceNumber].allSpectatorsBetted();
    }
    
    /**
     *
     */
    public void waitAcceptedTheBet(int raceNumber){
        this.races[raceNumber].waitAcceptedTheBet();
    }
   
    /**
     *
     * @param i
     */
    public void acceptBet(int i, int raceNumber){
        this.races[raceNumber].acceptBet(i);
    }
    
    /**
     *
     * @return
     */
    public Integer poolWaitingToBePaidSpectators(int raceNumber){
        return this.races[raceNumber].poolWaitingToBePaidSpectators();
    }
    
    /**
     *
     * @param i
     */
    public void addWaitingToBePaidSpectator(int i, int raceNumber){
        this.races[raceNumber].addWaitingToBePaidSpectator(i);
    }
    
    /**
     *
     * @return
     */
    public boolean allSpectatorsPaid(int raceNumber){
        return this.races[raceNumber].allSpectatorsPaid();
    }
    
    /**
     *
     * @param i
     * @return
     */
    public synchronized boolean getPaidSpectators(int i, int raceNumber){        
        return this.races[raceNumber].getPaidSpectators(i);
    }
    
    /**
     *
     * @param i
     * @param set
     */
    public synchronized void setPaidSpectators(int i, boolean set, int raceNumber){        
        this.races[raceNumber].setPaidSpectators(i, set);
    }
    
    /**
     *
     * @param spectatorId
     * @return
     */
    public Bet getSpectatorBet(int spectatorId, int raceNumber){        
        return this.races[raceNumber].getSpectatorBet(spectatorId);
    }
    
    /**
     *
     * @param horseId
     * @return
     */
    public double getHorseOdd(int horseId, int raceNumber){        
        return this.races[raceNumber].getHorseOdd(horseId);
    }
    
    /**
     *
     * @param horseId
     * @return
     */
    public int getHorsePosition(int horseId, int raceNumber){        
        return this.races[raceNumber].getHorsePosition(horseId);
    }
    /* end condition states */
}
