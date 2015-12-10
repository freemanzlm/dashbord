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
	var HotsellListingTable = function() {};
	HotsellListingTable.prototype = new cbt.Widget();
	
	var local = namespace.local;
	
	var defaultDataTableConfigs = {
			tableConfig : {
				'aLengthMenu': [20],
				'aaSorting': [[5, 'desc']],
				'aaSortingFixed': [[6, 'desc']],
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
				'oLanguage': {
					sEmptyTable: local.getText('dataTable.emptyTable'),
					sInfo: local.getText('dataTable.listing.info'),
					sInfoEmpty: "",
					sLoadingRecords: local.getText('dataTable.loading'),
					oPaginate: {
						sFirst: local.getText('dataTable.firstPage'),
						sLast: local.getText('dataTable.lastPage'),
						sPrevious: local.getText('dataTable.previousPage'),
						sNext: local.getText('dataTable.nextPage')
					}
				},
				'bScrollCollapse': true,
				'sScrollY': "600",
//				sAjaxSource: "/promotion/js/data/listing.json",
				sAjaxSource: "/promotion/hotsell/getPromotionListings",
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
							var jTr = $(oRow.nTr);
							jTr.find("input[type=checkbox]:enabled").prop("checked", oRow._aData.checked);
							oRow._aData.checked ? jTr.addClass("selected") : jTr.removeClass('selected');
						});
					}
				},
				columns: [
				    {data: 'itemId'},
					{data: 'name'},
					{data: 'price', aDataSort: [7, 2]},
					{data: 'volume'},
					{data: 'compCost', aDataSort: [7, 4]},
					{data: 'maxComp', aDataSort: [7, 5]},
					{data: 'state'},
					{data: 'currency'}
				],
				aoColumnDefs: [{
					aTargets: ["itemId"],
					bSortable: false,
					bVisible: false,
					sDefaultContent: "",					
					sType: "string",
					sWidth: "30px",
					sClass: "text-center",
					fnCreatedCell: function(nTd, sData, oRow, iRowIndex) {
						oRow.checked = oRow.checked || oRow.state == 'Applied';
						$(nTd).html($("<input type=checkbox name=item>").attr({
							value:sData,
							rowindex : iRowIndex,
							checked: oRow.checked
						}));
					}
				},{
					aTargets: ["currency"],
					bVisible: false,
					sDefaultContent: "",					
					sType: "string",
					mRender: function(data, type, full, meta) {
						data = data && data.toUpperCase();
						
						if (type == "sort") {
							switch(data) {
							case 'GBP':
								return 'zzzz';
							case 'EUR':
								return 'zzzy';
							case 'USD':
								return 'zzzx';							
							case 'AUD':
								return 'zzza';
							}
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
						if (type == "display") {
							var display = "<img src='http://thumbs2.ebaystatic.com/pict/" + full.itemId + ".jpg' height='50' width='50'/>";
							return display += "<p><a href='http://www.ebay.com/itm/" + full.itemId
							    + "' data-item-id='" + full.itemId + "'>" + data + "</a></p>";
						}
						
						return data;
					}					
				},
				{
					aTargets: ["target-volume"],
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
					aTargets: ["target-price", "compensate", "target-sales", 'compensate-per'],
					sClass: "text-right",
					sDefaultContent: "",
					sType: 'numeric',
					mRender: function(data, type, full) {
						if (type == "display") {
							return parseFloat(data).toUSFixed(2) + " (" + full.currency + ")";
						}

						return data;
					}
				},
				{
					aTargets: ["state"],
					sClass: "text-center",
					sDefaultContent: "",
					bSortable: false,
					sType: 'numeric',
					mRender: function(data, type, full) {
						if (type == "display") {
							if (pageData && pageData.expired === false && data == 'Nonapplied') {
								return local.getText('listing.state.Applicable');
							}
							return local.getText('listing.state.' + data);
						}
						
						if (type == "sort") {
							switch (data) {
							case 'Applied':
							case 'AuditSuccess':
								return 2;
							case 'Applicable':
								return 1;
							case 'Nonapplied':
								return (pageData && pageData.expired === false) ? 1 : 0;
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
	
	$.extend(HotsellListingTable.prototype, {
		init: function(config) {
			var that = this;

			this.dataTableConfig = $.extend(true, {}, defaultDataTableConfigs, config.dataTableConfig);

			this.dataTableConfig.tableConfig = $.extend(true, {}, this.dataTableConfig.tableConfig, this.dataTableConfig.customTableConfig);
	
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
						oRow.checked = oRow.state == 'Applied';
						return oRow.checked;
					});
					
					that.checkAllBox.prop("indeterminate", that.selectedItems.length > 0 && that.selectedItems.length < aRows.length);
					that.checkAllBox.prop("checked", that.selectedItems.length == aRows.length && that.selectedItems.length > 0);
					
					that.publish("initialized");
					that.publish("selectChange");
				}, 
				ajaxbegin: function() {
					$(that.container).isLoading({text: local.getText('dataTable.loading'), position: "inside"});
				},
				ajaxfinished: function(data) {
				    that.container.isLoading('hide');

				    if (data && data.status) {
				        that.container.find(".datatable_pager").show();
				    } else {
				    	that.container.find(".datatable_pager").hide();
				    	
				    	// initialize the empty table
				    	that.dataTable.again && that.initDataTable();
				        that.dataTable.updateAgain();
				    }
				    
				    if (config.fnDataUpdatedCallback) {config.fnDataUpdatedCallback.call(that, data);}
				},
				error: function(data) {
				    that.container.isLoading('hide');
				    
				    // initialize the empty table only when it's updated the second time.
				    that.dataTable.again && that.initDataTable();				 
				    that.dataTable.updateAgain();
//					namespace.alertDialog.alert(local.getText('dataTable.requestFail'));
				}
			}, this.dataTable);	
			
			this.checkAllBox.click(function(){
				if (!oDataTable) return;
				
				var aRows = oDataTable.data(), checkbox = this;
				aRows.each(function(item){
					// mark each row is selected
					item.checked = checkbox.checked;
				});
				
				var enabledBoxes = that.dataTable.table.find("input[name=item]:enabled").prop("checked", this.checked);
				
				if (this.checked) {
					that.selectedItems = Array.prototype.slice.apply(aRows) || [];
					that.selectedItems = that.selectedItems.filter(function(oRow){
						return oRow.checked;
					});
					enabledBoxes.parents('tr').addClass("selected");
				} else {
					that.selectedItems.splice(0); // empty selectedItems
					enabledBoxes.parents('tr').removeClass("selected");
				}
				
				that.publish("selectChange");
			});
			
			$("input[name=item]", this.container.get(0)).live("click", function(){
				if (!oDataTable) return;
				
				var parentTr = $(this).parents("tr");
				var oData = oDataTable.row(this.getAttribute("rowindex")).data();
				oData.checked = this.checked;
				
				if (this.checked) {
					that.selectedItems.push(oData);
					parentTr.addClass("selected");
					that.checkAllBox.prop("checked", that.selectedItems.length == that.getDataSize());
				} else {
					that.checkAllBox.removeAttr("checked");
					parentTr.removeClass("selected");
					that.removeItem(oData);
				}
				
				that.checkAllBox.prop("indeterminate", that.selectedItems.length > 0 && that.selectedItems.length < that.getDataSize());
				
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
				if (this.selectedItems[i] == item) {
					this.selectedItems.splice(i, 1);
					break;
				}
			}
		},
		
		hasSelectedItem: function() {
			return this.selectedItems.length > 0;
		},
		
		getDataSize: function() {
			return this.oDataTable.data().length;
		},
		
		getData: function() {
			return this.oDataTable.data();
		},
		
		setData: function(data) {
			this.oDataTable.clear();
			this.oDataTable.rows.add(data).draw();
		},
		
		hideCheckbox: function() {
			this.oDataTable.column(0).visible(false);
		},
		
		hideStateColumn: function() {
			this.oDataTable.column(6).visible(false);
		}
	});
	
	namespace.HotsellListingTable = HotsellListingTable;
})(BizReport = BizReport || {});