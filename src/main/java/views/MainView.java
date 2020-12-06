/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import data.ReadMatrix;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import models.Cluster;
import models.Coordenada;
import models.Matrix;

/**
 *
 * @author Willy Mateo
 */
public class MainView {
    private BorderPane root;
    private Matrix matrix;
    private long tiempo;
    private Color pincel;
    private boolean bandera;

    public MainView() {
        root = new BorderPane();
        tiempo = 2000;
        bandera = false;
        pincel = Color.LIGHTCORAL;
        loadFiles();
        createTop();
        createCenter();
        createBottom();
    }
    
    private void loadFiles(){
        matrix = new Matrix(ReadMatrix.readMatrixBin("resources/files/cuadro.txt"));
    }
    
    private void createTop() {
        Label lbl_Titulo = new Label("Color Canvas");
        VBox vB_Top = new VBox(lbl_Titulo);
        vB_Top.setAlignment(Pos.CENTER);
        root.setTop(vB_Top);
        root.setPadding(new Insets(10));
    }
    
    private void createCenter(){
        VBox vb_Pila = new VBox();
        HBox hb_Center = new HBox(matrix.getGridP_Contenedor(), vb_Pila);
        hb_Center.setAlignment(Pos.CENTER);
        root.setCenter(hb_Center);
    }
    
    private void createBottom() {
        Button btn_PlayPause = new Button("Play");
        Button btn_Step = new Button("Step");
        btn_PlayPause.setGraphicTextGap(3);
        btn_Step.setGraphicTextGap(3);
        try {
            btn_PlayPause.setGraphic(new ImageView(new Image("resources/icons/play.png")));
            btn_Step.setGraphic(new ImageView(new Image("resources/icons/paso.png")));
        } catch (IllegalArgumentException iaE) {
            System.out.println("No se pudieron cargar las imagenes");
        }
        
        HBox hB_Bottom = new HBox(btn_PlayPause, btn_Step);
        hB_Bottom.setPadding(new Insets(10));
        hB_Bottom.setAlignment(Pos.CENTER);
        root.setBottom(hB_Bottom);
        
        btn_Step.setOnMouseClicked((e)->{
            bandera = true;
        });
    }
    
    public BorderPane getRoot() {
        return root;
    }
    
    private class painThread implements Runnable{
        @Override
        public void run() {
            PriorityQueue<Cluster> queueCopy = new PriorityQueue<>();
            for (Cluster cluster : matrix.getClusters()) {
                queueCopy.offer(cluster);
            }
            
            matrix.resetMatrixBin();
            while (!queueCopy.isEmpty()) {                
                Cluster clusterNow = queueCopy.poll();
                Coordenada coordI = clusterNow.getPixeles()[0].getCoordenada();
                
            }
        }
        
        private void paint(Coordenada coord){
            ArrayDeque<Coordenada> pilaCoords = new ArrayDeque();
            boolean fin = false;
            while(!fin){
                if(matrix.isEmpty(coord)){
                    //matriz[p.x][p.y]=color;
                    pilaCoords.push(coord);
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
                    }else{fin = true;}
                }
            }
        }
    }
    
}
