
package unalcol.agents.examples.labyrinth.teseo.elCinco;
import java.util.ArrayList;
import unalcol.agents.examples.labyrinth.teseo.simple.SimpleTeseoAgentProgram;

/**
 *
 * @author Jose
 */
public class MyRunner extends SimpleTeseoAgentProgram {
    
       
    private Memory mem;
    private int x;
    private int y;
    private int giro;
    private ArrayList<Casilla> camino;
    private ArrayList<Casilla> casillasNoVisitadas;
    private Casilla casilla;
    boolean flag;
    boolean n;
    boolean cerrado;
    private int norte;

    
    public MyRunner() {
        mem = new Memory();
        camino = mem.getCamino();
        casillasNoVisitadas = mem.getCasillasNoVisitadas();
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
        if (MT){ 
            mem = new Memory();
            return -1;
            }
        /**
         * El automata solo se movera cuando el numero de giros k
         * lo deje mirando hacia un lado sin pared.
         * PF = 0
         * PD = 1
         * PA = 2
         * PI = 3
         */
        if (!mem.isCerrado())
            recorrerLaberinto(PF, PD, PA, PI);
        else
            giro = recorrerCamino();
       return giro;
    }
    
    /**
     * 
     * @param PF indica si hay una pared al frente
     * @param PD indica si hay pared a la derecha
     * @param PA indica si hay pared atras
     * @param PI indica si hay pared a la izquierda
     * 
     * Hace el recorrido normal del laberinto
     * 
     */
    public void recorrerLaberinto(boolean PF, boolean PD, boolean PA, boolean PI){
        x = mem.getX();
        y = mem.getY();
        norte = mem.getNorte();
        casilla = new Casilla(x,y);
        //vecinos = new ArrayList<>();
        flag = false;
        n = false;
        switch(norte){
            case Memory.DERECHA:
                if(!PF){
                    girar(-1, 0, Memory.FRENTE);
                    if(mem.isCerrado())
                        break;
                }
                if(!PD){
                    girar(0, 1, Memory.DERECHA);
                    if(mem.isCerrado())
                        break;
                    if(flag && n )
                        mem.setNorte(Memory.FRENTE);
                }
                if(!PI){
                    girar(0, -1, Memory.IZQUIERDA);
                    if(mem.isCerrado())
                        break;
                    if(flag && n)
                        mem.setNorte(Memory.ATRAS);
                }
                if(!PA){
                    girar(1, 0, Memory.ATRAS);
                    if(mem.isCerrado())
                        break;
                    if(flag && n)
                        mem.setNorte(Memory.IZQUIERDA);
                }
                break;
            case Memory.IZQUIERDA:
                if(!PF){
                    girar(1, 0, Memory.FRENTE);
                    if(mem.isCerrado())
                        break;
                }
                if(!PD){
                    girar(0, -1, Memory.DERECHA);
                    if(mem.isCerrado())
                        break;
                    if(flag && n)
                        mem.setNorte(Memory.ATRAS);
                }
                if(!PI){
                    girar(0, 1, Memory.IZQUIERDA);
                    if(mem.isCerrado())
                        break;
                    if(flag && n)
                        mem.setNorte(Memory.FRENTE);
                }
                if(!PA){
                    girar(-1, 0, Memory.ATRAS);
                    if(mem.isCerrado())
                        break;
                    if(flag && n)
                        mem.setNorte(Memory.DERECHA);
                }
                break;
            case Memory.FRENTE:
                if(!PF){
                    girar(0, 1, Memory.FRENTE);
                    if(mem.isCerrado())
                        break;
                }
                if(!PD){
                    girar(1, 0, Memory.DERECHA);
                    if(mem.isCerrado())
                        break;
                    if(flag && n)
                        mem.setNorte(Memory.IZQUIERDA);
                }
                if(!PI){
                    girar(-1, 0, Memory.IZQUIERDA);
                    if(mem.isCerrado())
                        break;
                    if(flag && n)
                        mem.setNorte(Memory.DERECHA);
                }
                if(!PA){
                    girar(0, -1, Memory.ATRAS);
                    if(mem.isCerrado())
                        break;
                    if(flag && n)
                        mem.setNorte(Memory.ATRAS);
                }
                break;
            case Memory.ATRAS:
                if(!PF){
                    girar(0, -1, Memory.FRENTE);
                    if(mem.isCerrado())
                        break;
                }
                if(!PD){
                    girar(-1, 0, Memory.DERECHA);
                    if(mem.isCerrado())
                        break;
                    if(flag && n)
                        mem.setNorte(Memory.DERECHA);
                }
                if(!PI){
                    girar(1, 0, Memory.IZQUIERDA);
                    if(mem.isCerrado())
                        break;
                    if(flag && n)
                        mem.setNorte(Memory.IZQUIERDA);
                }
                if(!PA){
                    girar(0, 1, Memory.ATRAS);
                    if(mem.isCerrado())
                        break;
                    if(flag && n)
                        mem.setNorte(Memory.FRENTE);
                }
                break;
        }
        if(!mem.isCerrado()){
            if (camino.contains(casilla)){
                camino.remove(casilla);
            }
            camino.add(casilla);
            mem.setCamino(camino);
            mem.setCasillasNoVisitadas(casillasNoVisitadas);
        }else{
            //mem.setCaminoANoVisitada(caminoANoVisitada());
            //recorrerCamino();
        }
    }
    /**
     * 
     * @param i 
     * @param j
     * @param mov 
     * 
     * Realiza el giro y almacena en la memoria la casilla con su vecino
     */
    public void girar(int i, int j,int mov){
        int tx = x + i;
        int ty = y + j;
        Casilla tCasilla = new Casilla(tx,ty);
        casilla.addVecino(tCasilla);
        tCasilla.addVecino(casilla);
        if(!flag){
            if (camino.contains(tCasilla)){
                camino.get(camino.indexOf(tCasilla)).addVecino(casilla);
                if(casillasNoVisitadas.contains(tCasilla)){
                    giro = mov;
                    flag = true;
                    casillasNoVisitadas.remove(tCasilla);
                    mem.setX(tx);
                    mem.setY(ty);
                    n = true;
                }else{
                    mem.setCerrado(true);
                    //camino.put(tCasilla, vecinosTCasilla);
                    caminoANoVisitada();
                    giro = recorrerCamino();
                }
            }else{
                camino.add(tCasilla);
                giro = mov;
                flag = true;
                mem.setX(tx);
                mem.setY(ty);
                n = true;
            }
        }else{
            if (!camino.contains(tCasilla)){
                camino.add(tCasilla);
                casillasNoVisitadas.add(tCasilla);
            }
            n = false;
        }
    }
    /**
     * 
     * @return ArrayList<Casilla> Camino
     * 
     * Calcula el camino mas corto a la casilla no visitada mas cercana
     */
    private ArrayList<Casilla> caminoANoVisitada(){
        Casilla casillaActual = new Casilla(mem.getX(),mem.getY());
        int index = mem.getCamino().indexOf(casillaActual);
        casillaActual = mem.getCamino().get(index);
        Casilla padre;
        ArrayList<Casilla> pila = new ArrayList<>();
        ArrayList<Casilla> visitadas = new ArrayList<>();
        ArrayList<Casilla> camino = new ArrayList<>();
        ArrayList<Casilla> vecinos;
        pila.add(casillaActual);
        casillaActual.setPadre(null);
        while(pila.size()>0){
            pila.remove(0);
            visitadas.add(casillaActual);
            vecinos = casillaActual.getVecinos();
            for(Casilla c: vecinos){
                index = mem.getCamino().indexOf(c);
                c = mem.getCamino().get(index);
                if(!visitadas.contains(c)){
                    c.setPadre(casillaActual);
                    if(mem.getCasillasNoVisitadas().contains(c)){
                        camino.add(c);
                            
                        padre = c.getPadre();
                        while (padre!=null){
                            camino.add(0,padre);
                            padre = padre.getPadre();
                        }
                        camino.remove(0);
                        mem.setCaminoANoVisitada(camino);
                        return camino;
                    }
                    visitadas.add(c);
                    pila.add(c);
                    
                }
            }
            casillaActual = pila.get(0);
        }
        
        return null;
    }
    
    /**
     * 
     * @return int giro
     * 
     * Recorre el camino devuelto pot caminoANoVistada()
     */
    private int recorrerCamino() {
        ArrayList<Casilla> camino = mem.getCaminoANoVisitada();
        norte = mem.getNorte();
        Casilla actual = new Casilla(mem.getX(),mem.getY());
        Casilla destino = camino.get(0);
        int giro = -1;
        switch(norte){
            case Memory.DERECHA:
                if(destino.getY()>actual.getY()){
                    giro = Memory.DERECHA;
                    mem.setNorte(Memory.FRENTE);
                    break;
                }
                if(destino.getY()<actual.getY()){
                    giro = Memory.IZQUIERDA;
                    mem.setNorte(Memory.ATRAS);
                    break;
                }
                if(destino.getX()>actual.getX()){
                    giro = Memory.ATRAS;
                    mem.setNorte(Memory.IZQUIERDA);
                    break;
                }
                if(destino.getX()<actual.getX()){
                    giro = Memory.FRENTE;
                    break;
                }
            case Memory.IZQUIERDA:
                if(destino.getY()>actual.getY()){
                    giro = Memory.IZQUIERDA;
                    mem.setNorte(Memory.FRENTE);
                    break;
                }
                if(destino.getY()<actual.getY()){
                    giro = Memory.DERECHA;
                    mem.setNorte(Memory.ATRAS);
                    break;
                }
                if(destino.getX()>actual.getX()){
                    giro = Memory.FRENTE;
                    break;
                }
                if(destino.getX()<actual.getX()){
                    giro = Memory.ATRAS;
                    mem.setNorte(Memory.DERECHA);
                    break;
                }
            case Memory.FRENTE:
                if(destino.getY()>actual.getY()){
                    giro = Memory.FRENTE;
                    break;
                }
                if(destino.getY()<actual.getY()){
                    giro = Memory.ATRAS;
                    mem.setNorte(Memory.ATRAS);
                    break;
                }
                if(destino.getX()>actual.getX()){
                    giro = Memory.DERECHA;
                    mem.setNorte(Memory.IZQUIERDA);
                    break;
                }
                if(destino.getX()<actual.getX()){
                    giro = Memory.IZQUIERDA;
                    mem.setNorte(Memory.DERECHA);
                    break;
                }
            case Memory.ATRAS:
                if(destino.getY()>actual.getY()){
                    giro = Memory.ATRAS;
                    mem.setNorte(Memory.FRENTE);
                    break;
                }
                if(destino.getY()<actual.getY()){
                    giro = Memory.FRENTE;
                    break;
                }
                if(destino.getX()>actual.getX()){
                    giro = Memory.IZQUIERDA;
                    mem.setNorte(Memory.IZQUIERDA);
                    break;
                }
                if(destino.getX()<actual.getX()){
                    giro = Memory.DERECHA;
                    mem.setNorte(Memory.DERECHA);
                    break;
                }
        }
        mem.setX(destino.getX());
        mem.setY(destino.getY());
        camino.remove(0);
        ArrayList noVisitadas =  mem.getCasillasNoVisitadas();
        if(noVisitadas.contains(destino)){
            noVisitadas.remove(destino);
            mem.setCasillasNoVisitadas(noVisitadas);
        }
        if(camino.isEmpty()){
            mem.setCerrado(false);
            mem.setCaminoANoVisitada(camino);
        }
        return giro;
    }
}
