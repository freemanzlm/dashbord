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
	var ListingTable = function() {};
	ListingTable.prototype = new cbt.Widget();
	
	var local = namespace.local;
	
	var defaultDataTableConfigs = {
			tableConfig : {
				'aLengthMenu': [20],
				'bAutoWidth': true,
				'bDeferRender': true,
				'bFilter': false,
				'bLengthChange': false,
				'bDestroy': true,
				'bPaginate': false,
				'bServerSide': false,
				'bSortCellsTop': true,
				'bSort': true,
				'iDisplayLength': 10,
				'sPaginationType': 'full_numbers',
				'sDom': '<"datatable_header"rf>t<"datatable_pager clr"ip>',
				'bScrollCollapse': true,
				'sScrollX': '100%',
				'sScrollY': "600",
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
				sAjaxSource: "/promotion/listings/getPromotionListings", //"/promotion/js/data/dealsListing.json"
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
				'fnPreDrawCallback': function(nHead, aData, iStart, iEnd, aiDisplay){
					// preinitialize threshold value
					var headers = this.fnSettings().aoHeader[0];
					for (var i in headers) {
						var col_header = headers[i];
						if (col_header && col_header.cell) {
							col_header.required = col_header.cell.hasAttribute('required');
						}
					}
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
					
					$(".file-input").each(function(){
						if (this.hasAttribute("inited")) return;
						
						var fileBox = $(this), textInput = fileBox.find("[type=text]");
						var fileInput = fileBox.find("[type=file]").change(function(){
							textInput.attr("value", this.value);
						}).width(textInput.width());
						fileBox.find(".btn").click(function(){
							fileInput.trigger("click");
						});
						
						this.setAttribute('inited', true);
					});
				},
				columns: null,
				aoColumnDefs: [{
					aTargets: ["check"],
					bSortable: false,
					sDefaultContent: "",
					sType: "numeric",
					sWidth: "30px",
					sClass: "text-center",
					fnCreatedCell: function(nTd, sData, oRow, iRowIndex) {
						oRow.checked = oRow.checked || (oRow.state == 'Enrolled');

						$(nTd).html($("<input type=checkbox name=item>").attr({
							value:sData,
							rowindex : iRowIndex,
							checked: oRow.checked,
							disabled: (oRow.state == 'ReviewFailed')
						}));
					}
				},
				{
					aTargets: ["Item_ID_base__c"],
					sDefaultContent: "",					
					sType: "numeric",
					sWidth: "150px",
					sClass: "pic-id",
					sDefaultContent: "NA",
					mRender: function(data, type, full, meta) {
						if (type == "display") {
							if(data) {
								var display = "<img src='http://thumbs2.ebaystatic.com/pict/" + data + ".jpg' height='50' width='50'/>";
								return display += "<p><a href='http://www.ebay.com/itm/" + data + "'>" + data + "</a></p>";
							}
						}
						
						return data;
					}					
				},
				{
					aTargets: ["integer"],
					sType: "numeric",
					sClass: "text-right",
					sDefaultContent: "NA",
					mRender: function(data, type, full) {
						var value = parseInt(data);
						
						if (type == "display") {
							return isNaN(value) ? 'NA' : value;
						}
						
						if (type == 'sort') {
							return isNaN(value) ? Number.NEGATIVE_INFINITY : value;
						}
						return data;
					}
				},
				{
					aTargets: ["date", "datetime", "time"],
					sType: "date",
					sClass: "text-center",
					sDefaultContent: "NA"
				},
				{
					aTargets: ["percent"],
					sType: "numeric",
					sClass: "text-right",
					sDefaultContent: "NA",
					mRender: function(data, type, full) {
						var value = parseInt(data);
						if (type == "display") {
							return isNaN(value) ? 'NA' : value.toUSFixed(2) + "%";
						}
						
						if (type == 'sort') {
							return isNaN(value) ? Number.NEGATIVE_INFINITY : value;
						}
						return data;
					}
				},
				{
					aTargets: ["email"],
					sType: "date",
					sClass: "text-right",
					sDefaultContent: "NA",
					mRender: function(data, type, full) {
						if (type == "display" && data) {
							return '<a href="mailto:' + data + '">' + data + '</a>';
						}
						
						return data;
					}
				},
				{
					aTargets: ["url"],
					sType: "date",
					sClass: "text-right",
					sDefaultContent: "NA",
					mRender: function(data, type, full) {
						if (type == "display" && data) {
							return '<a href="' + data + '" target="_blank">' + data + '</a>';
						}
						
						return data;
					}
				},
				{
					aTargets: ["currency"],
					sType: "numeric",
					sClass: "text-right",
					sDefaultContent: ""
				},
				{
					aTargets: ["double"],
					sType: "numeric",
					sClass: "text-right",
					sDefaultContent: "NA",
					mRender: function(data, type, full) {
						var value;
						if (typeof data === 'object' && data != null) {
							value = parseFloat(data.value);
							
							if (type == "display") {
								return isNaN(value) ? 'NA' : (value > 0 ? value.toUSFixed(2) : '0') + '(' + data.currency + ')';
							}
							
							if (type == 'sort') {
								return isNaN(value) ? Number.NEGATIVE_INFINITY : value;
							}
							
							return data.value;
						} else {
							value = parseFloat(data);
							if (type == "display") {
								return isNaN(value) ? 'NA' : (value > 0 ? value.toUSFixed(2) : '0');
							}
							
							if (type == 'sort') {
								return isNaN(value) ? Number.NEGATIVE_INFINITY : value;
							}
							
							return data;
						}
					}
				},
				{
					aTargets: ["attachment"],
					sType: "string",
					sClass: "text-center",
					sWidth: "230px",
					sDefaultContent: "NA",
					mRender: function(data, type, full) {
						if (type == "display") {
							var id = 'iframe'+ full.skuId;
							var str = 'disabled';
							var urlStr = '';
							if(full.state && full.state== 'Enrolled') {
								str = '';
							}
							if(pageData.isPreview && pageData.isPreview == 'true') {
								str = '';
							}
							if (full.uploadSuccess && full.downloadUrl) {
								return '<a href=/promotion/listings'+full.downloadUrl+'>'+local.getText('promo.listings.attachdownload')+'</a>';
							} else {
								return '<form id="form' + full.skuId + '" target="'+ id + '" method="post" enctype="multipart/form-data" action="/promotion/listings/uploadListingAttachment"><input type="hidden" value="'+pageData.promoId+'" name="promoId"/><input type="hidden" name="skuId" value="'+full.skuId+'" /><span class="file-input"><input type="text" style="height: 22px;" placeholder="选择文件" /> <input type="file" name="uploadFile" '+str+'/></span><button class="btn" id="btn'+full.skuId+'" type="button">上传</button></form>' +
								'<iframe name="'+ id + '" src="about:blank" frameborder="0" style="display: none;"></iframe>' +
									'<span class="hide" id="msg'+full.skuId+'"><b></b></span>';
							}
						}
						
						if (type == 'sort') {
							return !data ? 0 : 1;
						}

						return data;
					},
					fnCreatedCell: function(nTd, sData, oRow, iRowIndex, iColIndex) {
						var required = this.fnSettings().aoHeader[0][iColIndex].required;
						var listingBtn = $(nTd).find("#btn"+oRow.skuId);
						var listingIframe = $(nTd).find("iframe[name=iframe"+oRow.skuId+"]");
						/*if(pageData.hasListingsNominated) {
							$(nTd).find("#form"+oRow.skuId).remove();
						}*/
						if ($(nTd).find("#form"+oRow.skuId)) {
							if((!pageData.isPreview || pageData.isPreview != 'true') && pageData.regType == 'false') {
								$(nTd).find("#form"+oRow.skuId).remove();
							}
							if(oRow.hasUploaded) {
								$(nTd).find("#msg"+oRow.skuId).removeClass("hide");
								$(nTd).find("#msg"+oRow.skuId).find("b").html('<a id="href'+oRow.skuId+'" href=/promotion/listings'+oRow.downloadAttachUrl+'>'+local.getText('promo.listings.attachdownload')+'</a>');
								oRow.uploadSuccess = true;
								oRow.downloadUrl = oRow.downloadAttachUrl;
							}
							
							var listingForm = $(nTd).find("#form"+oRow.skuId).submit(function(){
							//$(nTd).find("#msg"+oRow.skuId).find("b").empty();
							var fileInput = $(nTd).find("#form"+oRow.skuId).find("input[type=file]");
							var fileName = fileInput.val();
							//文件不能为空
							if(required) {
								if(!fileName) {
									$(nTd).find("#msg"+oRow.skuId).removeClass("hide");
									$(nTd).find("#msg"+oRow.skuId).css({"color": "red"});
									$(nTd).find("#msg"+oRow.skuId).find("b").html(local.getText("promo.listings.notEmpty"));
									return false;
								}
								//文件类型校验
								if (fileName.indexOf(".xls")<0 && fileName.indexOf(".pdf")<0
										&& fileName.indexOf(".doc")<0 && fileName.indexOf(".docx")<0 && fileName.indexOf(".xlsx")<0
										&& fileName.indexOf(".jpg")<0 && fileName.indexOf(".JPG")<0 && fileName.indexOf(".gif")<0
										&& fileName.indexOf(".xls")<0 && fileName.indexOf(".zip")<0 && fileName.indexOf(".rar")<0 
										&& fileName.indexOf(".pdf")<0 && fileName.indexOf(".GIF")<0 && fileName.indexOf(".ZIP")<0 
										&& fileName.indexOf(".RAR")<0 && fileName.indexOf(".DOC")<0 && fileName.indexOf(".DOCX")<0 
										&& fileName.indexOf(".XLSX")<0 && fileName.indexOf(".PDF")<0 && fileName.indexOf(".XLS")<0){
									$(nTd).find("#msg"+oRow.skuId).removeClass("hide");
									$(nTd).find("#msg"+oRow.skuId).css({"color": "red"});
									$(nTd).find("#msg"+oRow.skuId).find("b").html(local.getText("promo.listings.typeError"));
									return false;
								}
							}
							
							$(nTd).isLoading({text: local.getText('dataTable.handling'), position: "inside"});
							
							listingIframe.on("load", function(){
								$(nTd).isLoading('hide');
								if (listingIframe.contents().length != 0 && listingIframe.contents().find("body").html().length > 0) {
									var response = listingIframe.contents().find("body").text();
									var responseData = $.parseJSON(response);
									if (responseData.message && responseData.message.length > 0) {
										$(nTd).find("#msg"+oRow.skuId).removeClass("hide");
										if(responseData.status==true) {
											//$("#form"+oRow.skuId).remove();
											$(nTd).find("#msg"+oRow.skuId).find("b").html('<a id="href'+oRow.skuId+'" href=/promotion/listings'+responseData.message+'>'+local.getText('promo.listings.attachdownload')+'</a>');
											oRow.uploadSuccess = true;
											oRow.downloadUrl = responseData.message;
										} else {
											$(nTd).find("#msg"+oRow.skuId).css({"color": "red"});
											$(nTd).find("#msg"+oRow.skuId).find("b").html(responseData.message);
										}
									} 
									else {
										$(nTd).find("#msg"+oRow.skuId).removeClass("hide");
										$(nTd).find("#msg"+oRow.skuId).css({"color": "red"});
										$(nTd).find("#msg"+oRow.skuId).find("b").html("upload file failed!");
									}
								}
							});
							return !!$(this).find("input[type=file]").attr("value");
						});
						
						$(listingBtn).click(function(event){
							event.preventDefault();
							listingForm.submit();
						});	
						}
					}
				},
				{
					aTargets: ["state"],
					bSortable: false,
					sClass: "text-center",
					sDefaultContent: "NA",
					width: "75",
					mRender: function(data, type, full) {
						if (type == "display") {
							return !!data ? local.getText('listing.state.' + data) : '';
						}
						
						if (type == "sort") {
							switch (data) {
							case 'Enrolled':
							case 'ReviewPassed':
								return 2;
							case 'CanEnroll':
							case 'NotEnrolled':
							case 'ReviewFailed':
								return 1;
							case 'Reviewing':
								return 0;
							default: return -1;
							}
						}

						return data;
					}
				}] 
			}
		};
	
	$.extend(ListingTable.prototype, {
		init: function(config) {
			var that = this;

			// deep copy, objects in array will also be extended by their appeared order. 
			this.dataTableConfig = $.extend(true, {}, defaultDataTableConfigs, config.dataTableConfig);

			this.dataTableConfig.tableConfig = $.extend(false, {}, this.dataTableConfig.tableConfig, this.dataTableConfig.customTableConfig);
	
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
						oRow.checked = oRow.state == 'Applied' || oRow.state == 'Confirmed' || oRow.state == 'Enrolled';
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
				    
				    data.data = data.data.filter(function(oRow) {
						if((pageData.isRegEnd == 'false') && (!pageData.isPreview || pageData.isPreview != 'true')) {
							if(pageData.regType=='false') {
								return oRow.state != 'CanEnroll';
							}
						}
						return true;
					});
				    
				    if (data && data.status) {
				        that.container.find(".datatable_pager").show();
				    } else {
				    	// initialize the empty table.
				    	that.dataTable.again && that.initDataTable();
				        
				        that.container.find(".datatable_pager").hide();
				        that.dataTable.updateAgain();
				    }
				    
				    if (config.fnDataUpdatedCallback) {config.fnDataUpdatedCallback.call(that, data);}
				},
				error: function(data) {
				    that.container.isLoading('hide');
				    
				 // initialize the empty table only when it's updated the second time.
				    that.dataTable.again && that.initDataTable();				 
				    that.dataTable.updateAgain();
//					namespace.cbt.alert(local.getText('dataTable.requestFail'));
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
				
				var enabledBoxes = that.dataTable.table.find("input[name=item]:enabled").prop("checked", this.checked);
				
				if (this.checked) {
					that.selectedItems = Array.prototype.slice.apply(aRows) || [];
					enabledBoxes.parents('tr').addClass("selected");
					that.dataTable.table.find("input[type=file]").prop('disabled', false);
				} else {
					that.selectedItems.splice(0); // empty selectedItems
					enabledBoxes.parents('tr').removeClass("selected");
					that.dataTable.table.find("input[type=file]").prop('disabled', true);
				}
				
				that.publish("selectChange");
			});
			
			$("input[name=item]:enabled", this.container.get(0)).live("click", function(){
				if (!oDataTable) return;
				
				var parentTr = $(this).parents("tr");
				var attachInput = parentTr.find("input[type=file]");
				var oData = oDataTable.row(this.getAttribute("rowindex")).data();
				oData.checked = this.checked;
				
				if (this.checked) {
					attachInput.prop('disabled', false);
					that.selectedItems.push(oData);
					parentTr.addClass("selected");
					that.checkAllBox.prop("checked", that.selectedItems.length == that.getDataSize());
				} else {
					attachInput.prop('disabled', true);
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
		
		getData: function() {
			return this.oDataTable.data();
		},
		
		setData: function(data) {
			this.oDataTable.clear();
			this.oDataTable.rows.add(data).draw();
			this.publish("dataupdated");
		},
		
		getDataSize: function() {
			return this.oDataTable.data().length;
		}
	});
	
	namespace.ListingTable = ListingTable;
})(BizReport = BizReport || {});