package GeneralRepository;
import entities.HorseJockey;
import entities.Spectators;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Random;
import java.util.TreeMap;

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
    Queue<Integer> betsOfSpectators = new LinkedList<>();
    private final Object[] waitingAcceptedTheBet = new Object[Races.N_OF_SPECTATORS];
    private final boolean[] acceptedBet = new boolean[Races.N_OF_SPECTATORS];
    LinkedList<Bet> bets = new LinkedList();
    private Object addedBet = new Object();
    
    Queue<Integer> waitingToBePaidSpectators = new LinkedList<>();
    private final boolean[] paidSpectators = new boolean[Races.N_OF_SPECTATORS];
    private int nPaidSpectators = 0;
    LinkedList<Integer> spectatorsWinners = new LinkedList();
    
    /* end condition states */
    
    private boolean raceStarted = false;
    LinkedList<Integer> winners = new LinkedList();
    private final int id;
    
    private final HashMap<Integer, HorseJockey> horsesRunning;
    private final HashMap<Integer, Double> horsesPosition;
    private final HashMap<Integer, Boolean> horsesFinished;
    private final Map<Double, Integer> horsesOdds;
    
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
        this.trackSize = Races.SIZE_OF_RACING_TRACK;
        
        // number of horses to run is between 2 and N of Horses available to run
        this.nHorsesToRun = (int)(2 + (new Random().nextDouble() * (Races.N_OF_HORSES - 2)));
        
        this.horsesRunning = new HashMap<>();
        this.horsesPosition = new HashMap<>();
        this.horsesFinished = new HashMap<>();
        this.horsesOdds = new TreeMap<>();
        
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
        }
        
        for(int i = 0; i < Races.N_OF_SPECTATORS; i++){
            this.paidSpectators[i] = false;
            this.waitingAcceptedTheBet[i] = new Object();
            this.acceptedBet[i] = false;
        }
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
    
    public synchronized void generateOdds(){
        for(Entry<?, ?> e: horsesRunning.entrySet()){
            double stepSize = ((HorseJockey)e.getValue()).getStepSize();
            int horseJockeyId = ((HorseJockey)e.getValue()).getHorseId();
            
            // max = 5
            // step = 5
            // max-step*0,75=1,25
            
            this.horsesOdds.put(Races.HORSE_MAX_STEP_SIZE-stepSize*0.75, horseJockeyId);
        }
    }
    
    public synchronized Bet chooseBet(){
        Spectators spectator = ((Spectators)Thread.currentThread());
        
        double perception = spectator.getInitialBet() / Races.MAX_SPECTATOR_BET;
        double capacity = spectator.getMoneyToBet() / spectator.getInitialBet();
        double peek = perception * capacity * 100;
        
        double interval = 100 / this.nHorsesToRun;
        
        int choosen_risk_interval = Math.round((float)(peek / interval))-1;
        
        int i = 0;
        double odd = 1.0;
        int horseId = 0;
        
        for(Map.Entry<Double, Integer> entry : this.horsesOdds.entrySet()) {
            if(i==choosen_risk_interval){
                odd = (double)entry.getKey();
                horseId = (int)entry.getValue();
            }
            i++;
        }
        
        double amountToBet = spectator.getMoneyToBet()*0.1;
        
        spectator.subtractMoneyToBet(amountToBet);
        
        return new Bet(horseId, amountToBet, spectator.getSpectatorId(), odd);
    }
    
    
    public synchronized boolean areThereAnyWinners(){
        boolean thereAreWinners = false;
        
        for (Integer horseId : this.winners) {
            for(Bet bet : this.bets){
                thereAreWinners |= (bet.getHorseId() == horseId);
                
                if(bet.getHorseId() == horseId && !spectatorsWinners.contains(bet.getSpectatorId())){
                    spectatorsWinners.add(bet.getSpectatorId());
                }
            }
        }
        
        return thereAreWinners;
    };
    
    public synchronized boolean haveIWon(){
        Spectators spectator = ((Spectators)Thread.currentThread());
        
        boolean haveIWon = false;
        
        for (Integer horseId : this.winners) {
            for(Bet bet : this.bets){
                if(bet.getHorseId() == horseId && bet.getSpectatorId()==spectator.getSpectatorId()){
                    haveIWon = true;
                }
            }
        }
        
        return haveIWon;
    };

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
            
            if(winners.isEmpty()){
                winners.add(horseId);
            }else{
                double pos_winner = this.horsesPosition.get(this.winners.getFirst());
                
                if(this.horsesPosition.get(horseId)>pos_winner){
                    while(!this.winners.isEmpty()){
                        this.winners.remove();
                    }
                    
                    this.winners.add(horseId);
                }else if(this.horsesPosition.get(horseId)==pos_winner){
                    this.winners.add(horseId);
                }
            }
            
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
    
<<<<<<< HEAD
    /**
    *
    * Method to retrieve the HorseJockey winner.
    */
    public LinkedList<Integer> getWinner(){
        return this.winners;
    }
    
    
    /**
    *
    * Method to retrieve the number of HorseJockey participating in the race.
    */
    public int getNRunningHorses(){
        return this.nHorsesToRun;
    }
    
    public double getCurrentRaceDistance(){
        return this.trackSize;
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
    protected Integer waitAddedBet(){
        if(!this.betsOfSpectators.isEmpty()){
            return this.betsOfSpectators.poll();
        }else{
            synchronized (this.addedBet) {
                try {
                    System.out.println("Broker Waiting for bets");
                    this.addedBet.wait();
                    System.out.println("Broker waked");
                } catch (Exception e) {}
            }
            return this.betsOfSpectators.poll();
        }
    }
    
    protected boolean allSpectatorsBettsAceppted(){
        return this.betsOfSpectators.isEmpty();
    }
    
    protected void addBetOfSpectator(Bet bet){
        Spectators spectator = ((Spectators)Thread.currentThread());
        
        synchronized (this.addedBet) {
            this.bets.add(bet);
            this.betsOfSpectators.add(spectator.getSpectatorId());
            System.out.println("Spectator Placed Bet S" + spectator.getSpectatorId());
            this.addedBet.notifyAll();
        }
    }
    
    protected boolean allSpectatorsBetted(){
        boolean allSpectatorsBetted = true;
        
        for(int i=0; i<Races.N_OF_SPECTATORS; i++){
            if(!this.acceptedBet[i]){
                allSpectatorsBetted = false;
                this.betsOfSpectators.add(i);
            }
        }
        
        return allSpectatorsBetted;
    }
    
    protected void waitAcceptedTheBet(){
        Spectators spectator = ((Spectators)Thread.currentThread());
        
        synchronized (this.waitingAcceptedTheBet[spectator.getSpectatorId()]) {
            try {
                this.waitingAcceptedTheBet[spectator.getSpectatorId()].wait();
                this.acceptedBet[spectator.getSpectatorId()] = true;
                System.out.println("Broker Accepted bets for S" + (spectator.getSpectatorId()));
            } catch (Exception e) {}
        }
    }
   
    protected void acceptBet(int i){
        synchronized (this.waitingAcceptedTheBet[i]) {
            this.waitingAcceptedTheBet[i].notifyAll();
        }
    }
    
    protected Integer poolWaitingToBePaidSpectators(){
        return this.waitingToBePaidSpectators.poll();
    }
    
    protected void addWaitingToBePaidSpectator(int i){
        this.waitingToBePaidSpectators.add(i);
    }
    
    protected boolean allSpectatorsPaid(){
        return this.nPaidSpectators == this.spectatorsWinners.size();
    }
    
    protected synchronized boolean getPaidSpectators(int i){
        boolean pay = this.paidSpectators[i];
        
        if(pay){
            Spectators spectator = ((Spectators)Thread.currentThread());

            for (Integer horseId : this.winners) {
                for(Bet bet : this.bets){
                    if(bet.getHorseId() == horseId && bet.getSpectatorId()==spectator.getSpectatorId()){
                        spectator.addMoneyToBet(bet.getAmount());
                    }
                }
            }
            
        }
        
        return pay;
    }
    
    /**
    *
    * Method to set the variable of get paid.
    * @param i The id of the spectator.
    * @param set The value of the variable to be assigned.
    */
    protected synchronized void setPaidSpectators(int i, boolean set){
        this.nPaidSpectators++;
        this.paidSpectators[i] = set;
    }
    /* end condition states */
}
