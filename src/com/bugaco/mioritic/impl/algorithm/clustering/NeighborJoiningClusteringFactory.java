package com.bugaco.mioritic.impl.algorithm.clustering;

import com.bugaco.mioritic.model.algorithm.clustering.ClusterCompiler;

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
public class NeighborJoiningClusteringFactory implements com.bugaco.mioritic.model.algorithm.clustering.ClusterCompilerFactory {
    public NeighborJoiningClusteringFactory() {
    }

    public ClusterCompiler createCompiler() {
        return new NeighborJoiningClustering();
    }

    public void disposeCompiler(ClusterCompiler compiler) {
    }
}
