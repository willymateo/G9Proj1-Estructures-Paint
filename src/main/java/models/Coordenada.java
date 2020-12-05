/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Willy Mateo
 */
public class Coordenada {
    private final int x;
    private final int y;

    public Coordenada(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" +x +", " +y +")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Coordenada && obj != null) {
            Coordenada cord2 = (Coordenada)obj;
            if (x == cord2.x && y == cord2.y) {
                return true;
            }
        }
        return false;
    }
    
}
