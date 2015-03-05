/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.teseo.elCinco;

import unalcol.agents.examples.labyrinth.teseo.simple.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import unalcol.agents.simulate.util.SimpleLanguage;

/**
 *
 * @author Jonatan
 */
public class ElCinco  extends SimpleTeseoAgentProgram {
    
    private static final String newLine = System.getProperty("line.separator");

    public ElCinco(SimpleLanguage _language) {
     super.setLanguage( _language);
    }
    
    public ElCinco() {
     //grafo.add(new Nodo(position));
    } 

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
        int caso = 0;
        String orden = "";
        while( flag ){
            
            if (PI&&!PF&&PD&&!PA){
                k = 0;
                flag = false;
                caso=1;
                
                //puede ir arriba y abajo
            
            }
            else if (!PI&&PF&&!PD&&PA){
                k = 1;
                flag = false;
                caso=2;
                //puede ir izquierda o derecha
            
            }
            else if (!PI&&!PF&&PD&&!PA){
                k = 3;
                flag = false;
                caso=3;
                //puede ir arriba, izquierda o abajo
            }
            else if (!PI&&!PF&&!PD&&PA){
                k = 3;
                flag = false;
                caso=4;
                //puede ir arriba, izquierda o derecha                
            }
            else if (PI&&!PF&&!PD&&!PA){
                k = 0;
                flag = false;
                caso=5;
                //puede ir arriba, derecha o izquierda
            }
            else if (!PI&&PF&&!PD&&!PA){
                k = 3;
                flag = false;
                caso=6;
                //puede ir izquierda, derecha y atras
            }
            else if (PI&&PF&&!PD&&!PA){
                k = 1;
                flag = false;            
                caso=7;
                //puede ir derecha o abajo
            }
            else if (PI&&!PF&&PD&&!PA){
                k = 0;
                flag = false;            
                caso=8;
                //puede ir arriba o derecha
            }
            else if (!PI&&!PF&&PD&&PA){
                k = 0;
                flag = false;           
                caso=9;
                //puede ir izquierda o arriba               
            }
            else if (!PI&&PF&&PD&&!PA){
                k = 3;
                flag = false;            
                caso=10;
                //puede ir izquierda o abajo
            }
            else if (PI&&PF&&!PD&&PA){
                k = 1;
                flag = false;            
                 caso=11;
                 //puede ir a derecha
            }
            else if (PI&&!PF&&PD&&PA){
                k = 0;
                flag = false;            
                caso=12;
                //puede ir arriba
            }
            else if (!PI&&PF&&PD&&PA){
                k = 3;
                flag = false;            
                caso=13;
                //puede ir izquierda
            }
            else if (PI&&PF&&PD&&!PA){
                k = 2;
                flag = false;            
                caso=14;
                //puede ir atras
            }
            else if (!PI&&!PF&&!PD&&!PA){
                ArrayList anteriores = lectura();
                int tam = anteriores.size()-1;
                //si no sabe para donde tomar y viene de una recta siga derecho a ver que encuentra
                if (anteriores.get(tam).equals("F")  && anteriores.get(tam-1).equals("F")&& anteriores.get(tam-2).equals("F")){
                    k = 0;
                }
                if (anteriores.get(tam).equals("F")  && anteriores.get(tam-1).equals("F")&& anteriores.get(tam-2).equals("F")&& anteriores.get(tam-2).equals("F")&& anteriores.get(tam-2).equals("F")&& anteriores.get(tam-2).equals("F")&& anteriores.get(tam-2).equals("F")&& anteriores.get(tam-2).equals("F")&& anteriores.get(tam-2).equals("F")&& anteriores.get(tam-2).equals("F")){
                    k = 1;
                }
                //si no sabe para donde tomar y viene de una casilla sin salida siga derecho a ver que encuentra
                else if (anteriores.get(tam).equals("A")  && anteriores.get(tam-1).equals("A")&& anteriores.get(tam-2).equals("A")){
                    k = 0;
                }
                else{
                    k = 2;
                }
                flag = false;            
                caso=15;
                //puede ir a todos lados
            }
            
            switch(k){
                case 0:
                    escribir("F");
                    orden="F";
                    break;
                case 1:
                    escribir("D");
                    orden="D";
                    break;
                case 2:
                    escribir("A");
                    orden="A";
                    break;
                case 3:
                    escribir("I");
                    orden="I";
                    break;
            }            
        }
        
        //System.out.println("Está en el caso: "+caso+". Tome: "+ orden);
        return k;
    } 
    
    /*
    * Se guarda todo en un archivo a manera de pila para no depender de ninguna clase externa
    */
    
    
    private ArrayList lectura(){
        ArrayList pila = new ArrayList();
        
        
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
 
        try {
           // Apertura del fichero y creacion de BufferedReader para poder
           // hacer una lectura comoda (disponer del metodo readLine()).
           archivo = new File ("pila.txt");
           fr = new FileReader (archivo);
           br = new BufferedReader(fr);

           // Lectura del fichero
           String linea;
           while((linea=br.readLine())!=null){
               pila.add(linea);
           }
           
        }
        catch(Exception e){
           e.printStackTrace();
        }finally{
           // En el finally cerramos el fichero, para asegurarnos
           // que se cierra tanto si todo va bien como si salta 
           // una excepcion.
           try{                    
              if( null != fr ){   
                 fr.close();     
              }                  
           }catch (Exception e2){ 
              e2.printStackTrace();
           }
        }
   return pila;
    
    }
    
    
    private synchronized void escribir(String msg)  {
        String fileName = "pila.txt";
        PrintWriter printWriter = null;
        File file = new File(fileName);
        try {
            if (!file.exists()) file.createNewFile();
            printWriter = new PrintWriter(new FileOutputStream(fileName, true));
            printWriter.write(newLine + msg);
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } finally {
            if (printWriter != null) {
                printWriter.flush();
                printWriter.close();
            }
        }
    }  
}
