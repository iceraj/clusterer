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
public interface ClusterBuilderFactory {
    ClusterBuilder createClusterBuilder() ;
    void disposeClusterBuilder( ClusterBuilder cb ) ;
}
