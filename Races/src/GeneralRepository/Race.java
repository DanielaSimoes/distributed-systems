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
    
    private int winner;
    private final int id;
    
    private final HashMap<Integer, HorseJockey> horsesRunning;
    private final HashMap<Integer, Double> horsesPosition;
    private final HashMap<Integer, Boolean> horsesFinished;
    private final int[] selectedHorses;
    
    private final double trackSize;
    private final int nHorsesToRun;
    
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
        }
        
        for (int i = 0; i < selectedHorses.length; i++){
            System.out.println("This horse has been selected: " + selectedHorses[i]);
        } 
        
        
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
        
        try{
            double start = 0;
            double end = this.horsesRunning.get(horseId).getStepSize();
            double random = new Random().nextDouble();
            double result = start + (random * (end - start));

            this.horsesPosition.put(horseId, this.horsesPosition.get(horseId)+result);

            if(this.horsesPosition.get(horseId) >= trackSize){
                this.horsesFinished.put(horseId, Boolean.TRUE);
            }
        }catch(Exception e){
        
        }
    }
    
    public boolean horseFinished(int horseId){
        return this.horsesFinished.get(horseId);
    }
    
    public int horsesFinished(){
        return 0;
    }
    
    public int getWinner(){
        return this.winner;
    }
    
    public int getNRunningHorses(){
        return this.nHorsesToRun;
    }
}
