/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shared.racingTrack;

import interfaces.RegisterInterface;
import interfaces.racingTrack.RacingTrackInterface;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import shared.racingTrack.RacingTrack;
import structures.constants.RegistryConfigs;

/**
 *
 * @author Daniela
 */
public class RacingTrackServer {
    
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
        
        /* instanciação do objecto remoto que representa o RacingTrack e geração de um stub para ele */
        RacingTrack racing_track = null;
        RacingTrackInterface racingTrackInterface = null;
        racing_track = new RacingTrack();
        
        try {
            racingTrackInterface = (RacingTrackInterface) UnicastRemoteObject.exportObject((Remote) racing_track, rc.racingTrackPort());
        } catch (RemoteException e) {
            System.out.println("Excepção na geração do stub para o Racing Track: " + e.getMessage());
            System.exit(1);
        }
        
        System.out.println("O stub para o RacingTrack site foi gerado!");

        /* seu registo no serviço de registo RMI */
        String nameEntryBase = RegistryConfigs.registerHandler;
        String nameEntryObject = RegistryConfigs.racingTrackNameEntry;
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
            reg.bind(nameEntryObject, (Remote) racingTrackInterface);
        } catch (RemoteException e) {
            System.out.println("Excepção no registo do Racing Track site: " + e.getMessage());
            System.exit(1);
        } catch (AlreadyBoundException e) {
            System.out.println("O RacingTrack já está registado: " + e.getMessage());
            System.exit(1);
        }
        
        System.out.println("O Racing Track site foi registado!");
    }
}
