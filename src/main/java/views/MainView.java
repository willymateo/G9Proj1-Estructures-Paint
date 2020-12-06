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
    private long tiempo;
    private final Color pincel;

    public MainView() {
        root = new BorderPane();
        tiempo = 1000;
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
        
        btn_PlayPause.setOnMouseClicked((e)->{
            System.out.println("Clic play");
            btn_Step.setDisable(true);
            Thread paintThread = new Thread(new PaintThread(matrix, pincel, tiempo));
            paintThread.start();
        });
    }
    
    public BorderPane getRoot() {
        return root;
    }
    
}
