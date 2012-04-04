package com.bugaco.ui.charts;

import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.DefaultTableXYDataset;
import java.beans.PropertyChangeEvent;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.chart.renderer.xy.DefaultXYItemRenderer;
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
public abstract class Chart2D extends javax.swing.JPanel {
    com.bugaco.common.PropertyChangeGenerator propertyGenerator = null ;

    protected XYPlot plot = null;
    protected XYSeries series = null;
    protected DefaultTableXYDataset dataset = null;
    protected ValueAxis rangeAxis = null;
    protected ValueAxis valueAxis = null;
    javax.swing.JButton text = new javax.swing.JButton( "Show data" ) ;
    protected StringBuffer textSB = new StringBuffer() ;
    protected MyListener listener = null;

    protected class MyListener implements java.beans.PropertyChangeListener {
        public final static String ALL ="ALL" ;
        String property = null ;

        public MyListener( String property ){
            setProperty( property );
        }

        public void setProperty( String property )
        {
            this.property = property ;
        }

        public void propertyChange(PropertyChangeEvent evt) {
            if ( ALL.equals( property ) ||  String.valueOf(property).equals(evt.getPropertyName())) {
                updateGraph();
            }
        }
    }

    public Chart2D() {

        listener = new MyListener( "property" ) ;
        rangeAxis = new NumberAxis( getRangeAxisLabel() ) ;
        valueAxis = new NumberAxis( getValueAxisLabel() ) ;
        dataset = new DefaultTableXYDataset();
        series = getXYSeries() ;
        dataset.addSeries(series);
        plot = new XYPlot(dataset, valueAxis, rangeAxis, getRenderer() );
        JFreeChart chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, plot, false);
        chart.setBackgroundPaint(java.awt.Color.white);
        ChartPanel panel = new ChartPanel(chart);
        javax.swing.JPanel panel2 = new javax.swing.JPanel() ;
        panel2.add( text ) ;
        text.addActionListener( new java.awt.event.ActionListener() {
           public void actionPerformed(java.awt.event.ActionEvent event) {
                 com.bugaco.ui.ExportDialog.exportDialog( text , "Distribution chart data" , new javax.swing.JScrollPane( new javax.swing.JTextArea( textSB.toString() , 20 , 80 ) ) , textSB.toString() );
           } ;
       } ) ;
       this.setLayout( new java.awt.BorderLayout() );
       this.add(panel , java.awt.BorderLayout.CENTER ) ;
       this.add(panel2 , java.awt.BorderLayout.SOUTH ) ;

    }

    public void setPropertyGenerator( com.bugaco.common.PropertyChangeGenerator propertyGenerator )
    {
        if( this.propertyGenerator != null )
        {
            this.propertyGenerator.removePropertyChangeListener( listener );
        }
        this.propertyGenerator = propertyGenerator ;
        if( this.propertyGenerator != null )
        {
            this.propertyGenerator.addPropertyChangeListener( listener );
        }
        updateGraph();
    }

    protected abstract void updateGraph() ;
    protected abstract String getRangeAxisLabel() ;
    protected abstract String getValueAxisLabel() ;
    protected abstract org.jfree.chart.renderer.xy.XYItemRenderer getRenderer() ;
    protected XYSeries getXYSeries()
    {
        return new XYSeries( "series1" , false , false ) ;
    }

    protected void clearGraph()
    {
        dataset.removeAllSeries();
        plot.datasetChanged(new DatasetChangeEvent(plot, dataset));
    }

    protected void replaceSeries( XYSeries series , String text )
    {
        textSB.setLength( 0 ) ;
        textSB.append( text ) ;
        dataset.removeSeries(this.series);
        dataset.addSeries(series);
        this.series = series ;
        plot.datasetChanged(new DatasetChangeEvent(plot, dataset));
    }

    public static XYItemRenderer getDefaultXYItemRenderer() {
    XYItemRenderer ret = new DefaultXYItemRenderer();
    java.awt.Stroke stroke = new java.awt.BasicStroke( 2 , java.awt.BasicStroke.CAP_ROUND , java.awt.BasicStroke.JOIN_BEVEL ) ;
    ret.setStroke( stroke );
    return ret ;
}

}
