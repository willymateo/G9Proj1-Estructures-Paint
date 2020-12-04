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
    private Rectangle shape;
    private Color color;
    private Coordenada coordenada;

    public Pixel(Color color, Coordenada coordenada) {
        this.color = color;
        this.coordenada = coordenada;
        this.shape = new Rectangle(5, 5, color);
    }

}
