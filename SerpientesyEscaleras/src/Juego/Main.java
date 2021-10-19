/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Juego;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import message.Message;

/**
 *
 * @author user
 */
public class Main {

    public static Main ThisGame;
    public static int player = -1;	//Numero para darle al jugador.
    public int RivalSelection = -1;
    public int myselection = -1;
	//Frames.
    public static Coordenada[] coordinatesArray = new Coordenada[100];	
    final static Tile_Object t1 = new Tile_Object(Color.GREEN, true);
    final static Tile_Object t2 = new Tile_Object(Color.BLUE, false);
    final static JButton btn1 = new JButton("EMPEZAR");
    final static JButton btn2 = new JButton("EMPEZAR");
    final static JButton again = new JButton("Jugar otra vez");
    final static JButton connect = new JButton("Conectarse al servidor");
    final static JLabel player1 = new JLabel("Jugador 1");
    final static JLabel player2 = new JLabel("Jugador 2");
    final static JLabel whoIsTurn = new JLabel("");

    public Main() {
        ThisGame = this;
    }

    public static void main(String[] args) {
        getCoordinates();	//Ponerle las coordenadas a los frames.
        JFrame obj = new JFrame();
        Tile tile = new Tile();
        obj.setLayout(null);
        obj.setBounds(250, 10, 705, 740);
        obj.setTitle("Serpientes y escaleras");

        JPanel panel1 = new JPanel();
        panel1.setBounds(0, 0, 700, 700);
        JPanel panel2 = new JPanel();
        panel2.setBounds(0, 610, 700, 99);
        panel2.setBackground(Color.decode("#6aab7b"));

        panel1.add(tile);
		//Los frames de los jugadores.
        t1.setBackground(coordinatesArray[0]._c);
        t1.setBounds(coordinatesArray[0]._x + 15, coordinatesArray[0]._y + 20, 35, 35);
        obj.add(t1);

        t2.setBackground(coordinatesArray[0]._c);
        t2.setBounds(coordinatesArray[0]._x + 15, coordinatesArray[0]._y + 20, 35, 35);
        obj.add(t2);
		//configurar el botón y agregarlo al marco.
        player1.setBounds(75, 610, 100, 50);
        obj.add(player1);
        btn1.setBounds(50, 650, 100, 50);
		//Se cierra el boton del jugador 2.
        if (t1._turn == true) {
            btn2.setEnabled(false);
        }
        obj.add(btn1);
        btn1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int flag = 0;	//Comprobante de que el juego aun no acaba.
                if (t1._turn == true) {	//Se cierra el boton del jugador 1.
                    t2._turn = true;
                    btn2.setEnabled(true);
                    int oldValue = t1.location;
                    Random rnd = new Random();
                    int randomNumber = rnd.nextInt(6 - 1 + 1) + 1;	//Tirar el dado.
                    t1.location = t1.location + randomNumber;
                    btn1.setText(String.valueOf(randomNumber));	//El numero que salió se escribe en el boton.
                    if (t1.location == 99) {	//Si se llega al final del tablero, se cambia el valor de nuestro comprobante.
                        flag = 1;				//Activar y desactivar los botones.
                        t2._turn = false;
                        btn2.setEnabled(false);
                        t1._turn = false;
                        btn1.setEnabled(false);
                    }
                    if (t1.location >= 100) {	//Si el valor es muy alto se queda con el anterior.
                        t1.location = oldValue;
                    } else {
                        t1.setBackground(coordinatesArray[t1.location]._c);
                        t1.setBounds(coordinatesArray[t1.location]._x + 15, coordinatesArray[t1.location]._y + 15, 35, 35);
                        if (coordinatesArray[t1.location].isSnakeOrLeader) {
                            try {
                                TimeUnit.MILLISECONDS.sleep(1000);
                                t1.setBounds(coordinatesArray[coordinatesArray[t1.location].nextValue]._x + 15, coordinatesArray[coordinatesArray[t1.location].nextValue]._y + 15, 35, 35);
                                t1.location = coordinatesArray[t1.location].nextValue;
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }

                    if (flag == 1) {	//Dice si alguien llego al final, osea que haya terminado el juego.
                        flag = 0;
                        whoIsTurn.setBounds(250, 580, 250, 100);
                        whoIsTurn.setFont(new Font("Serif", Font.BOLD, 20));
                        whoIsTurn.setText("¡Jugador 1 ha ganado!");
                        again.setVisible(true);	//Se pone visible el boton de reiniciar.
                    } else {
                        whoIsTurn.setText("->");	//El signo de los turnos.
                    }

                    t1._turn = false;
                    btn1.setEnabled(false);
                    Message msg = new Message(Message.Message_Type.Locations);	//Enviar la informacion para repetir con el otro jugador.
                    int[] array = {oldValue, randomNumber};
                    msg.array = array;
                    Juego.Client.Send(msg);
					//Lo mismo para el jugador 2.
                }
            }
        });

        player2.setBounds(575, 610, 100, 50);
        obj.add(player2);
        btn2.setBounds(550, 650, 100, 50);
        if (t2._turn == true) {
            btn1.setEnabled(false);
        }
        obj.add(btn2);
        btn2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int flag = 0;
                if (t2._turn == true) {
                    t1._turn = true;
                    btn1.setEnabled(true);
                    int oldValue = t2.location;
                    Random rnd = new Random();
                    int randomNumber = rnd.nextInt(6 - 1 + 1) + 1;
                    t2.location = t2.location + randomNumber;
                    btn2.setText(String.valueOf(randomNumber));
                    if (t2.location == 99) {
                        flag = 1;
                        t2._turn = false;
                        btn2.setEnabled(false);
                        t1._turn = false;
                        btn1.setEnabled(false);
                    }
                    if (t2.location >= 100) {
                        t2.location = oldValue;
                    } else {
                        t2.setBackground(coordinatesArray[t2.location]._c);
                        t2.setBounds(coordinatesArray[t2.location]._x + 15, coordinatesArray[t2.location]._y + 15, 35, 35);
                        if (coordinatesArray[t2.location].isSnakeOrLeader) {
                            try {
                                TimeUnit.MILLISECONDS.sleep(1000);
                                t2.setBounds(coordinatesArray[coordinatesArray[t2.location].nextValue]._x + 15, coordinatesArray[coordinatesArray[t2.location].nextValue]._y + 15, 35, 35);
                                t2.location = coordinatesArray[t2.location].nextValue;
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }

                    if (flag == 1) {
                        flag = 0;
                        whoIsTurn.setBounds(250, 580, 250, 100);
                        whoIsTurn.setFont(new Font("Serif", Font.BOLD, 20));
                        whoIsTurn.setText("¡Jugador 2 ha ganado!");
                        again.setVisible(true);
                    } else {
                        whoIsTurn.setText("<-");
                    }

                    t2._turn = false;
                    btn2.setEnabled(false);
                    Message msg = new Message(Message.Message_Type.Locations);
                    int[] array = {oldValue, randomNumber};
                    msg.array = array;
                    Juego.Client.Send(msg);
                }
            }
        });

        whoIsTurn.setBounds(350, 580, 100, 100);	//Lugar para señalar los turnos.
        whoIsTurn.setFont(new Font("Serif", Font.BOLD, 40));
        whoIsTurn.setText("<-");
        obj.add(whoIsTurn);

        try {
			//Agregar imagen de fondo.
            BufferedImage mypic = ImageIO.read(new File("src\\Juego\\background.jpg"));
            JLabel picLabel = new JLabel(new ImageIcon(mypic));
            picLabel.setBounds(0, -40, 700, 700);
            obj.add(picLabel);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

		//Boton para volver a jugar.
        again.setBounds(300, 650, 100, 50);
        again.setVisible(false);
        obj.add(again);
        again.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //Configuraciones para otra partida.
                again.setVisible(false);
                t1.setBounds(coordinatesArray[0]._x + 15, coordinatesArray[0]._y + 15, 35, 35);
                t1.location = 0;
                t2.setBounds(coordinatesArray[0]._x + 15, coordinatesArray[0]._y + 15, 35, 35);
                t2.location = 0;
                player1.setText("Tu turno");
                player2.setText("Tu turno");
                t1._turn = true;
                btn1.setEnabled(true);
                t2._turn = false;
                btn2.setEnabled(false);
                whoIsTurn.setBounds(350, 580, 100, 100);
                whoIsTurn.setFont(new Font("Serif", Font.BOLD, 40));
                whoIsTurn.setText("<-");
            }
        });

        connect.setBounds(300, 650, 100, 50);	//Boton para conectarse al servidor
        connect.setVisible(true);
        obj.add(connect);
        connect.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
				//Si el jugador solo ve su boton.
                Juego.Client.Start("192.168.100.1", 8080);
                try {
                    Thread.sleep(8080);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Jugador numero " + player);
                if (player % 2 == 0) {
                    btn1.setVisible(true);
                    btn2.setVisible(false);
                } else if (player % 2 == 1) {
                    btn2.setVisible(true);
                    btn1.setVisible(false);
                } else {
                    btn1.setVisible(false);
                    btn2.setVisible(false);
                }
                connect.setVisible(false);
            }
        });
		//Agregar los paneles.
        obj.add(panel2);
        obj.add(panel1);

        obj.setResizable(false);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.setVisible(true);
    }

    public static void getCoordinates() {
		//Se ajustan los cuadros del juego.
        int number = 0;
        int x = 6, y = 546;
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                x = 6;
                for (int j = 0; j < 10; j++) {
                    number++;
                    Coordenada c;
                    if (j % 2 == 0) {
                        c = new Coordenada(x, y, Color.yellow);
                    } else {
                        c = new Coordenada(x, y, Color.white);
                    }
                    coordinatesArray[number - 1] = c;
                    x += 70;
                }
            } else {
                x = 636;
                for (int j = 0; j < 10; j++) {
                    number++;
                    Coordenada c;
                    if (j % 2 == 0) {
                        c = new Coordenada(x, y, Color.yellow);
                    } else {
                        c = new Coordenada(x, y, Color.white);
                    }
                    coordinatesArray[number - 1] = c;
                    x -= 70;
                }
            }
            y -= 60;
        }
		//Lugares donde hay serpientes y escaleras.
        coordinatesArray[2].isSnakeOrLeader = true;
        coordinatesArray[2].nextValue = 38;
        coordinatesArray[9].isSnakeOrLeader = true;
        coordinatesArray[9].nextValue = 11;
        coordinatesArray[15].isSnakeOrLeader = true;
        coordinatesArray[15].nextValue = 12;
        coordinatesArray[26].isSnakeOrLeader = true;
        coordinatesArray[26].nextValue = 52;
        coordinatesArray[30].isSnakeOrLeader = true;
        coordinatesArray[30].nextValue = 3;
        coordinatesArray[46].isSnakeOrLeader = true;
        coordinatesArray[46].nextValue = 24;
        coordinatesArray[55].isSnakeOrLeader = true;
        coordinatesArray[55].nextValue = 83;
        coordinatesArray[60].isSnakeOrLeader = true;
        coordinatesArray[60].nextValue = 98;
        coordinatesArray[62].isSnakeOrLeader = true;
        coordinatesArray[62].nextValue = 59;
        coordinatesArray[65].isSnakeOrLeader = true;
        coordinatesArray[65].nextValue = 51;
        coordinatesArray[71].isSnakeOrLeader = true;
        coordinatesArray[71].nextValue = 89;
        coordinatesArray[96].isSnakeOrLeader = true;
        coordinatesArray[96].nextValue = 74;
    }
}
