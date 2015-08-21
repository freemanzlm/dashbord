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
			'storm': '暴款促销',
			'other': '其他活动'
		},
		'state': {
			'appliable': '可报名',
			'comfirmApplication': '确认报名刊登',
			'submitted': '已提交报名',
			'reviewing': '报名审核中',
			'ongoing': '活动进行中',
			'browseOnly': '仅供浏览',
			'rewardConfirming': '奖励确认中',
			'rewardAppliable': '可申领奖励',
			'claimReward': '领取奖励',
			'reclaimReward': '重新申领奖励',
			'toFillAgreement': '填写协议',
			'toUploadAgreement': '上传协议',
			'rewardReviewing': '申领奖励审核中',
			'rewardSuccess': '奖励领取成功',
			'end': '结束的活动'
		}
	},
	'listing': {
		'state': {
			'applied': '已报名',
			'reviewing': '正在审核',
			'reviewed': '通过预审'
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