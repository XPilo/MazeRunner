/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.teseo.memoria;

import java.util.ArrayList;
import java.util.HashMap;
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
    
    private Memory mem;
    private int giro;
    private int x;
    private int y;
    private boolean PF;
    private boolean PD;
    private boolean PA;
    private boolean PI;
    private boolean TPF;
    private boolean TPD;
    private boolean TPA;
    private boolean TPI;
    private Map<Casilla, ArrayList<Casilla>> caminoPrueba;
    private ArrayList<Casilla> vecinos;
    private ArrayList<Casilla> casillasNoVisitadas;
    private Casilla casilla;
    boolean flag;
    boolean n;
    boolean atras = false;
    
    
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
        mem = new Memory();
        caminoPrueba = mem.getCamino();
        casillasNoVisitadas = mem.getCasillasNoVisitadas();
        //giro = Memory.DERECHA;
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
    
    //@Test
    public void testRecorrido(){
        int norte;
        x = mem.getX();
        y = mem.getY();
        norte = mem.getNorte();
        casilla = new Casilla(x,y);
        vecinos = new ArrayList<>();
        flag = false;
        n = false;
        switch(norte){
            case Memory.DERECHA:
                if(!PF){
                    girar(-1, 0, Memory.FRENTE);                       
                }
                if(!PD){
                    girar(0, 1, Memory.DERECHA);
                    if(flag && n )
                        mem.setNorte(Memory.FRENTE);
                }
                if(!PI){
                    girar(0, -1, Memory.IZQUIERDA);
                    if(flag && n)
                        mem.setNorte(Memory.ATRAS);
                }
                if(!PA){
                    girar(1, 0, Memory.ATRAS);
                    if(flag && n)
                        mem.setNorte(Memory.IZQUIERDA);
                }
                break;
            case Memory.IZQUIERDA:
                if(!PF){
                    girar(1, 0, Memory.FRENTE);
                }
                if(!PD){
                    girar(0, -1, Memory.DERECHA);
                    if(flag && n)
                        mem.setNorte(Memory.ATRAS);
                }
                if(!PI){
                    girar(0, 1, Memory.IZQUIERDA);
                    if(flag && n)
                        mem.setNorte(Memory.FRENTE);
                }
                if(!PA){
                    girar(-1, 0, Memory.ATRAS);
                    if(flag && n)
                        mem.setNorte(Memory.DERECHA);
                }
                break;
            case Memory.FRENTE:
                if(!PF){
                    girar(0, 1, Memory.FRENTE);
                }
                if(!PD){
                    girar(1, 0, Memory.DERECHA);
                    if(flag && n)
                        mem.setNorte(Memory.IZQUIERDA);
                }
                if(!PI){
                    girar(-1, 0, Memory.IZQUIERDA);
                    if(flag && n)
                        mem.setNorte(Memory.DERECHA);
                }
                if(!PA){
                    girar(0, -1, Memory.ATRAS);
                    if(flag && n)
                        mem.setNorte(Memory.ATRAS);
                }
                break;
            case Memory.ATRAS:
                if(!PF){
                    girar(0, -1, Memory.FRENTE);
                }
                if(!PD){
                    girar(-1, 0, Memory.DERECHA);
                    if(flag && n)
                        mem.setNorte(Memory.DERECHA);
                }
                if(!PI){
                    girar(1, 0, Memory.IZQUIERDA);
                    if(flag && n)
                        mem.setNorte(Memory.IZQUIERDA);
                }
                if(!PA){
                    girar(0, 1, Memory.ATRAS);
                    if(flag && n)
                        mem.setNorte(Memory.FRENTE);
                }
                break;
        }
        
        caminoPrueba.put(casilla, vecinos);
        mem.setCamino(caminoPrueba);
        mem.setCasillasNoVisitadas(casillasNoVisitadas);
    }
    
    public void girar(int i, int j,int mov){
        int tx = x + i;
        int ty = y + j;
        Casilla tCasilla;
        tCasilla = new Casilla(tx,ty);
        vecinos.add(tCasilla);
        if(!flag){
            if (caminoPrueba.containsKey(tCasilla)){
                if(casillasNoVisitadas.contains(tCasilla)){
                    giro = mov;
                    flag = true;
                    casillasNoVisitadas.remove(tCasilla);
                    mem.setX(tx);
                    mem.setY(ty);
                    n = true;
                }
            }else{
                caminoPrueba.put(tCasilla, null);
                giro = mov;
                flag = true;
                mem.setX(tx);
                mem.setY(ty);
                n = true;
            }
        }else{
            if (!caminoPrueba.containsKey(tCasilla)){
                caminoPrueba.put(tCasilla, null);
                casillasNoVisitadas.add(tCasilla);
            }
            n = false;
        }
    }

    @Test
    public void pruebas(){
        PF = true;
        PD = true;
        PA = false;
        PI = false;
        testRecorrido();
        PF = true;
        PD = false;
        PA = false;
        PI = false;
        testRecorrido();
    }
}
