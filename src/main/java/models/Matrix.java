/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import data.ClusterComparator;
import data.ManejadorColor;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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
        clusters = new PriorityQueue<>(new ClusterComparator());
        getClustersQueue();
        resetMatrixBin();
    }
    
    public GridPane getGridP_Contenedor() {
        return gridP_Contenedor;
    }

    public PriorityQueue<Cluster> getClusters() {
        return clusters;
    }

    public int[][] getMatrixBin() {
        return matrixBin;
    }

    public int[][] getMatrixNum() {
        return matrixNum;
    }
    
    public void resetMatrixBin(){
        matrixBin = new int[rows][columns];
    }
    
    public boolean leftIsEqual(Coordenada coord){
        if (coord.getX() == 0 )return false;
        return matrixNum[coord.getY()][coord.getX()-1] == matrixNum[coord.getY()][coord.getX()];
    }
    
    public boolean rightIsEqual(Coordenada coord){
        if (coord.getX() == columns-1)return false;
        return matrixNum[coord.getY()][coord.getX()+1] == matrixNum[coord.getY()][coord.getX()];
    }
    
    public boolean upIsEqual(Coordenada coord){
        if (coord.getY() == 0)return false;
        return matrixNum[coord.getY()-1][coord.getX()] == matrixNum[coord.getY()][coord.getX()];
    }
    
    public boolean downIsEqual(Coordenada coord){
        if (coord.getY() == rows-1)return false;
        return matrixNum[coord.getY()+1][coord.getX()] == matrixNum[coord.getY()][coord.getX()];
    }
    
    public boolean leftIsEmpty(Coordenada coord){
        if (coord.getX() == 0 )return false;
        return matrixBin[coord.getY()][coord.getX()-1] <= 0;
    }
    
    public boolean rightIsEmpty(Coordenada coord){
        if (coord.getX() == columns-1)return false;
        return matrixBin[coord.getY()][coord.getX()+1] <= 0;
    }
    
    public boolean upIsEmpty(Coordenada coord){
        if (coord.getY() == 0 )return false;
        return matrixBin[coord.getY()-1][coord.getX()] <= 0;
    }
    
    public boolean downIsEmpty(Coordenada coord){
        if (coord.getY() == rows-1)return false;
        return matrixBin[coord.getY()+1][coord.getX()] <= 0;
    }

    public boolean isEmpty(Coordenada coord){
        return matrixBin[coord.getY()][coord.getX()] == 0;
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
        Color color = ManejadorColor.buscarColor(matrixNum[coord.getY()][coord.getX()]);
        Coordenada pixelLeft = new Coordenada(columns-1,0);
        Cluster clusterNew = new Cluster(color, pixelLeft);
        
        boolean continuar = true;
        
        while (continuar) {            
            if (matrixBin[coord.getY()][coord.getX()] == 0) {
                clusterNew.addPixel(coord);
                pila.push(coord);
                putInGrid(coord, clusterNew.getPixel(coord));
                matrixBin[coord.getY()][coord.getX()] = 1;
            }
            if(rightIsEqual(coord) && rightIsEmpty(coord)){
                coord = new Coordenada(coord.getX()+1, coord.getY());
            }else if(upIsEqual(coord) && upIsEmpty(coord)){
                coord = new Coordenada(coord.getX(), coord.getY()+1);
            }else if(leftIsEqual(coord) && leftIsEmpty(coord)){
                coord = new Coordenada(coord.getX()-1, coord.getY());
            }else if(downIsEqual(coord) && downIsEmpty(coord)){
                coord = new Coordenada(coord.getX(), coord.getY()+1);
            }else {
                if(!pila.isEmpty()){
                    coord = pila.pop();
                }else{continuar = false;} 
            }
        }
        return clusterNew;
    }
    
    private void putInGrid(Coordenada coordenada, Rectangle shape){
        gridP_Contenedor.add(shape, coordenada.getX(), coordenada.getY());
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
