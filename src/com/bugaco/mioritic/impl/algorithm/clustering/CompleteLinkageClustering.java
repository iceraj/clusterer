package com.bugaco.mioritic.impl.algorithm.clustering;

import com.bugaco.mioritic.model.algorithm.clustering.ClusterCompiler;
import java.util.logging.Logger;
import java.beans.BeanInfo;
import com.bugaco.mioritic.model.data.distancematrix.DistanceMatrix;
import com.bugaco.mioritic.model.misc.ProgressModel;
import com.bugaco.mioritic.model.data.clusters.Clusters;
import java.beans.PropertyChangeEvent;
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
public class CompleteLinkageClustering implements ClusterCompiler,
        java.lang.Runnable {

    Logger logger = null;
    Thread thread = null;
    BeanInfo bean = null;

    int treshold = 0;

    DistanceMatrix distanceMatrix = null;
    Clusters clusters = null;
    ProgressModel progress = null;

    long modifiedAt = 0;
    long compilationStartedAt = 0;

    boolean running = false;

    CompleteLinkageClustering compiler = this;
    java.beans.PropertyChangeListener listener = null;

    public CompleteLinkageClustering() {
    compiler = this;
    logger = java.util.logging.Logger.getLogger(this.getClass().getName());
    listener = new MyListener();

    }

    int currentGroup = 1;
    int[] group = null ;
    int[] group_name = null ;
    java.util.Set[] groups = null ;

    void buildClusterInit()
    {
        int size = distanceMatrix.getSize();
        group = new int[size];
        group_name = new int[size];
        groups = new java.util.Set[size] ;
        for (int i = 0; i < group.length; i++) {
            group[i] = 0;
        }
    }

    boolean buildCluster(int treshold , ProgressModel progress ) {
    logger.info("Running compler for " + treshold);
    try
    {
        long compilationStartedAt = System.currentTimeMillis();
        int size = distanceMatrix.getSize();
        String[] names = distanceMatrix.getNames();
        group_name = new int[size];
        for (int i = 0; i < group_name.length; i++) {
            group_name[i] = 0;
        }

        progress.setRangeProperties(0, 1, 0, size * 2, true);
        if (!running || (compilationStartedAt <= this.modifiedAt)) {
            return false;
        }

        for (int x = 0; x < size; x++) {
            progress.setValue(progress.getValue() + 1);
            for (int y = 0; y < x; y++) {
                if (!running || (compilationStartedAt <= this.modifiedAt)) {
                    return false;
                }
                if (group[x] == group[y] && group[x] != 0) {
                    continue;
                }

                if (distanceMatrix.get(x, y) <= treshold) {
                    if (group[x] == 0 && group[y] == 0) {
                        group[x] = currentGroup;
                        group[y] = currentGroup;

                        groups[ currentGroup ] = new java.util.HashSet() ;
                        groups[ currentGroup ].add( new Integer( x ) ) ;
                        groups[ currentGroup ].add( new Integer( y ) ) ;

                        currentGroup++;

                    } else if (group[x] != 0 && group[y] == 0) {
                        boolean add = true ;
                        if(! ( groups[ group[x] ].size() == 1 ) )
                        {
                            java.util.Iterator iter = groups[ group[x] ].iterator() ;
                            while( iter.hasNext() )
                            {
                                int elem = ((Integer)iter.next()).intValue() ;
                                if(! ( distanceMatrix.get( elem , y ) <= treshold ) )
                                {
                                    add = false ;
                                    break ;
                                }
                            }
                        }
                        if( add )
                        {
                            group[y] = group[x];
                            groups[group[x]].add(new Integer(y));
                        }
                    } else if (group[x] == 0 && group[y] != 0) {
                        boolean add = true ;
                        if(! ( groups[ group[y] ].size() == 1 ) )
                        {
                            java.util.Iterator iter = groups[ group[y] ].iterator() ;
                            while( iter.hasNext() )
                            {
                                int elem = ((Integer)iter.next()).intValue() ;
                                if(! ( distanceMatrix.get( elem , x ) <= treshold ) )
                                {
                                    add = false ;
                                    break ;
                                }
                            }
                        }
                        if( add )
                        {
                            group[x] = group[y];
                            groups[group[y]].add(new Integer(x));
                        }
                    } else if (group[x] != 0 && group[y] != 0) {
                        boolean add = true ;
                        if( groups[ group[x] ].size() != 1 && groups[ group[y] ].size() != 1 )
                        {
                            java.util.Iterator iterY = groups[ group[y] ].iterator() ;
                            while( iterY.hasNext() )
                            {
                                int elemY = ((Integer) iterY.next()).intValue();
                                java.util.Iterator iterX = groups[ group[x] ].iterator();
                                while (iterX.hasNext()) {
                                    int elemX = ((Integer) iterX.next()).intValue();
                                    if (!(distanceMatrix.get(elemX, elemY) <= treshold)) {
                                        add = false;
                                        break;
                                    }
                                }
                                if(! add )
                                {
                                    break ;
                                }
                            }
                        }

                        if( add )
                        {
                            int modify = group[y];
                            for (int i = 0; i < group.length; i++) {
                                if (group[i] == modify) {
                                    group[i] = group[x];
                                }
                            }
                            groups[ group[ x ] ].addAll( groups[ modify ] ) ;
                            groups[ modify ] = null ;
                        }
                    }
                }
            }
        }
        java.util.TreeMap map = new java.util.TreeMap();
        int currentCluster = 1;
        java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
        nf.setMaximumIntegerDigits(6);
        nf.setMinimumIntegerDigits(6);
        nf.setGroupingUsed(false);
        for (int i = 0; i < group.length; i++) {
            progress.setValue(progress.getValue() + 1);
            
            if (!running || (compilationStartedAt <= this.modifiedAt)) {
                return false;
            }

            if (group[i] == 0) {
                map.put(names[i], new Integer(i));
            } else {
                if (group_name[group[i]] == 0) {
                    group_name[group[i]] = currentCluster;
                    map.put("Cluster_" + nf.format(currentCluster),
                            new java.util.TreeSet());
                    currentCluster++;
                }

                java.util.Set mySet = (java.util.Set) map.get(
                        "Cluster_" + nf.format(group_name[group[i]]));
                mySet.add(names[i]);
            }
        }
        if (!running || (compilationStartedAt <= this.modifiedAt)) {
            return false;
        }
        clusters.put(treshold, map);
        return true;
    }
    catch( Exception e )
    {
        e.printStackTrace();
        return false ;
    }
}



    public void run() {
        logger.info("Running compler");
        running = true;
        while( running && clusters == null )
        {
            try
            {
            Thread.sleep( 500 ) ;
            }
            catch( Exception e )
            {
                e.printStackTrace();
            }
        }
        if( clusters != null )
        {
	        clusters.clear();
	        boolean skip = false ;
	        ProgressModel myprogress = new com.bugaco.mioritic.impl.misc.ProgressModel() ;
	        this.compilationStartedAt = System.currentTimeMillis();
	        while (running) {
	            if(! skip )
	            {
	                if( treshold == 0 )
	                {
	                    buildClusterInit() ;
	                }
	                if( !running ) { continue ; }
	                buildCluster(treshold , myprogress ) ;
	                if( !running ) { continue ; }
	                java.util.Map map = clusters.get( treshold );
	                if( map != null )
	                {
	                    progress.setRangeProperties(distanceMatrix.getSize() - map.size() , 1 , 0 , distanceMatrix.getSize() , true );
	                }
	                if (map == null || map.size() == 0 || map.size() == 1 ) {
	                    skip = true;
	                }
	                treshold++;
	
	            }
	            else
	            {
	            	if( !running ) { continue ; }
	                int last_size = distanceMatrix.getSize() ;
	                try {
	                    progress.setRangeProperties(1 , 1 , 0 , 1 , true );
	                    logger.info("Sleeping");
	                    Thread.sleep(3000);
	                } catch (InterruptedException ex) {
	                }
	                if( last_size != distanceMatrix.getSize() )
	                {
	                    skip = false ;
	                }
	
	            }
	            if( !running ) { continue ; }
	            if (compilationStartedAt <= this.modifiedAt) {
	                this.compilationStartedAt = System.currentTimeMillis();
	                clusters.clear();
	                treshold = 0;
	                skip = false ;
	            }
	        }
        }
        logger.info("Running finished");
    }

    public void setDistanceMatrix(DistanceMatrix distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
        modified() ;
    }

    public void setClusters(Clusters clusters) {
        this.clusters = clusters;
        modified() ;
    }

    public void setProgress(ProgressModel progress) {
        this.progress = progress;
    }

    public BeanInfo getBeanInfo() {
        return null;
    }

    public void start() {
        logger.log(Level.INFO, "Starting" + this.distanceMatrix);
        if (this.distanceMatrix != null) {
            modified() ;
            this.distanceMatrix.removePropertyChangeListener(listener);
            this.distanceMatrix.addPropertyChangeListener(listener);
            thread = new Thread(this);
            thread.start();
        }
    }

    public void stop() {
        logger.log(Level.INFO, "Stopping");
        if (this.distanceMatrix != null) {
            this.distanceMatrix.removePropertyChangeListener(listener);
        }
        running = false;
        modified();
    }


    void modified() {
        modifiedAt = System.currentTimeMillis() ;
    }

    class MyListener implements java.beans.PropertyChangeListener {
    public void propertyChange(PropertyChangeEvent evt) {
        logger.info("NJC " + evt);
        modified();
    }

}

}
