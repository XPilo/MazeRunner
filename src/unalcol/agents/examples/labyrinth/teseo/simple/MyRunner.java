/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.teseo.simple;
import unalcol.agents.examples.labyrinth.teseo.memoria.Memory;

/**
 *
 * @author Jose
 */
public class MyRunner extends SimpleTeseoAgentProgram {

    public MyRunner() {}
    
    public int ubicar_Norte(int norte, int mov){
        switch(norte){
            
        }
    return 0;
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
        Memory mem = new Memory();
        /**
         * El automata solo se movera cuando el numero de giros k
         * lo deje mirando hacia un lado sin pared.
         * PF = 0
         * PD = 1
         * PA = 2
         * PI = 3
         */
        
//        //Se realiza el primer movimiento y se ubica la posicion en la que quedo
//        //el norte en relación al frente del automata
//        if (mem.getUltimoMovimiento() == Memory.INICIO){
//            if (!PF){
//                mem.setUltimoMovimiento(Memory.PF);
//                mem.setNorte(Memory.PF);
//                return Memory.PF;
//            }
//            if (!PD){
//                mem.setUltimoMovimiento(Memory.PD);
//                mem.setNorte(Memory.PI);
//                return Memory.PD;
//            }
//            if (!PA){
//                mem.setUltimoMovimiento(Memory.PA);
//                mem.setNorte(Memory.PA);
//                return Memory.PA;
//            }
//            if (!PI){
//                mem.setUltimoMovimiento(Memory.PI);
//                mem.setNorte(Memory.PD);
//                return Memory.PI;
//            }
//        }
        if (!PD) return Memory.PD;
        if (PD && !PF) return Memory.PF;
        if (PD && PF) return Memory.PI;
        if (PD && PF && PI) return Memory.PA;
       return -1;
    }    
}
