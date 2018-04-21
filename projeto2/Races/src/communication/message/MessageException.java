/*
 * This file contains the exceptions to be thrown in case of error.
 */
package communication.message;

/**
 * This class implements the message exceptions.
 * @author Daniela Simões, 76771
 */
public class MessageException extends Exception{
     private final Message msg;
    
    /**
    * Constructor to create Message Exception.
    * @param error
    * @param msg
    */
   public MessageException(String error, Message msg) {
        super(error);
        this.msg = msg;
    }
   
    /**
     * Return Message.
     * @return 
     */
    public Message getMessageObject() {
        return msg;
    }
}
