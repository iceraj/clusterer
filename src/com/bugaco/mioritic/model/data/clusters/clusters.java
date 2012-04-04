package com.bugaco.mioritic.model.data.clusters;

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
public interface Clusters extends com.bugaco.common.PropertyChangeGenerator {
     static String CLEAR = "CLEAR" ;

     public void put( int distance , java.util.TreeMap sequenceMap ) ;
     public java.util.TreeMap get( int distance ) ;
     public int size() ;
     public void clear() ;

}
