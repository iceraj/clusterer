package com.bugaco.mioritic.impl.data.raw;

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
public class RawSequencesFactory implements com.bugaco.mioritic.model.data.raw.RawSequencesFactory {
    public RawSequencesFactory() {
    }

    public com.bugaco.mioritic.model.data.raw.RawSequences createRawSequences() {
        return new RawSequences() ;
    }

    public void disposeRawSequences(com.bugaco.mioritic.model.data.raw.RawSequences raw) {
    }
}
