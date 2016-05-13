package dollynho;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by carlos on 5/5/16.
 */
public interface InterfaceServ extends Remote {
    /**
     * 
     * @return toda a lista de passagens
     * @throws RemoteException 
     */
    public List<Passagem> getTodasPassagens() throws RemoteException;
    /**
     * 
     * @return toda a lista de hospedagens
     * @throws RemoteException 
     */
    public List<Hospedagem> getTodasHospedagens() throws RemoteException;
    /**
     * Realiza busca de passagens no servidor
     * @param origem
     * @param destino
     * @param ida
     * @param volta
     * @param numAdultos
     * @param numCriancas
     * @return resultado da busca/filtro
     * @throws RemoteException 
     */
    public List<Passagem> getSearchPassagens(String origem, String destino, LocalDate ida, LocalDate volta,
                                                  int numAdultos, int numCriancas) throws RemoteException;
    /**
     * Realiza busca de hospedagens no servidor
     * @param cidade
     * @param hotel
     * @param numPessoas
     * @return resultado da busca/filtro
     * @throws RemoteException 
     */
    public List<Hospedagem> getSearchHospedagem(String cidade, String hotel, int numPessoas) throws RemoteException;
    /**
     * Recebe o cadastro de um pedido de notificação de baixa de preço
     * @param cliente
     * @param id
     * @param preco
     * @param descricao
     * @throws RemoteException 
     */
    public void cadastraNotificacao(InterfaceCli cliente, int id, float preco, String descricao) throws RemoteException;
    /**
     * Teste de conexão RMI
     * @throws RemoteException 
     */
    public void echoTest() throws RemoteException;
    /**
     * Notifica quando algum cliente realiza alguma compra
     * @param id
     * @param nome
     * @param numCartao
     * @param descricao
     * @throws RemoteException 
     */
    public void notificaCompra(int id, String nome, long numCartao, String descricao) throws RemoteException;
}
