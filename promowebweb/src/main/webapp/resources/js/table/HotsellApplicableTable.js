/* Listing hotsell listings and there are selection checkboxes for further operation.
 * 
 * @Description All configuration for exposure table are put here
 * 
 * @Dependency jquery.isloading.js
 * @Dependency DataTable
 * @Dependency Widget
 */
var BizReport = BizReport || {};

(function(namespace){
	var HotsellApplicableTable = function() {};
	HotsellApplicableTable.prototype = new namespace.Widget();
	
	var locale = namespace.locale;
	
	var states = ['applied','verifying','reviewed'];
	
	var defaultDataTableConfigs = {
			tableConfig : {
				'aLengthMenu': [20],
				'aaSorting': [[1, 'asc']],
				'bAutoWidth': true,
				'bDeferRender': true,
				'bFilter': false,
				'bLengthChange': false,
				'bDestroy': true,
				'bServerSide': false,
				'bSortCellsTop': true,
				'bSort': true,
//				'iDisplayLength': 10,
				'sPaginationType': 'full_numbers',
				'sDom': '<"datatable_header"rf>t<"datatable_pager"i>',
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
				columns: [
				    {data: 'itemId'},
					{data: 'name'},
					{data: 'price'},
					{data: 'volume'},
					{data: 'sales'},
					{data: 'comp'}
				],
				aoColumnDefs: [{
					aTargets: ["itemId"],
					bSortable: false,
					sDefaultContent: "",					
					sType: "string",
					sWidth: "30px",
					sClass: "text-center",
					fnCreatedCell: function(nTd, sData, oRow, iRowIndex) {
						$(nTd).html($("<input type=checkbox name=item>").attr({
							value:sData,
							rowindex : iRowIndex
						}));
					}
				},
				{
					aTargets: ["name"],
					sDefaultContent: "",					
					sType: "string",
					sWidth: "250px",
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
					aTargets: ["target-price", "compensate", "target-sales"],
					sClass: "text-right",
					sDefaultContent: "",
					mRender: function(data, type, full) {
						if (type == "display") {
							return parseFloat(data).toUSFixed(2);
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
							return locale.getText('listing.state.' + states[data]);
						}

						return data;
					}
				}] 
			}
		};
	
	$.extend(HotsellApplicableTable.prototype, {
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
			
			this.checkAllBox.click(function(){
				if (!oDataTable) return;
				if (this.checked) {
					that.selectedItems.splice(0); // empty selectedItems
					that.dataTable.table.find("input[name=item]").each(function(){
						this.checked = true;
						that.selectedItems.push(oDataTable.row(this.getAttribute("rowindex")).data());
					});
				} else {
					that.dataTable.table.find("input[name=item]").removeAttr("checked");
					that.selectedItems.splice(0); // empty selectedItems
				}
				
				that.publish("selectChange");
			});
			
			$("input[name=item]").live("click", function(){
				if (!oDataTable) return;
				
				if (this.checked) {
					that.selectedItems.push(oDataTable.row(this.getAttribute("rowindex")).data());
				} else {
					that.checkAllBox.removeAttr("checked");
					that.removeItem(oDataTable.row(this.getAttribute("rowindex")).data());
				}
				
				that.publish("selectChange");
			});
			
			this.dataTable.table.on("page.dt", function(){
				// user can only select items on the displayed page.
				that.checkAllBox.removeAttr("checked");
				that.dataTable.table.find("input[name=item]").removeAttr("checked");
				that.selectedItems.splice(0); // empty selectedItems
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
		
		getDataSize: function() {
			return this.oDataTable.data().length;
		}
	});
	
	namespace.HotsellApplicableTable = HotsellApplicableTable;
})(BizReport = BizReport || {});