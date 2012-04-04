package com.bugaco.mioritic.impl.module.clusterbuilder;

import com.bugaco.mioritic.model.module.clusterbuilder.ClusterBuilder;

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
public class ClusterBuilderFactory implements com.bugaco.mioritic.model.module.clusterbuilder.ClusterBuilderFactory {
    public ClusterBuilderFactory() {
    }

    public ClusterBuilder createClusterBuilder() {
        return new com.bugaco.mioritic.impl.module.clusterbuilder.ClusterBuilder() ;
    }

    public void disposeClusterBuilder(ClusterBuilder cb) {
    }
}
