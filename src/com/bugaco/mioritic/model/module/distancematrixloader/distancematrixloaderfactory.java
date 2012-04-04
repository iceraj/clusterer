package com.bugaco.mioritic.model.module.distancematrixloader;

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
public interface DistanceMatrixLoaderFactory {
    DistanceMatrixLoader createDistanceMatrixLoader() ;
    void disposeDistanceMatrixLoader( DistanceMatrixLoader dmb ) ;

}
