/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.teseo.seth;

import java.util.HashSet;
import unalcol.agents.Action;
import unalcol.agents.AgentProgram;
import unalcol.agents.Percept;
import unalcol.agents.examples.labyrinth.teseo.simple.SimpleTeseoAgentProgram;
import unalcol.types.collection.vector.Vector;

/**
 *
 *@author seth
 */
public class SethSimpleLabyrinthAgent extends SimpleTeseoAgentProgram {
    private Vector<NodeInfo> nodesInfo = new Vector<>();
    private HashSet<VisitedNode> visitedNodes = new HashSet<>();
    private int rotation = 0;
    private static final int[][] RotationOffsets = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    
    private void reset() {
        visitedNodes.clear();
        nodesInfo.clear();
        rotation = 0;
        visitedNodes.add(new VisitedNode(0, 0));
        nodesInfo.add(new NodeInfo(0, 0, 0));
    }
    
    public SethSimpleLabyrinthAgent() {
        reset();
    }

    @Override
    public void init() {
        super.init();
        reset();
    }

    @Override
    public int accion(boolean PF, boolean PD, boolean PA, boolean PI, boolean MT) {
        
        if (MT) {
            return -1; // die
        }
        
        NodeInfo info = nodesInfo.get(nodesInfo.size() - 1);
        boolean wall[] = {PF, PD, PA, PI};
        
        for (int i = 0; i < 4; i++) {
            int d = (rotation + i) % 4;
            VisitedNode destinationNode = new VisitedNode(info.relativeRow + RotationOffsets[d][0],
                                    info.relativeCol + RotationOffsets[d][1]);
            boolean explored = wall[i]
                    || visitedNodes.contains(destinationNode);
            
            if (!explored) {
                visitedNodes.add(destinationNode);
                nodesInfo.add(new NodeInfo(destinationNode.relativeRow, 
                        destinationNode.relativeCol, d));
                rotation = d;
                
                return i;
            }

        }
        // todos los nodos ya han sido explorados
        nodesInfo.remove(nodesInfo.size() - 1);
        int exitDirection = (info.arrivalDirection + 2) % 4;
        int oldRotation = rotation;
        rotation = exitDirection;
     
        return (exitDirection + 4 - oldRotation) % 4;
    }

}

class NodeInfo {
    int relativeRow, relativeCol;
    int arrivalDirection;

    public NodeInfo(int relativeRow, int relativeCol, int arrivalDirection) {
        this.relativeRow = relativeRow;
        this.relativeCol = relativeCol;
        this.arrivalDirection = arrivalDirection;
    }
}

class VisitedNode {
    int relativeRow, relativeCol;

    public VisitedNode(int relativeRow, int relativeCol) {
        this.relativeCol = relativeCol;
        this.relativeRow = relativeRow;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.relativeRow;
        hash = 53 * hash + this.relativeCol;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final VisitedNode other = (VisitedNode) obj;
        if (this.relativeRow != other.relativeRow) {
            return false;
        }
        if (this.relativeCol != other.relativeCol) {
            return false;
        }
        return true;
    }

}