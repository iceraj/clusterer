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
public class MultiClusterModule extends javax.swing.JTabbedPane {
    java.util.logging.Logger logger = java.util.logging.Logger.getLogger(this.
        getClass().toString());

    MultiClusterViewer viewer = null ;
    MultiClusterChart chart = null ;
    MultiClusterSimilarityChart simChart = null ;

    public MultiClusterModule() {
        viewer = new MultiClusterViewer() ;
        chart = new MultiClusterChart();
        simChart = new MultiClusterSimilarityChart();
        addTab( "Similarity chart" , simChart ) ;
        addTab( "Distribution chart" , chart ) ;
        addTab( "Cluster image" , viewer ) ;
    }

    public void setClusters( com.bugaco.mioritic.model.data.clusters.Clusters clusters)
    {
        viewer.setClusters( clusters );
        chart.setClusters( clusters );
        simChart.setClusters( clusters );
    }

    public void setDistanceMatrix( com.bugaco.mioritic.model.data.distancematrix.DistanceMatrix distMatrix )
    {
        viewer.setDistanceMatrix( distMatrix );
        chart.setDistanceMatrix( distMatrix ) ;
        simChart.setDistanceMatrix( distMatrix );
    }

    public void setSequenceViewer( com.bugaco.mioritic.impl.data.sequences.SequencesStatisticsViewer viewer )
    {
        simChart.setSequenceViewer( viewer );
    }

}
