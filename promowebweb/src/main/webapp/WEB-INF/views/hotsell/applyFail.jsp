<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="res" uri="http://www.ebay.com/webres"%>
<%@ taglib prefix="rui" uri="http://ebay.com/uicomponents" %>
<%@ taglib prefix="r" uri="http://ebay.com/raptor"%>
<%@ taglib prefix="ghs" uri="http://www.ebay.com/raptor/globalheader" %>
<c:set var="categoryId" value="6000" />

<r:includeJquery jsSlot="body" />
<r:client />

<!DOCTYPE html>
<html>
<head>
	<title>爆款促销 - 审核失败</title>
	<meta name="description" content="爆款促销 - 审核失败">
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
	
</head>

<body>
<div class="container">
	<!--  Global Header -->
	<jsp:include page="../header.jsp"></jsp:include>
	<!-- end: Global Header -->
	
	<div id="page">
		<div id="page-pane">
			<div class="pane">
				<h2>爆款促销 活动名称</h2>
				<div class="steps-wrapper">
					<div class="steps clr">
						<div class="step done"><span>可报名</span></div>
						<div class="step done"><span>已提交报名</span></div>
						<div class="step current-step last"><span>审核失败</span></div>
					</div>
				</div>  <!-- steps end -->
				
				<div class="active-status-box fail">
					<h3>很遗憾，您的报名未通过审核</h3>
					<p class="desc">
						感谢您的参与！
					</p>
					<menu>
						<a href="../index" class="btn">返回活动列表</a>
					</menu>					
				</div> <!-- active status box end -->
				
				<jsp:include page="activity.jsp"></jsp:include>
			</div>
		</div>
	</div>

	<!-- Global Footer -->
		<jsp:include page="../footer.jsp"></jsp:include>
	<!-- End: Global Footer -->
</div>

<%@ include file="../dialog/alert.jsp" %>

<res:jsSlot id="body" />
<res:jsSlot id="page-js" />
<res:jsSlot id="exec-js" />
</body>
</html>