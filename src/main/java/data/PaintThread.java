/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.ArrayDeque;
import java.util.PriorityQueue;
import javafx.scene.paint.Color;
import models.Cluster;
import models.Coordenada;
import models.Matrix;

/**
 *
 * @author Willy Mateo
 */
public class PaintThread implements Runnable{
    private Matrix matrix;
    private boolean bandera;
    private long milis;
    private Color pincel;
    
    public PaintThread(Matrix matrix, Color pincel) {
        this.matrix = matrix;
        this.pincel = pincel;
        milis = 1000;
    }
    
    public PaintThread(Matrix matrix, Color pincel, long milis) {
        this(matrix, pincel);
        this.milis = milis;
    }
    
    @Override
    public void run() {
        PriorityQueue<Cluster> queueCopy = new PriorityQueue<>(new ClusterComparator());
            for (Cluster cluster : matrix.getClusters()) {
                queueCopy.offer(cluster);
            }
            
            matrix.resetMatrixBin();
            while (!queueCopy.isEmpty()) {                
                Cluster clusterNow = queueCopy.poll();
                paint(clusterNow, clusterNow.getCoordLeft());
            }
    }
    
    private void paint(Cluster cluster, Coordenada coord){
        ArrayDeque<Coordenada> pilaCoords = new ArrayDeque();
        boolean continuar = true;
        while(continuar){
            try {
                if(matrix.isEmpty(coord)){
                    cluster.getPixel(coord).setFill(pincel);
                    pilaCoords.push(coord);
                    matrix.getMatrixBin()[coord.getY()][coord.getX()] = 1;
                    Thread.sleep(milis);
                }
                if(matrix.rightIsEmpty(coord) && matrix.rightIsEqual(coord)){
                    coord = new Coordenada(coord.getX()+1, coord.getY());
                }else if(matrix.upIsEmpty(coord) && matrix.upIsEqual(coord)){
                    coord = new Coordenada(coord.getX(), coord.getY()-1);
                }else if(matrix.leftIsEmpty(coord) && matrix.leftIsEqual(coord)){
                    coord = new Coordenada(coord.getX()-1, coord.getY());
                }else if(matrix.downIsEmpty(coord) && matrix.downIsEqual(coord)){
                    coord = new Coordenada(coord.getX(), coord.getY()+1);
                }else {
                    if(!pilaCoords.isEmpty()){
                        coord = pilaCoords.pop();
                    }else{continuar = false;}
                }
            } catch (InterruptedException iE) {
                System.out.print("Se finalizó el hilo de pintar por un error inesperado.");
                continuar = false;
            }
            
        }
    }
}
