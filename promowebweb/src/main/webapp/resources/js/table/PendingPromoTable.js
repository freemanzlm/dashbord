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
	PendingPromoTable.prototype = new namespace.Widget();
	
	var locale = namespace.locale;
	
	var promos = ['hotsell', 'deals', 'dealsPreset', 'other'];
	
	function getLink(promoId) {
		return "promotion/" + promoId;
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
					/*{data: 'reward'},*/
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
							return "<a target='_blank' href='" + getLink(full.promoId) + "'>" + data + "</a>";
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
					aTargets: ["state"],
					sClass: "text-center state",
					sDefaultContent: "",
					sType: 'numeric',
					mRender: function(data, type, full) {
						var display ;
						if (type == "display") {
							if (full.type != 3) {
								switch (data) {
								case 'Created':
								case 'PromotionApproved':
									return "<a class='btn' target='_blank' href='" + getLink(full.promoId) + "'>" + locale.getText('promo.state.' + data) + "</a>";
								case 'Applied':
								case 'Verifying':
								case 'Submitted':
									return locale.getText('promo.state.' + data) + "<br/>" + "<a target='_blank' href='" + getLink(full.promoId) + "'>查看详情</a>";
								}
							}
							
							switch(data) {
							case 'Started':
							case 'SubsidyCounting':
							case 'SubsidyRetrieved':
								return locale.getText('promo.state.' + data) + "<br/>" + "<a target='_blank' href='" + getLink(full.promoId) + "'>查看详情</a>";
							case 'SubsidyWaiting':
							case 'SubsidyAccessed':
							case 'SubsidySubmmitted':
							case 'SubsidyRetrievable':
							case 'SubsidyResubmittable':
								if ((full.rewardType == 1 || full.rewardType == 4) && full.rewardUrl) {
									display = "<a class='btn' target='_blank' href='" + full.rewardUrl + "'>" + locale.getText('promo.state.' + data) + "</a>";
									display += "<br/>" + "<a target='_blank' href='" + getLink(full.promoId) + "'>查看详情</a>";
								} else {
									display = "<a class='btn' target='_blank' href='" + full.rewardUrl + "'>" + locale.getText('promo.state.SubsidyWaiting') + "</a>";
									display = "<a target='_blank' href='" + getLink(full.promoId) + "'>查看详情</a>";
								}
								
								return display;
							}
							
							return "<a target='_blank' href='" + getLink(full.promoId) + "'>查看详情</a>";
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
					
					that.container.parents(".pane-table").find(".type-filter").change(function(){
						oDataTable.column(1).search(this.value).draw();
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
				    that.initDataTable();
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
		}
	});
	
	namespace.PendingPromoTable = PendingPromoTable;
})(BizReport = BizReport || {});