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
public class Mapa {
    
    private final int DIM = 40;
    private final Celda [][] mapa = new Celda[DIM][DIM];
    int x, y;
    
    public Mapa(){
        x = DIM/2;
        y = DIM/2;
        for(int i = 0; i < DIM; i++){
            for(int j = 0; j < DIM; j++){
                mapa[i][j] = new Celda(0, false);
            }
        }
    }
    public Celda getCell(int levelX, int levelY){
        return this.mapa[x + levelX][y + levelY];
    }
    public boolean visited(int levelX, int levelY){
        return this.mapa[x + levelX][y + levelY].isVisited();
    }
    public void setCell(int levelX, int levelY, Celda value){
        this.mapa[x + levelX][y + levelY] = value;
    }
    public void visitCell( int levelX, int levelY ){
        this.mapa[x + levelX][y + levelY].visit(true);
    }
    @Override
    public String toString(){
        String cadenaMapa = "";
        for(int i = 0; i < DIM; i++){
            for(int j = 0; j < DIM; j++){
                if( this.mapa[i][j].getLevel() < 10 )
                    cadenaMapa += "  " + this.mapa[i][j].getLevel();
                else if ( this.mapa[i][j].getLevel() < 100 )
                    cadenaMapa += " " + this.mapa[i][j].getLevel();
                else
                    cadenaMapa += "" + this.mapa[i][j].getLevel();
                if( this.mapa[i][j].isVisited() )
                    cadenaMapa += "T";
                else
                    cadenaMapa += "F";
            }
            cadenaMapa += "\n";
        }
        return cadenaMapa;
    }
}
