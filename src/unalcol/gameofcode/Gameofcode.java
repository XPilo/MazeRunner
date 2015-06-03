/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.gameofcode;
import unalcol.agents.examples.labyrinth.teseo.simple.*;


/**
 *
 * @author Jonatan
 */
public class Gameofcode extends SimpleTeseoAgentProgram {

    public static final int FRENTE = 0;
    public static final int DERECHA = 1;
    public static final int IZQUIERDA = 3;
    public static final int ATRAS = 2;
    
    public Gameofcode() {}
    @Override
    public int accion(boolean PF, boolean PD, boolean PA, boolean PI, boolean MT) {
        if (MT) return -1;
        String pasos = "";
        char frente = PF ? '0':'1';
        char derecha = PD ? '0':'1';
        char izquierda = PI ? '0':'1';
        pasos = "" + frente + derecha + izquierda; 
        System.out.println("" + pasos);
        double aleatorio = Math.random();
        int seleccion = 0;
        if( pasos.equals("100") )
            return FRENTE;
        if( pasos.equals("010") )
            return DERECHA;
        if( pasos.equals("001") )
            return IZQUIERDA;
        if( pasos.equals("000") )
            return ATRAS;
        if( pasos.equals("011") ){
            seleccion = (int)(aleatorio * 2) + 1;
            if( seleccion == 1)
                return IZQUIERDA;
            else
                return DERECHA;
        }
        if( pasos.equals("101") ){
            seleccion = (int)(aleatorio * 2) + 1;
            if( seleccion == 1)
                return IZQUIERDA;
            else
                return FRENTE;
        }
        if( pasos.equals("110") ){
            seleccion = (int)(aleatorio * 2) + 1;
            if( seleccion == 1)
                return FRENTE;
            else
                return DERECHA;
        }
        if( pasos.equals("111") ){
            seleccion = (int)(aleatorio * 3) + 1;
            if( seleccion == 1)
                return IZQUIERDA;
            else if( seleccion == 2)
                return DERECHA;
            else
                return FRENTE;
        }
        return -1;    
        // FDI   frente, derecha, izquierda.
        /*
        
        if (!PD) return 1;
        if (!PF) return 0;
        if (!PI) return 3;
        return 2;*/
    }
    
}
