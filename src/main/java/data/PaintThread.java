/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.ArrayDeque;
import java.util.PriorityQueue;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import models.Cluster;
import models.Coordenada;
import models.Matrix;
import views.MainView;

/**
 *
 * @author Willy Mateo
 */
public class PaintThread implements Runnable{
    private final Matrix matrix;
    private static int milis;
    private final Color pincel;
    private final VBox vB_coordsQueue;
    public static boolean suspend;
    private Button btn_des;
    
    public PaintThread(Matrix matrix, VBox vB_coordsQueue, Color pincel) {
        this.matrix = matrix;
        this.pincel = pincel;
        this.vB_coordsQueue = vB_coordsQueue;
        suspend = false;
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
        suspend = true;
        matrix.originColor();
    }
    
    private void paint(Cluster cluster, Coordenada coord){
        ArrayDeque<Coordenada> pilaCoords = new ArrayDeque();
        boolean continuar = true;
        while(!suspend && continuar){
            try {
                while (isPaused()) {                    
                    Thread.sleep(1000);
                }
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
                while (MainView.llaveStep) {                        
                    Thread.sleep(1000);
                }
                MainView.llaveStep = true;
            } catch (InterruptedException iE) {
                System.out.print("Se finalizó el hilo de pintar por un error inesperado.");
                suspend = true;
            }
        }
    }
    
    public static void killThread(){
        suspend = true;
        MainView.llavePause = false;
    }
    
    public void pauseThread(){
        MainView.llavePause = true;
    }
    
    public synchronized void reanueThread(){
        MainView.llavePause = false;
    }
    
    public boolean isPaused(){
        return MainView.llavePause;
    }

    public static void setMilis(int milis) {
        PaintThread.milis = milis;
    }
    
}
