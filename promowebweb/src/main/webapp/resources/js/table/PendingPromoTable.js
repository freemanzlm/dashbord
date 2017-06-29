/* Define Exposure Table implementation.
 * 
 * @Description All configuration for exposure table are put here
 * 
 * @Dependency jquery.isloading.js
 * @Dependency DataTable
 * @Dependency Widget
 */
var BizReport = BizReport || {};

(function(namespace){
	var PendingPromoTable = function() {};
	PendingPromoTable.prototype = new cbt.Widget();
	
	var local = namespace.local, timezoneOffset = ((new Date()).getTimezoneOffset() - 420) * 60000 /*promotion time is beijing time, but server is in us -7*/;
	
	function getLink(promoId) {
		return "/promotion/" + promoId;
	}
	
	var defaultDataTableConfigs = {
			tableConfig : {
				'aaSorting': [[3, 'asc'], [2, 'asc']],
				'bAutoWidth': true,
				'bDeferRender': true,
				'bFilter': true,
				'bLengthChange': false,
				'bDestroy': true,
				'bServerSide': false,
				'bSortCellsTop': true,
				'bSort': true,
				'iDisplayLength': 10,
				'sPaginationType': 'full_numbers',
				'sDom': '<"datatable_header">t<"datatable_pager"p>',
				'oLanguage': {
					sEmptyTable: local.getText('dataTable.promo.emptyTable'),
					sInfo: local.getText('dataTable.promo.info'),
					sInfoFiltered: local.getText('dataTable.promo.infoFiltered'),
					sInfoEmpty: "",
					sLoadingRecords: local.getText('dataTable.loading'),
					sZeroRecords: local.getText('dataTable.promo.zeroRecords'),
					oPaginate: {
						sFirst: local.getText('dataTable.firstPage'),
						sLast: local.getText('dataTable.lastPage'),
						sPrevious: local.getText('dataTable.previousPage'),
						sNext: local.getText('dataTable.nextPage')
					}
				},
				sAjaxSource: "promotion/getIngPromotions", //"js/data/ongoing.json", 
				'fnServerParams': function(aoData){
					var settings = this.fnSettings(); 
					if (settings.aaSorting[0]) {
						aoData.push({name: "sSortCol", value: settings.aoColumns[settings.aaSorting[0][0]].mData});
						aoData.push({name: "sSortDir", value: settings.aaSorting[0][1]});
					}
				},
				"fnServerData": function ( sSource, aoData, fnCallback, oSettings ) {
				    oSettings.jqXHR = $.ajax( {
				        "dataType": 'json',
				        "type": "GET",
				        "url": sSource,
				        "data": aoData,
				        "success": function(json) {
				        	// stop update table if no data is gained.
				        	if (json.status != true || json.status != "ok") return;
				        	fnCallback(json);
				        }
				    });
				},
				columns: [
					{data: 'name'},
					{data: 'promoDlDt'},
					{data: 'promoEdt'},
					{data: 'state'}
				],
				aoColumnDefs: [{
					aTargets: ["name"],
					sDefaultContent: "",					
					sType: "string",
					sWidth: "350px",
					sClass: "item-title",
					mRender: function(data, type, full, meta) {
						if (type == "display") {
							return "<a href='" + getLink(full.promoId) + "'>" + data + "</a>";
						}
						
						return data;
					}
				},
				{
					aTargets: ["promoDlDt"],
					sType: "numeric",
					sClass: "text-center",
					sDefaultContent: " ",
					sWidth: "120px",
					mRender: function(data, type, full) {
						if (type == "display" && data) {
							return (new Date(data + timezoneOffset)).format("yyyy-MM-dd hh:mm");
						}
						
						if (type === 'sort') {
							return data ? data : -1;
						}
						
						return data;
					}
				},
				{
					aTargets: ["promoDt"],
					sType: "numeric",
					sClass: "text-center",
					sDefaultContent: "-",
					mRender: function(data, type, full) {
						if (type == "display") {
							return (full.promoSdt ? (new Date(full.promoSdt + timezoneOffset)).format("yyyy-MM-dd hh:mm") : '-') + " ~ " + (data ? (new Date(data + timezoneOffset)).format("yyyy-MM-dd hh:mm") : '-');
						}
						
						if (type === 'sort') {
							return data ? data : -1;
						}
						
						return data;
					}
				},
				{
					aTargets: ["state"],
					sClass: "text-center state",
					sDefaultContent: "",
					sType: 'numeric',
					sWidth: "120px",
					mRender: function(data, type, full) {
						if (type == "display") {
							return '<a href="' + getLink(full.promoId) + '" target="_blank">' + local.getText('promo.state.Detailed') + "</a>";
						}
						
						if (type == "sort") {
							return 20;
						}
						
						return data;
					}
				}] 
			}
		};
	
	$.extend(PendingPromoTable.prototype, {
		init: function(config) {
			var that = this;

			this.dataTableConfig = $.extend({}, defaultDataTableConfigs, config.dataTableConfig);

			this.dataTableConfig.tableConfig = $.extend({}, this.dataTableConfig.tableConfig, this.dataTableConfig.customTableConfig);
	
			this.config = $.extend({}, config);
			this.dataTable = new namespace.DataTable(this.dataTableConfig);
			
			// this statement must be put before this.dataTable.initDataTable();
			this.container = that.dataTable.table.parents(".dataTable-container:first");
			this.pane = this.container.parents(".pane-table");
			
			var oDataTable = this.oDataTable = null;
			
			this.dataTable.subscribe({
				initialized: function() {
					// get initialized DataTable instance
					that.oDataTable = oDataTable = this.table.DataTable();
				}, 
				ajaxbegin: function() {
					$(that.pane).isLoading({text: local.getText('dataTable.loading'), position: "inside"});
				},
				ajaxfinished: function(data) {
				    that.pane.isLoading('hide');

				    if (data && data.status) {
				        that.container.find(".datatable_pager").show();
				    } else {
				        that.container.find(".datatable_pager").hide();
				        that.dataTable.again && that.initDataTable();
				        that.dataTable.updateAgain();
				    }
				    
				    if (config.fnDataUpdatedCallback) {config.fnDataUpdatedCallback.call(that, data);}
				},
				error: function(data) {
				    that.pane.isLoading('hide');
				    that.dataTable.again && that.initDataTable();
			        that.dataTable.updateAgain();
//					namespace.cbt.alert(local.getText('dataTable.requestFail'));
				}
			}, this.dataTable);			
		},
		
		initDataTable: function() {
			this.dataTable.initDataTable();
		},
		
		update: function(param) {
			this.dataTable.update(param);
		},
		
		getDataSize: function() {
			return this.oDataTable.data().length;
		}
	});
	
	namespace.PendingPromoTable = PendingPromoTable;
})(BizReport = BizReport || {});