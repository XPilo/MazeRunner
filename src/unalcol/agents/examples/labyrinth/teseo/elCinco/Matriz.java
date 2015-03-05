/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.teseo.elCinco;

/**
 *
 * @author ooss
 */
class Matriz {
    private final int n = 300;
    private int [][] m = new int[n][n];
    private int x;
    private int y;
    
    public Matriz(){
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                m[i][j] = 0;
            }
        }
        x = n/2;
        y = n/2;
    }

    /**
     * @return the m
     */
    public int[][] getM() {
        return m;
    }

    /**
     * @param m the m to set
     */
    public void setM(int[][] m) {
        this.m = m;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        String output = "";
        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                    output+=m[i][j];
            }
            output +="\n";
        }
        return output;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }
    
    
    
}
