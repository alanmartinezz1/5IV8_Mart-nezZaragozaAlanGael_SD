package proceso;

import javax.swing.JFrame;

public class Principal extends JFrame{
    
    public Principal(){
        
       setTitle("Concierto de Crocksito, Martínez Zaragoza Alan Gael, 5IV8");
       setDefaultCloseOperation(EXIT_ON_CLOSE);
       setSize(1000,500);
       setLocationRelativeTo(null);
       setResizable(false);
       add(new Tablero());
       setVisible(true);
       
       
    }
    
    public static void main(String args[]){
       new Principal();
    }
    
}
