package com.bugaco.mioritic.impl.module.multiclasterviewer;

import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYSeries;

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
public class MultiClusterChart extends com.bugaco.ui.charts.Chart2D {

    com.bugaco.mioritic.model.data.distancematrix.DistanceMatrix distMatrix = null ;
    com.bugaco.mioritic.model.data.clusters.Clusters clusters = null ;

    public MultiClusterChart() {
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

    protected void updateGraph() {

        if( clusters != null )
        {
            StringBuffer sb = new StringBuffer() ;
            sb.append( getValueAxisLabel() + "," + getRangeAxisLabel() + "\n" ) ;
            XYSeries series = getXYSeries() ;
            for( int i = 0 ; i < clusters.size() ; i++ )
            {
                series.add( i , clusters.get( i ).size() ) ;
                sb.append( i + "," + clusters.get(i).size() + "\n" ) ;

            }
            replaceSeries( series , sb.toString() ) ;
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
        return "Distance";
    }

    protected XYItemRenderer getRenderer() {
        return getDefaultXYItemRenderer() ;
    }

}
