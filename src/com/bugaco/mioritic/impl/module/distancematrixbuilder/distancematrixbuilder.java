package com.bugaco.mioritic.impl.module.distancematrixbuilder;

import com.bugaco.mioritic.model.data.sequences.Sequences;
import com.bugaco.mioritic.model.data.distancematrix.DistanceMatrix;
import com.bugaco.ui.models.AlgorithmModel;
import javax.swing.JComponent;
import com.bugaco.ui.SelectorBean;
import java.awt.event.ItemEvent;
import com.bugaco.ui.CancellableProgressBean;
import com.l2fprod.common.propertysheet.PropertySheetPanel;
import java.awt.event.ActionEvent;

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
public class DistanceMatrixBuilder implements com.bugaco.mioritic.model.module.distancematrixbuilder.DistanceMatrixBuilder {
    Sequences sequences = null;
    DistanceMatrix distanceMatrix = null ;
    AlgorithmModel algorithms = null;
    javax.swing.JPanel ui = null;

    com.bugaco.mioritic.impl.data.distancematrix.DistanceMatrixTableModel dmtableModel = null ;
    SelectorBean algorithmBean = null;
    ItemListener algorithmListener = null;
    com.bugaco.mioritic.model.algorithm.distancematrix.DistanceCompiler
            algorithm = null;
    com.bugaco.mioritic.model.misc.ProgressModel progressModel = null;
    PropertySheetPanel propertySheet = null;
    CancellableProgressBean progress = null;
    javax.swing.JComponent parent = null;
    com.bugaco.mioritic.impl.data.distancematrix.DistanceMatrixStatisticsViewer viewer = null ;


    public DistanceMatrixBuilder() {
        algorithmBean = new SelectorBean();
        algorithmListener = new ItemListener();

        propertySheet = new PropertySheetPanel();
        progress = new CancellableProgressBean();
        progressModel = new com.bugaco.mioritic.impl.misc.ProgressModel();

        viewer = new com.bugaco.mioritic.impl.data.distancematrix.DistanceMatrixStatisticsViewer() ;
        dmtableModel = new com.bugaco.mioritic.impl.data.distancematrix.DistanceMatrixTableModel() ;

        final javax.swing.JButton showTable = new javax.swing.JButton( "Show matrix" ) ;
        final javax.swing.JButton showStat = new javax.swing.JButton( "Show statistics" ) ; ;

        javax.swing.JPanel exportPanel = new javax.swing.JPanel() ;
        exportPanel.add( showTable ) ;
        exportPanel.add( showStat ) ;

        ui = new javax.swing.JPanel();
        ui.setLayout(new javax.swing.BoxLayout(ui, javax.swing.BoxLayout.PAGE_AXIS));
        ui.add(algorithmBean);
        ui.add(propertySheet);
        ui.add(exportPanel) ;
        ui.add(progress);

        progress.setProgressModel(progressModel);

        propertySheet.setPreferredSize(new java.awt.Dimension(100, 60));
        propertySheet.setDescriptionVisible(false);
        propertySheet.setToolBarVisible(false);
        propertySheet.setMode(PropertySheetPanel.VIEW_AS_FLAT_LIST);

        showTable.addActionListener( new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                javax.swing.JTable table = new javax.swing.JTable( dmtableModel ) ;
                table.setAutoResizeMode( javax.swing.JTable.AUTO_RESIZE_OFF );
                table.setPreferredScrollableViewportSize( new java.awt.Dimension( 640 , 480 ) );
                com.bugaco.ui.ExportDialog.exportDialog( showTable , "Distance matrix data" , new javax.swing.JScrollPane( table ) , dmtableModel.getAsText() );
            } }
        ) ;

        showStat.addActionListener( new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewer.setPreferredSize( new java.awt.Dimension( 640 , 480 ) );
                javax.swing.JOptionPane.showMessageDialog( showStat , new javax.swing.JScrollPane( viewer ) , "Distance Matrix statistics" , javax.swing.JOptionPane.PLAIN_MESSAGE ) ;
            } }
        ) ;

    }

    public void setSequences(Sequences seq) {
        this.sequences = seq;
        if( algorithm != null )
        {
            algorithm.setSequences( seq ) ;
        }
    }

    public void setDistanceMatrix( com.bugaco.mioritic.model.data.distancematrix.DistanceMatrix distMatrix )
    {
        distanceMatrix = distMatrix ;
        dmtableModel.setDistanceMatrix( distMatrix ) ;
        viewer.setDistanceMatrix( distMatrix );
        if( algorithm != null )
        {
            algorithm.setDistanceMatrix( distanceMatrix ) ;
        }
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
                                        "com.bugaco.mioritic.model.algorithm.distancematrix.DistanceCompilerFactory".
                                        equals(ic.getName())) {
                                    instantiate = true;
                                    break;
                                }
                            }
                            if (instantiate) {
                                com.bugaco.mioritic.model.algorithm.
                                        distancematrix.DistanceCompiler
                                        compiler =
                                                ((com.bugaco.mioritic.model.
                                                  algorithm.distancematrix.
                                                  DistanceCompilerFactory) c.
                                                 newInstance()).
                                                createCompiler();
                                algorithm = compiler;
                                algorithm.setProgress(progressModel);
                                algorithm.setSequences(sequences);
                                algorithm.setDistanceMatrix( distanceMatrix );
                                if (algorithm != null) {
                                    System.out.println("alg = " + algorithm);
                                    algorithm.start();
                                    propertySheet.setBeanInfo(algorithm.
                                            getBeanInfo());
                                    propertySheet.readFromObject(algorithm);

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
