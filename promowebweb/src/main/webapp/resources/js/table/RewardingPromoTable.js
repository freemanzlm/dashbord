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
	var RewardingPromoTable = function() {};
	RewardingPromoTable.prototype = new namespace.Widget();
	
	var locale = namespace.locale;
	
	var promos = ['hotsell', 'deals', 'deals', 'other'];
	
	function getLink(promoId) {
		return "/promotion/" + promoId;
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
				'sDom': '<"datatable_header">t<"datatable_pager"ip>',
				'oLanguage': {
					sEmptyTable: locale.getText('dataTable.promo.emptyTable'),
					sInfo: locale.getText('dataTable.promo.info'),
					sInfoFiltered: locale.getText('dataTable.promo.infoFiltered'),
					sInfoEmpty: "",
					sLoadingRecords: locale.getText('dataTable.loading'),
					sZeroRecords: locale.getText('dataTable.promo.zeroRecords'),
					sInfoFiltered: locale.getText('dataTable.infoFiltered'),
					oPaginate: {
						sFirst: locale.getText('dataTable.firstPage'),
						sLast: locale.getText('dataTable.lastPage'),
						sPrevious: locale.getText('dataTable.previousPage'),
						sNext: locale.getText('dataTable.nextPage')
					}
				},
//				'sScrollX': "100%",
				sAjaxSource: "promotion/listPromotions", //"js/data/ongoing.json",
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
					{data: 'rewardDlDt'},
					{data: 'promoEdt'},					
					{data: 'reward'},
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
							return locale.getText('promo.type.' + promos[data]); 
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
					aTargets: ["rewardDlDt", "rewardClmDt"],
					sType: "date",
					sClass: "text-center",
					sDefaultContent: "-",
					sWidth: "120px",
					mRender: function(data, type, full) {
						return data;
					}
				},
				{
					aTargets: ["promoDt"],
					sType: "date",
					sClass: "text-center",
					sDefaultContent: "-",
					sWidth: "200px",
					mRender: function(data, type, full) {
						if (type == "display") {
							return full.promoSdt + " ~ " + data;
						}
						return data;
					}
				},
				{
					aTargets: ["reward"],
					sClass: "text-right",
					sDefaultContent: " ",
					mRender: function(data, type, full) {
						data = full.region == 'CN' ? data : ' ';
						var val = parseFloat(data);
						
						if (type == "display" && full.region == 'CN') {
							if ((full.rewardType != 0)) {
								if (!isNaN(val)) {
									return val.toUSFixed(2) + ' (RMB)';
								} else {
									return '0.00 (RMB)';
								}
							}
							
							return locale.getText('dataTable.promo.noReward');
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
					sType: "numeric",
					mRender: function(data, type, full) {
						var display ;
						
						if (type == 'filter') {
							if (full.region == 'CN') {
								if (!(full.rewardType == 1 || full.rewardType == 2) && full.rewardType != 0) {
									data = 'SubsidyRetrievable'; // filter
								}
							} else {
								data = 'SubsidyRetrievable'; // filter
							}
						}
						
						
						if (type == "display") {
							if (full.region == 'CN') {
								if ((full.rewardType == 1 || full.rewardType == 2)) {
									// Gas card, WLT, JD card
									switch (data) {
									case 'SubsidyWaiting':
									case 'SubsidyAccessed':
									case 'SubsidySubmitted':
									case 'SubsidyRetrievable':
									case 'SubsidyResubmittable':
										if (full.rewardUrl) {
											display = "<a class='btn' target='_blank' href='" + full.rewardUrl + "'>" + locale.getText('promo.state.' + data) + "</a>";
											display += "<br/>" + "<a href='" + getLink(full.promoId) + "'>查看详情</a>";
										} else {
											display = locale.getText('promo.state.' + data);
											display += "<a href='" + getLink(full.promoId) + "'>查看详情</a>";
										}
										return display;
									default:
										return locale.getText('promo.state.' + data) + "<br/>" + "<a href='" + getLink(full.promoId) + "'>查看详情</a>";
									}
								}
							}
							
							if (full.rewardType != 0) {
								display = "<a class='btn' href='" + getLink(full.promoId) + "'>" + locale.getText('promo.state.SubsidyRetrievable') + "</a>";
								return display;
							}
						}
						
						if (type == "sort") {
							
							if (full.region == 'CN') {
								if ((full.rewardType == 1 || full.rewardType == 2) && full.rewardUrl){
									switch (data) {
									case 'SubsidyWaiting':
										return 8;
									case 'SubsidyResubmittable':
										return 9;
									case 'SubsidyRetrievable':
										return 10;
									case 'SubsidyAccessed':
										return 11;
									case 'SubsidySubmitted':
										return 12;
									case 'SubsidyUploaded':
										return 13;
									}
								}
							}
							
							if (full.rewardType != 0) {
								return 10; // SubsidyRetrievable
							}
							
							return 20;
						}
						
						return data;
					}
				}] 
			}
		};
	
	$.extend(RewardingPromoTable.prototype, {
		init: function(config) {
			var that = this;

			this.dataTableConfig = $.extend(true, {}, defaultDataTableConfigs, config.dataTableConfig);

			this.dataTableConfig.tableConfig = $.extend(true, {}, this.dataTableConfig.tableConfig, this.dataTableConfig.customTableConfig);
	
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
						oDataTable.column(5).search(this.value).draw();
					});
					
					that.publish("initialized");
					that.publish("selectChange");
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
				        that.dataTable.again && that.initDataTable();
				        that.dataTable.updateAgain();
				    }
				    
				    if (config.fnDataUpdatedCallback) {config.fnDataUpdatedCallback.call(that, data);}
				},
				error: function(data) {
				    that.pane.isLoading('hide');
				    that.dataTable.again && that.initDataTable();
			        that.dataTable.updateAgain();
//					namespace.alertDialog.alert(locale.getText('dataTable.requestFail'));
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
		},
		
		hideReward: function() {
			this.oDataTable.column(4).visible(false);
		}
	});
	
	namespace.RewardingPromoTable = RewardingPromoTable;
})(BizReport = BizReport || {});