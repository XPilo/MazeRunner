/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.teseo.cualquierCosa;

import unalcol.agents.simulate.util.SimpleLanguage;


/**
 *
 * @author Oscar "ooss" Garcia
 *
 */
public class Runooss extends SimpleTeseoAgentProgram2 {

    
    public Runooss() {}
    
    /*
    public Runooss(SimpleLanguage language) {
        setLanguage(language);
        //north = Compass.NORTH;
    }

   
    public SimpleLanguage getLanguage() {
        return language;
    }
    */


    

    
    
    public enum Compass {NORTH, EAST, SOUTH, WEST}
    private Compass north = Compass.NORTH;
    private Matriz myMatriz = new Matriz();
    
    
    
    
    public void retornar(int[][] m, int x1, int y1){
        myMatriz.setM(m);
        myMatriz.setX(x1);
        myMatriz.setY(y1);
    }
    
    public int computeChoices(boolean PF,boolean PD,boolean PA, boolean PI, boolean AF, boolean AD, boolean AA, boolean AI, int re){
        //Compass realNorth=north;
        
        int[][] m = myMatriz.getM();
        int x1 = myMatriz.getX();
        int y1 = myMatriz.getY();
        
        m[x1][y1]++;
        
        
        //Viendo hacía el NORTH
        if (north == Compass.NORTH){
            
            if(!PA && !PF && (m[x1][y1-1]<m[x1][y1+1]) && (m[x1][y1-1]<m[x1][y1])){
                if(!AA){
                    re = 2;
                    y1--;
                    north=Compass.SOUTH;
                    retornar(m,x1,y1);
                    return re;
                }
            }
            if(!PD && !PF && (m[x1+1][y1]<m[x1][y1+1]) && (m[x1+1][y1]<m[x1][y1])){
                if(!AD){
                    re = 1;
                    x1++;
                    north=Compass.EAST;
                    retornar(m,x1,y1);
                    return re;
                }
            }
            if(!PI && !PF && (m[x1][y1+1]<m[x1-1][y1]) && (m[x1][y1+1]<m[x1][y1])){
                if(!AF){
                    re = 0;
                    y1++;
                    retornar(m,x1,y1);
                    return re;
                }
            }
            if(!PI && !PD && (m[x1-1][y1]<m[x1+1][y1]) && (m[x1-1][y1]<m[x1][y1])){
                if(!AI){
                    re = 3;
                    x1--;
                    north=Compass.WEST;
                    retornar(m,x1,y1);
                    return re;
                }
            }
                
                
            if(!PD && (m[x1+1][y1]<m[x1][y1])){
                if(!AD){
                    re = 1;
                    x1++;
                    north=Compass.EAST;
                    retornar(m,x1,y1);
                    return re;
                }
            }
            if(!PF && (m[x1][y1+1]<m[x1][y1])){
                if(!AF){
                    re = 0;
                    y1++;
                    retornar(m,x1,y1);
                    return re;
                }
            }
            if(!PI && (m[x1-1][y1]<m[x1][y1])){
                if(!AI){
                    re = 3;
                    x1--;
                    north=Compass.WEST;
                    retornar(m,x1,y1);
                    return re;
                }
            }
            if(!PA && (m[x1][y1-1]<m[x1][y1])){
                if(!AA){
                    re = 2;
                    y1--;
                    north=Compass.SOUTH;
                    retornar(m,x1,y1);
                    return re;
                }
            }
        }
        
        //Viendo hacía el EAST
        if (north == Compass.EAST){
            
            if(!PA && !PF && (m[x1-1][y1]<m[x1+1][y1]) && (m[x1-1][y1]<m[x1][y1])){
                if(!AA){
                    re = 2;
                    x1--;
                    north=Compass.WEST;
                    retornar(m,x1,y1);
                    return re;
                }
            }
            if(!PD && (m[x1][y1-1]<m[x1+1][y1]) && !PF && (m[x1][y1-1]<m[x1][y1])){
                if(!AD){
                    re = 1;
                    y1--;
                    north=Compass.SOUTH;
                    retornar(m,x1,y1);
                    return re;
                }
            }
            if(!PI && (m[x1+1][y1]<m[x1][y1+1]) && !PF && (m[x1+1][y1]<m[x1][y1])){
                if(!AF){
                    re = 0;
                    x1++;
                    retornar(m,x1,y1);
                    return re;
                }
            }
            if(!PD && (m[x1][y1+1]<m[x1][y1-1]) && !PI && (m[x1][y1+1]<m[x1][y1])){
                if(!AI){
                    re = 3;
                    y1++;
                    north=Compass.NORTH;
                    retornar(m,x1,y1);
                    return re;
                }
            }
            
            
            if(!PD && (m[x1][y1-1]<m[x1][y1])){
                if(!AD){
                    re = 1;
                    y1--;
                    north=Compass.SOUTH;
                    retornar(m,x1,y1);
                    return re;
                }
            }
            if(!PF && (m[x1+1][y1]<m[x1][y1])){
                if(!AF){
                    re = 0;
                    x1++;
                    retornar(m,x1,y1);
                    return re;
                }
            }
            if(!PI && (m[x1][y1+1]<m[x1][y1])){
                if(!AI){
                    re = 3;
                    y1++;
                    north=Compass.NORTH;
                    retornar(m,x1,y1);
                    return re;
                }
            }
            if(!PA && (m[x1-1][y1]<m[x1][y1])){
                if(!AA){
                    re = 2;
                    x1--;
                    north=Compass.WEST;
                    retornar(m,x1,y1);
                    return re;
                }
            }
        }
        
        //Viendo hacía el WEST
        if (north == Compass.WEST){
            
            if(!PA && !PF && (m[x1+1][y1]<m[x1-1][y1]) && (m[x1+1][y1]<m[x1][y1])){
                if(!AA){
                    re = 2;
                    x1++;
                    north=Compass.EAST;
                    retornar(m,x1,y1);
                    return re;
                }
            }
            if(!PD && (m[x1][y1+1]<m[x1-1][y1]) && !PF && (m[x1][y1+1]<m[x1][y1])){
                if(!AD){
                    re = 1;
                    y1++;
                    north=Compass.NORTH;
                    retornar(m,x1,y1);
                    return re;
                }
            }
            if(!PI && (m[x1-1][y1]<m[x1][y1-1]) && !PF && (m[x1-1][y1]<m[x1][y1])){
                if(!AF){
                    re = 0;
                    x1--;
                    retornar(m,x1,y1);
                    return re;
                }
            }
            if(!PD && (m[x1][y1-1]<m[x1][y1+1]) && !PI && (m[x1][y1-1]<m[x1][y1])){
                if(!AI){
                    re = 3;
                    y1--;
                    north=Compass.SOUTH;
                    retornar(m,x1,y1);
                    return re;
                }
            }
            
            if(!PD && (m[x1][y1+1]<m[x1][y1])){
                if(!AD){
                    re = 1;
                    y1++;
                    north=Compass.NORTH;
                    retornar(m,x1,y1);
                    return re;
                }
            }
            if(!PF && (m[x1-1][y1]<m[x1][y1])){
                if(!AF){
                    re = 0;
                    x1--;
                    retornar(m,x1,y1);
                    return re;
                }
            }
            if(!PI && (m[x1][y1-1]<m[x1][y1])){
                if(!AI){
                    re = 3;
                    y1--;
                    north=Compass.SOUTH;
                    retornar(m,x1,y1);
                    return re;
                }
            }
            if(!PA && (m[x1+1][y1]<m[x1][y1])){
                if(!AA){
                    re = 2;
                    x1++;
                    north=Compass.EAST;
                    retornar(m,x1,y1);
                    return re;
                }
            }
        }
        
        //Viendo hacía el SOUTH
        if (north == Compass.SOUTH){
            
            if(!PA && !PF && (m[x1][y1+1]<m[x1][y1-1]) && (m[x1][y1+1]<m[x1][y1])){
                if(!AA){
                    re = 2;
                    y1++;
                    north=Compass.NORTH;
                    retornar(m,x1,y1);
                    return re;
                }
            }
            if(!PD && (m[x1-1][y1]<m[x1][y1-1]) && !PF && (m[x1-1][y1]<m[x1][y1])){
                if(!AD){
                    re = 1;
                    x1--;
                    north=Compass.WEST;
                    retornar(m,x1,y1);
                    return re;
                }
            }
            if(!PI && (m[x1][y1-1]<m[x1+1][y1]) && !PF && (m[x1][y1-1]<m[x1][y1])){
                if(!AF){
                    re = 0;
                    y1--;
                    retornar(m,x1,y1);
                    return re;
                }
            }
            if(!PD && (m[x1+1][y1]<m[x1-1][y1]) && !PI && (m[x1+1][y1]<m[x1][y1])){
                if(!AI){
                    re = 3;
                    x1++;
                    north=Compass.EAST;
                    retornar(m,x1,y1);
                    return re;
                }
            }
            
            
            if(!PD && (m[x1-1][y1]<m[x1][y1])){
                if(!AD){
                    re = 1;
                    x1--;
                    north=Compass.WEST;
                    retornar(m,x1,y1);
                    return re;
                }
            }
            if(!PF && (m[x1][y1-1]<m[x1][y1])){
                if(!AF){
                    re = 0;
                    y1--;
                    retornar(m,x1,y1);
                    return re;
                }
            }
            if(!PI && (m[x1+1][y1]<m[x1][y1])){
                if(!AI){
                    re = 3;
                    x1++;
                    north=Compass.EAST;
                    retornar(m,x1,y1);
                    return re;
                }
            }
            if(!PA && (m[x1][y1+1]<m[x1][y1])){
                if(!AA){
                    re = 2;
                    y1++;
                    north=Compass.NORTH;
                    retornar(m,x1,y1);
                    return re;
                }
            }
        }
        
        return -1;
    }
    
    
    @Override
    public int accion(boolean PF, boolean PD, boolean PA, boolean PI, boolean MT, boolean AF, boolean AD, boolean AA, boolean AI) {
        if (MT){
            System.out.println("El agente ha llegado con exito al destino =D");
            myMatriz = new Matriz();
            return -1;
        }
        int re = 0;
        
        
        re = computeChoices(PF, PD, PA, PI, AF, AD, AA, AI, re);
        
        
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
    
    /*
    @Override
    public int findOtherWay(boolean PF, boolean PD, boolean PA, boolean PI, boolean AF, boolean AD, boolean AA, boolean AI) {
        if(!PF && !AF) return 0;
        if(!PD && !AD) return 1;        
        if(!PI && !AI) return 3;        
        if(!PA && !AA) return 2;
        return 5;
    }
    */
}
