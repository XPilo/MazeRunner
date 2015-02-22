/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.teseo.memoria;

import java.util.ArrayList;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jose
 */
public class MemoryTest {
    
    private static Casilla casillaInicio;
    private static Memory mem;
    public MemoryTest() {
    }
    
    
    @BeforeClass
    public static void setUpClass() {
        //public Casilla(boolean PF, boolean PD, boolean PA, boolean PI, int x, int y)
        casillaInicio = new Casilla(true, false, false, true, 0,0);
        mem = new Memory();
        mem.setNorte(Memory.FRENTE);
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getNorte method, of class Memory.
     */
    //@Test
    public void testGetNorte() {
        System.out.println("getNorte");
        Memory instance = new Memory();
        int expResult = 0;
        int result = instance.getNorte();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setNorte method, of class Memory.
     */
    //@Test
    public void testSetNorte() {
        System.out.println("setNorte");
        int norte = 0;
        Memory instance = new Memory();
        instance.setNorte(norte);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNombreMap method, of class Memory.
     */
    //@Test
    public void testGetCamino() {
        System.out.println("getNombreMap");
        Memory instance = new Memory();
        Map<Casilla, ArrayList<Casilla>> expResult = null;
        Map<Casilla, ArrayList<Casilla>> result = instance.getCamino();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setNombreMap method, of class Memory.
     */
    //@Test
    public void testSetCamino() {
        System.out.println("setNombreMap");
        Map<Casilla, ArrayList<Casilla>> nombreMap = null;
        Memory instance = new Memory();
        instance.setCamino(nombreMap);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    @Test
    public void testInicio(){
        //  mem.isInicio = true
        //  si es true se coloca el norte hacia el frente del automata
        //  se calcula el moviento del automata y se ubica el norte deacuerdo a
        //  este movimiento.
        if (mem.isInicio()){
            int giro = Memory.DERECHA;
            int x = 0,y = 0;
            switch(giro){
                case Memory.DERECHA:
                    mem.setNorte(Memory.IZQUIERDA);
                    x++;
                    break;
                case Memory.IZQUIERDA:
                    mem.setNorte(Memory.DERECHA);
                    x--;
                    break;
                case Memory.ATRAS:
                    mem.setNorte(Memory.ATRAS);
                    y--;
                    break;
                default: //se mueve hacia el frente
                    y++;
            }
            
            mem.setX(x);
            mem.setY(y);
            mem.setInicio(false);
            Map<Casilla, ArrayList<Casilla>> caminoPrueba = mem.getCamino();
            caminoPrueba.put(casillaInicio, null);
            System.out.println(caminoPrueba.containsKey(casillaInicio));
        }
    }
    
}
