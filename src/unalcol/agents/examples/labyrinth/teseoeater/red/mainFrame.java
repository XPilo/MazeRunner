package unalcol.agents.examples.labyrinth.teseoeater.red;

import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.border.EmptyBorder;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class mainFrame extends JFrame {

    private static JPanel contentPane;
    private JLabel salida;
    private boolean paint = false;
    private HashMap<String, JButton> buttonCache = new HashMap<>();
    private final int lectX = 13;
    private final int lectY = 5;
    private final int pixel = 30;
    private final int fact = 1;

    //Se crea una lista de todas las entradas y salidas
    ArrayList<SalidaDeseada> salidas;//salidas esperadas
    int nodosIntermedia = 14;
    int entradasIntermedia = lectX * lectY;
    int nodosSalida = 10;
    int balance = -1;
    Red red;

    /**
     * Launch the application.
     *
     * @param args
     */
    public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() {
            @SuppressWarnings("CallToPrintStackTrace")
            @Override
            public void run() {
                try {
                    mainFrame frame = new mainFrame();
                    frame.setVisible(true);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    //Se crean las salidas esperadas para los valores de entrada
    //todos son vectores de 10 valores + el balance

    private void crearSalidas() {
        //Si no hay salidas (no hay datos de estas antes) se crean
        if (salidas == null) {
            salidas = new ArrayList<>();
            //el 50 en binario 110010
            salidas.add(new SalidaDeseada(new double[]{0, 0, 0, 0, 1, 1, 0, 0, 1, 0, balance}));
            //el 100 en binario 1100100
            salidas.add(new SalidaDeseada(new double[]{0, 0, 0, 1, 1, 0, 0, 1, 0, 0, balance}));
            //el 200 en binario 11001000
            salidas.add(new SalidaDeseada(new double[]{0, 0, 1, 1, 0, 0, 1, 0, 0, 0, balance}));
            //el 500 en binario 111110100
            salidas.add(new SalidaDeseada(new double[]{0, 1, 1, 1, 1, 1, 0, 1, 0, 0, balance}));
            //el 1000 en binario 1111101000
            salidas.add(new SalidaDeseada(new double[]{1, 1, 1, 1, 1, 0, 1, 0, 0, 0, balance}));
        }
        //Igual se comprueba la red
        if(red == null)
            red = new Red(nodosIntermedia, entradasIntermedia, nodosSalida);
    }

    /**
     * Create the frame.
     */
    public mainFrame() {
        super("MLP");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 440, 350);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

//************** Crear botones para dibujar ****************//
        for (int i = 0; i < lectX * fact; i++) {
            for (int j = 0; j < lectY * fact; j++) {
                JButton button = new JButton();
                final JButton btn = button;
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        paint = true;
                        if (paint) {
                            if (btn.getBackground().equals(Color.white)) {
                                btn.setBackground(Color.black);
                            } else {
                                btn.setBackground(Color.white);
                            }
                        }
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        paint = false;
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        if (paint) {
                            if (btn.getBackground().equals(Color.white)) {
                                btn.setBackground(Color.black);
                            } else {
                                btn.setBackground(Color.white);
                            }
                        }
                    }
                });
                button.setSelectedIcon(null);
                button.setFocusPainted(false);
                button.setBorderPainted(false);
                button.setBorder(BorderFactory.createLineBorder(Color.black));
                button.setBackground(Color.white);
                button.setBounds(20 + pixel / fact * i, 20 + pixel / fact * j, pixel / fact, pixel / fact);
                contentPane.add(button);
                buttonCache.put(i + "-" + j, button);
            }
        }
//************** Fin Crear botones para dibujar ****************//

//************** Botones de patrones ****************//
//Estos botones son los que añaden cada patrón al valor de
//salida deseado
        JPanel control = new JPanel();
        control.setLayout(new FlowLayout(FlowLayout.CENTER));
        control.setBounds(70, 200, 320, 100);

        JButton btn50 = new JButton("50");
        btn50.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                salidas.get(0).addEntrada(getEstado());
                limpiar();
            }
        });
        control.add(btn50);

        JButton btn100 = new JButton("100");
        btn100.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                salidas.get(1).addEntrada(getEstado());
                limpiar();
            }
        });
        control.add(btn100);

        JButton btn200 = new JButton("200");
        btn200.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                salidas.get(2).addEntrada(getEstado());
                limpiar();
            }
        });
        control.add(btn200);

        JButton btn500 = new JButton("500");
        btn500.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                salidas.get(3).addEntrada(getEstado());
                limpiar();
            }
        });
        control.add(btn500);

        JButton btn1000 = new JButton("1000");
        btn1000.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                salidas.get(4).addEntrada(getEstado());
                limpiar();
            }
        });
        control.add(btn1000);
//************** Boton aprender ****************//
        JButton btnAprender = new JButton("Aprender");
        btnAprender.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                aprender();
            }
        });
        control.add(btnAprender);
//************** Boton guardar ****************//
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                guardar();
            }
        });
        control.add(btnGuardar);

//************** Boton limpiar ****************//		
        JButton btnClear = new JButton("Limpiar");
        //btnClear.setBounds(80, 250, 80, 20);		
        //contentPane.add(btnClear);
        control.add(btnClear);
        btnClear.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                limpiar();
            }
        });
//************** Fin Botones control ****************//

        JButton btnReconocer = new JButton("Reconocer");
        //btnClear.setBounds(80, 250, 80, 20);		
        //contentPane.add(btnClear);
        control.add(btnReconocer);
        btnReconocer.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                reconocer();
            }
        });

        salida = new JLabel();
        salida.setSize(300, 20);
        salida.setAlignmentY(JLabel.CENTER_ALIGNMENT);
        control.add(salida);
        //Se añade el panel con los botones de control al panel
        //principal
        contentPane.add(control);
        cargar();//se intentan cargar datos previos desde un archivo
        crearSalidas();//se crean salidas si no se cargaron datos
    }

    //Limpia el area de escritura
    private void limpiar() {
        for (int i = 0; i < lectX * fact; i++) {
            for (int j = 0; j < lectY * fact; j++) {
                JButton button = buttonCache.get(i + "-" + j);
                //se colocan todos los valores en blanco (cero)
                button.setBackground(Color.white);
            }
        }
    }

    //Se obtiene un arreglo con valores 1 y 0 de cada posición en la matriz de
    //dibujo, representando así el estado actual del dibujo
    private double[] getEstado() {
        double[] estado = new double[lectX * lectY + 1];
        int pos = 0;
        JButton button;
        for (int i = 0; i < lectX * fact; i++) {
            for (int j = 0; j < lectY * fact; j++) {
                //System.out.print(pos + " = ");
                button = buttonCache.get(i + "-" + j);
                if (button.getBackground().equals(Color.white)) {
                    estado[pos++] = 0;//Cero para blanco
                } else {
                    estado[pos++] = 1;//Uno para negro
                }
                //System.out.println(estado[pos - 1]);
            }
        }
        estado[estado.length - 1] = balance;
        return estado;
    }

    //Función que se encarga de que la red compruebe, actualice y guarde
    //los valores de pesos y errores correctos para reconocer los patrones
    //y salidas asociados a estos
    private void aprender() {
        int n = 600000; //máximas iteraciones para aprender
        double error;
        boolean aprendido;
        int iteraciones = 0;//contador de iteraciones totales (incluso repeticiones)

        try {
            for (int i = 0; i < salidas.size(); i++) {
            //System.out.println("Salidas: "+salidas.size());
                //System.out.println("Salidas: [entradas] "+salidas.get(i).getEntradas().size());
                //salidaActual = i;
                for (double entrada[] : salidas.get(i).getEntradas()) {
                    /*
                     if(iteraciones%1000 == 0){
                     System.out.println();
                     System.out.println("-------------------Nueva---------------------------------");
                
                     for(int l=0; l<65;l++)
                     System.out.print(""+Math.round(entrada[l])+",");
                     System.out.print(" Aprender ");
                     for(int l=0; l<10;l++)
                     System.out.print(""+Math.round(salidas.get(i).getSalida()[l])+",");
                
                     System.out.print("Salida");
                     }
                     */
                    //Se pasan las primeras entradas
                    red.entradas(entrada);
                    //Se activa la capa de nodos intermedia
                    red.activacionIntermedia();
                    //Se pasan los datos de salida de la intermedia a la de salida
                    red.entradasSalida();
                    //Se activa la de salida
                    red.activacionSalida();

                //Se comprueba con la capa de salida (esperada)
                    //el error al usar esta entrada
                    red.errorCapaSalida(salidas.get(i).getSalida());

                    //Se comprueba si los nodos dan la salida esperada
                    aprendido = true;

                    //System.out.println("Comparacion:");
                    for (int k = 0; k < nodosSalida; k++) {
                        //System.out.println(red.getActivacionSalida(k) +"->"+salidas.get(i).salida[k]);
                        if (red.getActivacionSalida(k) != salidas.get(i).salida[k]) {
                            aprendido = false;
                        }
                    }

                    /*
                    if (aprendido) {//Se muestran las salidas
                        System.out.println("Iteración: " + iteraciones);
                        for (int k = 0; k < nodosSalida; k++) {
                            System.out.print("" + Math.round(red.getActivacionSalida(k)) + ",");
                        }

                        double decimal = 0;
                        int t = 0;
                        for (int k = nodosSalida - 1; k >= 0; k--) {
                            decimal += red.getActivacionSalida(k) * Math.pow(2, t++);
                        }
                        System.out.println(" - " + decimal);
                    }
                            */

                    iteraciones++;
                    if (!aprendido) {
                        //se reinicia todo si no es igual la salida a la esperada
                        i = -1;
                        //Se propaga el ajuste de valores de adelante hacia atrás
                        //(backpropagation)
                        red.pesosSalida();
                        red.errorIntermedia();
                        red.pesosEntrada();
                        
                        //se acaba este ciclo for, para volver al primer ciclo y reiniciar
                        break;
                    }
                    
                    if (iteraciones > n) {
                        System.out.println("FIN");
                        return;
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Error: " + ex.getMessage() + " Valores: size="+salidas.size());
            ex.printStackTrace();
        }
    }

    //Toma el estado actual de la matriz y lo envía a la red para que sea evaluado, 
    //esta dá una salida en bits y en decimal del valor que reconoció
    private void reconocer() {
        JOptionPane.showMessageDialog(this, "Salida: " + red.cargarReconocimiento(getEstado()), "Comparación", JOptionPane.INFORMATION_MESSAGE);
        //salida.setText(red.cargarReconocimiento(getEstado()));
    }

    //Se guardan en un archivo de salida las salidas esperadas (con sus patrones)
    //y en otro la red y sus valores hasta ahora calculados
    private void guardar() {
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("datosSalidas.mlp"));
            oos.writeObject(salidas);
            oos.close();
            oos = new ObjectOutputStream(new FileOutputStream("datosRed.mlp"));
            oos.writeObject(red);
            oos.close();
            //imprimir();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Archivo no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error: " + ex.getMessage());
            return;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al escribir en el archivo", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error: " + ex.getMessage());
            return;
        }
        JOptionPane.showMessageDialog(this, "Los datos se han guardado correctamente", "Guardado", JOptionPane.INFORMATION_MESSAGE);
    }

    //Se cargan los datos de las salidas y de la red de ser posible
    @SuppressWarnings("CallToPrintStackTrace")
    private void cargar() {
        File archivo = new File("datosSalidas.mlp");
        // Se lee el primer objeto
        if (archivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("datosSalidas.mlp"))) {
                // Se lee el primer objeto
                Object aux = ois.readObject();
                if (aux instanceof ArrayList) {
                    salidas = (ArrayList<SalidaDeseada>) aux;  // Se escribe en pantalla el objeto
                    JOptionPane.showMessageDialog(this, "Salidas encontradas", "Lectura", JOptionPane.INFORMATION_MESSAGE);
                    imprimir();// se imprimen los datos de las salidas encontradas en el archivo
                }
                //imprimir();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error de lectura", "Error", JOptionPane.ERROR_MESSAGE);
                System.out.println("Error: " + ex.getMessage());
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Objeto no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
                System.out.println("Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
        
        archivo = new File("datosRed.mlp");
        if (archivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("datosRed.mlp"))) {
                // Se lee el primer objeto
                Object aux = ois.readObject();
                if (aux instanceof Red) {
                    red = (Red) aux;
                    JOptionPane.showMessageDialog(this, "Red encontrada", "Lectura", JOptionPane.INFORMATION_MESSAGE);
                }
                //imprimir();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error de lectura", "Error", JOptionPane.ERROR_MESSAGE);
                System.out.println("Error: " + ex.getMessage());
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Objeto no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
                System.out.println("Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    public void imprimir() {
        for (SalidaDeseada sal : salidas) {
            System.out.println(sal);
        }
    }
}
