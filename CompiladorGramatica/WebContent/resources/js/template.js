$(document).ready(function() {
	$(".file-chooser").on("change", function() {
		console.log($(this).val());
		if($(this).val()) {
			$(this).siblings(".btn").css({
				"color" : "#fff",
			    "background-color" : "#398439",
			    "border-color" : "#255625"
			});
		} else {
			$(this).siblings(".btn").css({
				"color" : "#fff",
			    "background-color" : "#337ab7",
			    "border-color" : "#2e6da4"
			});
		}
	});
});