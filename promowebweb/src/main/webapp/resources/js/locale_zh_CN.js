BizReport = BizReport || {};

BizReport.locale = {
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
	'dataTable': {
		'emptyTable': '<p>没有数据，如正在加载，请等待。</p>',
		'zeroRecords': '<p>没有数据相关数据。</p>',
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
		'requestFail': '请求失败，请检查网络是否连接正确。如果不是网络问题，请稍后再试。',
		'site': {
		    'us': '美国站',
		    'uk': '英国站',
		    'de': '德国站',
		    'au': '澳大利亚',
		    'other': '其他站点'
		},
		'listing': {
			'info': '当前显示 _START_ 至 _END_，总共_TOTAL_条刊登'
		},
		'skuList': {
			'info': '当前显示 _START_ 至 _END_，总共_TOTAL_条SKU'
		},
		'promo': {
			'rewardCounting': '待评估',
			'noReward': '无奖励',
			'emptyTable': '<p>没有符合筛选条件的活动。</p>',
			'zeroRecords': '<p>没有符合筛选条件的活动。</p>',
		}
	},
	'promo': {
		'type': {
			'deals': 'Deals招募',
			'dealsPreset': 'Deals招募预置',
			'hotsell': '暴款促销',
			'other': '其它活动'
		},
		'state': {
			'Created': '可报名',
			'Submitted': '已提交预审',
			'Verifying': '报名审核中',
			'PromotionApproved': '确认报名刊登',
			'VerifyFailed': '审核失败',
			'Applied': '已提交报名',
			'Started': '活动进行中',
			'SubsidyCounting': '奖励确认中',
			'SubsidyWaiting': '申领奖励',
			'NeedAgreement': '上传协议',
			'SubsidyVerifying': '奖励审核中',
			'Completed': '领取奖励成功',
			'ClaimFail': '重新申领奖励',	
			'Canceled': '活动已取消',
			'End': '活动已结束',
			'Claimed': '我已领取奖励'
		},
		'hotsell': {
			'zeroSubmitted': '您没有勾选任何刊登，这意味着您将取消报名，您确认吗？',
			'applyCondition': '申请参与活动前，请至少选择一条刊登。'
		},
		'deals': {
			'upload': '提交刊登报名前请先阅读法律协议，并<strong>全部读完</strong>，然后点击确认接受该法律协议后方可提交报名。',
			'onlyXls': '您选择提交的不是Excel文件，请重新选择刊登文件.'
		}
	},
	'listing': {
		'state': {
			'applicable': '可报名',
			'applied': '已报名',
			'nonapplied': '未报名',
			'pass': '通过审核',
			'notSubmitted': '未提交',
			'pretrial': '待审核',
			'pretrialPass': '通过预审',
			'pretrialFail': '未通过'
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