$(function(){
	$(".file-input").each(function(){
		if (this.hasAttribute("inited")) return;
		
		var fileBox = $(this), textInput = fileBox.find("[type=text]");
		var fileInput = fileBox.find("[type=file]").change(function(){
			textInput.attr("value", this.value);
		}).width(textInput.width());
		fileBox.find(".btn").click(function(){
			fileInput.trigger("click");
		});
		this.setAttribute('inited', true);
	});
});