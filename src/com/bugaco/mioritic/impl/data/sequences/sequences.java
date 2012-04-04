package com.bugaco.mioritic.impl.data.sequences;

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
public class Sequences extends javax.swing.DefaultListModel implements com.bugaco.mioritic.model.data.sequences.Sequences {
    public Sequences() {
    }

    public void setArray(Object[] elements) {
        fireIntervalRemoved( this , 0 , getSize() );
        synchronized (this) {
            if (elements != null) {
                setSize(elements.length);
                for (int i = 0; i < elements.length; i++) {
                    set(i, elements[i]);
                }
            } else {
                setSize(0);
            }
        }
System.err.println( "size = " + getSize() ) ;
        fireIntervalAdded( this , 0 , getSize() );
    }

    public int size()
    {
        System.err.println( "getSize = " + getSize() ) ;
        return getSize() ;
    }
}

