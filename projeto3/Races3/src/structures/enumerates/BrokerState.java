package structures.enumerates;

/**
 * This file contains an enum with the lifecycle states of the Broker.
 * @author Daniela Simes, 76771
 */
 
public enum BrokerState {
    /**
    * OPENING THE EVENT = OTE
    */

    OPENING_THE_EVENT{
        @Override
        public String toString(){
            return "OTE";
        }
    },
    
    /**
    * ANNOUNCING NEXT RACE = ANR
    */
            
    ANNOUNCING_NEXT_RACE{
        @Override
        public String toString(){
            return "ANR";
        }
    },
    
    /**
    * WAITING FOR BETS = WFB
    */
            
    WAITING_FOR_BETS{
        @Override
        public String toString(){
            return "WFB";
        }
    },
    
    /**
    * SUPERVISING THE RACE = STR
    */
            
    SUPERVISING_THE_RACE{
        @Override
        public String toString(){
            return "STR";
        }
    },
    
    /**
    * SETTING ACCOUNTS = SA
    */
            
    SETTLING_ACCOUNTS{
        @Override
        public String toString(){
            return "SEA";
        }
    },
    
    /**
    * PLAYING HOST AT THE BAR = PHATB
    */
        
    PLAYING_HOST_AT_THE_BAR{
        @Override
        public String toString(){
            return "PHB";
        }
    },
}
