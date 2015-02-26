/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.teseo.memoria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    //private Casilla casillaAnterior;
    private Map<Casilla, ArrayList<Casilla>> camino;
    private ArrayList<Casilla> casillasNoVisitadas;
    
    public Memory(){
        camino = new HashMap<>();
        norte = FRENTE;
        x = 0;
        y = 0;
        casillasNoVisitadas = new ArrayList<>();
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

//    public Casilla getCasillaAnterior() {
//        return casillaAnterior;
//    }
//
//    public void setCasillaAnterior(Casilla casillaAnterior) {
//        this.casillaAnterior = casillaAnterior;
//    }
  
    public Map<Casilla, ArrayList<Casilla>> getCamino() {
        return camino;
    }

    public void setCamino(Map<Casilla, ArrayList<Casilla>> camino) {
        this.camino = camino;
    }

    public ArrayList<Casilla> getCasillasNoVisitadas() {
        return casillasNoVisitadas;
    }

    public void setCasillasNoVisitadas(ArrayList<Casilla> casillasNoVisitadas) {
        this.casillasNoVisitadas = casillasNoVisitadas;
    }  
    
}
