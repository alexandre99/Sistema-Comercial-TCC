$(function() {
	$("#dialogStatus").dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		closeOnEscape : false
	});
});

function abrirDialog() {
	$("#dialogStatus").dialog("open");
};

function fecharDialog() {
	$("#dialogStatus").dialog("close");
};