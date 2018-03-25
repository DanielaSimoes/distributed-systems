package entities;

/**
 * This file contains an enum with the lifecycle states of the Spectator.
 * @author Daniela Simões, 76771
 */
public enum SpectatorsState {
    /**
    * WAITING FOR A RACE TO START = WFARTS
    */
            
    WAITING_FOR_A_RACE_TO_START{
        @Override
        public String toString(){
            return "WFARTS";
        }
    },
    
    /**
    * APPRAISING THE HORSES = ATH
    */
            
    APPRAISING_THE_HORSES{
        @Override
        public String toString(){
            return "ATH";
        }
    },
    
    /**
    * PLACING A BET = PAB
    */
        
    PLACING_A_BET{
        @Override
        public String toString(){
            return "PAB";
        }
    },
    
    /**
    * WATCHING A RACE = WAR
    */
           
    WATCHING_A_RACE{
        @Override
        public String toString(){
            return "WAR";
        }
    },
    
    /**
    * COLLECTING THE GAINS = CTG
    */
            
    COLLECTING_THE_GAINS{
        @Override
        public String toString(){
            return "CTG";
        }
    },
    
    /**
    * CELEBRATING = C
    */
    
    CELEBRATING{
        @Override
        public String toString(){
            return "C";
        }
    },
}
