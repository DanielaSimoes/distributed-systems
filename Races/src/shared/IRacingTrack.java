package shared;

/**
 * This file contains the Interface implemented by the shared memory region Racing Track.
 * @author Daniela Simões, 76771
 */
public interface IRacingTrack {
    public void startTheRace();
    public void proceedToStartLine();
    public boolean hasFinishLineBeenCrossed(int horseId);
    public void makeAMove();
}
