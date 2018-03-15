package entities;

/**
 *
 * @author Daniela
 */
public enum HorseJockeyState {
    
    /*
        AT THE STABLE = ATS
    */
    
    AT_THE_STABLE{
        @Override
        public String toString(){
            return "ATS";
        }
    },
    
    /*
        AT THE PADDOCK = ATP
    */
    
    AT_THE_PADDOCK{
        @Override
        public String toString(){
            return "ATP";
        }
    },
    
    /*
        AT THE START LINE = ATSL
    */
    
    AT_THE_START_LINE{
        @Override
        public String toString(){
            return "ATSL";
        }
    },
    
    /*
        RUNNNING = R
    */
    
    RUNNNING{
        @Override
        public String toString(){
            return "R";
        }
    },
    
    /*
        AT THE FINISH LINE = ATFL
    */
    
    AT_THE_FINISH_LINE{
        @Override
        public String toString(){
            return "ATFL";
        }
    },
}
