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
public class InvertedTreeNode {
   
   DataNode element;
   InvertedTreeNode father;
   InvertedTreeNode northNode;
   InvertedTreeNode eastNode;
   InvertedTreeNode westNode;

   public InvertedTreeNode( ) { }

   public InvertedTreeNode( DataNode theElement )
   {
      element = theElement;
   }

   public InvertedTreeNode( DataNode theElement, InvertedTreeNode theFather, InvertedTreeNode theNorthNode,
                            InvertedTreeNode theEastNode, InvertedTreeNode theWestNode )
   {
      element = theElement;
      father = theFather;
      northNode = theNorthNode;
      eastNode = theEastNode;
      westNode = theWestNode;
   }

   // accessor methods
   public InvertedTreeNode getFather( )
   {
      return father;
   }
   
   public InvertedTreeNode getNorthChild( )
   {
      return northNode;
   }
   
   public InvertedTreeNode getEastChild( )
   {
      return eastNode;
   }
   
   public InvertedTreeNode getWestChild( )
   {
      return westNode;
   }
   
   public DataNode getElement( )
   {
      return element;
   }

   // mutator methods
   public void setFather( InvertedTreeNode theFather )
   {
      father = theFather;
   }
   
   public void setNorthChild( InvertedTreeNode theNorthNode )
   {
      northNode = theNorthNode;
   }
   
   public void setEastChild( InvertedTreeNode theEastNode )
   {
      eastNode = theEastNode;
   }
   
   public void setWestChild( InvertedTreeNode theWestNode )
   {
      westNode = theWestNode;
   }

   public void setElement( DataNode theElement )
   {
      element = theElement;
   }

   @Override
   public String toString( )
   {
      return element.toString();
   }
}
