package structures.enumerates;

/**
 * This file contains an enum with the lifecycle states of the HorseJockey.
 * @author Daniela Simes, 76771
 */
public enum HorseJockeyState {
    
    /**
    * AT THE STABLE = ATS
    */
            
    AT_THE_STABLE{
        @Override
        public String toString(){
            return "ATS";
        }
    },
    
    /**
    * AT THE PADDOCK = ATP
    */
           
    AT_THE_PADDOCK{
        @Override
        public String toString(){
            return "ATP";
        }
    },
    
    /**
    * AT THE START LINE = ATSL
    */
            
    AT_THE_START_LINE{
        @Override
        public String toString(){
            return "ASL";
        }
    },
    
    /**
    * RUNNNING = R
    */
            
    RUNNNING{
        @Override
        public String toString(){
            return "RNN";
        }
    },
    
    /**
    * AT THE FINISH LINE = ATFL
    */
            
    AT_THE_FINISH_LINE{
        @Override
        public String toString(){
            return "AFL";
        }
    },
}
