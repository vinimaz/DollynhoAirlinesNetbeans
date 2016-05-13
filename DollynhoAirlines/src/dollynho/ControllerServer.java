package dollynho;

import javafx.embed.swing.JFXPanel;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe controller, executa a ligação da View com o código
 * @author vinimaz e carlos
 */
public class ControllerServer{
    public JFXPanel jfxPanelDummy;
    public int controlIndex;    //Controla em qual ID está, 
                                //para usar quando adiciona Passagem ou Hospedagem
    Hospedagem safeCopyHosp;    //Cópia de segurança da Hospedagem que está sendo editada
    Passagem safeCopyPas;       //Cópia de segurança da Passagem que está sendo editada
    
    //JFrame com os componentes visuais
    dollynhoAirlinesJFrame mainFrame;
    //Tabela de Passagens
    List<Passagem>      liPassagem;
    PassagemModel       passagemmodel;
    //Tabela de Hospedagens
    List<Hospedagem>    liHospedagem;
    HospedagemModel     hospedagemmodel;
    //Tabela de Notificacoes
    List<Usuario>       liNotificacao;
    UsuarioModel        notificacaomodel;
    
    /**
     * Construtor da classe ControllerServer, inicializa as tabelas, 
     * popula as mesmas e seta a visibilidade dos Frames
     * @param mainFrame Frame da view
     */
    public ControllerServer(dollynhoAirlinesJFrame mainFrame) {
        this.mainFrame = mainFrame;
        //Sumindo com os panes extras
        mainFrame.getPaneCadastroPassagem().setVisible(false);
        mainFrame.getPaneCadastroHospedagem().setVisible(false);
        mainFrame.getPaneCadastroPassagemEdit().setVisible(false);
        mainFrame.getPaneCadastroHospedagemEdit().setVisible(false);
        //Iniciando listas
        liPassagem          = new ArrayList<Passagem>();
        liHospedagem        = new ArrayList<Hospedagem>();
        liNotificacao       = new ArrayList<Usuario>();
        //Iniciando modelos
        passagemmodel       = new PassagemModel(liPassagem);
        hospedagemmodel     = new HospedagemModel(liHospedagem);
        notificacaomodel    = new UsuarioModel(liNotificacao);
        
        //Colocando modelo nas tabelas
        mainFrame.getTabelaPassagens().setModel(passagemmodel);
        mainFrame.getTabelaHospedagem().setModel(hospedagemmodel);
        mainFrame.getTabelaNotificacoes().setModel(notificacaomodel);
        
        //Colocando valores iniciais na tabela de Passagem
        LocalDate data;
        data  = LocalDate.of(2016, 1, 01);
        liPassagem.add(new Passagem("Curitiba", "Miami", data, (float) 1200.90, 100, 1));
        data = LocalDate.of(2017, 10, 12);
        liPassagem.add(new Passagem("Curitiba", "Frankfurt", data, (float) 5450.50, 100, 2));
        data = LocalDate.of(2016, 10, 12);
        liPassagem.add(new Passagem("São Paulo", "Tokyo", data, (float) 8199.90, 100, 3));
        data = LocalDate.of(2016, 10, 12);
        liPassagem.add(new Passagem("São Paulo", "Salvador", data, (float) 1100.90, 100, 4));
        data = LocalDate.of(2016, 9, 12);
        liPassagem.add(new Passagem("Brasilia", "Lima", data, (float) 850.0, 100, 5));
        data = LocalDate.of(2016, 9, 13);
        liPassagem.add(new Passagem("Lima", "Brasilia", data, (float) 850.0, 100, 5));
        
        
        //Colocando valores iniciais na tabela de Hospedagem
        liHospedagem.add(new Hospedagem("Las Vegas", "Flamingo", 200, (float)150.00, 6));
        liHospedagem.add(new Hospedagem("Berlin", "Mercure", 200, (float)110.50, 7));
        liHospedagem.add(new Hospedagem("Curitiba", "Ibis Budget", 200, (float)50.00, 8));
        liHospedagem.add(new Hospedagem("Brasilia", "Ibis", 200, (float)50.00, 9));
        liHospedagem.add(new Hospedagem("Tokyo", "Tokyo Hotel", 200, (float)50.00, 10));
        liHospedagem.add(new Hospedagem("Sao Paulo", "Mercure", 200, (float)50.00, 11));
        
        controlIndex    = 100;

    }


    /*=======
     PASSAGEM
     =======*/
    /**
     * Abre o painel de cadastrar passagens
     */
    public void abrirPaneCadastroPassagem(){
        mainFrame.getIntroPane().setVisible(false);
        mainFrame.getPaneCadastroPassagem().setVisible(true);
    }

    /**
     * Fecha o painel de cadastrar passagem
     */
    public void fecharCadastroPassagens(){
        mainFrame.getFieldPasData().setText("DD/MM/YYYY");
        mainFrame.getFieldPasDestino().setText(null);
        mainFrame.getFieldPasOrigem().setText(null);
        mainFrame.getFieldPasPreco().setText(null);
        mainFrame.getFieldPasQuantidade().setText(null);
        
        mainFrame.getIntroPane().setVisible(true);
        mainFrame.getPaneCadastroPassagem().setVisible(false);
    }

    /**
     * Cria efetivamente uma nova passagem e adiciona na lista
     */
    public void addPassagem(){
        String origem       = mainFrame.getFieldPasOrigem().getText();
        String destino      = mainFrame.getFieldPasDestino().getText();
        String[] parts      = mainFrame.getFieldPasData().getText().split("/");
        int idaDia          = Integer.parseInt(parts[0]);
        int idaMes          = Integer.parseInt(parts[1]);
        int idaAno          = Integer.parseInt(parts[2]);
        float preco         = Float.parseFloat(mainFrame.getFieldPasPreco().getText());
        int qtd             = Integer.parseInt(mainFrame.getFieldPasQuantidade().getText());
        LocalDate aux       = LocalDate.of(idaAno, idaMes, idaDia);
        
        liPassagem.add(new Passagem(origem, destino, aux, preco, qtd, controlIndex));
        controlIndex++;
        fecharCadastroPassagens();
    }
    
    /**
     * Abre o painel de editar passagens
     */
    public void abreEditPassagem(){
        int selectedRow = mainFrame.getTabelaPassagens().getSelectedRow();
        if(selectedRow == -1)
            return;
        safeCopyPas    = liPassagem.get(selectedRow);
        liPassagem.remove(selectedRow);
        
        mainFrame.getPaneCadastroPassagemEdit().setVisible(true);
        mainFrame.getIntroPane().setVisible(false);
        
        mainFrame.getFieldPasDataEdit().setText(safeCopyPas.getDataIda().getDayOfMonth() + "/"
                                            + safeCopyPas.getDataIda().getMonthValue() + "/"
                                            + safeCopyPas.getDataIda().getYear());
        mainFrame.getFieldPasDestinoEdit().setText(safeCopyPas.getDestino());
        mainFrame.getFieldPasOrigemEdit().setText(safeCopyPas.getOrigem());
        mainFrame.getFieldPasPrecoEdit().setText(Float.toString(safeCopyPas.getPreco()));
        mainFrame.getFieldPasQuantidadeEdit().setText(Integer.toString(safeCopyPas.getQtd()));
    }
    
    /**
     * Fecha o painel de editar passagens
     */
    public void cancelaEditPassagem(){
        liPassagem.add(safeCopyPas);
        mainFrame.getFieldPasDataEdit().setText("DD/MM/YYYY");
        mainFrame.getFieldPasDestinoEdit().setText(null);
        mainFrame.getFieldPasOrigemEdit().setText(null);
        mainFrame.getFieldPasPrecoEdit().setText(null);
        mainFrame.getFieldPasQuantidadeEdit().setText(null);
        
        mainFrame.getIntroPane().setVisible(true);
        mainFrame.getPaneCadastroPassagemEdit().setVisible(false);
    }
    
    /**
     * Edita efetivamente o valor da passagem. Adiciona depois na lista novamente
    o item editado
     * @throws RemoteException 
     */
    public void confirmaEditPassagem() throws RemoteException{
        String origem       = mainFrame.getFieldPasOrigemEdit().getText();
        String destino      = mainFrame.getFieldPasDestinoEdit().getText();
        String[] parts      = mainFrame.getFieldPasDataEdit().getText().split("/");
        int idaDia          = Integer.parseInt(parts[0]);
        int idaMes          = Integer.parseInt(parts[1]);
        int idaAno          = Integer.parseInt(parts[2]);
        float preco         = Float.parseFloat(mainFrame.getFieldPasPrecoEdit().getText());
        int qtd             = Integer.parseInt(mainFrame.getFieldPasQuantidadeEdit().getText());
        LocalDate aux       = LocalDate.of(idaAno, idaMes, idaDia);
        //Add novamente na lista o item editado
        liPassagem.add(new Passagem(origem, destino, aux, preco, qtd, safeCopyPas.getId()));
        //Verificar se há algo a notificar
        checkAndNotify(safeCopyPas.getId(), preco);
        
        mainFrame.getFieldPasData().setText("DD/MM/YYYY");
        mainFrame.getFieldPasDestino().setText(null);
        mainFrame.getFieldPasOrigem().setText(null);
        mainFrame.getFieldPasPreco().setText(null);
        mainFrame.getFieldPasQuantidade().setText(null);
        
        mainFrame.getIntroPane().setVisible(true);
        mainFrame.getPaneCadastroPassagemEdit().setVisible(false);
    }
    
    /**
     * Remove a passagem selecionada da lista
     */
    public void removePassagem(){
        int selectedRow = mainFrame.getTabelaPassagens().getSelectedRow();
        if(selectedRow == -1)
            return;
        liPassagem.remove(selectedRow);
    }


    /*=========
     HOSPEDAGEM
     =========*/
    /**
     * Abre o painel de cadastrar hospedagens
     */
    public void abrirPaneCadastroHospedagem(){
        mainFrame.getPaneCadastroHospedagem().setVisible(true);
        mainFrame.getIntroPane().setVisible(false);
    }

    /**
     * Fecha o painel de cadastrar hospedagens
     */
    public void fecharCadastroHospedagem(){        
        mainFrame.getFieldHospCidade().setText(null);
        mainFrame.getFieldHospHotel().setText(null);
        mainFrame.getFieldHospPreco().setText(null);
        mainFrame.getFieldHospQuantidade().setText(null);
        
        mainFrame.getPaneCadastroHospedagem().setVisible(false);
        mainFrame.getIntroPane().setVisible(true);
    }

    /**
     * Adiciona Efetivamente a hospedagem. Cria um novo objeto hospedagem e adiciona na lista
     */
    public void addHospedagem(){
        String hotel        = mainFrame.getFieldHospHotel().getText();
        String cidade       = mainFrame.getFieldHospCidade().getText();
        float preco         = Float.parseFloat(mainFrame.getFieldHospPreco().getText());
        int qtd             = Integer.parseInt(mainFrame.getFieldHospQuantidade().getText());
        
        liHospedagem.add(new Hospedagem(cidade, hotel, qtd, preco, controlIndex));
        controlIndex++; 
        fecharCadastroHospedagem();
    }
    
    /**
     * Abre o painel de edição de hospedagens
     */
    public void abreEditHospedagem(){
        int selectedRow = mainFrame.getTabelaHospedagem().getSelectedRow();
        if(selectedRow == -1)
            return;
        safeCopyHosp    = liHospedagem.get(selectedRow);
        liHospedagem.remove(selectedRow);
        //Troca a view de Panes para edição
        mainFrame.getPaneCadastroHospedagemEdit().setVisible(true);
        mainFrame.getIntroPane().setVisible(false);
        
        mainFrame.getFieldHospCidadeEdit().setText(safeCopyHosp.getCidade());
        mainFrame.getFieldHospHotelEdit().setText(safeCopyHosp.getHotel());
        mainFrame.getFieldHospPrecoEdit().setText(Float.toString(safeCopyHosp.getPrecoQuarto()));
        mainFrame.getFieldHospQuantidadeEdit().setText(Integer.toString(safeCopyHosp.getQuartos()));   
    }
    
    /**
     * Fecha o painel de edição de hospedagens e volta ao inicial
     */
    public void cancelaEditHospedagem(){
        liHospedagem.add(safeCopyHosp);
        mainFrame.getIntroPane().setVisible(true);
        mainFrame.getPaneCadastroHospedagemEdit().setVisible(false);
    }
    
    /**
     * Edita efetivamente a hospedagem e adiciona ela de volta na lista
     * @throws RemoteException 
     */
    public void confirmaEditHospedagem() throws RemoteException{
        String hotel        = mainFrame.getFieldHospHotelEdit().getText();
        String cidade       = mainFrame.getFieldHospCidadeEdit().getText();
        float preco         = Float.parseFloat(mainFrame.getFieldHospPrecoEdit().getText());
        int qtd             = Integer.parseInt(mainFrame.getFieldHospQuantidadeEdit().getText());
        //Add novamente na lista o item editado
        liHospedagem.add(new Hospedagem(cidade, hotel, qtd, preco, safeCopyHosp.getId()));
        //Verificar se há algo a notificar
        checkAndNotify(safeCopyHosp.getId(), preco);
        
        mainFrame.getIntroPane().setVisible(true);
        mainFrame.getPaneCadastroHospedagemEdit().setVisible(false);
    }
    
    /**
     * Tira a hospedagem selecionada da lista de hospedagens
     */
    public void removeHospedagem(){
        int selectedRow = mainFrame.getTabelaHospedagem().getSelectedRow();
        if(selectedRow == -1)
            return;
        liHospedagem.remove(selectedRow);
    }

    /**
     * Atualiza a view de todas as Tables
     */
    public void updateTables(){
        // Iniciando modelos - Força a iniciar os modelos toda vez que é atualizado
        passagemmodel       = new PassagemModel(liPassagem);
        hospedagemmodel     = new HospedagemModel(liHospedagem);
        notificacaomodel    = new UsuarioModel(liNotificacao);
        
        //Colocando modelo nas tabelas
        mainFrame.getTabelaPassagens().setModel(passagemmodel);
        mainFrame.getTabelaHospedagem().setModel(hospedagemmodel);
        mainFrame.getTabelaNotificacoes().setModel(notificacaomodel);
    }
    
    /**
     * 
     * @return A lista completa de passagens
     */
    public List<Passagem> getLiPassagem() {
        List<Passagem> passagemCopy = liPassagem;
        return passagemCopy;
    }
    /**
     * 
     * @return A lista completa de Hospedagens
     */
    public List<Hospedagem> getLiHospedagem() {
        return liHospedagem;
    }
    /**
     * 
     * @return Retorna a lista de pedidos de notificações
     */
    public List<Usuario> getLiNotificacao() {
        return liNotificacao;
    }
    
    /*==================
     METODOS DO SERVIDOR
     =================*/
    
    /**
     * Cadastra uma notificação no servidor
     * @param idTransacao qual o ID da transação que o usuario quer ser avisado
     * @param refCli referência RMI do cliente, para realizar a notificação
     * @param precoAnterior preco atual que vai ser comparado aos novos que vierem depois
     * @param descricao qual a descrição que explica o item
     * @throws RemoteException 
     */
    public void addRegistroUsuario(int idTransacao, 
            InterfaceCli refCli, float precoAnterior, String descricao) throws RemoteException{
        //adiciona a lista
        liNotificacao.add(new Usuario(idTransacao, refCli, precoAnterior, descricao));
        //atualiza a view das tabelas
        updateTables();
        
        System.out.println("Nova Notificacao cadastrada por usuario \n"
                + "ID: " + idTransacao + " Preco: " + precoAnterior + " Descricao: " + descricao);
    }
    
    /**
     * Quando tem uma nova notificação, chama o método do cliente que o notifica
     * @param id o ID para verificar se há notificação cadastrada relacionada
     * @param preco novo preço para comparar com o da notificação cadastrada
     * @throws RemoteException 
     */
    private void checkAndNotify(int id, float preco) throws RemoteException {
        int aux2Delete = -1;
        for(int i=0; i<liNotificacao.size(); i++){
            if((liNotificacao.get(i).getId() == id) && 
                    (preco < liNotificacao.get(i).getPrecoAnterior())){
                System.out.println("Opa, tem algo a notificar com o ID: " + id);
                liNotificacao.get(i).getRefCli().notificaUsuario(id, preco, liNotificacao.get(i).getDescricao());
                aux2Delete = i;
            }      
        }
        if(aux2Delete != -1)
            liNotificacao.remove(aux2Delete);
    }
    
    
}
