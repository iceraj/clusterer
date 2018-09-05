#!/bin/bash
java -Dfile.encoding=UTF-8 -classpath target/production/clusterer:web/jcommon-0.9.6.jar:web/jfreechart-0.9.21.jar:web/l2fprod-common-all.jar:web/rarefact.zip com.bugaco.mioritic.impl.module.project.Main


