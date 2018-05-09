package GeneralRepository;

import communication.Proxy.APC;
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
        NodeSettsStub proxy = new NodeSettsStub(); 
        SERVER_PORT = proxy.SERVER_PORTS().get("Races");
        
        // canais de comunicação
        ServerChannel schan, schani;
        
        // thread agente prestador do serviço
        APC apc;                               

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
                apc = new APC(schan, schani, server, "Races");
                apc.start();
            } catch (SocketTimeoutException ex) {
                Logger.getLogger(LogRun.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
