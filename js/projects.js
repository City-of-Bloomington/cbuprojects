clicked_button_id="";
var icons = {
    header:"ui-icon-circle-plus",
    activeHeader:"ui-icon-circle-minus"
};

$(".date").datepicker({
    nextText: "Next",
    prevText:"Prev",
    buttonText: "Pick Date",
    showOn: "both",
    navigationAsDateFormat: true,
    buttonImage: "/cbuprojects/js/calendar.gif"
});

function projectValidate(){
		var xx = document.getElementById("project_name");
		if(xx && xx.value.trim() == ""){
				alert("Project Name is required");
				xx.focus();
				return false;
		}
		xx = document.getElementById("project_type_id");
		if(xx){
				var yy = xx.value;
				if(yy == "-1"){
						alert("Project type is required");
						xx.focus();
						return false;
				}
		}
		xx = document.getElementById("phase_rank_id");
		if(xx){
				var yy = xx.value;
				if(yy == "-1"){
						xx.focus();
						alert("Phase rank is required");
						return false;
				}
		}
		return true;
}
$("#selection_id").change(function() {
		$("#type_id").val("");
		$("#type_name_id").val("");
    $("#form_id").submit();
});
function doRefresh(){
		document.getElementById("action2").value="Refresh";
		document.getElementById("form_id").submit();
}
function confirmDelete(){
	var x = confirm("Are you sure you want to delete this record");
	if(x){
		document.getElementById("action2").value="Delete";
		document.getElementById("form_id").submit();
		return true;
	}
	return false;
}
