/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import data.ReadMatrix;
import models.Matrix;

/**
 *
 * @author Willy Mateo
 */
public class Tests {
    public static void main(String[] args) {
        Matrix m = new Matrix(ReadMatrix.readMatrixBin("resources/files/cuadro.txt"));
        System.out.println(m);
    }
}
