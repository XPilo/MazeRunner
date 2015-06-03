/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.teseoeater.red;

/**
 *
 * @author DRAIKO
 */
public class Perceptron implements java.io.Serializable{
    //Valores activación y error
    private double activacion, error;
    //Valores de las entradas y los pesos que les corresponden
    private double[] entradas, pesos;
    
    public Perceptron(int entradas){
        this.entradas = new double[entradas];
        this.pesos = new double[entradas];
        error = 0.0;
    }
    
    public void activacion(){
        double sum = 0;
        for(int i=0; i<entradas.length; i++)
            sum+= entradas[i]*pesos[i];
        activacion = sigmoide(sum);
    }
    
    public double getActivacion(){
        return activacion;
    }
    
    //Función de activación
    private double sigmoide(double valor){
        return (1/(1+Math.pow(Math.E, -valor/1)));
    }
    
    public double[] getEntradas(){
        return entradas;
    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }

    public double[] getPesos() {
        return pesos;
    }
    
    public void verPesos(){
        System.out.println("");
        for(int i=0;i<pesos.length;i++){
            System.out.println(""+pesos[i]+",");
        }
        System.out.println();
    }
}
