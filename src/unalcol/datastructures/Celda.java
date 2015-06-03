/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.datastructures;

/**
 *
 * @author alej0
 */
public class Celda {
    private int level;
    private boolean visited;
    
    //public Celda(){}
    public Celda( int theLevel, boolean theVisit ){
        this.level = theLevel;
        this.visited = theVisit;
    }
    public int getLevel(){
        return this.level;
    }
    public boolean isVisited(){
        return this.visited;
    }
    public void setLevel( int theLevel ){
        this.level = theLevel;
    }
    public void visit( boolean theVisit ){
        this.visited = theVisit;
    }
}
