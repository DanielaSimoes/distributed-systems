/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GeneralRepository;
import entities.HorseJockey;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author Daniela
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
    
    public boolean nextMovingHorse(int horseJockeyId){
        return this.selectedHorses[horseToMove]==horseJockeyId;
    }
    
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
    
    public boolean horseFinished(int horseId){
        return this.horsesFinished.get(horseId);
    }
    
    public boolean horsesFinished(){
        return this.nHorsesFinished==this.nHorsesToRun;
    }
    
    public int getWinner(){
        return this.winner;
    }
    
    public int getNRunningHorses(){
        return this.nHorsesToRun;
    }
    
    /* condition states */
    /* Racing Track */
    protected synchronized boolean getStartTheRace(){
        return this.startTheRace;
    }
    
    protected synchronized void setStartTheRace(boolean startTheRace){
        this.startTheRace = startTheRace;
    }
    /* Stable */
    protected synchronized int getWakedHorsesToPaddock(){
        return this.wakedHorsesToPaddock;
    }
    
    protected synchronized void addWakedHorsesToPaddock(){
        this.wakedHorsesToPaddock = this.wakedHorsesToPaddock + 1;
    }
    
    protected synchronized boolean getAnnuncedNextRace(){
        return this.annuncedNextRace;
    }
    
    protected synchronized void setAnnuncedNextRace(boolean annuncedNextRace){
        this.annuncedNextRace = annuncedNextRace;
    }
    /* Paddock */
    protected synchronized boolean allSpectatorsArrivedAtPaddock(){
        return this.nSpectatorsArrivedAtPaddock == Races.N_OF_SPECTATORS;
    }
    
    protected synchronized void addNSpectatorsArrivedAtPaddock(){
        this.nSpectatorsArrivedAtPaddock = this.nSpectatorsArrivedAtPaddock + 1;
    }
    
    protected synchronized boolean allHorseJockeyLeftThePadock(){
        return this.nHorseJockeyLeftThePadock == this.getNRunningHorses();
    }
    
    protected synchronized void addNHorseJockeyLeftThePadock(){
        this.nHorseJockeyLeftThePadock = this.nHorseJockeyLeftThePadock + 1;
    }
    
    /* Control Centre */
    protected synchronized void setReportResults(boolean set){
        this.reportResults = set;
    }
    
    protected synchronized boolean getReportResults(){
        return this.reportResults;
    }
    
    protected synchronized void setProceedToPaddock(boolean set){
        this.proceedToPaddock = set;
    }
    
    protected synchronized boolean getProceedToPaddock(){
        return this.proceedToPaddock;
    }
    
    protected synchronized boolean allNHorsesInPaddock(){       
        return this.nHorsesInPaddock == this.getNRunningHorses();
    }
    
    protected synchronized void addNHorsesInPaddock(){
        this.nHorsesInPaddock = this.nHorsesInPaddock + 1;
    }
    
    /* Betting Centre */
    protected synchronized boolean getBetsOfSpectator(int i){
        return this.betsOfSpectators[i];
    }
    
    protected synchronized void setBetsOfSpectator(int i, boolean set){
        this.betsOfSpectators[i] = set;
    }
    
    protected synchronized boolean getAcceptedTheBet(int i){
        return this.acceptedTheBet[i];
    }
    
    protected synchronized void setAcceptedTheBet(int i, boolean set){
        this.acceptedTheBet[i] = set;
    }
    
    protected synchronized boolean getWaitingToBePaidSpectators(int i){
        return this.waitingToBePaidSpectators[i];
    }
    
    protected synchronized void setWaitingToBePaidSpectators(int i, boolean set){
        this.waitingToBePaidSpectators[i] = set;
    }
    
    protected synchronized boolean getPaidSpectators(int i){
        return this.paidSpectators[i];
    }
    
    protected synchronized void setPaidSpectators(int i, boolean set){
        this.paidSpectators[i] = set;
    }
    /* end condition states */
}
