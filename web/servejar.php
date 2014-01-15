<?

	$r = fopen( "/kunden/homepages/43/d88955110/htdocs/bugaco/mioritic/log.txt" , "a+" ) ;
	foreach( $_SERVER as $k => $v )
	{
		fwrite( $r , "SERVER: " . $k . "=" . $v . "\n" ) ;
	}

	foreach( $_GET as $k => $v )
	{
		fwrite( $r , "GET: " . $k . "=" . $v . "\n" ) ;
	}

	foreach( $_POST as $k => $v )
	{
		fwrite( $r , "GET: " . $k . "=" . $v . "\n" ) ;
	}
	fwrite( $r , "\n\n" ) ;

	fclose( $r ) ;
	$file = $_SERVER[ 'PATH_INFO' ] ;
	$encoding = $_SERVER[ 'HTTP_ACCEPT_ENCODING' ] ;
	$version = $_GET[ 'version-id' ] ;

	if( preg_match( "/\/[^\/]*\.jar$/" , $file ) )
	{
		#header( "Content-Type: application/x-java-jnlp-file" ) ;
		#header( "x-java-jnlp-version-id: " . $version ) ;
		header( "Content-Type: application/x-java-archive" ) ;
		#header( "x-java-archive-version-id: " . $version ) ;
		readfile( "." . $file ) ;
	}
	else
	{
		header( "Content-Type: text/html" ) ;
		print( "File not found: $file" ) ;
	}
?>
