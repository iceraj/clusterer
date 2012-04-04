package com.bugaco.mioritic.model.module.distancematrixbuilder;

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
public interface DistanceMatrixBuilder {
    void setSequences( com.bugaco.mioritic.model.data.sequences.Sequences seq ) ;
    void setDistanceMatrix( com.bugaco.mioritic.model.data.distancematrix.DistanceMatrix distMatrix ) ;
    void setAlgorithms( com.bugaco.ui.models.AlgorithmModel algorithms ) ;
    void setParent( javax.swing.JComponent parent ) ;

}
