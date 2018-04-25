package GeneralRepository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import entities.HorseJockeyState;
import entities.BrokerState;
import entities.SpectatorsState;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.sun.javafx.binding.Logging;
import communication.Proxy.Proxy;
import communication.message.Message;
import communication.message.MessageType;
import java.util.HashMap;
import settings.NodeSetts;
import settings.NodeSettsProxy;

/**
 * This file contains the code to generate a log file.
 * @author Daniela Simões, 76771
 */
public class Log implements ILog{
    
    private final RacesProxy races = new RacesProxy();
    
    /**
     * This will be a singleton
     */
    private static Log instance = null;
    
    /**
     *  File where the log will be saved
     */
    private final File log;
    
    private static PrintWriter pw;
    private boolean event_opened = false;
    
    private final int[] spectatorAmounts;
    private BrokerState brokerState;
    private final HashMap<Integer, HorseJockeyState> horseJockeysState;
    private final HashMap<Integer, SpectatorsState> spectatorsState;

    // number of terminates 
    private int nTerminates = 0;
    
    public Log(String filename){
        if(filename.length()==0){
            Date today = Calendar.getInstance().getTime();
            SimpleDateFormat date = new SimpleDateFormat("yyyyMMddhhmmss");
            filename = "AfternoonAtTheRaces.log";
        }
        this.log = new File(filename);
        this.spectatorAmounts = new int[NodeSetts.N_OF_SPECTATORS];
        this.horseJockeysState = new HashMap<>();
        this.spectatorsState = new HashMap<>();
        
        for (int i=0; i<NodeSetts.N_OF_SPECTATORS; i++){
            this.spectatorAmounts[i] = 0;
        }
    }
    
    static{
        instance = new Log("");
        instance.writeInit();
    }
    
    /**
     *
     * @return
     */
    public static Log getInstance(){
        return instance;
    }
    
    private void writeInit(){
        try{
                        
            pw = new PrintWriter(log);
            pw.println("         AFTERNOON AT THE RACE TRACK - Description of the internal state of the problem");
            
            pw.println("MAN/BRK           SPECTATOR/BETTER              HORSE/JOCKEY PAIR at Race RN");
            
            String head = "  Stat";
            
            for(int i=0; i<NodeSetts.N_OF_SPECTATORS; i++){
                head += " St" + Integer.toString(i+1) + "  Am" + Integer.toString(i+1) + " ";
            }
            
            head += 0+1;
            
            for(int i=0; i<NodeSetts.N_OF_HORSES_TO_RUN; i++){
                head += "  St" + Integer.toString(i+1) + " Len" + Integer.toString(i+1);
            }
            
            head += "\n\t\t\t\t\t\t Race RN Status \n";
            
            head += "  RN Dist ";
            
            for(int i=0; i<NodeSetts.N_OF_SPECTATORS; i++){
                head += " BS" + Integer.toString(i+1) + "  BA" + Integer.toString(i+1);
            }
            
            head += " ";
            
            for(int i=0; i<NodeSetts.N_OF_HORSES_TO_RUN; i++){
                head += " Od" + Integer.toString(i+1) + "  N" + Integer.toString(i+1)  + "  Ps" + Integer.toString(i+1)  + "  SD" + Integer.toString(i+1);
            }
            
            head += " ";
            
            pw.println(head);
          
            pw.flush();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Logging.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     *
     * @param raceNumber
     */
    public void writeLineRace(int raceNumber){
                
        String head = String.format("   %d  %2d  ", raceNumber+1, races.getCurrentRaceDistance(raceNumber));

        for(int i=0; i<NodeSetts.N_OF_SPECTATORS; i++){
            try{
                head += String.format("  %d  %4d", this.races.getSpectatorBet(i, raceNumber).getHorseId(), this.races.getSpectatorBet(i,raceNumber).getAmount());
            }catch(java.lang.NullPointerException e){
                head += " --- ----";
            }
        }

        head += " ";

        for(int i=0; i<NodeSetts.N_OF_HORSES; i++){
            if(this.races.horseHasBeenSelectedToRace(i, -1, raceNumber)){
                try{
                    head += String.format(" %2.1f %2d   %2d    %d ", this.races.getHorseOdd(i, raceNumber), this.races.getHorseIteration(i, raceNumber), this.races.getHorsePosition(i, raceNumber), this.races.getStandingPosition(i, raceNumber));
                }catch(java.lang.NullPointerException e){
                    head += " ---- --  --    - ";
                }
            }
        }

        head += " ";

        pw.println(head);

        pw.flush();
    }
    
    /**
     *
     * @param raceNumber
     */
    public void writeLineStatus(int raceNumber){
        String head = "  " + this.brokerState + " ";

        for(int i=0; i<NodeSetts.N_OF_SPECTATORS; i++){
            head += " " + spectatorsState.get(i) + "  " + String.format("%3d", this.spectatorAmounts[i]) + " ";
        }

        head += (raceNumber+1);

        for(int i=0; i<NodeSetts.N_OF_HORSES; i++){
            if(this.races.horseHasBeenSelectedToRace(i, -1, raceNumber)){
                head += "  " + horseJockeysState.get(i) + " " + String.format("%3d", this.races.getHorseJockeyStepSize(i));
            }
        }

        if(head.contains("null")){
            return;
        }
        
        pw.println(head);

        pw.flush();
        
        if(this.event_opened){
            this.writeLineRace(raceNumber);
        }
    }
    
    /**
     *
     * @param spectatorId
     * @param amount
     */
    @Override
    public void setSpectatorAmount(int spectatorId, int amount){
        this.spectatorAmounts[spectatorId] = amount;
    }

    /**
     *
     * @param state
     * @param raceNumber
     */
    @Override
    public void setBrokerState(BrokerState state, int raceNumber){
        this.brokerState = state;
        this.writeLineStatus(raceNumber);
        
        if(state==BrokerState.OPENING_THE_EVENT){
            event_opened = true;
        }
    }
    

    /**
     *
     * @param raceNumber
     */
    @Override
    public void makeAMove(int raceNumber){
        this.writeLineStatus(raceNumber);
    }
    
     /**
    *
    * Method to set the state of a HorseJockey.
    * @param id
    * @param state The state to be assigned.
     * @param raceNumber
    */
    public void setHorseJockeyState(int id, HorseJockeyState state, int raceNumber){
        if(this.horseJockeysState.containsKey(id)){
            this.horseJockeysState.replace(id, state);
        }else{
            this.horseJockeysState.put(id, state);
        }
        this.writeLineStatus(raceNumber);
    }
    
     /**
    *
    * Method to get the state of a HorseJockey.
     * @param id
     * @return 
    */
    public HorseJockeyState getHorseJockeyState(int id){
        return this.horseJockeysState.get(id);
    }

    /**
    *
    * Method to set the state of a Spectator.
     * @param id
    * @param state The state to be assigned.
     * @param raceNumber
    */
    @Override
    public void setSpectatorState(int id, SpectatorsState state, int raceNumber){
        if(this.spectatorsState.containsKey(id)){
            this.spectatorsState.replace(id, state);
        }else{
            this.spectatorsState.put(id, state);
        }
        this.writeLineStatus(raceNumber);
    }
    
    /**
    *
    * Method to get the state of a Spectator.
     * @param id
     * @return 
    */
    public SpectatorsState getSpectatorsState(int id){
        return this.spectatorsState.get(id);
    }
    
    public boolean terminateServers(){
        nTerminates++;
        
        if(nTerminates==3){
            // BettingCentre
            this.sendTerminate("BettingCentre");

            // RacingTrack
            this.sendTerminate("RacingTrack");

            // ControlCentre
            this.sendTerminate("ControlCentre");

            // Paddock
            this.sendTerminate("Paddock");

            // Stable
            this.sendTerminate("Stable");

            // Races
            this.sendTerminate("Races");

            // NodeSetts
            this.sendTerminate("NodeSetts");
            
            return true;
        }
        
        return false;
    }
    
    private void sendTerminate(String serverName){
        NodeSettsProxy proxy = new NodeSettsProxy(); 
        
        Proxy.connect(proxy.SERVER_HOSTS().get(serverName), 
                proxy.SERVER_PORTS().get(serverName), 
                new Message(MessageType.TERMINATE));
    }
    
}
