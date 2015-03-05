/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.teseo.wallE;

import java.util.Comparator;

/**
 *
 * @author Oscardu
 */
public class NodeComparator implements Comparator<Node>{
    @Override
    public int compare(Node firstNode, Node secondNode) {
        return firstNode.getNumActions()-secondNode.getNumActions();
    }
    
}
