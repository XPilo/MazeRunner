/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.teseo.elCinco.building;

import java.util.ArrayList;


/**
 *
 * @author Jose
 */
public class Memory {
    public static final int FRENTE = 0;
    public static final int DERECHA = 1;
    public static final int ATRAS = 2;
    public static final int IZQUIERDA = 3;
    
    private int norte;
    private int x,y;
    private ArrayList<Casilla> camino;
    private ArrayList<Casilla> casillasNoVisitadas;
    private ArrayList<Casilla> caminoANoVisitada;
    private boolean cerrado;
    private int count;
    private Casilla destino;
    
    public Memory(){
        camino = new ArrayList<>();
        norte = FRENTE;
        x = 0;
        y = 0;
        casillasNoVisitadas = new ArrayList<>();
        cerrado = false;
        count = 0;
    }

    public int getNorte() {
        return norte;
    }

    public void setNorte(int norte) {
        this.norte = norte;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
  

    public ArrayList<Casilla> getCasillasNoVisitadas() {
        return casillasNoVisitadas;
    }

    public void setCasillasNoVisitadas(ArrayList<Casilla> casillasNoVisitadas) {
        this.casillasNoVisitadas = casillasNoVisitadas;
    }  

    public boolean isCerrado() {
        return cerrado;
    }

    public void setCerrado(boolean cerrado) {
        this.cerrado = cerrado;
    }

    public ArrayList<Casilla> getCaminoANoVisitada() {
        return caminoANoVisitada;
    }

    public void setCaminoANoVisitada(ArrayList<Casilla> caminoANoVisitada) {
        this.caminoANoVisitada = caminoANoVisitada;
    }

    public ArrayList<Casilla> getCamino() {
        return camino;
    }

    public void setCamino(ArrayList<Casilla> camino) {
        this.camino = camino;
    }
    
    public void addCasilla(Casilla casilla){
        if(camino.contains(casilla))
            camino.remove(casilla);
        camino.add(casilla);
    }
    
    public Casilla getCasilla(int x,int y){
        Casilla c = new Casilla(x, y);
        if (camino.contains(c))
            c = camino.get(camino.indexOf(c));
        else
            c = null;
        return c;
    }
    
    public Casilla getCasilla(Casilla c){
        if (camino.contains(c))
            c = camino.get(camino.indexOf(c));
        else
            c = null;
        return c;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Casilla getDestino() {
        return destino;
    }

    public void setDestino(Casilla destino) {
        this.destino = destino;
    }
     
}
