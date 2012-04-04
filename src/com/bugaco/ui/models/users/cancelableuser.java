package com.bugaco.ui.models.users;

import com.bugaco.ui.models.Cancelable;

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
public interface CancelableUser {
    Cancelable getCancelable() ;
    void setCancelable( Cancelable cancelable ) ;
}
