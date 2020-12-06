/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import javafx.scene.paint.Color;

/**
 *
 * @author Willy Mateo
 */
public class ManejadorColor {
    public static Color buscarColor(int idColor){
        switch (idColor){
            case 0:
                return Color.WHITE;
            case 1:
                return Color.RED;
            case 2:
                return Color.LAWNGREEN;
            case 3:
                return Color.BLUE;
            case 4:
                return Color.ORANGE;
            default:
                return Color.WHITE;
        }
    } 
}
