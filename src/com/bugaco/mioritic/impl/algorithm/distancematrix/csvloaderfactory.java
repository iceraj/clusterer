package com.bugaco.mioritic.impl.algorithm.distancematrix;

import com.bugaco.mioritic.model.algorithm.distancematrix.DistanceLoader;
import com.bugaco.mioritic.model.algorithm.distancematrix.DistanceLoaderFactory;

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
public class CSVLoaderFactory implements DistanceLoaderFactory {

    public DistanceLoader createCompiler() {
    return new CSVLoader() ;
}

public void disposeCompiler(DistanceLoader compiler) {
}

}
