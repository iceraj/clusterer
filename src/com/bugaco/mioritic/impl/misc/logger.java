package com.bugaco.mioritic.impl.misc;

import java.util.logging.LogRecord;
import java.util.logging.Level;

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
public class Logger extends java.util.logging.Logger implements com.bugaco.mioritic.model.misc.Logger {
    java.util.logging.Logger logger = null ;
    public Logger( String className ) {
        super( className , null ) ;
        logger = java.util.logging.Logger.getLogger( className ) ;
        logger.log( Level.INFO , className );
    }

    public void log( LogRecord record )
    {
        logger.log( record );
    }

    public boolean isLoggable( Level level )
    {
        return logger.isLoggable( level ) ;
    }
}
