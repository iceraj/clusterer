package com.bugaco.mioritic.impl.data.distancematrix;

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
public class DistanceMatrixFactory implements com.bugaco.mioritic.model.data.distancematrix.DistanceMatrixFactory {
    public DistanceMatrixFactory() {
    }

    public com.bugaco.mioritic.model.data.distancematrix.DistanceMatrix createDistanceMatrix() {
        return new DistanceMatrix() ;
    }

    public void disposeDistanceMatrix(com.bugaco.mioritic.model.data.distancematrix.DistanceMatrix matrix) {
    }
}
