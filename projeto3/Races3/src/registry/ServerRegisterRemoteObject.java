package registry;

import interfaces.RegisterInterface;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import structures.constants.RegistryConfigs;

/**
 * This data type instantiates and registers a remote object that enables the
 * registration of other remote objects located in the same or other processing
 * nodes in the local registry service. Communication is based in Java RMI.
 * @author Daniela Simoes, 76771
 */
public class ServerRegisterRemoteObject {
    public static void main(String[] args) {
        /* get location of the registry service */

        String rmiRegHostName;                      // nome do sistema onde esta localizado o servico de registos RMI
        int rmiRegPortNumb;                         // port de escuta do servico

        RegistryConfigs rc = new RegistryConfigs("config.ini");
        rmiRegHostName = rc.registryHost();
        rmiRegPortNumb = rc.registryPort();

        /* create and install the security manager */
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        System.out.println("Security manager was installed!");

        /* instantiate a registration remote object and generate a stub for it */
        RegisterRemoteObject regEngine = new RegisterRemoteObject(rmiRegHostName, rmiRegPortNumb);
        RegisterInterface regEngineStub = null;

        try {
            regEngineStub = (RegisterInterface) UnicastRemoteObject.exportObject(regEngine, rc.objectPort());
        } catch (RemoteException ex) {
            Logger.getLogger(ServerRegisterRemoteObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Stub was generated!");

        /* register it with the local registry service */
        String nameEntry = RegistryConfigs.registerHandler;
        Registry registry = null;

        try {
            registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
        } catch (RemoteException e) {
            System.out.println("RMI registry creation exception: " + e.getMessage());
            System.exit(1);
        }
        System.out.println("RMI registry was created!");

        try {
            registry.rebind(nameEntry, regEngineStub);
        } catch (RemoteException e) {
            System.out.println("RegisterRemoteObject remote exception on registration: " + e.getMessage());
            System.exit(1);
        }
        System.out.println("RegisterRemoteObject object was registered!");
    }
}
