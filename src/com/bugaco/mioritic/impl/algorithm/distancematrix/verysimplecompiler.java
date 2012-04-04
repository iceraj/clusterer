package com.bugaco.mioritic.impl.algorithm.distancematrix;

import com.bugaco.mioritic.model.data.sequences.Sequences;
import com.bugaco.mioritic.model.data.distancematrix.DistanceMatrix;
import com.bugaco.mioritic.model.misc.ProgressModel;
import java.beans.BeanInfo;
import java.util.logging.Logger;
import java.beans.PropertyDescriptor;
import java.beans.BeanDescriptor;
import java.beans.EventSetDescriptor;
import java.beans.IntrospectionException;
import java.awt.Image;
import java.beans.MethodDescriptor;
import java.util.logging.Level;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListDataEvent;
import com.bugaco.mioritic.model.data.sequences.Sequence;

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
public class VerySimpleCompiler implements com.bugaco.mioritic.model.algorithm.distancematrix.DistanceCompiler , java.lang.Runnable {
    Logger logger = null ;
    Thread thread = null ;
    BeanInfo bean = null ;

    int gapgapdistance = 0 ;
    int gapnoinfodistance = 0 ;
    int gapinfodistance = 1 ;
    int gapNdistance = 1 ;
    int noinfoinfodistance = 0 ;

    Sequences sequences = null ;
    DistanceMatrix distanceMatrix = null ;
    ProgressModel progress = null ;

    VerySimpleCompiler compiler = null ;
    ListDataListener listener = null ;

    long modifiedAt = 0 ;
    long compilationStartedAt = 0 ;

    boolean running = false ;

    public VerySimpleCompiler() {
        compiler = this ;
        logger = java.util.logging.Logger.getLogger( this.getClass().getName() ) ;
        listener = new MyListDataListener();
        bean = new BeanInfo() {
    public int getDefaultEventIndex() {
        return 0;
    }

    public int getDefaultPropertyIndex() {
        return 0;
    }

    public Image getIcon(int iconKind) {
        return null;
    }

    public BeanDescriptor getBeanDescriptor() {
        return null;
    }

    public BeanInfo[] getAdditionalBeanInfo() {
        return null;
    }

    public EventSetDescriptor[] getEventSetDescriptors() {
        return null;
    }

    public MethodDescriptor[] getMethodDescriptors() {
        return null;
    }

    public PropertyDescriptor[] getPropertyDescriptors() {
        PropertyDescriptor[] properties = new PropertyDescriptor[ 5 ] ;

        try {

            properties[ 0 ] = new PropertyDescriptor( "noinfoinfodistance"
                         , compiler.getClass() ) ;
            properties[ 0 ].setDisplayName( "No info to a character distance");
            properties[ 0 ].setShortDescription( "distance between no info and a character");

            properties[ 1 ] = new PropertyDescriptor( "gapnoinfodistance"
                    , compiler.getClass() ) ;
            properties[ 1 ].setDisplayName( "No info to a gap distance");
            properties[ 1 ].setShortDescription( "distance between gap and no info character");


            properties[ 2 ] = new PropertyDescriptor( "gapinfodistance"
                    , compiler.getClass() ) ;
            properties[ 2 ].setDisplayName( "Gap-Character distance");
            properties[ 2 ].setShortDescription( "distance between gap and a character");

            properties[ 3 ] = new PropertyDescriptor( "gapNdistance"
                         , compiler.getClass() ) ;
            properties[ 3 ].setDisplayName( "Gap N-character distance");
            properties[ 3 ].setShortDescription( "distance between gap and an N character");

            properties[ 4 ] = new PropertyDescriptor( "gapgapdistance"
                    , compiler.getClass() );
            properties[ 4 ].setDisplayName( "Gap-Gap distance");
            properties[ 4 ].setShortDescription( "distance between two gap characters");



        } catch (IntrospectionException ex) {
            ex.printStackTrace();
        }
        return properties ;
    }

} ;

    }

    public void setNoinfoinfodistance( int gid )
    {
        noinfoinfodistance = gid ;
    }

    public int getNoinfoinfodistance()
    {
        return noinfoinfodistance ;
    }


    public void setGapnoinfodistance( int gid )
    {
        gapnoinfodistance = gid ;
    }

    public int getGapnoinfodistance()
    {
        return gapnoinfodistance ;
    }

    public void setGapinfodistance( int gid )
    {
        gapinfodistance = gid ;
    }

    public int getGapinfodistance()
    {
        return gapinfodistance ;
    }

    public void setGapNdistance( int gid )
    {
        gapNdistance = gid ;
    }

    public int getGapNdistance()
    {
        return gapNdistance ;
    }

    public void  setGapgapdistance( int ggd )
    {
        gapgapdistance = ggd ;
    }

    public int getGapgapdistance()
    {
        return gapgapdistance ;
    }



    public void setSequences(Sequences seq) {
        logger.log( Level.FINE , "setRawSequence: " + seq ) ;
        if( this.sequences != null )
        {
            this.sequences.removeListDataListener( listener ) ;
        }
        modified() ;
        this.sequences = seq ;
        if( this.sequences != null )
        {
            this.sequences.addListDataListener( listener ) ;
        }
    }

    public void setDistanceMatrix(DistanceMatrix dm ) {
        logger.log( Level.INFO , "setDistanceMatrix: " + dm ) ;
        this.distanceMatrix = dm ;
    }

    public void setProgress(ProgressModel progress) {
        logger.log( Level.FINE , "setProgress: " + progress ) ;
        this.progress = progress ;
    }

    public BeanInfo getBeanInfo() {
        return bean;
    }

    void modified() {
        modifiedAt = System.currentTimeMillis() ;
    }

    public void run() {
        running = true ;
        while( running )
        {
OUT:
            while( this.compilationStartedAt <= this.modifiedAt )
            {
                this.compilationStartedAt = System.currentTimeMillis();
                distanceMatrix.setArray( null ) ;
                int size = sequences.getSize();
                logger.log(Level.INFO,
                           "Starting compilation " + sequences.getSize());
                String[] seq_names = new String[size];
                Object[] values = new Object[size];
                for (int i = 0; i < sequences.getSize(); i++) {
                    Sequence s = (Sequence) sequences.getElementAt(i);
                    if( s == null )
                    {
                        compilationStartedAt = modifiedAt - 1;
                        break OUT;
                    }
                    seq_names[i] = s.getName();
                    values[i] = s.getData();
                }
                com.bugaco.mioritic.impl.data.distancematrix.DistanceMatrix dm = (
                        com.bugaco.mioritic.impl.data.distancematrix.
                        DistanceMatrix) (new com.bugaco.mioritic.impl.data.
                                         distancematrix.DistanceMatrixFactory()).
                                    createDistanceMatrix();
                dm.setSize(size);
                progress.setRangeProperties(0, 0, 0, size * (size + 1) / 2, true);
                if (!running || (this.compilationStartedAt <= this.modifiedAt)) {
                    break OUT;
                }
                for (int i = 0; i < size; i++) {
                    byte[] val_i = (byte[]) values[i];
                    for (int j = 0; j < i; j++) {
                        progress.setValue(i * (i - 1) / 2 + j);
                        byte[] val_j = (byte[]) values[j];
                        int distance = 0;
                        int overlap = (val_i.length < val_j.length ?
                                       val_i.length : val_j.length);
                        for (int k = 0; k < overlap; k++) {
                            if ((val_i[k] & val_j[k]) == 0) {
                                distance++;
                            }
                        }
                        dm.set(i, j, distance);
                        if (!running ||
                            (this.compilationStartedAt <= this.modifiedAt)) {
                            break OUT;
                        }
                    }
                }
                //                distanceMatrix.clear() ;

                if ((this.compilationStartedAt > this.modifiedAt) && running) {
                    if (sequences.getSize() != 0) {
                        logger.log(Level.INFO,
                                   "Write sequences: " + sequences.getSize());
                        progress.setRangeProperties(1, 0, 0, 1, true);
                        distanceMatrix.setArray( dm.getArray() );
                        distanceMatrix.setNames( seq_names ) ;
                    }
                }
            }
            try {
                logger.log( Level.FINEST , "Sleeping" );
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
            }
        }
        logger.log( Level.INFO , "Stopped: " + this.getClass().getName() );
    }

    public void start() {
        logger.log( Level.INFO , "Starting" );
        if( this.sequences != null )
        {
            this.sequences.removeListDataListener( listener );
            this.sequences.addListDataListener( listener );
            thread = new Thread( this ) ;
            thread.start();
        }
    }

    public void stop() {
        logger.log( Level.INFO , "Stopping" );
        if( this.sequences != null )
        {
            this.sequences.removeListDataListener( listener );
        }
        running = false ;
        modified() ;
    }

    class MyListDataListener implements ListDataListener
    {
        public void contentsChanged(ListDataEvent e) {
//            logger.log( Level.INFO , "contentsChanged" );
            modified() ;
        }

        public void intervalAdded(ListDataEvent e) {
            logger.log( Level.INFO , "intervalAdded" );
            modified() ;
        }

        public void intervalRemoved(ListDataEvent e) {
            logger.log( Level.INFO , "intervalRemoved" );
            modified() ;
        }
    }
}
