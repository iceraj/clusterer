set CLASSPATH=mioritic_clusterer.jar;mioritic_ui.jar;jcommon-0.9.6.jar;jfreechart-0.9.21.jar;l2fprod-common-all.jar

xcopy /y \iceraj\jbuilder\mioritic2\*.jar .
@rem c:\java\jdk\bin\java com.bugaco.mioritic.impl.module.project.Project
@rem c:\java\jdk1.5.0\bin\pack200 -g 
c:\java\jdk1.5.0\bin\javaws http://localhost/mioritic/clusterer_jnlp.php
