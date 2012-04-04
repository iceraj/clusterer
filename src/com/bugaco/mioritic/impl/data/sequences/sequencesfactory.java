package com.bugaco.mioritic.impl.data.sequences;

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
public class SequencesFactory implements com.bugaco.mioritic.model.data.sequences.SequencesFactory {
    public SequencesFactory() {
    }

    public com.bugaco.mioritic.model.data.sequences.Sequences createSequences() {
        return new Sequences() ;
    }

    public void disposeSequences(com.bugaco.mioritic.model.data.sequences.Sequences sequences) {
    }
}
