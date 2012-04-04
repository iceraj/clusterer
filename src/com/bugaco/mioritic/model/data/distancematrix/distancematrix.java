package com.bugaco.mioritic.model.data.distancematrix;

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
public interface DistanceMatrix extends com.bugaco.common.PropertyChangeGenerator {
 public void setArray( int[] data );
 public int[] getArray() ;
 public void setSize( int size );
 public int getSize() ;
 public void set( int x , int y , int d );
 public int get( int x , int y );
 public void setNames( String[] str ) ;
 public void setName( int index , String name ) ;
 public String[] getNames() ;

}
