package GeneralRepository;

import entities.BrokerState;
import entities.HorseJockeyState;
import entities.SpectatorsState;

/**
 * This file contains the log interface.
 * @author Daniela Simões, 76771
 */
public interface ILog {
    public void setSpectatorState(int id, SpectatorsState state, int raceNumber);
    public void setHorseJockeyState(int id, HorseJockeyState state, int raceNumber);
    public void setBrokerState(BrokerState state, int raceNumber);
    public void makeAMove(int raceNumber);
    public void setSpectatorAmount(int spectatorId, int amount);
}
