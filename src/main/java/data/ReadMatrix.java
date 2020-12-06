/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
}
