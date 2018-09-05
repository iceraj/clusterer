package com.bugaco.mioritic.impl.data.distancematrix;

import java.beans.PropertyChangeEvent;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DatasetChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

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
public class DistanceMatrixStatisticsViewer extends javax.swing.JPanel {
    com.bugaco.mioritic.model.data.distancematrix.DistanceMatrix distanceMatrix = null ;
    DistanceMatrixChangeListener listener = null ;

    javax.swing.JLabel minDistance = new javax.swing.JLabel() ;
    javax.swing.JLabel maxDistance = new javax.swing.JLabel() ;
    javax.swing.JLabel avgDistance = new javax.swing.JLabel() ;

    XYSeries series = null ;
    Plot plot = null ;
    DefaultTableXYDataset dataset = null ;

    javax.swing.JCheckBox showGraph = null ;
    ChartPanel chartPanel = null ;
    public DistanceMatrixStatisticsViewer() {
        listener = new DistanceMatrixChangeListener();
        this.setLayout( new javax.swing.BoxLayout( this , javax.swing.BoxLayout.PAGE_AXIS ) ) ;
        javax.swing.JPanel dataPanel = new javax.swing.JPanel() ;
        showGraph = new javax.swing.JCheckBox( "Show graph" ) ;

        dataPanel.setLayout( new java.awt.GridLayout( 4 , 2 ) );
        dataPanel.add( new javax.swing.JLabel( "Minimal distance:" ) ) ;
        dataPanel.add( minDistance ) ;
        dataPanel.add( new javax.swing.JLabel( "Maximal distance:" ) ) ;
        dataPanel.add( maxDistance ) ;
        dataPanel.add( new javax.swing.JLabel( "Average distance:" ) ) ;
        dataPanel.add( avgDistance ) ;
        dataPanel.add( showGraph ) ;

        dataset = new DefaultTableXYDataset();
        series = new XYSeries( "series1" , false , false ) ;
        dataset.addSeries( series );
        ValueAxis rangeAxis = new NumberAxis("Total number of sequence pairs");
        ValueAxis valueAxis = new NumberAxis("Nucleotide difference");
        XYBarRenderer renderer = new XYBarRenderer();
        plot = new XYPlot( dataset , valueAxis , rangeAxis , renderer ) ;
        JFreeChart chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, plot, false);
        chart.setBackgroundPaint(java.awt.Color.white);
        chartPanel = new ChartPanel(chart) ;
        this.add( dataPanel ) ;
        this.add( chartPanel ) ;

        showGraph.addChangeListener( new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                chartPanel.setVisible( showGraph.isSelected() ) ;
            }
        });
        showGraph.setSelected( true ) ;
        chartPanel.setVisible( true ) ;
    }

    public void setDistanceMatrix( com.bugaco.mioritic.model.data.distancematrix.DistanceMatrix dm )
    {
        if( distanceMatrix != null )
        {
            distanceMatrix.removePropertyChangeListener( listener ) ;
        }
        distanceMatrix = dm ;
        if( distanceMatrix != null )
        {
            distanceMatrix.addPropertyChangeListener( listener ) ;
        }

    }

    void updateDistanceMatrixView()
    {
        int[] data = distanceMatrix.getArray() ;
        if( data != null && data.length != 0 )
        {
            int min = Integer.MAX_VALUE ;
            int max = Integer.MIN_VALUE ;
            int sum = 0 ;
            for( int i = 0 ; i < data.length ; i++ )
            {
                if( data[ i ] < min ) { min = data[ i ] ; }
                if( data[ i ] > max ) { max = data[ i ] ; }
                sum = sum + data[ i ] ;
            }
            minDistance.setText( String.valueOf( min ) ) ;
            maxDistance.setText( String.valueOf( max ) ) ;
            avgDistance.setText( String.valueOf( Math.floor(sum*100f/data.length)/100 ));

            int[] count = new int[ max - min + 1 ] ;
            for( int i = 0 ; i < data.length ; i++ )
            {
                count[ data[ i ] - min ]++ ;
            }
            dataset.removeSeries( series );
            series.clear();
            for( int i = 0 ; i < count.length ; i++ )
            {
                series.add( i + min , count[ i ] );
            }
            dataset.addSeries( series );
            plot.datasetChanged( new DatasetChangeEvent( plot , dataset ) );
        }
        else
        {
            minDistance.setText( "NaN" );
            maxDistance.setText( "NaN" );
            avgDistance.setText( "NaN" );
        }

    }

    class DistanceMatrixChangeListener implements java.beans.PropertyChangeListener
    {
        public void propertyChange(PropertyChangeEvent evt) {
            if( "data".equals( evt.getPropertyName() ) )
            {
                updateDistanceMatrixView() ;
            }
        }

    }
}
