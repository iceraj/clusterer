package com.bugaco.mioritic.impl.module.clusterviewer;

import org.jfree.chart.axis.NumberAxis;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.JFreeChart;
import java.beans.PropertyChangeEvent;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.DefaultTableXYDataset;
import mylib.ProgressBar;
import statlib.Krebs;
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
class RarefactionChart extends javax.swing.JPanel {
       com.bugaco.mioritic.model.data.clusters.Clusters clusters = null;
       int distance = 0;
       MyListener listener = null;

       XYPlot plot = null;
       XYSeries series = null;
       DefaultTableXYDataset dataset = null;
       //org.jfree.data.contour.DefaultContourDataset dataset = null ;
       ValueAxis rangeAxis = null;
       ValueAxis valueAxis = null;
       javax.swing.JLabel status = new javax.swing.JLabel() ;
       javax.swing.JButton calc = new javax.swing.JButton( "Calculate" ) ;
       javax.swing.JButton text = new javax.swing.JButton( "Show data" ) ;
       StringBuffer textSB = new StringBuffer() ;
       public RarefactionChart() {
           listener = new MyListener();
           rangeAxis = new NumberAxis("Number of clusters");
           valueAxis = new NumberAxis("Number of sequences in subsample");

           XYItemRenderer renderer = com.bugaco.ui.charts.Chart2D.getDefaultXYItemRenderer() ;
           dataset = new DefaultTableXYDataset() ;
           clear() ;
           series = new XYSeries("series1", false, false);
           dataset.addSeries(series);

           plot = new XYPlot(dataset, valueAxis, rangeAxis, renderer);
           JFreeChart chart = new JFreeChart("",
                                             JFreeChart.DEFAULT_TITLE_FONT,
                                             plot, false);
           chart.setBackgroundPaint(java.awt.Color.white);
           ChartPanel panel = new ChartPanel(chart);
           this.setLayout( new java.awt.BorderLayout() );
           this.add( panel , java.awt.BorderLayout.CENTER );
           javax.swing.JPanel panel2 = new javax.swing.JPanel() ;
           panel2.add( status );
           panel2.add( calc ) ;
           panel2.add( text ) ;
           this.add( panel2 , java.awt.BorderLayout.SOUTH ) ;
           status.setVisible( false );

           calc.addActionListener( new java.awt.event.ActionListener()
                   {
                       public void actionPerformed( java.awt.event.ActionEvent event )
                       {
                           text.setVisible( false );
                           calc.setVisible(false);
                           status.setVisible(true);
                           Thread t = new Thread()
                           {
                               public void run()
                               {
                                   updateGraph();
                                   calc.setVisible(true);
                                   text.setVisible( true );
                                   status.setVisible(false);
                               }
                           } ;
                           t.setPriority( Thread.MIN_PRIORITY );
                           t.start();
                       }
                   }
                   );
                   text.addActionListener( new java.awt.event.ActionListener()                           {
                               public void actionPerformed(java.awt.event.ActionEvent
                                       event) {
                                   com.bugaco.ui.ExportDialog.exportDialog( text , "Rarefaciton data" , new javax.swing.JScrollPane( new javax.swing.JTextArea( textSB.toString() , 20 , 80 ) ) , textSB.toString() );
//                                   javax.swing.JOptionPane.showMessageDialog( text , new javax.swing.JScrollPane( new javax.swing.JTextArea( textSB.toString() , 20 , 80 ) ) );
                               } ;
                           } ) ;
    }

       public void setCluster(com.bugaco.mioritic.model.data.clusters.
                              Clusters clusters) {
           if (clusters != null) {
               clusters.removePropertyChangeListener(listener);
           }
           this.clusters = clusters;
           if (clusters != null) {
               clusters.addPropertyChangeListener(listener);
           }
           clear() ;
       }

       public void setDistance(int distance) {
           this.distance = distance;
           clear() ;
       }

       void clear()
       {
           /*
           java.lang.Double[] d = new Double[ 1 ] ;
           d[ 0 ] = new Double( 0 ) ;
           dataset.initialize( d , d , d );
           */
           dataset.removeAllSeries();
       }

       public synchronized void updateGraph() {
           java.util.TreeMap map = clusters.get(distance);
           if (map != null && map.size() != 0) {
               textSB.setLength( 0 );
               ProgressBar pb = new ProgressBar( new MyApplet() , "Rarefaction" ) ;
               int[][] counts = new int[ 1 ][ map.size() ] ;
               int numSpecies = 0 ;
               int current = 0 ;
               java.util.Iterator iter = map.keySet().iterator();
               while (iter.hasNext()) {
                   int value = 1 ;
                   Object key = iter.next();
                   Object elem = map.get(key);
                   if (elem instanceof java.util.TreeSet) {
                       value = ((java.util.TreeSet) elem).size() ;
                   }
                   counts[ 0 ][ current++ ] = value ;
                   numSpecies += value ;
               }
               int[] subsampleSizes = new int[ numSpecies ] ;
               for( int i = 0 ; i < subsampleSizes.length ; i++ )
               {
                   subsampleSizes[i] = i + 1;
               }
               int[] s = new int[ 1 ] ;
               int[] N = new int[ 1 ] ;
               double[][] E = new double[ 1 ][ subsampleSizes.length ] ;
               double[][] SD = new double[ 1 ][ subsampleSizes.length ] ;
               Krebs.Rarefact( pb , counts , subsampleSizes , s , N , E , SD ) ;

               clear() ;
               series.clear();

               for (int i = 0; i < subsampleSizes.length; i++) {
                   series.add(i + 1 , E[0][i] );
                   textSB.append( ( i + 1 ) + " " + E[ 0 ][ i ] + " " + SD[ 0 ][ i ] + "\n" ) ;
               }
               dataset.addSeries(series);
               valueAxis.setRange(0, subsampleSizes.length );
               rangeAxis.setRange( 0 , E[ 0 ][ subsampleSizes.length -1 ] );
               plot.datasetChanged(new DatasetChangeEvent(plot, dataset));
           } else {
               clear() ;
           }
       }

    class MyListener implements java.beans.PropertyChangeListener {
           public void propertyChange(PropertyChangeEvent evt) {
               if (String.valueOf(distance).equals(evt.getPropertyName())) {
                   //updateGraph();
                   clear() ;

               }
           }

       }

       class MyApplet extends java.applet.Applet {
           public void showStatus( String msg )
           {
               status.setText( msg );
           }
       }

   }
