/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.teseoeater.red;

import java.util.ArrayList;

/**
 *Clase que encapsula en un objeto la salida deseada dados unos
 * patrones de entrada
 * @author DRAIKO
 */
public class SalidaDeseada implements java.io.Serializable{
    double[] salida;
    ArrayList<double[]> entradas;//entradas posibles
    
    /*
    Se crea pasándole el arreglo con la salida deseada
    */
    public SalidaDeseada(double[] salida){
        this.salida = salida;
        entradas = new ArrayList<>();
    }

    public double[] getSalida() {
        return salida;
    }
    
    public ArrayList<double[]> getEntradas() {
        return entradas;
    }
    
    /*
    Se añaden las entradas posibles para esta salida
    */
    public void addEntrada(double[] entrada) {
        this.entradas.add(entrada);
    }

    @Override
    public String toString() {
        String str = "";
        str+="Salida deseada: [";
        for(int i=0; i<salida.length-1; i++)
            str+=Math.round(salida[i])+",";
        str+=Math.round(salida[salida.length-1])+"]\n";
        str+="Entradas asociadas:\n";
        for(double[] lista: entradas ){
            for(int i=0; i<lista.length; i++)
                str+=Math.round(lista[i]) + " ";
            str+="\n";
        }
        return str;
    }
}
