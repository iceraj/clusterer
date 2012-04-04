package com.bugaco.mioritic.impl.module.clusterviewer;

import org.jfree.chart.renderer.xy.XYBarRenderer;
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
class ClusterCumulativeChart extends com.bugaco.ui.charts.Chart2D {
       com.bugaco.mioritic.model.data.clusters.Clusters clusters = null;
       int distance = 0 ;

       public ClusterCumulativeChart() {
           super() ;
       }

       protected org.jfree.chart.renderer.xy.XYItemRenderer getRenderer()
       {
           return new XYBarRenderer();
       }

       protected String getRangeAxisLabel()
       {
           return "Cumulative number of sequences" ;
       }

       protected String getValueAxisLabel()
       {
           return "Number of sequences per cluster" ;
       }
       public void setCluster(com.bugaco.mioritic.model.data.clusters.Clusters clusters) {
           setPropertyGenerator( clusters );
           this.clusters = clusters ;
       }

       public void setDistance(int distance) {
           this.distance = distance ;
           this.listener.setProperty( String.valueOf( distance ) ) ;
           updateGraph();
       }

       public synchronized void updateGraph() {
           if( clusters == null )
           {
               clearGraph();
               return ;
           }
           java.util.TreeMap map = clusters.get(distance);
           if (map != null && map.size() != 0) {
               java.util.Iterator iter = map.keySet().iterator();
               int max = 3000;
               int[] freq = new int[max];
               for (int i = 0; i < max; i++) {
                   freq[i] = 0;
               } while (iter.hasNext()) {
                   Object key = iter.next();
                   Object elem = map.get(key);
                   if (elem instanceof java.util.TreeSet) {
                       int index = ((java.util.TreeSet) elem).size();
                       if (index >= freq.length) {
                           index = freq.length - 1;
                       }
                       freq[index] = freq[index] + 1;
                   } else {
                       freq[1] = freq[1] + 1;
                   }
               }
               int maxElem = 0;
               for (int i = freq.length - 1; i >= 0; i--) {
                   if (freq[i] > 0) {
                       maxElem = i;
                       break;
                   }
               }

               XYSeries series = getXYSeries() ;
               StringBuffer textSB = new StringBuffer() ;
               textSB.append("Cluster size, Number of clusters, Number of sequences in clusters, Cumulative number of sequences\n");
               int count = 0;
               for (int i = 0; i <= maxElem; i++) {
                   count = count + i * freq[ i ] ;
                   series.add(i, count);
                   textSB.append(String.valueOf(i));
                   textSB.append(',');
                   textSB.append(String.valueOf(freq[i]));
                   textSB.append(',');
                   textSB.append(String.valueOf(i * freq[i]));
                   textSB.append(',');
                   textSB.append(String.valueOf(count));
                   textSB.append('\n');
               }
               replaceSeries( series , textSB.toString() );
               valueAxis.setRange(0, maxElem);
           } else {
               clearGraph();
           }
       }

   }
