package com.bugaco.mioritic.model.algorithm.distancematrix;

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
public interface DistanceLoader extends com.bugaco.mioritic.model.misc.Activatable {
    void setRawDistanceMatrix( com.bugaco.mioritic.model.data.raw.RawDataMatrix seq ) ;
    void setDistanceMatrix( com.bugaco.mioritic.model.data.distancematrix.DistanceMatrix raw ) ;
    void setProgress( com.bugaco.mioritic.model.misc.ProgressModel progress ) ;
    java.beans.BeanInfo getBeanInfo() ;
}
