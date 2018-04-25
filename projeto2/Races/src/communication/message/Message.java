/*
 * This file contains the messages constructors.
 */
package communication.message;
import GeneralRepository.Bet;
import entities.BrokerState;
import entities.HorseJockeyState;
import entities.SpectatorsState;
import java.util.HashMap;
import java.util.LinkedList;
import java.io.Serializable;

/**
 *
 * @author Daniela
 */
public class Message implements Serializable {
    
    private static final long serialVersionUID = 1001L;
    private boolean message_b;
    private double message_d;
    private int message_i;
    private int message_i2;
    private int message_i3;
    private int message_i4;
    private Integer message_integer;
    private MessageType type;
    private HorseJockeyState horseJockeyState;
    private SpectatorsState spectatorsState;
    private BrokerState brokerState;
    private HashMap<?, ?> map = null;
    private Bet bet;
    private LinkedList<Integer> linked;

        
    public Message(MessageType type, int message){
        this.message_i = message;
        this.type = type; 
    }
    
    public Message(MessageType type, Integer message){
        this.message_integer = message;
        this.type = type; 
    }
    
    /**
     *
     * @param type
     * @param message
     * @param message2
     */
    public Message(MessageType type, int message, int message2){
        this.message_i = message;
        this.message_i2 = message2;
        this.type = type; 
    }
    
    public Message(MessageType type, int message, boolean message2){
        this.message_i = message;
        this.message_b = message2;
        this.type = type; 
    }
    
    public Message(MessageType type, int message, int message2, int message3){
        this.message_i = message;
        this.message_i2 = message2;
        this.message_i3 = message3;
        this.type = type; 
    }
    
    public Message(MessageType type, int message, int message2, int message3, int message4){
        this.message_i = message;
        this.message_i2 = message2;
        this.message_i3 = message3;
        this.message_i4 = message4;
        this.type = type; 
    }
    
    public Message(MessageType type, boolean message){
        this.message_b = message;
        this.type = type; 
    }
    
    public Message(MessageType type){
        this.type = type; 
    }
    
    public Message(MessageType type, int message, HorseJockeyState horseJockeyState){
        this.type = type;
        this.message_i = message;
        this.horseJockeyState = horseJockeyState;
    }
    
    public Message(MessageType type, int message, SpectatorsState spectatorsState){
        this.type = type;
        this.message_i = message;
        this.spectatorsState = spectatorsState;
    }
    
    public Message(MessageType type, BrokerState brokerState){
        this.type = type;
        this.brokerState = brokerState;
    }
    
    /**
     * Construct to create Message with type and HashMap
     * @param type
     * @param map
     */
    public Message(MessageType type, HashMap<?, ?> map){
        this(type);
        this.map = map;
    }

    public Message(MessageType mt, int raceNumber, int spectatorId, Bet bet) {
        this.type = mt;
        this.message_i = raceNumber;
        this.message_i2 = spectatorId;
        this.bet = bet;
    }

    public Message(MessageType messageType, Bet bet) {
        this.type = messageType;
        this.bet = bet;
    }

    public Message(MessageType messageType, LinkedList<Integer> linked) {
        this.type = messageType;
        this.linked = linked;
    }

    public Message(MessageType messageType, double response_double) {
        this.type = messageType;
        this.message_d = response_double;
    }
    
    /**
     * Return Message Type
     * @return 
     */
    public MessageType getType(){
        return this.type;
    }
    
    /**
     * Return Integer HashMap
     * @return 
     */
    public HashMap<String, Integer> getStrIntMap(){
        return (HashMap<String, Integer>) this.map;
    }
    
    /**
     * Return String HashMap
     * @return 
     */
    public HashMap<String, String> getStrStrMap(){
        return (HashMap<String, String>) this.map;
    }
    
    public Integer getInteger(){
        return this.message_integer;
    }
    
    /**
     * Return Integer
     * @return 
     */
    public int getInteger1(){
        return this.message_i;
    }
    
    /**
     * Return Integer
     * @return 
     */
    public int getInteger2(){
        return this.message_i2;
    }
    
    /**
     * Return Integer
     * @return 
     */
    public int getInteger3(){
        return this.message_i3;
    }
    
    /**
     * Return Integer
     * @return 
     */
    public int getInteger4(){
        return this.message_i4;
    }
    
    /**
     * Return Boolean
     * @return 
     */
    public boolean getBoolean(){
        return this.message_b;
    }

    public Bet getBet() {
        return this.bet;
    }

    public LinkedList<Integer> getLinkedList() {
        return this.linked;
    }

    public double getDouble() {
        return this.message_d;
    }

    public SpectatorsState getSpectatorState() {
        return this.spectatorsState;
    }

    public HorseJockeyState getHorseJockeyState() {
        return this.horseJockeyState;
    }
    
    public BrokerState getBrokerState() {
        return this.brokerState;
    }
}
