/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.teseo.elCinco;

import java.util.ArrayList;


/**
 *
 * @author Jose
 */
public class Casilla {
    private int x;
    private int y;
    private Casilla padre;
    private ArrayList<Casilla> vecinos;

    public Casilla() {
    }

    public Casilla(int x, int y) {
        this.x = x;
        this.y = y;
        vecinos = new ArrayList<>();
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

    public Casilla getPadre() {
        return padre;
    }

    public void setPadre(Casilla padre) {
        this.padre = padre;
    }    
    
        public ArrayList<Casilla> getVecinos() {
        return vecinos;
    }

    public void setVecinos(ArrayList<Casilla> vecinos) {
        this.vecinos = vecinos;
    }
    
    public void addVecino(Casilla vecino){
        if(!vecinos.contains(vecino))
            vecinos.add(vecino);
    }
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + this.x;
        hash = 73 * hash + this.y;
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
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }
  
}
