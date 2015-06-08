/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entidades;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pedro
 */
public abstract class Escalonador {
    
    protected List<Processo> processos;
    
    protected List<Processo> processosNaoProntos;
    
    protected List<Processo> prontos;

    protected Processo processoAtual = null;
        
    /**
     * Tempo atual da execução do escalonador. Se o valor for -1, então ainda
     * não houve execução.
     * 
     * @see #passaTempo()
     */
    private int tempoAtual = -1;
    
    /**
     * Inicializa o escalonador informando alguns/todos processos que serão
     * escalonados.
     * 
     * @param processos
     *            Processos por escalonar
     */
    public Escalonador(List<Processo> processos) {
        super();
        this.addProcessos(processos);
    }
    
    
    /**
     * Adiciona o processo a lista de processos.
     * 
     * Caso o escalonador já esteja em execução, então o tempo de entrada do
     * processo será mudado para o <code>tempoAtual</code> da execução, e este
     * já será colocado na lista de prontos.
     * 
     * @param processo
     *            Processo a ser adicionado
     * @see #processos
     * @see #tempoAtual
     * @see #prontos
     * @see #addProcessoPronto(Processo)
     */
    public Escalonador addProcesso(Processo processo) {
        //Se for o 1º processo a ser adicionado.
        if (this.processos == null) 
            this.processos = new ArrayList<Processo>();
        else {
            if (this.processos.contains(processo))
                return this;
        }
        
        //Se a lista está vazia ou se o tempo de chegada do ultimo processo é <= à chegada do processo atual.
        if (this.processos.isEmpty() || this.processos.get(this.processos.size() - 1).getChegada()<= processo.getChegada()) {
            this.processos.add(processo);
            return this;
        }
        
        
        int cur = 0;
        //Se o 1º processo chegou depois do atual.
        if (this.processos.get(0).getChegada() > processo.getChegada())
            cur = 0;
        else {
            int ini = 0; // Posição inicial da Lista de Processos.
            int fim = this.processos.size(); //Posição final da Lista de Processos.
            
            //Procura a posição adequada para inserir ordenado em tempo de chegada.
            while (fim > ini) {
                cur = (ini + fim) / 2;

                if (this.processos.get(cur).getChegada() < processo.getChegada()) {
                    ini = cur + 1;
                } else {
                    if (this.processos.get(cur).getChegada() > processo.getChegada()) {
                        fim = cur - 1;
                    } else {
                        for (cur++; cur < this.processos.size(); cur++) {
                            if (this.processos.get(cur).getChegada() > processo.getChegada())
                                break; // encontrou um maior
                        }
                        break; // chegou ao fim do vetor
                    }
                }
            }
        }
        
        //Se final da Lista.
        if (cur == this.processos.size()) {
            this.processos.add(processo);
            return this;
        }
        
        //Daqui até o final da Lista, procura a posição ... ???????
        for (; cur < this.processos.size(); cur++)
            if (this.processos.get(cur).getChegada() > processo.getChegada())
                break;

        Processo antigo = null;
        Processo atual = processo;

        for (; cur < this.processos.size(); cur++) {
            antigo = this.processos.get(cur);
            this.processos.set(cur, atual);
            atual = antigo;
        }

        this.processos.add(atual);
        
        if (this.tempoAtual != -1) {
            processo.setChegada(this.tempoAtual);
            this.addProcessoPronto(processo);
        }
        
        return this;
    }

    /**
     * Adiciona uma lista de processos aos processos do programa.
     * 
     * @param processos
     * 
     * @see #addProcesso(Processo)
     */
    public Escalonador addProcessos(List<Processo> processos) {
        //Para cada processo p em processos, adicioná-lo.
        for (Processo p : processos) {
            this.addProcesso(p);
        }
        return this;
    }
    
    /**
     * Adiciona um processo a lista de prontos.
     * 
     * @param processo
     *            Processo a entrar na memória
     * 
     * @see #prontos
     */
    abstract protected void addProcessoPronto(Processo processo);

    //Rever este Comentário!!!!!
    /**
     * Realiza o escalonamento dos processos inseridos no sistema. <br/>
     * <br/>
     * <ul>
     * <li>Alimenta a lista <code>processosNaoProntos</code> com uma cópia da
     * lista <code>processos</code>.</li>
     * <li>Incrementa o <code>tempoAtual</code></li>
     * <li>Alimenta a lista de <code>prontos</code> com os processo vindos da
     * lista <code>processosNaoProntos</code> que tenham o
     * <code>tempoEntrada</code> igual ao <code>tempoAtual</code>, esses
     * processo são removidos da lista <code>processosNaoProntos</code></li>
     * <li>Decrementa o <code>tempoExecução</code> do proceso
     * <code>processoAtual</code>.</li>
     * <li>Incrementa o <code>tempoEspera</code> dos processos na lista de
     * <code>prontos</code> execução.</li>
     * <li>Quando o processo <code>processoAtual</code> alcança o fim da execução (
     * <code>tempoExecucao</code> == 0), tira-o da lista de <code>prontos</code>
     * .</li>
     * </ul>
     * 
     * @see #processoAtual
     * @see #processos
     * @see #processosNaoProntos
     * @see #prontos
     * @see #tempoAtual
     * 
     * @return Verdadeiro se algum processo foi escalonado para o
     *         <code>tempoAtual</code>.
     */
    public boolean passaTempo() {
        if (this.tempoAtual == -1) { // copia a lista de processos para a lista de processos não prontos
            this.tempoAtual = 0;
            this.processosNaoProntos = new ArrayList<Processo>();
            for (Processo p : this.processos)
                this.processosNaoProntos.add(p);
            this.processaEntradas();
        } else
            this.tempoAtual++; // incrementa a passagem de tempo

        //Se não houver pronto...
        if (this.prontos.isEmpty())
            return false;

        Processo corrente = null;
        this.processaEntradas();

        corrente = this.processoAtual;

        corrente.passaTempo();

        for (Processo pronto : this.prontos) {
            if (pronto != corrente) // Quero saber se é o mesmo objeto, não se possuem os mesmos valores.
                pronto.incrementaEspera();
        }

        if (corrente.getDuracao() <= 0) {
            this.prontos.remove(corrente);
        }

        return true;
    }
    
    /**
     * Processa a lista de processos para incluí-los na espera
     */
    protected void processaEntradas() {
        if (this.prontos == null)
            this.prontos = new ArrayList<Processo>();

        List<Processo> entrando = new ArrayList<Processo>();

        for (int i = 0; i < this.processosNaoProntos.size(); i++) {
            if (this.processosNaoProntos.get(i) == null || this.processosNaoProntos.get(i).getChegada()!= this.tempoAtual)
                break;
            this.addProcessoPronto(this.processosNaoProntos.get(i));
            entrando.add(this.processosNaoProntos.get(i));
        }
        this.processosNaoProntos.removeAll(entrando);
    }

    /**
     * Retorna se o escalonador chegou ao final da execução ou não.
     * 
     * @return Verdadeiro se não há processos por executar.
     */
    public boolean isTerminado() {
        if (this.tempoAtual == -1)
            return false;

        if (this.prontos.isEmpty() && this.processosNaoProntos.isEmpty())
            return true;
        else
            return false;
    }

    /**
     * Retorna os processos do escalonador.
     * 
     * @see #processos
     */
    public List<Processo> getProcessos() {
        return processos;
    }

    /**
     * Retorna os processos que ainda não estão prontos.
     * 
     * @see #processosNaoProntos
     */
    public List<Processo> getProcessosNaoProntos() {
        return processosNaoProntos;
    }

    /**
     * Retorna os processos prontos no sistema.
     * 
     * @see #prontos
     */
    public List<Processo> getProntos() {
        return prontos;
    }

    /**
     * Retorna o processo Atual.
     * 
     * @see #processoAtual
     */
    public Processo getProcessoAtual() {
        return processoAtual;
    }

    /**
     * Retorna qual o tempo atual do escalonador.
     * 
     * @see #tempoAtual
     */
    public int getTempoAtual() {
        return tempoAtual;
    }
    
}
