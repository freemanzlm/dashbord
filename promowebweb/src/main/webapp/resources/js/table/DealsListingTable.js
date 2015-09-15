/* List listings and allow user to select or unselect some listings for further operations.
 * 
 * @Description All configuration for exposure table are put here
 * 
 * @Dependency jquery.isloading.js
 * @Dependency DataTable
 * @Dependency Widget
 */
var BizReport = BizReport || {};

(function(namespace){
	var DealsListingTable = function() {};
	DealsListingTable.prototype = new namespace.Widget();
	
	var locale = namespace.locale;
	
	var states = ['Applicable', 'Nonapplied', 'Applied', 'Pretrial', 'PretrialPass', 'ApplyConfirm', 'PretrialFail', 'NotSubmitted'];
	
	var defaultDataTableConfigs = {
			tableConfig : {
				'aLengthMenu': [20],
				'aaSorting': [[2, 'asc']],
				'aaSortingFixed': [[6, 'asc']],
				'bAutoWidth': true,
				'bDeferRender': true,
				'bFilter': false,
				'bLengthChange': false,
				'bDestroy': true,
				'bServerSide': false,
				'bSortCellsTop': true,
				'bSort': true,
				'iDisplayLength': 200,
				'sPaginationType': 'full_numbers',
				'sDom': '<"datatable_header"rf>t<"datatable_pager clr"ip>',
				'bScrollCollapse': true,
				'sScrollY': "600",
				'oLanguage': {
					sEmptyTable: locale.getText('dataTable.emptyTable'),
					sInfo: locale.getText('dataTable.listing.info'),
					sInfoEmpty: "",
					sLoadingRecords: locale.getText('dataTable.loading'),
					oPaginate: {
						sFirst: locale.getText('dataTable.firstPage'),
						sLast: locale.getText('dataTable.lastPage'),
						sPrevious: locale.getText('dataTable.previousPage'),
						sNext: locale.getText('dataTable.nextPage')
					}
				},
				sAjaxSource: "/promotion/deals/getPromotionListings", //"/promotion/js/data/dealsListing.json"
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
				"fnDrawCallback": function(settings){
					// update checkbox status
					if (settings.aoColumns[0].bVisible) {
						settings.aoData.forEach(function(oRow){
							$(oRow.nTr).find("input[type=checkbox]:enabled").prop("checked", oRow._aData.checked);
						});
					}				
				},
				columns: [
				    {data: 'itemId'},
				    {data: 'itemId'},
					{data: 'skuName'},
					{data: 'price'},
					{data: 'actPrice'},
					{data: 'inventory'},
					{data: 'state'}
				],
				aoColumnDefs: [{
					aTargets: ["itemId"],
					bSortable: false,
					sDefaultContent: "",					
					sType: "string",
					sWidth: "30px",
					sClass: "text-center",
					fnCreatedCell: function(nTd, sData, oRow, iRowIndex) {
						oRow.checked = oRow.checked || (oRow.state == 'Confirmed' || oRow.state == 'Applied');
							
						$(nTd).html($("<input type=checkbox name=item>").attr({
							value:sData,
							rowindex : iRowIndex,
							checked: oRow.checked,
							disabled: (oRow.state == 'PretrialFail')
						}));
					}
				},
				{
					aTargets: ["item-id"],
					sDefaultContent: "",					
					sType: "string",
					sWidth: "150px",
					sClass: "item-title",
					mRender: function(data, type, full, meta) {
						if (type == "display") {
							var display = "<img src='http://thumbs.ebaystatic.com/pict/" + data + ".jpg' height='50' width='50'/>";
							return display += "<p><a href='http://www.ebay.com/itm/" + "'>" + data + "</a></p>";
						}
						
						return data;
					}					
				},
				{
					aTargets: ["name"],
					sDefaultContent: "",					
					sType: "string",
					sWidth: "300px",
					sClass: "item-title",
					mRender: function(data, type, full, meta) {
						return data;
					}					
				},
				{
					aTargets: ["inventory"],
					sType: "numeric",
					sClass: "text-right",
					sDefaultContent: "",
					mRender: function(data, type, full) {
						if (type == "display") {
							return parseFloat(data).toUSFixed(0);
						}
						return data;
					}
				},
				{
					aTargets: ["price"],
					sType: "numeric",
					sClass: "text-right",
					sDefaultContent: "",
					mRender: function(data, type, full) {
						if (type == "display") {
							return parseFloat(data).toUSFixed(2) + " (" + full.currency + ")";
						}

						return data;
					}
				},
				{
					aTargets: ["activity-price"],
					sType: "numeric",
					sClass: "text-right",
					sDefaultContent: "",
					mRender: function(data, type, full) {
						if (type == "display") {
							if (full.sellerPrice > 0 && full.sellerPrice != full.price) {
								return "<span class='red'>" + parseFloat(data).toUSFixed(2) + " (" + full.currency + ")</span>" + "<br/><del>(" + parseFloat(full.sellerPrice).toUSFixed(2) + " " + full.currency + ")</del>";
							} else {
								return parseFloat(data).toUSFixed(2) + " (" + full.currency + ")";
							}
						}

						return data;
					}
				},
				{
					aTargets: ["state"],
					bSortable: false,
					sClass: "text-center",
					sDefaultContent: "",
					mRender: function(data, type, full) {
						if (type == "display") {
							return locale.getText('listing.state.' + data);
						}
						
						if (type == "sort") {
							switch (data) {
							case 'Confirmed':
								return 3;
							case 'PretrialPass':
								return 2;
							case 'PretrialFail':
								return 1;
							case 'Nonsubmitted':
								return 0;
							default: return -1;
							}
						}

						return data;
					}
				}] 
			}
		};
	
	$.extend(DealsListingTable.prototype, {
		init: function(config) {
			var that = this;

			this.dataTableConfig = $.extend({}, defaultDataTableConfigs, config.dataTableConfig);

			this.dataTableConfig.tableConfig = $.extend({}, this.dataTableConfig.tableConfig, this.dataTableConfig.customTableConfig);
	
			this.config = $.extend({}, config);
			this.dataTable = new namespace.DataTable(this.dataTableConfig);
			this.selectedItems = []; // the selected items in current page
			this.checkedBoxes = [];
			
			// this statement must be put before this.dataTable.initDataTable();
			this.checkAllBox = this.dataTable.table.find(".check-all").prop("checked", false);
			
			// this statement must be put before this.dataTable.initDataTable();
			this.container = that.dataTable.table.parents(".dataTable-container:first");
			
			var oDataTable = this.oDataTable = null;
			
			this.dataTable.subscribe({
				initialized: function() {
					// get initialized DataTable instance
					that.oDataTable = oDataTable = this.table.DataTable();
					var aRows = oDataTable.data();
					
					that.selectedItems = aRows.filter(function(oRow){
						return oRow.state == 'Applied' || oRow.state == 'Confirmed';
					});
					
					if (that.selectedItems.length == aRows.length && that.selectedItems.length > 0) {
						that.checkAllBox.prop("checked", true);
					}
					that.publish("initialized");
					that.publish("selectChange");
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
				    that.initDataTable();
//				    that.dataTable.table.css("visibility", "visible");
//					namespace.alertDialog.alert(locale.getText('dataTable.requestFail'));
				}
			}, this.dataTable);		
			
			this.checkAllBox.click(function(){
				if (!oDataTable) return;
				
				var aRows = oDataTable.data(), checkbox = this;
				aRows = aRows.filter(function(listing){
					return !(listing.state == 6 || listing.state == 'PretrialFail');
				});
				
				aRows.each(function(item){
					// mark each row is selected
					item.checked = checkbox.checked;
				});
				
				if (this.checked) {
					that.selectedItems = Array.prototype.slice.apply(aRows) || [];					
				} else {
					that.selectedItems.splice(0); // empty selectedItems
				}
				
				that.dataTable.table.find("input[name=item]:enabled").prop("checked", this.checked);
				
				that.publish("selectChange");
			});
			
			$("input[name=item]:enabled", this.container.get(0)).live("click", function(){
				if (!oDataTable) return;
				
				var oData = oDataTable.row(this.getAttribute("rowindex")).data();
				oData.checked = this.checked;
				
				if (this.checked) {
					that.selectedItems.push(oData);
					that.checkAllBox.prop("checked", that.selectedItems.length == that.getDataSize());
				} else {
					that.checkAllBox.removeAttr("checked");
					that.removeItem(oData);
				}
				
				that.publish("selectChange");
			});
		},
		
		initDataTable: function() {
			this.dataTable.initDataTable();
		},
		
		update: function(param) {
			this.dataTable.update(param);
		},
		
		removeItem: function(item) {
			// search from backmost to headmost
			var i = this.selectedItems.length;
			while (--i >= 0) {
				if (this.selectedItems[i] = item) {
					this.selectedItems.splice(i, 1);
					break;
				}
			}
		},
		
		hasSelectedItem: function() {
			return this.selectedItems.length > 0;
		},
		
		getData: function() {
			return this.oDataTable.data();
		},
		
		setData: function(data) {
			this.oDataTable.clear();
			this.oDataTable.rows.add(data).draw();
		},
		
		getDataSize: function() {
			return this.oDataTable.data().length;
		},
		
		hideCheckbox: function() {
			this.oDataTable.column(0).visible(false);
		},
		
		hideStateColumn: function() {
			this.oDataTable.column(6).visible(false);
		}
	});
	
	namespace.DealsListingTable = DealsListingTable;
})(BizReport = BizReport || {});