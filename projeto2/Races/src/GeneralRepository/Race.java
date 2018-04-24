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
    
    private final HashMap<Integer, Integer> horsesStepSize;
    private final HashMap<Integer, Integer> horsesPosition;
    private final HashMap<Integer, Boolean> horsesFinished;
    private final Map<Double, LinkedList<Integer>> horsesOdds;
    
    private int nHorsesFinished = 0;
    private final int[] selectedHorses;
    
    private final int trackSize;
    private int horseToMove = 0;
   
   /**
    *
    * Race Constructor - this constructor inits the horses positions and select the horses that will participate in the race.
     * @param id - race id
    */
    public Race(int id, LinkedList<Integer> horseJockeySelected){
        this.id = id;
        this.trackSize = Races.SIZE_OF_RACING_TRACK;
        
        this.horsesStepSize = new HashMap<>();
        this.horsesPosition = new HashMap<>();
        this.horsesFinished = new HashMap<>();
        this.horsesOdds = new TreeMap<>();
        
        this.selectedHorses = new int[Races.N_OF_HORSES_TO_RUN];
        
        for(int i=0; i<Races.N_OF_HORSES_TO_RUN; i++){
            boolean repeated;
            int selectedId;
            this.selectedHorses[i] = -1;
            
            do{
                repeated = false;
                selectedId = (int) (Math.random() * (Races.N_OF_HORSES));
                
                if(horseJockeySelected.contains(selectedId)){
                    repeated = true;
                }else{
                    for(int j=0; j<Races.N_OF_HORSES_TO_RUN; j++){
                        if(this.selectedHorses[j]==selectedId){
                            repeated = true;
                            break;
                        }
                    }   
                }
                
            }while(repeated);
            
            this.selectedHorses[i] = selectedId;
            horseJockeySelected.add(selectedId);
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
    public boolean horseHasBeenSelectedToRace(int horseJockeyID, int stepSize){
        for(int i=0; i<Races.N_OF_HORSES_TO_RUN; i++){
            if(this.selectedHorses[i]==horseJockeyID){
                this.horsesStepSize.put(horseJockeyID, stepSize);
                this.horsesPosition.put(horseJockeyID, 0);
                this.horsesFinished.put(horseJockeyID, false);
                return true;
            }
        }
        
        return false;
    }
    
    public boolean horseHasBeenSelectedToRace(int horseJockeyId){
        for(int i=0; i<this.selectedHorses.length; i++){
            if(this.selectedHorses[i]==horseJockeyId){
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Method to generate the odds - each bet is generated considering the maximum step size of each horse, the less the step size, the bigger the odd.
     * @param horseJockeyStepSize
     */
    public synchronized void generateOdds(HashMap<Integer, Integer> horseJockeyStepSize){
        int horseJockeyStepSizeSum = 0;
        
        for(int i=0; i<this.selectedHorses.length; i++){
            horseJockeyStepSizeSum += horseJockeyStepSize.get(this.selectedHorses[i]);
        }
        
        for(int i=0; i<this.selectedHorses.length; i++){
            int stepSize = horseJockeyStepSize.get(this.selectedHorses[i]);
            int horseJockeyId = this.selectedHorses[i];
            
            double odd = 1.0/(stepSize*1.0/horseJockeyStepSizeSum);
            
            LinkedList<Integer> horseJockeyIds;
            
            if(this.horsesOdds.containsKey(odd)){
                horseJockeyIds = this.horsesOdds.get(odd);
            }else{
                horseJockeyIds = new LinkedList<>();
            }
            
            horseJockeyIds.add(horseJockeyId);
            
            this.horsesOdds.put(odd, horseJockeyIds);
        }
    }
    
    /**
     * Method to choose the bet to each spectator.
     * @param spectatorId
     * @param initialBet
     * @param moneyToBet
     * @return
     */
    public synchronized Bet chooseBet(int spectatorId, int initialBet, int moneyToBet){
        // get initial bank and divide with the maximum amount possible to bet
        double perception = initialBet / Races.MAX_SPECTATOR_BET;
        // the capacity of the user compared to the initial bank
        double capacity = moneyToBet / initialBet;
        // 
        double peek = perception * capacity * 100;
        
        double interval = 100 / Races.N_OF_HORSES_TO_RUN;
        
        int choosen_risk_interval = Math.round((float)(peek / interval))-1;
        
        int i = 0;
        double odd = 1.0;
        int horseId = 0;
        
        for(Map.Entry<Double, LinkedList<Integer>> entry : this.horsesOdds.entrySet()) {
            if(i==choosen_risk_interval){
                odd = (double)entry.getKey();
                
                // idxMin = 0
                // idxMax = linkedlist size
                LinkedList<Integer> horseJockeyIds = ((LinkedList<Integer>)entry.getValue());
                
                int idxMin = 0;
                int idxMax = horseJockeyIds.size()-1;
                int idx = (int) (Math.random() * (idxMax - idxMin) + idxMin);
                        
                horseId = ((LinkedList<Integer>)entry.getValue()).get(idx);
            }
            i++;
        }
        
        int amountToBet = (int)(moneyToBet*0.1);
        
        return new Bet(horseId, amountToBet, spectatorId, odd);
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
        for(Map.Entry<Double, LinkedList<Integer>> entry : this.horsesOdds.entrySet()) {
            double odd = (double)entry.getKey();
            LinkedList<Integer> horseJockeyIds = (LinkedList<Integer>)entry.getValue();
            
            for(Integer horseJockeyId : horseJockeyIds){
                if(horseJockeyId==horseId){
                    return odd;
                }
            }
        }
        throw new java.lang.NullPointerException();
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
     * @param spectatorId
     * @return
     */
    public synchronized boolean haveIWon(int spectatorId){
        boolean haveIWon = false;
        
        for (Integer horseId : this.winners) {
            for(Bet bet : this.bets){
                if(bet.getHorseId() == horseId && bet.getSpectatorId()==spectatorId){
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
        int start = 1;
        int end = this.horsesStepSize.get(horseId);
        double random = new Random().nextDouble();
        int result = (int)(start + (random * (end - start)));

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
        
        horseToMove = (horseToMove + 1) % Races.N_OF_HORSES_TO_RUN;

        while(this.horsesFinished.get(this.selectedHorses[horseToMove]) && !this.horsesFinished()){
            horseToMove = (horseToMove + 1) % Races.N_OF_HORSES_TO_RUN;
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
    public int getHorsePosition(int horseId){
        if(!this.horsesPosition.containsKey(horseId)){
            return 0;
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
            Stream<Map.Entry<Integer, Integer>> entries = this.horsesPosition.entrySet().stream()
                .sorted((k1, k2) -> -k1.getValue().compareTo(k2.getValue()));
            
            Iterator<Map.Entry<Integer, Integer>> it = entries.iterator();
            
            while (it.hasNext()) {
                Map.Entry<Integer, Integer> pair = it.next();
                
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
        return this.nHorsesFinished==Races.N_OF_HORSES_TO_RUN;
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
        return Races.N_OF_HORSES_TO_RUN;
    }
    
    /**
     * Method to retrieve the racing track size.
     * @return
     */
    public int getCurrentRaceDistance(){
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
     * @param spectatorId
     */
    protected void addBetOfSpectator(Bet bet, int spectatorId){
        synchronized (this.addedBet) {
            this.bets.add(bet);
            
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
     * @param spectatorId
     */
    protected void waitAcceptedTheBet(int spectatorId){
        synchronized (this.waitingAcceptedTheBet[spectatorId]) {
            try {
                this.waitingAcceptedTheBet[spectatorId].wait();
                this.acceptedBet[spectatorId] = true;
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
     * @param spectatorId
     * @return
     */
    protected synchronized Integer getPaidSpectators(int spectatorId){
        boolean pay = this.paidSpectators[spectatorId];
        
        if(pay){
            for (Integer horseId : this.winners) {
                for(Bet bet : this.bets){
                    if(bet.getHorseId() == horseId && bet.getSpectatorId()==spectatorId){
                        return (int)(bet.getAmount()*bet.getOdd());
                    }
                }
            }
            
        }
        
        return -1;
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
