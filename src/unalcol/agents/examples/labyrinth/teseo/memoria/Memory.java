/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.teseo.memoria;

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
    
    private int ultimoMovimiento;
    private int norte;
    public Memory(){
        ultimoMovimiento = INICIO;
        norte = -1;
    }

    public int getUltimoMovimiento() {
        return ultimoMovimiento;
    }

    public void setUltimoMovimiento(int ultimoMovimiento) {
        this.ultimoMovimiento = ultimoMovimiento;
    }

    public int getNorte() {
        return norte;
    }

    public void setNorte(int norte) {
        this.norte = norte;
    }
    
    
}
