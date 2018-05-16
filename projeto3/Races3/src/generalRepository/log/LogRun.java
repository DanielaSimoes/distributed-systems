package generalRepository.log;

import interfaces.RegisterInterface;
import interfaces.ILog;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import structures.constants.RegistryConfigs;

/**
 *
 * @author Daniela
 */
public class LogRun {
    public static void main(String[] args) throws NotBoundException, AlreadyBoundException {
        /* obtencao da localizacao do servico de registo RMI */
        
        // nome do sistema onde esta localizado o servico de registos RMI
        String rmiRegHostName;
        
        // port de escuta do servico
        int rmiRegPortNumb;            

        RegistryConfigs rc = new RegistryConfigs("config.ini");
        rmiRegHostName = rc.registryHost();
        rmiRegPortNumb = rc.registryPort();
        
        /* instanciacao e instalacao do gestor de seguranca */
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        
        interfaces.IRaces ri = null;
        
        try
        { 
            Registry registry = LocateRegistry.getRegistry (rmiRegHostName, rmiRegPortNumb);
            ri = (interfaces.IRaces) registry.lookup (RegistryConfigs.racesNameEntry);
        }
        catch (RemoteException e)
        { 
            System.out.println("Exception thrown while locating races: " + e.getMessage () + "!");
            System.exit (1);
        }
        catch (NotBoundException e)
        { 
            System.out.println("Races is not registered: " + e.getMessage () + "!");
            System.exit(1);
        }
        
        /* instanciacao do objecto remoto que representa o Betting Centre e geracao de um stub para ele */
        Log log = null;
        ILog logInterface = null;
        log = new Log("", ri);
        
        try {
            logInterface = (ILog) UnicastRemoteObject.exportObject((Remote) log, rc.logPort());
        } catch (RemoteException e) {
            System.out.println("Excepcao na geracao do stub para o Log: " + e.getMessage());
            System.exit(1);
        }
        
        System.out.println("O stub para o log foi gerado!");

        /* seu registo no servico de registo RMI */
        String nameEntryBase = RegistryConfigs.registerHandler;
        String nameEntryObject = RegistryConfigs.logNameEntry;
        Registry registry = null;
        RegisterInterface reg = null;

        try {
            registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
        } catch (RemoteException e) {
            System.out.println("Excepcao na criacao do registo RMI: " + e.getMessage());
            System.exit(1);
        }
        
        System.out.println("O registo RMI foi criado!");

        try {
            reg = (RegisterInterface) registry.lookup(nameEntryBase);
        } catch (RemoteException e) {
            System.out.println("RegisterRemoteObject lookup exception: " + e.getMessage());
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("RegisterRemoteObject not bound exception: " + e.getMessage());
            System.exit(1);
        }

        try {
            reg.bind(nameEntryObject, (Remote) logInterface);
        } catch (RemoteException e) {
            System.out.println("Excepcao no registo do log:  " + e.getMessage());
            System.exit(1);
        } catch (AlreadyBoundException e) {
            System.out.println("O log ja esta registado: " + e.getMessage());
            System.exit(1);
        }
        
        System.out.println("O log foi registado!");
    }
}
