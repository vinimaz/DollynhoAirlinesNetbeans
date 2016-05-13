package dollynho;

import javafx.embed.swing.JFXPanel;
import javafx.fxml.Initializable;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javax.swing.JOptionPane;

public class ControllerClient{
    InterfaceServ refServidor;
    ClientImpl cliente;
    jFrameDollynhoClient mainFrame;
    String compraDescricao;
    
    //Tabela de Passagens
    List<Passagem>      liPassagem;
    PassagemModel       passagemmodel;
    //Tabela de Hospedagens
    List<Hospedagem>    liHospedagem;
    HospedagemModel     hospedagemmodel;

    public ControllerClient(jFrameDollynhoClient mainFrame) throws RemoteException, NotBoundException {
        this.mainFrame              = mainFrame;
        mainFrame.getPaneCompraHosp().setVisible(false);
        mainFrame.getPaneCompraPas().setVisible(false);
        mainFrame.setSize(630, 530);
        
        
        Registry refServidorDeNomes = LocateRegistry.getRegistry(6789);
        refServidor                 = (InterfaceServ) refServidorDeNomes.lookup("Dollynho");
        cliente                     = new ClientImpl(refServidor, this);
        
        
        //Iniciando as listas
        liPassagem          = new ArrayList<Passagem>();
        liHospedagem        = new ArrayList<Hospedagem>();
        //Iniciando modelos
        passagemmodel       = new PassagemModel(liPassagem);
        hospedagemmodel     = new HospedagemModel(liHospedagem);
        //Colocando modelo nas tabelas
        mainFrame.getTableBuscaPassagem().setModel(passagemmodel);
        mainFrame.getTableBuscaHospedagem().setModel(hospedagemmodel);
    }
    
    public void popUpNotificacao(String mensagem){        
        JOptionPane.showMessageDialog(null, mensagem, "Preço atualizado! ", 
                JOptionPane.INFORMATION_MESSAGE); 
        updateTables();
    }
    
    public void buscaPassagem() throws RemoteException{
        String origem           = mainFrame.getTextPasOrigem().getText();
        String destino          = mainFrame.getTextPasDestino().getText();
        String [] dataIdaStr    = mainFrame.getTextPasData().getText().split("/");
        int adultos             = Integer.parseInt(mainFrame.getTextPasAdultos().getText());
        int criancas            = Integer.parseInt(mainFrame.getTextPasCriancas().getText());
        int diaIda              = Integer.parseInt(dataIdaStr[0]);
        int mesIda              = Integer.parseInt(dataIdaStr[1]);
        int anoIda              = Integer.parseInt(dataIdaStr[2]);
        LocalDate dataIda       = LocalDate.of(anoIda, mesIda, diaIda);
        String [] dataVoltaStr  = mainFrame.getTextPasDataVolta().getText().split("/");
        int diaVolta            = Integer.parseInt(dataVoltaStr[0]);
        int mesVolta            = Integer.parseInt(dataVoltaStr[1]);
        int anoVolta            = Integer.parseInt(dataVoltaStr[2]);
        LocalDate dataVolta     = LocalDate.of(anoVolta, mesVolta, diaVolta);        
        
        liPassagem.clear();
        liPassagem = refServidor.getSearchPassagens(origem, destino, 
                dataIda, dataVolta, adultos, criancas);
        updateTables();
    }
    
    public void buscaHospedagem() throws RemoteException{
        String cidade       = mainFrame.getTextHospCidade().getText();
        String hotel        = mainFrame.getTextHospHotel().getText();
        int numPessoas      = 1;
        
        liHospedagem.clear();
        liHospedagem = refServidor.getSearchHospedagem(cidade, hotel, numPessoas);
        updateTables();
    }
    
    public void avisemePassagem() throws RemoteException{
        int index = mainFrame.getTableBuscaPassagem().getSelectedRow();
        System.out.println("Avisar do INDEX " + index);
        if(index == -1)
            return;
        Passagem auxPassagem    = liPassagem.get(index);
        refServidor.cadastraNotificacao(cliente, auxPassagem.getId(), auxPassagem.getPreco(), 
                "Passagem " + auxPassagem.getOrigem() + "->" + auxPassagem.getDestino());
        
        JOptionPane.showMessageDialog(null, "Foi criado o aviso, quando o preço baixar, "
                + "você será avisado(a)", "Criado aviso", 
                JOptionPane.INFORMATION_MESSAGE); 
    }
    
    public void avisemeHospedagem() throws RemoteException{
        int index = mainFrame.getTableBuscaHospedagem().getSelectedRow();
        if(index == -1)
            return;
        Hospedagem auxHospedagem    = liHospedagem.get(index);
        refServidor.cadastraNotificacao(cliente, auxHospedagem.getId(), auxHospedagem.getPrecoQuarto(), "Hospedagem em " + 
                auxHospedagem.getHotel() + ", " + auxHospedagem.getCidade()); 
        JOptionPane.showMessageDialog(null, "Foi criado o aviso, quando o preço baixar, "
                + "você será avisado(a)", "Criado aviso", 
                JOptionPane.INFORMATION_MESSAGE); 
    }
    
    public void mostraTudoPassagem() throws RemoteException{
        liPassagem = refServidor.getTodasPassagens();
        updateTables();
    }
    
    public void mostraTudoHospedagem() throws RemoteException{
        liHospedagem = refServidor.getTodasHospedagens();
        updateTables();
    }
    public void abreCompraPassagem(){
        int index = mainFrame.getTableBuscaPassagem().getSelectedRow();
        if(index == -1)
            return;
        Passagem auxPassagem    = liPassagem.get(index);
        compraDescricao = "Passagem " + auxPassagem.getOrigem() + "->" + auxPassagem.getDestino();
        mainFrame.getMainPane().setVisible(false);
        mainFrame.getPaneCompraPas().setVisible(true);
        mainFrame.getTextCompraDescricaoPas().setText(compraDescricao);
    }
    public void compraPassagem() throws RemoteException{
        int index = mainFrame.getTableBuscaPassagem().getSelectedRow();
        if(index == -1)
            return;
        Passagem auxPassagem    = liPassagem.get(index);
        compraDescricao = "Passagem " + auxPassagem.getOrigem() + "->" + auxPassagem.getDestino();
        String nome     = mainFrame.getTextCompraNomePas().getText();
        int numCartao   = Integer.parseInt(mainFrame.getTextCompraNumCartaoPas().getText());
        
        refServidor.notificaCompra(index, nome, numCartao, compraDescricao);
        fechaPaneCompraPassagem();
    }
    
    public void fechaPaneCompraPassagem(){
        mainFrame.getPaneCompraPas().setVisible(false);
        mainFrame.getMainPane().setVisible(true);
    }
    public void abreCompraHospedagem(){
        int index = mainFrame.getTableBuscaHospedagem().getSelectedRow();
        if(index == -1)
            return;
        Hospedagem auxHospedagem    = liHospedagem.get(index);
        compraDescricao = "Passagem " + auxHospedagem.getHotel()+ "," + auxHospedagem.getCidade();
        mainFrame.getMainPane().setVisible(false);
        mainFrame.getPaneCompraHosp().setVisible(true);
        mainFrame.getTextCompraDescricaoHosp().setText(compraDescricao);
    }
    public void compraHospedagem() throws RemoteException{
        int index = mainFrame.getTableBuscaHospedagem().getSelectedRow();
        if(index == -1)
            return;
        Hospedagem auxHospedagem    = liHospedagem.get(index);
        compraDescricao = "Hospedagem " + auxHospedagem.getHotel() + "," + auxHospedagem.getCidade();
        String nome     = mainFrame.getTextCompraNomeHosp().getText();
        int numCartao   = Integer.parseInt(mainFrame.getTextCompraNumCartaoHosp().getText());
        
        refServidor.notificaCompra(index, nome, numCartao, compraDescricao);
        fechaPaneCompraHospedagem();
    }
    
    public void fechaPaneCompraHospedagem(){
        mainFrame.getPaneCompraHosp().setVisible(false);
        mainFrame.getMainPane().setVisible(true);
    }
    
    public void updateTables(){
        //Iniciando modelos
        passagemmodel       = new PassagemModel(liPassagem);
        hospedagemmodel     = new HospedagemModel(liHospedagem);
        //Colocando modelo nas tabelas
        mainFrame.getTableBuscaPassagem().setModel(passagemmodel);
        mainFrame.getTableBuscaHospedagem().setModel(hospedagemmodel);
    }
}
