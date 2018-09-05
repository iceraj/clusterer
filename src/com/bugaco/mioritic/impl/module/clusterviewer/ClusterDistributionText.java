package com.bugaco.mioritic.impl.module.clusterviewer;

import javax.swing.text.BadLocationException;
import java.beans.PropertyChangeEvent;

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
class ClusterDistributionText {
       MyListener listener = new MyListener();
       com.bugaco.mioritic.model.data.clusters.Clusters clusters = null;
       javax.swing.text.PlainDocument document = new javax.swing.text.
                                                 PlainDocument();
       int distance = 0;

       public void setCluster(com.bugaco.mioritic.model.data.clusters.
                              Clusters clusters) {
           if (clusters != null) {
               clusters.removePropertyChangeListener(listener);
           }
           this.clusters = clusters;
           if (clusters != null) {
               clusters.addPropertyChangeListener(listener);
           }
           updateDocument();
       }

       void setSeries(String series) {
           try {
               document.remove(0, document.getLength());
               document.insertString(0, series, null);
           } catch (BadLocationException ex) {
           }
       }

       public javax.swing.text.Document getSeries() {
           return document;
       }

       public void setDistance(int distance) {
           this.distance = distance;
           updateDocument();
       }

       void updateDocument() {
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

               StringBuffer seriesText = new StringBuffer(maxElem * 20);
               seriesText.append("Cluster size, Number of clusters, Number of sequences in clusters, Cumulative number of sequences\n");
               int count = 0;
               for (int i = 0; i <= maxElem; i++) {
                   seriesText.append(String.valueOf(i));
                   seriesText.append(',');
                   seriesText.append(String.valueOf(freq[i]));
                   seriesText.append(',');
                   seriesText.append(String.valueOf(i * freq[i]));
                   seriesText.append(',');
                   count = count + i * freq[i];
                   seriesText.append(String.valueOf(count));
                   seriesText.append('\n');
               }
               setSeries(seriesText.toString());
           }
       }

       class MyListener implements java.beans.PropertyChangeListener {
           public void propertyChange(PropertyChangeEvent evt) {
               if (String.valueOf(distance).equals(evt.getPropertyName())) {
                   updateDocument();
               }
           }
       }
   }
