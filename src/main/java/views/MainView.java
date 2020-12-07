/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import data.PaintThread;
import data.ReadMatrix;
import data.StepPaintThread;
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
    private PaintThread paintThread;
    private StepPaintThread stepPaintThread;
    private Slider velocidad;
    private boolean primeraVez;
    public static boolean llavePause;
    public static boolean llaveStep;

    public MainView() {
        root = new BorderPane();
        vB_coordsQueue = new VBox();
        tiempo = 1000;
        pincel = Color.ORANGE;
        llavePause = false;
        llaveStep = false;
        loadFiles();
        createTop();
        createCenter();
        createBottom();
        primeraVez = true;
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
        paintThread = new PaintThread(matrix, vB_coordsQueue, pincel, tiempo);
        Thread thPlay = new Thread(paintThread);
        stepPaintThread = new StepPaintThread(matrix, vB_coordsQueue, pincel);
        Thread thStep = new Thread(stepPaintThread);
        
        velocidad = new Slider(1, 5, 1);
        velocidad.setShowTickLabels(true);
        velocidad.setShowTickMarks(true);
        Button btn_PlayPause = new Button("Play");
        Button btn_Step = new Button("Step");
        btn_PlayPause.setGraphicTextGap(3);
        btn_Step.setGraphicTextGap(3);
        try {
            btn_Step.setGraphic(new ImageView(new Image("resources/icons/step.jpg")));
            btn_PlayPause.setGraphic(new ImageView(new Image("resources/icons/play.png")));
        } catch (IllegalArgumentException iaE) {
            System.out.println("No se pudieron cargar las imagenes");
        }
        HBox hB_Bottom = new HBox(velocidad,btn_PlayPause, btn_Step);
        hB_Bottom.setPadding(new Insets(20));
        hB_Bottom.setAlignment(Pos.CENTER);
        root.setBottom(hB_Bottom);
        
        btn_PlayPause.setOnMouseClicked((e)->{
            btn_Step.setDisable(true);
            if (primeraVez) {
                thPlay.start();
                primeraVez = false;
                btn_PlayPause.setText("Pause");
            }else if (PaintThread.suspend){
                paintThread = new PaintThread(matrix, vB_coordsQueue, pincel, tiempo);
                new Thread(paintThread).start();
            }
            
            if (llavePause) {
                btn_PlayPause.setText("Pause");
                paintThread.reanueThread();
            }else {
                btn_PlayPause.setText("Play");
                paintThread.pauseThread();
            }
        });
        
        btn_Step.setOnMouseClicked((e)->{
            llaveStep = false;
            btn_PlayPause.setDisable(true);
            if (!primeraVez) {
                thStep.start();
                primeraVez = false;
            }else if (stepPaintThread.suspend){
                stepPaintThread = new StepPaintThread(matrix, vB_coordsQueue, pincel); 
                new Thread(stepPaintThread).start();
            }
        });
        
        velocidad.setOnMouseReleased((ev)->{
            tiempo = (int) (velocidad.getValue()*1000);
            PaintThread.setMilis(tiempo);
        });
    }
    
    public BorderPane getRoot() {
        return root;
    }
    
}
