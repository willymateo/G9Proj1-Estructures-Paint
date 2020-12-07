/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import data.PaintThread;
import data.ReadMatrix;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import models.Matrix;

/**
 *
 * @author Willy Mateo
 */
public class MainView {
    private final BorderPane root;
    private Matrix matrix;
    private int tiempo;
    private final Color pincel;
    private VBox vB_coordsQueue;
    private Thread paintThread;
    private Slider velocidad;

    public MainView() {
        root = new BorderPane();
        vB_coordsQueue = new VBox();
        tiempo = 1000;
        pincel = Color.ORANGE;
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
        HBox hb_Center = new HBox(matrix.getGridP_Contenedor(), vB_coordsQueue);
        hb_Center.setAlignment(Pos.BOTTOM_CENTER);
        root.setCenter(hb_Center);
    }
    
    private void createBottom() {
        velocidad = new Slider(1, 5, 1);
        velocidad.setShowTickLabels(true);
        velocidad.setShowTickMarks(true);
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
        HBox hB_Bottom = new HBox(velocidad,btn_PlayPause, btn_Step);
        hB_Bottom.setPadding(new Insets(20));
        hB_Bottom.setAlignment(Pos.CENTER);
        root.setBottom(hB_Bottom);
        
        btn_PlayPause.setOnMouseClicked((e)->{
            btn_Step.setDisable(true);
            if (btn_PlayPause.getText().equals("Play")) {
                btn_PlayPause.setText("Pause");
            }else {
                btn_PlayPause.setText("Play");
            }
            
            PaintThread pt = new PaintThread(matrix, vB_coordsQueue, pincel, tiempo);
            paintThread = new Thread(pt);
            vB_coordsQueue = pt.getvB_coordsQueue();
            paintThread.start();
        });
        
        velocidad.setOnMouseReleased((ev)->{
            tiempo = (int) (velocidad.getValue()*1000);
            //paintThread.set
        });
    }
    
    public BorderPane getRoot() {
        return root;
    }
    
}
