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
public class MultiClusterViewer extends javax.swing.JPanel {
    java.util.logging.Logger logger = java.util.logging.Logger.getLogger(this.getClass().toString());
    com.bugaco.mioritic.model.data.clusters.Clusters clusters = null;
    com.bugaco.mioritic.impl.data.multicluster.MultiClusterTableModel model = null;

    javax.swing.JTable table = null ;
    javax.swing.JButton compile = null ;
    javax.swing.JButton showTable = null ;
    javax.swing.JButton showText = null ;
    MultiClusterImage img = null ;

    com.bugaco.mioritic.model.misc.ProgressModel progressModel = null ;
    javax.swing.JProgressBar progress = null ;

    public MultiClusterViewer() {
        this.setLayout(new java.awt.BorderLayout());
        model = new com.bugaco.mioritic.impl.data.multicluster.
                MultiClusterTableModel();
        table = new javax.swing.JTable(model);
        table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        table.setPreferredScrollableViewportSize(new java.awt.Dimension(800, 600));
        table.setShowGrid(false);
        table.setIntercellSpacing(new java.awt.Dimension(0, 0));
        javax.swing.JPanel showPanel = new javax.swing.JPanel() ;
        progressModel = new com.bugaco.mioritic.impl.misc.ProgressModel() ;
        progress = new javax.swing.JProgressBar( progressModel ) ;
        compile = new javax.swing.JButton( "Update" ) ;
        showTable = new javax.swing.JButton( "Show table" ) ;
        showText = new javax.swing.JButton( "Show data" ) ;
        showPanel.add( compile ) ;
        showPanel.add( showText ) ;
        showPanel.add( progress ) ;
        progress.setVisible( false );
        img = new MultiClusterImage( model ) ;
        this.add( new javax.swing.JScrollPane( img ) , java.awt.BorderLayout.CENTER ) ;
        this.add( showPanel , java.awt.BorderLayout.SOUTH ) ;

        showText.addActionListener( new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent ev) {
                StringBuffer sb = new StringBuffer() ;
                if( model != null )
                {
                    for( int i = 0 ; i < model.getRowCount() ; i++ )
                    {
                        for( int j = 0 ; j < model.getColumnCount() ; j++ )
                        {
                            sb.append( model.getValueAt( i , j ) ) ;
                            sb.append( "," ) ;
                        }
                        sb.append( "\n" ) ;
                    }
                }

                javax.swing.JTable table = new javax.swing.JTable( model ) ;
                table.setAutoResizeMode( javax.swing.JTable.AUTO_RESIZE_OFF );
                table.setPreferredScrollableViewportSize( new java.awt.Dimension( 640 , 480 ) );
                com.bugaco.ui.ExportDialog.exportDialog( showTable , "MultiCluster data" , new javax.swing.JScrollPane( table ) , sb.toString() );
            }
        } ) ;

        compile.addActionListener( new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent ev) {
                compile.setVisible( false );

                java.lang.Runnable update = new java.lang.Runnable() {
                	public void run()
                	{
                        progress.setVisible( true ) ;
                        model.compile( progressModel );
                		java.util.Enumeration e = table.getColumnModel().getColumns() ;
		                if( e.hasMoreElements() )
		                {
		                    javax.swing.table.TableColumn c = (javax.swing.table.TableColumn) e.nextElement() ;
		                    c.setPreferredWidth( 128 );
		                }
		                while( e.hasMoreElements() )
		                {
		                    javax.swing.table.TableColumn c = (javax.swing.table.TableColumn) e.nextElement() ;
		                    c.setPreferredWidth( 4 );
		                }
		                
		                progress.setVisible( false );
		                compile.setVisible( true );
		                img.setSize( img.getPreferredSize() );
                	}
                } ;
                (new Thread( update )).start() ;
            }
        });
    }

    public void setClusters( com.bugaco.mioritic.model.data.clusters.Clusters clusters)
    {
        model.setClusters( clusters );
    }

    public void setDistanceMatrix( com.bugaco.mioritic.model.data.distancematrix.DistanceMatrix distMatrix )
    {
        model.setDistanceMatrix( distMatrix );
    }
}
