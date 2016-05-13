package dollynho;

import com.sun.javafx.application.PlatformImpl;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.CountDownLatch;

/**
 * Created by vinimaz on 5/5/16.
 */
public class ClientImpl extends UnicastRemoteObject implements InterfaceCli{
    InterfaceServ refServidor;
    ControllerClient controller;
    public ClientImpl(InterfaceServ refServidor, ControllerClient controller) throws RemoteException {
        this.refServidor    = refServidor;
        this.controller     = controller;
    }

    @Override
    public void notificaUsuario(int id, float precoNovo, String text) throws RemoteException {
        System.out.println("Recebida notificação do servidor para Hospedagem!");
        System.out.println("ID:" + id + " Novo Preço:$" + precoNovo);
        controller.popUpNotificacao("Preco novo $" + precoNovo + " de " + text);
    }
    
    public void enviaNotificacao(int idTransacao, 
            InterfaceCli refCli, float precoAnterior, String descricao){
        
    }
}
