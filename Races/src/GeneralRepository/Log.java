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
    /*
    entities.BrokerState BrokerState;
    entities.SpectatorsState SpectatorsState[] = new entities.SpectatorsState[4];
    int SpectatorAmmount[] = new int[4];
    int raceNumber;
    entities.HorseJockeyState HorseJockeyState[] = new entities.HorseJockeyState[4];
    int lengthStep[] = new int[4];
    int raceNumber2;
    int distance;
    double BS[] = new double[4];
    double BA[] = new double[4];
    double ODD[] = new double[4];
    int N[] = new int[4];
    double Ps[] = new double[4];
    int SD[] = new int[4];
    */
    
    
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
            /*
            String head = "Ref  Coa 1";
            for(int i=1; i<=Match.N_CONTESTANTS/2; i++){
                head += " Cont " + Integer.toString(i);
            }
            head += " Coa 2";
            for(int i=1; i<=Match.N_CONTESTANTS/2; i++){
                head += " Cont " + Integer.toString(i);
            }
            head += "       Trial";
            pw.println(head);
            
            head = "Sta  Stat";
            for(int i=1; i<=Match.N_CONTESTANTS/2; i++){
                head += " Sta SG";
            }
            head += "  Stat";
            for(int i=1; i<=Match.N_CONTESTANTS/2; i++){
                head += " Sta SG";
            }
            head += " 3 2 1 . 1 2 3 NB PS";
            pw.println(head);
            */
            pw.flush();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Logging.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setHorseJockeyState(int id, entities.HorseJockeyState state){
        
    }

    public void setBrokerState(entities.HorseJockeyState state){
        
    }
    
    public void setSpectatorState(entities.SpectatorsState state){
        
    }

}
