/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GeneralRepository;

import communication.Proxy.ServerInterface;
import communication.ServerChannel;
import communication.message.Message;
import communication.message.MessageException;
import communication.message.MessageType;
import java.net.SocketException;
import java.util.LinkedList;

/**
 *
 * @author Daniela
 */
public class RacesServer extends Races implements ServerInterface{
    
    private boolean serverEnded;
    private Bet response_bet;
    private int response_int;
    private double response_double;
    private boolean response_boolean;
    private Integer response_integer;
    private String name;
    
    
    public RacesServer() {
        super();
        this.name = "Races Server";
        this.serverEnded = false;
    }

    @Override
    public Message processAndReply(Message inMessage, ServerChannel scon) throws MessageException, SocketException {
        switch(inMessage.getType()){
            case TERMINATE:
                this.serverEnded = true;
                break;
            case chooseBet:
                response_bet = super.chooseBet(inMessage.getInteger1(), inMessage.getInteger2(), inMessage.getInteger3(), inMessage.getInteger4());
                return new Message(MessageType.ACK, response_bet);
            case horseHasBeenSelectedToRace:
                response_boolean = super.horseHasBeenSelectedToRace(inMessage.getInteger1(), inMessage.getInteger2(), inMessage.getInteger3());
                return new Message(MessageType.ACK, response_boolean);
            case setHorseJockeyStepSize:
                super.setHorseJockeyStepSize(inMessage.getInteger1(), inMessage.getInteger2());
                break;
            case getHorseJockeyStepSize:
                response_int = super.getHorseJockeyStepSize(inMessage.getInteger1());
                return new Message(MessageType.ACK, response_int);
            case areThereAnyWinners:
                response_boolean = super.areThereAnyWinners(inMessage.getInteger1());
                return new Message(MessageType.ACK, response_boolean);
            case haveIWon:
                response_boolean = super.haveIWon(inMessage.getInteger1(), inMessage.getInteger2());
                return new Message(MessageType.ACK, response_boolean);
            case getWinner:
                LinkedList<Integer> response6 = super.getWinner(inMessage.getInteger1());
                return new Message(MessageType.ACK, response6);
            case hasMoreRaces:
                response_boolean = super.hasMoreRaces();
                return new Message(MessageType.ACK, response_boolean);
            case makeAMove:
                super.makeAMove(inMessage.getInteger1(), inMessage.getInteger2());
                break;
            case getHorseIteration:
                response_int = super.getHorseIteration(inMessage.getInteger1(), inMessage.getInteger2());
                return new Message(MessageType.ACK, response_int);
            case getStandingPosition:
                response_int = super.getStandingPosition(inMessage.getInteger1(), inMessage.getInteger2());
                return new Message(MessageType.ACK, response_int);
            case nextMovingHorse:
                response_boolean = super.nextMovingHorse(inMessage.getInteger1(), inMessage.getInteger2());
                return new Message(MessageType.ACK, response_boolean);
            case horseFinished:
                response_boolean = super.horseFinished(inMessage.getInteger1(), inMessage.getInteger2());
                return new Message(MessageType.ACK, response_boolean);
            case horsesFinished:
                response_boolean = super.horsesFinished(inMessage.getInteger1());
                return new Message(MessageType.ACK, response_boolean);
            case getNRunningHorses:
                response_int = super.getNRunningHorses(inMessage.getInteger1());
                return new Message(MessageType.ACK, response_int);
            case getCurrentRaceDistance:
                response_int = super.getCurrentRaceDistance(inMessage.getInteger1());
                return new Message(MessageType.ACK, response_int);
            case getStartTheRace:
                response_boolean = super.getStartTheRace(inMessage.getInteger1());
                return new Message(MessageType.ACK, response_boolean);
            case setStartTheRace:
                super.setStartTheRace(inMessage.getBoolean(), inMessage.getInteger1());
                break;
            case getWakedHorsesToPaddock:
                response_int = super.getWakedHorsesToPaddock(inMessage.getInteger1());
                return new Message(MessageType.ACK, response_int);
            case addWakedHorsesToPaddock:
                super.addWakedHorsesToPaddock(inMessage.getInteger1());
                break;
            case getAnnouncedNextRace:
                response_boolean = super.getAnnouncedNextRace(inMessage.getInteger1());
                return new Message(MessageType.ACK, response_boolean);
            case setAnnouncedNextRace:
                super.setAnnouncedNextRace(inMessage.getBoolean(), inMessage.getInteger1());
                break;
            case allSpectatorsArrivedAtPaddock:
                response_boolean = super.allSpectatorsArrivedAtPaddock(inMessage.getInteger1());
                return new Message(MessageType.ACK, response_boolean);
            case addNSpectatorsArrivedAtPaddock:
                super.addNSpectatorsArrivedAtPaddock(inMessage.getInteger1());
                break;
            case allHorseJockeyLeftThePadock:
                response_boolean = super.allHorseJockeyLeftThePadock(inMessage.getInteger1());
                return new Message(MessageType.ACK, response_boolean);
            case addNHorseJockeyLeftThePadock:
                super.addNHorseJockeyLeftThePadock(inMessage.getInteger1());
                break;
            case setReportResults:
                super.setReportResults(inMessage.getBoolean(), inMessage.getInteger1());
                break;
            case getReportResults:
                response_boolean = super.getReportResults(inMessage.getInteger1());
                return new Message(MessageType.ACK, response_boolean);
            case setProceedToPaddock:
                super.setProceedToPaddock(inMessage.getBoolean(), inMessage.getInteger1());
                break;
            case getProceedToPaddock:
                response_boolean = super.getProceedToPaddock(inMessage.getInteger1());
                return new Message(MessageType.ACK, response_boolean);
            case allNHorsesInPaddock:
                response_boolean = super.allNHorsesInPaddock(inMessage.getInteger1());
                return new Message(MessageType.ACK, response_boolean);
            case addNHorsesInPaddock:
                super.addNHorsesInPaddock(inMessage.getInteger1());
                break;
            case waitAddedBet:
                response_integer = super.waitAddedBet(inMessage.getInteger1());
                return new Message(MessageType.ACK, response_integer);
            case allSpectatorsBettsAceppted:
                response_boolean = super.allSpectatorsBettsAceppted(inMessage.getInteger1());
                return new Message(MessageType.ACK, response_boolean);
            case addBetOfSpectator:
                super.addBetOfSpectator(inMessage.getBet(), inMessage.getInteger1(), inMessage.getInteger2());
                break;
            case allSpectatorsBetted:
                response_boolean = super.allSpectatorsBetted(inMessage.getInteger1());
                return new Message(MessageType.ACK, response_boolean);
            case waitAcceptedTheBet:
                super.waitAcceptedTheBet(inMessage.getInteger1(), inMessage.getInteger2());
                break;
            case acceptBet:
                super.acceptBet(inMessage.getInteger1(), inMessage.getInteger2());
                break;
            case poolWaitingToBePaidSpectators:
                response_integer = super.poolWaitingToBePaidSpectators(inMessage.getInteger1());
                return new Message(MessageType.ACK, response_integer);
            case addWaitingToBePaidSpectator:
                super.addWaitingToBePaidSpectator(inMessage.getInteger1(), inMessage.getInteger2());
                break;
            case allSpectatorsPaid:
                response_boolean = super.allSpectatorsPaid(inMessage.getInteger1());
                return new Message(MessageType.ACK, response_boolean);
            case getPaidSpectators:
                response_integer = super.getPaidSpectators(inMessage.getInteger1(), inMessage.getInteger2());
                return new Message(MessageType.ACK, response_integer);
            case setPaidSpectators:
                super.setPaidSpectators(inMessage.getInteger1(), inMessage.getBoolean(), inMessage.getInteger2());
                break;
            case getSpectatorBet:
                response_bet = super.getSpectatorBet(inMessage.getInteger1(), inMessage.getInteger2());
                return new Message(MessageType.ACK, response_bet);
            case getHorseOdd:
                response_double = super.getHorseOdd(inMessage.getInteger1(), inMessage.getInteger2());
                return new Message(MessageType.ACK, response_double);
            case getHorsePosition:
                response_int = super.getHorsePosition(inMessage.getInteger1(), inMessage.getInteger2());
                return new Message(MessageType.ACK, response_int);
        }
        
        return new Message(MessageType.ACK);
    }

    @Override
    public boolean serviceEnded() {
        return serverEnded;
    }
    
    @Override
    public String serviceName() {
        return this.name;
    }
}
