/* Sortable Table Implementation.
 * 
 * @Description It's like a 2nd level tree and follow the UI components style
 * @depend jquery.dataTables.js(1.10.5), jquery(1.7)
 */

(function(namespace) {
	var DataTable = function(cfg) {
		this.ajaxConfig = {
				type : 'GET',
				contentType : 'application/json',
				dataType : 'json',
				headers: {'Cache-Control': 'no-cache', 'Pragma': 'no-cache'},
				data : null,
				context : this,
				success : this.processResp,
				error: this.error
			};
		
		this.initialized = false;
		this.init(cfg);
	};

	DataTable.prototype = new namespace.Widget();

	$.extend(DataTable.prototype, {
		/**
		 * cfg = {
		 * 	table_id: "id",
		 *  fnDataLoadedCallback: function(){},
		 * }
		 */
		init : function(cfg) {
			this.config =  $.extend({}, this.config, cfg);
	
			this.table = $('#' + cfg.tableId);
			this.tableConfig = $.extend({}, cfg.tableConfig);
			this.ajaxConfig = $.extend({}, this.ajaxConfig, {url: this.tableConfig.sAjaxSource, type: this.tableConfig.sServerMethod});
			
			if (!this.tableConfig.bServerSide) {
				delete this.tableConfig.sAjaxSource;
			}
		},
		
		/**
		 * This method should be called after init method.
		 * 
		 * Event binding should be done in init method, this method is used to get dataTable objects.
		 */
		initDataTable: function() {
			if (!this.initialized) {
				this.$dataTable = this.table.dataTable(this.tableConfig);
				this.oDataTable = this.table.DataTable();
				this.initialized = true;
				this.publish("initialized");
			}
		},
	
		/**
		 * Clear table
		 */
		clear : function() {
			if ($.fn.DataTable.fnIsDataTable(this.table.get(0)))
				this.$dataTable.fnClearTable(false);
		},
		
		hide: function() {
			if ($.fn.DataTable.fnIsDataTable(this.table.get(0))) {
				$(this.$dataTable.fnSettings().nTableWrapper).hide();
			} else {
				this.table.hide();
			}
		},
		
		show: function() {
			if ($.fn.DataTable.fnIsDataTable(this.table.get(0))) {
				$(this.$dataTable.fnSettings().nTableWrapper).show();
			} else {
				this.table.show();
			}
		},
	
		/**
		 * If it's the first time to open this tab, send request and get the data from server. Otherwise,
		 * just refresh the table from local cache.
		 * 
		 * @param param
		 */
		update : function(param, toHideLoading) {
			if (this.tableConfig.bServerSide) {
				// Option "fnServerParams" will set the request parameters.
				this.table.fnDraw();
			} else {
				this.ajaxConfig.data = param || {};
				this.ajaxConfig.data.timestamp = Date.now();
				$.ajax(this.ajaxConfig);
				this.publish("ajaxbegin");
			}
		},
	
		error : function() {
			this.publish("error", {message: "Data is non-available, please try it later"});
		},
	
		/**
		 * if data come from server, this method will be used to refresh the table.
		 * @param data
		 */
		processResp : function(data) {
			this.publish("ajaxfinished", data);
			if (data && (data.status == "true" || data.status == true || data.status == "ok")) {
				this.publish("dataloaded", data);
				
				this._preprocessData(data);
				
				if (this.initialized) {
					this.feedTable(data);
				} else {
					this.tableConfig.data = data.data;
					this.initDataTable();
				}				
			} else {
				if (this.initialized) {
					this.$dataTable.fnClearTable();
				}
			}
		},
	
		/**
		 * Resize data table column.
		 */
		resizeTable : function() {
			if (this.initialized) {
				this.oDataTable.columns.adjust();
//				this.$dataTable.fnAdjustColumnSizing();
				var oFixedColumns = this.$dataTable.fnSettings().oFixedColumns;
				if (oFixedColumns) {
	//				oFixedColumns.fnRedrawLayout();
					oFixedColumns._fnGridLayout();
				}
			}
		},
		
		_preprocessData: function(data) {
			if (!data) return;
			
			try {
				if (typeof data.data === "string") {
					data.data = JSON.parse(data.data);
				}
			} catch (e) {
				console.log("Data is not a valid JSON string!");
			}
		},
		
		/**
		 * Use the data to update table, this data object must have "data" property and "aoColumns"
		 * property.
		 * 
		 * @param data
		 */
		feedTable: function(data){
			if (!data) return;
			
			this.publish("beforeDataApplied", data);
			
//			try {
				if (this.initialized) {
					this.$dataTable.fnClearTable();
					if (this.$dataTable.fnClearSearchs) {
						this.$dataTable.fnClearSearchs();
					}
					
					data.data && this.$dataTable.fnAddData(data.data);
					
					this.publish("afterDataApplied", data);
				}
				
				this.resizeTable();
//			} catch(e) {
//				console.error("Failed to feed table: %s", e.message);
//			}
			
		}

	});

	namespace.DataTable = DataTable;
})(BizReport = BizReport || {});