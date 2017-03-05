/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p_medianas_AG;

/**
 *
 * @author Tarcisio
 */
public class Calculos {
        private double [][] custos;
        private int []solucao_testada;
        private int[] mais_proximo;
        private int tot_facilidades, tot_pontos_demanda;

    public void setMais_proximo(int[] mais_proximo) {
        this.mais_proximo = mais_proximo;
    }

    public int[] getMais_proximo() {
        return mais_proximo;
    }

    public void setSolucao_testada(int[] solucao_testada) {
        this.solucao_testada = solucao_testada;
    }

    public Calculos(double[][] custos, int tot_facilidades, int tot_pontos_demanda) {
        this.custos = custos;
        this.tot_facilidades = tot_facilidades;
        this.tot_pontos_demanda=tot_pontos_demanda;
        mais_proximo=new int[tot_pontos_demanda];
    }

    public void preenche_mais_proximo(){
        //Para cada ponto de demanda, informa qual a facilidade usada mais próxima
        int melhor=0;
        double custo_melhor=0;
        for (int i=0;i<this.tot_pontos_demanda;i++){
            for (int k=0;k<this.solucao_testada.length;k++){
                if (k==0){
                    melhor=this.solucao_testada[0];
                    custo_melhor=this.custos[this.solucao_testada[k]][i];
                }else{
                    if (this.custos[this.solucao_testada[k]][i]<custo_melhor){
                        melhor=this.solucao_testada[k];
                        custo_melhor=this.custos[this.solucao_testada[k]][i];
                    }
                }
            }
            this.mais_proximo[i]=melhor;
        }
    }  
    
    public double calculaCustos(){
        preenche_mais_proximo();

        double soma=0;
        for (int i=0; i<this.tot_pontos_demanda;i++){
            soma+=this.custos[this.mais_proximo[i]][i];
        }
        return soma;
    }
}
