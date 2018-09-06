package com.bugaco.mioritic.impl.data.multicluster;

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
public class MultiClusterTableModel extends javax.swing.table.AbstractTableModel {
    com.bugaco.mioritic.model.data.clusters.Clusters clusters = null ;
    com.bugaco.mioritic.model.data.distancematrix.DistanceMatrix distanceMatrix = null ;
    MySequenceObject[] sequenceObjects = null ;
    int columns = 0 ;

    MyClusterListener clusterListener = new MyClusterListener() ;
    MyDistaneMatrixListener distanceMatrixListener = new MyDistaneMatrixListener() ;

    public MultiClusterTableModel() {
    }

    public void setClusters( com.bugaco.mioritic.model.data.clusters.Clusters c )
    {
        if( clusters != null )
        {
            clusters.removePropertyChangeListener( clusterListener );
        }
        this.clusters = c ;
        if( clusters != null )
        {
            clusters.addPropertyChangeListener( clusterListener );
        }
    }

    public void setDistanceMatrix( com.bugaco.mioritic.model.data.distancematrix.DistanceMatrix dm )
    {
        if( this.distanceMatrix != null )
        {
            distanceMatrix.removePropertyChangeListener( distanceMatrixListener );
        }
        this.distanceMatrix = dm ;
        if( this.distanceMatrix != null )
        {
            distanceMatrix.addPropertyChangeListener( distanceMatrixListener );
        }
    }

    public String getColumnName( int index )
    {
        if( index == 0 )
        {
            return "Sequence" ;
        }
        else
        {
            return Integer.toString( columns - index ) ;
        }
    }
    public int getColumnCount() {
        return columns + 1 ;
    }

    public int getRowCount() {
        if( sequenceObjects != null )
        {
            return sequenceObjects.length ;
        }
        else
        {
            return 0;
        }
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if( columnIndex == 0 )
        {
            return sequenceObjects[ rowIndex ].getName() ;
        }
        else
        {
            return new Integer( sequenceObjects[ rowIndex ].data[ columns - columnIndex ] ) ;
        }
    }

    public void compile( com.bugaco.mioritic.model.misc.ProgressModel progress )
    {
        try
        {
            init(progress);
            sort();
            fireTableStructureChanged();
        }
        catch( Exception e )
        {
            columns = 0 ;
            sequenceObjects = null ;
            fireTableStructureChanged();
        }
    }

    void sort()
    {
        java.util.Arrays.sort( sequenceObjects , new java.util.Comparator(){
            public boolean equals(Object obj) {
                return false;
            }

            public int compare(Object o1, Object o2) {
                MySequenceObject obj1 = (MySequenceObject) o1 ;
                MySequenceObject obj2 = (MySequenceObject) o2 ;
                for( int j = 0 ; j < obj1.data.length ; j++ )
                {
                    int i = obj1.data.length - j - 1 ;
                    if( obj1.data[ i ] > obj2.data[ i ] )
                    {
                        System.out.println( obj1.getName() + ">" + obj2.getName() + " " + obj1.data[ i ] + " " + obj2.data[ i ] + " " + i ) ;
                        return 1 ;
                    }
                    else if( obj1.data[ i ] < obj2.data[ i ] )
                    {
                        System.out.println( obj1.getName() + ">" + obj2.getName() + " " + obj1.data[ i ] + " " + obj2.data[ i ] + " " + i ) ;
                        return -1 ;
                    }
                }
                System.out.println( obj1.getName() + "=" + obj2.getName() ) ;
                return 0;
            }
        }
        );
    }

    void init( com.bugaco.mioritic.model.misc.ProgressModel progress )
    {
        if( distanceMatrix != null )
        {

            String[] names = distanceMatrix.getNames() ;
            if( names != null )
            {
                progress.setRangeProperties( 0 , 0 , 0 , names.length , true );
                sequenceObjects = new MySequenceObject[names.length];
                java.util.Map map = new java.util.TreeMap();
                for (int i = 0; i < sequenceObjects.length; i++) {
                    sequenceObjects[i] = new MySequenceObject();
                    sequenceObjects[i].setName(names[i]);
                    map.put(names[i], new Integer(i));
                }
                if (clusters != null) {
                    columns = clusters.size() ;
                    progress.setRangeProperties( 0 , 0 , 0 , columns , true );
                    for (int i = 0; i < clusters.size(); i++) {
                        progress.setValue( i );
                        java.util.TreeMap map2 = clusters.get(i);
                        int index = 0;
                        java.util.Iterator iter = map2.keySet().iterator();
                        while (iter.hasNext()) {
                            Object key = iter.next();
                            Object value = map2.get(key);
                            index++;
                            if (value instanceof java.util.Set) {
                                java.util.Iterator iter2 = ((java.util.Set)
                                        value).iterator();
                                while (iter2.hasNext()) {
                                    Object key2 = iter2.next();
                                    sequenceObjects[((Integer) map.get(key2.
                                            toString())).intValue()].setData(i,
                                            index);
                                }
                            } else {
                                sequenceObjects[((Integer) map.get(key.toString())).
                                        intValue()].setData(i, index);
                            }
                        }
                    }
                }
                else
                {
                    columns = 0 ;
                }
            }
        }
        else
        {
            sequenceObjects = null ;
        }
    }

    class MyClusterListener implements java.beans.PropertyChangeListener
    {
        public void propertyChange(PropertyChangeEvent evt) {
        }

    }

    class MyDistaneMatrixListener implements java.beans.PropertyChangeListener
    {
        public void propertyChange(PropertyChangeEvent evt) {
        }

    }

    class MySequenceObject
    {
        int MAX_LEN = 2048 ;
        public String name = null ;
        public int[] data = new int[ MAX_LEN ] ;

        public String getName()
        {
            return name ;
        }

        public void setName( String name )
        {
            this.name = name ;
        }

        public void setData( int index , int value )
        {
            data[ index ] = value ;
        }

        public int getData( int index )
        {
            return data[ index ] ;
        }
    }



}
