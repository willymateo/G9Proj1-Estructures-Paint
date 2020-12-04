/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author Willy Mateo
 */
public class MainView {
    private BorderPane root;

    public MainView() {
        root = new BorderPane();
        createTop();
        createCenter();
        createBottom();
    }
    
    public void createTop() {
        Label lbl_Titulo = new Label("Color Canvas");
        VBox vB_Top = new VBox(lbl_Titulo);
        vB_Top.setAlignment(Pos.CENTER);
        root.setTop(vB_Top);
        root.setPadding(new Insets(10));
    }
    
    public void createCenter(){
        
    }
    
    public void createBottom() {
        Button btn_PlayPause = new Button();
        Button btn_Step = new Button();
        btn_PlayPause.setGraphic(new ImageView(new Image("resources/icons/play.png")));
        btn_Step.setGraphic(new ImageView(new Image("resources/icons/paso.png")));
        
        HBox hB_Bottom = new HBox(btn_PlayPause, btn_Step);
        hB_Bottom.setPadding(new Insets(10));
        hB_Bottom.setAlignment(Pos.CENTER);
        root.setBottom(hB_Bottom);
    }
    
    public BorderPane getRoot() {
        return root;
    }
    
}
