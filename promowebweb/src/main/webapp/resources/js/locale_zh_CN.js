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
		'open': '展开',
		'close': '收起',
		'info': '当前显示 _START_ 至 _END_，总共_TOTAL_条',
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
			'info': '总共_TOTAL_个刊登'
		}
	},
	'promo': {
		'type': {
			'deals': 'Deals招募',
			'dealsPreset': 'Deals招募预置',
			'hotsell': '暴款促销',
			'other': '其他活动'
		},
		'state': {
			'applicable': '可报名',
			'applyExpired': '未报名', // apply period expired
			'applied': '已提交报名',
			'verifying': '报名审核中',
			'approved': '确认报名刊登',
			'ongoing': '活动进行中',
			'rewardVerifying': '奖励确认中',
			'rewarding': '可申领奖励',
			'claimExpired': '申领已过期', // reward claim period expired
			'complete': '活动完成',
			'end': '活动已结束'
		},
		'hotsell': {
			'zeroSubmitted': '您没有勾选任何刊登，这意味着您将取消报名，您确认吗？',
			'applyCondition': '申请参与活动前，请至少选择一条刊登。'
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