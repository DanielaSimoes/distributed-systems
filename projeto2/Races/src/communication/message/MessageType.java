/*
 * This file describes the message types in the system.
 */
package communication.message;

/**
 * This enum describes the messages' types.
 * @author Daniela Simões, 76771
 */
public enum MessageType {
    /* Broker messages */
    summonHorsesToPaddock, acceptTheBets, startTheRace, reportResults, areThereAnyWinners, honourTheBets, entertainTheGuests,
    /* Spectators messages */
    waitForNextRace, goCheckHorses, placeABet, goWatchTheRace, haveIWon, goCollectTheGains, relaxABit,
    /* HorseJockey messages */
    proceedToStable, proceedToPaddock, proceedToStartLine, makeAMove, hasFinishLineBeenCrossed,
    /* To compile */
    ACK, TERMINATE, SERVER_PORTS, SERVER_HOSTS,
    /* Vars */
    N_OF_HORSES, HORSE_MAX_STEP_SIZE, N_OF_SPECTATORS, MAX_SPECTATOR_BET,
    /* Log messages */
    setSpectatorAmount, setBrokerState, setHorseJockeyState, setSpectatorState
}
