package proceso;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Tablero extends JPanel implements Runnable{

    private final Image background;
    private  Image personas;
    private  Image personas2;
    private Thread hilo;
    private JLabel label;
    
    private  int x,y;
    private int x1, y1;
    private int i;
    private int contador = 0;
    private int contador1 = 0;
    private String capacidad;
    
    
    public Tablero(){
        
        setBackground(Color.WHITE);
        setDoubleBuffered(true);
        background = new ImageIcon(this.getClass().getResource("/img/fondo.jpg")).getImage();
        x=-50;
        y=170;
        x1=800;
        y1=170;
        
        tortuga();
        liebre();
        
        label = new JLabel ("Capacidad alcanzada "+(contador+contador1));
        label.setBounds(400,10,100,50);
        
        
        hilo = new Thread(this);
        hilo.start();
   }
    
    void tortuga(){
        personas = new ImageIcon(this.getClass().getResource("/img/personas.jpg")).getImage();
        
        
    }
    void liebre(){
        personas2 = new ImageIcon(this.getClass().getResource("/img/personas2.jpg")).getImage();
       
    }
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(background, 0,0, null);
        g2.drawImage(personas,x,y, null);
        g2.drawImage(personas2,x1,y1,null);
        g2.drawString(capacidad, 450, 20);
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }
    
    public void ciclo(){
                
                x += 50;
            if ( x > 360 ){
                x = -50;
                contador= contador+20;
            }
           
        
        
    }
    
    public void ciclo2(){
                
                x1 -= 50;
            if ( x1 == 350 ){
                x1 = 800;
                contador1 = contador1+20;
            }
            
    }
    
    
    
    @Override
    public void run() {
        while(contador != 500){
            
            
                ciclo();
                
                ciclo2();
                capacidad = "Capacidad alcanzada: " +(contador+contador1);
                repaint();        
            
               
                
            try{
                Thread.sleep(500);
            
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }
    

    
}
