/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import data.ManejadorColor;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.PriorityQueue;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 *
 * @author Willy Mateo
 */
public class Matrix {
    private int[][] matrixNum;
    private int rows;
    private int columns;
    private PriorityQueue<Cluster> clusters;
    private GridPane contenedor;

    public Matrix() {
        contenedor = new GridPane();
    }
    
    public Matrix(int[][] matrixBin) {
        this();
        this.matrixNum = matrixBin;
        setRowColumns();
        getClustersQueue();
    }
    
    private void setRowColumns(){
        rows = matrixNum.length;
        columns = matrixNum[0].length;
    }
    
    @Override
    public String toString() {
        StringBuilder texto = new StringBuilder();
        for (int[] fila : matrixNum){
            for (int j = 0; j<fila.length; j++)
                texto.append(fila[j]).append(" ");
            texto.append(System.getProperty("line.separator"));
        }
        return texto.toString();
    }
    
    private void getClustersQueue(){
        int[][] matrixBin = new int[rows][columns];
        
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                if (matrixBin[row][column] == 0) {
                    Coordenada cord = new Coordenada(row, column);
                    clusters.offer(getCluster(matrixBin, cord));
                }
            }
        }
    }
    
    private Cluster getCluster(int[][] matrixBin, Coordenada coord){
        ArrayDeque<Coordenada> pila = new ArrayDeque<>();
        ArrayList<Pixel> pixeles = new ArrayList<>();
        int colorNum = matrixNum[coord.getY()][coord.getX()];
        int idPixelLeft = matrixNum[0].length-1;
        boolean continuar = true;
        
        while (continuar) {            
            if (matrixBin[coord.getY()][coord.getX()] == 0) {
                pixeles.add(new Pixel(coord));
                pila.push(coord);
                matrixBin[coord.getY()][coord.getX()] = 1;
            }
            
            if(rightIsEqual(coord)){
                coord = new Coordenada(coord.getX(), coord.getY()-1);
            }else if(upIsEqual(coord)){
                coord = new Coordenada(coord.getX(), coord.getY()+1);
            }else if(leftIsEqual(coord)){
                if (coord.getX()-1 < idPixelLeft) {
                    idPixelLeft = coord.getX()-1;
                }
                coord = new Coordenada(coord.getX()-1, coord.getY());
            }else if(downIsEqual(coord)){
                coord = new Coordenada(coord.getX()+1, coord.getY());
            }else {
                if(!pila.isEmpty()){
                    coord = pila.pop();
                }else{continuar = false;} 
            }
        }
        Color color = ManejadorColor.buscarColor(colorNum);
        return new Cluster(color, idPixelLeft, (Pixel[]) pixeles.toArray());
    }
    
    private boolean leftIsEqual(Coordenada coord){
        if (coord.getX() == 0 )return false;
        return matrixNum[coord.getY()][coord.getX()-1] == matrixNum[coord.getY()][coord.getX()];
    }
    
    private boolean rightIsEqual(Coordenada coord){
        if (coord.getX() == matrixNum[0].length-1 )return false;
        return matrixNum[coord.getY()][coord.getX()+1] == matrixNum[coord.getY()][coord.getX()];
    }
    
    private boolean upIsEqual(Coordenada coord){
        if (coord.getY() == 0 )return false;
        return matrixNum[coord.getY()-1][coord.getX()] == matrixNum[coord.getY()][coord.getX()];
    }
    
    private boolean downIsEqual(Coordenada coord){
        if (coord.getY() == matrixNum.length-1 )return false;
        return matrixNum[coord.getY()+1][coord.getX()] == matrixNum[coord.getY()][coord.getX()];
    }
    
}
