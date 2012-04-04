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
public interface AlgorithmModel extends javax.swing.MutableComboBoxModel, java.awt.ItemSelectable {
    Object getSelectedAlgorithm() ;
    String getText() ;
    void setText( String text ) ;
    void setItemsFromProperties( java.util.Properties prop ) ;
}
