package com.bugaco.mioritic.impl.module.project;

import java.awt.event.ActionEvent;

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
public class Applet extends javax.swing.JApplet {
    public Applet() {
    }

    public void init()
    {
        this.setBackground( java.awt.Color.lightGray );
        Main project = new Main() ;
        final com.l2fprod.common.swing.JTaskPaneGroup starter = new com.l2fprod.common.swing.JTaskPaneGroup() ;
        starter.setText( "Start analysis" );
        javax.swing.JButton raw = new javax.swing.JButton( "Start analysis from aligned sequences" ) ;
        javax.swing.JButton dm = new javax.swing.JButton( "Start analysis from distance matrix" ) ;
        starter.add( raw ) ;
        starter.add( dm ) ;

        raw.addActionListener( new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Project.main( null );
            }
        });

        dm.addActionListener( new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ProjectDM.main( null );
            }
        });

        com.l2fprod.common.swing.JTaskPane taskPane = new com.l2fprod.common.swing.JTaskPane() ;
        taskPane.add( project.projectFeedback( -1 ) ) ;
        this.getContentPane().add( taskPane ) ;

        if( project.acceptLicense( taskPane ) )
        {
            taskPane.add( starter ) ;
        }
        else
        {
            this.getContentPane().add( new javax.swing.JLabel( "You did not accept license. You are not permitted to use this application." ) ) ;
        }

    }
}
