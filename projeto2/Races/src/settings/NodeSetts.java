/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package settings;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.JSONException;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Daniela Simões, 76771
 */
public class NodeSetts {
    protected static HashMap<String, Integer> SERVER_PORTS;
    protected static HashMap<String, String> SERVER_HOSTS;
    
    protected static boolean DEBUG = false;
    
    /**
     * Number of races.
     */
    public static final int N_OF_RACES = 10;

    /**
     * Number of horses.
     */
    public static final int N_OF_HORSES = 8;
    
    /**
     * Number of horses in the race.
     */
    public static final int N_OF_HORSES_TO_RUN = 4;

    /**
     * Number os spectators.
     */
    public static final int N_OF_SPECTATORS = 4;

    /**
     * Size of racing track.
     */
    public static final int SIZE_OF_RACING_TRACK = 20;

    /**
     * Horse maxium step size.
     */
    public static final int HORSE_MAX_STEP_SIZE = 8;

    /**
     * Maximum ammount each spectator can bet.
     */
    public static final int MAX_SPECTATOR_BET = 2000;
    
    /**
     * Node settings, it will read from the jsonfilepath the JSON with the hosts
     * @param jsonfilepath
     */
    public NodeSetts(String jsonfilepath){
        JSONParser parser = new JSONParser();
        
        try {     
            Object obj = parser.parse(new FileReader(jsonfilepath));

            JSONArray json =  (JSONArray) obj;
            
            SERVER_HOSTS = JSONUtils.jsonToHashString(json);
        } catch (JSONException ex) {
            Logger.getLogger(NodeSetts.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NodeSetts.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | org.json.simple.parser.ParseException ex) {
            Logger.getLogger(NodeSetts.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // 22440 - 22449
        SERVER_PORTS = new HashMap<>();
        SERVER_PORTS.put("BettingCentre", 22440);
        SERVER_PORTS.put("Log", 22441);
        SERVER_PORTS.put("RacingTrack", 22442);
        SERVER_PORTS.put("ControlCentre", 22443);
        SERVER_PORTS.put("Paddock", 22444);
        SERVER_PORTS.put("Stable", 22448);
        SERVER_PORTS.put("Races", 2246);
        SERVER_PORTS.put("NodeSetts", 2247);
        
    }
}
