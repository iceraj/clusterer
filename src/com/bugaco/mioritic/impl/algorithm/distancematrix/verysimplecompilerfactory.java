package com.bugaco.mioritic.impl.algorithm.distancematrix;

import com.bugaco.mioritic.model.algorithm.distancematrix.DistanceCompiler;

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
public class VerySimpleCompilerFactory implements com.bugaco.mioritic.model.algorithm.distancematrix.DistanceCompilerFactory {
    public VerySimpleCompilerFactory() {
    }

    public DistanceCompiler createCompiler() {
        return new VerySimpleCompiler() ;
    }

    public void disposeCompiler(DistanceCompiler compiler) {
    }
}
