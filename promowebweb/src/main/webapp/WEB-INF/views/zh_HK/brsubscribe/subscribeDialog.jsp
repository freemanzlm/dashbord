<%@ page trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="res" uri="http://www.ebay.com/webres"%>

<res:useCss value="${res.css.local.css.subscribe_css}" target="head-css" />

<c:set var="isDisplayConv" value="${!isInConvWhitelist && isCanSubscribeConv}" />  <!-- !isInConvWhitelist && isCanSubscribeConv -->
<c:set var="isDisplayDDS" value="${!isInDDSWhitelist && isCanSubscribeDDS}" />  <!-- !accessDDS && isCanSubscribeDDS -->
<div style="display:none;">
<div id="subscribe-dialog" class="subscribe-notice">
	<h1 align=center>賣家中心新功能提醒</h1>
	<hr />

	<div class="subscribe-content">
		<p>為了提升廣大賣家在eBay的銷售業績，我們新增了銷售相關的分析數據，希望助您的銷售一臂之力。</p>
		<br />
		<c:if test="${isDisplayConv eq true }">
			<div id="divConv">
				<p><strong>轉化率情況分析</strong>：找出您的潛力刊登？提升銷售轉化率！數據包括：</p>
				<ul>
					<li>刊登級別的曝光量（Impression），瀏覽量（View Item），交易量（Transaction）轉化數據。</li>
					<li>參照大中華賣家平均，TOP10賣家平均篩選出您的潛力刊登。</li>
					<li>包含US，UK，DE，AU，Motors站點數據。</li>					
				</ul>
			</div>
		</c:if>

		<c:if test="${isDisplayDDS eq true }">
			<br />
			<div id="divDDS">
				<p><strong>eBay供需分析</strong>：各eBay平台上哪些貨好賣？看供需分析一目了然！</p>
				<ul>
					<li>熱銷分類，SKU報告。</li>
					<li>供求指數，售價區間，不良交易率，銷售趨勢等輔助數據。</li>
					<li>大賣家專屬！</li>
				</ul>
			</div>
		</c:if>
		<div class="actions">
			<a id="btnSubscribe" class="btn" >點擊使用</a>
			<br/>
			<a href="http://community.ebay.cn/portal.php?mod=view&aid=246/" target="_blank">相關幫助</a>
		</div>
	</div>
</div>
</div>
<script type="text/javascript">
	var userId = "${userId}";
	var closeByClickSubscribe = false;
	$(document.body).ready(function() {
		$("#subscribe-dialog").on('show', function() {
		}).on('close', function(){
			if (closeByClickSubscribe) return;
			$.ajax({
				url : "subscription/subscribeDialogClosed?userId="+userId,
				type : 'GET',
				contentType : 'application/json',
				dataType : 'json',
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
		closeByClickSubscribe = true;
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

		$.ajax({
			url : "subscription/subscribe?whitelistType=" + whitelistType+"&userId="+userId,
			type : 'GET',
			contentType : 'application/json',
			dataType : 'json',
			success : function(data) {
				if (data.status == true) {
					$("#subscribe-dialog").dialog("close");
					if (whitelistType == 1 || whitelistType == 3){
						window.location.href = "http://biz.ebay.com.hk/bizreportweb/index";
					} else {
						window.location.href = "http://biz.ebay.com.hk/bizreportweb/index?type=skusad&lang=zh_HK";
					}
				} else {
					closeByClickSubscribe = false;
					cbt.alert(BizReport.local.getText('subscribe.fail'));
				}
			},
			error : function() {
				closeByClickSubscribe = false;
				cbt.alert(BizReport.local.getText('subscribe.fail'));
			}
		});

	});
</script>

