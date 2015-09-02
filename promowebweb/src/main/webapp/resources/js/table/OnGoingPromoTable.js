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
	
	var locale = namespace.locale;
	
	var promos = ['hotsell', 'deals', 'dealsPreset', 'other'];
	var states = ['Created', 'Submitted', 'Verifying', 'PromotionApproved', 'VerifyFailed', 'Applied', 'Started', 'SubsidyCounting', 'SubsidyWaiting', 'NeedAgreement', 'SubsidyVerifying', 'Completed', 'ClaimFail', 'rewardVerifying', 'Canceled', 'End'];
	
	function getLink(promoId) {
		return "promotion/" + promoId;
	}
	
	var defaultDataTableConfigs = {
			tableConfig : {
				'aLengthMenu': [20],
				'aaSorting': [[5, 'asc'], [3, 'asc']],
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
					sEmptyTable: locale.getText('dataTable.promo.emptyTable'),
					sInfo: locale.getText('dataTable.info'),
					sInfoEmpty: "",
					sLoadingRecords: locale.getText('dataTable.loading'),
					sZeroRecords: locale.getText('dataTable.promo.zeroRecords'),
					oPaginate: {
						sFirst: locale.getText('dataTable.firstPage'),
						sLast: locale.getText('dataTable.lastPage'),
						sPrevious: locale.getText('dataTable.previousPage'),
						sNext: locale.getText('dataTable.nextPage')
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
					{data: 'reward'},
					{data: 'state'}
				],
				aoColumnDefs: [{
					aTargets: ["name"],
					sDefaultContent: "",					
					sType: "string",
					sWidth: "250px",
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
							return locale.getText('promo.type.' + promos[data]); 
						}
						
						return data;
					}
				},
				{
					aTargets: ["promoDlDt"],
					sType: "date",
					sClass: "text-center",
					sDefaultContent: " ",
					mRender: function(data, type, full) {
						return data;
					}
				},
				{
					aTargets: ["promoDt"],
					sType: "date",
					sClass: "text-center",
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
							if (full.rewardType != 0 && full.state != 6) {
								if (val > 0) {
									return val.toUSFixed(2) + " (" + full.currency + ")";
								} else {
									return locale.getText('dataTable.promo.rewardCounting');
								}
							} else {
								return locale.getText('dataTable.promo.noReward');
							}
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
							if (full.type == 1) {
								switch (data) {
								case 0:
								case 1:
									return "<a class='btn' href='" + getLink(full.promoId) + "'>" + locale.getText('promo.state.' + states[data]) + "</a>";
								case 2:
								case 3:
								case 5:
								case 6:
									return locale.getText('promo.state.' + states[data]) + "<br/>" + "<a href='" + getLink(full.promoId) + "'>查看详情</a>";
								default:
									return "<a href='" + getLink(full.promoId) + "'>查看详情</a>";
								}
							} else if (full.type != 3) {
								switch (data) {
								case 0:
									return "<a class='btn' href='" + getLink(full.promoId) + "'>" + locale.getText('promo.state.' + states[data]) + "</a>";
								case 3:
								case 5:
								case 6:
									return locale.getText('promo.state.' + states[data]) + "<br/>" + "<a href='" + getLink(full.promoId) + "'>查看详情</a>";
								default:
									return "<a href='" + getLink(full.promoId) + "'>查看详情</a>";
								}
							} else {
								if (data == 6) {
									// calculating reward
									return locale.getText('promo.state.' + states[data]) + "<br/>" + "<a href='" + getLink(full.promoId) + "'>查看详情</a>";
								} else {
									return "<a href='" + getLink(full.promoId) + "'>查看详情</a>";
								}
							}
						}
						
						if (type == "filter") {
							if (full.type == 1) {
								if (data == 4 || data > 6) {
									return 4;
								}
							} else if (full.type != 3) {
								if (data == 1 || data == 2 || data > 6) {
									return 4;
								}
							} else {
								if (data != 6) {
									return 4;
								}
							}
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
			
			var oDataTable = this.oDataTable = null, openRow = null;
			
			this.dataTable.subscribe({
				initialized: function() {
					// get initialized DataTable instance
					that.oDataTable = oDataTable = this.table.DataTable();
					
					that.container.parents(".pane-table").find(".type-filter").change(function(){
						oDataTable.column(1).search(this.value).draw();
					});
					
					that.container.parents(".pane-table").find(".state-filter").change(function(){
						oDataTable.column(5).search(this.value).draw();
					});
				}, 
				ajaxbegin: function() {
					$(that.pane).isLoading({text: locale.getText('dataTable.loading'), position: "inside"});
				},
				ajaxfinished: function(data) {
				    that.pane.isLoading('hide');

				    if (data && data.status) {
				        that.container.find(".datatable_pager").show();
				    } else {
				        that.container.find(".datatable_pager").hide();
				    }
				    
				    if (config.fnDataUpdatedCallback) {config.fnDataUpdatedCallback.call(that, data);}
				},
				error: function(data) {
				    that.pane.isLoading('hide');
					namespace.alertDialog.alert(locale.getText('dataTable.requestFail'));
				}
			}, this.dataTable);			
		},
		
		initDataTable: function() {
			this.dataTable.initDataTable();
		},
		
		update: function(param) {
			this.dataTable.update(param);
		},
		
		fnOpenCallback: function(nTr) {
			var html = null;
			if (this.config.fnOpenCallback) {
				html = this.config.fnOpenCallback.call(this);
			}
			return this.dataTable.$dataTable.fnOpen(nTr, html || "", "open-tr");
		},
		
		getDataSize: function() {
			return this.oDataTable.data().length;
		}
	});
	
	namespace.OnGoingPromoTable = OnGoingPromoTable;
})(BizReport = BizReport || {});