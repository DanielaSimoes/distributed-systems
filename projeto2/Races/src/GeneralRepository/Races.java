package GeneralRepository;

/**
 * This file describes the set of the Races.
 * @author Daniela Simões, 76771
 */

import java.util.HashMap;

import java.util.LinkedList;
import settings.NodeSetts;

/**
 * This file implements the Races.
 * @author Daniela Simões, 76771.
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
    * Races Constructor.
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
    * Method to choose the horse to bet.
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
    * Method to set the horse max step size.
    * @param id
    * @param stepSize
    */
    @Override
    public synchronized void setHorseJockeyStepSize(int id, int stepSize){
        this.horseJockeyStepSize.put(id, stepSize);
        
        if(this.horseJockeyStepSize.size()==NodeSetts.N_OF_HORSES){
            for (Race race : this.races) {
                race.generateOdds(this.horseJockeyStepSize);
            }
        }
    }
    
    /**
    * Method to get the horse max step size.
    * @param id
    * @return
    */
    @Override
    public synchronized int getHorseJockeyStepSize(int id){
        return this.horseJockeyStepSize.get(id);
    }
    
    /**
    * Method to verify if there are any winners.
    * @param raceNumber
    * @return
    */
    @Override
    public boolean areThereAnyWinners(int raceNumber){
        return this.races[raceNumber].areThereAnyWinners();
    };
    
    /**
    * Method to verify if a spectator has won.
    * @param raceNumber
    * @param spectatorId
    * @return
    */
    @Override
    public boolean haveIWon(int raceNumber, int spectatorId){
        return this.races[raceNumber].haveIWon(spectatorId);
    };
    
    /**
    * Method to get the winner of the race.
    * @param raceNumber
    * @return 
    */
    @Override
    public LinkedList<Integer> getWinner(int raceNumber){        
        return races[raceNumber].getWinner();
    }
    
    /**
    * Method to verify if has more races to happen.
    * @return 
    */
    @Override
    public boolean hasMoreRaces(){
        return !this.races[NodeSetts.N_OF_RACES-1].horsesFinished();
    }
    
    /**
    * Method to allow the HorseJockey to make a move in the race.
    * @param horseId The ID of the HorseJockey.
    * @param raceNumber
    */
    @Override
    public void makeAMove(int horseId, int raceNumber){        
        this.races[raceNumber].makeAMove(horseId);
    }
    
    /**
    * Method to get the horse iteration in a given race.
    * @param horseId
    * @param raceNumber
    * @return
    */
    @Override
    public int getHorseIteration(int horseId, int raceNumber){        
        return this.races[raceNumber].getHorseIteration(horseId);
    }
    
    /**
    * Method to get the standing position of a given horse in a given race.
    * @param horseId
    * @param raceNumber
    * @return
    */
    @Override
    public int getStandingPosition(int horseId, int raceNumber){
        return this.races[raceNumber].getStandingPosition(horseId);
    }
    
    /**
    * Method to choose the next moving horse.
    * @param horseJockeyId
    * @param raceNumber
    * @return
    */
    @Override
    public boolean nextMovingHorse(int horseJockeyId, int raceNumber){        
        return this.races[raceNumber].nextMovingHorse(horseJockeyId);
    }
    
    /**
    * Method to verify if a given horse has finished the race.
    * @param horseId
    * @param raceNumber
    * @return
    */
    @Override
    public boolean horseFinished(int horseId, int raceNumber){        
        return this.races[raceNumber].horseFinished(horseId);
    }
   
    /**
    * Method to verify if all horses finished the race.
    * @param raceNumber
    * @return 
    */
    @Override
    public boolean horsesFinished(int raceNumber){     
        return this.races[raceNumber].horsesFinished();
    }
    
    /**
    * Method to get the running horses.
    * @param raceNumber
    * @return
    */
    @Override
    public int getNRunningHorses(int raceNumber){        
        return this.races[raceNumber].getNRunningHorses();
    }
    
    /**
    * Method to get the current distance in a given race.
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
    * Method to get start the race flag.
    * @param raceNumber
    * @return
    */
    @Override
    public boolean getStartTheRace(int raceNumber){        
        return this.races[raceNumber].getStartTheRace();
    }
    
    /**
    * Method to set start the race flag.
    * @param startTheRace
    * @param raceNumber
    */
    @Override
    public void setStartTheRace(boolean startTheRace, int raceNumber){        
        this.races[raceNumber].setStartTheRace(startTheRace);
    }
    /* Stable */

    /**
    * Method to get the awaked horses to paddock.
    * @param raceNumber
    * @return
    */
    @Override
    public int getWakedHorsesToPaddock(int raceNumber){        
        return this.races[raceNumber].getWakedHorsesToPaddock();
    }
    
    /**
    * Method to add a awaked horse to paddock.
    * @param raceNumber
    */
    @Override
    public void addWakedHorsesToPaddock(int raceNumber){        
        this.races[raceNumber].addWakedHorsesToPaddock();
    }
    
    /**
    * Method to get the announced next race flag.
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
    * Method to set the announced next race flag.
    * @param annuncedNextRace
    * @param raceNumber
    */
    @Override
    public void setAnnouncedNextRace(boolean annuncedNextRace, int raceNumber){        
        this.races[raceNumber].setAnnuncedNextRace(annuncedNextRace);
    }
    /* Paddock */

    /**
    * Method to verify if all spectators had arrived to paddock.
    * @param raceNumber
    * @return
    */
    @Override
    public boolean allSpectatorsArrivedAtPaddock(int raceNumber){        
        return this.races[raceNumber].allSpectatorsArrivedAtPaddock();
    }
    
    /**
    * Method to add spectator if has arrived to paddock.
    * @param raceNumber
    */
    @Override
    public void addNSpectatorsArrivedAtPaddock(int raceNumber){        
        this.races[raceNumber].addNSpectatorsArrivedAtPaddock();
    }
    
    /**
    * Method to verify if all horses has left the paddock.
    * @param raceNumber
    * @return
    */
    @Override
    public boolean allHorseJockeyLeftThePadock(int raceNumber){        
        return this.races[raceNumber].allHorseJockeyLeftThePadock();
    }
    
    /**
    * Method to add a horse jockey that has left the paddock.
    * @param raceNumber
    */
    @Override
    public void addNHorseJockeyLeftThePadock(int raceNumber){        
        this.races[raceNumber].addNHorseJockeyLeftThePadock();
    }
    
    /* Control Centre */

    /**
    * Method to set the report results flag.
    * @param set
    * @param raceNumber
    */
    @Override
    public void setReportResults(boolean set, int raceNumber){        
        this.races[raceNumber].setReportResults(set);
    }
    
    /**
    * Method to get the report results flag.
    * @param raceNumber
    * @return
    */
    @Override
    public boolean getReportResults(int raceNumber){        
        return this.races[raceNumber].getReportResults();
    }
    
    /**
    * Method to set the proceed to paddock flag.
    * @param set
    * @param raceNumber
    */
    @Override
    public void setProceedToPaddock(boolean set, int raceNumber){        
        this.races[raceNumber].setProceedToPaddock(set);
    }
    
    /**
    * Method to get the proceed to paddock flag.
    * @param raceNumber
    * @return
    */
    @Override
    public boolean getProceedToPaddock(int raceNumber){        
        return this.races[raceNumber].getProceedToPaddock();
    }
    
    /**
    * Method to verify if every horse are in paddock.
    * @param raceNumber
    * @return
    */
    @Override
    public boolean allNHorsesInPaddock(int raceNumber){        
        return this.races[raceNumber].allNHorsesInPaddock();
    }
    
    /**
    * Method to add a horse when it arrives to paddock.
    * @param raceNumber
    */
    @Override
    public void addNHorsesInPaddock(int raceNumber){        
        this.races[raceNumber].addNHorsesInPaddock();
    }
    
    /* Betting Centre */

    /**
    * Method to wait to add a bet.
    * @param raceNumber
    * @return
    */
    @Override
    public Integer waitAddedBet(int raceNumber){        
        return this.races[raceNumber].waitAddedBet();
    }
    
    /**
    * Method to verify if all spectators bet was accepted.
    * @param raceNumber
    * @return
    */
    @Override
    public boolean allSpectatorsBettsAceppted(int raceNumber){        
        return this.races[raceNumber].allSpectatorsBettsAceppted();
    }
    
    /**
    * Method to add the bet of spectator.
    * @param bet
    * @param raceNumber
    * @param spectatorId
    */
    @Override
    public void addBetOfSpectator(Bet bet, int raceNumber, int spectatorId){        
        this.races[raceNumber].addBetOfSpectator(bet, spectatorId);
    }
    
    /**
    * Method to verify if all spectators betted.
    * @param raceNumber
    * @return
    */
    @Override
    public boolean allSpectatorsBetted(int raceNumber){        
        return this.races[raceNumber].allSpectatorsBetted();
    }
    
    /**
    * Method to wait to accept the bet.
    * @param raceNumber
    * @param spectatorId
    */
    @Override
    public void waitAcceptedTheBet(int raceNumber, int spectatorId){
        this.races[raceNumber].waitAcceptedTheBet(spectatorId);
    }
   
    /**
    * Method to accept the bet.
    * @param i
    * @param raceNumber
    */
    @Override
    public void acceptBet(int i, int raceNumber){
        this.races[raceNumber].acceptBet(i);
    }
    
    /**
    * Method to pool the spectators waiting to be paid.
    * @param raceNumber
    * @return
    */
    @Override
    public Integer poolWaitingToBePaidSpectators(int raceNumber){
        return this.races[raceNumber].poolWaitingToBePaidSpectators();
    }
    
    /**
    * Method to wait to be paid.
    * @param i
    * @param raceNumber
    */
    @Override
    public void addWaitingToBePaidSpectator(int i, int raceNumber){
        this.races[raceNumber].addWaitingToBePaidSpectator(i);
    }
    
    /**
    * Method to verify if all spactators has been paid.
    * @param raceNumber
    * @return
    */
    @Override
    public boolean allSpectatorsPaid(int raceNumber){
        return this.races[raceNumber].allSpectatorsPaid();
    }
    
    /**
    * Method to get the paid spectators.
    * @param i
    * @param raceNumber
    * @return
    */
    @Override
    public int getPaidSpectators(int i, int raceNumber){        
        return this.races[raceNumber].getPaidSpectators(i);
    }
    
    /**
    * Method to set the paid spectators.
    * @param i
    * @param raceNumber
    */
    @Override
    public void setPaidSpectators(int i, int raceNumber){        
        this.races[raceNumber].setPaidSpectators(i);
    }
    
    /**
    * Method to get the spectator bet.
    * @param spectatorId
    * @param raceNumber
    * @return
    */
    @Override
    public Bet getSpectatorBet(int spectatorId, int raceNumber){        
        return this.races[raceNumber].getSpectatorBet(spectatorId);
    }
    
    /**
    * Method to get the horse odd.
    * @param horseId
    * @param raceNumber
    * @return
    */
    @Override
    public double getHorseOdd(int horseId, int raceNumber){        
        return this.races[raceNumber].getHorseOdd(horseId);
    }
    
    /**
    * Method to get the horse position.
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
