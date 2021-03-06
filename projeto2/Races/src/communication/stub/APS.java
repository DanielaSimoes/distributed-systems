/*
 * This file contains the server APS.
 */
package communication.stub;

import communication.ServerChannel;
import communication.message.Message;
import communication.message.MessageException;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Class that implements the server APS.
 * @author Daniela Simões, 76771
 */
public class APS extends Thread{
    
    private static int nAPS;
    private final ServerChannel sconi;
    private final ServerInterface sInterface;
    private final ServerChannel scon;

    
    /**
     * Constructor to create the APS.
     * @param scon
     * @param sconi
     * @param sInterface
     * @param name
     */
    public APS(ServerChannel scon, ServerChannel sconi, ServerInterface sInterface, String name) {
        super(name);

        this.sconi = sconi;
        this.sInterface = sInterface;
        this.scon = scon;
    }

    /**
     * Method to run the APS.
     */
    @Override
    public void run() {
        Message request = null;  // mensagem de entrada
        Message response = null; // mensagem de saída

        request = (Message) sconi.readObject();
        
        try{
            response = sInterface.processAndReply(request, scon);
        } catch (MessageException e) {
            System.out.println("Thread " + getName() + ": " + e.getMessage() + "!");
            System.out.println(e.getMessageObject().toString());
            System.exit(1);
        } catch (SocketException ex) {
            Logger.getLogger(APS.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        sconi.writeObject(response);                                // enviar resposta ao cliente
        sconi.close();                                                // fechar canal de comunicação
        
        if(sInterface.serviceEnded())
        {
            System.out.println(this.sInterface.serviceName() + " - Closing service ... Done!");
            System.exit(0);
        }
    }

    /**
     * Method to retrieve the server APS ID.
     * @return 
     */
    public static int getApsId(){
        return nAPS;
    }
}
