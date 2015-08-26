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
	var EndPromoTable = function() {};
	EndPromoTable.prototype = new namespace.Widget();
	
	var locale = namespace.locale;
	
	var promos = ['deals', 'dealsPreset', 'hotsell', 'other'];
	var states = ['applicable', 'approved', 'submitted', 'applied', 'verifying', 'ongoing', 'rewardCounting', 'rewarding', 'claimFail', 'agreement', 'rewardVerifying', 'complete', 'applyExpired', 'verifyFailed', 'claimExpired', 'canceled', 'end'];
	
	function getLink(type, state) {
		type = parseInt(type), state = parseInt(state);
		switch (type) {
		case 0:
			switch(state) {
			case 5: return "deals/state";
			default: return 'deals/end';
			}
			break;
		case 1:
			switch(state) {
			case 5: return "dealsPreset/state";
			default: return "dealsPreset/end";
			}
			break;
		case 2:
			switch(state) {
			case 5: return "hotsell/state";
			default: return "hotsell/end";
			}
			break;
		default:
			switch(state) {
			case 5: return "other/state";
			default: return "other/end";
			}
			break;
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
					sEmptyTable: locale.getText('dataTable.emptyTable'),
					sInfo: locale.getText('dataTable.info'),
					sInfoEmpty: "",
					sLoadingRecords: locale.getText('dataTable.loading'),
					sZeroRecords: locale.getText('dataTable.zeroRecords'),
					oPaginate: {
						sFirst: locale.getText('dataTable.firstPage'),
						sLast: locale.getText('dataTable.lastPage'),
						sPrevious: locale.getText('dataTable.previousPage'),
						sNext: locale.getText('dataTable.nextPage')
					}
				},
//				'sScrollX': "100%",
				sAjaxSource: "promotion/listPromotions", //"js/data/end.json",
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
							return "<a href='http://www.ebay.com/itm/" + full.itemId
							    + "' data-item-id='" + full.itemId + "'>" + data + "</a>";
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
					aTargets: ["rewardDlDt"],
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
							if (!isNaN(val)) {
								return val.toUSFixed(2) + " (" + full.currency + ")";
							}
						}

						return data;
					}
				},
				{
					aTargets: ["state"],
					sClass: "text-center state",
					sDefaultContent: "",
					mRender: function(data, type, full) {
						if (type == "display") {
							if (data == 11) { // complete
								return locale.getText('promo.state.complete') + "<br/><a href='" + getLink(full.type, data)  + "'>查看详情</a>";
							} else {
								return "<a href='" + getLink(full.type, data)  + "'>查看详情</a>";
							}
						}
						
						if (type == "sort") {
							if (data != 11) {
								return 12;
							}
						}

						return data;
					}
				}] 
			}
		};
	
	$.extend(EndPromoTable.prototype, {
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
			
			function showRowDetail(nTr) {
				if (!nTr) return;
				
				closeRow(openRow);
				nTr.setAttribute("open", "");
				openRow = nTr;
				var nOpenRow = that.fnOpenCallback(nTr);
				that.publish("open", {nTr: nTr, openTr: nOpenRow, data: oDataTable.row(nTr).data()});
			}
			
			function closeRow(nTr) {
				if (!nTr) return;
				
				nTr.removeAttribute("open");
				$(nTr).find("button").html(locale.getText('dataTable.open')).removeAttr("open");
				that.dataTable.$dataTable.fnClose(nTr);
				that.publish("close", {nTr: nTr});
			}
			
			this.dataTable.table.find("button").live("click", function(){
				var nTr = $(this).parents("tr").get(0);
				
				if (this.hasAttribute("open")) {
					closeRow(nTr);
					this.removeAttribute("open");
					this.innerHTML=locale.getText('dataTable.open');
				} else {
					showRowDetail(nTr);
					this.setAttribute("open", "");
					this.innerHTML=locale.getText('dataTable.close');
				}
			});
			
			this.dataTable.table.on("order.dt page.dt", function(){
				closeRow(openRow);
			});
			
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
	
	namespace.EndPromoTable = EndPromoTable;
})(BizReport = BizReport || {});