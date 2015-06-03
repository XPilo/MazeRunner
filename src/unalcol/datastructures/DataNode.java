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
public class DataNode {
    private int level;
    private char direction;
    private int move;
    private int mapX;
    private int mapY;
    public DataNode(){
        
    }
    public boolean equals(DataNode nodo){
        return this.direction == nodo.getDirection() & this.level == nodo.getLevel() & this.move == nodo.getMove();
    }
    public DataNode( int theLevel, char theDirection, int theMove, int theMapX, int theMapY ){
        level = theLevel;
        direction = theDirection;
        move = theMove;
        mapX = theMapX;
        mapY = theMapY;
    }
    public int getLevel(){
        return level;
    }
    public char getDirection(){
        return direction;
    }
    public int getMove(){
        return move;
    }
    public int getMapX(){
        return mapX;
    }
    public int getMapY(){
        return mapY;
    }
    public void setLevel( int theLevel ){
        level = theLevel;
    }
    public void setDirection( char theDirection ){
        direction = theDirection;
    }
    public void setMove( int theMove ){
        move = theMove;
    }
    public void setMapX( int theMapX ){
        mapX = theMapX;
    }
    public void setMapY( int theMapY ){
        mapY = theMapY;
    }
}
