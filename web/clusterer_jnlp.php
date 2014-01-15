<?php
	header("Content-type: application/x-java-jnlp-file"); 
	header("Cache-Control: no-store, no-cache, must-revalidate");
	header("Expires: " . date( "F j, Y, g:i a" , time() + 60 ) ) ;
	header("Pragma: no-cache");
	header('Content-Disposition: attachment; filename="clusterer.jnlp"');

echo( '<?xml version="1.0" encoding="utf-8"?>' ) ;
?>
<jnlp spec="1.0+" codebase="http://www.bugaco.com/" href="/mioritic/clusterer_jnlp.php">
  <information>
    <title>Clusterer</title>
    <vendor>Bugaco.com</vendor>
    <homepage href="bioinf"/>
    <description>Clusterer long</description>
    <description kind="short">Clusterer short</description>
    <icon kind="shortcut" href="Mioritic.png"/>
    <icon kind="splash" href="/images/logo/bugaco_200x100_ti.gif"/>
	<shortcut online='true'>
		<desktop/>
	</shortcut>

  </information>
  <security>
<!--      <all-permissions/> -->
  </security>
  <resources>
  	  <j2se version="1.4+" max-heap-size="999m"/>
	  <jar href='/mioritic/mioritic_clusterer.jar'/>
	  <jar href='/mioritic/mioritic_ui.jar'/>
	  <jar href='/mioritic/jcommon-0.9.6.jar'/>
	  <jar href='/mioritic/jfreechart-0.9.21.jar' />
	  <jar href='/mioritic/l2fprod-common-all.jar' />
	  <jar href='/mioritic/rarefact.zip' />
  </resources>
  <application-desc 
	name="Clusterer"
	main-class="com.bugaco.mioritic.impl.module.project.Main"
	width=800
	height=600
	>
	<param name="report" value="http://www.bugaco.com/reports/report.php"/> 
	</application-desc>
</jnlp> 
