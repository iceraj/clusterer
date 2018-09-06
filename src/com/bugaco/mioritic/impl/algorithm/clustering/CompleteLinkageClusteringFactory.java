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
public class CompleteLinkageClusteringFactory implements com.bugaco.mioritic.model.algorithm.clustering.ClusterCompilerFactory {
    public CompleteLinkageClusteringFactory() {
    }

    public ClusterCompiler createCompiler() {
        return new CompleteLinkageClustering();
    }

    public void disposeCompiler(ClusterCompiler compiler) {
    }
}
