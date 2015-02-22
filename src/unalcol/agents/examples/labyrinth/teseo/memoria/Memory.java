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
    
    private boolean inicio;
    private int norte;
    private int x,y;
    private Map<Casilla, ArrayList<Casilla>> camino;
    
    public Memory(){
        camino = new HashMap<>();
        inicio = true;
    }

    public boolean isInicio() {
        return inicio;
    }

    public void setInicio(boolean inicio) {
        this.inicio = inicio;
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

    
    public Map<Casilla, ArrayList<Casilla>> getCamino() {
        return camino;
    }

    public void setCamino(Map<Casilla, ArrayList<Casilla>> camino) {
        this.camino = camino;
    }

    
    
    
}
