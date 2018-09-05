package com.bugaco.mioritic.project;

import java.io.*;

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
public class Project implements Serializable {
    private DistanceMatrix distanceMatrix;
    private RawSequences rawSequences;
    private SequencesArray sequencesArray;
    public Project() {
    }

    public RawSequences getRawSequences()
    {
        return rawSequences ;
    }
    public DistanceMatrix getDistanceMatrix()
    {
        return distanceMatrix ;
    }
    public SequencesArray getSequencesArray()
    {
        return sequencesArray ;
    }

    private void readObject(ObjectInputStream ois) throws IOException,
            ClassNotFoundException {
        ois.defaultReadObject();
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
    }

    public void setDistanceMatrix(DistanceMatrix distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }

    public void setRawSequences(RawSequences rawSequences) {
        this.rawSequences = rawSequences;
    }

    public void setSequencesArray(SequencesArray sequencesArray) {
        this.sequencesArray = sequencesArray;
    }
}
