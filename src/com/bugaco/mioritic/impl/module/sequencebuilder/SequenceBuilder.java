package com.bugaco.mioritic.impl.module.sequencebuilder;

import com.bugaco.mioritic.model.data.raw.RawSequences;
import com.bugaco.mioritic.model.data.sequences.Sequences;
import javax.swing.JComponent;
import javax.swing.JPanel;
import com.bugaco.ui.LoadBean;
import com.bugaco.ui.SelectorBean;
import com.bugaco.ui.models.AlgorithmModel;
import com.l2fprod.common.propertysheet.PropertySheetPanel ;
import java.awt.event.ItemEvent;
import com.bugaco.ui.CancellableProgressBean;

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
public class SequenceBuilder implements com.bugaco.mioritic.model.module.sequencebuilder.SequenceBuilder {
    RawSequences rawSequences = null ;
    Sequences sequences = null ;
    JComponent parent = null ;
    JPanel ui = null ;
    LoadBean loadBean = null ;
    SelectorBean algorithmBean = null ;
    AlgorithmModel algorithms = null ;
    PropertySheetPanel propertySheet = null ;
    CancellableProgressBean progress = null ;
    ItemListener algorithmListener = null ;
    com.bugaco.mioritic.model.algorithm.rawcompiler.RawCompiler algorithm = null ;
    com.bugaco.mioritic.model.misc.ProgressModel progressModel = null ;
    com.bugaco.mioritic.impl.data.sequences.SequencesStatisticsViewer viewer = null;

    public SequenceBuilder() {
        ui = new JPanel() ;
        loadBean = new LoadBean() ;
        algorithmBean = new SelectorBean();
        propertySheet = new PropertySheetPanel() ;
        progress = new CancellableProgressBean() ;
        progressModel = new com.bugaco.mioritic.impl.misc.ProgressModel() ;
        algorithmListener = new ItemListener();
        viewer = new com.bugaco.mioritic.impl.data.sequences.
                   SequencesStatisticsViewer();

        ui.setLayout( new javax.swing.BoxLayout( ui , javax.swing.BoxLayout.PAGE_AXIS ) );
        if( true )
        {
            javax.swing.Box b = javax.swing.Box.createHorizontalBox();
            b.add(new javax.swing.JLabel("<html><body><p align='right'>Paste aligned sequences in selected file format in the box below or use \"Import file\" button to select a file: </p></body></html>"));
            ui.add(b);
        }
        ui.add( loadBean ) ;
        ui.add( algorithmBean ) ;
        if( true )
        {
            javax.swing.Box b = javax.swing.Box.createHorizontalBox();
            b.add(new javax.swing.JLabel( "<html><body><p align='right'>Import sequences options: </p></body></html>" ) ) ;
            ui.add(b);
        }
        ui.add( propertySheet ) ;
        if( true )
        {
            javax.swing.Box b = javax.swing.Box.createHorizontalBox();
            b.add(new javax.swing.JLabel( "<html><body><p align='right'><br>Dataset statistics: </p></body></html>" ) ) ;
            ui.add(b);
        }
        ui.add(viewer);
        ui.add( progress ) ;

        progress.setProgressModel( progressModel );

        loadBean.setDocumentModel( rawSequences );

        propertySheet.setPreferredSize( new java.awt.Dimension( 100 , 60 ) );
        propertySheet.setDescriptionVisible( false );
        propertySheet.setToolBarVisible( false );
        propertySheet.setMode( PropertySheetPanel.VIEW_AS_FLAT_LIST ) ;
    }

    public void setRawSequences(RawSequences raw) {
        this.rawSequences = raw ;
        loadBean.setDocumentModel( raw );
    }

    public void setSequences(Sequences seq) {
        this.sequences = seq ;
        viewer.setSequences(seq);
    }

    public com.bugaco.mioritic.impl.data.sequences.SequencesStatisticsViewer getViewer()
    {
        return viewer ;
    }

    public void setParent(JComponent parent) {
        if( parent != null )
        {
            parent.remove( ui ) ;
        }
        this.parent = parent ;
        if( parent != null )
        {
            parent.add( ui ) ;
        }
    }

    public void setAlgorithms(AlgorithmModel algorithms) {
        Object selectedItem = null ;
        if( this.algorithms != null )
        {
            algorithms.removeItemListener(algorithmListener);
            selectedItem = algorithms.getSelectedItem() ;
        }
        this.algorithms = algorithms ;
        algorithmBean.setModel( algorithms );
        algorithms.addItemListener( algorithmListener );
        if( selectedItem != null )
        {
            algorithms.setSelectedItem(selectedItem);
        }
        else
        {
            algorithms.setSelectedItem(algorithms.getSelectedItem() ) ;
        }
    }

    class ItemListener implements java.awt.event.ItemListener
    {
        public void itemStateChanged(ItemEvent e) {
            if( e.getStateChange() == ItemEvent.SELECTED )
            {
            try
            {
                if( algorithm != null )
                {
                    algorithm.stop();
                }
                Object algorithmClass = algorithms.getSelectedAlgorithm();
                if (algorithmClass != null) {
                    if (algorithmClass instanceof String) {
                        Class c = Class.forName(algorithmClass.toString());
                        Class[] interfaces = c.getInterfaces() ;
                        boolean instantiate = false ;
                        for( int i = 0 ; i < interfaces.length ; i++ )
                        {
                            Class ic = interfaces[ i ] ;
                            if ("com.bugaco.mioritic.model.algorithm.rawcompiler.RawCompilerFactory".equals( ic.getName() )) {
                                instantiate = true ; break ;
                            }
                        }
                        if( instantiate ) {
                                com.bugaco.mioritic.model.algorithm.rawcompiler.
                                        RawCompiler compiler =
                                                ((com.bugaco.mioritic.model.
                                                  algorithm.rawcompiler.
                                                  RawCompilerFactory) c.
                                                 newInstance()).
                                                createRawCompiler();
                                algorithm = compiler;
                                algorithm.setProgress(progressModel);
                                algorithm.setRawSequences(rawSequences);
                                algorithm.setSequences(sequences);
                                if( algorithm != null )
                                {
                                    algorithm.start();
                                    propertySheet.setBeanInfo( algorithm.getBeanInfo() );
                                    propertySheet.readFromObject( algorithm );

                                }


                        }
                    }
                }
            }
            catch( Exception ex )
            {
                ex.printStackTrace();
            }
            }
        }
    }
}
