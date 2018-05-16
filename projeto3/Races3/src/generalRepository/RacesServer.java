/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generalRepository;

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
public class RacesServer {
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
        
        /* instanciação do objecto remoto que representa o Betting Centre e geração de um stub para ele */
        Races races = null;
        RacesInterface racesInterface = null;
        races = new Races();
        
        try {
            racesInterface = (RacesInterface) UnicastRemoteObject.exportObject((Remote) races, rc.racesPort());
        } catch (RemoteException e) {
            System.out.println("Excepção na geração do stub para a races: " + e.getMessage());
            System.exit(1);
        }
        
        System.out.println("O stub para a races foi gerado!");

        /* seu registo no serviço de registo RMI */
        String nameEntryBase = RegistryConfigs.registerHandler;
        String nameEntryObject = RegistryConfigs.racesNameEntry;
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
            reg.bind(nameEntryObject, (Remote) racesInterface);
        } catch (RemoteException e) {
            System.out.println("Excepção no registo da races:  " + e.getMessage());
            System.exit(1);
        } catch (AlreadyBoundException e) {
            System.out.println("A races já está registado: " + e.getMessage());
            System.exit(1);
        }
        
        System.out.println("A races foi registado!");
    }
}
