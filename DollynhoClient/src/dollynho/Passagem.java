package dollynho;

import java.io.Serializable;
import javafx.beans.property.*;

import java.time.LocalDate;
import javax.swing.table.AbstractTableModel;

/**
 * Created by vinimaz and carlos on 5/5/16.
 */

/**
 * Classe que representa uma passagem
 * @author vinimaz
 */
public class Passagem implements Serializable{
    private float       preco;
    private int         qtd;
    private String      origem, destino;
    private LocalDate   dataIda;
    private int         id;
       
    /**
     * Construtor da classe Passagem
     * @param origem
     * @param destino
     * @param dataIda
     * @param preco
     * @param qtd
     * @param id 
     */
    public Passagem(String origem, String destino, LocalDate dataIda, float preco, int qtd, int id){
        this.id         = id;
        this.preco      = preco;
        this.qtd        = qtd;
        this.origem     = origem;
        this.destino    = destino;
        this.dataIda    = dataIda;
       /* Print pra debug quando uma passagem é criada */
        System.out.println("Passagem criada:");
        System.out.println(origem + " -> " + destino + " $" + preco);
        System.out.println("QTD: " + qtd +  " " + dataIda.getDayOfMonth() + "/" +
                dataIda.getMonth() + "/" + dataIda.getYear());
    }
    
    public float getPreco() {
        return preco;
    }
    public int getQtd() {
        return qtd;
    }
    public String getOrigem() {
        return origem;
    }
    public String getDestino() {
        return destino;
    }
    public LocalDate getDataIda() {
        return dataIda;
    }
    public int getId() {
        return id;
    }
}
