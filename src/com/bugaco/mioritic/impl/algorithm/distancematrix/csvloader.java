package com.bugaco.mioritic.impl.algorithm.distancematrix;

import com.bugaco.mioritic.model.data.raw.RawDataMatrix;
import com.bugaco.mioritic.model.data.distancematrix.DistanceMatrix;
import com.bugaco.mioritic.model.misc.ProgressModel;
import java.beans.BeanInfo;
import java.util.logging.Level;
import javax.swing.event.DocumentEvent;
import javax.swing.text.BadLocationException;

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
public class CSVLoader implements com.bugaco.mioritic.model.algorithm.distancematrix.DistanceLoader, Runnable {
    java.util.logging.Logger logger = java.util.logging.Logger.getLogger( this.getClass().getName() ) ;
    RawDataMatrix data = null ;
    DistanceMatrix dm = null ;
    ProgressModel progress = null ;
    Thread thread = null ;
    boolean running = false ;
    long modifiedAt = 0 ;
    long compilationStartedAt = 0 ;
    RawDataListener listener = null ;

    public CSVLoader() {
        listener = new RawDataListener();
    }

    public void start() {
        logger.log( Level.INFO , "Starting" );
        if( this.data != null )
        {
            this.data.removeDocumentListener( listener );
            this.data.addDocumentListener( listener );
            thread = new Thread( this ) ;
            thread.start();
        }
    }

    public void stop() {
        logger.log( Level.INFO , "Stopping" );
        if( this.data != null )
        {
            this.data.removeDocumentListener( listener );
        }
        running = false ;
        modified() ;
    }

    public void run()
    {
        running = true ;
        while( running )
        {
            try
            {
                runOne();
                while( running && (this.compilationStartedAt > this.modifiedAt) ) {
                	logger.log( Level.FINEST , "Sleeping" );
                    Thread.sleep(1000);
                }
            }
            catch( Exception e )
            {
                e.printStackTrace();
            }
        }
    }
    public void runOne()
    {
        while( running )
        {
            this.compilationStartedAt = System.currentTimeMillis() ;
            javax.swing.text.Element rootElement = data.getDefaultRootElement() ;
            progress.setRangeProperties( 0 , 0 , 0 , rootElement.getElementCount() , true );
            dm.setSize( rootElement.getElementCount() - 1 ) ;
            if( rootElement.getElementCount() < 2 )
            {
                break ;
            }
            int size = 0 ;
            for( int i = 1 ; i < rootElement.getElementCount() ; i++ )
            {
            	boolean hasData = false ;
                progress.setValue(i);
                if( !running || (this.compilationStartedAt <= this.modifiedAt) ) { break ; }
                javax.swing.text.Element paragraph = rootElement.getElement( i ) ;
                if( paragraph.isLeaf() )
                {
                    try {
                        String line = data.getText(paragraph.getStartOffset(),
                                                  paragraph.getEndOffset()-paragraph.getStartOffset());
                        java.util.StringTokenizer st = new java.util.StringTokenizer( line , "," ) ;
                        if( st.hasMoreTokens() )
                        {
                            String name = st.nextToken() ;
                            dm.setName( i - 1 , name );
                        }
                        int j = 0 ;
                        while( st.hasMoreTokens() && ( j < i ) )
                        {
                        	hasData = true ;
                            dm.set( i - 1 , j++ , Integer.parseInt( st.nextToken().trim() ));
                        }
                    } catch (BadLocationException ex1) {
                        ex1.printStackTrace();
                    }
                    if( hasData  ) { size++ ; } 
                }
            }
            int[] array = dm.getArray() ;
            String[] names = dm.getNames() ;
            dm.setSize( 0 ) ;
            dm.setSize( size ) ;
            int[] array2 = new int[ dm.getArray().length ] ;
            System.arraycopy( array , 0 , array2 , 0 , array2.length ) ;
            dm.setNames( names ) ;
            dm.setArray( array2 ) ;
            progress.setRangeProperties( 1 , 0 , 0 , 1 , true );
            break ;
        }
    }

    void modified() {
        modifiedAt = System.currentTimeMillis() ;
    }

    public void setRawDistanceMatrix(RawDataMatrix data) {
        this.data = data ;
    }

    public void setDistanceMatrix(DistanceMatrix dm) {
        this.dm = dm ;
    }

    public void setProgress(ProgressModel progress) {
        this.progress = progress ;
    }

    public BeanInfo getBeanInfo() {
        return null;
    }

    class RawDataListener implements javax.swing.event.DocumentListener {
    public void changedUpdate(DocumentEvent e) {
        modified() ;
    }

    public void insertUpdate(DocumentEvent e) {
        modified() ;
    }

    public void removeUpdate(DocumentEvent e) {
        modified() ;
    }

}

}
