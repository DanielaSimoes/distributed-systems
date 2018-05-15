/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shared.stable;

import interfaces.RegisterInterface;
import interfaces.stable.StableInterface;
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
public class StableServer {
    
    public static void main(String[] args) throws NotBoundException, AlreadyBoundException {
        /* obtenção da localização do serviço de registo RMI */
        
        // nome do sistema onde está localizado o serviço de registos RMI
        String rmiRegHostName;
        
        // port de escuta do serviço
        int rmiRegPortNumb;            

        RegistryConfigs rc = new RegistryConfigs("config.ini");
        rmiRegHostName = rc.registryHost();
        rmiRegPortNumb = rc.registryPort();
        
        /* instanciação e instalação do gestor de segurança */
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        
        /* instanciação do objecto remoto que representa o Stable e geração de um stub para ele */
        Stable stable = null;
        StableInterface stableInterface = null;
        stable = new Stable();
        
        try {
            stableInterface = (StableInterface) UnicastRemoteObject.exportObject((Remote) stable, rc.stablePort());
        } catch (RemoteException e) {
            System.out.println("Excepção na geração do stub para o Stable: " + e.getMessage());
            System.exit(1);
        }
        
        System.out.println("O stub para o Stable site foi gerado!");

        /* seu registo no serviço de registo RMI */
        String nameEntryBase = RegistryConfigs.registerHandler;
        String nameEntryObject = RegistryConfigs.stableNameEntry;
        Registry registry = null;
        RegisterInterface reg = null;

        try {
            registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
        } catch (RemoteException e) {
            System.out.println("Excepção na criação do registo RMI: " + e.getMessage());
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
            reg.bind(nameEntryObject, (Remote) stableInterface);
        } catch (RemoteException e) {
            System.out.println("Excepção no registo do Stable site: " + e.getMessage());
            System.exit(1);
        } catch (AlreadyBoundException e) {
            System.out.println("O Stable já está registado: " + e.getMessage());
            System.exit(1);
        }
        
        System.out.println("O Stable site foi registado!");
    }
}
