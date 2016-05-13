package dollynho;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * @author vinimaz and carlos
 */

/**
 * Classe Modelo das hospedagens: Controla a informação das colunas da lista de hospedagens 
 * que são passadas pra tabela. Colunas: Hotel, Cidade, Quantidade, Preço
 * @author vinimaz
 */
public class HospedagemModel extends AbstractTableModel{
    private List<Hospedagem> li = new ArrayList(); 
    private String[] columnNames = {"Hotel", "Cidade",
                "Quantidade", "Preço"};
    
    /**
     * Construtor
     * @param li lista com a qual se relaciona
     */ 
    public HospedagemModel(List<Hospedagem> li){
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
     * @return  número de linhas
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
     * Retorna valor na linha e coluna especificada
     * @param rowIndex qual a linha
     * @param columnIndex qual a coluna
     * @return 
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        // Escolhe a hospedagem com base na linha selecionada
        Hospedagem si = li.get(rowIndex);
      
        // Seletor para saber qual atributo daquela hospedagem vai retornar
        switch (columnIndex) {
            case 0: 
                return si.getHotel();
            case 1:
                return si.getCidade();
            case 2:
                return si.getQuartos();
            case 3:
                return si.getPrecoQuarto();
           }
           return null;
   }
    
    /**
     * 
     * @param columnIndex
     * @return classe da coluna
     */
    @Override
   public Class<?> getColumnClass(int columnIndex){
          switch (columnIndex){
             case 0:
               return String.class;
             case 1:
               return String.class;
             case 2:
               return Integer.class;
             case 3:
               return Float.class;
          }
             return null;
      }
    
}
