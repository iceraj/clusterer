package com.bugaco.mioritic.impl.module.project;

import java.util.Properties;

import com.bugaco.mioritic.model.data.raw.RawSequences;

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
public class Project extends AbstractProject
        implements com.bugaco.mioritic.model.module.project.Project {

    public static void main( String[] str )
    {
        Project project = new Project() ;

        RawSequences rawSequences = ( new com.bugaco.mioritic.impl.data.raw.RawSequencesFactory() ).createRawSequences() ;
        com.bugaco.mioritic.model.data.sequences.Sequences seqs = (new com.bugaco.mioritic.impl.data.sequences.SequencesFactory()).createSequences() ;
        com.bugaco.mioritic.model.data.distancematrix.DistanceMatrix distanceMatrix = new com.bugaco.mioritic.impl.data.distancematrix.DistanceMatrix() ;
        com.bugaco.mioritic.model.data.clusters.Clusters clusters = new com.bugaco.mioritic.impl.data.clusters.Clusters() ;

        com.bugaco.ui.models.AlgorithmModel algModelSeq = new com.bugaco.ui.models.impl.DefaultAlgorithmModel() ;
        algModelSeq.setText( "Select file format:" ) ;
        try
        {
            Properties p = new Properties() ;
            p.load( algModelSeq.getClass().getResourceAsStream( "/conf/SequenceImporter.properties" ) ) ;
            System.err.println( p ) ;
            algModelSeq.setItemsFromProperties( p ) ;
        }
        catch( Exception e )
        {
        	e.printStackTrace() ;
        }
        //algModelSeq.addElement( new com.bugaco.ui.models.impl.DefaultAlgorithmItem( "Fasta" , "com.bugaco.mioritic.impl.algorithm.rawcompiler.FastaCompilerFactory" ) ) ;
        //algModelSeq.addElement( new com.bugaco.ui.models.impl.DefaultAlgorithmItem( "Nexus" , "com.bugaco.mioritic.impl.algorithm.rawcompiler.NexusCompilerFactory" ) ) ;
        

        com.bugaco.ui.models.AlgorithmModel algModelDM = new com.bugaco.ui.models.impl.DefaultAlgorithmModel() ;
        algModelDM.setText( "Select algorithm:" ) ;
        try
        {
            Properties p = new Properties() ;
            p.load( algModelDM.getClass().getResourceAsStream( "/conf/DistanceCompiler.properties" ) ) ; 
            algModelDM.setItemsFromProperties( p ) ;
        }
        catch( Exception e )
        {
        	e.printStackTrace() ;
        }
        //algModelDM.addElement( new com.bugaco.ui.models.impl.DefaultAlgorithmItem( "Simple" , "com.bugaco.mioritic.impl.algorithm.distancematrix.VerySimpleCompilerFactory" ) ) ;

        com.l2fprod.common.swing.JTaskPane taskPane = new com.l2fprod.common.swing.JTaskPane() ;
        taskPane.add( project.projectFeedback( 5000 ) ) ;
        taskPane.add( project.sequenceBuilder( new com.bugaco.mioritic.impl.module.sequencebuilder.SequenceBuilderFactory() , rawSequences , seqs , algModelSeq ) ) ;
        taskPane.add( project.distanceMatrixBuilder( seqs , distanceMatrix , algModelDM ) ) ;
        taskPane.add( project.clusterBuilder( distanceMatrix , clusters )  ) ;
        taskPane.add( project.multiClusterStatistics( seqs , distanceMatrix , clusters ) ) ;

        javax.swing.JFrame frame = project.getMainFrame( taskPane ) ;

        if( !project.acceptLicense( taskPane ) )
        {
            frame.setVisible( false ) ;
            frame = null ;
        }
    }
}
