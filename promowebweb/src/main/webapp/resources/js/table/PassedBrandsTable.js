/* Define Exposure Table implementation.
 *
 * @Description All configuration for exposure table are put here
 *
 * @Dependency jquery.isloading.js
 * @Dependency DataTable
 * @Dependency Widget
 */
var BizReport = BizReport || {};

(function (namespace) {
	var PassedBrandsTable = function () {};
	PassedBrandsTable.prototype = new cbt.Widget();

	var local = namespace.local;

	function getLink(promoId) {
		return "/promotion/" + promoId;
	}

	var defaultDataTableConfigs = {
		tableConfig : {
			'aaSorting' : [[3, 'asc']],
			'bAutoWidth' : true,
			'bDeferRender' : true,
			'bFilter' : true,
			'bLengthChange' : false,
			'bDestroy' : true,
			'bServerSide' : false,
			'bSortCellsTop' : true,
			'bSort' : true,
			'iDisplayLength' : 10,
			'sPaginationType' : 'full_numbers',
			'sDom' : '<"datatable_header">t<"datatable_pager"i>',
			'oLanguage' : {
				sEmptyTable : local.getText('dataTable.brand.emptyTable'),
				sInfoFiltered : local.getText('dataTable.promo.infoFiltered'),
				sInfo : local.getText('dataTable.brand.info'),
				sInfoEmpty : "",
				sLoadingRecords : local.getText('dataTable.loading'),
				sZeroRecords : local.getText('dataTable.brand.zeroRecords'),
				oPaginate : {
					sFirst : local.getText('dataTable.firstPage'),
					sLast : local.getText('dataTable.lastPage'),
					sPrevious : local.getText('dataTable.previousPage'),
					sNext : local.getText('dataTable.nextPage')
				}
			},
			sAjaxSource : "/promotion/brands/passed", 
			//sAjaxSource: "js/data/ongoing.json",
			'fnServerParams' : function (aoData) {
				var settings = this.fnSettings();
				if (settings.aaSorting[0]) {
					aoData.push({
						name : "sSortCol",
						value : settings.aoColumns[settings.aaSorting[0][0]].mData
					});
					aoData.push({
						name : "sSortDir",
						value : settings.aaSorting[0][1]
					});
				}
			},
			"fnServerData" : function (sSource, aoData, fnCallback, oSettings) {
				oSettings.jqXHR = $.ajax({
						"dataType" : 'json',
						"type" : "GET",
						"url" : sSource,
						"data" : aoData,
						"success" : function (json) {
							// stop update table if no data is gained.
							if (json.status != true || json.status != "ok")
								return;
							fnCallback(json);
						}
					});
			},
			columns : [{
					data : 'name'
				}, {
					data : 'lastAuditDt'
				}, {
					data : 'nextAuditDt'
				}, {
					data : 'state'
				}, {
					data : 'defectRateNCompliantAmount'
				}
			],
			aoColumnDefs : [{
					aTargets : ["name"],
					sDefaultContent : "",
					sType : "string",
					sClass : "item-title",
					sWidth : "300px",
					mRender : function (data, type, full, meta) {
						if (type == "display") {
							return "<a href='" + getLink(full.promoId) + "'>" + data + "</a>";
						}

						return data;
					}
				}, {
					aTargets : ["date"],
					sType : "date",
					sClass : "text-center",
					sDefaultContent : "-",
					mRender : function (data, type, full) {
						return !data ? '-' : (new Date(data)).format("yyyy-MM-dd");
					}
				}, {
					aTargets : ["state"],
					sType : "string",
					sClass : "text-center",
					sWidth : "120px",
					sDefaultContent : "-",
					mRender : function (data, type, full) {
						if (data == '1') {
							return '<b class="color-green fa fa-circle-o font16"></b>';
						} else if (data == '0') {
							return '<b class="color-orange fa fa-times font16"></b>'
						}
						
						return '-';
					}
				}, {
					aTargets : ["number"],
					sType : "string",
					sClass : "text-right",
					sDefaultContent : "-",
					mRender : function (data, type, full) {
						if (type == "sort") {
							return isNaN(data) ? -1 : parseInt(data);
						}
					}
				}
				
			]
		}
	};

	$.extend(PassedBrandsTable.prototype, {
		init : function (config) {
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
				initialized : function () {
					// get initialized DataTable instance
					that.oDataTable = oDataTable = this.table.DataTable();

					that.container.parents(".pane-table").find(".type-filter").dropdown().change(function (e, data) {
						oDataTable.column(1).search(data.value).draw();
					});

					that.container.parents(".pane-table").find(".state-filter").dropdown().change(function (e, data) {
						oDataTable.column(3).search(data.value).draw();
					});
				},
				ajaxbegin : function () {
					$(that.pane).isLoading({
						text : local.getText('dataTable.loading'),
						position : "inside"
					});
				},
				ajaxfinished : function (data) {
					that.pane.isLoading('hide');

					if (data && data.status) {
						if (data.data) {
							// Remove it from UI if this promotion doesn't have name.
							data.data = data.data.filter(function(promo){
								return promo && promo.name;
							});
						}
						that.container.find(".datatable_pager").show();
					} else {
						that.container.find(".datatable_pager").hide();
						that.dataTable.again && that.initDataTable();
						that.dataTable.updateAgain();
					}

					if (config.fnDataUpdatedCallback) {
						config.fnDataUpdatedCallback.call(that, data);
					}
				},
				error : function (data) {
					that.pane.isLoading('hide');
					that.dataTable.again && that.initDataTable();
					that.dataTable.updateAgain();
					//					namespace.cbt.alert(local.getText('dataTable.requestFail'));
				}
			}, this.dataTable);
		},

		initDataTable : function () {
			this.dataTable.initDataTable();
		},

		update : function (param) {
			this.dataTable.update(param);
		},

		getDataSize : function () {
			return this.oDataTable.data().length;
		}
	});

	namespace.PassedBrandsTable = PassedBrandsTable;
})(BizReport = BizReport || {});
