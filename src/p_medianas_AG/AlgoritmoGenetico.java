/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p_medianas_AG;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;

/**
 *
 * @author Tarcisio
 */
public class AlgoritmoGenetico {
    private double [][]custos;
    private int tot_facilidades, tot_pontos_demanda, qtde_facilidades_usar,tamanho_populacao, pior_fitness, sem_melhoria;
    private int[][]populacao;
    private int[]vlr_fitness;
    private String nome_arq_texto;
    

    public AlgoritmoGenetico(double[][] custos, int tot_facilidades, int tot_pontos_demanda, int qtde_facilidades_usar, int tamanho_populacao) {
        this.custos = custos;
        this.tot_facilidades = tot_facilidades;
        this.tot_pontos_demanda = tot_pontos_demanda;
        this.qtde_facilidades_usar = qtde_facilidades_usar;
        this.tamanho_populacao=tamanho_populacao;
        this.populacao= new int[tamanho_populacao][qtde_facilidades_usar];
        this.vlr_fitness=new int[tamanho_populacao];
        this.nome_arq_texto="Alocacao_Facilidades_AG";
        run_AG();
    }
    private void run_AG() {
        populacao_inicial();//Cria a população Inicial
        this.sem_melhoria=0;
        int soma_fitness=0;
        int[] pais = new int[2];
        int[] filho=new int[this.qtde_facilidades_usar];
        
        while (this.sem_melhoria<200){
            soma_fitness=fitness();
            pais=roleta(soma_fitness);
            filho=cruzamento(pais);
            filho=mutacao(filho);
            atualiza_populacao(filho);
        }
    }
    private void populacao_inicial(){
        Random r = new Random();
        int i, aux;
        
        //Esvaziando a populacao inicial
        for (int t=0;t<this.tamanho_populacao;t++){
            for (i=0;i<this.qtde_facilidades_usar;i++){
                 this.populacao[t][i]=-1;
            }
        }
        boolean usado;
        int j;
        for (i=0;i<this.tamanho_populacao;i++){
            j=0;
            while(j<this.qtde_facilidades_usar){
                aux=r.nextInt(this.tot_facilidades);
                usado=false;
                for (int k=0;k<this.qtde_facilidades_usar;k++){
                    if (this.populacao[i][k]==aux){
                        usado=true;
                    }
                }
                if (!usado){
                    this.populacao[i][j]=aux;
                    j++;
                }
            }
        }
    }
    private int fitness() {
        Calculos c = new Calculos(this.custos,this.tot_facilidades,this.tot_pontos_demanda);
        int []aux= new int[this.qtde_facilidades_usar];
        for (int i=0;i<this.tamanho_populacao;i++){
            //Armazenando as facilidades para cada linha, no vetor aux
            System.arraycopy(this.populacao[i], 0, aux, 0, this.qtde_facilidades_usar);
            
            //Calculando o fitnes de cada indivíduo
            c.setSolucao_testada(aux);
            this.vlr_fitness[i]=(int)Math.round(c.calculaCustos()); 
        }
        
        //A função objetivo procura minimizar os custos, então, na verdade o melhor fitness é aquele que tem um menor custo
        //Procurando o pior fitness (maior custo)
        this.pior_fitness=this.vlr_fitness[0];
        int soma=0;
        for (int i=1;i<this.tamanho_populacao;i++){
            if (vlr_fitness[i]>this.pior_fitness){
                this.pior_fitness=vlr_fitness[i];
            }
        }
        
        //Invertendo as coisas
        for (int i=0;i<this.tamanho_populacao;i++){
            vlr_fitness[i]=this.pior_fitness-vlr_fitness[i];
            soma+=vlr_fitness[i];
        }
        return soma;//Retorna o somatório de todos os fitness para ser usado na roleta
    }
    private int[] roleta(int  somatorio_fitness) {
        int[] pais = new int[2];
        int sorteado;
        Random r = new Random();
        
        //Sorteando o pai e mae
        for (int i=0;i<2;i++){
            sorteado=r.nextInt(somatorio_fitness);
            for (int k=0;k<this.tamanho_populacao;k++){
                sorteado-=this.vlr_fitness[k];
                if (sorteado<=0){
                    pais[i]=k;//Indica a linha da matriz que foi sorteada, ou seja, mostra o individuo
                    k=this.tamanho_populacao+1;//Para sair
                }
            }
        }
        return pais; //Indica onde estão os pais na matriz
    }
    private int[] cruzamento(int[] pais){
        int[] filho = new int[this.qtde_facilidades_usar];
        Random r = new Random();
        int ponto_corte=0;
        while ((ponto_corte==0)||(ponto_corte==this.qtde_facilidades_usar)){        
                ponto_corte=r.nextInt(this.qtde_facilidades_usar);
        }
        //Genes herdados do Pai
        System.arraycopy(this.populacao[pais[0]], 0, filho, 0, ponto_corte);
        
        //Genes herdados da mãe
        System.arraycopy(this.populacao[pais[1]], ponto_corte, filho, ponto_corte, this.qtde_facilidades_usar-ponto_corte);
        return filho;
    }
    
    private int[] mutacao(int[] filho){
        Random r = new Random();
        
        //Percorrendo todo o filho e verificando se ele possui facilidades duplicadas
        boolean repetiu;
        int j=0;
        while(j<this.qtde_facilidades_usar){
            repetiu=false;
            for (int k=j+1;k<this.qtde_facilidades_usar;k++){
                if (filho[j]==filho[k]){
                    repetiu=true;
                }
            }
            if (repetiu){//Se tiver repetido, sorteia uma nova facilidade para a posição j
                filho[j]=r.nextInt(this.tot_facilidades);
            }else{
                j++;
            }
        }
        return filho;
    }
    private void atualiza_populacao(int[] filho){
        int menor=this.vlr_fitness[0], indice=0;//O pior é o menor fitness, pois já foi invertido na funcao fitness
        
        for (int i=1;i<this.tamanho_populacao;i++){
            if (this.vlr_fitness[i]<menor){
                menor=this.vlr_fitness[i];
                indice=i;
            }
        }
        //Só atualiza se este indivíduo for melhor
        Calculos c2 = new Calculos(this.custos,this.tot_facilidades,this.tot_pontos_demanda);
        c2.setSolucao_testada(filho);
       int fitness_filho=this.pior_fitness-(int)Math.round(c2.calculaCustos()); 
        
        if (fitness_filho<0){
            //Copiando o filho para o lugar do pior indivíduo
            System.arraycopy(filho, 0, this.populacao[indice], 0, this.qtde_facilidades_usar);
        }else{
            this.sem_melhoria+=1;
        }
    }
    public int[] getMais_proximo(){
        int maior=this.vlr_fitness[0], indice=0;
        for (int i=1;i<this.tamanho_populacao;i++){
            if (this.vlr_fitness[i]>maior){
                maior=this.vlr_fitness[i];
                indice=i;
            }
        }
        int[] melhor=this.populacao[indice];
        Calculos c3 = new Calculos(this.custos,this.tot_facilidades,this.tot_pontos_demanda);
        c3.setSolucao_testada(melhor);
        c3.preenche_mais_proximo();
        
        int[] retorno=c3.getMais_proximo();
        return retorno;
    }
    public static void escritor(String path, String texto) throws IOException {
          BufferedWriter buffWrite = new BufferedWriter(new FileWriter(path));
          buffWrite.append(texto + "\n");
          buffWrite.close();
      }    
   
}
