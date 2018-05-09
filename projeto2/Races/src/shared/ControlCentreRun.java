package shared;

import GeneralRepository.RacesStub;
import communication.Proxy.APS;
import communication.ServerChannel;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import settings.NodeSettsStub;

/**
 * This class implements the main of control centre.
 * @author Daniela Simões, 76771
 */
public class ControlCentreRun {
    private static int SERVER_PORT;
    
    public static void main(String[] args) throws SocketException {
        NodeSettsStub proxy = new NodeSettsStub(); 
        SERVER_PORT = proxy.SERVER_PORTS().get("ControlCentre");
        
        // canais de comunicação
        ServerChannel schan, schani;
        
        // thread agente prestador do serviço
        APS aps;                               

        /* estabelecimento do servico */
        
        // criação do canal de escuta e sua associação
        schan = new ServerChannel(SERVER_PORT);    
        schan.start();
        
        RacesStub races = new RacesStub();
        
        ControlCentreInterface controlCentreServer = new ControlCentreInterface(races);
        System.out.println("Control Centre service has started!\nServer is listening.");

        /* processamento de pedidos */
        
        while (true) {
            
            try {
                // entrada em processo de escuta
                schani = schan.accept();
                // lançamento do agente prestador do serviço
                aps = new APS(schan, schani, controlCentreServer, "ControlCentre");
                aps.start();
            } catch (SocketTimeoutException ex) {
                Logger.getLogger(BettingCentreRun.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
