package com.bugaco.mioritic.model.algorithm.rawcompiler;

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
public interface RawCompiler extends com.bugaco.mioritic.model.misc.Activatable {
    void setRawSequences( com.bugaco.mioritic.model.data.raw.RawSequences raw ) ;
    void setSequences( com.bugaco.mioritic.model.data.sequences.Sequences seq ) ;
    void setProgress( com.bugaco.mioritic.model.misc.ProgressModel progress ) ;
    java.beans.BeanInfo getBeanInfo() ;
}
