/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generalRepository.races;

import interfaces.IRaces;
import interfaces.RegisterInterface;
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
public class RacesRun {
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
        
        /* instanciacao do objecto remoto que representa o Betting Centre e geracao de um stub para ele */
        Races races = null;
        IRaces racesInterface = null;
        races = new Races();
        
        try {
            racesInterface = (IRaces) UnicastRemoteObject.exportObject((Remote) races, rc.racesPort());
        } catch (RemoteException e) {
            System.out.println("Excepcao na geracao do stub para a races: " + e.getMessage());
            System.exit(1);
        }
        
        System.out.println("O stub para a races foi gerado!");

        /* seu registo no servico de registo RMI */
        String nameEntryBase = RegistryConfigs.registerHandler;
        String nameEntryObject = RegistryConfigs.racesNameEntry;
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
            reg.bind(nameEntryObject, (Remote) racesInterface);
        } catch (RemoteException e) {
            System.out.println("Excepcao no registo da races:  " + e.getMessage());
            System.exit(1);
        } catch (AlreadyBoundException e) {
            System.out.println("A races ja esta registado: " + e.getMessage());
            System.exit(1);
        }
        
        System.out.println("A races foi registado!");
    }
}
