$( function() {
	// TO CREATE AN INSTANCE
	// select the tree container using jQuery
	$( "#results-nav" )
	// call `.jstree` with the options object
	.jstree( {
		// the `plugins` array allows you to configure the active plugins on
		// this instance
		"plugins" : [ "themes", "html_data", "ui", "crrm", "hotkeys" ],
		// each plugin you have included can have its own config object
		"core" : {
			"initially_open" : [ "phtml_1" ]
		}

	// it makes sense to configure a plugin only if overriding the defaults
	} )
} );
