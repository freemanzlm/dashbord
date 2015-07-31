<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="res" uri="http://www.ebay.com/webres"%>
<%@ taglib prefix="rui" uri="http://ebay.com/uicomponents" %>
<%@ taglib prefix="r" uri="http://ebay.com/raptor"%>
<%@ taglib prefix="ghs" uri="http://www.ebay.com/raptor/globalheader" %>
<c:set var="categoryId" value="6000" />

<r:includeJquery jsSlot="body" />
<r:includeRaptorJS jsSlot="body" />
<r:client />

<!DOCTYPE html>
<html>
<head>
	<title>eBay: Application</title>
	<meta name="description" content="eBay Application">
	<meta name="author" content="eBay: Apps">
	<res:cssSlot id="head-css" />
	<res:jsSlot id="head-js" />
	
	<%--module "ebay.page" add Resets and Global css --%>
	<r:includeModule name="ebay.UIComponentsResource.page" cssSlot="head-css" />
	<res:useCss value="${res.css.local.css.app_css}" target="head-css"/>
	
</head>

<body class="demo">
<div class="container">
	<!--  Global Header -->
	<div class="header">
	    <ghs:header layoutType="FULL" categoryId="${categoryId}" jsSlot="page-js" cssSlot="head-css" />	    
	</div>
	<!-- end: Global Header -->
	
	<!-- Page Content -->
	<h1>${greeting}</h1>
	<p>This is a sample web application generated from Raptor wizard. To change the content of this page, please modify <b>/webapp/WEB-INF/views/index.jsp</b></p>
	<br><b>This sample eBay app has helped you setup a starter project with the following features:</b>
	<%-- <rui:standardContainer title="This sample eBay app will get you started with followings:" cssSlot="head-css">				 --%>
		<ul class="features">
			<li>Demonstrates Global Header/Footer, Raptor JS framework and Raptor DS3 UI Components</li>
			<li>Include jQuery and Raptor JS files</li>
			<li>Resets and Globals CSS</li>		
			<li>All the required JS and CSS resources slots are automatically included </li>
			<li>Precludes need to define any slot</li>
		</ul>				
	<%-- </rui:standardContainer> --%>
	
	<div class="mt30">
		<rui:button size="small" variant="checkout" value="Click Here to Visit Validate Internals" type="anchor" url="/admin/v3console/ValidateInternals" target="_blank" cssSlot="head-css" jsSlot="body"/>
	</div>
	
	<div class="mt30">
		To learn more about Raptor, please visit: <a href="http://raptor" target="_blank">http://raptor</a>
	</div>
	
	<div class="mt30">
		For support on Raptor, visit: <a href="https://answerhub.corp.ebay.com/spaces/44/raptor.html" target="_blank">AnswerHub</a>
		<p>Please review the previous threads and answers, most likely you will find the answers for your questions.</p>
	</div>
	
	<!-- End: Page Content -->

</div>
<!-- Global Footer -->
<div id="Foot">
	<div id="vFoot">
	   <div id="Bottom">
			<ghs:footer />
	   </div>
	</div>
</div>
<!-- End: Global Footer -->
<res:jsSlot id="body" />
<res:jsSlot id="page-js" />
<res:jsSlot id="exec-js" />
</body>
</html>
