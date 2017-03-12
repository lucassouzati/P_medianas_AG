/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p_medianas_AG;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFrame;

/**
 *
 * @author Tarcisio
 */
public class Principal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        int     largura=800, 
                altura=600, 
                tot_pontos_demanda=500, 
                tot_facilidades=500, 
                tot_facilidades_a_usar=6;
        
        Pontos p = new Pontos(tot_pontos_demanda,tot_facilidades,largura, altura,tot_facilidades_a_usar);
        JFrame application = new JFrame("Todas as conexões");
        application.add(p);
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.setSize(altura,largura);
        application.setVisible(true);
        
        AlgoritmoGenetico ag = new AlgoritmoGenetico(p.getCustos(),tot_facilidades,tot_pontos_demanda,tot_facilidades_a_usar,10) ;
        int[] mais_proximo=ag.getMais_proximo();
        Plotar plo = new Plotar(tot_pontos_demanda,
                tot_facilidades,
                p.getCoordenadas_pontos_demanda(),
                p.getCoordenadas_candidatos(),
                largura, altura,mais_proximo);
        JFrame Alg_Genetico = new JFrame("Algoritmo Genético");
        Alg_Genetico.add(plo);
        
        Alg_Genetico.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Alg_Genetico.setSize(altura,largura);
        Alg_Genetico.setVisible(true);
    }
    
    public static void escritor(String path, String texto) throws IOException {
          BufferedWriter buffWrite = new BufferedWriter(new FileWriter(path, true));
          buffWrite.append(texto);
          buffWrite.close();
      }   
}
