/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simuladorescalonadorprocessosfifo;

import Algoritmos.FIFO;
import Entidades.Escalonador;
import Entidades.Processo;
import LeituraDeArquivo.LeitorDeArquivo;
import java.io.File;
import java.util.List;

/**
 *
 * @author Pedro
 */
public class SimuladorEscalonadorProcessosFIFO {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String[] vetorInicializacao = new String[] { "Processos.csv.txt", "FIFO", "sim" };
        
        String sProcessosCSV = vetorInicializacao[0].trim();
        String sAlgoritmo = vetorInicializacao[1].trim().toLowerCase();
        
        File fProcessosCSV = new File(sProcessosCSV);
        List<Processo> processos = null;

        Escalonador escalonador = null;
        char algoritmo = sAlgoritmo.charAt(0);
        
        if (!fProcessosCSV.exists()) {
            System.out.println(" * Arquivo CSV de processos nao existe!");
            return;
        }
        
        if (!fProcessosCSV.isFile()) {
            System.out.println(" * É preciso informar um arquivos CSV no primeiro parametro!");
            return;
        }
        
        if (!fProcessosCSV.canRead()) {
            System.out.println(" * O usuário nao tem permissao de leitura no arquivo!");
            return;
        }
        
        try {
            LeitorDeArquivo.validarCSV(fProcessosCSV);
        } catch (Exception e) {
            System.out.println("Arquivo CSV não é válido, a seqüência de colunas é: Nome;TempoEntrada;Duração");
            return;
        }
        
        try {
            processos = LeitorDeArquivo.csvParaProcessos(fProcessosCSV);

            switch (algoritmo) {
                case 'f':
                    escalonador = new FIFO(processos);
                    break;
                default:
                    System.out.println("Erro na escolha do algoritmo de escalonamento!!!");
            }

            StringBuilder sb = new StringBuilder();

            System.out.println(String.format("\nProgresso - %s:", escalonador .toString()));
            System.out.println("-------------------------------------------------------");

            while (!escalonador.isTerminado()) {
                escalonador.passaTempo();
                if (escalonador.getProcessoAtual() != null) {
                    System.out.println(String.format("Tempo %3d - Processo %s", 
                                                     escalonador.getTempoAtual(), 
                                                     escalonador.getProcessoAtual().getNomeProcesso()));
                    sb.append(String.format("%s;", escalonador.getProcessoAtual().getNomeProcesso()));
                } else {
                    System.out.println(String.format("Tempo %3d - Nenhum processo", escalonador.getTempoAtual()));
                    sb.append(";");
                }
            }

            System.out.println(String.format("Sequencia Execucao: %s", sb.toString()));

            System.out.println("\nRelatorio:");
            System.out.println("-------------------------------------------------------");

            int acumulador = 0;

            for (Processo p : escalonador.getProcessos()) {
                System.out.println(String.format("Processo %s - %d", p.getNomeProcesso(), p.getEspera()));
                acumulador += p.getEspera();
            }

            System.out.println("-------------------------------------------------------");
            System.out.println(String.format("Tempo de espera medio: %.2f", 
                                             ((float)acumulador) / ((float)escalonador.getProcessos().size())));

        } catch (Exception e) {
            System.out.println("Ocorreu o seguinte erro: " + e.getMessage());
            e.printStackTrace();
        }
        
    }
    
}
