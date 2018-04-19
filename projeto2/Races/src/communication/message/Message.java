/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication.message;

/**
 *
 * @author Daniela
 */
public class Message{
    
    private String message_s;
    private boolean message_b;
    private int message_i;
    private MessageType type;
        
    public Message(MessageType type, int message){
        this.message_i = message;
        this.type = type; 
    } 
    
    public Message(MessageType type, String message){
        this.message_s = message;
        this.type = type; 
    }
    
    public Message(MessageType type, boolean message){
        this.message_b = message;
        this.type = type; 
    }
}
