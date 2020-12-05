/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.paint.Color;
import models.Cluster;
import models.Coordenada;
import models.Matrix;
import models.Pixel;

/**
 *
 * @author Willy Mateo
 */
public class ReadMatrix {
    
    public static int [][] readMatrixBin(String filePath) {
        int[][] matrixNum = new int[0][0];
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            int rows = Integer.parseInt(br.readLine());
            int columns = Integer.parseInt(br.readLine());
            matrixNum = new int[rows][columns];
            
            String linea;
            int y = 0;
            while ((linea = br.readLine()) != null) {                
                String partes[] = linea.strip().split(",");
                for (int x = 0; x < partes.length; x++) {
                    matrixNum[y][x] = Integer.parseInt(partes[x]);
                }
                y++;
            }
        } catch (IOException ioE) {
            System.out.println("Se han producido problemas al leer el archivo: \n" +filePath +"\n" +ioE.getMessage());
        } finally {
            return matrixNum;
        }
    }
    
    /*
    public static Matrix readMatrix(String FilePath) {
        Matrix matrix = new Matrix();
        ArrayList<Cluster> clusters = new  ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(FilePath))) {
            int rows = Integer.parseInt(br.readLine());
            int columns = Integer.parseInt(br.readLine());
            
            String linea;
            int x = 0;
            while ((linea = br.readLine()) != null) {                
                String partes[] = linea.strip().split(",");
                for (int y = 0; y < partes.length; y++) {
                    Coordenada coord = new Coordenada(x, y);
                    Color color = ManejadorColor.buscarColor(Integer.parseInt(partes[y]));
                    Pixel pixel = new Pixel(color, coord);
                }
                x++;
            }
        } catch (Exception e) {
        }
        return matrix;
    }
    */
    
}
