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
	<title>Deals招募</title>
	<meta name="description" content="Deals招募 ">
	<res:cssSlot id="head" />
	<res:cssSlot id="head-css" />
	
	<script type="text/javascript">
		var BizReport = BizReport || {};
	</script>
	<res:jsSlot id="head" />	
	<res:jsSlot id="head-js" />
	
	<%--module "ebay.page" add Resets and Global css --%>
	<res:useCss value="${res.css.local.css['normalize.css']}" target="head-css"/>
	<res:useCss value="${res.css.local.css['jquery.dataTables.css']}" target="head-css"/>
	<res:useCss value="${res.css.local.css['dataTables.override.css']}" target="head-css"/>
	<res:useCss value="${res.css.local.css.reset_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.button_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.dropdown_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.module_css}" target="head-css" />
	<res:useCss value="${res.css.local.css.prettyText_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.dialog_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.layout_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.header_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.topNavigation_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.app_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.base_css}" target="head-css"/>

	<res:useJs value="${res.js.local.js['util.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js['local_zh_CN.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.lib['Widget.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.lib['MaskManager.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.lib['posManager.js']}" target="page-js"></res:useJs>
	
	<res:useJs value="${res.js.local.js.dialog['Dialog.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.dialog['TermsDialog.js']}" target="page-js2"></res:useJs>
</head>

<body>
<div class="container">
	<!--  Global Header -->
	<jsp:include page="../header.jsp"></jsp:include>
	<!-- end: Global Header -->

	<jsp:include page="../topNavigator.jsp"></jsp:include>
	
	<div id="page-pane">
		<div class="pane">
			<h2>Deals招募 ${promo.name}</h2>
			<div class="steps-wrapper">
				<div class="steps">
					<div class="step current-step last"><span>活动已取消</span></div>
				</div>
			</div>  <!-- steps end -->
			
			<%@ include file="../stateMessages/forCancelled.jsp" %>				
			
			<%@ include file="activity.jsp" %>
		</div>
	</div>

	<!-- Global Footer -->
		<jsp:include page="../footer.jsp"></jsp:include>
	<!-- End: Global Footer -->
</div>

<%@ include file="../dialog/terms.jsp" %>
<res:jsSlot id="body" />
<res:jsSlot id="page-js" />
<res:jsSlot id="page-js2" />
<res:jsSlot id="exec-js" />
<script type="text/javascript">
	$(".terms-conditions").click(function(event){
		BizReport.termsDialog.show();
	});
</script>
</body>
</html>
