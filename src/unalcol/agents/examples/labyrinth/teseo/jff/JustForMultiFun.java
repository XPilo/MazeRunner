package unalcol.agents.examples.labyrinth.teseo.jff;

import java.util.Stack;
import java.util.TreeMap;

public class JustForMultiFun extends MultiTeseoAgentProgram{

    private int[] m_pos;
    private byte m_dir;
    private byte m_lDir;
    private boolean m_f;    
    private byte[] m_lastM;
    private String m_anterior;    
    Stack<Byte> thePath;
    boolean m_haveWay;
    private TreeMap<String,Nodo> m_mapa;

    public JustForMultiFun() {
        m_pos = new int[2];
        m_pos[0] = 0; m_pos[1] = 0;
        
        m_f = true;
        m_dir = 0;
        m_lDir = 0;
        m_lastM = new byte[]{-1,-1};
        this.m_mapa = new TreeMap<>();

        m_mapa.put(actualPos(), new Nodo());
        m_anterior = actualPos();
        
        thePath = new Stack<>();
        m_haveWay = false;
    }
    
    @Override
    public int accion(boolean PF, boolean PD, boolean PA, boolean PI, boolean MT, boolean AF, boolean AD, boolean AA, boolean AI) {
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
          
        return walkAlgorithm(PF, PD, PA, PI, AF, AD, AA, AI);
    }
    
    boolean bifurcacion(boolean PF, boolean PD, boolean PA, boolean PI) {
        int sum = 0;
        if (!PF) sum++;
        if (!PI) sum++;
        if (!PD) sum++;
        
        return sum > 1 && sum < 4;
    }
    
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

    private int walkAlgorithm(boolean PF, boolean PD, boolean PA, boolean PI, boolean AF, boolean AD, boolean AA, boolean AI) {
        if(PD && !PF && !PI && !PA && !AI){
            changeDir(3);
            changePos();
            saveMoves(3);
            if(m_f){
                m_lDir = m_dir;
                m_f = false;
            }
            return 3;
        }
        if(PI && !PF && !PD && !PA && !AF){
            changeDir(0);
            changePos();
            saveMoves(0);
            if(m_f){
                m_lDir = m_dir;
                m_f = false;
            }
            return 0;
        }
        if(!PF && !PD && !PI && !PA && !AI && ceros()){
            changeDir(0);
            changePos();
            saveMoves(0);
            if(m_f){
                m_lDir = m_dir;
                m_f = false;
            }
            return 0;
        }
        if(!PF && !PD && !PI && !PA && !AI && !ceros()){
            changeDir(3);
            changePos();
            saveMoves(3);
            if(m_f){
                m_lDir = m_dir;
                m_f = false;
            }
            return 3;
        }
        if (!PF && !AF){
            changeDir(0);
            changePos();
            saveMoves(0);
            if(m_f){
                m_lDir = m_dir;
                m_f = false;
            }
            return 0;
        }
        if (!PD && !AD){
            changeDir(1);
            changePos();
            saveMoves(1);
            if(m_f){
                m_lDir = m_dir;
                m_f = false;
            }
            return 1;
        }
        if (!PI && !AI){
            changeDir(3);
            changePos();
            saveMoves(3);
            if(m_f){
                m_lDir = m_dir;
                m_f = false;
            }
            return 3;
        }
        if(!AA || !PA){
            changeDir(2);
            changePos();
        }
        saveMoves(2);
        if(m_f){
            m_lDir = m_dir;
            m_f = false;
        }
        return 2;
    }

    private String actualPos() {
        return m_pos[0] + "," + m_pos[1];
    }

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
