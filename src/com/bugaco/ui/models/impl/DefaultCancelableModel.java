package com.bugaco.ui.models.impl;

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
public class DefaultCancelableModel
implements com.bugaco.ui.models.Cancelable, java.io.Serializable
{
    private boolean cancelRequested = false ;
    private boolean canceled = false ;

    final static String CANCEL = "cancel" ;
    final static String CANCELED = "canceled" ;

    java.beans.PropertyChangeSupport propertyChangeSupport ;

    private void init()
    {
        propertyChangeSupport = new java.beans.PropertyChangeSupport( this ) ;
    }
    public DefaultCancelableModel() {
        init() ;
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean isCancelRequested() {
        return cancelRequested ;
    }

    public void setCancelRequested(boolean cancel) {
        boolean current = isCancelRequested() ;
        if( current != cancel )
        {
            this.cancelRequested = cancel ;
            fireCancelEvent( current , cancel ) ;
        }
    }

    protected void fireCancelEvent( boolean old_val , boolean new_val )
    {
        propertyChangeSupport.firePropertyChange( CANCEL , old_val , new_val );
    }

    public void addCancelChangeListener(java.beans.PropertyChangeListener x) {
        propertyChangeSupport.addPropertyChangeListener( CANCEL , x );
    }

    public void removeCancelChangeListener(java.beans.PropertyChangeListener x) {
        propertyChangeSupport.removePropertyChangeListener( CANCEL , x );
    }

    public boolean isCanceled() {
        return canceled ;
    }

    public void setCanceled(boolean canceled) {
        boolean current = isCanceled() ;
        if( current != canceled )
        {
            this.canceled = canceled ;
            fireCanceledEvent( current , canceled ) ;
        }
    }

    public void addCanceledChangeListener(java.beans.PropertyChangeListener x) {
        propertyChangeSupport.addPropertyChangeListener( CANCELED , x );
    }

    public void removeCanceledChangeListener(java.beans.PropertyChangeListener x) {
        propertyChangeSupport.removePropertyChangeListener( CANCELED , x );
    }

    protected void fireCanceledEvent( boolean old_val , boolean new_val )
    {
        propertyChangeSupport.firePropertyChange( CANCELED , old_val , new_val );
    }

    private void jbInit() throws Exception {
    }
}
