package GeneralRepository;

import communication.Proxy.ClientProxy;
import communication.message.Message;
import communication.message.MessageType;
import entities.BrokerState;
import entities.HorseJockeyState;
import entities.SpectatorsState;

/**
 * This class implements a log proxy.
 * @author Daniela Simões, 76771
 */
public class LogProxy extends ClientProxy implements ILog{
    
    /**
    * Constructor to log proxy.
    */
    public LogProxy(){
        super("Log");
    }
    
    /**
    * Method to set the spectator state.
    * @param id
    * @param state
    * @param raceNumber
    */
    @Override
    public void setSpectatorState(int id, SpectatorsState state, int raceNumber) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, id, state, raceNumber));
    }

    /**
    * Method to set the horse jockey state.
    * @param id
    * @param state
    * @param raceNumber
    */
    @Override
    public void setHorseJockeyState(int id, HorseJockeyState state, int raceNumber) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, id, state, raceNumber));
    }

    /**
    * Method to set the broker state.
    * @param state
    * @param raceNumber
    */
    @Override
    public void setBrokerState(BrokerState state, int raceNumber) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, state, raceNumber));
    }

    /**
    * Method to allow the horse to make a move.
    * @param raceNumber
    */
    @Override
    public void makeAMove(int raceNumber) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, raceNumber));
    }

    /**
    * Method to set spectator amount of money to bet.
    * @param spectatorId
    * @param amount
    */
    @Override
    public void setSpectatorAmount(int spectatorId, int amount) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, spectatorId, amount));
    }
    
}
