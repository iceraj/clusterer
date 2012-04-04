package com.bugaco.mioritic.impl.module.multiclasterviewer;

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
public class MultiClusterImage extends javax.swing.JComponent {
    javax.swing.table.TableModel model = null;

    final static int Xoffset = 20 ;
    final static int Yoffset = 20 ;
    final static int Yendoffset = 0 ;
    public MultiClusterImage( javax.swing.table.TableModel model ) {
        this.model = model ;
    }

    public java.awt.Dimension getPreferredCellSize()
    {
        int xx = 0 ;
        int yy = 0 ;
        if( model != null && model.getRowCount() != 0 && model.getColumnCount() != 0 )
        {
            xx = ( this.getWidth() - Xoffset ) / model.getRowCount();
            yy = ( this.getHeight() - Yoffset - Yendoffset ) / model.getColumnCount();
            if (xx < 2) {
                xx = 2;
            }
            if (xx > 30) {
                xx = 30 ;
            }

            if (yy < 1) {
                yy = 1;
            }
            if (yy > 30) {
                yy = 30 ;
            }
        }
        return new java.awt.Dimension( xx , yy ) ;
    }

    public java.awt.Dimension getPreferredSize()
    {
        if( model != null )
        {
            java.awt.Dimension d = getPreferredCellSize() ;
            return new java.awt.Dimension( d.width * model.getRowCount() + Xoffset , d.height * model.getColumnCount() + Yoffset ) ;
        }
        else
        {
            return new java.awt.Dimension( 0, 0 ) ;
        }
    }

    public void paintComponent( java.awt.Graphics g )
    {
        boolean left = false ;
        boolean right = false ;
        java.awt.Dimension d = getPreferredCellSize() ;
        g.setColor( java.awt.Color.white );
        g.fillRect( 0 , 0 , this.getWidth() , this.getHeight() );
        for( int i = 1 ; i < model.getRowCount() ; i++ )
        {
            if( ( i & 1 ) == 1 )
            {
                g.setColor( java.awt.Color.pink ) ;
                //g.fillRect( Xoffset , d.height * i , d.width * model.getColumnCount() , d.height );
                g.fillRect( Xoffset +  d.width * i , Yoffset , d.width , d.height * ( model.getColumnCount() ) ) ;
            }
        }
        for( int j = model.getColumnCount() - 1 ; j > 0 ; j-- )
        {
            if( ( j & 1 ) == 1 )
            {
                g.setColor( java.awt.Color.pink ) ;
                //g.fillRect( Xoffset + d.width * j , 0 , d.width , d.height );
                //g.fillRect( Xoffset + d.width * j , d.height * ( model.getRowCount() + 1 ), d.width , d.height );
            }
        }
            for( int i = 1 ; i < model.getRowCount() ; i++ )
            {
                for( int j = model.getColumnCount() - 1 ; j > 0 ; j-- )
                {
                left = false ;
                right = false ;
                //System.out.println( model.getValueAt( i - 1 , j ) + " " + model.getValueAt( i , j ) + " " + model.getValueAt( i + 1 , j ) ) ;
                if( i > 0 ) { left = model.getValueAt( i - 1 , j ).equals( model.getValueAt( i , j ) ) ; }
                if( ( i < model.getRowCount() - 1 ) ) { right = model.getValueAt( i + 1 , j ).equals( model.getValueAt( i , j ) ) ; }
                g.setColor( java.awt.Color.red ) ;
                if( left ) {
                    g.fillRect( Xoffset + d.width * i, Yoffset + d.height * j, d.width / 2, d.height * ( model.getColumnCount() - j + 1 ) );
                    g.fillRect( Xoffset + d.width * i, Yoffset + d.height * ( model.getColumnCount() - j ), d.width / 2, d.height * ( j + 1 ) );
//                        g.fillRect( Xoffset , d.height * ( i + 1 ) , d.width * ( j + 1 ) , d.height / 2 );
                }
                if( right ) {
                    g.fillRect( Xoffset + d.width * i + d.width / 2 , Yoffset + d.height * j, 1 + d.width - d.width / 2, d.height * ( model.getColumnCount() - j + 1 ) );
                    g.fillRect( Xoffset + d.width * i + d.width / 2 , Yoffset + d.height * ( model.getColumnCount() - j ), 1 + d.width - d.width / 2, d.height * ( j + 1 ) );
//                    g.fillRect( Xoffset , d.height * ( i + 1 ) + d.height / 2 , d.width * ( j + 1 ) , 1 + d.height - d.height / 2 );
                }
                if( left & right )
                {
                    break ;
                }
            }
        }
        g.setColor( java.awt.Color.black );
        String str2 = "Base pair diff" ;
        String str1 = "Sequences" ;
        g.drawString( str1 , ( d.width * model.getRowCount() - g.getFontMetrics().stringWidth( str1 ) )/ 2 , d.height * model.getColumnCount() + Yoffset + g.getFontMetrics().getHeight() + g.getFontMetrics().getMaxAscent() );
        for( int i = 0 ; i < str2.length() ; i++ )
        {
            g.drawString( str2.substring( i , i + 1 ) , 0 , ( d.height * model.getColumnCount() - g.getFontMetrics().getHeight() * str2.length() ) / 2 + g.getFontMetrics().getHeight() * i );
        }
    }
}
