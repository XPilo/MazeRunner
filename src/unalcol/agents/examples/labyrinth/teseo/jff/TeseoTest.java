package unalcol.agents.examples.labyrinth.teseo.jff;

import java.util.Stack;
import java.util.TreeMap;

public class TeseoTest extends SimpleTeseoAgentProgramNew{
    
    /**
     * posición actual
     */
    private int[] m_pos;
    
    /**
     * Dirección en la que esta mirando, absoluta en base al inicio
     * inicializandolo con 0 hacia donde comienza mirando.
     */
    private byte m_dir;
    
    /**
     * Dirección de la cual sale de un nodo.
     */
    private byte m_lDir;
    
    /**
     * Flag, para actualizar @m_lDir.
     */
    private boolean m_f;
    
    private byte[] m_lastM;
    
    /**
     * Key en el Treemap referente al ultimo nodo visitado.
     */
    private String m_anterior;
    
    Stack<Byte> thePath;
        
    boolean m_haveWay;
    
    /**
     * Estructura de Datos con los NODOS
     */
    private TreeMap<String,Nodo> m_mapa;
    
    

    public TeseoTest() {
        //Inicializacion de Variables
        m_pos = new int[2];
        m_pos[0] = 0; m_pos[1] = 0;
        
        m_f = true;
        m_dir = 0;
        m_lDir = 0;
        m_lastM = new byte[]{-1,-1};
        this.m_mapa = new TreeMap<>();
        
        //Crear nodo Incial (0,0) con las paredes que se tengan en el nodo
        //guiando de tal manera que @true seria si hay camino y @false si no hay camino
        //Nodo actual = New Nodo();
        m_mapa.put(actualPos(), new Nodo());
        m_anterior = actualPos();
        
        thePath = new Stack<>();
        m_haveWay = false;
    }
    
  
    
    @Override
    public int accion(boolean PF, boolean PD, boolean PA, boolean PI, boolean MT) {
        
        Nodo lux;
        int m_way;
        
        //Inicio
        //System.out.println(m_dir + " - " + m_lDir + " - " + actualPos() + " - " + m_anterior + " - " + m_mapa);
        
        if (MT){
            this.init();
            return -1;
        }
        
        
        
        if(bifurcacion(PF, PD, PA, PI)){
            //Existe nodo en esta posicion?
            if(m_mapa.containsKey(actualPos())){
                
            //Si!
                if(m_haveWay){
                    if(thePath.size() == 1){
                        m_haveWay = false;
                    }
                    //System.out.println("Took way: " + thePath);
                    return takeWay();
                }
                
                lux = m_mapa.get(actualPos());
                
                if(m_anterior == null ? actualPos() != null : !m_anterior.equals(actualPos())){
                    m_mapa.get(actualPos()).asignarNodos(m_mapa.get(m_anterior), newDir(2));
                    m_mapa.get(m_anterior).asignarNodos(m_mapa.get(actualPos()), m_lDir);
                }
                else
                    lux.sealWall(newDir(2));
                    
                //Tiene vias?
                //System.out.println("Lux has NO F*CKING WAYZ!!! = " + lux.hasNoWays() + " Walls: " + lux.getWalls());
                if(!lux.hasNoWays()){
                //SI!!
                    m_way = takeThisDir(lux.takeWay());
                    //changeDir(<Valor del return subsiguiente>);
                    changeDir(m_way);
                    //Take one of those --AQUI RETURN--
                    m_anterior = actualPos();
                    changePos();
                    //System.out.println("The Direction out of old node: " + m_dir);
                    m_lDir = m_dir;
                    
                    return m_way;
                }
                else{
                //NO!!
                    
                    //find new Path through the nodes
                    lux.findTheWay(thePath);
                    m_haveWay = true;
                    
                    //System.out.println("Found the WAY = " + thePath + " in node " + actualPos());
                    
                    m_way = takeThisDir(thePath.pop());
                    if(thePath.size() == 0)
                        m_haveWay = false;
                    
                    changeDir(m_way);
                    m_lDir = m_dir;
                    m_anterior = actualPos();
                    changePos();
                    return m_way;
                }
            }
            //NO!!
            else {
                //Create New Node
                m_mapa.put(actualPos(), new Nodo());
                m_mapa.get(actualPos()).sealWalls(PF, PD, PI, m_dir);
                m_mapa.get(actualPos()).asignarNodos(m_mapa.get(m_anterior), newDir(2));
                m_mapa.get(m_anterior).asignarNodos(m_mapa.get(actualPos()), m_lDir);
                m_anterior = actualPos();
                m_f = true;
            }
        }
          
        return walkAlgorithm(PF, PD, PA, PI);
    }
    
    /**
     * Resive los parametros de las paredes para la posicion actual y retorna
     * @true si esta posicion tiene una bifurcacion o @false en caso contrario.
     * @param PF
     * @param PD
     * @param PA
     * @param PI
     * @return 
     */
    boolean bifurcacion(boolean PF, boolean PD, boolean PA, boolean PI) {
        int sum = 0;
        if (!PF) sum++;
        if (!PI) sum++;
        if (!PD) sum++;
        
        return sum > 1 && sum < 4;
    }
    
    /**
     * Funcion que cambia los valores de posicion del agente, para asi saber en
     * cual posicion estamos actualmente.
     * @param pos es aquel que posee la posicion actual la cual vamos a cambiar
     * al final de la funcion.
     * @param dir es aquel que posee la direccion en la cual esta viendo el
     * agente actualmente.
     */
    private void changePos(){
        switch(m_dir){
            case 0:
                m_pos[1]++;
                break;
            case 1:
                m_pos[0]++;
                break;
            case 2:
                m_pos[1]--;
                break;
            case 3:
                m_pos[0]--;
                break;
            default:
                break;
        }
    }

    private void changeDir(int i) {
        m_dir = (byte) ((m_dir+i)%4);
    }

    private int walkAlgorithm(boolean PF, boolean PD, boolean PA, boolean PI) {
        if(PD && !PF && !PI && !PA){
            changeDir(3);
            changePos();
            saveMoves(3);
            if(m_f){
                m_lDir = m_dir;
                m_f = false;
            }
            return 3;
        }
        if(PI && !PF && !PD && !PA){
            changeDir(0);
            changePos();
            saveMoves(0);
            if(m_f){
                m_lDir = m_dir;
                m_f = false;
            }
            return 0;
        }
        if(!PF && !PD && !PI && !PA && ceros()){
            changeDir(0);
            changePos();
            saveMoves(0);
            if(m_f){
                m_lDir = m_dir;
                m_f = false;
            }
            return 0;
        }
        if(!PF && !PD && !PI && !PA && ceros()){
            changeDir(3);
            changePos();
            saveMoves(3);
            if(m_f){
                m_lDir = m_dir;
                m_f = false;
            }
            return 3;
        }
        if (!PF){
            changeDir(0);
            changePos();
            saveMoves(0);
            if(m_f){
                m_lDir = m_dir;
                m_f = false;
            }
            return 0;
        }
        if (!PD){
            changeDir(1);
            changePos();
            saveMoves(1);
            if(m_f){
                m_lDir = m_dir;
                m_f = false;
            }
            return 1;
        }
        if (!PI){
            changeDir(3);
            changePos();
            saveMoves(3);
            if(m_f){
                m_lDir = m_dir;
                m_f = false;
            }
            return 3;
        }
        
        changeDir(2);
        changePos();
        saveMoves(2);
        if(m_f){
            m_lDir = m_dir;
            m_f = false;
        }
        return 2;
    }

    private int walkAlgorithmOld(boolean PF, boolean PD, boolean PA, boolean PI) {
        if (!PD){
            changeDir(1);
            changePos();
            if(m_f){
                m_lDir = m_dir;
                m_f = false;
            }
            return 1;
        }
        if (!PF){
            changeDir(0);
            changePos();
            if(m_f){
                m_lDir = m_dir;
                m_f = false;
            }
            return 0;
        }
        if (!PI){
            changeDir(3);
            changePos();
            if(m_f){
                m_lDir = m_dir;
                m_f = false;
            }
            return 3;
        }
        
        changeDir(2);
        changePos();
        if(m_f){
            m_lDir = m_dir;
            m_f = false;
        }
        return 2;
    }
    
    /**
     * Retorna un @String como la key para el treemap
     * @return 
     */
    private String actualPos() {
        return m_pos[0] + "," + m_pos[1];
    }

    /**
     * Retorna una posición de guardado para @Nodo dependiendo de la dirección actual
     * @param i
     * @return 
     */
    private byte newDir(int i) {
        return (byte) ((m_dir + i) % 4);
    }

    private int takeThisDir(int takeWay) {
        int x;
        x = takeWay - m_dir;
        if(x < 0) x += 4;
        return x;
    }

    private int takeWay() {
        int way;
        //System.out.println(thePath);
        way = takeThisDir(thePath.pop());
        
        changeDir(way);
        m_lDir = m_dir;
        m_anterior = actualPos();
        changePos();
        
        return way;
    }
    
    private void saveMoves(int n){
        m_lastM[0] = m_lastM[1];
        m_lastM[1] = (byte) n;
    }
    
    private boolean ceros() {
        if(m_lastM[0] == 0 && m_lastM[1] == 0)
            return true;
        else
            return false;
    }

}
