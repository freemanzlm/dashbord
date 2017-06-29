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
	var RewardingPromoTable = function () {};
	RewardingPromoTable.prototype = new cbt.Widget();

	var local = namespace.local, util = namespace.util;
	var timezoneOffset = ((new Date()).getTimezoneOffset() - 420) * 60000 /*promotion time is beijing time, but server is in us -7*/;

	function getLink(promoId) {
		return "/promotion/" + promoId;
	}
	
	function getSubsidyLink(promoId) {
		return "/promotion/subsidy/acknowledgment?promoId=" + promoId;
	}

	var defaultDataTableConfigs = {
		tableConfig : {
			'aaSorting' : [[4, 'asc'], [2, 'asc']],
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
			'sDom' : '<"datatable_header">t<"datatable_pager"ip>',
			'oLanguage' : {
				sEmptyTable : local.getText('dataTable.promo.emptyTable'),
				sInfo : local.getText('dataTable.promo.info'),
				sInfoFiltered : local.getText('dataTable.promo.infoFiltered'),
				sInfoEmpty : "",
				sLoadingRecords : local.getText('dataTable.loading'),
				sZeroRecords : local.getText('dataTable.promo.zeroRecords'),
				sInfoFiltered : local.getText('dataTable.infoFiltered'),
				oPaginate : {
					sFirst : local.getText('dataTable.firstPage'),
					sLast : local.getText('dataTable.lastPage'),
					sPrevious : local.getText('dataTable.previousPage'),
					sNext : local.getText('dataTable.nextPage')
				}
			},
			sAjaxSource : "promotion/listPromotions", //"js/data/ongoing.json",
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
					data : 'rewardDlDt'
				}, {
					data : 'promoEdt'
				}, {
					data : 'reward'
				}, {
					data : 'state'
				}
			],
			aoColumnDefs : [{
					aTargets : ["name"],
					sDefaultContent : "",
					sType : "string",
					sWidth : "350px",
					sClass : "item-title",
					mRender : function (data, type, full, meta) {
						if (type == "display") {
							return "<a href='" + getLink(full.promoId) + "'>" + data + "</a>";
						}

						return data;
					}
				}, {
					aTargets : ["rewardDlDt", "rewardClmDt"],
					sType : "numeric",
					sClass : "text-center",
					sDefaultContent : "-",
					sWidth : "120px",
					mRender : function (data, type, full) {
						if(data && type === 'display') {
							return (new Date(data + timezoneOffset)).format("yyyy-MM-dd hh:mm");
						}
						
						if (type === 'sort') {
							return data ? data : -1;
						}
						
						return data;
					}
				}, {
					aTargets : ["promoDt"],
					sType : "numeric",
					sClass : "text-center",
					sDefaultContent : "-",
					sWidth : "220px",
					mRender : function (data, type, full) {
						if (type == "display") {
							return (full.promoSdt ? (new Date(full.promoSdt + timezoneOffset)).format("yyyy-MM-dd hh:mm") : '-') + " ~ " + (data ? (new Date(data + timezoneOffset)).format("yyyy-MM-dd hh:mm") : '-');
						}
						
						if (type === 'sort') {
							return data ? data : -1;
						}
						
						return data;
					}
				}, {
					aTargets : ["reward"],
					sClass : "text-right",
					sDefaultContent : " ",
					mRender : function (data, type, full) {
						var val = parseFloat(data);

						if (type == "display") {
							if ((full.rewardType != 0)) {
								if (!isNaN(val) && val > 0) {
									return val.toUSFixed(2) + '(' + full.currency + ')';
								} else {
									return '0';
								}
							}

							return local.getText('dataTable.promo.noReward');
						}

						if (type == "sort") {
							if (full.rewardType != 0) {
								if (!isNaN(val) && val > 0) {
									return val;
								} else {
									return 0;
								}
							}
							return -1;
						}

						return data;
					}
				}, {
					aTargets : ["state"],
					sClass : "text-center state",
					sDefaultContent : "",
					sType : "numeric",
					swidth: '120px',
					mRender : function (data, type, full) {
						// data: Awarding, Visited, Commited(Spell Mistake, ignore it), Appliable, AppliableAgain, Uploaded
						var display, sortOrder={Awarding:8, AppliableAgain:9, Visited:10, Commited:11, Uploaded:12, Appliable:13};

						if (type == "display") {
							switch (data) {
							case 'Awarding':
							case 'Visited':
							case 'Commited':
							case 'Appliable':
							case 'AppliableAgain':							
								if (full.onlineVettingFlag) {
									display = "<a class='btn' target='_blank' href='" + getSubsidyLink(full.promoId) + "'>" + local.getText('promo.state.' + data) + "</a><br/>";
								} else if (!full.onlineVettingFlag) {
									display = "<a class='btn' target='_blank' href='" + getLink(full.promoId) + "'>" + local.getText('promo.state.' + data) + "</a><br/>";
								}
								
								display += '<a href="' + getLink(full.promoId) + '" target="_self">' + local.getText('promo.state.Detailed') + "</a>";
								return display;
							default:
								return local.getText('promo.state.' + data) + "<br/>" + '<a href="' + getLink(full.promoId) + '" target="_self">' + local.getText('promo.state.Detailed') + "</a>";
							}
						}

						if (type == "sort") {
							return sortOrder[data] || 20;
						}

						return data;
					}
				}
			]
		}
	};

	$.extend(RewardingPromoTable.prototype, {
		init : function (config) {
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
				initialized : function () {
					// get initialized DataTable instance
					that.oDataTable = oDataTable = this.table.DataTable();

					that.container.parents(".pane-table").find(".type-filter").dropdown().change(function (e, data) {
						oDataTable.column(1).search(data.value).draw();
					});

					that.container.parents(".pane-table").find(".state-filter").dropdown().change(function (e, data) {
						oDataTable.column(4).search(data.value).draw();
					});

					that.publish("initialized");
					that.publish("selectChange");
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
		},

		hideReward : function () {
			this.oDataTable.column(4).visible(false);
		}
	});

	namespace.RewardingPromoTable = RewardingPromoTable;
})(BizReport = BizReport || {});
