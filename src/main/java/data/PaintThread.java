/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.ArrayDeque;
import java.util.PriorityQueue;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import models.Cluster;
import models.Coordenada;
import models.Matrix;

/**
 *
 * @author Willy Mateo
 */
public class PaintThread implements Runnable{
    private final Matrix matrix;
    private static boolean continuar;
    private int milis;
    private final Color pincel;
    private final VBox vB_coordsQueue;
    private boolean suspend;
    private boolean pause;
    
    public PaintThread(Matrix matrix, VBox vB_coordsQueue, Color pincel) {
        this.matrix = matrix;
        this.pincel = pincel;
        this.vB_coordsQueue = vB_coordsQueue;        
        milis = 1000;
    }
    
    public PaintThread(Matrix matrix, VBox vB_coordsQueue, Color pincel, int milis) {
        this(matrix, vB_coordsQueue, pincel);
        this.milis = milis;
    }

    public VBox getvB_coordsQueue() {
        return vB_coordsQueue;
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
        continuar = true;
        while(continuar){
            try {
                if(matrix.isEmpty(coord)){
                    cluster.getPixel(coord).setFill(pincel);
                    pilaCoords.push(coord);
                    
                    final int x = coord.getX();
                    final int y = coord.getY();
                    Platform.runLater(()->{
                        final Coordenada coordenada = new Coordenada(x, y);
                        vB_coordsQueue.getChildren().add(0,new Label(coordenada.toString()));
                    });
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
                        Platform.runLater(()->{
                            vB_coordsQueue.getChildren().remove(0);
                        });
                        Thread.sleep(milis);
                    }else{continuar = false;}
                }
                
                if (suspend) {
                    break;
                }
                if (pause) {
                    wait();
                }
            } catch (InterruptedException iE) {
                System.out.print("Se finalizó el hilo de pintar por un error inesperado.");
                continuar = false;
            }
        }
    }
    
    public void killThread(){
        suspend = false;
    }
    
    public void pauseThread(){
        pause = true;
    }
    
    public void reanueThread(){
        pause = false;
        notify();
    }
    
    public boolean isPaused(){
        return pause;
    }

    public void setMilis(int milis) {
        this.milis = milis;
    }
    
}
