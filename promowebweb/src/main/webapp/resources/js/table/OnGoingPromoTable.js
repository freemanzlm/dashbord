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
	var OnGoingPromoTable = function() {};
	OnGoingPromoTable.prototype = new namespace.Widget();
	
	var local = namespace.local;
	
	var promos = ['hotsell', 'deals', 'deals', 'other'];
	
	function getLink(promoId) {
		return "/promotion/" + promoId;
	}
	
	var defaultDataTableConfigs = {
			tableConfig : {
				'aLengthMenu': [20],
				'aaSorting': [[4, 'asc'], [3, 'asc']],
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
				'sDom': '<"datatable_header">t<"datatable_pager"ip>',
				'oLanguage': {
					sEmptyTable: local.getText('dataTable.promo.emptyTable'),
					sInfoFiltered: local.getText('dataTable.promo.infoFiltered'),
					sInfo: local.getText('dataTable.promo.info'),
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
//				'sScrollX': "100%",
				sAjaxSource: "promotion/getIngPromotions", //"js/data/ongoing.json", 
//				sAjaxSource: "js/data/ongoing.json",
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
					{data: 'type'},
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
					aTargets: ["type"],
					sClass: "text-center",
					sDefaultContent: "",
					mRender: function(data, type, full) {
						if (type == "display") {
							return local.getText('promo.type.' + promos[data]); 
						}
						
						if (type == 'sort' || type == 'filter') {
							if (data == '2') {
								return 1;
							}
						}
						
						return data;
					}
				},
				{
					aTargets: ["promoDlDt"],
					sType: "date",
					sClass: "text-center",
					sWidth: "120px",
					sDefaultContent: " ",
					mRender: function(data, type, full) {
						return data;
					}
				},
				{
					aTargets: ["promoDt"],
					sType: "date",
					sClass: "text-center",
					sWidth: "200px",
					sDefaultContent: "-",
					mRender: function(data, type, full) {
						if (type == "display") {
							return full.promoSdt + " ~ " + data;
						}
						return data;
					}
				},
				{
					aTargets: ["reward"],
					bSortable: false,
					sClass: "text-right",
					sDefaultContent: "-",
					mRender: function(data, type, full) {
						var val = parseFloat(data);
						if (type == "display") {
							if (full.rewardType != 0) {
								if (full.state > 7) { // After subsidy counting
									if (val > 0) {
										return val.toUSFixed(2) + " (" + full.currency + ")";
									}
								}  else {
									return local.getText('dataTable.promo.SubsidyCounting');
								}
							}
							
							return local.getText('dataTable.promo.noReward');
						}
						
						if (type == "sort") {
							return isNaN(val) ? -1 : val;
						}

						return data;
					}
				},
				{
					aTargets: ["state"],
					sClass: "text-center state",
					sDefaultContent: "",
					sType: 'numeric',
					mRender: function(data, type, full) {
						if (type == "display") {
							if (full.type != 3) {
								switch (data) {
								case 'Created':
								case 'PromotionApproved':
									return "<a class='btn' href='" + getLink(full.promoId) + "'>" + local.getText('promo.state.' + data) + "</a>";
								case 'Applied':
								case 'Verifying':
								case 'Submitted':
								case 'Started':
								case 'SubsidyCounting':
									return local.getText('promo.state.' + data) + "<br/>" + "<a href='" + getLink(full.promoId) + "'>" + local.getText('promo.state.Detailed') + "</a>";
								}
							} else {
								switch (data) {
								case 'Started':
								case 'SubsidyCounting':
									return local.getText('promo.state.' + data) + "<br/>" + "<a href='" + getLink(full.promoId) + "'>" + local.getText('promo.state.Detailed') + "</a>";
								}
							}
							
							return "<a href='" + getLink(full.promoId) + "'>" + local.getText('promo.state.Detailed') + "</a>";
						}
						
						if (type == "filter") {
							switch (data) {
							case 'Created':
								return full.type == 3 ? 'Detailed' : 'Created';
							case 'PromotionApproved':
								return 'PromotionApproved';
							case 'Applied':
								return 'Applied';
							case 'Submitted':
								return 'Submitted';
							case 'Started':
								return 'Started';
							case 'SubsidyCounting':
								return 'SubsidyCounting';
							}
							
							return 'Detailed';
						}
						
						if (type == "sort") {
							switch (data) {
							case 'Created':
								return full.type == 3 ? 20 : 0;
							case 'PromotionApproved':
								return 1;
							case 'Applied':
								return 2;
							case 'Submitted':
								return 3;
							case 'Started':
								return 4;
							case 'SubsidyCounting':
								return 5;
							}
							
							return 20;
						}

						return data;
					}
				}] 
			}
		};
	
	$.extend(OnGoingPromoTable.prototype, {
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
					
					that.container.parents(".pane-table").find(".type-filter").change(function(){
						oDataTable.column(1).search(this.value).draw();
					});
					
					that.container.parents(".pane-table").find(".state-filter").change(function(){
						oDataTable.column(4).search(this.value).draw();
					});
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
//					namespace.alertDialog.alert(local.getText('dataTable.requestFail'));
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
	
	namespace.OnGoingPromoTable = OnGoingPromoTable;
})(BizReport = BizReport || {});