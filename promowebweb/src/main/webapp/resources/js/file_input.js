$(function(){
	$(".file-input").each(function(){
		var fileBox = $(this), textInput = fileBox.find("[type=text]");
		fileBox.find("[type=file]").change(function(){
			textInput.attr("value", this.value);
		}).width(textInput.width());
	});
});