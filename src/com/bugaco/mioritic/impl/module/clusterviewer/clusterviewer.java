package com.bugaco.mioritic.impl.module.clusterviewer;

import com.bugaco.mioritic.impl.data.clusters.ClustersComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.text.*;

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
public class ClusterViewer extends javax.swing.JPanel {
    java.util.logging.Logger logger = java.util.logging.Logger.getLogger(this.
            getClass().toString());
    com.bugaco.mioritic.model.data.clusters.Clusters clusters = null;
    com.bugaco.mioritic.impl.data.clusters.ClustersTreeModel treeModel = null;
    javax.swing.JTree tree = null;
    javax.swing.JComboBox combo = null;
    ClusterDistributionChart chart = null;
    ClusterDistributionText chartDocument = null;
    ClusterCumulativeChart chartCumulative = null ;
    RarefactionChart rarefactionChart = null ;
    int distance = 0;

    public ClusterViewer() {
        tree = new javax.swing.JTree();
        treeModel = new com.bugaco.mioritic.impl.data.clusters.
                    ClustersTreeModel();
        tree.setModel(treeModel);
        chart = new ClusterDistributionChart();
        chartCumulative = new ClusterCumulativeChart() ;
        rarefactionChart = new RarefactionChart() ;
        combo = new javax.swing.JComboBox();
        javax.swing.JPanel comboPanel = new javax.swing.JPanel() ;
        comboPanel.setLayout( new java.awt.BorderLayout() );
        comboPanel.add( new javax.swing.JLabel( "Parameter (distance): " ) , java.awt.BorderLayout.NORTH  ) ;
        comboPanel.add( combo , java.awt.BorderLayout.CENTER  ) ;
        chartDocument = new ClusterDistributionText();
        javax.swing.JTabbedPane tabs = new javax.swing.JTabbedPane();
        this.setLayout(new java.awt.BorderLayout());
        this.add(comboPanel, java.awt.BorderLayout.NORTH);
        this.add(tabs, java.awt.BorderLayout.CENTER);

        javax.swing.JPanel treePanel = new javax.swing.JPanel() ;
        javax.swing.JPanel treeButtonPanel = new javax.swing.JPanel() ;
        final javax.swing.JButton treeText = new javax.swing.JButton( "Show data" ) ;
        treeButtonPanel.add( treeText ) ;
        treePanel.setLayout( new java.awt.BorderLayout() );
        treePanel.add( new javax.swing.JScrollPane(tree) , java.awt.BorderLayout.CENTER );
        treePanel.add( treeButtonPanel , java.awt.BorderLayout.SOUTH ) ;

        treeText.addActionListener( new java.awt.event.ActionListener()                           {
            public void actionPerformed(java.awt.event.ActionEvent
                    event) {
                                try {
                                    com.bugaco.ui.ExportDialog.exportDialog(
                                            treeText, "Tree data",
                                            new javax.
                                            swing.JScrollPane(new javax.swing.
                                            JTextArea(treeModel.getDocument(), null,
                                            20, 80)),
                                            treeModel.getDocument().
                                            getText(0,
                                            treeModel.getDocument().getLength()));
                                } catch (BadLocationException ex) {
                                }
//                javax.swing.JOptionPane.showMessageDialog( treeText , new javax.swing.JScrollPane( new javax.swing.JTextArea( treeModel.getDocument() , null , 20 , 80 ) ) );
            } ;
        } ) ;

        tabs.addTab( "Cluster grouping overview" , treePanel ) ;
        tabs.addTab("Distribution chart", new javax.swing.JScrollPane(chart));
        tabs.addTab("Cumulative chart", new javax.swing.JScrollPane(chartCumulative));
        /*
        tabs.addTab("Export chart data",
                    new javax.swing.JScrollPane(new javax.swing.JTextArea(chartDocument.getSeries() , null, 20, 80) ) ) ;
         */
        tabs.addTab("Rarefaction chart", new javax.swing.JScrollPane( rarefactionChart ));

        combo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (combo.getSelectedItem() != null) {
                    setDistance(Integer.parseInt(combo.getSelectedItem().
                                                 toString()));
                }
            }
        });
    }


    public void setClusters(com.bugaco.mioritic.model.data.clusters.Clusters
                            clusters) {
        this.clusters = clusters;
        treeModel.setClusters(clusters);
        chart.setCluster(clusters);
        combo.setModel(new ClustersComboBoxModel(clusters));
        chartDocument.setCluster( clusters );
        chartCumulative.setCluster( clusters );
        rarefactionChart.setCluster( clusters ) ;
    }

    public void setDistance(int distance) {
        this.distance = distance;
        treeModel.setDistance(distance);
        chart.setDistance(distance);
        chartDocument.setDistance(distance);
        chartCumulative.setDistance(distance);
        rarefactionChart.setDistance(distance);
    }
}
