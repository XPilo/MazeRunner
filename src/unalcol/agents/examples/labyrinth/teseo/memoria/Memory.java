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
    public static final int INICIO = -1;
    public static final int PF = 0;
    public static final int PD = 1;
    public static final int PA = 2;
    public static final int PI = 3;
    
    private int norte;
    private Map<Casilla, ArrayList<Casilla>> camino;
    
    public Memory(){
        camino = new HashMap<>();
    }

    public int getNorte() {
        return norte;
    }

    public void setNorte(int norte) {
        this.norte = norte;
    }

    public Map<Casilla, ArrayList<Casilla>> getCamino() {
        return camino;
    }

    public void setCamino(Map<Casilla, ArrayList<Casilla>> camino) {
        this.camino = camino;
    }

    
    
    
}
