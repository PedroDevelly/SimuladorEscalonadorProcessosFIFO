/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entidades;

/**
 * Classe que representa cada processo no programa.
 * @author Pedro
 */
public class Processo {
    
//<editor-fold defaultstate="collapsed" desc="Atributos">
    private int duracao; // Tempo de execução do processo.
    private int chegada = 0; // Tempo de entrada do processo.
    private int espera = 0; // Tempo em espera do processo.
    private String nomeProcesso; // Nome para identificar o processo.
//</editor-fold>    
    
//<editor-fold defaultstate="collapsed" desc="Contrutores">
    /**
     * Cria uma instancia de processo com base nos parâmetros
     * 
     * @param nomeProcesso
     *            Nome do processo
     * @param duracao
     *            Tempo de execução total do processo
     * @param chegada
     *            Tempo em que o processo entra no programa
     */
    public Processo(String nomeProcesso, int duracao, int chegada) {
        super();
        this.nomeProcesso = nomeProcesso;
        this.duracao = duracao;
        this.chegada = chegada;
    }
//</editor-fold>    
    
//<editor-fold defaultstate="collapsed" desc="Métodos - Comportamentos">    
    /**
     * Decrementa o tempo de execução dentro do processo
     * 
     * @return Tempo restante
     */
    public int passaTempo() {
        return --this.duracao;
    }
    
    /**
     * Aumenta o tempo de espera de um processo.
     * 
     * @return Tempo em espera incrementado.
     */
    public int incrementaEspera() {
        return ++this.espera;
    }
    
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Gets">
    public int getDuracao() {
        return duracao;
    }

    public int getChegada() {
        return chegada;
    }

    public int getEspera() {
        return espera;
    }

    public String getNomeProcesso() {
        return nomeProcesso;
    }
//</editor-fold>
    
//    <editor-fold defaultstate="collapsed" desc="user-Sets">
    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public void setChegada(int chegada) {
        this.chegada = chegada;
    }

    public void setEspera(int espera) {
        this.espera = espera;
    }

    public void setNomeProcesso(String nomeProcesso) {
        this.nomeProcesso = nomeProcesso;
    }
//    </editor-fold>
    
    
    @Override
    public String toString() {
        return "Processo<" + nomeProcesso + ">";
    }
}
