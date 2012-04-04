package com.bugaco.mioritic.impl.algorithm.rawcompiler;

import com.bugaco.mioritic.model.algorithm.rawcompiler.RawCompiler;

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
public class FastaCompilerFactory implements com.bugaco.mioritic.model.algorithm.rawcompiler.RawCompilerFactory {
    public FastaCompilerFactory() {
    }

    public RawCompiler createRawCompiler() {
        return new FastaCompiler() ;
    }

    public void disposeRawCompiler(RawCompiler compiler) {
    }
}
