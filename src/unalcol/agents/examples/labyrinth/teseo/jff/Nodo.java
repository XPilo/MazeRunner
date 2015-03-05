/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unalcol.agents.examples.labyrinth.teseo.jff;

import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Camilo
 */
public class Nodo {
    
    private Nodo arriba;
    private Nodo abajo;
    private Nodo derecha;
    private Nodo izquierda;
    
    //4 bits menos significativos representan las 4 paredes
    //visitado es 1, no visitado es 0
    //si ya ha sido visitado y el nodo esta nullo es que no hay via
    //de lo contrario no se ha visitado (no se ha tomado esa via)
    private byte walls;

    public Nodo() {
        this.walls = 0;
    }
    
    /**
     * 8 bits 0000
     * el orden va del bit menos significativo al mas significativo 
     * bit 0 = arriba
     * bit 1 = derecha
     * bit 2 = abajo
     * bit 3 = izquierda
     * los demas no se utilizaran
     * @param nodo
     * @param pos 
     * 
     */
    public void asignarNodos(Nodo nodo, byte pos ){
        //boolean s_seal;
        
        //s_seal = nodo.hasNoWays();
        
        if(pos == 0){
            this.arriba = nodo;
            //if(!s_seal)
                walls = (byte)(getWalls() | 1);
        }
        if(pos == 1){
            this.derecha = nodo;
            //if(!s_seal)
                walls = (byte)(getWalls() | 2);
        }
        if(pos == 2){
            this.abajo = nodo;
            //if(!s_seal)
                walls = (byte)(getWalls() | 4);
        }
        if(pos == 3){
            this.izquierda = nodo;
            //if(!s_seal)
                walls = (byte)(getWalls() | 8);
        }
        
    }

    protected boolean hasNoWays() {
        return getWalls() == 15;
    }

    protected int takeWay() {
        if((getWalls() & 1) == 0) return 0;
        if((getWalls() & 2) == 0) return 1;
        if((getWalls() & 8) == 0) return 3;
        return 2;
    }

    void sealWalls(boolean PF, boolean PD, boolean PI, byte dir) {
        if(dir == 0){
            if(PF) walls = (byte) (getWalls() | 1);
            if(PD) walls = (byte) (getWalls() | 2);
            if(PI) walls = (byte) (getWalls() | 8);
            walls =        (byte) (getWalls() | 4);
        }
        
        if(dir == 1){
            if(PF) walls = (byte) (getWalls() | 2);
            if(PD) walls = (byte) (getWalls() | 4);
            if(PI) walls = (byte) (getWalls() | 1);
            walls =        (byte) (getWalls() | 8);
        }
        
        if(dir == 2){
            if(PF) walls = (byte) (getWalls() | 4);
            if(PD) walls = (byte) (getWalls() | 8);
            if(PI) walls = (byte) (getWalls() | 2);
            walls =        (byte) (getWalls() | 1);
        }
        
        if(dir == 3){
            if(PF) walls = (byte) (getWalls() | 8);
            if(PD) walls = (byte) (getWalls() | 1);
            if(PI) walls = (byte) (getWalls() | 4);
            walls =        (byte) (getWalls() | 2);
        }
        //System.out.println("WALLZ SEALED!!!! = " + getWalls() + " , " + PI + " , " + PF + " , " + PD);
    }
    
    @Override
    public String toString() {
        boolean[] lol = new boolean[4];
        lol[0] = this.getArriba() != null;
        lol[1] = this.getDerecha() != null;
        lol[2] = this.getAbajo() != null;
        lol[3] = this.getIzquierda() != null;
        
        return "[" + lol[3] + "," + lol[2] + "," + lol[1] + "," + lol[0] + " - " + Integer.toBinaryString(getWalls()) + "]";
    }

    void sealWall(byte newDir) {
        int value = (int) Math.pow(2, newDir);
        walls = (byte) (getWalls() | value);
    }

    /**
     * @return the arriba
     */
    public Nodo getArriba() {
        return arriba;
    }

    /**
     * @return the abajo
     */
    public Nodo getAbajo() {
        return abajo;
    }

    /**
     * @return the derecha
     */
    public Nodo getDerecha() {
        return derecha;
    }

    /**
     * @return the izquierda
     */
    public Nodo getIzquierda() {
        return izquierda;
    }

    /**
     * @return the walls
     */
    public byte getWalls() {
        return walls;
    }

    void findTheWay(Stack<Byte> thePath) {
        thePath.clear();
        
        for(int i = 0; i < 8; i++) {
            this.findWay(thePath, i);
            if(thePath.size() > 0)
                return;
        }
        
    }

    private void findWay(Stack<Byte> thePath, int i) {
        if(i > 0){
            try {
                this.abajo.findWay(thePath, i - 1);
            } catch (Exception e) {
            }
            if(thePath.size() == i){
                thePath.add((byte) 2);
                return;
            }
            try {
                this.arriba.findWay(thePath, i - 1);
            } catch (Exception e) {
            }
            if(thePath.size() == i){
                thePath.add((byte) 0);
                return;
            }
            try {
                this.derecha.findWay(thePath, i - 1);
            } catch (Exception e) {
            }
            if(thePath.size() == i){
                thePath.add((byte) 1);
                return;
            }
            try {
                this.izquierda.findWay(thePath, i - 1);
            } catch (Exception e) {
            }
            if(thePath.size() == i){
                thePath.add((byte) 3);
                return;
            }
        }
        
        try {
            if (!this.abajo.hasNoWays()) {
                thePath.add((byte) 2);
                return;
            }
        } catch (Exception e) {
        }
        try {
            if (!this.arriba.hasNoWays()) {
                thePath.add((byte) 0);
                return;
            }
        } catch (Exception e) {
        }
        try {
            if (!this.derecha.hasNoWays()) {
                thePath.add((byte) 1);
                return;
            }
        } catch (Exception e) {
        }
        try {
            if (!this.izquierda.hasNoWays()) {
                thePath.add((byte) 3);
            }
        } catch (Exception e) {
        }
    }
    
}
