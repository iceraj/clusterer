package com.bugaco.ui.models.impl;

import java.awt.event.ItemListener;
import java.util.* ;
import java.awt.event.ItemEvent;

/**
 * <p>Title: Mioritic</p>
 *
 * <p>Description: Clusterer 3</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: bugaco</p>
 *
 * @author Ivica Ceraj
 * @version 1.0
 */
public class DefaultAlgorithmModel extends javax.swing.DefaultComboBoxModel
implements com.bugaco.ui.models.AlgorithmModel
{
    String text ;
    HashSet itemListeners = new HashSet() ;

    public DefaultAlgorithmModel() {
    }

    public Object getSelectedAlgorithm()
    {
        Object o = getSelectedItem() ;
        if( o instanceof DefaultAlgorithmItem )
        {
            return ((DefaultAlgorithmItem)o).getAlgorithmClass() ;
        }
        else
        {
            return getSelectedItem();
        }
    }

    public String getText()
    {
        return text ;
    }

    public void setText( String text )
    {
        this.text = text ;
    }

    public void setSelectedItem( Object anItem )
    {
        fireItemChanged( getSelectedItem() , java.awt.event.ItemEvent.DESELECTED ) ;
        super.setSelectedItem( anItem ) ;
        fireItemChanged( getSelectedItem() , java.awt.event.ItemEvent.SELECTED ) ;
    }

    protected void fireItemChanged( Object item , int state )
    {
        ItemEvent e = new ItemEvent( this , 0 , item , state ) ;
        synchronized( itemListeners )
        {
            Iterator iter = itemListeners.iterator() ;
            while( iter.hasNext() )
            {
                ItemListener l = (ItemListener) iter.next() ;
                l.itemStateChanged( e ) ;
            }
        }
    }
    public void addItemListener(ItemListener l) {
        itemListeners.add( l ) ;
    }

    public void removeItemListener(ItemListener l) {
        itemListeners.remove( l ) ;
    }

    public Object[] getSelectedObjects() {
        Object item = getSelectedItem() ;
        Object[] ret = null ;
        if( item != null )
        {
            ret = new Object[ 1 ] ;
            ret[ 0 ] = item ;
        }
        return ret ;
    }
    
    public void setItemsFromProperties( java.util.Properties prop )
    {
    	removeAllElements() ;
    	Enumeration e = prop.keys() ;
    	while( e.hasMoreElements() )
    	{
    		String clazz = (String) e.nextElement() ;
    		String label = prop.getProperty( clazz ) ;
    		addElement( new com.bugaco.ui.models.impl.DefaultAlgorithmItem( label , clazz ) ) ;
    	}
    }


}
