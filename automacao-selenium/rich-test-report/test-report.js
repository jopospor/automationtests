$().ready(
		function() {
			// Faz a Requisição do Arquivo Json
			$.ajax( {
				url : "relatorio_de_testes.json",
				dataType : "json",
				async : false,
				success : function(data) {
					// Nome e Status da Suíte de Testes executada na div de topo
					$( "#reportTitle" ).append( getTestSuiteName( data ) ).append( getTestSuiteExecutionDate( data ) )
							.append( getTestSuiteElapsedTime( data ) ).append( getTestSuiteAssertsRan( data ) );
					$( data ).each( function(index) {
						$( "#test-results-accordion" )
					} );
					// Each para a Montagem dos Accordions dos test Cases
					$( data.testCases ).each( function(index) {
						createTest( this, index );
					} );
					// Configuração do plugin MultiAccordion
					$( "#box,.subAccordion" ).multiAccordion( {
						collapsible : true,
						active : false,
						autoHeight : false
					} )
					$( ".results-nav" ).removeClass( "jstree-leaf" );
					// Linka os Accordions ao Menu Lateral
					$( ".test-results-accordion" ).tabs();
					$( "#results-nav a" ).click( function() {
						var clicked = null;
						if ($( this ).hasClass( "jstree-clicked" )) {
							clicked = $( "#" + $( this ).data( "test" ) ).parents( ".test-item" );
						}
						$( ".test-item" ).not( ".ui-state-default" ).not( clicked ).each( function() {
							$( this ).trigger( "click" );
						} )
						// Scroll To
						$( "body" ).scrollTo( $( "#" + $( this ).data( "test" ) ).trigger( "click" ), 300 );
					} );
				},
			} );
		} );

getStatusFilter = function() {
	var statusFilter = [];
	if ($( "#checkbox-passed" ).hasClass( "active" )) {
		statusFilter.push( ".test-passed" );
	}
	if ($( "#checkbox-failed" ).hasClass( "active" )) {
		statusFilter.push( ".test-failed" );
	}
	return statusFilter;
}

// Monta as Tabs e Accordions

// Método para Mostrar o nome do teste e seu Status (inserido na div
// reportTitle)
getTestSuiteName = function(data) {
	return '<h2>' + data.testSuiteName + '</h2>';
}

getTestSuiteExecutionDate = function(data) {
	return 'Data da execução: ' + data.testSuiteExecutionDate + ' ';
}

getTestSuiteElapsedTime = function(data) {
	return 'Tempo de execução: ' + data.testSuiteElapsedTime + ' ';
}

getTestSuiteAssertsRan = function(data) {
	return data.testSuiteAssertsRan + ' asserções realizadas, ' + data.totalTestRuns + '.';
}

// Método para montar Test Cases no Menu e no Accordion
createTest = function(test, index, j) {
	var resultClass = ((test.testCaseStatus == "Failed") ? "test-failed"
			: (test.testCaseStatus == "Unstable" ? "test-unstable" : "test-passed"));
	var resultClassStyled = ((test.testCaseStatus == "Failed") ? "btn-danger test-failed"
			: (test.testCaseStatus == "Unstable" ? "btn-unstable test-unstable" : "btn-success test-passed"));
	index++;
	// Insere novo item no menu
	$( ".menu-treeview" ).append(
			'<li class="' + resultClass + '"><a href="" class="' + resultClassStyled + '"data-test="test-' + index
					+ '">' + index + ' - ' + test.testCaseName + '</a></li>' );
	// Insere teste no Accordion
	var testElement = $( '<h3 class="test-item ' + resultClassStyled + '" id="test-item-' + index + '"><a id="test-'
			+ index + '"class="' + resultClassStyled + '" >' + index + ' - ' + test.testCaseName + '</a></h3>' );
	var testRunsContainer = $( '<div class="test-container" id="test-' + index + '-container">'
			+ '<div class="test-results-accordion ">' + '<ul>' + '<li><a href="#test-' + index
			+ '-data" tab-data>Data</a></li>' + '<li><a class="tab-run "href="#test-' + index + '-run">Runs</a></li>'
			+ '</ul>' + '<div id="test-' + index + '-data">' + test.testCaseDataProvider + '</div>' + '<div id="test-'
			+ index + '-data">' + test.testCaseParameters + '</div>' + '<div class="subAccordion"id="test-' + index
			+ '-run"></div>' + '</div>' + '</div>' );
	$( test.run ).each( function() {
		var run = this;
		var testRunContainer = createTestRun( run, testRunsContainer );
		// Each para Montar os Accordions dos Steps
		$( run.steps ).each( function(j) {
			var step = this;
			createTestStep( step, j, testRunContainer );
		} )
	} );
	// insere o Conteúdo na Div com id Box
	$( "#box" ).append( testElement ).append( testRunsContainer );
};
// Método que insere as Runs
createTestRun = function(run, testRunsContainer) {
	var statusRun = (run.runStatus == "Failed") ? "btn-danger test-failed"
			: ((run.runStatus == "Unstable" ? "btn-unstable test-unstable" : "btn-success test-passed"));
	var testRunTitle = $( '<h3 class="sub-test-item ' + statusRun + '"><a class="' + statusRun + ' sub-test-item" >'
			+ run.rowNumber + '</a></h3>' );
	var testRunContainer = $( '<div class="test-run-container">' + run.testData + '</div>' );
	$( ".subAccordion", testRunsContainer ).append( testRunTitle ).append( testRunContainer );
	return testRunContainer;
};
// Método para criar os Steps das runs
createTestStep = function(step, j, testRunContainer) {
	var statusStep = (step.stepStatus == "Failed" || step.stepStatus == "CheckPoint Failed") ? "label-important"
			: "label-success";
	var testStepTitle = $( '<h3 id="steps-header" class="' + statusStep + '">' + (j + 1) + ' - ' + step.info
			+ '</a></h3>' );
	// Se ScreenCapture estiver definido, insira a opção screenshot
	if (typeof step.screenCapture != "undefined") {
		var rand = Math.floor( Math.random() * (9999999 - 1111111 + 1) ) + 1111111;
		var testStepContainer = $( '<a href="#myModal'
				+ rand
				+ '" role="button" class="btn" data-toggle="modal"><span class="icon icon-camera"></span> Screenshot</a>'
				+ '<div id="myModal' + rand + '" class="modal hide fade">' + '<div class="modal-header">'
				+ '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>'
				+ '<h3>' + step.info + '</h3>' + '</div>' + '<div class="modal-body">' + '<img src="'
				+ step.screenCapture + '">' + '</div>' + '<div class="modal-footer">'
				+ '<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>' + '</div>' + '</div>' );
	}
	$( testRunContainer ).append( testStepTitle ).append( testStepContainer );
	return testStepContainer;
};

$.extend( $.expr[":"], {
	"containsNC" : function(elem, i, match, array) {
		return (elem.textContent || elem.innerText || "").toLowerCase().indexOf( (match[3] || "").toLowerCase() ) >= 0;
	}
} );
