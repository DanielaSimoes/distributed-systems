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
public class Races implements IRaces{
    
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
    @Override
    public Bet chooseBet(int raceNumber, int spectatorId, int initialBet, int moneyToBet){        
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
    @Override
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
    @Override
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
    @Override
    public int getHorseJockeyStepSize(int id){
        return this.horseJockeyStepSize.get(id);
    }
    
    /**
     *
     * @param raceNumber
     * @return
     */
    @Override
    public boolean areThereAnyWinners(int raceNumber){
        return this.races[raceNumber].areThereAnyWinners();
    };
    
    /**
     *
     * @param raceNumber
     * @param spectatorId
     * @return
     */
    @Override
    public boolean haveIWon(int raceNumber, int spectatorId){
        return this.races[raceNumber].haveIWon(spectatorId);
    };
    
    /**
    *
    * Method to get the winner of the race.
     * @param raceNumber
     * @return 
    */
    @Override
    public LinkedList<Integer> getWinner(int raceNumber){        
        return races[raceNumber].getWinner();
    }
    
    /**
    *
    * Method to verify if has more races to happen.
     * @return 
    */
    @Override
    public boolean hasMoreRaces(){
        return !this.races[NodeSetts.N_OF_RACES-1].horsesFinished();
    }
    
    /**
    *
    * Method to allow the HorseJockey to make a move in the race.
    * @param horseId The ID of the HorseJockey.
     * @param raceNumber
    */
    @Override
    public void makeAMove(int horseId, int raceNumber){        
        this.races[raceNumber].makeAMove(horseId);
    }
    
    /**
     *
     * @param horseId
     * @param raceNumber
     * @return
     */
    @Override
    public int getHorseIteration(int horseId, int raceNumber){        
        return this.races[raceNumber].getHorseIteration(horseId);
    }
    
    /**
     *
     * @param horseId
     * @param raceNumber
     * @return
     */
    @Override
    public int getStandingPosition(int horseId, int raceNumber){
        return this.races[raceNumber].getStandingPosition(horseId);
    }
    
    /**
     *
     * @param horseJockeyId
     * @param raceNumber
     * @return
     */
    @Override
    public boolean nextMovingHorse(int horseJockeyId, int raceNumber){        
        return this.races[raceNumber].nextMovingHorse(horseJockeyId);
    }
    
    /**
     *
     * @param horseId
     * @param raceNumber
     * @return
     */
    @Override
    public boolean horseFinished(int horseId, int raceNumber){        
        return this.races[raceNumber].horseFinished(horseId);
    }
   
    /**
    *
    * Method to verify if all horses finished the race.
     * @param raceNumber
     * @return 
    */
    @Override
    public boolean horsesFinished(int raceNumber){     
        return this.races[raceNumber].horsesFinished();
    }
    
    /**
     *
     * @param raceNumber
     * @return
     */
    @Override
    public int getNRunningHorses(int raceNumber){        
        return this.races[raceNumber].getNRunningHorses();
    }
    
    /**
     *
     * @param raceNumber
     * @return
     */
    @Override
    public int getCurrentRaceDistance(int raceNumber){
        return this.races[raceNumber].getCurrentRaceDistance();
    }
    
    /* condition states */
    /* Racing Track */

    /**
     *
     * @param raceNumber
     * @return
     */

    @Override
    public boolean getStartTheRace(int raceNumber){        
        return this.races[raceNumber].getStartTheRace();
    }
    
    /**
     *
     * @param startTheRace
     * @param raceNumber
     */
    @Override
    public void setStartTheRace(boolean startTheRace, int raceNumber){        
        this.races[raceNumber].setStartTheRace(startTheRace);
    }
    /* Stable */

    /**
     *
     * @param raceNumber
     * @return
     */

    @Override
    public int getWakedHorsesToPaddock(int raceNumber){        
        return this.races[raceNumber].getWakedHorsesToPaddock();
    }
    
    /**
     *
     * @param raceNumber
     */
    @Override
    public void addWakedHorsesToPaddock(int raceNumber){        
        this.races[raceNumber].addWakedHorsesToPaddock();
    }
    
    /**
     *
     * @param raceNumber
     * @return
     */
    @Override
    public boolean getAnnouncedNextRace(int raceNumber){        
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
    @Override
    public void setAnnouncedNextRace(boolean annuncedNextRace, int raceNumber){        
        this.races[raceNumber].setAnnuncedNextRace(annuncedNextRace);
    }
    /* Paddock */

    /**
     *
     * @param raceNumber
     * @return
     */
    @Override
    public boolean allSpectatorsArrivedAtPaddock(int raceNumber){        
        return this.races[raceNumber].allSpectatorsArrivedAtPaddock();
    }
    
    /**
     *
     * @param raceNumber
     */
    @Override
    public void addNSpectatorsArrivedAtPaddock(int raceNumber){        
        this.races[raceNumber].addNSpectatorsArrivedAtPaddock();
    }
    
    /**
     *
     * @param raceNumber
     * @return
     */
    @Override
    public boolean allHorseJockeyLeftThePadock(int raceNumber){        
        return this.races[raceNumber].allHorseJockeyLeftThePadock();
    }
    
    /**
     *
     * @param raceNumber
     */
    @Override
    public void addNHorseJockeyLeftThePadock(int raceNumber){        
        this.races[raceNumber].addNHorseJockeyLeftThePadock();
    }
    
    /* Control Centre */

    /**
     *
     * @param set
     * @param raceNumber
     */

    @Override
    public void setReportResults(boolean set, int raceNumber){        
        this.races[raceNumber].setReportResults(set);
    }
    
    /**
     *
     * @param raceNumber
     * @return
     */
    @Override
    public boolean getReportResults(int raceNumber){        
        return this.races[raceNumber].getReportResults();
    }
    
    /**
     *
     * @param set
     * @param raceNumber
     */
    @Override
    public void setProceedToPaddock(boolean set, int raceNumber){        
        this.races[raceNumber].setProceedToPaddock(set);
    }
    
    /**
     *
     * @param raceNumber
     * @return
     */
    @Override
    public boolean getProceedToPaddock(int raceNumber){        
        return this.races[raceNumber].getProceedToPaddock();
    }
    
    /**
     *
     * @param raceNumber
     * @return
     */
    @Override
    public boolean allNHorsesInPaddock(int raceNumber){        
        return this.races[raceNumber].allNHorsesInPaddock();
    }
    
    /**
     *
     * @param raceNumber
     */
    @Override
    public void addNHorsesInPaddock(int raceNumber){        
        this.races[raceNumber].addNHorsesInPaddock();
    }
    
    /* Betting Centre */

    /**
     *
     * @param raceNumber
     * @return
     */
    @Override
    public Integer waitAddedBet(int raceNumber){        
        return this.races[raceNumber].waitAddedBet();
    }
    
    /**
     *
     * @param raceNumber
     * @return
     */
    @Override
    public boolean allSpectatorsBettsAceppted(int raceNumber){        
        return this.races[raceNumber].allSpectatorsBettsAceppted();
    }
    
    /**
     *
     * @param bet
     * @param raceNumber
     * @param spectatorId
     */
    @Override
    public void addBetOfSpectator(Bet bet, int raceNumber, int spectatorId){        
        this.races[raceNumber].addBetOfSpectator(bet, spectatorId);
    }
    
    /**
     *
     * @param raceNumber
     * @return
     */
    @Override
    public boolean allSpectatorsBetted(int raceNumber){        
        return this.races[raceNumber].allSpectatorsBetted();
    }
    
    /**
     *
     * @param raceNumber
     * @param spectatorId
     */
    @Override
    public void waitAcceptedTheBet(int raceNumber, int spectatorId){
        this.races[raceNumber].waitAcceptedTheBet(spectatorId);
    }
   
    /**
     *
     * @param i
     * @param raceNumber
     */
    @Override
    public void acceptBet(int i, int raceNumber){
        this.races[raceNumber].acceptBet(i);
    }
    
    /**
     *
     * @param raceNumber
     * @return
     */
    @Override
    public Integer poolWaitingToBePaidSpectators(int raceNumber){
        return this.races[raceNumber].poolWaitingToBePaidSpectators();
    }
    
    /**
     *
     * @param i
     * @param raceNumber
     */
    @Override
    public void addWaitingToBePaidSpectator(int i, int raceNumber){
        this.races[raceNumber].addWaitingToBePaidSpectator(i);
    }
    
    /**
     *
     * @param raceNumber
     * @return
     */
    @Override
    public boolean allSpectatorsPaid(int raceNumber){
        return this.races[raceNumber].allSpectatorsPaid();
    }
    
    /**
     *
     * @param i
     * @param raceNumber
     * @return
     */
    @Override
    public Integer getPaidSpectators(int i, int raceNumber){        
        return this.races[raceNumber].getPaidSpectators(i);
    }
    
    /**
     *
     * @param i
     * @param set
     * @param raceNumber
     */
    @Override
    public void setPaidSpectators(int i, boolean set, int raceNumber){        
        this.races[raceNumber].setPaidSpectators(i, set);
    }
    
    /**
     *
     * @param spectatorId
     * @param raceNumber
     * @return
     */
    @Override
    public Bet getSpectatorBet(int spectatorId, int raceNumber){        
        return this.races[raceNumber].getSpectatorBet(spectatorId);
    }
    
    /**
     *
     * @param horseId
     * @param raceNumber
     * @return
     */
    @Override
    public double getHorseOdd(int horseId, int raceNumber){        
        return this.races[raceNumber].getHorseOdd(horseId);
    }
    
    /**
     *
     * @param horseId
     * @param raceNumber
     * @return
     */
    @Override
    public int getHorsePosition(int horseId, int raceNumber){        
        return this.races[raceNumber].getHorsePosition(horseId);
    }
    /* end condition states */
}
