package com.bugaco.mioritic.impl.module.sequencebuilder;

import com.bugaco.mioritic.model.module.sequencebuilder.SequenceBuilder;

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
public class SequenceBuilderFactory implements com.bugaco.mioritic.model.module.sequencebuilder.SequenceBuilderFactory {
    public SequenceBuilderFactory() {
    }

    public SequenceBuilder createSequenceBuilder() {
        return new com.bugaco.mioritic.impl.module.sequencebuilder.SequenceBuilder();
    }

    public void disposeSequenceBuilder( SequenceBuilder module ) {
    }
}
