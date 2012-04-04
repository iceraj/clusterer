package com.bugaco.mioritic.model.module.clusterbuilder;

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
public interface ClusterBuilder {
    void setDistanceMatrix( com.bugaco.mioritic.model.data.distancematrix.DistanceMatrix distMatrix ) ;
    void setClusters( com.bugaco.mioritic.model.data.clusters.Clusters clusters ) ;
    void setAlgorithms( com.bugaco.ui.models.AlgorithmModel algorithms ) ;
    void setParent( javax.swing.JComponent parent ) ;
}
