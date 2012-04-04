package com.bugaco.ui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JButton;
import com.bugaco.ui.models.Cancelable;
import com.bugaco.ui.models.ProgressModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

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
public class CancellableProgressBean extends JPanel
implements com.bugaco.ui.models.users.CancelableUser, com.bugaco.ui.models.users.ProgressModelUser
{
    Cancelable cancelable = null ;
    ProgressModel progressModel = null ;

    BorderLayout borderLayout1 = new BorderLayout();
    JProgressBar jProgressBar = new JProgressBar();
    JButton jCancel = new JButton();

    public CancellableProgressBean() {
        try {
            jbInit();
            jCancel.addActionListener( new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Cancelable c = getCancelable() ;
                    if( c != null )
                    {
                        c.setCancelRequested( true ) ;
                    }
                }
            });
            setCancelable( null );
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        setLayout(borderLayout1);
        jCancel.setText("Cancel");
        this.add(jCancel, java.awt.BorderLayout.EAST);
        this.add(jProgressBar, java.awt.BorderLayout.CENTER);
    }

    public Cancelable getCancelable() {
        return cancelable;
    }

    public void setCancelable(Cancelable cancelable) {
        this.cancelable = cancelable ;
        if( cancelable != null )
        {
            cancelable.addCancelChangeListener( new PropertyChangeListener()
            {
                public void propertyChange(PropertyChangeEvent evt) {
                    Cancelable c = (Cancelable)evt.getSource() ;
                    jCancel.setEnabled( !c.isCancelRequested() ) ;
                }
            });
            cancelable.addCanceledChangeListener( new PropertyChangeListener()
            {
                public void propertyChange(PropertyChangeEvent evt) {
                    Cancelable c = (Cancelable)evt.getSource() ;
                    c.setCancelRequested( false ) ;
                }
            });

        }
        else
        {
            jCancel.setVisible( false );
        }
    }

    public ProgressModel getProgressModel() {
        return progressModel;
    }

    public void setProgressModel( ProgressModel progressModel ) {
        this.progressModel = progressModel ;
        jProgressBar.setModel( progressModel ) ;
    }
}
