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
	HotsellListingTable.prototype = new namespace.Widget();
	
	var locale = namespace.locale;
	
	var defaultDataTableConfigs = {
			tableConfig : {
				'aLengthMenu': [20],
				'aaSorting': [[5, 'desc']],
				'aaSortingFixed': [[6, 'desc'], [7, 'asc']],
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
					{data: 'price'},
					{data: 'volume'},
					{data: 'compCost'},
					{data: 'maxComp'},
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
						if (type == "sort") {
							switch(data) {
							case 'GBP':
								return '1';
							case 'EUR':
								return '2';
							case 'USD':
								return '3';							
							case 'AUD':
								return '4';
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
							var display = "<img src='http://thumbs.ebaystatic.com/pict/" + full.itemId + ".jpg' height='50' width='50'/>";
							return display += "<p><a href='http://www.ebay.com/itm/" + full.itemId
							    + "' data-item-id='" + full.itemId + "'>" + data + "</a></p>";
						}
						
						return data;
					}					
				},
				{
					aTargets: ["target-volume"],
					sType: "date",
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
					sType: 'numeric',
					mRender: function(data, type, full) {
						if (type == "display") {
							return locale.getText('listing.state.' + data);
						}
						
						if (type == "sort") {
							switch (data) {
							case 'Applied':
							case 'AuditSuccess':
								return 1;
							case 'Nonapplied':
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
						return oRow.state == 'Applied';
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
				    	that.initDataTable();
				        that.container.find(".datatable_pager").hide();
				    }
				    
				    if (config.fnDataUpdatedCallback) {config.fnDataUpdatedCallback.call(that, data);}
				},
				error: function(data) {
				    that.container.isLoading('hide');
				    that.initDataTable();
//					namespace.alertDialog.alert(locale.getText('dataTable.requestFail'));
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