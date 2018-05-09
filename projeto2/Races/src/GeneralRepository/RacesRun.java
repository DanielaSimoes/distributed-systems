package GeneralRepository;

import communication.stub.APS;
import communication.ServerChannel;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import settings.NodeSettsStub;

/**
 * This class implements the main of the races.
 * @author Daniela Simões
 */
public class RacesRun {
    private static int SERVER_PORT;
    
    public static void main(String[] args) throws SocketException, SocketTimeoutException {
        NodeSettsStub nodeSettsStub = new NodeSettsStub(); 
        SERVER_PORT = nodeSettsStub.SERVER_PORTS().get("Races");
        
        // canais de comunicação
        ServerChannel schan, schani;
        
        // thread agente prestador do serviço
        APS aps;                               

        /* estabelecimento do servico */
        
        // criação do canal de escuta e sua associação
        schan = new ServerChannel(SERVER_PORT);    
        schan.start();
        
        RacesInterface server = new RacesInterface();
        System.out.println("Races service has started!\nServer is listening.");

        /* processamento de pedidos */
        
        while (true) {
            
            try {
                // entrada em processo de escuta
                schani = schan.accept();
                // lançamento do agente prestador do serviço
                aps = new APS(schan, schani, server, "Races");
                aps.start();
            } catch (SocketTimeoutException ex) {
                Logger.getLogger(LogRun.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
