/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Willy Mateo
 */
public class Pixel {
    private final Rectangle shape;
    private final Coordenada coordenada;

    public Pixel(Coordenada coordenada, Color color) {
        this.coordenada = coordenada;
        shape = new Rectangle(30, 30, color);
        shape.setStroke(color.BLACK);
    }

    public Coordenada getCoordenada() {
        return coordenada;
    }

    public Rectangle getShape() {
        return shape;
    }

    @Override
    public String toString() {
        return coordenada.toString();
    }
    
}
