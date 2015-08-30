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
	
	var promos = ['hotsell', 'deals', 'dealsPreset', 'other'];
	var states = ['applicable', 'approved', 'submitted', 'applied', 'verifying', 'ongoing', 'rewardCounting', 'rewarding', 'claimFail', 'agreement', 'rewardVerifying', 'complete', 'applyExpired', 'verifyFailed', 'claimExpired', 'canceled', 'end'];
	
	function getLink(type, state, promoId) {
		switch (type) {
		case 0: // deals
			switch(state) {
			case 0: return "hotsell/applicable/?promoId=" + promoId; // applicable
			case 3: return "hotsell/applied/?promoId=" + promoId; // applied
			case 5: // ongoing
			case 6: // rewardCounting
			case 7: // rewarding
			case 8: // agreement
			case 9: // rewardVerifying
			case 10: // rewardVerifying
			case 11:
				return "hotsell/state/?promoId=" + promoId; // complete
			default: return "hotsell/end/?promoId=" + promoId;
			}
		case 1:
			switch(state) {
			case 0: return "deals/applicable/?promoId=" + promoId;
			case 1: return "deals/listing/?promoId=" + promoId;
			case 2: return "deals/listing/?promoId=" + promoId;
			case 3: return "deals/applied/?promoId=" + promoId;
			case 5: // ongoing
			case 6: // rewardCounting
			case 7: // rewarding
			case 8: // agreement
			case 9: // rewardVerifying
			case 10: // rewardVerifying
			case 11:
				return "deals/state/?promoId=" + promoId; // complete
			default: return "deals/end/?promoId=" + promoId; // complete
			}
		case 2:
			switch(state) {
			case 0: return "dealspreset/applicable/?promoId=" + promoId; // applicable
			case 3: return "dealspreset/applied/?promoId=" + promoId; // applied
			case 5: // ongoing
			case 6: // rewardCounting
			case 7: // rewarding
			case 8: // claimFail
			case 9: // agreement
			case 10: // rewardVerifying
			case 11:
				return "dealspreset/state/?promoId=" + promoId; // complete
			default: return "dealspreset/end/?promoId=" + promoId;
			}
		default:
			switch(state) {
			case 5: // ongoing
			case 6: // rewardCounting
			case 7: // rewarding
			case 8: // claimFail
			case 9: // agreement
			case 10: // rewardVerifying
			case 11:
				return "other/state/?promoId=" + promoId; // complete
			default: return "other/end/?promoId=" + promoId;
			}
		}
		
		return "";
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
					sWidth: "250px",
					sClass: "item-title",
					mRender: function(data, type, full, meta) {
						if (type == "display") {
							return "<a href='" + getLink(full.type, full.state, full.promoId) + "'>" + data + "</a>";
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
					aTargets: ["rewardDlDt", "rewardClmDt"],
					sType: "date",
					sClass: "text-center",
					sDefaultContent: "-",
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
					sClass: "text-right",
					sDefaultContent: "",
					mRender: function(data, type, full) {
						var val = parseFloat(data);
						if (type == "display") {
							if (full.rewardType != 0) {
								if (val > 0) {
									return val.toUSFixed(2) + " (" + full.currency + ")";
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
						if (type == "display") {
							switch (parseInt(data)) {
							case 7: // rewarding
							case 8: // upload agreement
							case 9: // reclaim reward
								return "<a class='btn' target='_blank' href='" + full.rewardUrl + "'>" + locale.getText('promo.state.' + states[data]) + "</a>";
							default:
								return locale.getText('promo.state.' + states[data]) + "<br/>" + "<a href='" + getLink(full.type, full.state, full.promoId) + "'>查看详情</a>";
							}
						}

						return data;
					}
				}] 
			}
		};
	
	$.extend(RewardingPromoTable.prototype, {
		init: function(config) {
			var that = this;

			this.dataTableConfig = $.extend({}, defaultDataTableConfigs, config.dataTableConfig);

			this.dataTableConfig.tableConfig = $.extend({}, this.dataTableConfig.tableConfig, this.dataTableConfig.customTableConfig);
	
			this.config = $.extend({}, config);
			this.dataTable = new namespace.DataTable(this.dataTableConfig);
			
			// this statement must be put before this.dataTable.initDataTable();
			this.container = that.dataTable.table.parents(".dataTable-container:first");
			
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
					$(that.container).isLoading({text: locale.getText('dataTable.loading'), position: "inside"});
				},
				ajaxfinished: function(data) {
				    that.container.isLoading('hide');

				    if (data && data.status) {
				        that.container.find(".datatable_pager").show();
				    } else {
				        that.container.find(".datatable_pager").hide();
				    }
				    
				    if (config.fnDataUpdatedCallback) {config.fnDataUpdatedCallback.call(that, data);}
				},
				error: function(data) {
				    that.container.isLoading('hide');
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
	
	namespace.RewardingPromoTable = RewardingPromoTable;
})(BizReport = BizReport || {});