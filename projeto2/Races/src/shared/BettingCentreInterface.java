package shared;

import GeneralRepository.Bet;
import GeneralRepository.RacesStub;
import communication.stub.ServerInterface;
import communication.ServerChannel;
import communication.message.Message;
import communication.message.MessageException;
import communication.message.MessageType;
import java.net.SocketException;

/**
 * This class implements the server of betting centre.
 * @author Daniela Simões, 76771
 */
public class BettingCentreInterface extends BettingCentre implements ServerInterface {
    
    private boolean serverEnded;
    private final String name;
    
    /**
    * BettingCentreServer constructor.
    * @param races
    */
    public BettingCentreInterface(RacesStub races) {
        super(races);
        this.name = "Betting Centre Server";
        this.serverEnded = false;
    }

    /**
    * Method to process and reply.
    * @throws communication.message.MessageException
    * @throws java.net.SocketException
    */
    @Override
    public Message processAndReply(Message inMessage, ServerChannel scon) throws MessageException, SocketException {
        switch(inMessage.getType()){
            case TERMINATE:
                this.serverEnded = true;
                break;
            case acceptTheBets:
                super.acceptTheBets(inMessage.getInteger1());
                break;
            case honourTheBets:
                super.honourTheBets(inMessage.getInteger1());
                break;
            case areThereAnyWinners:
                boolean response = super.areThereAnyWinners(inMessage.getInteger1());
                return new Message(MessageType.ACK, response);
            case placeABet:
                Bet bet = super.placeABet(inMessage.getInteger1(), inMessage.getInteger2(), inMessage.getInteger3(), inMessage.getInteger4());
                return new Message(MessageType.ACK, bet);
            case goCollectTheGains:
                int response_integer = super.goCollectTheGains(inMessage.getInteger1(), inMessage.getInteger2());
                return new Message(MessageType.ACK, response_integer);
        }
        
        return new Message(MessageType.ACK);
    }

    /**
    * Method to return the flag of the service ended.
    * @return
    */
    @Override
    public boolean serviceEnded() {
        return serverEnded;
    }
    
    /**
    * Method to return the service name.
    * @return
    */
    @Override
    public String serviceName() {
        return this.name;
    }
}
