<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="res" uri="http://www.ebay.com/webres"%>
<%@ taglib prefix="r" uri="http://ebay.com/raptor"%>
<c:set var="categoryId" value="6000" />

<r:includeJquery jsSlot="head" />
<r:client />

<!DOCTYPE html>
<html>
<head>
	<title>错误</title>
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
	<res:useCss value="${res.css.local.css.base_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.button_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.dropdown_css}" target="head-css"/>
	<res:useCss value="${res.css.local.less.module_less}" target="head-css" />
	<res:useCss value="${res.css.local.css.dialog_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.layout_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.header_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.promotion_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.error_css}" target="head-css"/>
	
	<res:useJs value="${res.js.local.js['cookie.js']}" target="head"></res:useJs>
	<res:useJs value="${res.js.local.js.dropdown_js}" target="page-js"/>
	
</head>

<body>
<div class="container">
	<!--  Global Header -->
	<jsp:include page="header.jsp"></jsp:include>
	<!-- end: Global Header -->
	
	<div id="page-pane" >
		<div class="error-box text-center mb15 clr">
			<h2 class="mb20"><i class="fa fa-times"></i>錯誤</h2>
			<p>很抱歉，您請求的頁面或檔案不存在！</p>
		</div>
	</div>	

	<!-- Global Footer -->
		<jsp:include page="footer.jsp"></jsp:include>
	<!-- End: Global Footer -->
</div>

<res:jsSlot id="body" />
<res:jsSlot id="page-js" />
<res:jsSlot id="exec-js" />
</body>
</html>