package p_medianas_AG;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tarcisio
 */
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JPanel;

public class Pontos  extends JPanel{
    private int qtde_pontos_demanda, qtde_candidatos, qtde_facilidades_a_usar;
    private int[][] coordenadas_pontos_demanda; 
    private int[][] coordenadas_candidatos; 
    private double[][] custos; 
    private int largura, altura;

    public int[][] getCoordenadas_pontos_demanda() {
        return coordenadas_pontos_demanda;
    }

    public int[][] getCoordenadas_candidatos() {
        return coordenadas_candidatos;
    }

    public double[][] getCustos() {
        return custos;
    }
    
    public Pontos(int qtde_pontos_demanda, int qtde_candidatos, int largura, int altura, int qtde_facilidades_a_usar) {
        this.qtde_pontos_demanda = qtde_pontos_demanda;
        this.qtde_candidatos = qtde_candidatos;
        this.largura = largura;
        this.altura = altura;
        this.qtde_facilidades_a_usar=qtde_facilidades_a_usar;
        
        custos = new double[qtde_candidatos][qtde_pontos_demanda];
        coordenadas_candidatos=new int[qtde_candidatos][2];
        coordenadas_pontos_demanda=new int[qtde_pontos_demanda][2];
        
        aleatorio();
        calcula_custos();
        
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
        g.setColor(Color.red);
        for (int i=0; i<this.qtde_candidatos;i++){
           g.fillOval(this.coordenadas_candidatos[i][0], this.coordenadas_candidatos[i][1], 10, 10);
           g.drawString(" "+(i), this.coordenadas_candidatos[i][0], this.coordenadas_candidatos[i][1]);
        }
     } 
    
    public void aleatorio(){
        Random r = new Random();
        int x, y;
        
        //Gerando os pontos de demanda
        for (int i=0; i<this.qtde_pontos_demanda;i++){
            x=r.nextInt(this.largura);
            y=r.nextInt(this.altura);
            this.coordenadas_pontos_demanda[i][0]=x;
            this.coordenadas_pontos_demanda[i][1]=y;
        }
        
         //Gerando os locais candidados
        for (int i=0; i<this.qtde_candidatos;i++){
            x=r.nextInt(this.largura);
            y=r.nextInt(this.altura);
            this.coordenadas_candidatos[i][0]=x;
            this.coordenadas_candidatos[i][1]=y;
        }
    }

    private void calcula_custos(){
        double h,a,b;
        for (int i=0;i<this.qtde_candidatos;i++){
            for (int j=0;j<this.qtde_pontos_demanda;j++){
                a=Math.abs(this.coordenadas_candidatos[i][0]-this.coordenadas_pontos_demanda[j][0]);
                b=Math.abs(this.coordenadas_candidatos[i][1]-this.coordenadas_pontos_demanda[j][1]);
                h=Math.sqrt(a*a+b*b);
                this.custos[i][j]=h;
              }
        }
    }
}