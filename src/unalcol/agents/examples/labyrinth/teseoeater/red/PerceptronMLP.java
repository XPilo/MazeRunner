/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.teseoeater.red;

import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;

import javax.swing.border.EmptyBorder;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class PerceptronMLP extends JFrame {

    private static final long serialVersionUID = 1L;
    private static JPanel contentPane;
    private final JLabel salida;
    private final JLabel load;
    private final JButton btnReconocer;
    private final JButton btnClear;
    @SuppressWarnings("FieldMayBeFinal")
    private HashMap<String, JButton> buttonCache = new HashMap<>();
    private final int lectX = 13;
    private final int lectY = 5;
    private final int pixel = 30;
    private boolean generar = false;
    private boolean paint = false;
    static Thread th;

    //Se crea una lista de todas las entradas y salidas
    ArrayList<SalidaDeseada> salidas;//salidas esperadas
    int nodosIntermedia = 14;
    int entradasIntermedia = lectX * lectY;
    int nodosSalida = 10;
    int balance = -1;
    Red red;

    public static void main(String args[]) {
        /*
         try {
         // Set System L&F
         UIManager.setLookAndFeel(
         UIManager.getSystemLookAndFeelClassName());
         } catch (UnsupportedLookAndFeelException e) {
         // handle exception
         } catch (ClassNotFoundException e) {
         // handle exception
         } catch (InstantiationException e) {
         // handle exception
         } catch (IllegalAccessException e) {
         // handle exception
         }
         */
        EventQueue.invokeLater(new Runnable() {
            @Override
            @SuppressWarnings("CallToPrintStackTrace")
            public void run() {
                try {
                    final PerceptronMLP frame = new PerceptronMLP();
                    frame.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                            if (th.isAlive()) {//Se comprueba si hay operaciones en ejecución
                                if (JOptionPane.showConfirmDialog(frame,
                                        "Hay operaciones pendientes aún                "
                                                + "\n¿desea cancelarlas y cerrar?", "Cerrando...",
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                                    th.interrupt();//Se cancelan las operaciones
                                }else{
                                    System.out.println("Salir cancelado por usuario " + (new Date ()));
                                    return;//no se hace nada
                                }
                            }
                            System.exit(0);//se termina el programa
                        }
                    });
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
        //Se crean las salidas
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

        //Se crea la red
        red = new Red(nodosIntermedia, entradasIntermedia, nodosSalida);

    }

    public PerceptronMLP() {
        super("MLP");
        //se controla manual y no automáticamente la salida del programa
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationByPlatform(true);
        setBounds(100, 100, 440, 260);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

//************** Crear botones para dibujar ****************//
        for (int i = 0; i < lectX; i++) {
            for (int j = 0; j < lectY; j++) {
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
                button.setBounds(20 + pixel * i, 20 + pixel * j, pixel, pixel);
                button.setVisible(false);
                contentPane.add(button);
                buttonCache.put(i + "-" + j, button);
            }
        }
//************** Fin Crear botones para dibujar ****************//

//************** Boton limpiar ****************//		
        btnClear = new JButton("Limpiar");
        btnClear.setBounds(110, 190, 80, 20);
        btnClear.setVisible(false);
        contentPane.add(btnClear);
        btnClear.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                limpiar();
            }
        });
//************** Boton Reconocer ****************//	
        btnReconocer = new JButton("Reconocer");
        btnReconocer.setBounds(200, 190, 100, 20);
        btnReconocer.setVisible(false);
        contentPane.add(btnReconocer);
        btnReconocer.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                reconocer();
            }
        });

        salida = new JLabel();
        salida.setSize(300, 20);
        salida.setAlignmentY(JLabel.CENTER_ALIGNMENT);
        contentPane.add(salida);

        ImageIcon loading = new ImageIcon(getClass().getResource("/resources/loader.gif"));
        load = new JLabel("Aprendiendo... ", loading, JLabel.CENTER);
        load.setBounds(0, 0, 400, 230);
        load.setVisible(false);
        contentPane.add(load);

        crearSalidas();//se crean salidas si no se cargaron datos

        //patrones de las monedas
        salidas.get(0).addEntrada(new double[]{1.0, 1.0, 1.0, 0.0, 1.0, 1.0, 0.0, 1.0, 0.0, 1.0, 1.0, 0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, balance});
        salidas.get(0).addEntrada(new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 0.0, 1.0, 1.0, 0.0, 1.0, 0.0, 1.0, 1.0, 0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, balance});
        salidas.get(0).addEntrada(new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 0.0, 1.0, 1.0, 0.0, 1.0, 0.0, 1.0, 1.0, 0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, balance});
        salidas.get(0).addEntrada(new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 0.0, 1.0, 1.0, 0.0, 1.0, 0.0, 1.0, 1.0, 0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, balance});
        salidas.get(0).addEntrada(new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 0.0, 1.0, 1.0, 0.0, 1.0, 0.0, 1.0, 1.0, 0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, balance});
        salidas.get(0).addEntrada(new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 0.0, 1.0, 1.0, 0.0, 1.0, 0.0, 1.0, 1.0, 0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, balance});
        salidas.get(0).addEntrada(new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 0.0, 1.0, 1.0, 0.0, 1.0, 0.0, 1.0, 1.0, 0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, balance});
        salidas.get(1).addEntrada(new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, balance});
        salidas.get(1).addEntrada(new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, balance});
        salidas.get(1).addEntrada(new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, balance});
        salidas.get(1).addEntrada(new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, balance});
        salidas.get(1).addEntrada(new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, balance});
        salidas.get(1).addEntrada(new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, balance});
        salidas.get(2).addEntrada(new double[]{1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 0.0, 1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, balance});
        salidas.get(2).addEntrada(new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 0.0, 1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, balance});
        salidas.get(2).addEntrada(new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 0.0, 1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, balance});
        salidas.get(3).addEntrada(new double[]{1.0, 1.0, 1.0, 0.0, 1.0, 1.0, 0.0, 1.0, 0.0, 1.0, 1.0, 0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, balance});
        salidas.get(3).addEntrada(new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 0.0, 1.0, 1.0, 0.0, 1.0, 0.0, 1.0, 1.0, 0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, balance});
        salidas.get(3).addEntrada(new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 0.0, 1.0, 1.0, 0.0, 1.0, 0.0, 1.0, 1.0, 0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, balance});
        salidas.get(4).addEntrada(new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, balance});

        elegirInicio();

    }

    // Elije si carga una archivo con la red ya aprendida, o realiza el proceso de aprendizaje
    private void elegirInicio() {
        Object[] options = {"Si", "No"};
        int precarga = JOptionPane.showOptionDialog(contentPane,
                "¿Desea cargar el archivo de aprendizaje?",
                "Modo de Inicio",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        load.setVisible(true);

        if (precarga == JOptionPane.YES_OPTION) {
            th = new Thread() {
                @Override
                public void run() {
                    cargar();
                }
            };
            th.start();
        } else {
            th = new Thread() {
                @Override
                public void run() {
                    aprender();
                }
            };
            th.start();
        }
    }

    //Cambia la visivilidad de los elementos 
    private void visibleElements(boolean visible) {
        for (int i = 0; i < lectX; i++) {
            for (int j = 0; j < lectY; j++) {
                JButton button = buttonCache.get(i + "-" + j);
                button.setVisible(visible);
            }
        }
        btnClear.setVisible(visible);
        btnReconocer.setVisible(visible);
        load.setVisible(!visible);
    }

    //Limpia el area de escritura
    private void limpiar() {
        for (int i = 0; i < lectX; i++) {
            for (int j = 0; j < lectY; j++) {
                JButton button = buttonCache.get(i + "-" + j);
                //se colocan todos los valores en blanco (cero)
                button.setBackground(Color.white);
            }
        }
    }

    //Se obtiene un arreglo con valores 1 y 0 de cada posicion en la matriz de
    //dibujo, representando asi el estado actual del dibujo
    private double[] getEstado() {
        double[] estado = new double[lectX * lectY + 1];
        int pos = 0;
        JButton button;
        for (int i = 0; i < lectX; i++) {
            for (int j = 0; j < lectY; j++) {
                //System.out.print(pos + " = ");
                button = buttonCache.get(i + "-" + j);
                if (button.getBackground().equals(Color.white)) {
                    estado[pos++] = 0;//Cero para blanco
                } else {
                    estado[pos++] = 1;//Uno para negro
                }
            }
        }
        estado[estado.length - 1] = balance;
        return estado;
    }

    //Funcion que se encarga de que la red compruebe, actualice y guarde
    //los valores de pesos y errores correctos para reconocer los patrones
    //y salidas asociados a estos
    @SuppressWarnings("CallToPrintStackTrace")
    private void aprender() {
        int n = 600000; //maximas iteraciones para reiniciar aprendizaje
        boolean aprendido;
        int iteraciones = 0;//contador de iteraciones totales (incluso repeticiones)

        try {
            for (int i = 0; i < salidas.size(); i++) {

                for (double entrada[] : salidas.get(i).getEntradas()) {

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

                    for (int k = 0; k < nodosSalida; k++) {
                        if (red.getActivacionSalida(k) != salidas.get(i).salida[k]) {
                            aprendido = false;
                        }
                    }

                    iteraciones++;
                    if (!aprendido) {
                        //se reinicia todo si no es igual la salida a la esperada
                        i = -1;
                        //Se propaga el ajuste de valores de adelante hacia atras
                        //(backpropagation)
                        red.pesosSalida();
                        red.errorIntermedia();
                        red.pesosEntrada();

                        //se acaba este ciclo for, para volver al primer ciclo y reiniciar
                        break;
                    }

                    if (iteraciones > n) {
                        //Vuelve aprender
                        System.out.println("FIN [Mal] "  + (new Date ()));
                        aprender();
                        return;
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Error: " + ex.toString() + " Valores: size=" + salidas.size());
            ex.printStackTrace();
        }
        System.out.println("FIN [Correcto] "  + (new Date ()));
        if (generar) {
            guardar();
        }
        visibleElements(true);
    }

    //Toma el estado actual de la matriz y lo envia a la red para que sea evaluado, 
    //esta da una salida en bits y en decimal del valor que reconocio
    private void reconocer() {
        JOptionPane.showMessageDialog(this, red.cargarReconocimiento(getEstado()), "Reconocimiento", JOptionPane.INFORMATION_MESSAGE);
    }

    //Se cargan los datos de las salidas y de la red de ser posible
    public void imprimir() {
        for (SalidaDeseada sal : salidas) {
            System.out.println(sal);
        }
    }

    //Reemplaza o crea el archivo datosRed.mlp
    @SuppressWarnings("ConvertToTryWithResources")
    private void guardar() {
        try {
            File archivo = new File("datosRed.mlp");
            if (archivo.exists()) {
                if (archivo.delete()) {
                    System.out.println("Archivo eleminado");
                } else {
                    System.out.println("Error al eliminar el archivo");
                }
            }
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("datosRed.mlp"));
            oos.writeObject(red);
            oos.close();
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
        generar = false;
    }

    //Se cargan los datos de la red de si el archivo existe si no inicia el aprendizaje y crea el archivo
    private void cargar() {
        File archivo = new File("datosRed.mlp");
        if (archivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("datosRed.mlp"))) {
                // Se lee el primer objeto
                Object aux = ois.readObject();
                if (aux instanceof Red) {
                    red = (Red) aux;
                    //JOptionPane.showMessageDialog(this, "Red encontrada", "Lectura", JOptionPane.INFORMATION_MESSAGE);
                    visibleElements(true);
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error de lectura, Generando archivo de aprendizaje", "Error", JOptionPane.ERROR_MESSAGE);
                System.out.println("Error: " + ex.getMessage());
                generar = true;
                aprender();
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Objeto no encontrado, Generando archivo de aprendizaje", "Error", JOptionPane.ERROR_MESSAGE);
                System.out.println("Error: " + ex.getMessage());
                generar = true;
                aprender();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Generando archivo de aprendizaje", "Red no encontrada", JOptionPane.INFORMATION_MESSAGE);
            generar = true;
            aprender();
        }
    }
}
