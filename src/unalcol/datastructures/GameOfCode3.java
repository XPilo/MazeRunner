/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.datastructures;

import java.util.LinkedList;
import java.util.List;
import unalcol.agents.Action;
import unalcol.agents.AgentProgram;
import unalcol.agents.Percept;
import unalcol.agents.simulate.util.SimpleLanguage;
import unalcol.types.collection.vector.Vector;

/**
 *
 * @author alej0
 */
public class GameOfCode3 implements AgentProgram {
    protected SimpleLanguage language;
    protected Vector<String> cmd = new Vector<>();
    public static final int FRENTE = 0;
    public static final int DERECHA = 1;
    public static final int IZQUIERDA = 3;
    public static final int ATRAS = 2;
    
    public static List dfsNodes = new LinkedList();
    public static List nodosRecorridos = new LinkedList();
    DataNode nodoActual, nodoAnterior;
    public static Mapa mapa = new Mapa();
    public static int counter = 1;
    public static int counterX = 0;
    public static int counterY = 0;
    boolean retrocediendo;
    boolean seguidos = false;
    char direccion, direction;
    char pointing = 'N';
    
    public void setLanguage(  SimpleLanguage _language ){
        language = _language;
    }

    @Override
    public void init(){
        cmd.clear();
    }
    @Override
    public Action compute(Percept p){
        if( cmd.size() == 0 ){
            boolean PF =  ((Boolean)p.getAttribute(language.getPercept(0)));
            boolean PD = ( (Boolean) p.getAttribute(language.getPercept(1)));
            boolean PA = ( (Boolean) p.getAttribute(language.getPercept(2)));
            boolean PI = ( (Boolean) p.getAttribute(language.getPercept(3)));
            boolean MT = ( (Boolean) p.getAttribute(language.getPercept(4)));
            boolean AF =( (Boolean) p.getAttribute(language.getPercept(5)));
            boolean AD =( (Boolean) p.getAttribute(language.getPercept(6)));
            boolean AA =( (Boolean) p.getAttribute(language.getPercept(7)));
            boolean AI =( (Boolean) p.getAttribute(language.getPercept(8)));
            int d = accion(PF, PD, PA, PI, MT,AF, AD, AA, AI);
            if(d==6){
                cmd.add(language.getAction(1));
            }
            if(d==8){
                cmd.add(language.getAction(0));
            }
            else if (0 <= d && d < 4) {
              for (int i = 1; i <= d; i++) {
                cmd.add(language.getAction(3)); //rotate
              }
              cmd.add(language.getAction(2)); // advance
            }
            else{
              cmd.add(language.getAction(0)); // die
            }
        }
        String x = cmd.get(0);
        cmd.remove(0);
        return new Action(x);
    }
    public char direccionAtras(){
        char direccionAtras = pointing;
        if( pointing == 'N')
            direccionAtras = 'S';
        if( pointing == 'E')
            direccionAtras = 'O';
        if( pointing == 'S')
            direccionAtras = 'N';
        if( pointing == 'O')
            direccionAtras = 'E';
        return direccionAtras;
    }
    public char direccionIzquierda(){
        char direccionIzquierda = pointing;
        if( pointing == 'N')
            direccionIzquierda = 'O';
        if( pointing == 'O')
            direccionIzquierda = 'S';
        if( pointing == 'S')
            direccionIzquierda = 'E';
        if( pointing == 'E')
            direccionIzquierda = 'N';
        return direccionIzquierda;
    }
    public char direccionDerecha(){
        char direccionDerecha = pointing;
        if( pointing == 'N')
            direccionDerecha = 'E';
        if( pointing == 'E')
            direccionDerecha = 'S';
        if( pointing == 'S')
            direccionDerecha = 'O';
        if( pointing == 'O')
            direccionDerecha = 'N';
        return direccionDerecha;
    }
    public boolean marcarMapa( char direccion, boolean visitada ){
        boolean marco = false;
        if( direccion == 'N'){
            if ( !mapa.visited(counterY - 1, counterX) ){
                mapa.setCell(counterY - 1, counterX, new Celda(counter, visitada));
                marco = true;
            }
        }
        if( direccion == 'E'){
            if ( !mapa.visited(counterY, counterX + 1) ){
                mapa.setCell(counterY, counterX + 1, new Celda(counter, visitada));
                marco = true;
            }
        }
        if( direccion == 'S'){
            if ( !mapa.visited(counterY + 1, counterX) ){
                mapa.setCell(counterY + 1, counterX, new Celda(counter, visitada));
                marco = true;
            }
        }
        if( direccion == 'O'){
            if ( !mapa.visited(counterY, counterX - 1) ){
                mapa.setCell(counterY, counterX - 1, new Celda(counter, visitada));
                marco = true;
            }
        }
        return marco;
    }
    public int posMapaX( char direccion ){
        if( direccion == 'N' )
            return counterX;
        if( direccion == 'S' )
            return counterX;
        if( direccion == 'E' )
            return counterX + 1;
        if( direccion == 'O' )
            return counterX - 1;
        return -1;
    }
    public int posMapaY( char direccion ){
        if( direccion == 'N' )
            return counterY - 1;
        if( direccion == 'S' )
            return counterY + 1;
        if( direccion == 'E' )
            return counterY;
        if( direccion == 'O' )
            return counterY;
        return -1;
    }
    public boolean nodoAtras(){
        boolean visitar = false;
        if ( marcarMapa(direccionAtras(), false) ){
            int casillaX = posMapaX( direccionAtras() );
            int casillaY = posMapaY( direccionAtras() );
            dfsNodes.add( new DataNode(counter, direccionAtras(), ATRAS, casillaX, casillaY) );
            visitar = true;
        }
        return visitar;
    }
    public boolean nodoIzquierda(){
        boolean visitar = false;
        if ( marcarMapa(direccionIzquierda(), false) ){
            int casillaX = posMapaX( direccionIzquierda() );
            int casillaY = posMapaY( direccionIzquierda() );
            dfsNodes.add( new DataNode(counter, direccionIzquierda(), IZQUIERDA, casillaX, casillaY) );
            visitar = true;
        }
        return visitar;
    }
    public boolean nodoDerecha(){
        boolean visitar = false;
        if ( marcarMapa(direccionDerecha(), false) ){
            int casillaX = posMapaX( direccionDerecha() );
            int casillaY = posMapaY( direccionDerecha() );
            dfsNodes.add( new DataNode(counter, direccionDerecha(), DERECHA, casillaX, casillaY) );
            visitar = true;
        }
        return visitar;
    }
    public boolean nodoAdelante(){
        boolean visitar = false;
        if (marcarMapa(pointing, false)){
            int casillaX = posMapaX( pointing );
            int casillaY = posMapaY( pointing );
            dfsNodes.add( new DataNode(counter, pointing, FRENTE, casillaX, casillaY) );
            visitar = true;
        }
        return visitar;
    }
    public int movimientoEste(boolean PF, boolean PD, boolean PA, boolean PI){
        if( pointing == 'N' && !PD)
            return DERECHA;
        if( pointing == 'E' && !PF)
            return FRENTE;
        if( pointing == 'S' && !PI)
            return IZQUIERDA;
        if( pointing == 'O' && !PA)
            return ATRAS;
        return -1;
    }
    public int movimientoOeste(boolean PF, boolean PD, boolean PA, boolean PI){
        if( pointing == 'N' && !PI)
            return IZQUIERDA;
        if( pointing == 'E' && !PA)
            return ATRAS;
        if( pointing == 'S' && !PD)
            return DERECHA;
        if( pointing == 'O' && !PF)
            return FRENTE;
        return -1;
    }
    public int movimientoNorte(boolean PF, boolean PD, boolean PA, boolean PI){
        if( pointing == 'N' && !PF)
            return FRENTE;
        if( pointing == 'E' && !PI)
            return IZQUIERDA;
        if( pointing == 'S' && !PA)
            return ATRAS;
        if( pointing == 'O' && !PD)
            return DERECHA;
        return -1;
    }
    public int movimientoSur(boolean PF, boolean PD, boolean PA, boolean PI){
        if( pointing == 'N' && !PA)
            return ATRAS;
        if( pointing == 'E' && !PD)
            return DERECHA;
        if( pointing == 'S' && !PF)
            return FRENTE;
        if( pointing == 'O' && !PI)
            return IZQUIERDA;
        return -1;
    }
    public int calcularMovimiento( int x, int y, boolean PF, boolean PD, boolean PA, boolean PI ){
        int movement = -1;
        if( counterX < x && counterY == y ){
            direction = 'E';
            movement = movimientoEste( PF, PD, PA, PI );
        }
        if( counterX > x && counterY == y ){
            direction = 'O';
            movement = movimientoOeste( PF, PD, PA, PI );
        }
        if( counterX == x && counterY < y ){
            direction = 'S';
            movement = movimientoSur( PF, PD, PA, PI );
        }
        if( counterX == x && counterY > y ){
            direction = 'N';
            movement = movimientoNorte( PF, PD, PA, PI );
        }
        return movement;
    }
    public GameOfCode3() {}
    
    public void reiniciar(){
        dfsNodes = new LinkedList();
        nodosRecorridos = new LinkedList();
        nodoActual = null;
        nodoAnterior = null;
        mapa = new Mapa();
        counter = 1;
        counterX = 0;
        counterY = 0;
        seguidos = false;
        pointing = 'N';
    }

    public int accion(boolean PF, boolean PD, boolean PA, boolean PI, boolean MT, boolean AF, boolean AD, boolean AA, boolean AI) {  
        if (MT){
            reiniciar();
            return -1;
        }
        if( AF ){
            reiniciar();
            if(!PA)
                return ATRAS;
            if(!PD)
                return DERECHA;
            if(!PI)
                return IZQUIERDA;
            return 4;
        }   
        if( AD ){
            reiniciar();
            if(!PF)
                return FRENTE;
            if(!PA)
                return ATRAS;
            if(!PI)
                return IZQUIERDA;
            return 4;
        }
        if( AA ){
            reiniciar();
            if(!PF)
                return FRENTE;
            if(!PD)
                return DERECHA;
            if(!PI)
                return IZQUIERDA;
            return 4;
        }
        if( AI ){
            reiniciar();
            if(!PF)
                return FRENTE;
            if(!PD)
                return DERECHA;
            if(!PA)
                return ATRAS;
            return 4;
        } 
        retrocediendo = false;
        boolean siNodos = false;
        if( counter == 1 )
            mapa.setCell(counterY, counterX, new Celda(counter, true));
        mapa.visitCell(counterY, counterX);
        if( dfsNodes.size() > 0)
            nodoAnterior = (DataNode)dfsNodes.get( dfsNodes.size() - 1 );
        counter++;
        if(!PA)
            siNodos |= nodoAtras();
        if (!PI)
            siNodos |= nodoIzquierda();
        if (!PD)
            siNodos |= nodoDerecha();
        if (!PF)
            siNodos |= nodoAdelante();
        retrocediendo = !siNodos;
        nodoActual = (DataNode)dfsNodes.get( dfsNodes.size() - 1 );
        direccion = nodoActual.getDirection();
        if (!retrocediendo ){
            pointing = nodoActual.getDirection();
            if( pointing == 'N')
                counterY--;
            if( pointing == 'E')
                counterX++;
            if( pointing == 'S')
                counterY++;
            if( pointing == 'O')
                counterX--;
            nodosRecorridos.add(nodoActual);
            seguidos = false;
            mapa.visitCell(counterY, counterX);
            return nodoActual.getMove(); 
        }else{
            counter -= 2;
            int movimiento = -1;
            if (nodosRecorridos.size() < 2){
                seguidos = false;
            }
            if( ! seguidos ){
                pointing = direccion;
                char aux = direccion;
                if( pointing == 'N'){
                    aux = 'S'; 
                    counterY++;
                }
                if( pointing == 'E'){
                    aux = 'O';
                    counterX--;
                }
                if( pointing == 'S'){
                    aux = 'N';
                    counterY--;
                }
                if( pointing == 'O'){
                    aux = 'E';
                    counterX++;
                }    
                pointing = aux;
                movimiento = ATRAS;
                nodosRecorridos.remove(nodosRecorridos.size() - 1);
            }
            else{
                nodosRecorridos.remove(nodosRecorridos.size() - 1);
                DataNode nodoAtras = (DataNode)nodosRecorridos.get(nodosRecorridos.size() - 1);
                if( nodoAtras.equals( nodoActual )){
                    nodosRecorridos.remove(nodosRecorridos.size() - 1);
                    nodoAtras = (DataNode)nodosRecorridos.get(nodosRecorridos.size() - 1);
                }
                movimiento = calcularMovimiento( nodoAtras.getMapX(), nodoAtras.getMapY(), PF, PD, PA, PI );
                pointing = direction;
                if( pointing == 'N')
                    counterY--;
                if( pointing == 'E')
                    counterX++;
                if( pointing == 'S')
                    counterY++;
                if( pointing == 'O')
                    counterX--;
            }
            mapa.visitCell(counterY, counterX);
            seguidos = true;
            return movimiento;
        }
    }  
    
}
