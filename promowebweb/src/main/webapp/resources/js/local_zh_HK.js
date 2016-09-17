BizReport = BizReport || {};

BizReport.local = {
	'export': {
		'exporting': '<p>後台正在生成導出文件，可能需要一點時間，請耐心等待。<br/>文件開始下載，此彈出對話框會自動消失，您也可以選擇直接關閉。</p>',
		'fail': '<p>導出失敗，請稍後再試。</p>'
	},
	'loader': {
		'loading': '正在加載，請稍候...',
		'fail': '<div class="text-center">抱歉，數據繁忙，請稍後刷新。</div>'
	},
	'filter': {
		'noBrandData': '沒有符合篩選條件的SKU。'
	},
	'dialog': {
		'hint': '提醒'
	},
	'highchart': {
		'noData': '當前分類暫時沒有分析數據，請嘗試瀏覽其它分類。'
	},
	'currency': {
		'RMB': '人民幣'
	},
	'dataTable': {
		'emptyTable': '<p>沒有相關數據，如果是網絡問題，請稍後再試。</p>',
		'zeroRecords': '<p>沒有相關數據。</p>',
		'open': '展開',
		'close': '收起',
		'info': '當前顯示 _START_ 至 _END_，總共_TOTAL_條',
		'infoFiltered': '所有記錄總共_TOTAL_條',
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
		    'au': '澳大利亞',
		    'other': '其他站點'
		},
		'listing': {
			'info': '當前顯示 _START_ 至 _END_，總共_TOTAL_條刊登'
		},
		'skuList': {
			'info': '當前顯示 _START_ 至 _END_，總共_TOTAL_條SKU'
		},
		'promo': {
			'info': '當前顯示 _START_ 至 _END_，總共_TOTAL_項活動',
			'infoFiltered': ' ',
			'SubsidyCounting': '待評估',
			'noReward': '無獎勵',
			'emptyTable': '<p>沒有符合篩選條件的活動。</p>',
			'zeroRecords': '<p>沒有符合篩選條件的活動。</p>',
		}
	},
	'promo': {
		'step': {
			'null': '無',
			'DRAFT': '草稿',
			'NOMINATION EDM IN APPROVE FLOW': '報名階段',
			'SELLER NOMINATION_NEED APPROVE' : '報名階段',
			'PROMOTION SUBMITTED': '審核階段',
			'PROMOTION APPROVED': '審核階段',
			'SELLER FEEDBACK': '報名階段',
			'PROMOTION IN PROGRESS': '活動進行階段',
			'PROMOTION IN VALIDATION': '獎勵審核階段',
			'PROMOTION VALIDATED': '申領獎勵階段',
			'PROMOTION END': '活動結束階段'
		},
		'state': {
			'Created': '報名',
			'Submitted': '已提交預審',
			'Verifying': '預審進行中',
			'PromotionApproved': '正式報名',
			'VerifyFailed': '審核失敗',
			'Applied': '已報名',
			'Started': '活動進行中',
			'SubsidyCounting': '獎勵確認中',
			//'SubsidyWaiting': '申領獎勵',
			//'SubsidyAccessed': '待填寫協議',
			//'SubsidySubmitted': '待上傳協議',
			//'SubsidyUploaded': '申領審核中',
			//'SubsidyRetrievable': '領取獎勵',
			//'SubsidyResubmittable': '重新申領獎勵',
			//'SubsidyRetrieved': '領取獎勵成功',
			//'SubsidyRetrieveFailed': '獎勵領取失敗',	
			//'SubsidyExpired': '獎勵領取過期',
			'Canceled': '活動已取消',
			'Claimed': '我已領取獎勵',
			'Detailed': '查看詳情',
			'ReEnroll':'重新報名',
			'Enrolled':'已提交報名',
			'NotEnrolled':'活動報名',
			'Reviewing':'報名審核中',
			'ReviewPassed':'正式報名',
			'RewardAuditing':'獎勵審核中',
			'InProgress':'活動進行中',
			'Awarding':'申領獎勵',
			//'ReAwarding':'重新領取獎勵',
			'End':'活動已結束',
			'Visited':'待填寫協議',
			'AppliableAgain':'重新申領獎勵',
			'Commited':'待上傳協議',
			'Appliable':'領取獎勵',
			'Uploaded':'申領審核中',
			'Failed':'獎勵領取失敗',
			'Expired':'獎勵領取過期',
			'Applied':'領取獎勵成功'
		},
		'listings': {
			'zeroSubmitted': '您沒有勾選任何刊登，這意味著您將取消報名，確認嗎？',
			'applyCondition': '申請參與活動需要選擇至少一條刊登報名。',
			'onlyXls': '您選擇提交的不是Excel文件，請重新選擇刊登文件.',
			'rrpLink': '連結',
			'typeError' : '文件類型不合法',
			'notEmpty' : '上傳文件不能為空',
			'needCheck' : '請勾選左邊的複選框',
			'attachdownload' : '下載附件'
		},
		'request': {
			'fail': '請求提交失敗',
			'sending': '請求已發送，請稍候。。。',
			'counting' : '已成功上傳文件{0}/{1}'
		}
	},
	'listing': {
		'state': {
			'null':'N/A',
			'CanEnroll':'可報名',
			'Enrolled':'已報名',
			'NotEnrolled':'未報名',
			'Reviewing':'審核中',
			'ReviewPassed':'審核通過',
			'ReviewFailed':'審核未通過',
			'ReEnroll':'重新報名'
		},
		attachment: '附件'
	},
	'errorMsg': {
		'regDateExpired': '很遺憾，報名已經截止，如需繼續報名，請聯繫客服！',
		'uploadListingError': '上傳刊登失敗，請聯繫客服瞭解詳細情況！'
	}	
};

BizReport.local.getText = function(key, params) {
    var names = key.split(".");
    var value = BizReport.local;
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