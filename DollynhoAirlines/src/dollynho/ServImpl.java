package dollynho;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import static dollynho.ControllerServer.*;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Created by vinimaz and carlos on 5/5/16.
 */
public class ServImpl extends UnicastRemoteObject implements InterfaceServ {
    ControllerServer controller;
  
    /* Construtor - recebe por parâmetro também um controller que prende o o modelo
    do servidor com a view */
    public ServImpl(ControllerServer controller) throws RemoteException {
        this.controller = controller;
    }

    /* Retorna para o cliente a lista com todas as passagens (sem filtro) */
    @Override
    public List<Passagem> getTodasPassagens() throws RemoteException {
        System.out.println("Cliente chegou a pedir todas as passagens");
        return controller.getLiPassagem();
    }

    
    /* Retorna para o cliente a lista com todas as hospedagens (sem filtro)*/
    @Override
    public List<Hospedagem> getTodasHospedagens() throws RemoteException {
        System.out.println("Cliente chegou a pedir todas as hospedagens");
        return controller.getLiHospedagem();
    }

    /* Filtra as passagens que o cliente escolher procurar com os dados específicos */
    @Override
    public List<Passagem> getSearchPassagens(String origem, String destino, 
            LocalDate ida, LocalDate volta, int numAdultos, int criancas) throws RemoteException {
        List<Passagem> auxRetorno       = new ArrayList<Passagem>();
        List<Passagem> listaCompleta    = controller.getLiPassagem();
        //Se a busca é ida e volta
        if(volta != null)
        {
            for(int i=0; i< listaCompleta.size(); i++){
                //Confere infos da ida
                if((listaCompleta.get(i).getDataIda().isEqual(ida)) && 
                        (listaCompleta.get(i).getOrigem().equals(origem)) &&
                        (listaCompleta.get(i).getDestino().equals(destino)) ){
                    auxRetorno.add(listaCompleta.get(i));
                }
                //Confere infos da volta
                if((listaCompleta.get(i).getDataIda().isEqual(volta)) && 
                        (listaCompleta.get(i).getOrigem().equals(destino)) &&
                        (listaCompleta.get(i).getDestino().equals(origem)) ){
                    auxRetorno.add(listaCompleta.get(i));
                }
            }
        }else{ //somente ida
            for(int i=0; i< listaCompleta.size(); i++){
                if((listaCompleta.get(i).getDataIda().isEqual(ida)) && 
                        (listaCompleta.get(i).getOrigem().equals(origem)) &&
                        (listaCompleta.get(i).getDestino().equals(destino)) ){
                    auxRetorno.add(listaCompleta.get(i));
                }   
            }
        }
        return auxRetorno;
    }

    /* Filtra as hospedagens que o cliente escolher procurar com os dados específicos */
    @Override
    public List<Hospedagem> getSearchHospedagem(String cidade, 
            String hotel, int numPessoas) throws RemoteException {
        List<Hospedagem> auxRetorno       = new ArrayList<Hospedagem>();
        List<Hospedagem> listaCompleta    = controller.getLiHospedagem();
        if((cidade != null)&&(hotel !=null)){
            for(int i=0; i< listaCompleta.size(); i++){
                if((listaCompleta.get(i).getCidade().equals(cidade)) &&
                        (listaCompleta.get(i).getHotel().equals(hotel)) ){
                    auxRetorno.add(listaCompleta.get(i));
                }
            }     
        }else if((cidade == null)&&(hotel != null)){
            for(int i=0; i< listaCompleta.size(); i++){
                if(listaCompleta.get(i).getHotel().equals(hotel)){
                    auxRetorno.add(listaCompleta.get(i));
                }
            }     
        }else if((cidade != null)&&(hotel == null)){
            for(int i=0; i< listaCompleta.size(); i++){
                if(listaCompleta.get(i).getCidade().equals(cidade)){
                    auxRetorno.add(listaCompleta.get(i));
                }
            }     
        }
        return auxRetorno;
    }

    
    /*
    Método que o cliente usa para se cadastrar no serviço de notificações.
    O cliente usa quando quer saber quando uma passagem vai abaixar de preço
    */
    @Override
    public synchronized void cadastraNotificacao(InterfaceCli cliente, int id,
                                                 float preco, String descricao) throws RemoteException {
        System.out.println("Cliente passou pelo cadastraNotificacao");
        controller.addRegistroUsuario(id, cliente, preco, descricao);
        
    }

    
    /* Teste básico para o cliente chamar e testar as chamadas por RMI */
    public void echoTest() throws RemoteException{
        System.out.println("Cliente passou pelo echoTest");
    }

    @Override
    /**
     * Notifica o servidor que foi realizada uma compra
     */
    public synchronized void notificaCompra(int id, String nome, long numCartao, String descricao) throws RemoteException {
        String notificacao  = ("Uma compra acabou de ser efetuada para o cliente " + nome + ".\n"
        + "Item vendido: " + descricao + "\n"
        + "Cartão: " + numCartao);
        JOptionPane.showMessageDialog(null, notificacao, "Compra efetuada ", 
                JOptionPane.INFORMATION_MESSAGE); 
    }
}
