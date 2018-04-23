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
    
    
    public RacesServer() {
        super();
        this.serverEnded = false;
    }

    @Override
    public Message processAndReply(Message inMessage, ServerChannel scon) throws MessageException, SocketException {
        switch(inMessage.getType()){
            case TERMINATE:
                this.serverEnded = true;
            case chooseBet:
                response_bet = super.chooseBet(inMessage.getInteger());
                return new Message(MessageType.ACK, response_bet);
            case horseHasBeenSelectedToRace:
                response_boolean = super.horseHasBeenSelectedToRace(inMessage.getInteger(), inMessage.getInteger(), inMessage.getInteger());
                return new Message(MessageType.ACK, response_boolean);
            case setHorseJockeyStepSize:
                super.setHorseJockeyStepSize(inMessage.getInteger(), inMessage.getInteger());
                break;
            case getHorseJockeyStepSize:
                response_int = super.getHorseJockeyStepSize(inMessage.getInteger());
                return new Message(MessageType.ACK, response_int);
            case areThereAnyWinners:
                response_boolean = super.areThereAnyWinners(inMessage.getInteger());
                return new Message(MessageType.ACK, response_boolean);
            case haveIWon:
                response_boolean = super.haveIWon(inMessage.getInteger());
                return new Message(MessageType.ACK, response_boolean);
            case getWinner:
                LinkedList<Integer> response6 = super.getWinner(inMessage.getInteger());
                return new Message(MessageType.ACK, response6);
            case hasMoreRaces:
                response_boolean = super.hasMoreRaces();
                return new Message(MessageType.ACK, response_boolean);
            case makeAMove:
                super.makeAMove(inMessage.getInteger(), inMessage.getInteger());
                break;
            case getHorseIteration:
                response_int = super.getHorseIteration(inMessage.getInteger(), inMessage.getInteger());
                return new Message(MessageType.ACK, response_int);
            case getStandingPosition:
                response_int = super.getStandingPosition(inMessage.getInteger(), inMessage.getInteger());
                return new Message(MessageType.ACK, response_int);
            case nextMovingHorse:
                response_boolean = super.nextMovingHorse(inMessage.getInteger(), inMessage.getInteger());
                return new Message(MessageType.ACK, response_boolean);
            case horseFinished:
                response_boolean = super.horseFinished(inMessage.getInteger(), inMessage.getInteger());
                return new Message(MessageType.ACK, response_boolean);
            case getNRunningHorses:
                response_int = super.getNRunningHorses(inMessage.getInteger());
                return new Message(MessageType.ACK, response_int);
            case getCurrentRaceDistance:
                response_int = super.getCurrentRaceDistance(inMessage.getInteger());
                return new Message(MessageType.ACK, response_int);
            case getStartTheRace:
                response_boolean = super.getStartTheRace(inMessage.getInteger());
                return new Message(MessageType.ACK, response_boolean);
            case setStartTheRace:
                super.setStartTheRace(inMessage.getBoolean(), inMessage.getInteger());
                break;
            case getWakedHorsesToPaddock:
                response_int = super.getWakedHorsesToPaddock(inMessage.getInteger());
                return new Message(MessageType.ACK, response_int);
            case addWakedHorsesToPaddock:
                super.addWakedHorsesToPaddock(inMessage.getInteger());
                break;
            case getAnnouncedNextRace:
                response_boolean = super.getAnnouncedNextRace(inMessage.getInteger());
                return new Message(MessageType.ACK, response_boolean);
            case setAnnouncedNextRace:
                super.setAnnouncedNextRace(inMessage.getBoolean(), inMessage.getInteger());
                break;
            case allSpectatorsArrivedAtPaddock:
                response_boolean = super.allSpectatorsArrivedAtPaddock(inMessage.getInteger());
                return new Message(MessageType.ACK, response_boolean);
            case addNSpectatorsArrivedAtPaddock:
                super.addNSpectatorsArrivedAtPaddock(inMessage.getInteger());
                break;
            case allHorseJockeyLeftThePadock:
                response_boolean = super.allHorseJockeyLeftThePadock(inMessage.getInteger());
                return new Message(MessageType.ACK, response_boolean);
            case addNHorseJockeyLeftThePadock:
                super.addNHorseJockeyLeftThePadock(inMessage.getInteger());
                break;
            case setReportResults:
                super.setReportResults(inMessage.getBoolean(), inMessage.getInteger());
                break;
            case getReportResults:
                response_boolean = super.getReportResults(inMessage.getInteger());
                return new Message(MessageType.ACK, response_boolean);
            case setProceedToPaddock:
                super.setProceedToPaddock(inMessage.getBoolean(), inMessage.getInteger());
                break;
            case getProceedToPaddock:
                response_boolean = super.getProceedToPaddock(inMessage.getInteger());
                return new Message(MessageType.ACK, response_boolean);
            case allNHorsesInPaddock:
                response_boolean = super.allNHorsesInPaddock(inMessage.getInteger());
                return new Message(MessageType.ACK, response_boolean);
            case addNHorsesInPaddock:
                super.addNHorsesInPaddock(inMessage.getInteger());
                break;
            case waitAddedBet:
                response_integer = super.waitAddedBet(inMessage.getInteger());
                return new Message(MessageType.ACK, response_integer);
            case allSpectatorsBettsAceppted:
                response_boolean = super.allSpectatorsBettsAceppted(inMessage.getInteger());
                return new Message(MessageType.ACK, response_boolean);
            case addBetOfSpectator:
                super.addBetOfSpectator(inMessage.getBet(), inMessage.getInteger());
                break;
            case allSpectatorsBetted:
                response_boolean = super.allSpectatorsBetted(inMessage.getInteger());
                return new Message(MessageType.ACK, response_boolean);
            case waitAcceptedTheBet:
                super.waitAcceptedTheBet(inMessage.getInteger());
                break;
            case acceptBet:
                super.acceptBet(inMessage.getInteger(), inMessage.getInteger());
                break;
            case poolWaitingToBePaidSpectators:
                response_integer = super.poolWaitingToBePaidSpectators(inMessage.getInteger());
                return new Message(MessageType.ACK, response_integer);
            case addWaitingToBePaidSpectator:
                super.addWaitingToBePaidSpectator(inMessage.getInteger(), inMessage.getInteger());
                break;
            case allSpectatorsPaid:
                response_boolean = super.allSpectatorsPaid(inMessage.getInteger());
                return new Message(MessageType.ACK, response_boolean);
            case getPaidSpectators:
                response_boolean = super.getPaidSpectators(inMessage.getInteger(), inMessage.getInteger());
                return new Message(MessageType.ACK, response_boolean);
            case setPaidSpectators:
                super.setPaidSpectators(inMessage.getInteger(), inMessage.getBoolean(), inMessage.getInteger());
                break;
            case getSpectatorBet:
                response_bet = super.getSpectatorBet(inMessage.getInteger(), inMessage.getInteger());
                return new Message(MessageType.ACK, response_bet);
            case getHorseOdd:
                response_double = super.getHorseOdd(inMessage.getInteger(), inMessage.getInteger());
                return new Message(MessageType.ACK, response_double);
            case getHorsePosition:
                response_int = super.getHorsePosition(inMessage.getInteger(), inMessage.getInteger());
                return new Message(MessageType.ACK, response_int);
        }
        
        return new Message(MessageType.ACK);
    }

    @Override
    public boolean serviceEnded() {
        return serverEnded;
    }
    
}
