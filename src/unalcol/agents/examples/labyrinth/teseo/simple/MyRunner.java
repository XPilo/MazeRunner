/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.teseo.simple;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import unalcol.agents.examples.labyrinth.teseo.memoria.Casilla;
import unalcol.agents.examples.labyrinth.teseo.memoria.Memory;

/**
 *
 * @author Jose
 */
public class MyRunner extends SimpleTeseoAgentProgram {
    
    private Memory mem;
    
    public MyRunner() {
        mem = new Memory();
    }
    
    /**
     *
     * @param PF Pared al frente
     * @param PD Pared derecha
     * @param PA Pared atras
     * @param PI Pared a la izquierda
     * @param MT Meta
     * @return
     */
    @Override    
    public int accion(boolean PF, boolean PD, boolean PA, boolean PI, boolean MT) {
        if (MT) return -1;
        /**
         * El automata solo se movera cuando el numero de giros k
         * lo deje mirando hacia un lado sin pared.
         * PF = 0
         * PD = 1
         * PA = 2
         * PI = 3
         */
        Map<Casilla, ArrayList<Casilla>> camino = new HashMap<>();
        Casilla c = new Casilla();
        c.setX(0);
        c.setY(0);
        camino.put(c, null);
        c.setX(0);
        c.setY(1);
        System.out.println(camino.containsKey(c)); 
//        if (!PI) return Memory.PI;
//        if (PI && !PF) return Memory.PF;
//        if (PI && PF && !PD) return Memory.PD;
//        if (PD && PF && PI) return Memory.PA;
       return -1;
    }    
}
