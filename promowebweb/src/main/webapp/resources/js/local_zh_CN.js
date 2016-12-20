BizReport = BizReport || {};

BizReport.local = {
	'export': {
		'exporting': '<p>后台正在生成导出文件，可能需要一点时间，请耐心等待。<br/>文件开始下载，此弹出对话框会自动消失，您也可以选择直接关闭。</p>',
		'fail': '<p>导出失败，请稍后再试。</p>'
	},
	'loader': {
		'loading': '正在加载，请稍候...',
		'fail': '<div class="text-center">抱歉，数据繁忙，请稍后刷新。</div>'
	},
	'filter': {
		'noBrandData': '没有符合筛选条件的SKU。'
	},
	'dialog': {
		'hint': '提醒'
	},
	'highchart': {
		'noData': '当前分类暂时没有分析数据，请尝试浏览其它分类。'
	},
	'currency': {
		'RMB': '人民币'
	},
	'dataTable': {
		'emptyTable': '<p>没有相关数据，如果是网络问题，请稍后再试。</p>',
		'zeroRecords': '<p>没有相关数据，如果是网络问题，请稍后再试。。</p>',
		'open': '展开',
		'close': '收起',
		'info': '当前显示 _START_ 至 _END_，总共_TOTAL_条',
		'infoFiltered': '所有记录总共_TOTAL_条',
		'loading': '请稍等，正在加载...',
		'handling': '处理中。。。',
		'firstPage': '第一页',
		'lastPage': '最后一页',
		'previousPage': '上一页',
		'nextPage': '下一页',
		'requestFail': '表格数据请求失败，请检查网络是否连接正确。如果不是网络问题，请稍后再试。',
		'site': {
		    'us': '美国站',
		    'uk': '英国站',
		    'de': '德国站',
		    'au': '澳大利亚',
		    'other': '其他站点'
		},
		'listing': {
			'info': '当前显示 _START_ 至 _END_，总共_TOTAL_条刊登',
			'emptyTable': '<p>没有相关报名刊登数据,如果是网络问题，请稍后再试。</p>',
			'zeroRecords': '<p>没有相关报名刊登数据,如果是网络问题，请稍后再试。</p>'
		},
		'promo': {
			'info': '当前显示 _START_ 至 _END_，总共_TOTAL_项活动',
			'infoFiltered': ' ',
			'SubsidyCounting': '待评估',
			'noReward': '无奖励',
			'emptyTable': '<p>没有符合筛选条件的活动。</p>',
			'zeroRecords': '<p>没有符合筛选条件的活动。</p>',
		}
	},
	'promo': {
		'step': {
			'null': '无',
			'DRAFT': '草稿',
			'NOMINATION EDM IN APPROVE FLOW': '报名阶段',
			'SELLER NOMINATION_NEED APPROVE' : '报名阶段',
			'PROMOTION SUBMITTED': '审核阶段',
			'PROMOTION APPROVED': '审核阶段',
			'SELLER FEEDBACK': '报名阶段',
			'PROMOTION IN PROGRESS': '活动进行阶段',
			'PROMOTION IN VALIDATION': '奖励审核阶段',
			'PROMOTION VALIDATED': '申领奖励阶段',
			'PROMOTION END': '活动结束阶段'
		},
		'state': {
			'Created': '报名',
			'Submitted': '已提交预审',
			'Verifying': '预审进行中',
			'PromotionApproved': '正式报名',
			'VerifyFailed': '审核失败',
			'Applied': '已报名',
			'Started': '活动进行中',
			'SubsidyCounting': '奖励确认中',
			//'SubsidyWaiting': '申领奖励',
			//'SubsidyAccessed': '待填写协议',
			//'SubsidySubmitted': '待上传协议',
			//'SubsidyUploaded': '申领审核中',
			//'SubsidyRetrievable': '领取奖励',
			//'SubsidyResubmittable': '重新申领奖励',
			//'SubsidyRetrieved': '领取奖励成功',
			//'SubsidyRetrieveFailed': '奖励领取失败',	
			//'SubsidyExpired': '奖励领取过期',
			'Canceled': '活动已取消',
			'Claimed': '我已领取奖励',
			'Detailed': '查看详情',
			'ReEnroll':'重新报名',
			'Enrolled':'已提交报名',
			'NotEnrolled':'活动报名',
			'Reviewing':'报名审核中',
			'ReviewPassed':'正式报名',
			'RewardAuditing':'奖励审核中',
			'InProgress':'活动进行中',
			'Awarding':'申领奖励',
			//'ReAwarding':'重新领取奖励',
			'End':'活动已结束',
			'Visited':'待填写协议',
			'AppliableAgain':'重新申领奖励',
			'Commited':'待上传协议',
			'Appliable':'领取奖励',
			'Uploaded':'申领审核中',
			'Failed':'奖励领取失败',
			'Expired':'奖励领取过期',
			'Applied':'领取奖励成功'
		},
		'listings': {
			'zeroSubmitted': '您没有勾选任何刊登，点击“确认”将取消本活动报名!',
			'applyCondition': '申请参与活动需要选择至少一条刊登报名。',
			'onlyXls': '您选择提交的不是Excel文件，请重新选择刊登文件.',
			'rrpLink': '链接',
			'typeError' : '文件类型不合法',
			'notEmpty' : '上传文件不能为空',
			'needCheck' : '请勾选左边的复选框',
			'attachdownload' : '下载附件'
		},
		'request': {
			'fail': '请求提交失败',
			'sending': '请求已发送，请稍候。。。',
			'counting' : '已成功上传文件{0}/{1}'
		}
	},
	'listing': {
		'state': {
			'null':'N/A',
			'CanEnroll':'可报名',
			'Enrolled':'已报名',
			'NotEnrolled':'未报名',
			'Reviewing':'审核中',
			'ReviewPassed':'审核通过',
			'ReviewFailed':'审核未通过',
			'ReEnroll':'重新报名'
		},
		attachment: '附件'
	},
	'errorMsg': {
		'regDateExpired': '很遗憾，报名已经截止，如需继续报名，请联系客服！',
		'uploadListingError': '上传刊登失败，请联系客服了解详细情况！'
	},
	'subscribe': {
		'fail': '<p>订阅失败！请联系客服查询具体原因！</p>'
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