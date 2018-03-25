package GeneralRepository;
import entities.HorseJockey;
import java.util.HashMap;
import java.util.Random;

/**
 * This file describes the Race itself.
 * @author Daniela Simões, 76771
 */
public class Race {
    
    /* condition states */
    /* Racing Track */
    private boolean startTheRace = false;
    
    /* Stable */
    private int wakedHorsesToPaddock = 0;
    private boolean annuncedNextRace = false;
    
    /* Paddock */
    protected int nSpectatorsArrivedAtPaddock = 0;
    protected int nHorseJockeyLeftThePadock = 0;
    
    /* Control Centre */
    private boolean reportResults = false;
    private boolean proceedToPaddock = false;
    protected int nHorsesInPaddock = 0;
    
    /* BettingCentre */
    private final boolean[] betsOfSpectators = new boolean[Races.N_OF_SPECTATORS];
    private final boolean[] acceptedTheBet = new boolean[Races.N_OF_SPECTATORS];
    private final boolean[] waitingToBePaidSpectators = new boolean[Races.N_OF_SPECTATORS];
    private final boolean[] paidSpectators = new boolean[Races.N_OF_SPECTATORS];
    
    /* end condition states */
    
    private boolean raceStarted = false;
    private int winner;
    private final int id;
    
    private final HashMap<Integer, HorseJockey> horsesRunning;
    private final HashMap<Integer, Double> horsesPosition;
    private final HashMap<Integer, Boolean> horsesFinished;
    private int nHorsesFinished = 0;
    private final int[] selectedHorses;
    
    private final double trackSize;
    private final int nHorsesToRun;
    private int horseToMove = 0;
   
   /**
    *
    * Race Constructor - this constructor inits the horses positions and select the horses that will participate in the race.
    * @param ID The ID of the race.
    */
    public Race(int id){
        this.id = id;
        this.winner = -1;
        this.trackSize = Races.SIZE_OF_RACING_TRACK;
        
        // number of horses to run is between 2 and N of Horses available to run
        this.nHorsesToRun = (int)(2 + (new Random().nextDouble() * (Races.N_OF_HORSES - 2)));
        
        this.horsesRunning = new HashMap<>();
        this.horsesPosition = new HashMap<>();
        this.horsesFinished = new HashMap<>();
        
        this.selectedHorses = new int[this.nHorsesToRun];
        
        for(int i=0; i<this.nHorsesToRun; i++){
            boolean repeated;
            int selectedId;
            
            do{
                repeated = false;
                selectedId = (int)(new Random().nextDouble() * Races.N_OF_HORSES); 
                
                for(int j=0; j<this.nHorsesToRun; j++){
                    if(this.selectedHorses[j]==selectedId){
                        repeated = true;
                        break;
                    }
                }
            }while(repeated);
            
            this.selectedHorses[i] = selectedId;
            //System.out.printf("%d ", this.selectedHorses[i]);
        }
        
        //System.out.println();
        
        for(int i = 0; i < Races.N_OF_SPECTATORS; i++){
            this.betsOfSpectators[i] = false;
            this.acceptedTheBet[i] = false;
            this.waitingToBePaidSpectators[i] = false;
            this.paidSpectators[i] = false;
        }
        /*
        for(int x = 0; x < selectedHorses.length; x++ ){
            System.out.println(selectedHorses[x]);
        }
        */
    }
    
    /**
    *
    * Method to verify which horse should iterate next.
    * @param ID The ID of the HorseJockey.
    */
    public boolean nextMovingHorse(int horseJockeyId){
        return this.selectedHorses[horseToMove]==horseJockeyId;
    }
    
    /**
    *
    * Method to verify if a given horse was selected to participate in the race.
    * @param ID The ID of the HorseJockey.
    */
    public boolean horseHasBeenSelectedToRace(HorseJockey horseJockey){
        for(int i=0; i<this.nHorsesToRun; i++){
            if(this.selectedHorses[i]==horseJockey.getHorseId()){
                this.horsesRunning.put(horseJockey.getHorseId(), horseJockey);
                this.horsesPosition.put(horseJockey.getHorseId(), 0.0);
                this.horsesFinished.put(horseJockey.getHorseId(), false);
                return true;
            }
        }
        
        return false;
    }
    
    /**
    *
    * Method to increment a position of in the race a given horse.
    * @param ID The ID of the HorseJockey.
    */
    public void makeAMove(int horseId){
        double start = 0;
        double end = this.horsesRunning.get(horseId).getStepSize();
        double random = new Random().nextDouble();
        double result = start + (random * (end - start));

        this.horsesPosition.put(horseId, this.horsesPosition.get(horseId)+result);

        if(this.horsesPosition.get(horseId) >= trackSize){
            this.horsesFinished.put(horseId, Boolean.TRUE);
            this.nHorsesFinished += 1;
        }
        
        horseToMove = (horseToMove + 1) % this.nHorsesToRun;

        while(this.horsesFinished.get(this.selectedHorses[horseToMove]) && !this.horsesFinished()){
            horseToMove = (horseToMove + 1) % this.nHorsesToRun;
        }
    }
    
    /**
    *
    * Method to verify if a given horse has finished the race.
    * @param ID The ID of the HorseJockey.
    */
    public boolean horseFinished(int horseId){
        return this.horsesFinished.get(horseId);
    }
    
    /**
    *
    * Method to verify if all horses finished the race.
    */
    public boolean horsesFinished(){
        return this.nHorsesFinished==this.nHorsesToRun;
    }
    
    /**
    *
    * Method to retrieve the HorseJockey winner.
    */
    public int getWinner(){
        return this.winner;
    }
    
    /**
    *
    * Method to retrieve the number of HorseJockey participating in the race.
    */
    public int getNRunningHorses(){
        return this.nHorsesToRun;
    }
    
    /* condition states */
    /* Racing Track */

    /**
    *
    * Method to retrieve the status of the start of the race.
    */
    protected synchronized boolean getStartTheRace(){
        return this.startTheRace;
    }
    
    /**
    *
    * Method to set the variable of start the race.
    * @param startTheRace The value of the variable to be assigned.
    */
    protected synchronized void setStartTheRace(boolean startTheRace){
        this.startTheRace = startTheRace;
    }

    /* Stable */
    /**
    *
    * Method to retrieve the waked horses variable.
    */
    protected synchronized int getWakedHorsesToPaddock(){
        return this.wakedHorsesToPaddock;
    }
    
    /**
    *
    * Method to add waked horses.
    */
    protected synchronized void addWakedHorsesToPaddock(){
        this.wakedHorsesToPaddock = this.wakedHorsesToPaddock + 1;
    }
    
    /**
    *
    * Method to get the variable of announcing next race.
    */
    protected synchronized boolean getAnnuncedNextRace(){
        return this.annuncedNextRace;
    }
    
    /**
    *
    * Method to set the variable of announcing next race.
    * @param annuncedNextRace The value of the variable to be assigned.
    */
    protected synchronized void setAnnuncedNextRace(boolean annuncedNextRace){
        this.annuncedNextRace = annuncedNextRace;
    }

    /* Paddock */
    /**
    *
    * Method to verify if all Spectators reached the Paddock.
    */
    protected synchronized boolean allSpectatorsArrivedAtPaddock(){
        return this.nSpectatorsArrivedAtPaddock == Races.N_OF_SPECTATORS;
    }
    
    /**
    *
    * Method to add the variable of the Spectators that has arrived to Paddock.
    */
    protected synchronized void addNSpectatorsArrivedAtPaddock(){
        this.nSpectatorsArrivedAtPaddock = this.nSpectatorsArrivedAtPaddock + 1;
    }
    
    /**
    *
    * Method to verify if all horses left the Paddock.
    */
    protected synchronized boolean allHorseJockeyLeftThePadock(){
        return this.nHorseJockeyLeftThePadock == this.getNRunningHorses();
    }
    
    /**
    *
    * Method to add the variable of horses which left the Paddock.
    */
    protected synchronized void addNHorseJockeyLeftThePadock(){
        this.nHorseJockeyLeftThePadock = this.nHorseJockeyLeftThePadock + 1;
    }
    
    /* Control Centre */
    /**
    *
    * Method to set the variable of report results.
    * @param set The value of the variable to be assigned.
    */
    protected synchronized void setReportResults(boolean set){
        this.reportResults = set;
    }
    
    /**
    *
    * Method to retrieve the variable of report results.
    */
    protected synchronized boolean getReportResults(){
        return this.reportResults;
    }
    
    /**
    *
    * Method to set the variable of proceed to paddock.
    * @param set The value of the variable to be assigned.
    */
    protected synchronized void setProceedToPaddock(boolean set){
        this.proceedToPaddock = set;
    }
    
    /**
    *
    * Method to retrieve the variable of proceed to paddock.
    */
    protected synchronized boolean getProceedToPaddock(){
        return this.proceedToPaddock;
    }
    
    /**
    *
    * Method to verify if all horses are in paddock.
    */
    protected synchronized boolean allNHorsesInPaddock(){       
        return this.nHorsesInPaddock == this.getNRunningHorses();
    }
    
    /**
    *
    * Method to add the variable of horses in paddock.
    */
    protected synchronized void addNHorsesInPaddock(){
        this.nHorsesInPaddock = this.nHorsesInPaddock + 1;
    }
    
    /* Betting Centre */
    /**
    *
    * Method to retrieve the bet of the spectator.
    * @param i The id of the spectator.
    */
    protected synchronized boolean getBetsOfSpectator(int i){
        return this.betsOfSpectators[i];
    }
    
    /**
    *
    * Method to set the bet of the spectator.
    * @param i The id of the spectator.
    */
    protected synchronized void setBetsOfSpectator(int i, boolean set){
        this.betsOfSpectators[i] = set;
    }
    
    /**
    *
    * Method to verify if a given bet was accepted by the broker.
    * @param i The id of the spectator.
    */
    protected synchronized boolean getAcceptedTheBet(int i){
        return this.acceptedTheBet[i];
    }
    
    /**
    *
    * Method to accept the bet by the broker.
    * @param i The id of the spectator.
    * @param set The value of the variable to be assigned.
    */
    protected synchronized void setAcceptedTheBet(int i, boolean set){
        this.acceptedTheBet[i] = set;
    }
    
    /**
    *
    * Method to retrieve the variable of waiting to be paid.
    * @param i The id of the spectator.
    */
    protected synchronized boolean getWaitingToBePaidSpectators(int i){
        return this.waitingToBePaidSpectators[i];
    }
    
    /**
    *
    * Method to set the variable of waiting to be paid.
    * @param i The id of the spectator.
    * @param set The value of the variable to be assigned.
    */
    protected synchronized void setWaitingToBePaidSpectators(int i, boolean set){
        this.waitingToBePaidSpectators[i] = set;
    }
    
    /**
    *
    * Method to retrieve the variable of get paid.
    * @param i The id of the spectator.
    */
    protected synchronized boolean getPaidSpectators(int i){
        return this.paidSpectators[i];
    }
    
    /**
    *
    * Method to set the variable of get paid.
    * @param i The id of the spectator.
    * @param set The value of the variable to be assigned.
    */
    protected synchronized void setPaidSpectators(int i, boolean set){
        this.paidSpectators[i] = set;
    }
    /* end condition states */
}
