<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="res" uri="http://www.ebay.com/webres"%>
<%@ taglib prefix="rui" uri="http://ebay.com/uicomponents" %>
<%@ taglib prefix="r" uri="http://ebay.com/raptor"%>
<%@ taglib prefix="ghs" uri="http://www.ebay.com/raptor/globalheader" %>
<c:set var="categoryId" value="6000" />

<r:includeJquery jsSlot="head" />
<r:client />

<!DOCTYPE html>
<html>
<head>
	<title>活动促销</title>
	<meta name="description" content="活动促销">
	<meta name="author" content="eBay: Apps">
	<res:cssSlot id="head" />
	<res:cssSlot id="head-css" />
	
	<script type="text/javascript">
		var BizReport = BizReport || {};
	</script>
	<res:jsSlot id="head" />	
	<res:jsSlot id="head-js" />
	
	<%--module "ebay.page" add Resets and Global css --%>
	<r:includeModule name="ebay.UIComponentsResource.page" cssSlot="head" />
	<res:useCss value="${res.css.local.css['jquery.dataTables.css']}" target="head-css"/>
	<res:useCss value="${res.css.local.css['dataTables.override.css']}" target="head-css"/>
	<res:useCss value="${res.css.local.css.reset_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.button_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.module_css}" target="head-css" />
	<res:useCss value="${res.css.local.css.dialog_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.layout_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.app_css}" target="head-css"/>
	
	<res:useJs value="${res.js.local.js['util.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js['locale_zh_CN.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.lib['Widget.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.lib['MaskManager.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.lib['posManager.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.jquery['jquery.dataTables.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.jquery['jquery.isloading.js']}" target="page-js"></res:useJs>
	
	<res:useJs value="${res.js.local.js.dialog['Dialog.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.dialog['AlertDialog.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.jquery['DataTable.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.table['OnGoingPromoTable.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.table['RewardingPromoTable.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.table['EndPromoTable.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.table['PendingPromoTable.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.page['index.js']}" target="exec-js"></res:useJs>
	
</head>

<body>
<div class="container">
	<!--  Global Header -->
	<jsp:include page="header.jsp"></jsp:include>
	<!-- end: Global Header -->
	
	<div id="page">
		<div id="page-pane">
			<div class="clr" style="margin-bottom: 15px;">
				<h2>活动促销</h2>
			</div>
			
			<c:if test="${ invisible eq true }">
				<div class="pane pane-table">
					<div class="header">
						<div class="fr cl">
							<select name="" id="" class="fr type-filter">
								<option value="">全部活动类型</option>
								<option value="0">爆款促销</option>
								<option value="1">Deals招募</option>
								<option value="3">其它活动</option>
							</select>
						</div>
						<h3>等待开放的活动</h3>
					</div>
					<jsp:include page="table/pending.jsp"></jsp:include>
				</div>
			</c:if>			
			
			<div class="pane pane-table">
				<div class="header">
					<div class="fr cl">
						<select name="" id="" class="fr state-filter">
							<option value="">显示所有活动</option>
							<option value="Created">可报名的活动</option>
							<option value="PromotionApproved">待正式报名的活动</option>
							<option value="Submitted">已提交预审的活动</option>
							<option value="Verifying">预审中的活动</option>
							<option value="Applied">已报名的活动</option>
							<option value="Started">进行中的活动</option>
							<option value="SubsidyCounting">奖励确认中的活动</option>
							<option value="Detailed">只能查看详情的活动</option>
						</select>
						<select name="" id="" class="fr type-filter">
							<option value="">全部活动类型</option>
							<option value="0">爆款促销</option>
							<option value="1">Deals招募</option>
							<option value="3">其它活动</option>
						</select>
					</div>
					<h3>进行中的活动</h3>
				</div>
				<jsp:include page="table/ongoing.jsp"></jsp:include>
			</div>
			
			<div class="pane pane-table">
				<div class="header">
					<div class="fr cl">
						<select name="" id="" class="fr state-filter">
							<option value="">显示所有活动</option>
							<option value="SubsidyWaiting">可申领奖励的活动</option>
							<option value="SubsidyAccessed">待填写协议的活动</option>
							<option value="SubsidyResubmittable">需要重新申领奖励的活动</option>
							<option value="SubsidyRetrievable">可领取奖励的活动</option>
							<option value="SubsidySubmitted">待上传协议的活动</option>
							<option value="SubsidyUploaded">申领审核中的活动</option>
						</select>
						<select name="" id="" class="fr type-filter">
							<option value="">全部活动类型</option>
							<option value="0">爆款促销</option>
							<option value="1">Deals招募</option>
							<option value="3">其它活动</option>
						</select>
					</div>
					<h3>领取活动奖励</h3>
				</div>
				<jsp:include page="table/rewarding.jsp"></jsp:include>
			</div>
			
			<div class="pane pane-table">
				<div class="header">
					<div class="fr cl">
						<select name="" id="" class="fr state-filter">
							<option value="">显示所有活动</option>
							<option value="SubsidyRetrieved">领取奖励成功的活动</option>
							<option value="Detailed">只能查看详情的活动</option>
						</select>
						<select name="" id="" class="fr type-filter">
							<option value="">全部活动类型</option>
							<option value="0">爆款促销</option>
							<option value="1">Deals招募</option>
							<option value="3">其它活动</option>
						</select>
					</div>
					<h3>已结束的活动</h3>
				</div>
				<jsp:include page="table/end.jsp"></jsp:include>
			</div>
		</div>
	</div>

	<!-- Global Footer -->
		<jsp:include page="footer.jsp"></jsp:include>
	<!-- End: Global Footer -->
</div>

<%@ include file="dialog/alert.jsp" %>

<script type="text/javascript">
	var pageData = {
		region: '${ region }'
	};
</script>

<res:jsSlot id="body" />
<res:jsSlot id="page-js" />
<res:jsSlot id="page-js2" />
<res:jsSlot id="exec-js" />
</body>
</html>
