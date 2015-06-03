/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.teseoeater.red;

import java.util.ArrayList;

/**
 *
 * @author DRAIKO
 */
public class Red implements java.io.Serializable {

    int nodosCapaIntermedia, entradasIntermedia, nodosSalida;
    double factorAprendizaje = 0.5;
    double balance = -1;

    //Las listas de nodos a usar en cada capa
    ArrayList<Perceptron> listaNodosSalida, listaNodosIntermedia;

    //Ya se conocen el número de salidas necesarias, por esto sólo
    //se necesitan conocer los nodos intermedios y las entradas iniciales
    public Red(int nodosIntermedia, int entradasIntermedia, int nodosSalida) {
        //Se crean las listas asegurando las capacidades mínimas pedidas
        //y se asignan los demás datos para la red
        this.listaNodosSalida = new ArrayList(nodosSalida);
        this.listaNodosIntermedia = new ArrayList(nodosIntermedia);
        this.nodosCapaIntermedia = nodosIntermedia;
        this.entradasIntermedia = entradasIntermedia;
        this.nodosSalida = nodosSalida;

        //Se crean las capas y se inicializan los pesos en cada nodo
        crear();

        inicializar();
    }

    //Se crean y guardan los nodos de cada capa
    //(internamente, cada nodo genera los pesos aleatoriamente)
    private void crear() {
        for (int i = 0; i < nodosSalida; i++)//Las entradas de la salida serán
        //El mismo número de las salidas de la intermedia más 1 (balance)
        {
            listaNodosSalida.add(new Perceptron(nodosCapaIntermedia + 1));
        }

        for (int i = 0; i < nodosCapaIntermedia; i++) {
            listaNodosIntermedia.add(new Perceptron(entradasIntermedia + 1));
        }
    }

    //Método que evalúa rápidamente una entrada y retorna la salida encontrada
    public String cargarReconocimiento(double[] entrada) {
        entradas(entrada);
        //Se activa la capa de nodos intermedia
        activacionIntermedia();
        //Se pasan los datos de salida de la intermedia a la de salida
        entradasSalida();
        //Se activa la de salida
        activacionSalida();

        //Se construye un string con los bits de salida de los nodos, y su
        //respectiva representación en decimal
        String salida = "";
        for (int k = 0; k < nodosSalida; k++) {
            salida += "" + Math.round(getActivacionSalida(k)) + ",";
        }
        double decimal = 0;
        int t = 0;
        for (int k = nodosSalida - 1; k >= 0; k--) {
            decimal += getActivacionSalida(k) * Math.pow(2, t++);
        }
        salida+= " --> "+decimal;
        
        //Se hace una aproximación a los valores que puede reconocer realmente
        salida += "\n Numero reconocido: ";
        if(decimal <= 75 )
        	salida += "50 (0000110010)";
        else if(decimal <= 150 )
        	salida += "100 (0001100100)";
        else if(decimal <= 350 )
        	salida += "200 (0011001000)";
        else if(decimal <= 750 )
        	salida += "500 (0111110100)";
        else 
        	salida += "1000 (1111101000)";
        return salida;
    }

    private void inicializar() {
        //Se inicializan los pesos de forma aleatoria
        for (Perceptron sal : listaNodosSalida) {
            for (int i = 0; i < sal.getPesos().length; i++) {
                sal.getPesos()[i] = Math.random();
            }
        }

        for (Perceptron inter : listaNodosIntermedia) {
            for (int i = 0; i < inter.getPesos().length; i++) {
                inter.getPesos()[i] = Math.random();
            }
        }
    }

    //Salida por consola, usada en pruebas iniciales
    public void verPesosSalida() {
        System.out.println("\nPesos salida: ");
        for (Perceptron sal : listaNodosSalida) {
            sal.verPesos();
            break;
        }
    }

    //Salida por consola, usada en pruebas iniciales
    public void verPesosIntermedia() {
        System.out.println("Pesos intermedia: ");
        for (Perceptron inter : listaNodosIntermedia) {
            inter.verPesos();
        }
    }

    //Se recorre toda la lista y se activa cada nodo
    public void activacionIntermedia() {
        for (Perceptron p : listaNodosIntermedia) {
            p.activacion();
        }
    }

    //Se recorre toda la lista y se activa cada nodo
    public void activacionSalida() {
        for (Perceptron p : listaNodosSalida) {
            p.activacion();
        }
    }

    //Se pasan los valores de salida (activación) de la capa intermedia a la
    //capa de salida como las entradas de esta
    public void entradasSalida() {
        int i;
        for (Perceptron pSal : listaNodosSalida) {
            for (i = 0; i < listaNodosIntermedia.size(); i++) {
                pSal.getEntradas()[i] = listaNodosIntermedia.get(i).getActivacion();
            }
            pSal.getEntradas()[i] = balance;
        }
    }

    //Se calcula el error en la capa de salida, estos valores se usan para
    //ajustar los pesos dependiendo de si la salida es la deseada o no
    public void errorCapaSalida(double aprender[]) {
        double error = 0;
        //System.out.println("Erro capa salida: "+ aprender.length + "-"+listaNodosSalida.size());
        for (int i = 0; i < listaNodosSalida.size(); i++) {
            error = (/*aprender[i][patron]*/aprender[i] - listaNodosSalida.get(i).getActivacion())
                    * listaNodosSalida.get(i).getActivacion() * (1 - listaNodosSalida.get(i).getActivacion());
            listaNodosSalida.get(i).setError(error);
        }
    }

    //Se reajustan los pesos de los nodos de salida
    public void pesosSalida() {

        for (Perceptron pSal : listaNodosSalida) {//Se recorren los nodos de salida
            for (int i = 0; i < pSal.getEntradas().length; i++) {//se recore la lista de entradas
                pSal.getPesos()[i] += (factorAprendizaje * pSal.getError() * pSal.getEntradas()[i]);
            }
        }
    }

    //Se reajustan los pesos de la capa intermedia, tomando en cuenta
    //los pesos y errores de la de salida (backpropagation)
    public void errorIntermedia() {
        double suma, error;
        Perceptron actual;
        for (int i = 0; i < listaNodosIntermedia.size(); i++) {
            suma = 0; //Se inicializa la suma
            for (int j = 0; j < listaNodosSalida.size(); j++) {
                suma += (listaNodosSalida.get(j).getError()
                        * listaNodosSalida.get(j).getPesos()[i]);
            }
            actual = listaNodosIntermedia.get(i);//nodo actual de intermedio
            error = (actual.getActivacion() * (1 - actual.getActivacion()) * suma);
            actual.setError(error);
        }
    }

    //Reajuste de pesos en la capa intermedia dependiendo del error
    //actual y las entradas dadas
    public void pesosEntrada() {
        for (Perceptron actual : listaNodosIntermedia) {
            for (int i = 0; i < actual.getEntradas().length; i++) {
                actual.getPesos()[i] += (factorAprendizaje * actual.getError()
                        * actual.getEntradas()[i]);
            }
        }
    }

    public void entradas(double[] entrada) {
        for (Perceptron actual : listaNodosIntermedia) {
            System.arraycopy(entrada, 0, actual.getEntradas(), 0, actual.getEntradas().length);
        }
    }

    //Se retorna el error total de las salidas
    public double getError() {
        double suma = 0;
        for (Perceptron pSal : listaNodosSalida) {
            suma += pSal.getError();
        }
        return Math.abs(suma);
    }

    public double getActivacionSalida(int perceptron) {
        //retorna 1 si la activacion >= 0.5, si no 0
        //return Math.round(listaNodosSalida.get(perceptron).getActivacion());

        if (listaNodosSalida.get(perceptron).getActivacion() >= 0.5) {
            return 1;
        } else {
            return 0;
        }
    }
}
