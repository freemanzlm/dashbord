<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="res" uri="http://www.ebay.com/webres"%>
<%@ taglib prefix="rui" uri="http://ebay.com/uicomponents"%>
<%@ taglib prefix="r" uri="http://ebay.com/raptor"%>
<%@ taglib prefix="ghs" uri="http://www.ebay.com/raptor/globalheader"%>
<c:set var="categoryId" value="6000" />

<r:includeJquery jsSlot="head" />
<r:client />

<!DOCTYPE html>
<html>
<head>
<title>活動促銷</title>
<meta name="description" content="活動促銷">
<meta name="author" content="eBay: Apps">
<res:cssSlot id="head" />
<res:cssSlot id="head-css" />

<script type="text/javascript">
	var BizReport = BizReport || {};
</script>
<res:jsSlot id="head" />
<res:jsSlot id="head-js" />

<%--module "ebay.page" add Resets and Global css --%>
<res:useCss value="${res.css.local.css['normalize.css']}" target="head-css" />
<res:useCss value="${res.css.local.css['font.awesome.min.css']}" target="head-css"/>
<res:useCss value="${res.css.local.css['jquery.dataTables.1.10.css']}" target="head-css" />
<res:useCss value="${res.css.local.css['dataTables.override.css']}" target="head-css" />
<res:useCss value="${res.css.local.css.reset_css}" target="head-css" />
<res:useCss value="${res.css.local.css.icon_css}" target="head-css" />
<res:useCss value="${res.css.local.css.button_css}" target="head-css" />
<res:useCss value="${res.css.local.css.dropdown_css}" target="head-css" />
<res:useCss value="${res.css.local.css.module_css}" target="head-css" />
<res:useCss value="${res.css.local.css.dialog_css}" target="head-css" />
<res:useCss value="${res.css.local.css.layout_css}" target="head-css" />
<res:useCss value="${res.css.local.css.header_css}" target="head-css" />
<res:useCss value="${res.css.local.css.topNavigation_css}" target="head-css" />
<res:useCss value="${res.css.local.css.promotion_css}" target="head-css" />
<res:useCss value="${res.css.local.css.base_css}" target="head-css" />

<res:useJs value="${res.js.local.js['extension.js']}" target="head"></res:useJs>
<res:useJs value="${res.js.local.js['util.js']}" target="head"></res:useJs>
<res:useJs value="${res.js.local.js['local_zh_HK.js']}" target="head"></res:useJs>
<res:useJs value="${res.js.local.js['cookie.js']}" target="head"></res:useJs>
<res:useJs value="${res.js.local.js.lib['widget.js']}" target="page-js"></res:useJs>
<res:useJs value="${res.js.local.js.lib['mask.js']}" target="page-js"></res:useJs>
<res:useJs value="${res.js.local.js.lib['posManager.js']}" target="page-js"></res:useJs>
<res:useJs value="${res.js.local.js['dropdown.js']}" target="page-js"></res:useJs>
<res:useJs value="${res.js.local.js.jquery['jquery.dataTables.js']}" target="page-js"></res:useJs>
<res:useJs value="${res.js.local.js.jquery['jquery.isloading.js']}" target="page-js"></res:useJs>

<res:useJs value="${res.js.local.js.dialog['dialog.js']}" target="page-js2"></res:useJs>
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
				<h2>活動促銷</h2>
			</div>

			<c:if test="${ invisible eq true }">
				<div class="pane pane-table mt20">
					<div class="header clr">
						<div class="fr cl">
							
							<select name="" id="" class="fr type-filter">
								<option value="">全部活動類型</option>
								<option value="0">爆款促銷</option>
								<option value="1">Deals招募</option>
								<option value="3">其它活動</option>
							</select>
						</div>
						<h3>等待開放的活動</h3>
					</div>
					<jsp:include page="table/pending.jsp"></jsp:include>
				</div>
			</c:if>

			<div class="pane pane-table mt20">
				<div class="header clr">
					<div class="fr cl">
						<span class="select-control state-filter fr">
							<select name="" id="" class="">
								<option value="">顯示所有活動</option>
								<option value="Created">可報名的活動</option>
								<option value="PromotionApproved">待正式報名的活動</option>
								<option value="Submitted">已提交預審的活動</option>
								<option value="Verifying">預審中的活動</option>
								<option value="Applied">已報名的活動</option>
								<option value="Started">進行中的活動</option>
								<option value="SubsidyCounting">獎勵確認中的活動</option>
								<option value="Detailed">只能查看詳情的活動</option>
							</select></span>
						<span class="select-control type-filter fr">
							 <select name="" id="">
								<option value="">全部活動類型</option>
								<option value="0">爆款促銷</option>
								<option value="1">Deals招募</option>
								<option value="3">其它活動</option>
							</select>
						</span>
					</div>
					<h3>進行中的活動</h3>
				</div>
				<jsp:include page="table/ongoing.jsp"></jsp:include>
			</div>

			<div class="pane pane-table mt20">
				<div class="header clr">
					<div class="fr cl">
						<span class="select-control state-filter fr">
							<select name="" id="">
								<option value="">顯示所有活動</option>
								<option value="SubsidyWaiting">可申領獎勵的活動</option>
								<option value="SubsidyAccessed">待填寫協定的活動</option>
								<option value="SubsidyResubmittable">需要重新申領獎勵的活動</option>
								<option value="SubsidyRetrievable">可領取獎勵的活動</option>
								<option value="SubsidySubmitted">待上傳協定的活動</option>
								<option value="SubsidyUploaded">申領稽核中的活動</option>
							</select>
						</span>
						<span class="select-control type-filter fr">
							<select name="" id="" class="fr type-filter">
								<option value="">全部活動類型</option>
								<option value="0">爆款促銷</option>
								<option value="1">Deals招募</option>
								<option value="3">其它活動</option>
							</select>
						</span>
					</div>
					<h3>領取活動獎勵</h3>
				</div>
				<jsp:include page="table/rewarding.jsp"></jsp:include>
			</div>

			<div class="pane pane-table mt20">
				<div class="header clr">
					<div class="fr cl">
						<span class="select-control state-filter fr">
							<select name="" id="" class="fr state-filter">
								<option value="">顯示所有活動</option>
								<option value="SubsidyRetrieved">領取獎勵成功的活動</option>
								<option value="Detailed">只能查看詳情的活動</option>
							</select>
						</span>
						<span class="select-control type-filter fr">
							<select name="" id="" class="fr type-filter">
								<option value="">全部活動類型</option>
								<option value="0">爆款促銷</option>
								<option value="1">Deals招募</option>
								<option value="3">其它活動</option>
							</select>
						</span>
					</div>
					<h3>已結束的活動</h3>
				</div>
				<jsp:include page="table/end.jsp"></jsp:include>
			</div>
		</div>

		<!-- Global Footer -->
		<jsp:include page="footer.jsp"></jsp:include>
		<!-- End: Global Footer -->
	</div>

	<%@ include file="dialog/alert.jsp"%>

	<script type="text/javascript">
		var pageData = {
			region : '${ region }',
			admin: ${invisible}
		};
	</script>

	<res:jsSlot id="body" />
	<res:jsSlot id="page-js" />
	<res:jsSlot id="page-js2" />
	<res:jsSlot id="exec-js" />
</body>
</html>
