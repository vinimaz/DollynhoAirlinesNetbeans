package dollynho;

import javafx.beans.property.*;

/**
 * Created by vinimaz and carlos on 5/10/16.
 */

/**
 * Classe que representa um usuário
 * @author vinimaz and carlos
 */
public class Usuario {
    private int id;
    /* Referência do client para poder chamar seus métodos */
    private InterfaceCli refCli;
    private float precoAnterior;
    private String descricao;

    /**
     * Construtor da classe
     * @param id
     * @param refCli
     * @param precoAnterior
     * @param descricao 
     */
    public Usuario(int id, InterfaceCli refCli, float precoAnterior, String descricao){
        this.id             = id;
        this.refCli         = refCli;              
        this.precoAnterior  = precoAnterior;
        this.descricao      = descricao;
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
     * @return Referência Cliente
     */
    public InterfaceCli getRefCli() {
        return refCli;
    }
    /**
     * 
     * @return Preco Anterior
     */
    public float getPrecoAnterior() {
        return precoAnterior;
    }
    /**
     * 
     * @return descrição
     */
    public String getDescricao() {
        return descricao;
    }
}
