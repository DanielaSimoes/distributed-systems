/*
 * This file is a message wrapper.
 */
package communication.message;

/**
 *
 * @author Daniela
 */
public class MessageWrapper {
    private Message m;
    
    public void setMessage(Message m){
        this.m = m;
    }
    
    public Message getMessage(){
        return this.m;
    }

}
