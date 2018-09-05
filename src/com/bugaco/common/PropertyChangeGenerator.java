package com.bugaco.common;

import java.beans.PropertyChangeListener;

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
public interface PropertyChangeGenerator {
    public void addPropertyChangeListener( PropertyChangeListener listener ) ;
    public void removePropertyChangeListener(PropertyChangeListener listener) ;
}
