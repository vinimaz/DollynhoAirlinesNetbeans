package dollynho;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by vinimaz and carlos on 5/5/16.
 */
public interface InterfaceCli extends Remote {
    /**
     * Método para receber a notificação do Servidor de baixa de preço
     * @param id
     * @param precoNovo
     * @param text
     * @throws RemoteException 
     */
    public void notificaUsuario(int id, float precoNovo, String text) throws RemoteException;
}
