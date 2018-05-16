/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shared.stable;

import interfaces.RegisterInterface;
import interfaces.IStable;
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
public class StableRun {
    
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
         
        /* instanciacao do objecto remoto que representa o Stable e geracao de um stub para ele */
        Stable stable = null;
        IStable stableInterface = null;
        stable = new Stable(ri);
        
        try {
            stableInterface = (IStable) UnicastRemoteObject.exportObject((Remote) stable, rc.stablePort());
        } catch (RemoteException e) {
            System.out.println("Excepcao na geracao do stub para o Stable: " + e.getMessage());
            System.exit(1);
        }
        
        System.out.println("O stub para o Stable site foi gerado!");

        /* seu registo no servico de registo RMI */
        String nameEntryBase = RegistryConfigs.registerHandler;
        String nameEntryObject = RegistryConfigs.stableNameEntry;
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
            reg.bind(nameEntryObject, (Remote) stableInterface);
        } catch (RemoteException e) {
            System.out.println("Excepcao no registo do Stable site: " + e.getMessage());
            System.exit(1);
        } catch (AlreadyBoundException e) {
            System.out.println("O Stable ja esta registado: " + e.getMessage());
            System.exit(1);
        }
        
        System.out.println("O Stable site foi registado!");
    }
}
