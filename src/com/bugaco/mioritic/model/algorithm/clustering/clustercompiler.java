package com.bugaco.mioritic.model.algorithm.clustering;

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
public interface ClusterCompiler extends com.bugaco.mioritic.model.misc.Activatable {
    void setDistanceMatrix( com.bugaco.mioritic.model.data.distancematrix.DistanceMatrix raw ) ;
    void setClusters( com.bugaco.mioritic.model.data.clusters.Clusters clusters ) ;
    void setProgress( com.bugaco.mioritic.model.misc.ProgressModel progress ) ;
    java.beans.BeanInfo getBeanInfo() ;
}
