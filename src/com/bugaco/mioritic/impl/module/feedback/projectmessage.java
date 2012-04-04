package com.bugaco.mioritic.impl.module.feedback;

import javax.swing.JComponent;
import java.net.*;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

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
public class ProjectMessage implements com.bugaco.mioritic.model.module.feedback.Feedback {
    JComponent parent = null ;
    javax.swing.JTabbedPane ui = null ;

    public ProjectMessage() {
        ui = new javax.swing.JTabbedPane() ;
        ScrollableText newsflash = new ScrollableText();
        ui.addTab( "Updates" , new javax.swing.JScrollPane( newsflash ) );
//        ui.addTab( "Tutorial" , new javax.swing.JLabel( "Placeholder: Help, Bugs, Features, Release notes" ) );
        ui.addTab( "Report a bug" , new javax.swing.JLabel( "To report a bug email clusterer@mit.edu" ) );
        ui.addTab( "Request a feature" , new javax.swing.JLabel( "To request a feature email clusterer@mit.edu" ) );
        ui.addTab( "Manual" , new javax.swing.JLabel( "Download manual from website's homepage" ) ) ;
        try {
            new Newsflash(newsflash,
                          new java.net.URL("newsflash.html"));
        } catch (MalformedURLException ex) {
        }
    }

    class ScrollableText extends javax.swing.JLabel implements javax.swing.Scrollable
    {

        public java.awt.Dimension getPreferredScrollableViewportSize()
        {

            return new java.awt.Dimension( 150 , 100 ) ;
        }

        public boolean getScrollableTracksViewportHeight() {
            return false;
        }

        public boolean getScrollableTracksViewportWidth() {
            return false;
        }

        public int getScrollableBlockIncrement(Rectangle visibleRect,
                                               int orientation, int direction) {
            return 25;
        }

        public int getScrollableUnitIncrement(Rectangle visibleRect,
                                              int orientation, int direction) {
            return 25;
        }

    }


    class Newsflash implements Runnable
    {
        java.net.URL source = null ;
        javax.swing.JLabel textPane = null ;

        boolean going = false ;
        public void go()
        {
            going = true ;
            Thread t = new Thread( this ) ;
            t.start();
        }
        public Newsflash( javax.swing.JLabel textPane , java.net.URL source )
        {
            this.source = source ;
            this.textPane = textPane ;
            textPane.setText( source.toString() );
            textPane.addMouseListener( new java.awt.event.MouseListener() {
                public void mouseClicked(MouseEvent e) {
                    if( !going )
                    {
                        go();
                    }
                }

                public void mouseEntered(MouseEvent e) {
                }

                public void mouseExited(MouseEvent e) {
                }

                public void mousePressed(MouseEvent e) {
                }

                public void mouseReleased(MouseEvent e) {
                }

            });
            go() ;
        }
        public void run() {
            StringBuffer sb = new StringBuffer() ;
            try {
            java.net.URLConnection c = source.openConnection() ;
            java.io.InputStream is = new java.io.BufferedInputStream( c.getInputStream() ) ;
            int x ;
            while( ( x = is.read()) != -1 )
            {
                sb.append( (char) x ) ;
            }
            } catch( Exception e )
            {
                e.printStackTrace();
            }
            textPane.setText( sb.toString() );
            textPane.repaint( 1500 );
            going = false ;
        }

    }

    public void setParent(JComponent parent) {
    if (parent != null) {
        System.err.println("Parent=" + parent + ";UI=" + ui);
        parent.remove(ui);
    }
    this.parent = parent;
    if (parent != null) {
        System.err.println("Parent=" + parent + ";UI=" + ui);
        parent.add(ui);
    }
}

}
