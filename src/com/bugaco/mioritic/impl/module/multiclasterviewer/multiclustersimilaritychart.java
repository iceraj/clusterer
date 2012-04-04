package com.bugaco.mioritic.impl.module.multiclasterviewer;

import org.jfree.data.xy.XYSeries;
import org.jfree.chart.renderer.xy.XYItemRenderer;

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
public class MultiClusterSimilarityChart extends com.bugaco.ui.charts.Chart2D {

    com.bugaco.mioritic.model.data.distancematrix.DistanceMatrix distMatrix = null ;
    com.bugaco.mioritic.model.data.clusters.Clusters clusters = null ;
    com.bugaco.mioritic.impl.data.sequences.SequencesStatisticsViewer seqStat = null ;

    public MultiClusterSimilarityChart() {
        super() ;
        listener.setProperty( com.bugaco.ui.charts.Chart2D.MyListener.ALL );
    }

    public void setClusters( com.bugaco.mioritic.model.data.clusters.Clusters clusters)
    {
        this.clusters = clusters ;
        setPropertyGenerator( clusters );
    }
    public void setDistanceMatrix( com.bugaco.mioritic.model.data.distancematrix.DistanceMatrix distMatrix )
    {
        this.distMatrix = distMatrix ;
    }

    public void setSequenceViewer( com.bugaco.mioritic.impl.data.sequences.SequencesStatisticsViewer viewer )
    {
        this.seqStat = viewer ;
    }

    protected void updateGraph() {

        if( clusters != null )
        {
            try
            {
                StringBuffer sb = new StringBuffer() ;
                sb.append( getValueAxisLabel() + "," + getRangeAxisLabel() + "\n" ) ;
                XYSeries series = getXYSeries();
                for (int i = 0; i < clusters.size(); i++) {
                    series.add(1 - (((double)i) / seqStat.getAvgLength()),
                               clusters.get(i).size());
                    sb.append( ( 1 - (((double)i) / seqStat.getAvgLength()) ) + "," + clusters.get(i).size() + "\n" ) ;
                }
                replaceSeries(series, sb.toString() );
                valueAxis.setRangeWithMargins( 0 , 1 );
            }
            catch( Exception e )
            {
                clearGraph() ;
            }
        }
        else
        {
            clearGraph();
        }
    }

    protected String getRangeAxisLabel() {
        return "Total number of clusters";
    }

    protected String getValueAxisLabel() {
        return "Similarity";
    }

    protected XYItemRenderer getRenderer() {
        return getDefaultXYItemRenderer() ;
    }

}
