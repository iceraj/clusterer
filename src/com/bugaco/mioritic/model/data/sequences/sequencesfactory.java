package com.bugaco.mioritic.model.data.sequences;

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
public interface SequencesFactory {
    Sequences createSequences() ;
    void disposeSequences( Sequences sequences ) ;
}
