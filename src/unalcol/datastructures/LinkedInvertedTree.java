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
public class LinkedInvertedTree implements InvertedTree {
    
    InvertedTreeNode root;   // root node
    
    public LinkedInvertedTree( )
    {
      root = null;
    }

    // instance methods
    /** @return true iff tree is empty */
    public boolean isEmpty( )
    {
       return root == null;
    }

    /** @return root element if tree is not empty
     * @return null if tree is empty */
    public DataNode root( )
    {
       return ( root == null ) ? null : root.element;
    }

    /** set this to the tree with the given root and subtrees
     * CAUTION: does not clone left and right */
    public void makeTree( DataNode root, InvertedTree father, InvertedTree northChild, InvertedTree eastChild, InvertedTree westChild )
    {
       this.root = new InvertedTreeNode( root, ((LinkedInvertedTree) father).root, ((LinkedInvertedTree) northChild).root,
                                         ((LinkedInvertedTree) eastChild).root, ((LinkedInvertedTree) westChild).root );
    }
}
