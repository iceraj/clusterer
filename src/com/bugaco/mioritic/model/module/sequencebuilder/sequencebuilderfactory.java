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
public interface SequenceBuilderFactory {
    static String className = "com.bugaco.mioritic.model.module.sequencebuilder.SequenceBuilderFactory" ;
    SequenceBuilder createSequenceBuilder() ;
    void disposeSequenceBuilder( SequenceBuilder module ) ;
}
