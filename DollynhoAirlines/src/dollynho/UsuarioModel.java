package dollynho;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
/**
 * @author vinimaz
 */

/*
Classe Modelo dos usuarios: Controla a informação das colunas da lista de usuarios cadastrados 
que são passadas pra tabela. Colunas: ID do produto (transacao), preço antigo, descricão
*/
public class UsuarioModel extends AbstractTableModel{
    
    private List<Usuario> li = new ArrayList();
    private String[] columnNames = {"ID da transação", "Preco Anterior",
                "Descrição"};
    
    // Construtor do Modelo
    public UsuarioModel(List<Usuario> li){
        this.li     = li;
        
    }
    
    @Override
    public String getColumnName(int columnIndex){
         return columnNames[columnIndex];
    }

    @Override     
    public int getRowCount() {
        return li.size();
    }

    @Override        
    public int getColumnCount() {
        return columnNames.length; 
    }
    
    /* Pega e retorna o valor que está na (linha,coluna) */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
       
        // Escolhe o usuario com base na linha selecionada
        Usuario si = li.get(rowIndex);
        
        // Seletor para saber qual atributo daquela passagem vai retornar
        switch (columnIndex) {
            case 0: 
                return si.getId();
            case 1:
                return si.getPrecoAnterior();
            case 2:
                return si.getDescricao();
           }
           return null;
   }
    
     /* Retorna a classe da Coluna */
    @Override
   public Class<?> getColumnClass(int columnIndex){
          switch (columnIndex){
             case 0:
               return Integer.class;
             case 1:
               return Float.class;
             case 2:
               return String.class;
             }
             return null;
      }
    
    
}
