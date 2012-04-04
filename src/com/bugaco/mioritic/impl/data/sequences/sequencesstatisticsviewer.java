package com.bugaco.mioritic.impl.data.sequences;

import javax.swing.JLabel;
import javax.swing.event.ListDataEvent;
import javax.swing.JList;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;

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
public class SequencesStatisticsViewer extends javax.swing.JPanel {
    com.bugaco.mioritic.model.data.sequences.Sequences seq = null ;
    JLabel sequencesCounter = new JLabel();
    JLabel sequencesStat = new JLabel() ;
    SequenceChangeListener listener = null ;
    JList jList1 = new JList();
    JPanel jPanel1 = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    JScrollPane jScrollPane1 = new JScrollPane();

    double avgLength = Double.NaN ;
    public double getAvgLength()
    {
        return avgLength ;
    }

    public SequencesStatisticsViewer() {
        listener = new SequenceChangeListener();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setSequences( com.bugaco.mioritic.model.data.sequences.Sequences seq )
    {
        if( seq != null )
        {
            seq.removeListDataListener( listener );
        }
        this.seq = seq ;
        jList1.setModel( seq );
        if( seq != null )
        {
            seq.addListDataListener( listener );
        }

    }

    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        JLabel jLabel1 = new JLabel("Number of sequences: ");
        sequencesCounter.setText("0");
        jList1.setVisibleRowCount(8);
        this.setInputVerifier(null);
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel("Min/max/avg sequence length: ");
        sequencesStat = new javax.swing.JLabel("NA/NA/NA");

        jPanel1.setLayout( new java.awt.GridLayout( 3 , 2 ) ) ;
        jPanel1.add(jLabel1);
        jPanel1.add(sequencesCounter);
        jPanel1.add(jLabel2);
        jPanel1.add(sequencesStat);
        jPanel1.add( new javax.swing.JLabel( "List of sequences:" ) ) ;
        this.add(jPanel1, java.awt.BorderLayout.NORTH);
        this.add(jScrollPane1, java.awt.BorderLayout.CENTER);
        jScrollPane1.getViewport().add(jList1);
        jScrollPane1.getViewport().setPreferredSize( new java.awt.Dimension( 100 , 50 ) );
    }

    boolean showWarning = false ;
    void updateSequenceView()
    {
        sequencesCounter.setText( String.valueOf( seq.getSize() ) );
        int min = Integer.MAX_VALUE ;
        int max = Integer.MIN_VALUE ;
        int sum = 0 ;
        int size = seq.getSize() ;
        for( int i = 0 ; i < seq.getSize() ; i++ )
        {
            com.bugaco.mioritic.model.data.sequences.Sequence s =  (com.bugaco.mioritic.model.data.sequences.Sequence) seq.getElementAt( i ) ;
            if( s != null && s.getData() != null )
            {
                int l = s.getData().length ;
                min = l < min ? l : min ;
                max = l > max ? l : max ;
                sum += l ;
            }
        }
        if( size != 0 )
        {
            sequencesStat.setText(min + "/" + max + "/" + ((float) sum / size));
            avgLength = ((double) sum / size) ;
        }
        else
        {
            sequencesStat.setText( "NA/NA/NA" ) ;
            avgLength = Double.NaN ;
        }

        if( ( min != Integer.MAX_VALUE ) && ( min != max ) && ( size != 0 ) )
        {
            sequencesStat.setText( sequencesStat.getText() + " (mouse over to see note!) " );
            sequencesStat.setToolTipText(
                    "<html><body><p>Importing non-aligned sequences is not supported<br> and may produce inaccurate results.</p></body></html>"
                    ) ;
            sequencesStat.setForeground( java.awt.Color.red );
        }
        else
        {
            sequencesStat.setToolTipText( "" ) ;
            sequencesStat.setForeground( java.awt.Color.black );

        }
    }

    class SequenceChangeListener implements javax.swing.event.ListDataListener
    {
        public void contentsChanged(ListDataEvent e) {
            updateSequenceView() ;
        }

        public void intervalAdded(ListDataEvent e) {
            updateSequenceView() ;
        }

        public void intervalRemoved(ListDataEvent e) {
            updateSequenceView() ;
        }

    }

}
