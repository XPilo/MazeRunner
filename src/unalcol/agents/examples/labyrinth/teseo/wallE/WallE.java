package unalcol.agents.examples.labyrinth.teseo.wallE;

import java.util.TreeMap;

/**
 * @author oscardu
 */
public class WallE extends SimpleTeseoAgentProgram {

    private final Position mPosition;
    private int mOrientation;
    private Node mLastNode;
    private int mLastNodeLeavingDirection;
    private Node mPresentNode;

    private int distanceFromLastNode;

    private TreeMap<String, Node> mMap;

    public WallE() {
        mPosition = new Position(0, 0);
        mOrientation = 0;
        distanceFromLastNode = 0;
        this.mMap = new TreeMap<>();
        mLastNode = null;
    }

    @Override
    public int accion(boolean PF, boolean PD, boolean PA, boolean PI, boolean MT) {
        int action = 0;
        if (MT) {
            return -1;
        }
        //CASOS BIFURCACION        
        if ((!PF && !PD) || (!PF && !PI) || (!PI && !PD) || (!PF && !PD && !PI) || (mPosition.getX() == 0 && mPosition.getY() == 0)) {  //se agrega 0,0 como nodo          
            int dirSalida = nodeManagment(PF, PD, PA, PI, MT);
            if (dirSalida == -1) {
                return dirSalida;
            }
            mLastNodeLeavingDirection = ((mOrientation + dirSalida) % 4);
            action = dirSalida;
        }else if (PF && PD && PI) {// DEVUELVASE POR QUE ESTA CERRADO
            action = 2;
        }else if (PD && PF) { //GIRAR IZQUIERDA POR QUE NO HAY MAS
            action = 3;
        }else if (PI && PF) { //GIRAR DERECHA POR QUE NO HAY MAS
            action = 1;
        }//AVANCE, NO HAY NADA MAS     
        changeDirection(action);
        changePos();
        addDistanceStep();
        //addNumActions(action);
        return action;
    }

    /**
     * Se encarga de inciar, modificar, revisar o buscar nodos en los casos
     * donde el agente debe hacer uso de ellos.
     *
     * @param PF
     * @param PD
     * @param PA
     * @param PI
     * @param MT
     * @return
     */
    private int nodeManagment(boolean PF, boolean PD, boolean PA, boolean PI, boolean MT) {          
        if (mMap.containsKey(getPositionStr())) {//Existe un nodo en la posicion actual?            
            mPresentNode = mMap.get(getPositionStr());
        } else {//Crea un nuevo nodo y verifica las fronteras.
            mPresentNode = new Node(mPosition.getX(), mPosition.getY());
            evaluateBoundaries(mPresentNode, PF, PD, PA, PI);
        }        

        if (mPresentNode == mLastNode) {//verifica que no haya vuelto al mismo nodo, por que llegue a una via sin salida.
            mPresentNode.addBoundaryType(mLastNodeLeavingDirection, Node.BoundaryType.deadEnd);
            mPresentNode.addBoundaryType(invertOrientation(), Node.BoundaryType.deadEnd);
        } else {//Actualiza el nodo actual con la referencia del anterior.            
            if (mLastNode != null) {
                mPresentNode.addEdge(invertOrientation(), distanceFromLastNode, mLastNode);
                mPresentNode.addBoundaryType(invertOrientation(), Node.BoundaryType.node);

                //Modifica el nodo anterior con la referencia del actual
                mLastNode.addEdge(mLastNodeLeavingDirection, distanceFromLastNode, mPresentNode);
                mLastNode.addBoundaryType(mLastNodeLeavingDirection, Node.BoundaryType.node);
                        
                //Actualizar el treemap con el nodo nuevo y el anterior con la nueva referencia
                mMap.put(getPositionStr(), mPresentNode);
                mMap.put(mLastNode.getPosition().toString(), mLastNode);
                //resetNumActions();
                resetDistanceStep();
            }
            mLastNode = mPresentNode;
        }
        
        int wayToGo;
        if (mPresentNode.hasOpenWays()) {  //Tiene vias abiertas?               
            wayToGo = mPresentNode.openWayMovement(mOrientation);
        } else if (mPresentNode.hasNodeWays()) {//No, no hay vias abiertas, hay vias ya recorridas?
            wayToGo = mPresentNode.nodeSearchWay(mOrientation, mPresentNode, mMap);
        } else {
            System.out.println("OPSS!");
            wayToGo = 0;
        }
        return wayToGo;
    }

    /**
     * Verifica las percepciones recibidas para detectar paredes alrededor.
     *
     * @param node
     * @param PF
     * @param PD
     * @param PA
     * @param PI
     */
    public void evaluateBoundaries(Node node, boolean PF, boolean PD, boolean PA, boolean PI) {
        if (PF) {
            node.addBoundaryType((0 + mOrientation) % 4, Node.BoundaryType.wall);
        }
        if (PD) {
            node.addBoundaryType((1 + mOrientation) % 4, Node.BoundaryType.wall);
        }
        if (PA) {
            node.addBoundaryType((2 + mOrientation) % 4, Node.BoundaryType.wall);
        }
        if (PI) {
            node.addBoundaryType((3 + mOrientation) % 4, Node.BoundaryType.wall);
        }
    }

    /**
     * Funcion que cambia los valores de posicion del agente, para asi saber en
     * cual posicion estamos actualmente.
     *
     * @param pos es aquel que posee la posicion actual la cual vamos a cambiar
     * al final de la funcion.
     * @param dir es aquel que posee la direccion en la cual esta viendo el
     * agente actualmente.
     */
    private void changePos() {
        switch (mOrientation) {
            case 0:
                mPosition.setY(mPosition.getY() + 1);
                break;
            case 1:
                mPosition.setX(mPosition.getX() + 1);
                break;
            case 2:
                mPosition.setY(mPosition.getY() - 1);
                break;
            case 3:
                mPosition.setX(mPosition.getX() - 1);
                break;
            default:
                break;
        }
    }

    /**
     * Durante cada paso, aumenta la cantidad de pasos.
     */
    private void addDistanceStep() {
        distanceFromLastNode++;
    }
    private void resetDistanceStep() {
        distanceFromLastNode=0;
    }

    /**
     * Durante cada paso, aumenta la cantidad de actiones necesarias para
     * desplazarse.
     */
    private void addNumActions(int numActions) {
        distanceFromLastNode += numActions+1;//numactions es las rotaciones y +1 por el costo de avanzar
    }
    private void resetNumActions(){
        distanceFromLastNode = 0;
    }

    /**
     * retorna la orientacion contraria del agente para saber por donde ingreso.
     *
     * @return
     */
    private int invertOrientation() {
        return ((mOrientation + 2) % 4);
    }

    /**
     * Cambia la orientacion relativa del agente.
     *
     * @param rotation
     */
    private void changeDirection(int rotation) {
        mOrientation = ((mOrientation + rotation) % 4);
    }

    /**
     * Retorna un @String como la key para el treemap
     *
     * @return
     */
    private String getPositionStr() {
        return mPosition.toString();
    }

    /**
     * @deprecated retorna un numero pseudoaleatorio de camino a tomar,
     * dependiendo de las opciones que se puedan tomar.
     *
     * @param values
     * @return
     */
    int RandomFrom(Integer... values) {
        int myRand = (int) (Math.random() * (values.length - 0) + 0);
        return values[myRand];
    }

}
