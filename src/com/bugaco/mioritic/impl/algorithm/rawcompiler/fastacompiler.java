package com.bugaco.mioritic.impl.algorithm.rawcompiler;

import com.bugaco.mioritic.model.data.raw.RawSequences;
import com.bugaco.mioritic.model.data.sequences.Sequences;
import com.bugaco.mioritic.model.misc.ProgressModel;
import java.beans.BeanInfo;
import javax.swing.event.DocumentEvent;
import java.awt.Image;
import java.beans.BeanDescriptor;
import java.beans.EventSetDescriptor;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.*;
import javax.swing.text.*;
import com.bugaco.mioritic.model.misc.Logger;
import java.util.logging.Level;

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
public class FastaCompiler implements com.bugaco.mioritic.model.algorithm.rawcompiler.RawCompiler, java.lang.Runnable {
    Logger logger = null ;
    Thread thread = null ;
    RawSequences raw = null ;
    Sequences seq = null ;
    ProgressModel progress = null ;
    BeanInfo bean = null ;
    char gapCharacter = '-' ;
    char noInfoCharacter = '.' ;

    RawSequencesListener listener = null ;
    FastaCompiler compiler = this ;

    long modifiedAt = 0 ;
    long compilationStartedAt = 0 ;

    boolean running = false ;

    public FastaCompiler() {
        logger = new com.bugaco.mioritic.impl.misc.Logger( this.getClass().getName() ) ;
        //logger.setLevel( Level.ALL ) ;
        listener = new RawSequencesListener();
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
                PropertyDescriptor[] properties = new PropertyDescriptor[2] ;
                try {
                    properties[ 0 ] = new PropertyDescriptor( "gapCharacter"
                            , compiler.getClass() );
                    properties[ 0 ].setDisplayName( "gap character");
                    properties[ 0 ].setShortDescription( "Please set gap character, default gap character is '-'");
                    properties[ 1 ] = new PropertyDescriptor( "noInfoCharacter"
                            , compiler.getClass() ) ;
                    properties[ 1 ].setDisplayName( "no information character");
                    properties[ 1 ].setShortDescription( "Please set no info character, default gap character is '.'");
                } catch (IntrospectionException ex) {
                    ex.printStackTrace();
                }
                return properties ;
            }

        } ;
    }

    public void start() {
        logger.log( Level.INFO , "Starting" );
        if( this.raw != null )
        {
            this.raw.removeDocumentListener( listener );
            this.raw.addDocumentListener( listener );
            thread = new Thread( this ) ;
            thread.start();
        }
    }

    public void stop() {
        logger.log( Level.INFO , "Stopping" );
        if( this.raw != null )
        {
            this.raw.removeDocumentListener( listener );
        }
        running = false ;
        modified() ;
    }

    public void setRawSequences(RawSequences raw) {
        if( this.raw != null )
        {
            this.raw.removeDocumentListener( listener );
        }
        logger.log( Level.FINE , "setRawSequence: " + raw ) ;
        this.raw = raw ;
        modified() ;
        if( this.raw != null )
        {
            this.raw.addDocumentListener( listener );
        }
    }

    public void setSequences(Sequences seq) {
        logger.log( Level.FINE , "setRawSequence: " + seq ) ;
        this.seq = seq ;
        modified() ;
    }

    public void setProgress(ProgressModel progress) {
        logger.log( Level.FINE , "setProgress: " + progress ) ;
        this.progress = progress ;
    }

    public BeanInfo getBeanInfo() {
        return bean ;
    }

    public void setGapCharacter( String c ) {
        logger.log( Level.FINE , "setGapCharacter: " + c ) ;
        modified() ;
        this.gapCharacter = c.charAt( 0 ) ;
    }

    public String getGapCharacter() {
        return String.valueOf( gapCharacter ) ;
    }

    public void setNoInfoCharacter( char c ) {
        logger.log( Level.FINE , "setNoInfoCharacter: " + c ) ;
        modified() ;
        this.noInfoCharacter = c ;
    }

    public char getNoInfoCharacter() {
        return this.noInfoCharacter ;
    }

    public void run() {
        running = true ;
        java.util.HashMap sequences = new java.util.HashMap() ;
        while( running )
        {
            while( this.compilationStartedAt <= this.modifiedAt )
            {
                logger.log( Level.FINE , "Starting compilation" );
                sequences.clear();
                this.compilationStartedAt = System.currentTimeMillis() ;
                javax.swing.text.Element rootElement = raw.getDefaultRootElement() ;
                progress.setRangeProperties( 0 , 0 , 0 , rootElement.getElementCount() , true );
                String name = null ;
                StringBuffer data = new StringBuffer() ;
                for( int i = 0 ; i < rootElement.getElementCount() ; i++ )
                {
                    progress.setValue( i );
                    if( !running || (this.compilationStartedAt <= this.modifiedAt) ) { break ; }
                    javax.swing.text.Element paragraph = rootElement.getElement( i ) ;
                    if( paragraph.isLeaf() )
                    {
                        try {
                            String line = raw.getText(paragraph.getStartOffset(),
                                    paragraph.getEndOffset()-paragraph.getStartOffset());
                            if (line.startsWith(">")) {
                                if (name != null) {
                                    sequences.put( name , data.toString().trim() ) ;
                                    logger.log( Level.FINE , "Loading \"" + name + "\" " + data.length());
                                }
                                data.setLength(0);
                                name = line.substring(1).trim() ;
                            } else {
                                data.append(line);
                            }
                        } catch (BadLocationException ex1) {
                            ex1.printStackTrace();
                        }
                    }
                }
                if( name != null )
                {
                    sequences.put( name , data.toString().trim() ) ;
                    logger.log( Level.FINE , "Loading \"" + name + "\" " + data.length());
                }
                data.setLength( 0 );
            }
            if( (this.compilationStartedAt > this.modifiedAt) && running )
            {

                if( sequences.size() != 0 )
                {
                    logger.log( Level.INFO , "Write sequences: " + sequences.size() ) ;
                    this.seq.setArray( getSequences( sequences ) ) ;
                    progress.setRangeProperties( 1 , 0 , 0 , 1 , true );
                    sequences.clear(); ;
                }
                try {
                    logger.log( Level.FINEST , "Sleeping" );
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                }
            }
        }
        logger.log( Level.INFO , "Stopped: " + this.getClass().getName() );
    }

    Object[] getSequences( java.util.HashMap sequences ) {
        com.bugaco.mioritic.impl.data.sequences.Sequence[] seqs = new com.bugaco.mioritic.impl.data.sequences.Sequence[ sequences.size() ] ;
        java.util.Iterator iter = sequences.keySet().iterator() ;
        String uipac = "\0ACMGRSVTWYHKDBN\0" ;
        String uipac_lowercase = uipac.toLowerCase() ;
        int i = 0 ;
        while( iter.hasNext() && seqs.length > i )
        {
            String name = (String)iter.next() ;
            String data = (String)sequences.get( name ) ;
            byte[] bytes = new byte[ data.length() ] ;
            int k = 0 ;
            for( int j = 0 ; j < data.length() ; j++ )
            {
                char c = data.charAt( j ) ;
                if( c == gapCharacter )
                {
                    bytes[k++] |= 16 ;
                }
                else if( c == noInfoCharacter )
                {
                    bytes[k++] |= 32 ;
                }
                else
                {
                    if( true ) {
                        int index = uipac.indexOf(c);
                        if (index != -1) {
                            bytes[k++] |= index;
                        }
                    }
                    if( true ) {
                        int index = uipac_lowercase.indexOf(c);
                        if (index != -1) {
                            bytes[k++] |= index;
                        }
                    }

                }
            }
            if( k != bytes.length )
            {
                byte[] bytes2 = bytes ;
                bytes = new byte[ k ] ;
                System.arraycopy( bytes2 , 0 , bytes , 0 , bytes.length );
            }
            seqs[ i ] = new com.bugaco.mioritic.impl.data.sequences.Sequence( name , bytes ) ;
            i++ ;
        }
        return seqs ;
    }

    void modified() {
        modifiedAt = System.currentTimeMillis() ;
    }

    class RawSequencesListener implements javax.swing.event.DocumentListener {
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
