package shared;

/**
 * This file contains the Interface implemented by the shared memory region Racing Track.
 * @author Daniela Simões, 76771
 */
public interface IRacingTrack {
    public void startTheRace(int raceNumber);
    public void proceedToStartLine(int raceNumber, int horseId);
    public boolean hasFinishLineBeenCrossed(int horseId, int raceNumber);
    public void makeAMove(int raceNumber, int horseId);
}
