package shared;

import GeneralRepository.RacesStub;

/**
 * This file contains the shared memory region Paddock.
 * @author Daniela Simões, 76771
 */
public class Paddock implements IPaddock {
    
    private RacesStub races;
    
    public Paddock(RacesStub races){
        this.races = races;
    }
    
    /**
    *
    * Method to send the horses to paddock.
     * @param raceNumber
    */
    @Override
    public synchronized void proceedToPaddock(int raceNumber){
        while(!this.races.allSpectatorsArrivedAtPaddock(raceNumber)){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
    };
    
    /**
    *
    * Method to send the horses to the start line.
     * @param raceNumber
    */
    @Override
    public synchronized void proceedToStartLine(int raceNumber){
        this.races.addNHorseJockeyLeftThePadock(raceNumber);
        
        if(this.races.allHorseJockeyLeftThePadock(raceNumber)){
            notifyAll();
        }
    };
    
    /**
    *
    * Method to get the broker to announce the next race.
     * @param raceNumber
    */
    @Override
    public synchronized void summonHorsesToPaddock(int raceNumber){
        while(!this.races.allSpectatorsArrivedAtPaddock(raceNumber)){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
    };
    
    /**
    *
    * Method to get spectators to go check the horses.
     * @param raceNumber
    */
    @Override
    public synchronized void goCheckHorses(int raceNumber){
        this.races.addNSpectatorsArrivedAtPaddock(raceNumber);
        notifyAll();
    
        while(!this.races.allHorseJockeyLeftThePadock(raceNumber)){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
    };
    
}
