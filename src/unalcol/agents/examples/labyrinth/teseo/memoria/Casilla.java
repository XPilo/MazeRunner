/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.teseo.memoria;

import java.util.ArrayList;

/**
 *
 * @author Jose
 */
public class Casilla {
    private boolean PF;
    private boolean PD;
    private boolean PA;
    private boolean PI;
    private int x;
    private int y;

    public Casilla() {
    }

    public Casilla(boolean PF, boolean PD, boolean PA, boolean PI, int x, int y) {
        this.PF = PF;
        this.PD = PD;
        this.PA = PA;
        this.PI = PI;
        this.x = x;
        this.y = y;
    }
    
    

    public boolean isPF() {
        return PF;
    }

    public void setPF(boolean PF) {
        this.PF = PF;
    }

    public boolean isPD() {
        return PD;
    }

    public void setPD(boolean PD) {
        this.PD = PD;
    }

    public boolean isPA() {
        return PA;
    }

    public void setPA(boolean PA) {
        this.PA = PA;
    }

    public boolean isPI() {
        return PI;
    }

    public void setPI(boolean PI) {
        this.PI = PI;
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + (this.PF ? 1 : 0);
        hash = 11 * hash + (this.PD ? 1 : 0);
        hash = 11 * hash + (this.PA ? 1 : 0);
        hash = 11 * hash + (this.PI ? 1 : 0);
        hash = 11 * hash + this.x;
        hash = 11 * hash + this.y;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Casilla other = (Casilla) obj;
        if (this.PF != other.PF) {
            return false;
        }
        if (this.PD != other.PD) {
            return false;
        }
        if (this.PA != other.PA) {
            return false;
        }
        if (this.PI != other.PI) {
            return false;
        }
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }
    
    
    
}
