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
    
    private Casilla casillaInicio;
    private Memory mem;
    private int giro;
    private int x;
    private int y;
    private boolean PF;
    private boolean PD;
    private boolean PA;
    private boolean PI;
    //paredes relativas al norte
    private boolean RPF;
    private boolean RPD;
    private boolean RPA;
    private boolean RPI;
    private Casilla casillaAnterior;
    Map<Casilla, ArrayList<Casilla>> caminoPrueba;
    ArrayList<Casilla> vecinos;
    public MemoryTest() {
    }
    
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        //public Casilla(boolean PF, boolean PD, boolean PA, boolean PI, int x, int y)
        PF = true;
        PD = false;
        PA = false;
        PI = true;
        casillaInicio = new Casilla(PF, PD, PA, PI, 0,0);
        mem = new Memory();
        giro = Memory.DERECHA;
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
            x = 0;
            y = 0;
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
            caminoPrueba = mem.getCamino();
            caminoPrueba.put(casillaInicio, null);
            mem.setCasillaAnterior(casillaInicio);
            for (Casilla c: caminoPrueba.keySet())
                System.out.println("x = " + c.getX() + " y = " + c.getY());
            System.out.println("Norte " + mem.getNorte());
        }
        if(mem.isInicio() == false){
            int norte;
            x = mem.getX();
            y = mem.getY();
            norte = mem.getNorte();
            //valores que recibe la funcion int accion(boolean PF, boolean PD, boolean PA, boolean PI, boolean MT)
            //creamos una nueva casilla
            PF = false;
            PD = true;
            PA = false;
            PI = true;
            
            switch(norte){
                case Memory.DERECHA:
                    RPF = PD;
                    RPD = PA;
                    RPA = PI;
                    RPI = PF;
                    break;
                case Memory.ATRAS:
                    RPF = PA;
                    RPD = PI;
                    RPA = PF;
                    RPI = PD;
                    break;
                case Memory.IZQUIERDA:
                    RPF = PI;
                    RPD = PF;
                    RPA = PD;
                    RPI = PA;
                    break;
                    
            }
            Casilla casilla = new Casilla(RPF, RPD, RPA, RPI, x,y);
            vecinos = new ArrayList<>();
            vecinos.add(mem.getCasillaAnterior());
            caminoPrueba.put(casilla, vecinos);
            System.out.println("siguiente");
            for (Casilla c: caminoPrueba.keySet()){
                System.out.println("PF " + c.isPF()+ " PD " + c.isPD() + " PA " + c.isPA() + " PI " + c.isPI());
                System.out.println("x = " + c.getX() + " y = " + c.getY());
            }
        }
    }
    
}
