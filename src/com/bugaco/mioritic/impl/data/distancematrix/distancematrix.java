package com.bugaco.mioritic.impl.data.distancematrix;

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
public class DistanceMatrix implements com.bugaco.mioritic.model.data.distancematrix.DistanceMatrix {
    java.beans.PropertyChangeSupport beanSupport ;

    int[] data = null ;
    String[] names = null ;
    int size = 0 ;

    public DistanceMatrix() {
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

    public void setArray( int[] data )
    {
        int[] old = this.data ;
        this.data = data ;
        beanSupport.firePropertyChange("data", old , data );
    }

    public int[] getArray()
    {
        return this.data ;
    }
    public void setSize( int size )
    {
        data = new int[ size*(size+1)/2 ] ;
        names = new String[ size ] ;
        this.size = size ;
    }

    public int getSize()
    {
      if( data != null )
      {
          return (int)(Math.sqrt( 1 + 8 * data.length ) - 1 )/2 ;
      }
      else
      {
          return 0 ;
      }
    }

    public void set( int x , int y , int d )
    {
    	//int old = data[ pos( x , y ) ] ;
        data[ pos( x , y ) ] = d ;
        //beanSupport.firePropertyChange("data", old , d );
    }

    public int get( int x , int y )
    {
        if( x == y ) { return 0 ; }
        return data[ pos( x , y ) ] ;
    }

    public void setNames( String[] str )
    {
        this.names = str ;
    }

    public void setName( int index , String name )
    {
        this.names[ index ] = name ;
    }

    public String[] getNames()
    {
        return names ;
    }

    int pos( int x , int y )
    {
        int a = x > y ? x : y ;
        int b = x < y ? x : y ;
        return a*(a-1)/2+b ;
    }
}
