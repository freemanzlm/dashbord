BizReport = BizReport || {};

BizReport.locale = {
	'export': {
		'exporting': '<p>後臺正在生成導出文件，可能需要一點時間，請耐心等待。<br/>文件開始下載，此潭出對話框會自動關閉，您也可以選擇直接關閉。</p>',
		'fail': '<p>導出失敗，請稍后再試。</p>'
	},
	'loader': {
		'loading': '正在加載，請稍后...',
		'fail': '<div class="text-center">抱歉，數據繁忙，請稍後重試。</div>'
	},
	'filter': {
		'noBrandData': '沒有符合篩選條件的SKU。'
	},
	'dialog': {
		'hint': '提醒'
	},
	'highchart': {
		'noData': '當前分類暫時沒有分析數據，請嘗試瀏覽其他分類。'
	},
	'dataTable': {
		'emptyTable': '<p>沒有相關數據，如有疑問，請聯係您的客戶經理。</p>',
		'open': '展開',
		'close': '收起',
		'info': '當前顯示 _START_ 至 _END_，總共_TOTAL_條',
		'loading': '請稍等，正在加載...',
		'handling': '處理中。。。',
		'firstPage': '第一頁',
		'lastPage': '最後一頁',
		'previousPage': '上一頁',
		'nextPage': '下一頁',
		'requestFail': '請求失敗，請檢查網絡是否連接正確，如果不是網絡問題，請稍后再試。',
        'site': {
            'us': '美國站',
            'uk': '英國站',
            'de': '德國站',
            'au': '澳洲站',
            'other': '其他站點'
        }
	}
};

BizReport.locale.getText = function(key, params) {
    var names = key.split(".");
    var value = BizReport.locale;
    for (var i = 0; i < names.length; i++) {
        value = value[names[i]];
        if (!value) break;
    }
    
    if (params) {
        for (var ii = 0; ii < params.length; ii++) {
            var reg = new RegExp ("\\{" + ii + "\\}", "g");
            value = value.replace(reg, params[ii]);
        }
    }
    
    return value ? value : key;
};