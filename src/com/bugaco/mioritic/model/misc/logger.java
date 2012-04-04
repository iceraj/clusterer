package com.bugaco.mioritic.model.misc;

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
import java.util.logging.Level;

public interface Logger {
    void log( Level level , String text ) ;
    void log( Level level , String msg, Throwable thrown) ;
    void setLevel( Level level ) ;
    Level getLevel() ;
}
