 EGD.Model.GetTable = Backbone.Model.extend({

	// url:"../../json/transactions.json",
	url : function() {
		return "http://127.0.0.1:8080/api/getegdmessage";
	},
	parse:function(response,options){
		return response.data;
	}
	
});
// EGD.model.GetTable = new EGD.Model.GetTable({},{parse:true});
EGD.model.GetTable = new EGD.Model.GetTable();
