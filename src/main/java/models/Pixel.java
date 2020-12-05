/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javafx.scene.shape.Rectangle;

/**
 *
 * @author Willy Mateo
 */
public class Pixel {
    private final Rectangle shape;
    private final Coordenada coordenada;

    public Pixel(Coordenada coordenada) {
        this.coordenada = coordenada;
        this.shape = new Rectangle(5, 5);
    }

    @Override
    public String toString() {
        return coordenada.toString();
    }
    
}
