/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LeituraDeArquivo;

import Entidades.Processo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pedro
 */
public class LeitorDeArquivo {
    /**
     * Valida se a primeira linha do CSV está de acordo com a estrutura para
     * conversão em Processo
     * @see Processo
     * @param csv CSV a ser avaliado
     * @return Verdadeiro se corresponde a estrutura esperada.
     * @throws Exception Caso o número de colunas não esteja correto, 
     *                   ou se algum campo estiver no formato errado
     * @throws IOException Qualquer problema relacionado a entrada e saída do CSV.
     */
    public static boolean validarCSV(File csv) throws Exception, IOException {
        BufferedReader br = new BufferedReader(new FileReader(csv));
        
        String primeiraLinha = br.readLine(); // Nome;TempoEntrada;TempoExecucao;
        String[] conteudo = primeiraLinha.split(";");

        try {
            if (conteudo.length < 3)
                throw new Exception("Número de dados/colunas inválido!");

            for (int i = 0; i < conteudo.length; i++)
                conteudo[i] = conteudo[i].trim();

            // Caso algum dos campos abaixo não seja inteiro será lançada uma exceção.
            Integer.valueOf(conteudo[1]);
            Integer.valueOf(conteudo[2]);
            //Integer.valueOf(conteudo[3]);

        } finally {
            br.close();
        }
        return true;
    }

    /**
     * Valida o arquivo, o lê e converte para uma lista de processos.
     * 
     * @see #validarCSV(File)
     * 
     * @param csv Arquivo CSV a se converter
     * @return Lista de processos
     * @throws Exception
     * @throws IOException
     */
    public static List<Processo> csvParaProcessos(File csv) throws Exception, IOException {
        if (!validarCSV(csv))
            return null;

        BufferedReader br = new BufferedReader(new FileReader(csv));

        List<Processo> processos = new ArrayList<Processo>();
        Processo processo = null;
        //String linha = null;
        String[] campos = null;

        int tempoChegada;
        int duracao;

        //int i;
        try {
            for (String linha = br.readLine(); linha != null; linha = br.readLine()) { // Nome;TempoEntrada;TempoExecucao;
                if (linha.length() > 0) {
                    campos = linha.split(";");

                    for (int i = 0; i < campos.length; i++)
                        campos[i] = campos[i].trim(); //Remove espaços em branco no início e final do campos[i]

                    tempoChegada = Integer.valueOf(campos[1]);
                    duracao = Integer.valueOf(campos[2]);
                    //prioridade = Integer.valueOf(campos[3]);

                    processo = new Processo(campos[0], duracao, tempoChegada);
                    processos.add(processo);

                }
            }
        } finally {
            br.close();
        }

        return processos;
    }
}
