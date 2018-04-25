/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GeneralRepository;

import entities.BrokerState;
import entities.HorseJockeyState;
import entities.SpectatorsState;

/**
 *
 * @author Daniela Simões, 76771
 */
public interface ILog {
    public void setSpectatorState(int id, SpectatorsState state, int raceNumber);
    public void setHorseJockeyState(int id, HorseJockeyState state, int raceNumber);
    public void setBrokerState(BrokerState state, int raceNumber);
    public void makeAMove(int raceNumber);
    public void setSpectatorAmount(int spectatorId, int amount);
}
