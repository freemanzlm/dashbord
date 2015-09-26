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
	'currency': {
		'RMB': '人民幣'
	},
	'dataTable': {
		'emptyTable': '<p>沒有相關資料，如果是網絡問題，請稍後再試。</p>',
		'zeroRecords': '<p>沒有相關資料。</p>',
		'open': '展開',
		'close': '收起',
		'info': '當前顯示 _START_ 至 _END_，總共_TOTAL_條',
		'loading': '請稍等，正在加載...',
		'handling': '處理中。。。',
		'firstPage': '第一頁',
		'lastPage': '最後一頁',
		'previousPage': '上一頁',
		'nextPage': '下一頁',
		'requestFail': '表格數據請求失敗，請檢查網絡是否連接正確。如果不是網絡問題，請稍後再試。',
        'site': {
            'us': '美國站',
            'uk': '英國站',
            'de': '德國站',
            'au': '澳洲站',
            'other': '其他站點'
        },
        'listing': {
			'info': '當前顯示_START_至_END_，總共_TOTAL_條刊登'
		},
		'skuList': {
			'info': '當前顯示_START_至_END_，總共_TOTAL_條SKU'
		},
		'promo': {
			'SubsidyCounting': '待評估',
			'noReward': '無獎勵',
			'emptyTable': '<p>沒有符合篩選條件的活動。</p>',
			'zeroRecords': '<p>沒有符合篩選條件的活動。</p>',
		}
	},
	'promo': {
		'type': {
			'deals': 'Deals招募',
			'dealsPreset': 'Deals招募預置',
			'hotsell': '爆款促銷',
			'other': '其它活動'
		},
		'state': {
			'Created': '可報名',
			'Submitted': '已提交預審',
			'Verifying': '報名稽核中',
			'PromotionApproved': '確認報名刊登',
			'VerifyFailed': '稽核失敗',
			'Applied': '已提交報名',
			'Started': '活動進行中',
			'SubsidyCounting': '獎勵確認中',
			'SubsidyWaiting': '可申領獎勵',
			'SubsidyAccessed': '待填寫協定',
			'SubsidySubmitted': '待上傳協定',
			'SubsidyUploaded': '獎勵稽核中',
			'SubsidyRetrievable': '領取獎勵',
			'SubsidyResubmittable': '重新申領獎勵',
			'SubsidyRetrieved': '領取獎勵成功',
			'SubsidyRetrieveFailed': '獎勵領取失敗',	
			'SubsidyExpired': '獎勵領取過期',
			'Canceled': '活動已取消',
			'End': '活動已結束',
			'Claimed': '我已領取獎勵'
		},
		'hotsell': {
			'zeroSubmitted': '您沒有勾選任何刊登，這意味著您將取消報名，確認嗎？',
			'applyCondition': '申請參與活動需要選擇至少一條刊登報名。'
		},
		'deals': {
			'upload': '提交刊登報名前請先閱讀其他條款，並<strong>全部讀完</strong>，然後點擊確認接受該其他條款後方可提交報名。',
			'onlyXls': '您選擇提交的不是Excel檔案，請重新選擇刊登檔案.'
		},
		'request': {
			'fail': '請求提交失敗',
			'sending': '請求已發送，請稍候。。。'
		}
	},
	'listing': {
		'state': {
			'Applicable': '可報名',
			'Applied': '已報名', // hot sell and deals preset
			'Nonapplied': '未報名',
			'AuditSuccess': '通過審核',
			'Pretrial': '待審核', // deals
			'PretrialPass': '通過預審',
			'PretrialFail': '未通過',
			'Confirmed': '已確認報名',
			'Nonsubmitted': '未提交'
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