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

/**
 *
 * @author Daniela
 */
public class Log {
    
    private final Races races = Races.getInstace();
    
    /**
     * This will be a singleton
     */
    private static Log instance = null;
    
    /**
     *  File where the log will be saved
     */
    private final File log;
    
    private static PrintWriter pw;
    
    private Log(String filename){
        if(filename.length()==0){
            Date today = Calendar.getInstance().getTime();
            SimpleDateFormat date = new SimpleDateFormat("yyyyMMddhhmmss");
            filename = "AfternoonAtTheRaces_" + date.format(today) + ".log";
        }
        this.log = new File(filename);
    }
    
    static{
        instance = new Log("");
        instance.writeInit();
    }
    
    public synchronized static Log getInstance(){
        return instance;
    }
    
    private void writeInit(){
        try{
                        
            pw = new PrintWriter(log);
            pw.println("         AFTERNOON AT THE RACE TRACK - Description of the internal state of the problem");
            
            pw.println("MAN/BRK           SPECTATOR/BETTER              HORSE/JOCKEY PAIR at Race RN");
            
            String head = "  Stat";
            
            for(int i=1; i<=Races.N_OF_SPECTATORS; i++){
                head += " St" + Integer.toString(i) + "  Am" + Integer.toString(i) + " ";
            }
            
            head += 0+1;
            
            for(int i=1; i<=Races.N_OF_HORSES; i++){
                head += "  St" + Integer.toString(i) + " Len" + Integer.toString(i);
            }
            
            //head += "                                        Race R" + this.races.getRaceNumber() + " Status";
            
            pw.println(head);
            
          
            pw.flush();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Logging.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setHorseJockeyState(int id, HorseJockeyState state){
        this.races.setHorseJockeyState(id, state);
    }

    public void setBrokerState(BrokerState state){
        this.races.setBrokerState(state);
    }
    
    public void setSpectatorState(int id, SpectatorsState state){
        this.races.setSpectatorState(id, state);
    }

}
