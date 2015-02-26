/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.teseo.simple;

/**
 *
 * @author Jonatan
 */
public class RandomReflexTeseo  extends SimpleTeseoAgentProgram {

    public RandomReflexTeseo() {}

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
        if (MT) return -1;
        boolean flag = true;
        int k=0;
        while( flag ){
            
            if (PI&&!PF&&PD&&!PA){
                k = 0;
                flag = false;
                System.out.println("Caso 1");
                
                //puede ir arriba y abajo
            
            }
            else if (!PI&&PF&&!PD&&PA){
                k = 1;
                flag = false;
                System.out.println("Caso 2");
                //puede ir izquierda o derecha
            
            }
            else if (!PI&&!PF&&PD&&!PA){
                k = 3;
                flag = false;
                System.out.println("Caso 3");
                //puede ir arriba, izquierda o abajo
            }
            else if (!PI&&!PF&&!PD&&PA){
                k = 3;
                flag = false;
                System.out.println("Caso 4");
                //puede ir arriba, izquierda o derecha                
            }
            else if (PI&&!PF&&!PD&&!PA){
                k = 0;
                flag = false;
                System.out.println("Caso 5");
                //puede ir arriba, derecha o izquierda
            }
            else if (!PI&&PF&&!PD&&!PA){
                k = 3;
                flag = false;
                System.out.println("Caso 6");
                //puede ir izquierda, derecha y atras
            }
            else if (PI&&PF&&!PD&&!PA){
                k = 1;
                flag = false;            
                System.out.println("Caso 7");
                //puede ir derecha o abajo
            }
            else if (PI&&!PF&&PD&&!PA){
                k = 0;
                flag = false;            
                System.out.println("Caso 8");
                //puede ir arriba o derecha
            }
            else if (!PI&&!PF&&PD&&PA){
                k = 0;
                flag = false;           
                System.out.println("Caso 9");
                //puede ir izquierda o arriba               
            }
            else if (!PI&&PF&&PD&&!PA){
                k = 3;
                flag = false;            
                System.out.println("Caso 10");
                //puede ir izquierda o abajo
            }
            else if (PI&&PF&&!PD&&PA){
                k = 1;
                flag = false;            
                 System.out.println("Caso 11");
                 //puede ir a derecha
            }
            else if (PI&&!PF&&PD&&PA){
                k = 0;
                flag = false;            
                System.out.println("Caso 12");
                //puede ir arriba
            }
            else if (!PI&&PF&&PD&&PA){
                k = 3;
                flag = false;            
                System.out.println("Caso 13");
                //puede ir izquierda
            }
            else if (PI&&PF&&PD&&!PA){
                k = 2;
                flag = false;            
                System.out.println("Caso 14");
                //puede ir atras
            }
            else if (!PI&&!PF&&!PD&&!PA){
                k = 2;
                //k = (int)(Math.random()*4);
                flag = false;            
                System.out.println("Caso 14");
                //puede ir a todos lados
            }
            
        }    
        
        return k;
    }    
}
