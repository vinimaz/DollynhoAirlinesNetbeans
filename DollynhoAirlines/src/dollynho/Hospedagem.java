package dollynho;

import java.io.Serializable;
import javafx.beans.property.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

/**
 * Created by vinimaz and carlos on 5/5/16.
 */

/**
 * Classe que representa um objeto Hospedagem
 * @author vinimaz and carlos
 */
public class Hospedagem implements Serializable{
    private int                     quartos, id;
    private float                   precoQuarto;
    private String                  hotel, cidade;
    private ArrayList<LocalDate>    datasUsadas;

    /**
     * Construtor da classe hospedagem, seta os valores das variáveis
     * @param cidade em qual cidade está a hospedagem
     * @param hotel o nome do hotel
     * @param quartos quantos quartos disponíveis
     * @param preco preço do quarto
     * @param id id geral de controle
     */
    public Hospedagem(String cidade, String hotel, int quartos, float preco, int id){
        this.id          = id;
        this.datasUsadas = new ArrayList<LocalDate>();
        this.cidade      = cidade;
        this.hotel       = hotel;
        this.quartos     = quartos;
        this.precoQuarto = preco;
    }
    
    /**
     * 
     * @return Número de quartos
     */
    public int getQuartos() {
        return quartos;
    }
    /**
     * 
     * @return ID único
     */
    public int getId() {
        return id;
    }
    /**
     * 
     * @return Preço do quarto
     */
    public float getPrecoQuarto() {
        return precoQuarto;
    }
    /**
     * 
     * @return nome do hotel
     */
    public String getHotel() {
        return hotel;
    }
    /**
     * 
     * @return Cidade em que o hotel se situa
     */
    public String getCidade() {
        return cidade;
    }
    /**
     * 
     * @return Array com as datas em uso
     */
    public ArrayList<LocalDate> getDatasUsadas() {
        return datasUsadas;
    }
    
}
