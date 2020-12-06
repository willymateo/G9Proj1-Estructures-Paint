/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Willy Mateo
 */
public class Cluster{
    private Color color;
    private int size;
    private Coordenada coordLeft;
    private Map<Coordenada, Rectangle> pixelesMap = new HashMap<>();

    public Cluster(Color color, Coordenada coordLeft) {
        this.color = color;
        this.coordLeft = coordLeft;
    }
    
    public Cluster(Color color, Coordenada coordLeft, HashMap<Coordenada, Rectangle> pixelesMap) {
        this(color, coordLeft);
        this.pixelesMap = pixelesMap;
        size = pixelesMap.size();
    }

    public Map<Coordenada, Rectangle> getPixelesMap() {
        return pixelesMap;
    }
    
    public Rectangle getPixel(Coordenada coordenada){
        return pixelesMap.get(coordenada);
    }

    public int getSize() {
        return size;
    }

    public Coordenada getCoordLeft() {
        return coordLeft;
    }

    private void evaluateCoordLeft(Coordenada coordenada) {
        if (coordenada.getX()-1 < this.coordLeft.getX()) {
            this.coordLeft = coordenada;
        }
    }
    
    public void addPixel(Coordenada coordenada){
        Rectangle shape = new Rectangle(30, 30, color);
        shape.setStroke(color.BLACK);
        pixelesMap.put(coordenada, shape);
        size++;
        evaluateCoordLeft(coordenada);
    }
    
}
