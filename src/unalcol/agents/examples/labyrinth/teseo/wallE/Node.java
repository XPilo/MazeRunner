/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unalcol.agents.examples.labyrinth.teseo.wallE;

import java.awt.BorderLayout;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.TreeMap;
import javax.swing.JFrame;

/**
 *
 * @author oscardu
 */
public class Node {
    
    public enum BoundaryType{open, wall, node, deadEnd};
    
    /**
     * the node position.
     */
    private Position position;   
    
    /**
     * the search distance.
     */
    private int numActions;
    
    /**
     * Parent reference, for backtracking. 
     */
    private Node parent;
    
    /**
     * the boundaries of the node in his position in the format: [up, right, down, left].
     */
    private BoundaryType[] boundary;
    
    /**
     * the edges the node is linked with.
     */
    private Edge[] edges;
    
     /**
     * constructor for the initial node.
     * @param position 
     */
    public Node(int x, int y){
        this.position = new Position(x, y);
        this.edges = new Edge[4];
        //this.edges[0] = new Edge(-1, 0, this);
        //this.edges[1] = new Edge(-1, 0, this);
        //this.edges[2] = new Edge(-1, 0, this);
        //this.edges[3] = new Edge(-1, 0, this);
        this.boundary = new BoundaryType[4];
        this.boundary[0] = BoundaryType.open;
        this.boundary[1] = BoundaryType.open;
        this.boundary[2] = BoundaryType.open;
        this.boundary[3] = BoundaryType.open;   
        
        this.numActions = Integer.MAX_VALUE;
    }    

    public void setEdges(Edge edges[]){
        this.edges=edges;
    }
    
    public Edge[] getEdges(){
        return edges;
    }
    public void addEdge(int direction, int distance, Node nodeReference){
        if(this.edges[direction]==null){
            this.edges[direction] = new Edge(direction, distance, nodeReference);
        }
    } 
    
    public void setBoundary(BoundaryType boundary[]){
        this.boundary = boundary;
    }
    public void setBoundary(BoundaryType up, BoundaryType right, BoundaryType down, BoundaryType left){
        this.boundary[0] = up;
        this.boundary[1] = right;
        this.boundary[2] = down;
        this.boundary[3] = left;
    }
    public void addBoundaryType(int direction, BoundaryType type){
        this.boundary[direction] = type;
    }   
    public BoundaryType[] getBoundary(){
        return boundary;
    }
    public Position getPosition(){
        return position;
    }
    public int getNumActions(){
        return numActions;
    }
    public void setNumActions(int numActions){
        this.numActions = numActions;
    }
    
    public boolean hasOpenWays(){
        return (boundary[0]==BoundaryType.open ||
                boundary[1]==BoundaryType.open ||
                boundary[2]==BoundaryType.open ||
                boundary[3]==BoundaryType.open);
    }   
    
    public int openWayMovement(int mOrientation){
        //Mira todas las posibles salidas abiertas y devuelve la que requiera menos movimientos. frente > izquierda > detras > derecha
        int min = 4;
        for(int i = 0; i<4;i++){
            if(boundary[i]==BoundaryType.open){ min = Math.min(min, ((i-mOrientation)+4) % 4);}            
        }        
       return min;
    }
    public boolean hasNodeWays(){
        return (boundary[0]==BoundaryType.node ||
                boundary[1]==BoundaryType.node ||
                boundary[2]==BoundaryType.node ||
                boundary[3]==BoundaryType.node);
    }
    public int nodeSearchWay(int mOrientation, Node presentNode, TreeMap<String, Node> map){
        //Modificar para retornar todo el path
        PriorityQueue<Node> nodes = new PriorityQueue<>(new NodeComparator());
        presentNode.numActions = 0;
        presentNode.parent = null;
        nodes.add(presentNode);                      
        Node node, nextNode, closestNode;
        do{            
            node = nodes.remove();
            for(Edge edge: node.getEdges()){
                if(edge!=null){
                        if(!edge.getNextNode().equals(presentNode)&&!edge.getNextNode().equals(node.parent)){
                        nextNode = edge.getNextNode(); 
                        nextNode.parent = node;
                        nextNode.numActions = 0;
                        nextNode.numActions = node.getNumActions() + edge.getDistance();// + (((edge.getDirection() - mOrientation) + 4)%4);
                        nodes.add(nextNode);                       
                    }
                }
            }
        }while(!node.hasOpenWays()&&!nodes.isEmpty());        
        //presentNode --> ClosestNode --> ... --> node
        System.out.println("Desde: "+presentNode.getPosition().toString()+" Hasta: "+node.getPosition().toString());
        
        
        //ESTA MAL
        closestNode = node;
        while(!closestNode.parent.equals(presentNode)){            
            closestNode = closestNode.parent;            
        }
        for(Edge edge : presentNode.edges){  
            if(edge != null){
                if(edge.getNextNode().equals(closestNode)){  
                    return ((edge.getDirection() - mOrientation) + 4) % 4;                    
                }
            }
        }        
        return -1;
    }   
    
    
    @Override
    public String toString() {
        
        String nodeString = "\n\tNodo en:"+position.getX() +","+position.getY()+"\n";/*+
                            "\t\tFronteras:   \n\t\t\tArriba: "+boundary[0].toString()+                
                            "\n\t\t\tderecha: "+boundary[1].toString()+
                            "\n\t\t\tAbajo: "+boundary[2].toString()+
                            "\n\t\t\tIzquierda: "+boundary[3].toString();*/
        
        return nodeString;
    }
    
    public String printDebug(){
        String debugTitle =  "NODE DEBUG\n";
        String debugPosicion = "Posicion: "+getPosition().toString()+"\n";
        String debugActions = "Numero de acciones: "+getNumActions()+"\n";
        String debugParent;
        if(parent!=null){
            debugParent = "Padre: "+parent.toString()+"\n";
        }else{
            debugParent = "Padre: null \n";
        }
        String debugBoundaries ="fronteras:"+"\n"+
                            "\tArriba: "+boundary[0].toString()+"\n"+
                            "\tderecha: "+boundary[1].toString()+"\n"+
                            "\tAbajo: "+boundary[2].toString()+"\n"+
                            "\tIzquierda: "+boundary[3].toString()+"\n";
        String debugEdges ="Vertices:"+"\n";
                           for(Edge edge : getEdges()){  
                                if(edge != null){
                                    debugEdges += "\tNodo en"+ edge.getNextNode().getPosition().toString() +", a "+ edge.getDistance() +" pasos"+"\n";
                                }
                            }        
        return debugTitle+debugPosicion+debugActions+debugParent+debugBoundaries+debugEdges;
    }
    
    
    
}
