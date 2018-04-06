package GeneralRepository;
import entities.HorseJockey;
import entities.Spectators;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

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

    /**
     * Number os spectators arrived at paddock.
     */

    protected int nSpectatorsArrivedAtPaddock = 0;

    /**
     * Number of horses which left the paddock.
     */
    protected int nHorseJockeyLeftThePadock = 0;
    
    /* Control Centre */
    private boolean reportResults = false;
    private boolean proceedToPaddock = false;

    /**
     * Number of horses in paddock.
     */
    protected int nHorsesInPaddock = 0;
    private int[] horseIterations = new int[Races.N_OF_HORSES];
    
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
     * @param id - race id
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
            this.horseIterations[i] = 0;
        }
        
    }
    
    /**
    *
    * Method to verify which horse should iterate next.
     * @param horseJockeyId
     * @return next moving horse
    */
    public boolean nextMovingHorse(int horseJockeyId){
        return this.selectedHorses[horseToMove]==horseJockeyId;
    }
    
    /**
    *
    * Method to verify if a given horse was selected to participate in the race.
     * @param horseJockey
     * @return if the horse has been selected to run and init its position.
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
     * Method to generate the odds - each bet is generated considering the maximum step size of each horse, the less the step size, the bigger the odd.
     */
    public synchronized void generateOdds(){
        for(Entry<?, ?> e: horsesRunning.entrySet()){
            double stepSize = ((HorseJockey)e.getValue()).getStepSize();
            int horseJockeyId = ((HorseJockey)e.getValue()).getHorseId();
            
            // max = 5
            // step = 5
            // max-step*0,75=1,25
            double percent = 0.75;
            double odd = Races.HORSE_MAX_STEP_SIZE-stepSize*percent;
            
            while(this.horsesOdds.containsKey(odd)){
                percent += 0.01;
                odd = Races.HORSE_MAX_STEP_SIZE-stepSize*percent;
            }
            
            this.horsesOdds.put(odd, horseJockeyId);
        }
    }
    
    /**
     * Method to choose the bet to each spectator.
     * @return
     */
    public synchronized Bet chooseBet(){
        if(this.horsesOdds.size()!=this.nHorsesToRun){
            this.generateOdds();
        }
        
        Spectators spectator = ((Spectators)Thread.currentThread());
        
        // get initial bank and divide with the maximum amount possible to bet
        double perception = spectator.getInitialBet() / Races.MAX_SPECTATOR_BET;
        // the capacity of the user compared to the initial bank
        double capacity = spectator.getMoneyToBet() / spectator.getInitialBet();
        // 
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
    
    /**
     * Method to retrieve the spectator Bet.
     * @param spectatorId
     * @return
     */
    public Bet getSpectatorBet(int spectatorId){
        for(Bet bet : this.bets){
            if(bet.getSpectatorId()==spectatorId){
                return bet;
            }
        }
        return null;
    }
    
    /**
     * Method to get the odd generated to a given horse.
     * @param horseId
     * @return
     */
    public double getHorseOdd(int horseId){
        for(Map.Entry<Double, Integer> entry : this.horsesOdds.entrySet()) {
            if((int)entry.getValue()==horseId){
                return (double)entry.getKey();
            }
        }
        return 0.0;
    }
    
    /**
     * Method to verify if are there any winners of the bets.
     * @return
     */
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
    
    /**
     * Method to verify if a spectator has won the bet.
     * @return
     */
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
     * @param horseId
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
                double iteration_winner = this.horseIterations[this.winners.getFirst()];
                
                if(this.horsesPosition.get(horseId)>pos_winner && this.horseIterations[horseId] <= iteration_winner){
                    while(!this.winners.isEmpty()){
                        this.winners.remove();
                    }
                    
                    this.winners.add(horseId);
                }else if(this.horsesPosition.get(horseId)==pos_winner && this.horseIterations[horseId] <= iteration_winner){
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
        
        this.horseIterations[horseId]++;
    }
    
    /**
     * Method to retrieve the number of iterations that a given horse has in the race.
     * @param horseId
     * @return
     */
    public int getHorseIteration(int horseId){
        return this.horseIterations[horseId];
    }
    
    /**
     * Method to retrieve the horse position in the race.
     * @param horseId
     * @return
     */
    public double getHorsePosition(int horseId){
        if(!this.horsesPosition.containsKey(horseId)){
            return 0.0;
        }
        return this.horsesPosition.get(horseId);
    }
    
    /**
     * Method to verify the standing position at the end of the race.
     * @param horseId
     * @return
     */
    public int getStandingPosition(int horseId){
        int pos = 0;
        
        if(this.horsesFinished()){
            Stream<Map.Entry<Integer, Double>> entries = this.horsesPosition.entrySet().stream()
                .sorted((k1, k2) -> -k1.getValue().compareTo(k2.getValue()));
            
            Iterator<Map.Entry<Integer, Double>> it = entries.iterator();
            
            while (it.hasNext()) {
                Map.Entry<Integer, Double> pair = it.next();
                
                if(pair.getKey()==horseId){
                    return pos;
                }
                
                pos++;
            }
        }
        
        return 0;
    }
    
    /**
    *
    * Method to verify if a given horse has finished the race.
     * @param horseId
     * @return 
    */
    public boolean horseFinished(int horseId){
        return this.horsesFinished.get(horseId);
    }
    
    /**
    *
    * Method to verify if all horses finished the race.
     * @return 
    */
    public boolean horsesFinished(){
        return this.nHorsesFinished==this.nHorsesToRun;
    }
    
    /**
    *
    * Method to retrieve the HorseJockey winner.
     * @return 
    */
    public LinkedList<Integer> getWinner(){
        return this.winners;
    }
    
    
    /**
    *
    * Method to retrieve the number of HorseJockey participating in the race.
     * @return 
    */
    public int getNRunningHorses(){
        return this.nHorsesToRun;
    }
    
    /**
     * Method to retrieve the racing track size.
     * @return
     */
    public double getCurrentRaceDistance(){
        return this.trackSize;
    }
    
    /* condition states */
    /* Racing Track */

    /**
    *
    * Method to retrieve the status of the start of the race.
     * @return 
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
     * @return 
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
     * @return 
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
     * @return 
    */
    protected boolean allSpectatorsArrivedAtPaddock(){
        return this.nSpectatorsArrivedAtPaddock == Races.N_OF_SPECTATORS;
    }
    
    /**
    *
    * Method to add the variable of the Spectators that has arrived to Paddock.
    */
    protected void addNSpectatorsArrivedAtPaddock(){
        this.nSpectatorsArrivedAtPaddock = this.nSpectatorsArrivedAtPaddock + 1;
    }
    
    /**
    *
    * Method to verify if all horses left the Paddock.
     * @return 
    */
    protected boolean allHorseJockeyLeftThePadock(){
        return this.nHorseJockeyLeftThePadock == this.getNRunningHorses();
    }
    
    /**
    *
    * Method to add the variable of horses which left the Paddock.
    */
    protected void addNHorseJockeyLeftThePadock(){
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
     * @return 
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
     * @return 
    */
    protected synchronized boolean getProceedToPaddock(){
        return this.proceedToPaddock;
    }
    
    /**
    *
    * Method to verify if all horses are in paddock.
     * @return 
    */
    protected boolean allNHorsesInPaddock(){       
        return this.nHorsesInPaddock == this.getNRunningHorses();
    }
    
    /**
    *
    * Method to add the variable of horses in paddock.
    */
    protected void addNHorsesInPaddock(){
        this.nHorsesInPaddock = this.nHorsesInPaddock + 1;
    }
    
    /* Betting Centre */

    /**
     * Method to wait for the next spectator to bet.
     * @return
     */

    protected Integer waitAddedBet(){
        if(!this.betsOfSpectators.isEmpty() || this.betsOfSpectators.peek()==null){
            return this.betsOfSpectators.poll();
        }else{
            synchronized (this.addedBet) {
                try {
                    this.addedBet.wait();
                } catch (Exception e) {}
            }
            return this.betsOfSpectators.poll();
        }
    }
    
    /**
     * Method to verify if all spectators had their bets accepted.
     * @return
     */
    protected boolean allSpectatorsBettsAceppted(){
        return this.betsOfSpectators.peek()==null || this.betsOfSpectators.isEmpty();
    }
    
    /**
     * Method to add the bet of the spectator.
     * @param bet
     */
    protected void addBetOfSpectator(Bet bet){
        Spectators spectator = ((Spectators)Thread.currentThread());
        
        synchronized (this.addedBet) {
            this.bets.add(bet);
            Integer spectatorId = (Integer)spectator.getSpectatorId();
            
            this.betsOfSpectators.add(spectatorId);
            //System.out.println("Spectator Placed Bet S" + spectator.getSpectatorId());
            this.addedBet.notifyAll();
        }
    }
    
    /**
     * Method to verify if every spectator has betted.
     * @return
     */
    protected boolean allSpectatorsBetted(){
        boolean allSpectatorsBetted = true;
        
        for(int i=0; i<Races.N_OF_SPECTATORS; i++){
            if(!this.acceptedBet[i]){
                allSpectatorsBetted = false;
                synchronized (this.addedBet) {
                    this.betsOfSpectators.add(i);
                    this.addedBet.notifyAll();
                }
            }
        }
        
        return allSpectatorsBetted;
    }
    
    /**
     * Method wait for the spectator bet to be accepted by the broker.
     */
    protected void waitAcceptedTheBet(){
        Spectators spectator = ((Spectators)Thread.currentThread());
        
        synchronized (this.waitingAcceptedTheBet[spectator.getSpectatorId()]) {
            try {
                this.waitingAcceptedTheBet[spectator.getSpectatorId()].wait();
                this.acceptedBet[spectator.getSpectatorId()] = true;
            } catch (Exception e) {}
        }
    }
   
    /**
     * Method to wake the spectator who has betted.
     * @param i
     */
    protected void acceptBet(int i){
        synchronized (this.waitingAcceptedTheBet[i]) {
            this.waitingAcceptedTheBet[i].notifyAll();
        }
    }
    
    /**
     * Method to wait for the spectators to be paid.
     * @return
     */
    protected Integer poolWaitingToBePaidSpectators(){
        return this.waitingToBePaidSpectators.poll();
    }
    
    /**
     * Method to set that a given spectator has been paid.
     * @param i
     */
    protected void addWaitingToBePaidSpectator(int i){
        this.waitingToBePaidSpectators.add(i);
    }
    
    /**
     * Method to verify if every spectator have been paid.
     * @return
     */
    protected boolean allSpectatorsPaid(){
        return this.nPaidSpectators == this.spectatorsWinners.size();
    }
    
    /**
     * Method to verify if a given spectator has been paid.
     * @param i
     * @return
     */
    protected synchronized boolean getPaidSpectators(int i){
        boolean pay = this.paidSpectators[i];
        
        if(pay){
            Spectators spectator = ((Spectators)Thread.currentThread());

            for (Integer horseId : this.winners) {
                for(Bet bet : this.bets){
                    if(bet.getHorseId() == horseId && bet.getSpectatorId()==spectator.getSpectatorId()){
                        spectator.addMoneyToBet(bet.getAmount()*bet.getOdd());
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
