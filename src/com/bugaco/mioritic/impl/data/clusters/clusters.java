package com.bugaco.mioritic.impl.data.clusters;

import java.beans.PropertyChangeListener;

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
public class Clusters implements com.bugaco.mioritic.model.data.clusters.Clusters {
    java.util.HashMap map = null ;
    java.beans.PropertyChangeSupport beanSupport ;

    public Clusters() {
        map = new java.util.HashMap() ;
        beanSupport = new java.beans.PropertyChangeSupport( this )  ;
    }

    public void addPropertyChangeListener( PropertyChangeListener listener )
    {
        beanSupport.addPropertyChangeListener( listener );
    }

    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        beanSupport.removePropertyChangeListener( listener );
    }


    public void put( int distance , java.util.TreeMap sequenceMap )
    {
        map.put( new Integer( distance ) , sequenceMap ) ;
        beanSupport.firePropertyChange( String.valueOf( distance ) , null , null );
        System.err.println( "\n\n Fire property changed for SeqMap ; " + distance + " " + sequenceMap ) ;
    }

    public java.util.TreeMap get( int distance )
    {
        return (java.util.TreeMap) map.get( new Integer( distance ) ) ;
    }

    public int size() {
        return map.size() ;
    }

    public void clear() {
        map.clear();
        beanSupport.firePropertyChange( CLEAR , null , null );
    }
}
