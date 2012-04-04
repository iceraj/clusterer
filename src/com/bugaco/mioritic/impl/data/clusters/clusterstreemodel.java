package com.bugaco.mioritic.impl.data.clusters;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;
import java.beans.PropertyChangeEvent;
import javax.swing.text.*;

/**
 * <p>Title: Mioritic</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: bugaco</p>
 *
 * @author Ivica Ceraj
 * @version 1.0
 */
public class ClustersTreeModel implements javax.swing.tree.TreeModel {
    com.bugaco.mioritic.model.data.clusters.Clusters clusters = null ;
    java.util.HashSet listeners = new java.util.HashSet() ;
    int distance = 20 ;
    java.beans.PropertyChangeListener listener = null ;
    ClustersTreeModel treeModel = null ;
    String rootNode = "Sequences" ;
    javax.swing.text.PlainDocument document = null ;

    public ClustersTreeModel() {
        document = new javax.swing.text.PlainDocument() ;
        treeModel = this ;
        listener = new java.beans.PropertyChangeListener()
        {
            public void propertyChange(PropertyChangeEvent evt) {
                if( evt != null
                    && !String.valueOf( distance ).equalsIgnoreCase( evt.getPropertyName() )
                    && !"CLEAR".equalsIgnoreCase( evt.getPropertyName() )
                  )
                {
                    return;
                }
                java.util.Iterator iter =  listeners.iterator() ;
                javax.swing.event.TreeModelEvent event = new javax.swing.event.TreeModelEvent( treeModel , new TreePath( getRoot() )) ;
                while( iter.hasNext() )
                {
                    try
                    {
                        ((TreeModelListener) iter.next()).treeStructureChanged(
                                event);
                        updateDocument( distance );
                    }
                    catch( Exception e )
                    {
                    }
                }
            }
        } ;
    }

    public void setClusters( com.bugaco.mioritic.model.data.clusters.Clusters clusters )
    {
        if( clusters != null )
        {
            clusters.removePropertyChangeListener( listener );
        }
        this.clusters = clusters ;
        if( clusters != null )
        {
            clusters.addPropertyChangeListener( listener );
        }

    }

    public void setDistance( int distance )
    {
        this.distance = distance ;
        listener.propertyChange( null );
        updateDocument( distance );
    }

    public Object getRoot() {
        return rootNode ;
    }

    java.util.TreeMap myMap()
    {
        if( clusters == null )
        {
            return new java.util.TreeMap() ;
        }
        if( null ==  clusters.get( distance ) )
        {
            return new java.util.TreeMap() ;
        }
        return clusters.get( distance ) ;
    }

    public int getChildCount(Object parent) {
        if( rootNode.equals( parent ) )
        {
            return ( myMap() != null ) ? myMap().size() : 0 ;
        }
        else
        {
            if( myMap() != null )
            {
                Object child = myMap().get( parent ) ;
                if( child != null )
                {
                    if( child instanceof java.util.TreeSet )
                    {
                        return ( (java.util.Set)child ).size() ;
                    }
                    else
                    {
                        return 0 ;
                    }
                }
            }
        }
        return 0 ;
    }

    public boolean isLeaf(Object node) {
      if( rootNode.equals( node ) )
      {
          return false ;
      }
      if( myMap().get( node ) != null && myMap().get( node ) instanceof java.util.TreeSet)
      {
          return false ;
      }
      return true ;
    }


    public Object getChild(Object parent, int index) {
        if( rootNode.equals( parent ) )
        {
            int i = 0 ;
            java.util.Iterator iter = myMap().keySet().iterator() ;
            while( i < index && iter.hasNext() )
            {
                iter.next() ;
                i++ ;
            }
            if( iter.hasNext() )
            {
                return iter.next() ;
            }
            return null ;
        }
        else if( myMap() != null )
        {
            java.util.TreeSet set = (java.util.TreeSet)myMap().get( parent ) ;
            return set.toArray()[ index ] ;
        }
        return null ;
    }

    public int getIndexOfChild(Object parent, Object child) {
        if( rootNode.equals( parent ) )
        {
            int i = 0 ;
            java.util.Iterator iter = myMap().keySet().iterator() ;
            while( iter.hasNext() )
            {
                if( iter.next().equals( child ) )
                {
                    return i ;
                }
                i++ ;
            }
            return -1 ;
        }
        else
        {   java.util.TreeSet set = (java.util.TreeSet)myMap().get( parent ) ;
            Object[] children = set.toArray() ;
            for( int i = 0 ; i < children.length ; i++ )
            {
                if( children[ i ] == child )
                {
                    return i ;
                }
            }
        }
        return 0 ;
    }

    public void valueForPathChanged(TreePath path, Object newValue) {
    }

    public void addTreeModelListener(TreeModelListener l) {
        listeners.add( l ) ;
    }

    public void removeTreeModelListener(TreeModelListener l) {
        listeners.remove( l ) ;
    }

    public void updateDocument( int distance )
    {
        java.util.TreeMap map = clusters.get( distance ) ;
        StringBuffer sb = new StringBuffer() ;
        if( map != null )
        {
            java.util.Iterator iter = map.keySet().iterator();
            while (iter.hasNext()) {
                Object key = iter.next();
                Object value = map.get(key);
                if (value instanceof java.util.TreeSet) {
                    java.util.Iterator iter2 = ((java.util.TreeSet) value).
                                               iterator();
                    while (iter2.hasNext()) {
                        sb.append("cluster," + iter2.next() + "," + key +
                                  "\n");
                    }
                } else {
                    sb.append("singleton," + key + "," + key + "\n");
                }
            }
        }
        try {
            document.remove(0, document.getLength());
            document.insertString( 0 , sb.toString() , null );
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }

    }

    public javax.swing.text.Document getDocument()
    {
        return document ;
    }

}
