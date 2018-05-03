package settings;

import communication.ServerChannel;
import communication.Proxy.ServerProxy;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This file implements the main of the node settings.
 * @author Daniela Simões.
 */
public class NodeSettsRun {
    public static void main(String[] args) throws SocketException {
        String json_path = "hosts.json";
        
        if(NodeSetts.DEBUG){
            json_path = "debug_hosts.json";
        }
        
        if(args.length == 1){
            json_path = args[0];
        }
        
        // canais de comunicação
        ServerChannel schan, schani;
        
        // thread agente prestador do serviço
        ServerProxy cliProxy;                               

        /* estabelecimento do servico */
        
        NodeSettsServer server = new NodeSettsServer(json_path);
        
        // criação do canal de escuta e sua associação
        schan = new ServerChannel(server.SERVER_PORTS.get("NodeSetts"));    
        schan.start();
        
        System.out.println("Node Setts service has started!\nServer is listening.");

        /* processamento de pedidos */
        
        while (true) {
            
            try {
                // entrada em processo de escuta
                schani = schan.accept();
                // lançamento do agente prestador do serviço
                cliProxy = new ServerProxy(schan, schani, server, "NodeSetts");
                cliProxy.start();
            } catch (SocketTimeoutException ex) {
                Logger.getLogger(NodeSettsRun.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
