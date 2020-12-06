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
    private GridPane gridP_Contenedor;

    public Matrix() {
        gridP_Contenedor = new GridPane();
        gridP_Contenedor.setGridLinesVisible(true);
    }
    
    public Matrix(int[][] matrixNum) {
        this();
        this.matrixNum = matrixNum;
        setRowColumns();
        matrixBin = new int[rows][columns];
        clusters = new PriorityQueue<>();
        getClustersQueue();
        System.out.println("cantidad de elementos en pila: " +clusters.size());
    }
    
    public GridPane getGridP_Contenedor() {
        return gridP_Contenedor;
    }

    public PriorityQueue<Cluster> getClusters() {
        return clusters;
    }
    
    private void setRowColumns(){
        rows = matrixNum.length;
        columns = matrixNum[0].length;
    }   
    
    private void getClustersQueue(){
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                if (matrixBin[row][column] == 0) {
                    clusters.offer(getCluster(new Coordenada(column, row)));
                }
            }
        }
    }
    
    private Cluster getCluster(Coordenada coord){
        ArrayDeque<Coordenada> pila = new ArrayDeque<>();
        List<Pixel> pixeles = new ArrayList<>();
        Color color = ManejadorColor.buscarColor(matrixNum[coord.getY()][coord.getX()]);
        int idPixelLeft = matrixNum[0].length-1;
        boolean continuar = true;
        
        while (continuar) {            
            if (matrixBin[coord.getY()][coord.getX()] == 0) {
                Pixel pixel = new Pixel(coord, color);
                pixeles.add(pixel);
                pila.push(coord);
                putInGrid(pixel);
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
        return new Cluster(color, idPixelLeft, pixeles.toArray(new Pixel[pixeles.size()]));
    }
    
    private void putInGrid(Pixel pixel){
        int idRow = pixel.getCoordenada().getY();
        int idColumn = pixel.getCoordenada().getX();
        gridP_Contenedor.add(pixel.getShape(), idColumn, idRow);
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
}
