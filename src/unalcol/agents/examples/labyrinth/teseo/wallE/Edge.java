/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unalcol.agents.examples.labyrinth.teseo.wallE;

import java.util.ArrayList;

/**
 *
 * @author oscardu
 */
public class Edge {
   
    /**
     *  the direction to the linked node: up = 0, right = 1, down = 2, left = 3.
     */    
    private int direction;
    
    /**
     * the distance to the next node.
     */
    private int distance;
    
    /**
     * the reference of the next node.
     */
    private Node nextNode;
    
    
    public Edge(int direction, int distance, Node nextNode){
        this.direction = direction;
        this.distance = distance;
        this.nextNode = nextNode;
    }
    
    public int getDirection(){
        return direction;
    }
    public int getDistance(){
        return distance;
    }
    public Node getNextNode(){
        return nextNode;
    }
}
