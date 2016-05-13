package dollynho;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
/*
 * @author vinimaz e carlos
 */

/**
 * Classe Modelo das passagens: Controla a informação das colunas da lista de passagens 
 * que são passadas pra tabela. Colunas: Origem, Destino, Data, Preço, Quantidade.
 * @author vinimaz e carlos
 */
public class PassagemModel extends AbstractTableModel{
    
    private List<Passagem> li = new ArrayList();
    private String[] columnNames = {"Origem", "Destino",
                "Data", "Preço", "Quantidade"};
   
    /**
     * Construtor da classe
     * @param li list que ficará conectada
     */
    public PassagemModel(List<Passagem> li){
        this.li     = li;
        
    }
    /**
     * 
     * @param columnIndex
     * @return nome da coluna
     */
    @Override
    public String getColumnName(int columnIndex){
         return columnNames[columnIndex];
    }
    /**
     * 
     * @return número de linhas
     */
    @Override     
    public int getRowCount() {
        return li.size();
    }
    /**
     * 
     * @return número de colunas
     */
    @Override        
    public int getColumnCount() {
        return columnNames.length; 
    }
   
    
    /**
     * Retorna o objeto com o número da linha e da coluna
     * @param rowIndex
     * @param columnIndex
     * @return objeto da linha e coluna
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        // Escolhe a passagem com base na linha selecionada
        Passagem si = li.get(rowIndex);
      
        // Seletor para saber qual atributo daquela passagem vai retornar
        switch (columnIndex) {
            case 0: 
                return si.getOrigem();
            case 1:
                return si.getDestino();
            case 2:
                return si.getDataIda();
            case 3:
                return si.getPreco();
            case 4:
                return si.getQtd();
           }
           return null;
   }
    /**
     * 
     * @param columnIndex
     * @return a classe da coluna
     */
    @Override
   public Class<?> getColumnClass(int columnIndex){
          switch (columnIndex){
             case 0:
               return String.class;
             case 1:
               return String.class;
             case 2:
               return LocalDate.class;
             case 3:
               return Float.class;
             case 4:
               return Integer.class;
             }
             return null;
      }  
}
