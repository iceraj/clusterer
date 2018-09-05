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
public class DefaultAlgorithmItem
implements com.bugaco.ui.models.AlgorithmItem
{
    private String name ;
    private String algorithm ;
    public DefaultAlgorithmItem() {
    }

    public DefaultAlgorithmItem( String name , String algorithm )
    {
        setName( name ) ;
        setAlgorithmClass( algorithm );
    }

    public String getName() {
        return name ;
    }

    public void setName(String name) {
        this.name = name ;
    }

    public String getAlgorithmClass() {
        return algorithm;
    }

    public void setAlgorithmClass(String algorithm) {
        this.algorithm = algorithm ;
    }

    public String toString()
    {
        return getName() ;
    }
}
