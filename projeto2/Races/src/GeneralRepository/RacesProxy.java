package GeneralRepository;

import communication.Proxy.ClientProxy;
import communication.message.Message;
import communication.message.MessageType;
import communication.message.MessageWrapper;
import java.util.LinkedList;

/**
 * This file implements the proxy of Races.
 * @author Daniela Simões, 76771
 */
public class RacesProxy extends ClientProxy implements IRaces{
    
    public RacesProxy(){
        super("Races");
    }
    
    /**
     * Method to choose a horse to bet.
     * @param raceNumber
     * @param spectatorId
     * @param initialBet
     * @param moneyToBet
     * @return
     */
    @Override
    public Bet chooseBet(int raceNumber, int spectatorId, int initialBet, int moneyToBet){        
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, raceNumber, spectatorId, initialBet, moneyToBet));
        return result.getMessage().getBet();
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
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, horseJockeyID, horseStepSize, raceNumber));
        return result.getMessage().getBoolean();
    }
    
    /**
    * Method to set the horse max step size.
    * @param id
    * @param stepSize
    */
    @Override
    public void setHorseJockeyStepSize(int id, int stepSize){
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, id, stepSize));
    }
    
    /**
    * Method to get the horse jockey step.
    * @param id
    * @return
    */
    @Override
    public int getHorseJockeyStepSize(int id){
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, id));
        return result.getMessage().getInteger1();
    }
    
    /**
    * Method to verify if there are any winners.
    * @param raceNumber
    * @return
    */
    @Override
    public boolean areThereAnyWinners(int raceNumber){
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, raceNumber));
        return result.getMessage().getBoolean();
    };
    
    /**
     * Method to verify if a usar has won.
     * @param raceNumber
     * @param spectatorId
     * @return
     */
    @Override
    public boolean haveIWon(int raceNumber, int spectatorId){
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, raceNumber, spectatorId));
        return result.getMessage().getBoolean();
    };
    
    /**
    * Method to get the winner of the race.
    * @param raceNumber
    * @return 
    */
    @Override
    public LinkedList<Integer> getWinner(int raceNumber){        
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, raceNumber));
        return result.getMessage().getLinkedList();
    }
    
    /**
    * Method to verify if has more races to happen.
    * @return 
    */
    @Override
    public boolean hasMoreRaces(){
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt));
        return result.getMessage().getBoolean();
    }
    
    /**
    *
    * Method to allow the HorseJockey to make a move in the race.
    * @param horseId The ID of the HorseJockey.
    * @param raceNumber
    */
    @Override
    public void makeAMove(int horseId, int raceNumber){        
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, horseId, raceNumber));
    }
    
    /**
    * Method to get the horse iteration in a given race.
    * @param horseId
    * @param raceNumber
    * @return
    */
    @Override
    public int getHorseIteration(int horseId, int raceNumber){        
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, horseId, raceNumber));
        return result.getMessage().getInteger1();
    }
    
    /**
    * Method to get the standing position of a horse in a given race.
    * @param horseId
    * @param raceNumber
    * @return
    */
    @Override
    public int getStandingPosition(int horseId, int raceNumber){
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, horseId, raceNumber));
        return result.getMessage().getInteger1();
    }
    
    /**
    * Method to choose the next horse to move.
    * @param horseJockeyId
    * @param raceNumber
    * @return
    */
    @Override
    public boolean nextMovingHorse(int horseJockeyId, int raceNumber){        
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, horseJockeyId, raceNumber));
        return result.getMessage().getBoolean();
    }
    
    /**
    * Method to berify if a horse has finished the race.
    * @param horseId
    * @param raceNumber
    * @return
    */
    @Override
    public boolean horseFinished(int horseId, int raceNumber){        
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, horseId, raceNumber));
        return result.getMessage().getBoolean();
    }
    
    /**
    * Method to verify if the horse has finished.
    * @param raceNumber
    * @return
    */
    @Override
    public boolean horsesFinished(int raceNumber){        
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, raceNumber));
        return result.getMessage().getBoolean();
    }
    
    /**
    * Method to get the running horses.
    * @param raceNumber
    * @return
    */
    @Override
    public int getNRunningHorses(int raceNumber){        
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, raceNumber));
        return result.getMessage().getInteger1();
    }
    
    /**
    * Method to get the current race distance.
    * @param raceNumber
    * @return
    */
    @Override
    public int getCurrentRaceDistance(int raceNumber){
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, raceNumber));
        return result.getMessage().getInteger1();
    }
    
    /* condition states */
    /* Racing Track */

    /**
    * Method to get the start the race flag.
    * @param raceNumber
    * @return
    */
    @Override
    public boolean getStartTheRace(int raceNumber){        
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, raceNumber));
        return result.getMessage().getBoolean();
    }
    
    /**
    * Method to set start the race flag.
    * @param startTheRace
    * @param raceNumber
    */
    @Override
    public void setStartTheRace(boolean startTheRace, int raceNumber){        
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, raceNumber, startTheRace));
    }
    /* Stable */

    /**
    * Method to get the awaked horses to paddock.
    * @param raceNumber
    * @return
    */
    @Override
    public int getWakedHorsesToPaddock(int raceNumber){        
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, raceNumber));
        return result.getMessage().getInteger1();
    }
    
    /**
    * Method to add a awaked horse to paddock.
    * @param raceNumber
    */
    @Override
    public void addWakedHorsesToPaddock(int raceNumber){        
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, raceNumber));
    }
    
    /**
    * Method to get the announced next race flag.
    * @param raceNumber
    * @return
    */
    @Override
    public boolean getAnnouncedNextRace(int raceNumber){        
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, raceNumber));
        return result.getMessage().getBoolean();
    }
    
    /**
    * Method to set the announced next race flag.
    * @param announcedNextRace
    * @param raceNumber
    */
    @Override
    public void setAnnouncedNextRace(boolean announcedNextRace, int raceNumber){        
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, raceNumber, announcedNextRace));
    }
    /* Paddock */

    /**
    *
    * @param raceNumber
    * @return
    */
    @Override
    public boolean allSpectatorsArrivedAtPaddock(int raceNumber){        
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, raceNumber));
        return result.getMessage().getBoolean();
    }
    
    /**
    * Method to add spectator if has arrived to paddock.
    * @param raceNumber
    */
    @Override
    public void addNSpectatorsArrivedAtPaddock(int raceNumber){        
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, raceNumber));
    }
    
    /**
    * Method to verify if all horses has left the paddock.
    * @param raceNumber
    * @return
    */
    @Override
    public boolean allHorseJockeyLeftThePadock(int raceNumber){        
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, raceNumber));
        return result.getMessage().getBoolean();
    }
    
    /**
    * Method to add a horse jockey that has left the paddock.
    * @param raceNumber
    */
    @Override
    public void addNHorseJockeyLeftThePadock(int raceNumber){        
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, raceNumber));
    }
    
    /* Control Centre */

    /**
    * Method to set the report results flag.
    * @param set
    * @param raceNumber
    */
    @Override
    public void setReportResults(boolean set, int raceNumber){        
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, raceNumber, set));
    }
    
    /**
    * Method to get the report results flag.
    * @param raceNumber
    * @return
    */
    @Override
    public boolean getReportResults(int raceNumber){        
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, raceNumber));
        return result.getMessage().getBoolean();
    }
    
    /**
    * Method to set the proceed to paddock flag.
    * @param set
    * @param raceNumber
    */
    @Override
    public void setProceedToPaddock(boolean set, int raceNumber){        
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, raceNumber, set));
    }
    
    /**
    * Method to get the proceed to paddock flag.
    * @param raceNumber
    * @return
    */
    @Override
    public boolean getProceedToPaddock(int raceNumber){        
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, raceNumber));
        return result.getMessage().getBoolean();
    }
    
    /**
    * Method to verify if every horse are in paddock.
    * @param raceNumber
    * @return
    */
    @Override
    public boolean allNHorsesInPaddock(int raceNumber){        
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, raceNumber));
        return result.getMessage().getBoolean();
    }
    
    /**
    * Method to add a horse when it arrives to paddock.
    * @param raceNumber
    */
    @Override
    public void addNHorsesInPaddock(int raceNumber){        
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, raceNumber));
    }
    
    /* Betting Centre */

    /**
    * Method to wait to add a bet.
    * @param raceNumber
    * @return
    */
    @Override
    public Integer waitAddedBet(int raceNumber){        
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, raceNumber));
        return result.getMessage().getInteger();
    }
    
    /**
    * Method to verify if all spectators bet was accepted.
    * @param raceNumber
    * @return
    */
    @Override
    public boolean allSpectatorsBettsAceppted(int raceNumber){        
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, raceNumber));
        return result.getMessage().getBoolean();
    }
    
    /**
    * Method to add the bet of spectator.
    * @param bet
    * @param raceNumber
    * @param spectatorId
    */
    @Override
    public void addBetOfSpectator(Bet bet, int raceNumber, int spectatorId){        
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, raceNumber, spectatorId, bet));
    }
    
    /**
    * Method to verify if all spectators betted.
    * @param raceNumber
    * @return
    */
    @Override
    public boolean allSpectatorsBetted(int raceNumber){        
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, raceNumber));
        return result.getMessage().getBoolean();
    }
    
    /**
    * Method to wait to accept the bet.
    * @param raceNumber
    * @param spectatorId
    */
    @Override
    public void waitAcceptedTheBet(int raceNumber, int spectatorId){
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, raceNumber, spectatorId));
    }
   
    /**
    * Method to accept the bet.
    * @param i
    * @param raceNumber
    */
    @Override
    public void acceptBet(int i, int raceNumber){
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, i, raceNumber));
    }
    
    /**
    * Method to pool the spectators waiting to be paid.
    * @param raceNumber
    * @return
    */
    @Override
    public Integer poolWaitingToBePaidSpectators(int raceNumber){
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, raceNumber));
        return result.getMessage().getInteger();
    }
    
    /**
    * Method to wait to be paid.
    * @param i
    * @param raceNumber
    */
    @Override
    public void addWaitingToBePaidSpectator(int i, int raceNumber){
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, i, raceNumber));
    }
    
    /**
    * Method to verify if all spactators has been paid.
    * @param raceNumber
    * @return
    */
    @Override
    public boolean allSpectatorsPaid(int raceNumber){
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, raceNumber));
        return result.getMessage().getBoolean();
    }
    
    /**
    * Method to get the paid spectators.
    * @param i
    * @param raceNumber
    * @return
    */
    @Override
    public int getPaidSpectators(int i, int raceNumber){        
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, i, raceNumber));
        return result.getMessage().getInteger1();
    }
    
    /**
    * Method to set the paid spectators.
    * @param i
    * @param raceNumber
    */
    @Override
    public void setPaidSpectators(int i, int raceNumber){        
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, i, raceNumber));
    }
    
    /**
    * Method to get the spectator bet.
    * @param spectatorId
    * @param raceNumber
    * @return
    */
    @Override
    public Bet getSpectatorBet(int spectatorId, int raceNumber){        
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, spectatorId, raceNumber));
        return result.getMessage().getBet();
    }
    
    /**
    * Method to get the horse odd.
    * @param horseId
    * @param raceNumber
    * @return
    */
    @Override
    public double getHorseOdd(int horseId, int raceNumber){        
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, horseId, raceNumber));
        return result.getMessage().getDouble();
    }
    
    /**
    * Method to get the horse position.
    * @param horseId
    * @param raceNumber
    * @return
    */
    @Override
    public int getHorsePosition(int horseId, int raceNumber){        
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, horseId, raceNumber));
        return result.getMessage().getInteger1();
    }
    /* end condition states */
    
}
