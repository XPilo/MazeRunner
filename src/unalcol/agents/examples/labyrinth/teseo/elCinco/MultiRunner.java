/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.teseo.elCinco;

import java.util.ArrayList;

/**
 *
 * @author Jose
 */
public class MultiRunner extends MultiTeseoAgentProgram{
    
    private Memory mem;
    private int x;
    private int y;
    private int giro;
    private ArrayList<Casilla> camino;
    private ArrayList<Casilla> casillasNoVisitadas;
    private Casilla casilla;
    private boolean flag;
    private boolean n;
    private boolean cerrado;
    private int norte;
    private boolean AF;
    private boolean AD;
    private boolean AA;
    private boolean AI;

    public MultiRunner() {
        mem = new Memory();
        camino = mem.getCamino();
        casillasNoVisitadas = mem.getCasillasNoVisitadas();
    }
    
    

    @Override
    public int accion(boolean PF, boolean PD, boolean PA, boolean PI, boolean MT, boolean AF, boolean AD, boolean AA, boolean AI) {
        
        if (MT){ 
            mem = new Memory();
            camino = mem.getCamino();
            casillasNoVisitadas = mem.getCasillasNoVisitadas();
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
        this.AF = AF;
        this.AD = AD;
        this.AA = AA;
        this.AI = AI;
        
        if (!mem.isCerrado())
            recorrerLaberinto(PF, PD, PA, PI);
        else{
            giro = recorrerCamino();
            if(giro ==-1){
                        mem = new Memory();
                        camino = mem.getCamino();
                        casillasNoVisitadas = mem.getCasillasNoVisitadas();   
                    }
        }
       return giro;
    }
    
public void recorrerLaberinto(boolean PF, boolean PD, boolean PA, boolean PI){
        x = mem.getX();
        y = mem.getY();
        norte = mem.getNorte();
        casilla = new Casilla(x,y);
        Casilla t;
        //vecinos = new ArrayList<>();
        flag = false;
        n = false;
        switch(norte){
            case Memory.DERECHA:
                if(!PD){
                    if(AD){
                        t = new Casilla(x,y+1);
                        t.addVecino(casilla);
                        casilla.addVecino(t);
                        camino.add(t);
                        casillasNoVisitadas.add(t);
                        break;
                    }
                    girar(0, 1, Memory.DERECHA);
                    if(mem.isCerrado())
                        break;
                    if(flag && n )
                        mem.setNorte(Memory.FRENTE);
                }
                if(!PF){
                    if(AF){
                        t = new Casilla(x-1,y);
                        t.addVecino(casilla);
                        casilla.addVecino(t);
                        camino.add(t);
                        casillasNoVisitadas.add(t);
                        break;
                    }
                    girar(-1, 0, Memory.FRENTE);
                    if(mem.isCerrado())
                        break;
                }
                if(!PI){
                    if(AI){
                        t = new Casilla(x,y-1);
                        t.addVecino(casilla);
                        casilla.addVecino(t);
                        camino.add(t);
                        casillasNoVisitadas.add(t);
                        break;
                    }
                    girar(0, -1, Memory.IZQUIERDA);
                    if(mem.isCerrado())
                        break;
                    if(flag && n)
                        mem.setNorte(Memory.ATRAS);
                }
                if(!PA){
                    if(AA){
                        t = new Casilla(x+1,y);
                        t.addVecino(casilla);
                        casilla.addVecino(t);
                        camino.add(t);
                        casillasNoVisitadas.add(t);
                        break;
                    }
                    girar(1, 0, Memory.ATRAS);
                    if(mem.isCerrado())
                        break;
                    if(flag && n)
                        mem.setNorte(Memory.IZQUIERDA);
                }
                break;
            case Memory.IZQUIERDA:
                if(!PD){
                    if(AD){
                        t = new Casilla(x,y-1);
                        t.addVecino(casilla);
                        casilla.addVecino(t);
                        camino.add(t);
                        casillasNoVisitadas.add(t);
                        break;
                    }
                    girar(0, -1, Memory.DERECHA);
                    if(mem.isCerrado())
                        break;
                    if(flag && n)
                        mem.setNorte(Memory.ATRAS);
                }
                if(!PF){
                    if(AF){
                        t = new Casilla(x+1,y);
                        t.addVecino(casilla);
                        casilla.addVecino(t);
                        camino.add(t);
                        casillasNoVisitadas.add(t);
                        break;
                    }
                    girar(1, 0, Memory.FRENTE);
                    if(mem.isCerrado())
                        break;
                }
                if(!PI){
                    if(AI){
                        t = new Casilla(x,y+1);
                        t.addVecino(casilla);
                        casilla.addVecino(t);
                        camino.add(t);
                        casillasNoVisitadas.add(t);
                        break;
                    }
                    girar(0, 1, Memory.IZQUIERDA);
                    if(mem.isCerrado())
                        break;
                    if(flag && n)
                        mem.setNorte(Memory.FRENTE);
                }
                if(!PA){
                    if(AA){
                        t = new Casilla(x-1,y);
                        t.addVecino(casilla);
                        casilla.addVecino(t);
                        camino.add(t);
                        casillasNoVisitadas.add(t);
                        break;
                    }
                    girar(-1, 0, Memory.ATRAS);
                    if(mem.isCerrado())
                        break;
                    if(flag && n)
                        mem.setNorte(Memory.DERECHA);
                }
                break;
            case Memory.FRENTE:
                if(!PD){
                    if(AD){
                        t = new Casilla(x+1,y);
                        t.addVecino(casilla);
                        casilla.addVecino(t);
                        camino.add(t);
                        casillasNoVisitadas.add(t);
                        break;
                    }
                    girar(1, 0, Memory.DERECHA);
                    if(mem.isCerrado())
                        break;
                    if(flag && n)
                        mem.setNorte(Memory.IZQUIERDA);
                }
                if(!PF){
                    if(AF){
                        t = new Casilla(x,y+1);
                        t.addVecino(casilla);
                        casilla.addVecino(t);
                        camino.add(t);
                        casillasNoVisitadas.add(t);
                        break;
                    }
                    girar(0, 1, Memory.FRENTE);
                    if(mem.isCerrado())
                        break;
                }
                if(!PI){
                    if(AI){
                        t = new Casilla(x-1,y);
                        t.addVecino(casilla);
                        casilla.addVecino(t);
                        camino.add(t);
                        casillasNoVisitadas.add(t);
                        break;
                    }
                    girar(-1, 0, Memory.IZQUIERDA);
                    if(mem.isCerrado())
                        break;
                    if(flag && n)
                        mem.setNorte(Memory.DERECHA);
                }
                if(!PA){
                    if(AA){
                        t = new Casilla(x,y-1);
                        t.addVecino(casilla);
                        casilla.addVecino(t);
                        camino.add(t);
                        casillasNoVisitadas.add(t);
                        break;
                    }
                    girar(0, -1, Memory.ATRAS);
                    if(mem.isCerrado())
                        break;
                    if(flag && n)
                        mem.setNorte(Memory.ATRAS);
                }
                break;
            case Memory.ATRAS:
                if(!PD){
                    if(AD){
                        t = new Casilla(x-1,y);
                        t.addVecino(casilla);
                        casilla.addVecino(t);
                        camino.add(t);
                        casillasNoVisitadas.add(t);
                        break;
                    }
                    girar(-1, 0, Memory.DERECHA);
                    if(mem.isCerrado())
                        break;
                    if(flag && n)
                        mem.setNorte(Memory.DERECHA);
                }
                if(!PF){
                    if(AF){
                        t = new Casilla(x,y-1);
                        t.addVecino(casilla);
                        casilla.addVecino(t);
                        camino.add(t);
                        casillasNoVisitadas.add(t);
                        break;
                    }
                    girar(0, -1, Memory.FRENTE);
                    if(mem.isCerrado())
                        break;
                }
                if(!PI){
                    if(AI){
                        t = new Casilla(x+1,y);
                        t.addVecino(casilla);
                        casilla.addVecino(t);
                        camino.add(t);
                        casillasNoVisitadas.add(t);
                        break;
                    }
                    girar(1, 0, Memory.IZQUIERDA);
                    if(mem.isCerrado())
                        break;
                    if(flag && n)
                        mem.setNorte(Memory.IZQUIERDA);
                }
                if(!PA){
                    if(AA){
                        t = new Casilla(x,y+1);
                        t.addVecino(casilla);
                        casilla.addVecino(t);
                        camino.add(t);
                        casillasNoVisitadas.add(t);
                        break;
                    }
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
                    if(caminoANoVisitada()!=null)
                        giro = recorrerCamino();
                    if(giro ==-1){
                        mem = new Memory();
                        camino = mem.getCamino();
                        casillasNoVisitadas = mem.getCasillasNoVisitadas();   
                    }
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
        Casilla casillaActual = mem.getCasilla(mem.getX(),mem.getY());
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
                c = mem.getCasilla(c);
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
            if(pila.isEmpty()){
                giro =-1;
                return null;
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
        switch(giro){
            case Memory.ATRAS:
                if(AA) return -1;
                break;
            case Memory.FRENTE:
                if(AF) return -1;
                break;
            case Memory.DERECHA:
                if(AD) return-1;
                break;
            case Memory.IZQUIERDA:
                if(AI) return -1;
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
