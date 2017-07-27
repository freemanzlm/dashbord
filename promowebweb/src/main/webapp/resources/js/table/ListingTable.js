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
	var fileTypeReg = /\.(doc|docx|xls|xlsx|jpg|gif|zip|rar|pdf)$/i;
	
	/**
	 * Check if file size exceed maximum size (in bytes).
	 */
	function hasValidSize(inputElement, maxSize) {
		if (inputElement && inputElement.files) {
			for (var i = 0; i < inputElement.files.length; i++)	{
				var file = inputElement.files[i];
				if (file && file.size > maxSize) {
					return false;
				} else {
					continue;
				}
			}
		}
		return true;
	}
	
	/**
	 * Check if table has check box for selection.
	 */
	function hasCheckbox(settings) {
		if (!settings || !settings.aoColumns) return false;
		
		for (var i = 0; i < settings.aoColumns.length; i++) {
			var oColumn = settings.aoColumns[i];
			if (oColumn && oColumn.data === 'skuId') {
				return true;
			}
		}
		
		return false;
	}
	
	var defaultDataTableConfigs = {
			tableConfig : {
				'aLengthMenu': [20],
				'bAutoWidth': true,
				'bDeferRender': false,
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
				'sScrollX': true,
				'sScrollY': "600",
				'oLanguage': {
					sZeroRecords: local.getText('dataTable.listing.zeroRecords'),
					sEmptyTable: local.getText('dataTable.listing.emptyTable'),
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
					if (hasCheckbox(settings)) {
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
				fnRowCallback: function(nRow, oRow) {
					nRow.setAttribute("id", "tr-" + oRow.skuId);
				},
				columns: null,
				aoColumnDefs: [{
					aTargets: ["stickyFlag"],
					sType: "numeric",
					sDefaultContent: "0",
					bVisible: false,
					mRender: function(data, type, full, meta) {
						if (type === 'sort') {
							if (typeof data === 'boolean') {
								return data ? 1 : 0;
							} else if (typeof data === 'string') {
								return data == 'true' ? 1 : 0;
							} else {
								return data ? parseInt(data) : 0;
							}
						}
						
						return data;
					}
				},{
					aTargets: ["lockFlag"],
					bVisible: false,
					sType: "numeric",
					sDefaultContent: "",
					sClass: "text-center dt-nowrap",
					mRender: function(data, type, full, meta) {
						if (type === 'display') {
							return data ? local.getText('dataTable.listing.locked') : local.getText('dataTable.listing.unlocked');
						}
						
						if (type === 'sort') {
							return data ? 1 : 0;
						}
						
						return data;
					}
				},{
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
							disabled: (oRow.state == 'ReviewFailed' || oRow.lock == true)
						}));
					}
				},
				{
					aTargets: ["Item_ID_base__c"],
					sDefaultContent: "",					
					sType: "numeric",
					sClass: "pic-id dt-nowrap",
					sDefaultContent: "NA",
					mRender: function(data, type, full, meta) {
						if (type == "display") {
							if(data) {
								// 150px must set on div, it's useless in sWidth or sWidthOrg
								var display = "<div style='width:150px;'><img src='http://thumbs2.ebaystatic.com/pict/" + data + ".jpg' style='height:50px;width:50px;'/>";
								return display += "<p><a href='http://www.ebay.com/itm/" + data + "'>" + data + "</a></p></div>";
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
					aTargets: ["time"],
					sType: "numeric",
					sClass: "text-center",
					sDefaultContent: "NA",
					mRender: function(data, type, full) {
						if (type == "display" && data) {
							return new Date(data).format('HH:mm:ss');
						}
						
						if (type === 'sort') {
							return data ? data : -1;
						}
						
						return data;
					}
				},
				{
					aTargets: ["datetime"],
					sType: "numeric",
					sClass: "text-center",
					sDefaultContent: "NA",
					mRender: function(data, type, full) {
						if (type == "display" && data) {
							return new Date(data).format('yyyy-MM-dd HH:mm:ss');
						}
						
						if (type === 'sort') {
							return data ? data : -1;
						}
						
						return data;
					}
				},
				{
					aTargets: ["date"],
					sType: "numeric",
					sClass: "text-center",
					sDefaultContent: "NA",
					mRender: function(data, type, full) {
						if (type == "display" && data) {
							return new Date(data).format('yyyy-MM-dd');
						}
						
						if (type === 'sort') {
							return data ? data : -1;
						}
						
						return data;
					}
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
					sType: "string",
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
					sType: "string",
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
					aTargets: ["string"],
					sType: "string",
					sClass: "text-right",
					sDefaultContent: ""
				},
				{
					aTargets: ["double"],
					sType: "numeric",
					sClass: "text-right",
					sDefaultContent: "NA",
					mRender: function(data, type, full, meta) {
						var value;
						if (typeof data === 'object' && data != null) {
							value = parseFloat(data.value);
							
							if (type == "display") {
								return isNaN(value) ? 'NA' : (value > 0 ? value : '0') + '(' + data.currency + ')';
							}
							
							if (type == 'sort') {
								return isNaN(value) ? Number.NEGATIVE_INFINITY : value;
							}
							
							return data.value;
						} else {
							value = parseFloat(data);
							if (type == "display") {
								return isNaN(value) ? 'NA' : (value > 0 ? value : '0');
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
					sClass: "text-center dt-nowrap",
					sWidthOrig: "230px",
					sDefaultContent: "NA",
					mRender: function(data, type, full, meta) {
						var key = meta.settings.aoColumns[meta.col].data;
						var readonly = meta.settings.aoColumns[meta.col].bReadonly;
						var disabled = meta.settings.aoColumns[meta.col].bDisabled;
						var disabled = disabled ? 'disabled' : "";
						var id = full.skuId + "-" + key;
						var iframeId = 'iframe-' + id;
						var attachmentLink = data ? '<a class="font-bold attachment" href=/promotion/listings'+data+'>'+local.getText('promo.listings.attachdownload')+'</a>' : "";
						
						if (type == "display") {
							if(readonly) {
								return attachmentLink;
							} else {
								// promoId is stored in meta.settings.oInit.promo object.
								return '<form class="attachment-form" uploading="false" target="'+ iframeId + '" method="post" enctype="multipart/form-data" action="/promotion/listings/uploadListingAttachment"><input type="hidden" value="'+ 
								meta.settings.oInit.promo.promoId+'" name="promoId"/><input type="hidden" name="skuId" value="'+ 
								full.skuId+'" /><input type="hidden" name="key" value="' + key + '"/><span class="file-input"><input type="text" style="height: 22px;" value="" placeholder="选择文件" /> <input type="file" name="uploadFile" '+
								disabled+'/></span><button class="btn submit-btn" type="button">上传</button></form>' +
								'<iframe name="'+ iframeId + '" src="about:blank" frameborder="0" style="display: none;"></iframe>' +
								'<span class="msg font-bold">' + attachmentLink + '</span>';
							}
						}
						
						if (type == 'sort') {
							return !data ? 0 : 1;
						}

						return data;
					},
					fnCreatedCell: function(nTd, sData, oRow, iRowIndex, iColIndex) {
						var $nTd = $(nTd), settings = this.fnSettings();
						var key = settings.aoColumns[iColIndex].data, id = oRow.skuId + "-" + key;
						var required = settings.aoColumns[iColIndex].bRequired;
						var listingIframe = $nTd.find("iframe[name=iframe-" + id +"]");
						var $attachForm = $nTd.find(".attachment-form").attr({rowIndex: iRowIndex, colIndex: iColIndex});
						var $fileUploadBtn = $attachForm.find(".submit-btn");
						var errorMsgEle = $nTd.find(".msg");
						
						required && $attachForm.attr("required", required);
						
						if ($attachForm && $attachForm.length > 0) {
							$attachForm.submit(function(){
								var fileInput = $attachForm.find("input[type=file]");
								var fileName = fileInput.val();
								
								if(required) { //attachment is a must
									if(!fileName && !oRow[key]) {
										errorMsgEle.css({"color": "red"}).html(local.getText("promo.listings.notEmpty"));
										$attachForm.attr("hasError", true);
										return false;
									}
								}
								
								if (!fileName) return false;
								
								if (!fileTypeReg.test(fileName)) { // check attachment file type
									errorMsgEle.css({"color": "red"}).html(local.getText("promo.listings.typeError"));
									$attachForm.attr("hasError", true);
									return false;
								}
								
								if (!hasValidSize(fileInput.get(0), 3145728)) {
									// attachment size should be less than 3M.
									errorMsgEle.css({"color": "red"}).html(local.getText("promo.listings.attachmentSizeError"));
									$attachForm.attr("hasError", true);
									return false;
								}
								
								$attachForm.attr('uploading', !!fileName);
								$nTd.isLoading({text: local.getText('dataTable.handling'), position: "inside"});
								
								// get attachment upload result.
								listingIframe.on("load", function(){
									$attachForm.attr('uploading', false);
									$nTd.isLoading('hide');
									if (listingIframe.contents().length != 0 && listingIframe.contents().find("body").html().length > 0) {
										var response = listingIframe.contents().find("body").text();
										var responseData = $.parseJSON(response);
										if (responseData.message && responseData.message.length > 0) {
											// If uploading success, response should have attachemnt download URL.
											if(responseData.status==true) {
												errorMsgEle.html('<a id="href'+oRow.skuId+'" href=/promotion/listings'+responseData.message+'>'+local.getText('promo.listings.attachdownload')+'</a>');
												oRow[key] = responseData.message;
												$attachForm.find(".file-input input").val(""); // clear input[type=file] input value
												$attachForm.attr("hasError", false);
											} else {
												errorMsgEle.css({"color": "red"}).html(responseData.message);
												$attachForm.attr("hasError", true);
											}
										} else if(responseData.status == false) {
											errorMsgEle.css({"color": "red"}).html(local.getText("attachmentUploadFailed"));
											$attachForm.attr("hasError", true);
										}
									}
								});
								return !!fileInput.attr("value");
							});
							
							$fileUploadBtn.click(function(event){
								event.preventDefault();
								var fileDir = $attachForm.find("input[type=file]").val();
								if(!fileDir) {
									return false;
								} else {
									//validate file type
									if (!fileTypeReg.test(fileDir)) {
										errorMsgEle.css({"color": "red"}).html(local.getText("promo.listings.typeError"));
										return false;
									} else {
										$attachForm.submit();
									}
								}
							});	
						}
					}
				},
				{
					aTargets: ["state"],
					bSortable: true,
					sClass: "text-center dt-nowrap",
					sDefaultContent: "NA",
					sWidthOrig: "75",
					mRender: function(data, type, full) {
						var lockLabel = full.lock ? local.getText('dataTable.listing.locked') : '';
						var stateLabel = !!data ? local.getText('listing.state.' + data) : '';
						
						if(data == 'ReEnroll') {
							stateLabel = '<span class="color-red">'+local.getText('listing.state.' + data)+'</span>';
						}
						
						if (type == "display") {
							if (full.lock) {
								return stateLabel + (!!stateLabel ? "<br/>" : "") + lockLabel;
							}
							return stateLabel;
						}
						
						if (type == "sort") {
							if(full.lock == true) {
								return 4;
							}
							
							switch (data) {
							case 'ReEnroll':
								return 3;
							case 'Enrolled':
							case 'ReviewPassed':
								return 2;
							case 'CanEnroll':
							case 'NotEnrolled':
							case 'ReviewFailed':
								return 0;
							case 'Reviewing':
								return 1;
							default: return -1;
							}
						}

						return data;
					}
				},
				{
					aTargets: ["Auction_Title_base__c"],
					sType: "string",
					sClass: "text-left",
					sWidthOrig: "350",
					sDefaultContent: "NA",
					mRender: function(data, type, full, meta) {
						if (type == "display") {
							if(data) {
								if(full.Item_ID_base__c) {
									return "<p><a href='http://www.ebay.com/itm/" + full.Item_ID_base__c + "'>" + data + "</a></p>";
								} else {
									return "<p>"+data+"</p>";
								}
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
			this.promo = this.dataTableConfig.tableConfig.promo || {};
			
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
				    
				    if (data && data.data) {
				    	data.data = data.data.filter(function(oRow) {
				    		if(!that.promo.regType) {
				    			if(that.promo.currentStep == 'SELLER NOMINATION_NEED APPROVE' || that.promo.currentStep == 'SELLER FEEDBACK' || that.promo.currentStep == 'PROMOTION SUBMITTED') {
									if(that.promo.isRegEnd == false && that.promo.isListingPreview != true) {
										// promotion upload type
										return (oRow.state != 'CanEnroll' && oRow.state!='NotEnrolled');
									}
								}
								if(that.promo.currentStep == 'PROMOTION IN PROGRESS' && that.promo.isRegEnd == false) {
									if(that.promo.isListingPreview != true) {
										return (oRow.state != 'CanEnroll' && oRow.state!='NotEnrolled');
									}
								}
				    		}
							
							return true;
						});
				    }
				    
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
					return !(listing.state == 6 || listing.state == 'PretrialFail' || listing.lock == true);
				});
				
				var lockRows = oDataTable.data().filter(function(listing) {
					return !!(listing.lock == true && listing.state == 'Enrolled');
				}) ;
				
				aRows.each(function(item){
					// mark each row is selected
					item.checked = checkbox.checked;
				});
				
				var enabledBoxes = that.dataTable.table.find("input[name=item]:enabled").prop("checked", this.checked);
				
				if (this.checked) {
					that.selectedItems = Array.prototype.slice.apply(aRows) || [];
					var lockRowArray = Array.prototype.slice.apply(lockRows) || [];
					for(var i=0; i<lockRowArray.length; i++) {
						that.selectedItems.push(lockRowArray[i]);
					}
					//that.selectedItems.push(Array.prototype.slice.apply(lockRows) || []);
					enabledBoxes.parents('tr').addClass("selected");
					that.dataTable.table.find("input[type=file]").prop('disabled', false);
				} else {
					that.selectedItems.splice(0); // empty selectedItems
					var lockRowArray = Array.prototype.slice.apply(lockRows) || [];
					for(var i=0; i<lockRowArray.length; i++) {
						that.selectedItems.push(lockRowArray[i]);
					}
					//that.selectedItems.push(Array.prototype.slice.apply(lockRows) || []);
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