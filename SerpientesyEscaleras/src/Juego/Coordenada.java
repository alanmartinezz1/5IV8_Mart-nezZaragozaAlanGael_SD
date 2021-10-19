/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Juego;


import java.awt.Color;
/**
 *
 * @author user
 */
public class Coordenada {
    
    boolean isSnakeOrLeader = false;
    int nextValue;
    int _x;
    int _y;
    Color _c;
    
    public Coordenada(int x, int y, Color c)
    {
        _x =x;
        _y = y;
        _c = c;
    }
    
}
