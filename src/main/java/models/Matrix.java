/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import data.ManejadorColor;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 *
 * @author Willy Mateo
 */
public class Matrix {
    private int[][] matrixNum;
    private int[][] matrixBin;
    private int rows;
    private int columns;
    private PriorityQueue<Cluster> clusters;
    private GridPane contenedor;

    public Matrix() {
        contenedor = new GridPane();
    }
    
    public Matrix(int[][] matrixNum) {
        this();
        this.matrixNum = matrixNum;
        setRowColumns();
        matrixBin = new int[rows][columns];
        clusters = new PriorityQueue<>();
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
        for (int row = 0; row < rows-1; row++) {
            for (int column = 0; column < columns-1; column++) {
                if (matrixBin[row][column] == 0) {
                    Cluster clusterNew = getCluster(new Coordenada(column, row));
                    clusters.offer(clusterNew);
                }
            }
        }
    }
    
    private Cluster getCluster(Coordenada coord){
        ArrayDeque<Coordenada> pila = new ArrayDeque<>();
        List<Pixel> pixeles = new ArrayList<>();
        int colorNum = matrixNum[coord.getY()][coord.getX()];
        int idPixelLeft = matrixNum[0].length-1;
        boolean continuar = true;
        
        while (continuar) {            
            if (matrixBin[coord.getY()][coord.getX()] == 0) {
                pixeles.add(new Pixel(coord));
                pila.push(coord);
                matrixBin[coord.getY()][coord.getX()] = 1;
            }
            if(rightIsEqual(coord) && rightIsEmpty(coord)){
                coord = new Coordenada(coord.getX()+1, coord.getY());
            }else if(upIsEqual(coord) && upIsEmpty(coord)){
                coord = new Coordenada(coord.getX(), coord.getY()+1);
            }else if(leftIsEqual(coord) && leftIsEmpty(coord)){
                if (coord.getX()-1 < idPixelLeft) {
                    idPixelLeft = coord.getX()-1;
                }
                coord = new Coordenada(coord.getX()-1, coord.getY());
            }else if(downIsEqual(coord) && downIsEmpty(coord)){
                coord = new Coordenada(coord.getX(), coord.getY()+1);
            }else {
                if(!pila.isEmpty()){
                    coord = pila.pop();
                }else{continuar = false;} 
            }
        }
        Color color = ManejadorColor.buscarColor(colorNum);
        Cluster clusterNew = new Cluster(color, idPixelLeft, pixeles.toArray(new Pixel[pixeles.size()]));
        return clusterNew;
    }
    
    private boolean leftIsEqual(Coordenada coord){
        if (coord.getX() == 0 )return false;
        return matrixNum[coord.getY()][coord.getX()-1] == matrixNum[coord.getY()][coord.getX()];
    }
    
    private boolean rightIsEqual(Coordenada coord){
        if (coord.getX() == columns-1)return false;
        return matrixNum[coord.getY()][coord.getX()+1] == matrixNum[coord.getY()][coord.getX()];
    }
    
    private boolean upIsEqual(Coordenada coord){
        if (coord.getY() == 0)return false;
        return matrixNum[coord.getY()-1][coord.getX()] == matrixNum[coord.getY()][coord.getX()];
    }
    
    private boolean downIsEqual(Coordenada coord){
        if (coord.getY() == rows-1)return false;
        return matrixNum[coord.getY()+1][coord.getX()] == matrixNum[coord.getY()][coord.getX()];
    }
    
    private boolean leftIsEmpty(Coordenada coord){
        if (coord.getX() == 0 )return false;
        return matrixBin[coord.getY()][coord.getX()-1] <= 0;
    }
    
    private boolean rightIsEmpty(Coordenada coord){
        if (coord.getX() == columns-1)return false;
        return matrixBin[coord.getY()][coord.getX()+1] <= 0;
    }
    
    private boolean upIsEmpty(Coordenada coord){
        if (coord.getY() == 0 )return false;
        return matrixBin[coord.getY()-1][coord.getX()] <= 0;
    }
    
    private boolean downIsEmpty(Coordenada coord){
        if (coord.getY() == rows-1)return false;
        return matrixBin[coord.getY()+1][coord.getX()] <= 0;
    }
}
