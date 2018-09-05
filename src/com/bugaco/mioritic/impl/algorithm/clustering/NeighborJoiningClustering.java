package com.bugaco.mioritic.impl.algorithm.clustering;

import com.bugaco.mioritic.model.algorithm.clustering.ClusterCompiler;
import com.bugaco.mioritic.model.data.distancematrix.DistanceMatrix;
import com.bugaco.mioritic.model.data.clusters.Clusters;
import com.bugaco.mioritic.model.misc.ProgressModel;
import java.beans.BeanInfo;
import java.util.logging.Logger;
import java.util.logging.Level;
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
public class NeighborJoiningClustering implements ClusterCompiler,
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

    NeighborJoiningClustering compiler = this;
    java.beans.PropertyChangeListener listener = null;

    public NeighborJoiningClustering() {
        compiler = this;
        logger = //new com.bugaco.mioritic.impl.misc.Logger( this.getClass().getName() ) ;
                java.util.logging.Logger.getLogger(this.getClass().getName());
        //logger.setLevel(Level.ALL);
        listener = new MyListener();

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

    void modified() {
        modifiedAt = System.currentTimeMillis() ;
    }

    boolean buildCluster(int treshold , ProgressModel progress ) {
        logger.info("Running compler for " + treshold);
        try
        {
            long compilationStartedAt = System.currentTimeMillis();
            int size = distanceMatrix.getSize();
            String[] names = distanceMatrix.getNames();
            int currentGroup = 1;
            int[] group = new int[size];
            int[] group_name = new int[size];

            progress.setRangeProperties(0, 1, 0, size * 2, true);
            for (int i = 0; i < group.length; i++) {
                group[i] = 0;
                group_name[i] = 0;
            }
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
                            currentGroup++;
                            logger.info( "DM" + distanceMatrix.get( x , y ) ) ;
                        } else if (group[x] != 0 && group[y] == 0) {
                            group[y] = group[x];
                        } else if (group[x] == 0 && group[y] != 0) {
                            group[x] = group[y];
                        } else if (group[x] != 0 && group[y] != 0) {
                            for (int i = 0; i < group.length; i++) {
                                int modify = group[y];
                                if (group[i] == modify) {
                                    group[i] = group[x];
                                }
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
            return false ;
        }
    }


    public void run() {
        
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
	        	logger.info("Running compler " );
	            if(! skip )
	            {
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
	                    logger.fine("Sleeping");
	                    Thread.sleep(3000);
	                } catch (InterruptedException ex) {
	                }
	                if( !running ) { continue ; }
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
            modified() ;
            this.distanceMatrix.removePropertyChangeListener(listener);
        }
        running = false;
        modified();
    }

    class MyListener implements java.beans.PropertyChangeListener {
        public void propertyChange(PropertyChangeEvent evt) {
            logger.info("NJC " + evt);
            modified();
        }

    }
}
