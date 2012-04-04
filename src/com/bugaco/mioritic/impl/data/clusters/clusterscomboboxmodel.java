package com.bugaco.mioritic.impl.data.clusters;

import javax.swing.event.ListDataListener;
import java.beans.PropertyChangeEvent;
import javax.swing.event.ListDataEvent;

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
public class ClustersComboBoxModel implements javax.swing.ComboBoxModel, java.beans.PropertyChangeListener {

    Object selectedItem = null ;
    java.util.HashSet listeners = new java.util.HashSet() ;
    java.util.ArrayList set = new java.util.ArrayList() ;
    com.bugaco.mioritic.model.data.clusters.Clusters clusters = null ;

    public ClustersComboBoxModel( com.bugaco.mioritic.model.data.clusters.Clusters c ) {
        this.clusters = c ;
        clusters.addPropertyChangeListener( this ) ;
    }

    public Object getSelectedItem() {
        return selectedItem ;
    }

    public void setSelectedItem(Object anItem) {
        selectedItem = anItem ;
    }

    public int getSize() {
        return set.size() ;
    }

    public Object getElementAt(int index) {
        return set.get( index ) ;
    }

    public void addListDataListener(ListDataListener l) {
        listeners.add( l ) ;
    }

    public void removeListDataListener(ListDataListener l) {
        listeners.remove( l ) ;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if( com.bugaco.mioritic.model.data.clusters.Clusters.CLEAR.equals( evt.getPropertyName() ) ) {
            set.clear();
            java.util.Iterator i = listeners.iterator() ;
            while( i.hasNext() )
            {
                ((ListDataListener) i.next()).intervalRemoved( new ListDataEvent( this , ListDataEvent.INTERVAL_REMOVED , 0 , clusters.size() )  );
            }
        } else if( set.contains( evt.getPropertyName() ) )
        {
            java.util.Iterator i = listeners.iterator() ;
            while( i.hasNext() )
            {
                int pos = Integer.parseInt( evt.getPropertyName() ) ;
                ((ListDataListener) i.next()).contentsChanged( new ListDataEvent( this , ListDataEvent.CONTENTS_CHANGED , pos , pos ) );
            }
        }
        else
        {
            set.add( evt.getPropertyName() ) ;
            java.util.Iterator i = listeners.iterator() ;
            while( i.hasNext() )
            {
                //int pos = Integer.parseInt( evt.getPropertyName() ) ;
                ((ListDataListener) i.next()).contentsChanged( new ListDataEvent( this , ListDataEvent.INTERVAL_ADDED , clusters.size() , clusters.size() ) );
            }

        }
    }

}
