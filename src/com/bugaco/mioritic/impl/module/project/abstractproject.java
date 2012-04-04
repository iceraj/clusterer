package com.bugaco.mioritic.impl.module.project;

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
public class AbstractProject {
    String name = null ;
    private static boolean licenseAccepted = false ;

    static final String license ="" 
    +	"Copyright (c) 2005 bugaco.com\n"
    +	"Permission is hereby granted, free of charge, to any person obtaining a copy of this software and\n"
    +	"associated documentation files (the 'Software'), to deal in the Software without restriction, \n"
    +	"including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,\n"
    +	"and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,\n"
    +	"subject to the following conditions:\n"
    +	"      The above copyright notice and this permission notice shall be included in all copies\n"
    +	"      or substantial portions of the Software.\n"
    +	"and\n"
    +	"      Any work that includes results produced by this application by citing [insert article]\n"
    +	"\n"
    +	"THE SOFTWARE IS PROVIDED 'AS IS', WITHOUT WARRANTY OF ANY KIND,\n"
    +	"EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES\n"
    +	"OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND\n"
    +	"NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS\n"
    +	"BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN\n"
    +	"ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN\n"
    +	"CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE."
    +	"\n\n"
    +	"Do you accept this terms and conditions?" ;

    public AbstractProject() {
    }

    public void setName(String name) {
        this.name = name ;
    }

    public String getName() {
        return name ;
    }

    public boolean acceptLicense( javax.swing.JComponent frame )
    {
        if( licenseAccepted )
        {
            return true ;
        }
        int ret = javax.swing.JOptionPane.showConfirmDialog(frame, license, "License", javax.swing.JOptionPane.YES_NO_OPTION);
        if( ret != javax.swing.JOptionPane.YES_OPTION )
        {
            javax.swing.JOptionPane.showMessageDialog( frame , "License not accepted - Terminating application" );
            try
            {
                System.exit(0);
            }
            catch( Exception e )
            {
            }
            return false ;
        }
        licenseAccepted = true ;
        return true ;
    }

    public javax.swing.JComponent projectFeedback( final long timeout )
    {
        com.bugaco.mioritic.model.module.feedback.Feedback feedback = new com.bugaco.mioritic.impl.module.feedback.ProjectMessage() ;
        final com.l2fprod.common.swing.JTaskPaneGroup projectFeedback = new com.l2fprod.common.swing.JTaskPaneGroup() ;
        projectFeedback.setText( "Projects updates and tutorial" );
        feedback.setParent( projectFeedback ) ;
        (new Thread()
        {
            public void run()
            {
                try {
                    Thread.sleep( timeout );
                } catch (InterruptedException ex) {
                }
                if( timeout >= 0 )
                {
                    projectFeedback.setExpanded(false);
                }
            }
        }).start();
        return projectFeedback ;
    }

    public javax.swing.JComponent sequenceBuilder(
            com.bugaco.mioritic.impl.module.sequencebuilder.SequenceBuilderFactory sbf
          , com.bugaco.mioritic.model.data.raw.RawSequences raw
          , com.bugaco.mioritic.model.data.sequences.Sequences seqs
          , com.bugaco.ui.models.AlgorithmModel algModel
            )
    {
        final com.l2fprod.common.swing.JTaskPaneGroup sequenceBuilder = new com.l2fprod.common.swing.JTaskPaneGroup() ;
        sequenceBuilder.setText( "Import aligned sequences" ) ;
        com.bugaco.mioritic.model.module.sequencebuilder.SequenceBuilder sb = sbf.createSequenceBuilder() ;
        sb.setParent( sequenceBuilder );
        sb.setSequences( seqs );
        sb.setRawSequences( raw );
        sb.setAlgorithms( algModel );
        return sequenceBuilder ;
    }

    public javax.swing.JComponent distanceMatrixLoader(
          com.bugaco.mioritic.model.data.distancematrix.DistanceMatrix distanceMatrix
          , com.bugaco.ui.models.AlgorithmModel algModel
            )
    {
        final com.l2fprod.common.swing.JTaskPaneGroup distanceMatrixBuilder = new com.l2fprod.common.swing.JTaskPaneGroup() ;
        distanceMatrixBuilder.setText( "Build distance loader" ) ;
        com.bugaco.mioritic.model.module.distancematrixloader.DistanceMatrixLoader dm = new com.bugaco.mioritic.impl.module.distancematrixloader.DistanceMatrixLoader() ;
        dm.setParent( distanceMatrixBuilder );
        dm.setDistanceMatrix( distanceMatrix );
        dm.setAlgorithms( algModel );
        return distanceMatrixBuilder ;
    }


    public javax.swing.JComponent distanceMatrixBuilder(
           com.bugaco.mioritic.model.data.sequences.Sequences seqs
          , com.bugaco.mioritic.model.data.distancematrix.DistanceMatrix distanceMatrix
          , com.bugaco.ui.models.AlgorithmModel algModel
            )
    {
        final com.l2fprod.common.swing.JTaskPaneGroup distanceMatrixBuilder = new com.l2fprod.common.swing.JTaskPaneGroup() ;
        distanceMatrixBuilder.setText( "Build distance matrix" ) ;
        com.bugaco.mioritic.model.module.distancematrixbuilder.DistanceMatrixBuilder dm = new com.bugaco.mioritic.impl.module.distancematrixbuilder.DistanceMatrixBuilder() ;
        dm.setParent( distanceMatrixBuilder );
        dm.setSequences( seqs ) ;
        dm.setDistanceMatrix( distanceMatrix );
        dm.setAlgorithms( algModel );
        return distanceMatrixBuilder ;
    }

    public javax.swing.JComponent clusterBuilder(
            com.bugaco.mioritic.model.data.distancematrix.DistanceMatrix distanceMatrix
            , com.bugaco.mioritic.model.data.clusters.Clusters clusters
            )
    {
        final com.l2fprod.common.swing.JTaskPaneGroup clusterBuilder = new com.l2fprod.common.swing.JTaskPaneGroup() ;
        clusterBuilder.setText( "Cluster analysis" ) ;
        com.bugaco.mioritic.model.module.clusterbuilder.ClusterBuilder cb = new com.bugaco.mioritic.impl.module.clusterbuilder.ClusterBuilder() ;
        cb.setParent( clusterBuilder ) ;
        cb.setDistanceMatrix( distanceMatrix ) ;
        cb.setClusters( clusters ) ;
        return clusterBuilder ;
    }

    public javax.swing.JComponent multiClusterStatistics(
            com.bugaco.mioritic.model.data.sequences.Sequences seqs
            , com.bugaco.mioritic.model.data.distancematrix.DistanceMatrix distanceMatrix
            , com.bugaco.mioritic.model.data.clusters.Clusters clusters

            )
    {
        final com.l2fprod.common.swing.JTaskPaneGroup multiClusterViewPane = new com.l2fprod.common.swing.JTaskPaneGroup() ;
        multiClusterViewPane.setText( "Cluster summary" );
        com.bugaco.mioritic.impl.module.multiclasterviewer.MultiClusterModule mcv = new com.bugaco.mioritic.impl.module.multiclasterviewer.MultiClusterModule() ;
        mcv.setClusters( clusters );
        mcv.setDistanceMatrix( distanceMatrix );
        com.bugaco.mioritic.impl.data.sequences.SequencesStatisticsViewer
                viewer = new com.bugaco.mioritic.impl.data.sequences.SequencesStatisticsViewer();
        viewer.setSequences( seqs );
        mcv.setSequenceViewer(viewer) ;
        multiClusterViewPane.add( mcv ) ;
        return multiClusterViewPane ;
    }

    public javax.swing.JFrame getMainFrame( javax.swing.JComponent mainPane )
    {
        javax.swing.JFrame frame = new javax.swing.JFrame( "SequenceBuilderProject" ) ;
        frame.setIconImage( java.awt.Toolkit.getDefaultToolkit().getImage( this.getClass().getClassLoader().getResource( "com/bugaco/mioritic/resources/images/mioritic.gif" ) ) ) ;
        final javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane( mainPane ) ;
        scrollPane.getVerticalScrollBar().setBlockIncrement( 50 );
        frame.getContentPane().add( scrollPane , java.awt.BorderLayout.CENTER ) ;
        if(! licenseAccepted )
        {
            frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        }
        else
        {
            frame.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
        }
        frame.setVisible( true ) ;
        frame.pack() ;
        if( frame.getHeight() > 600 )
        {
            frame.setSize( frame.getWidth() , 600 );
        }
        return frame ;
    }

}
