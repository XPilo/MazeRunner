/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.teseo.elCinco;

import unalcol.agents.examples.labyrinth.teseo.simple.SimpleTeseoAgentProgram;


/**
 *
 * @author Oscar
 */
public class Runner extends SimpleTeseoAgentProgram {
    
    public enum Compass {NORTH, EAST, SOUTH, WEST}
    public Compass north = Compass.NORTH;
    public Matriz myMatriz = new Matriz();
    
    
    public void retornar(int[][] m, int x1, int y1){
        myMatriz.setM(m);
        myMatriz.setX(x1);
        myMatriz.setY(y1);
    }
    
    public int computeChoices(boolean PF,boolean PD,boolean PI, boolean PA, int re){
        //Compass realNorth=north;
        
        int[][] m = myMatriz.getM();
        int x1 = myMatriz.getX();
        int y1 = myMatriz.getY();
        
        m[x1][y1]++;
        
        //Viendo hacía el NORTH
        if (north == Compass.NORTH){
            
            if(!PD && !PF && (m[x1+1][y1]<m[x1][y1+1]) && (m[x1+1][y1]<m[x1][y1])){
                re = 1;
                x1++;
                north=Compass.EAST;
                retornar(m,x1,y1);
                return re;
            }
            if(!PI && !PF && (m[x1][y1+1]<m[x1-1][y1]) && (m[x1][y1+1]<m[x1][y1])){
                re = 0;
                y1++;
                retornar(m,x1,y1);
                return re;
            }
            if(!PI && !PD && (m[x1-1][y1]<m[x1+1][y1]) && (m[x1-1][y1]<m[x1][y1])){
                re = 3;
                x1--;
                north=Compass.WEST;
                retornar(m,x1,y1);
                return re;
            }
                
                
            if(!PD && (m[x1+1][y1]<m[x1][y1])){
                re = 1;
                x1++;
                north=Compass.EAST;
                retornar(m,x1,y1);
                return re;
            }
            if(!PF && (m[x1][y1+1]<m[x1][y1])){
                re = 0;
                y1++;
                retornar(m,x1,y1);
                return re;
            }
            if(!PI && (m[x1-1][y1]<m[x1][y1])){
                re = 3;
                x1--;
                north=Compass.WEST;
                retornar(m,x1,y1);
                return re;
            }
            if(!PA && (m[x1][y1-1]<m[x1][y1])){
                re = 2;
                y1--;
                north=Compass.SOUTH;
                retornar(m,x1,y1);
                return re;
            }
        }
        
        //Viendo hacía el EAST
        if (north == Compass.EAST){
            
            if(!PD && (m[x1][y1-1]<m[x1+1][y1]) && !PF && (m[x1][y1-1]<m[x1][y1])){
                re = 1;
                y1--;
                north=Compass.SOUTH;
                retornar(m,x1,y1);
                return re;
            }
            if(!PI && (m[x1+1][y1]<m[x1][y1+1]) && !PF && (m[x1+1][y1]<m[x1][y1])){
                re = 0;
                x1++;
                retornar(m,x1,y1);
                return re;
            }
            if(!PD && (m[x1][y1+1]<m[x1][y1-1]) && !PI && (m[x1][y1+1]<m[x1][y1])){
                re = 3;
                y1++;
                north=Compass.NORTH;
                retornar(m,x1,y1);
                return re;
            }
            
            
            if(!PD && (m[x1][y1-1]<m[x1][y1])){
                re = 1;
                y1--;
                north=Compass.SOUTH;
                retornar(m,x1,y1);
                return re;
            }
            if(!PF && (m[x1+1][y1]<m[x1][y1])){
                re = 0;
                x1++;
                retornar(m,x1,y1);
                return re;
            }
            if(!PI && (m[x1][y1+1]<m[x1][y1])){
                re = 3;
                y1++;
                north=Compass.NORTH;
                retornar(m,x1,y1);
                return re;
            }
            if(!PA && (m[x1-1][y1]<m[x1][y1])){
                re = 2;
                x1--;
                north=Compass.WEST;
                retornar(m,x1,y1);
                return re;
            }
        }
        
        //Viendo hacía el WEST
        if (north == Compass.WEST){
            
            if(!PD && (m[x1][y1+1]<m[x1-1][y1]) && !PF && (m[x1][y1+1]<m[x1][y1])){
                re = 1;
                y1++;
                north=Compass.NORTH;
                retornar(m,x1,y1);
                return re;
            }
            if(!PI && (m[x1-1][y1]<m[x1][y1-1]) && !PF && (m[x1-1][y1]<m[x1][y1])){
                re = 0;
                x1--;
                retornar(m,x1,y1);
                return re;
            }
            if(!PD && (m[x1][y1-1]<m[x1][y1+1]) && !PI && (m[x1][y1-1]<m[x1][y1])){
                re = 3;
                y1--;
                north=Compass.SOUTH;
                retornar(m,x1,y1);
                return re;
            }
            
            if(!PD && (m[x1][y1+1]<m[x1][y1])){
                re = 1;
                y1++;
                north=Compass.NORTH;
                retornar(m,x1,y1);
                return re;
            }
            if(!PF && (m[x1-1][y1]<m[x1][y1])){
                re = 0;
                x1--;
                retornar(m,x1,y1);
                return re;
            }
            if(!PI && (m[x1][y1-1]<m[x1][y1])){
                re = 3;
                y1--;
                north=Compass.SOUTH;
                retornar(m,x1,y1);
                return re;
            }
            if(!PA && (m[x1+1][y1]<m[x1][y1])){
                re = 2;
                x1++;
                north=Compass.EAST;
                retornar(m,x1,y1);
                return re;
            }
        }
        
        //Viendo hacía el SOUTH
        if (north == Compass.SOUTH){
            
            if(!PD && (m[x1-1][y1]<m[x1][y1-1]) && !PF && (m[x1-1][y1]<m[x1][y1])){
                re = 1;
                x1--;
                north=Compass.WEST;
                retornar(m,x1,y1);
                return re;
            }
            if(!PI && (m[x1][y1-1]<m[x1+1][y1]) && !PF && (m[x1][y1-1]<m[x1][y1])){
                re = 0;
                y1--;
                retornar(m,x1,y1);
                return re;
            }
            if(!PD && (m[x1+1][y1]<m[x1-1][y1]) && !PI && (m[x1+1][y1]<m[x1][y1])){
                re = 3;
                x1++;
                north=Compass.EAST;
                retornar(m,x1,y1);
                return re;
            }
            
            
            if(!PD && (m[x1-1][y1]<m[x1][y1])){
                re = 1;
                x1--;
                north=Compass.WEST;
                retornar(m,x1,y1);
                return re;
            }
            if(!PF && (m[x1][y1-1]<m[x1][y1])){
                re = 0;
                y1--;
                retornar(m,x1,y1);
                return re;
            }
            if(!PI && (m[x1+1][y1]<m[x1][y1])){
                re = 3;
                x1++;
                north=Compass.EAST;
                retornar(m,x1,y1);
                return re;
            }
            if(!PA && (m[x1][y1+1]<m[x1][y1])){
                re = 2;
                y1++;
                north=Compass.NORTH;
                retornar(m,x1,y1);
                return re;
            }
        }
        
        
        return -1;
    }
    
    
    

    public Runner() {}
    @Override
    public int accion(boolean PF, boolean PD, boolean PA, boolean PI, boolean MT) {
        
        if (MT) return -1;
        int re = 0;
        
        
        re = computeChoices(PF, PD, PI, PA, re);
        
        
        //System.out.println(myMatriz);
        return re;
        
        /*
        if (!PD&&!PI&&!PF) return 0;
        if (!PD&&!PI) return 3;
        if (!PD) return 1; //Derecha
        if (!PF) return 0; //Frente
        if (!PI) return 3; //Izquierda
        return 2; //Atras
        */
        
    }
    
}
