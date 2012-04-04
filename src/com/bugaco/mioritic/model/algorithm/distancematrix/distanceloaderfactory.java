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
public interface DistanceLoaderFactory {
    DistanceLoader createCompiler() ;
    void disposeCompiler( DistanceLoader compiler ) ;

}
