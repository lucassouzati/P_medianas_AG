/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p_medianas_AG;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Tarcisio
 */
public class Plotar  extends JPanel{
    private int qtde_pontos_demanda, qtde_candidatos;
    private int[][] coordenadas_pontos_demanda; 
    private int[][] coordenadas_candidatos; 
    private int largura, altura;
    private int[] mais_proximo;

    public Plotar(int qtde_pontos_demanda, int qtde_candidatos, int[][] coordenadas_pontos_demanda, int[][] coordenadas_candidatos, int largura, int altura, int[] mais_proximo) {
        this.qtde_pontos_demanda = qtde_pontos_demanda;
        this.qtde_candidatos = qtde_candidatos;
        this.coordenadas_pontos_demanda = coordenadas_pontos_demanda;
        this.coordenadas_candidatos = coordenadas_candidatos;
        this.largura = largura;
        this.altura = altura;
        this.mais_proximo = mais_proximo;
        setLargura(largura);
    }

    public void setLargura(int largura) {
        this.largura = largura;
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        //Desenhando os pontos de demanda gerados
        g.setColor(Color.black);
        for (int i=0; i<this.qtde_pontos_demanda;i++){
           g.fillOval(this.coordenadas_pontos_demanda[i][0], this.coordenadas_pontos_demanda[i][1], 10, 10);
           g.drawString(" "+(i), this.coordenadas_pontos_demanda[i][0], this.coordenadas_pontos_demanda[i][1]);
        }
        
        //Desenhando os candidatos gerados
        g.setColor(Color.blue);
        for (int i=0; i<this.qtde_candidatos;i++){
           g.fillOval(this.coordenadas_candidatos[i][0], this.coordenadas_candidatos[i][1], 10, 10);
           g.drawString(" "+(i), this.coordenadas_candidatos[i][0], this.coordenadas_candidatos[i][1]);
        }
        
        //Desenhando  as conexões
        g.setColor(Color.orange);
        int x1,x2,y1,y2;
        for (int i=0;i<this.qtde_pontos_demanda;i++){
            //Coordenadas das facilidades usadas
            x1 =this.coordenadas_candidatos[this.mais_proximo[i]][0];
            y1 =this.coordenadas_candidatos[this.mais_proximo[i]][1];
            
            //Coordenadas dos pontos de demanda
            x2=this.coordenadas_pontos_demanda[i][0];
            y2=this.coordenadas_pontos_demanda[i][1];
            g.drawLine(x1,y1,x2,y2);
         }
     } 
}

