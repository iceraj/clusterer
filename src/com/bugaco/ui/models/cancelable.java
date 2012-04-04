package com.bugaco.ui.models;

/**
 * <p>Title: Mioritic</p>
 *
 * <p>Description: Clusterer 3</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: bugaco</p>
 *
 * @author Ivica Ceraj
 * @version 1.0
 */
public interface Cancelable {
    boolean isCancelRequested() ;
    void setCancelRequested( boolean cancel ) ;
    void addCancelChangeListener(java.beans.PropertyChangeListener x) ;
    void removeCancelChangeListener(java.beans.PropertyChangeListener x) ;

    boolean isCanceled() ;
    void setCanceled( boolean canceled ) ;
    void addCanceledChangeListener(java.beans.PropertyChangeListener x) ;
    void removeCanceledChangeListener(java.beans.PropertyChangeListener x) ;
}
