/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Algoritmos;

import Entidades.Escalonador;
import Entidades.Processo;
import java.util.List;

/**
 *
 * @author Pedro
 */
public class FIFO extends Escalonador{
    
    /**
     * Inicializa um FCFS com processos
     * @param processos Processo por escalonar
     */
    public FIFO(List<Processo> processos) {
        super(processos);
    }
    
    @Override
    protected void addProcessoPronto(Processo processo) {
        this.prontos.add(processo);
    }
    
//    @Override
//    public String toString() {
//        return "FCFS";
//    }
    
}
