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
public class Sequence implements com.bugaco.mioritic.model.data.sequences.Sequence {
    String name ;
    byte[] data ;

    public Sequence( String name , byte[] data ) {
        this.name = name ;
        this.data = data ;
    }

    public String getName() {
        return name;
    }

    public byte[] getData() {
        return data;
    }

    public String toString() {
        return getName() + " " + ( data != null ? data.length : 0 );
    }

}
