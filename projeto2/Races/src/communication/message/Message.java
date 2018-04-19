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
public class Message {
    
    private Message m;
    
    public Message(){
    
    }
    
    public void setMessage(Message m){
        this.m = m;
    }
    
    public Message getMessage(){
        return this.m;
    }
    
}
