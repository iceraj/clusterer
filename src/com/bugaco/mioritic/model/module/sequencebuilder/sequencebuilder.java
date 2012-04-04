package com.bugaco.mioritic.model.module.sequencebuilder;

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
public interface SequenceBuilder {
    void setRawSequences( com.bugaco.mioritic.model.data.raw.RawSequences raw ) ;
    void setSequences( com.bugaco.mioritic.model.data.sequences.Sequences seq ) ;
    void setAlgorithms( com.bugaco.ui.models.AlgorithmModel algorithms ) ;
    void setParent( javax.swing.JComponent parent ) ;
}
