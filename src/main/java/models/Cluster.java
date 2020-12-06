/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javafx.scene.paint.Color;

/**
 *
 * @author Willy Mateo
 */
public class Cluster implements Comparable<Cluster>{
    private Color color;
    private int size;
    private int idPixelLeft;
    private Pixel[] pixeles;

    public Cluster(Color color, int idPixelLeft, Pixel[] pixeles) {
        this.color = color;
        this.size = pixeles.length;
        this.idPixelLeft = idPixelLeft;
        this.pixeles = pixeles;
    }

    public Pixel[] getPixeles() {
        return pixeles;
    }

    @Override
    public int compareTo(Cluster cl2) {
        int cSize = Integer.compare(this.size, cl2.size)*-1;
        if (cSize == 0) {
            int cIzq = Integer.compare(this.idPixelLeft, cl2.idPixelLeft);
            return cIzq;
        } else {
            return cSize;
        }
    }
}
