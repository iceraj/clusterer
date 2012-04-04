package com.bugaco.mioritic.impl.data.distancematrix;

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
public class DistanceMatrixTableModel extends javax.swing.table.AbstractTableModel
{
    com.bugaco.mioritic.model.data.distancematrix.DistanceMatrix distanceMatrix = null ;

    public String getColumnName( int column )
    {
        if( distanceMatrix != null )
        {
            if( column == 0 )
            {
                return "" ;
            }
            else
            {
                return ((String[]) (distanceMatrix.getNames()))[column - 1];
            }
        }
        return null ;
    }

    public int getColumnCount() {
        if( distanceMatrix != null )
        {
            return distanceMatrix.getSize() + 1 ;
        }
        return 0;
    }

    public int getRowCount() {
        if( distanceMatrix != null )
        {
            return distanceMatrix.getSize() ;
        }
        return 0;
    }

    public Class getColumnClass( int columnIndex )
    {
        if( columnIndex == 0 )
        {
            return String.class ;
        }
        else
        {
            return Integer.class ;
        }

    }
    public Object getValueAt(int rowIndex, int columnIndex) {
        if( distanceMatrix != null )
        {
            if( columnIndex == 0 )
            {
                return getColumnName( rowIndex + 1 ) ;
            }
            return new Integer( distanceMatrix.get( rowIndex , columnIndex - 1 ) ) ;
        }
        return null ;
    }

    public void setDistanceMatrix( com.bugaco.mioritic.model.data.distancematrix.DistanceMatrix distMatrix )
    {
        this.distanceMatrix = distMatrix ;
    }

    public String getAsText()
    {
        DistanceMatrixTableModel dmtableModel = this ;
        StringBuffer sb = new StringBuffer();
        int cols = dmtableModel.getColumnCount();
        int rows = dmtableModel.getRowCount();
        for (int x = 0; x < cols; x++) {
            sb.append(dmtableModel.getColumnName(x));
            sb.append(',');
        }
        sb.append('\n');
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                sb.append(dmtableModel.getValueAt(y, x));
                sb.append(',');
            }
            sb.append('\n');
        }
        return sb.toString() ;
    }
    public DistanceMatrixTableModel() {
    }
}
