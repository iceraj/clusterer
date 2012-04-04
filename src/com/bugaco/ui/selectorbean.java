package com.bugaco.ui;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import com.bugaco.ui.models.users.AlgorithmModelUser ;
import com.bugaco.ui.models.AlgorithmModel;

import java.awt.BorderLayout;
import java.awt.event.ItemListener;

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
public class SelectorBean extends Box
        implements AlgorithmModelUser
{
    JLabel jLabel = new JLabel();
    JComboBox jSelector = new JComboBox();
    AlgorithmModel model = null ;

    public SelectorBean() {
        super( javax.swing.BoxLayout.PAGE_AXIS ) ;
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        jLabel.setText("Label:");
        JPanel p = new JPanel() ;
        p.setLayout( new BorderLayout() ) ;
        this.add( p ) ; p.add( jLabel , BorderLayout.WEST ) ;
        jSelector.setToolTipText("");
        //this.add(jLabel);
        this.add(jSelector);
    }

    public AlgorithmModel getModel() {
        return model;
    }

    public void setModel(AlgorithmModel model) {
        this.model = model ;
        jSelector.setModel( model );
        setText( model.getText() ) ;
    }

    public String getText()
    {
        return jLabel.getText() ;
    }

    public void setText( String text )
    {
        jLabel.setText( text ) ;
    }

    public void addItemListener( ItemListener listener )
    {
        jSelector.addItemListener( listener ) ;
    }

    public void removeItemListener( ItemListener listener )
    {
        jSelector.removeItemListener( listener ) ;
    }

}
