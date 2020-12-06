/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.Comparator;
import models.Cluster;

/**
 *
 * @author Willy Mateo
 */
public class ClusterComparator implements Comparator<Cluster>{

    @Override
    public int compare(Cluster cl1, Cluster cl2) {
        int cSize = Integer.compare(cl1.getSize(), cl2.getSize());
        if (cSize == 0) {
            int cIzq = Integer.compare(cl1.getCoordLeft().getX(), cl2.getCoordLeft().getX());
            return cIzq;
        } else {
            return cSize*-1;
        }
    }
    
}
