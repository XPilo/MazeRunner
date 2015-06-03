/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.datastructures;

/**
 *
 * @author alej0
 */

import java.lang.reflect.*;

public interface InvertedTree {
    boolean isEmpty( );
    DataNode root( );
    void makeTree( DataNode root, InvertedTree father, InvertedTree northChild, InvertedTree eastChild, InvertedTree westChild );
}
