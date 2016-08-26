<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="res" uri="http://www.ebay.com/webres"%>
<%@ taglib prefix="r" uri="http://ebay.com/raptor"%>
<%@ taglib prefix="ghs" uri="http://www.ebay.com/raptor/globalheader" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- jQuery is included in body jsSlot by default --%>
<r:includeJquery jsSlot="head" />
<r:client />
<r:includeHtml5Tags />

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
	<res:useCss value="${res.css.local.css['normalize.css']}" target="head-css"/>
	<res:useCss value="${res.css.local.css['font.awesome.min.css']}" target="head-css"/>
	<res:useCss value="${res.css.local.css['jquery.dataTables.1.10.css']}" target="head-css"/>
	<res:useCss value="${res.css.local.css['dataTables.override.css']}" target="head-css"/>
	<res:useCss value="${res.css.local.css.reset_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.icon_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.button_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.dropdown_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.module_css}" target="head-css" />
	<res:useCss value="${res.css.local.css.dialog_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.layout_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.header_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.topNavigation_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.promotion_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.base_css}" target="head-css"/>
	
	<res:useJs value="${res.js.local.js['extension.js']}" target="head-js" />
	<res:useJs value="${res.js.local.js['util.js']}" target="head-js" />
	<res:useJs value="${res.js.local.js['local_zh_CN.js']}" target="head-js" />
	<res:useJs value="${res.js.local.js['cookie.js']}" target="head-js"/>
	
	<res:useJs value="${res.js.local.js.lib['widget.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.lib['mask.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.lib['posManager.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js['dropdown.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.jquery['jquery.dataTables.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.jquery['jquery.isloading.js']}" target="page-js"></res:useJs>
	
	<res:useJs value="${res.js.local.js.dialog['dialog.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.dialog['alert.js']}" target="page-js2"></res:useJs>
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
	
	<jsp:include page="topNavigator.jsp"></jsp:include>
	
	<div id="page-pane">
		<div class="clr" style="margin-bottom: 15px;">
			<h2>活动促销</h2>
		</div>
		
		<c:if test="${ isAdmin eq true }">
			<div class="pane pane-table mt20">
				<div class="header clr">
					<div class="fr cl">
					</div>
					<h3>等待开放的活动</h3>
				</div>
				<jsp:include page="table/pending.jsp"></jsp:include>
			</div>
		</c:if>			
		
		<div class="pane pane-table mt20">
			<div class="header clr">
				<div class="fr cl">
					<span class="select-control state-filter fr">
						<select name="" id="">
							<option value="">显示所有活动</option>
							<option value="Seller nomination_Need approve">报名阶段的活动</option>
							<option value="Promotion Submitted">审核阶段的活动</option>
							<option value="Promotion in progress">活动进行阶段的活动</option>
							<option value="Promotion in validation">奖励审核阶段的活动</option>
						</select>
					</span>
				</div>
				<h3>进行中的活动</h3>
			</div>
			<jsp:include page="table/ongoing.jsp"></jsp:include>
		</div>
		
		<div class="pane pane-table mt20">
			<div class="header clr">
				<div class="fr cl">
					<span class="select-control state-filter fr">
						<select name="" id="">
							<option value="">显示所有活动</option>
							<option value="SubsidyWaiting">可申领奖励的活动</option>
							<option value="SubsidyAccessed">待填写协议的活动</option>
							<option value="SubsidyResubmittable">需要重新申领奖励的活动</option>
							<option value="SubsidyRetrievable">可领取奖励的活动</option>
							<option value="SubsidySubmitted">待上传协议的活动</option>
							<option value="SubsidyUploaded">申领审核中的活动</option>
						</select>
					</span>
				</div>
				<h3>领取活动奖励</h3>
			</div>
			<jsp:include page="table/rewarding.jsp"></jsp:include>
		</div>
		
		<div class="pane pane-table mt20">
			<div class="header clr">
				<div class="fr cl">
					<span class="select-control state-filter fr">
						<select name="" id="">
							<option value="">显示所有活动</option>
							<option value="SubsidyRetrieved">领取奖励成功的活动</option>
							<option value="Detailed">只能查看详情的活动</option>
						</select>
					</span>
				</div>
				<h3>已结束的活动</h3>
			</div>
			<jsp:include page="table/end.jsp"></jsp:include>
		</div>
	</div>

	<!-- Global Footer -->
		<jsp:include page="footer.jsp"></jsp:include>
	<!-- End: Global Footer -->
</div>

<%@ include file="dialog/alert.jsp" %>

<script type="text/javascript">
	var pageData = {
		region: '${ region }',
		admin: JSON.parse('${not empty isAdmin ? isAdmin : false}')
	};
	var clientContext = ${client.serialize(client.getContext())} ; 
	var supportHTML5 = ${client.supportHtml5Tags()} ; 
</script>

<res:jsSlot id="body" />
<res:jsSlot id="page-js" />
<res:jsSlot id="page-js2" />
<res:jsSlot id="exec-js" />
</body>
</html>
