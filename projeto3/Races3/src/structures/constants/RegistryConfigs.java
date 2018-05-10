package structures.constants;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class implements the constants related with Registry.
 * @author Daniela Simões, 76771
 */
public class RegistryConfigs {
    
    /**
     * Bash property of the file.
     */
    private Properties prop;
    
    /**
     * RegisterHandler name entry on the registry.
     */
    public static String registerHandler = "RegisterHandler";
    
    /**
     * Log name entry on the registry.
     */
    public static String logNameEntry = "LogInt";

    /**
     * Paddock name entry on the registry.
     */
    public static String paddockNameEntry = "PaddockInt";

    /**
     * Racing Track name entry on the registry.
     */
    public static String racingTrackNameEntry = "racingTrackInt";

    /**
     * Stable name entry on the registry.
     */
    public static String stableNameEntry = "StableInt";
    
    /**
     * Betting Centre name entry on the registry.
     */
    public static String bettingCentreNameEntry = "BettingCentreInt";
    
    /**
     * Control Centre name entry on the registry.
     */
    public static String controlCentreNameEntry = "ControlCentreInt";
    
    /**
     * Constructor that receives the file with the configurations.
     * @param filename path for the configuration file
     */
    public RegistryConfigs(String filename) {
        prop = new Properties();
        InputStream in = null;
        try {
            in = new FileInputStream(filename);
            prop.load(in);
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(RegistryConfigs.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    Logger.getLogger(RegistryConfigs.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }
        }
    }
    
    /** 
     * Loads the parameter REGISTER_HOST from the configuration file.
     * @return parameter value
     */
    public String registryHost() {
        return prop.getProperty("registry_host");
    }
    
    /** 
     * Loads the parameter REGISTER_PORT from the configuration file.
     * @return parameter value
     */
    public int registryPort() {
        return Integer.parseInt(prop.getProperty("registry_port"));
    }
    
    /** 
     * Loads the parameter REGISTER_OBJECT_PORT from the configuration file.
     * @return parameter value
     */
    public int objectPort() {
        return Integer.parseInt(prop.getProperty("registryobject"));
    }
    
    /** 
     * Loads the parameter LOG_PORT from the configuration file.
     * @return parameter value
     */
    public int logPort() {
        return Integer.parseInt(prop.getProperty("log_port"));
    }
    
    /** 
     * Loads the parameter PADDOCK_PORT from the configuration file.
     * @return parameter value
     */
    public int paddockPort() {
        return Integer.parseInt(prop.getProperty("paddock_port"));
    }
    
    /** 
     * Loads the parameter STABLE_PORT from the configuration file.
     * @return parameter value
     */
    public int stablePort() {
        return Integer.parseInt(prop.getProperty("stable_port"));
    }
    
    /** 
     * Loads the parameter RACINGTRACK_SITE_PORT from the configuration file.
     * @return parameter value
     */
    public int racingTrackPort() {
        return Integer.parseInt(prop.getProperty("racingTrack_port"));
    }
    
    /** 
     * Loads the parameter BETTING_CENTRE_PORT from the configuration file.
     * @return parameter value
     */
    public int bettingCentrePort() {
        return Integer.parseInt(prop.getProperty("bettingCentre_port"));
    }
    
    /** 
     * Loads the parameter CONTROL_CENTRE_PORT from the configuration file.
     * @return parameter value
     */
    public int controlCentrePort() {
        return Integer.parseInt(prop.getProperty("controlCentre_port"));
    }
}
