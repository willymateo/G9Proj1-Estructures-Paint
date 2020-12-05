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
        int[][] matrixBin = ReadMatrix.readMatrixBin("resources/files/cuadro.txt");
        Matrix m = new Matrix(matrixBin);
        System.out.println(m);
        System.out.println(matrixBin.length);
        System.out.println(matrixBin[0].length);

    }
}
