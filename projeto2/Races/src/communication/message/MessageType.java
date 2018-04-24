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
    N_OF_HORSES, HORSE_MAX_STEP_SIZE, N_OF_SPECTATORS, MAX_SPECTATOR_BET, SIZE_OF_RACING_TRACK, N_OF_HORSES_TO_RUN, N_OF_RACES,
    /* Log messages */
    setSpectatorAmount, setBrokerState, setHorseJockeyState, setSpectatorState,
    /* Races messages */
    chooseBet, horseHasBeenSelectedToRace, setHorseJockeyStepSize, getHorseJockeyStepSize, getWinner, hasMoreRaces, getHorseIteration,
    getStandingPosition, nextMovingHorse, horseFinished, horsesFinished, getNRunningHorses, getCurrentRaceDistance, getStartTheRace,
    setStartTheRace, getWakedHorsesToPaddock, addWakedHorsesToPaddock, getAnnouncedNextRace, setAnnouncedNextRace, allSpectatorsArrivedAtPaddock,
    addNSpectatorsArrivedAtPaddock, allHorseJockeyLeftThePadock, addNHorseJockeyLeftThePadock, setReportResults, getReportResults,
    setProceedToPaddock, getProceedToPaddock, allNHorsesInPaddock, addNHorsesInPaddock, waitAddedBet, allSpectatorsBettsAceppted,
    addBetOfSpectator, allSpectatorsBetted, waitAcceptedTheBet, acceptBet, poolWaitingToBePaidSpectators, addWaitingToBePaidSpectator,
    allSpectatorsPaid, getPaidSpectators, setPaidSpectators, getSpectatorBet, getHorseOdd, getHorsePosition
}
