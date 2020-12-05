/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import models.Coordenada;

/**
 *
 * @author Willy Mateo
 */
public class ManejadorMatrix {
    
    public static boolean leftIsEmpty(int[][] matrix, Coordenada coord){
        if (coord.getX() == 0 )return false;
        return matrix[coord.getY()][coord.getX()-1] <= 0;
    }
    
    public static boolean rightIsEmpty(int[][] matrix, Coordenada coord){
        if (coord.getX() == matrix[0].length-1 )return false;
        return matrix[coord.getY()][coord.getX()+1] <= 0;
    }
    
    public static boolean upIsEmpty(int[][] matrix, Coordenada coord){
        if (coord.getY() == 0 )return false;
        return matrix[coord.getY()-1][coord.getX()] <= 0;
    }
    
    public static boolean downIsEmpty(int[][] matrix, Coordenada coord){
        if (coord.getY() == matrix.length-1 )return false;
        return matrix[coord.getY()+1][coord.getX()] <= 0;
    }
    
}
