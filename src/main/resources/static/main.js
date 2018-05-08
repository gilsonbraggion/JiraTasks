$(document).ready(function() {

	debugger;

	$(".campoData").mask("99/99/9999", {
		placeholder : "mm/dd/yyyy"
	});

	$("#selectBoard").change(function() {
		$.ajax({
			url : "http://localhost:8090/sprint/rest-service.guides.spring.io/greeting"
		}).then(function(data) {
			$('.greeting-id').append(data.id);
			$('.greeting-content').append(data.content);
		});

	});

});