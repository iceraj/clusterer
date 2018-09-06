package com.bugaco.mioritic.impl.module.clusterbuilder;

import com.bugaco.mioritic.model.data.distancematrix.DistanceMatrix;
import com.bugaco.mioritic.model.data.clusters.Clusters;
import com.bugaco.ui.models.AlgorithmModel;
import javax.swing.JComponent;
import com.bugaco.ui.SelectorBean;
import com.bugaco.ui.CancellableProgressBean;
import java.awt.event.ItemEvent;
import java.util.Properties;

import com.l2fprod.common.propertysheet.PropertySheetPanel;

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
public class ClusterBuilder implements com.bugaco.mioritic.model.module.
        clusterbuilder.ClusterBuilder {
    javax.swing.JPanel ui = null;
    javax.swing.JComponent parent = null;
    DistanceMatrix distanceMatrix = null;
    Clusters clusters = null;
    AlgorithmModel algorithms = null;
    SelectorBean algorithmBean = null;
    ItemListener algorithmListener = null;
    com.bugaco.mioritic.impl.data.distancematrix.DistanceMatrixStatisticsViewer viewer = null ;
//    com.bugaco.mioritic.impl.algorithm.clustering.NeighborJoiningClustering clusterCompiler = null ;
    com.bugaco.mioritic.model.algorithm.clustering.ClusterCompiler algorithm = null ;
    com.bugaco.mioritic.model.misc.ProgressModel progressModel = null;
    PropertySheetPanel propertySheet = null;

    CancellableProgressBean progress = null;
    com.bugaco.mioritic.impl.module.clusterviewer.ClusterViewer clusterViewer = null ;

    public ClusterBuilder() {
//        clusterCompiler = new com.bugaco.mioritic.impl.algorithm.clustering.NeighborJoiningClustering() ;
        viewer = new com.bugaco.mioritic.impl.data.distancematrix.DistanceMatrixStatisticsViewer() ;
        progress = new CancellableProgressBean();
        progressModel = new com.bugaco.mioritic.impl.misc.ProgressModel();
        algorithmBean = new SelectorBean() ;
        algorithmListener = new ItemListener();
        progress.setProgressModel( progressModel );
//        clusterCompiler.setProgress( progressModel );
        clusterViewer = new  com.bugaco.mioritic.impl.module.clusterviewer.ClusterViewer() ;

        propertySheet = new PropertySheetPanel() ;
        propertySheet.setPreferredSize(new java.awt.Dimension(100, 60));
        propertySheet.setDescriptionVisible(false);
        propertySheet.setToolBarVisible(false);
        propertySheet.setMode(PropertySheetPanel.VIEW_AS_FLAT_LIST);


        com.bugaco.ui.models.AlgorithmModel algModel = new com.bugaco.ui.models.impl.DefaultAlgorithmModel() ;
        algModel.setText( "Select clustering algorithm:" ) ;
        try
        {
        	Properties p = new Properties() ;
        	p.load( algModel.getClass().getResourceAsStream( "/conf/ClusteringCompiler.properties" ) ) ;
        	algModel.setItemsFromProperties( p ) ;
        }
        catch( Exception e )
        {
        	e.printStackTrace() ;
        }
        //algModel.addElement( new com.bugaco.ui.models.impl.DefaultAlgorithmItem( "Complete Linkage" , "com.bugaco.mioritic.impl.algorithm.clustering.CompleteLinkageClusteringFactory" ) ) ;
        //algModel.addElement( new com.bugaco.ui.models.impl.DefaultAlgorithmItem( "Single Linkage" , "com.bugaco.mioritic.impl.algorithm.clustering.NeighborJoiningClusteringFactory" ) ) ;
        

        setAlgorithms( algModel );
        ui = new javax.swing.JPanel();
        ui.setLayout(new javax.swing.BoxLayout(ui, javax.swing.BoxLayout.PAGE_AXIS));
        ui.add( algorithmBean ) ;
        ui.add( clusterViewer ) ;
        ui.add( progress ) ;
    }

    public void setDistanceMatrix(DistanceMatrix distMatrix) {
        this.distanceMatrix = distMatrix ;
        viewer.setDistanceMatrix( distanceMatrix );
        algorithm.setDistanceMatrix( distanceMatrix );
        System.out.println( "CB setDistanceMatrix " + distMatrix ) ;
        algorithm.stop();
        algorithm.start();

    }

    public void setClusters(Clusters clusters) {
        this.clusters = clusters ;
        algorithm.setClusters( clusters );
        clusterViewer.setClusters( clusters );
    }

    public void setAlgorithms(AlgorithmModel algorithms) {
        Object selectedItem = null;
        if (this.algorithms != null) {
            algorithms.removeItemListener(algorithmListener);
            selectedItem = algorithms.getSelectedItem();
        }
        this.algorithms = algorithms;
        algorithmBean.setModel(algorithms);
        algorithms.addItemListener(algorithmListener);
        if (selectedItem != null) {
            algorithms.setSelectedItem(selectedItem);
        } else {
            algorithms.setSelectedItem(algorithms.getSelectedItem());
        }

    }

    public void setParent(JComponent parent) {
        if (parent != null) {
            System.err.println("Parent=" + parent + ";UI=" + ui);
            parent.remove(ui);
        }
        this.parent = parent;
        if (parent != null) {
            System.err.println("Parent=" + parent + ";UI=" + ui);
            parent.add(ui);
        }

    }

    class ItemListener implements java.awt.event.ItemListener {
    public void itemStateChanged(ItemEvent e) {
        //TODO: set algorithms, set property sheet
        if (ItemEvent.SELECTED == e.getStateChange()) {
            System.out.println("Item Changed: " + e);
            try {
                if (algorithm != null) {
                    algorithm.stop();
                }
                Object algorithmClass = algorithms.getSelectedAlgorithm();
                if (algorithmClass != null) {
                    if (algorithmClass instanceof String) {
                        Class c = Class.forName(algorithmClass.toString());
                        Class[] interfaces = c.getInterfaces();
                        boolean instantiate = false;
                        for (int i = 0; i < interfaces.length; i++) {
                            Class ic = interfaces[i];
                            if (
                                    "com.bugaco.mioritic.model.algorithm.clustering.ClusterCompilerFactory".
                                    equals(ic.getName())) {
                                instantiate = true;
                                break;
                            }
                        }
                        if (instantiate) {
                            com.bugaco.mioritic.model.algorithm.clustering.ClusterCompiler
                                    compiler =
                                            ((com.bugaco.mioritic.model.algorithm.clustering.ClusterCompilerFactory) c.
                                             newInstance()).
                                            createCompiler();
                            algorithm = compiler;
                            algorithm.setProgress(progressModel);
                            algorithm.setDistanceMatrix( distanceMatrix );
                            algorithm.setClusters( clusters );
                            if (algorithm != null) {
                                System.out.println("alg = " + algorithm);
                                algorithm.start();
                                if( algorithm.getBeanInfo() != null )
                                {
                                    propertySheet.setVisible( true );
                                    propertySheet.setBeanInfo(algorithm.
                                            getBeanInfo());
                                    propertySheet.readFromObject(algorithm);
                                }
                                else
                                {
                                    propertySheet.setVisible( false );
                                }

                            }

                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}

}
