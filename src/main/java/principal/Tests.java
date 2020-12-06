/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import data.PaintThread;
import data.ReadMatrix;
import javafx.scene.paint.Color;
import models.Matrix;

/**
 *
 * @author Willy Mateo
 */
public class Tests {
    public static void main(String[] args) {
        Matrix matrix = new Matrix(ReadMatrix.readMatrixBin("resources/files/cuadro.txt"));
        Thread painThread = new Thread(new PaintThread(matrix, Color.LINEN));
        painThread.start();
    }
}
