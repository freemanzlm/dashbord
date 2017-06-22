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
	<title>中國品牌智造計畫</title>
	<meta name="description" content="中國品牌智造計畫">
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
	<res:useCss value="${res.css.local.less.module_less}" target="head-css" />
	<res:useCss value="${res.css.local.css.dialog_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.layout_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.header_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.topNavigation_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.promotion_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.base_css}" target="head-css"/>
	
	<res:useJs value="${res.js.local.js['extension.js']}" target="head-js" />
	<res:useJs value="${res.js.local.js['util.js']}" target="head-js" />
	<res:useJs value="${res.js.local.js['local_zh_HK.js']}" target="head-js" />
	<res:useJs value="${res.js.local.js['cookie.js']}" target="head-js"/>
	
	<res:useJs value="${res.js.local.js.lib['widget.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.lib['mask.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.lib['posManager.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js['dropdown.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.jquery['jquery.dataTables.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.jquery['jquery.isloading.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.lib['vue.js']}" target="page-js"></res:useJs>
	
	<res:useJs value="${res.js.local.js.dialog['dialog.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.dialog['alert.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.jquery['DataTable.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.table['BrandRegPromoTable.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.table['PassedBrandsTable.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.table['OnGoingPromoTable.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.table['RewardingPromoTable.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.table['EndPromoTable.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.table['PendingPromoTable.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.page['brands_index.js']}" target="exec-js"></res:useJs>
	
</head>

<body>
<div class="container">
	<!--  Global Header -->
	<jsp:include page="header.jsp"></jsp:include>
	<!-- end: Global Header -->
	
	<c:if test="${pgcReadyFlag eq true}">
        	<jsp:include page="pgc_entrance.jsp"></jsp:include>
    </c:if>
	
	<jsp:include page="topNavigator.jsp"></jsp:include>
	
	<div id="page-pane">
		<c:if test="${ isAdmin eq true }">
			<div class="pane pane-table mt20">
				<div class="header clr">
					<div class="fr cl">
					</div>
					<h3>等待開放的活動</h3>
				</div>
				<jsp:include page="table/pending.jsp"></jsp:include>
			</div>
		</c:if>
		
		<c:choose>
			<c:when test="${passedBrandsCnt le 0 and brandVettingCnt le 0}">
				<div class="text-center mt20 mb20">
					<p>沒有符合篩選條件的活動</p>
				</div>
			</c:when>
			<c:otherwise>
				<c:if test="${not empty introduction}">
					<div class="mb20 clr">
						<h2>項目簡介</h2>
						<div class="pretty-text mt10">
							${ introduction }
						</div>
					</div>
				</c:if>
				
				<div class="pane pane-table mt20">
					<div class="header clr">
						<h3>品牌認證</h3>
					</div>
					<jsp:include page="table/brand_reg_promotions.jsp"></jsp:include>
				</div>
				
				<div class="pane pane-table mt20">
					<div class="header clr">
						<h3>已通過認證品牌表現追跡</h3>
					</div>
					<jsp:include page="table/passed_brands.jsp"></jsp:include>
				</div>
				
				<div class="mt20 clr">
					<h2>品牌推廣活動</h2>
				</div>
				
				<div class="pane pane-table mt20">
					<div class="header clr">
						<div class="fr cl">
							<span class="select-control state-filter fr">
								<select name="" id="">
									<option value="">顯示所有活動</option>
									<option value="SELLER NOMINATION_NEED APPROVE">報名階段的活動</option>
									<option value="PROMOTION SUBMITTED">審核階段的活動</option>
									<option value="PROMOTION IN PROGRESS">活動進行階段的活動</option>
									<option value="PROMOTION IN VALIDATION">獎勵審核階段的活動</option>
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
									<option value="Awarding">可申請獎勵的活動</option>
									<option value="Visited">待填寫確認函的活動</option>
									<option value="AppliableAgain">需要重新申請江離的活動</option>
									<option value="Appliable">可領取獎勵的活動</option>
									<option value="Commited">待上傳確認函的活動</option>
									<option value="Uploaded">申請審核中的活動</option>
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
								<select name="" id="">
									<option value="">顯示所有活動</option>
									<option value="SubsidyRetrieved">領取獎勵成功的活動</option>
									<option value="Detailed">只能查看詳情的活動</option>
								</select>
							</span>
						</div>
						<h3>已結束的活動</h3>
					</div>
					<jsp:include page="table/end.jsp"></jsp:include>
				</div>
			</c:otherwise>
		</c:choose>
		
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
