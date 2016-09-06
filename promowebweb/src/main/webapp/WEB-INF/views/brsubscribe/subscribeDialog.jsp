<%@ page trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="res" uri="http://www.ebay.com/webres"%>

<res:useCss value="${res.css.local.css.subscribe_css}" target="head-css" />

<c:set var="isDisplayConv" value="${!isInConvWhitelist && isCanSubscribeConv}" />  <!-- !isInConvWhitelist && isCanSubscribeConv -->
<c:set var="isDisplayDDS" value="${!isInDDSWhitelist && isCanSubscribeDDS}" />  <!-- !accessDDS && isCanSubscribeDDS -->
<div style="display:none;">
<div id="subscribe-dialog" class="subscribe-notice">
	<h1 align=center>新功能提醒</h1>
	<hr />

	<div class="subscribe-content">
		<p>为了提升广大卖家在eBay的销售业绩，我们新增了销售相关的分析数据，希望对您的销售助一臂之力。</p>
		<c:if test="${isDisplayConv eq true }">
			<br />
			<div id="divConv">
				<p>
					<strong>转化率情况分析</strong>：找出您的潜力刊登？提升销售转化率！数据包括：
				</p>
				<ul>
					<li>刊登级别的曝光量（Impression），浏览量（View Item），交易量（Transaction）转化数据。</li>
					<li>参照大中华卖家平均，TOP10卖家平均筛选出您的潜力刊登。</li>
					<li>包含US，UK，DE，AU，Motors站点数据。</li>
				</ul>
			</div>
		</c:if>

		<c:if test="${isDisplayDDS eq true }">
			<br />
			<div id="divDDS">
				<p>
					<strong>eBay供需分析</strong>：各eBay平台上哪些货好卖？看供需分析一目了然！
				</p>
				<ul>
					<li>热销分类，SKU报告。</li>
					<li>供求指数，售价区间，不良交易率，销售趋势等辅助数据。</li>
					<li>大卖家专属！</li>
				</ul>
			</div>
		</c:if>
		<div class="actions">
			<a id="btnSubscribe" class="btn">点击使用</a> 
			<br /> 
			<a href="http://www.baidu.com/">相关帮助</a>
		</div>
	</div>
</div>
</div>
<script type="text/javascript">
	var userId = "${userId}";
	$(document.body).ready(function() {
		$("#subscribe-dialog").on('show', function() {
		}).on('close', function(){
			var params = {};
			$.ajax({
				url : "subscription/subscribeDialogClosed?userId="+userId,
				type : 'GET',
				contentType : 'application/json',
				dataType : 'json',
				data : params,
				success : function(data) {
					if (data.status == true) {
						
					} else {
						cbt.alert(BizReport.local.getText('subscribe.fail'));
					}
				},
				error : function() {
					cbt.alert(BizReport.local.getText('subscribe.fail'));
				}
			});
		}).dialog({
			clazz : 'overlay'
		}).dialog();
	});

	$("#btnSubscribe").click(function() {

		var divConv = document.getElementById("divConv");

		var divDDS = document.getElementById("divDDS");
		var whitelistType = 0;

		if (divConv != null && divDDS == null) {
			whitelistType = 1;
		} else if (divDDS != null && divConv == null) {
			whitelistType = 2;
		} else if (divDDS != null && divConv != null) {
			whitelistType = 3;
		}

		var params = {};
		$.ajax({
			url : "subscription/subscribe?whitelistType=" + whitelistType+"&userId="+userId,
			type : 'GET',
			contentType : 'application/json',
			dataType : 'json',
			data : params,
			success : function(data) {
				if (data.status == true) {
					$("#subscribe-dialog").dialog("close");
					window.location.href = "/promotion/index";
				} else {
					cbt.alert(BizReport.local.getText('subscribe.fail'));
				}
			},
			error : function() {
				cbt.alert(BizReport.local.getText('subscribe.fail'));
			}
		});

	});
</script>

