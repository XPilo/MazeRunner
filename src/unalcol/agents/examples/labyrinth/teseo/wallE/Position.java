/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unalcol.agents.examples.labyrinth.teseo.wallE;

/**
 *
 * @author oscardu
 */
public class Position {
    
    private int xPosition;
    private int yPosition;
     
    public Position(Position position){
        this.xPosition = position.getX();
        this.yPosition = position.getY();
    }
    
    public Position(int xPosition, int yPosition){
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }
    
    public void setX(int xPosition){
        this.xPosition = xPosition;
    }
    public int getX(){
        return xPosition;
    }
    
    public void setY(int yPosition){
        this.yPosition = yPosition;
    }
    public int getY(){
        return yPosition;
    }
    
    @Override
    public String toString(){
        return xPosition +","+yPosition;
    } 
}
