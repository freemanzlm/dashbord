<%@ page trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="r" uri="http://ebay.com/raptor"%>
<%@ taglib prefix="res" uri="http://www.ebay.com/webres"%>
<%@ taglib prefix="ghs" uri="http://www.ebay.com/raptor/globalheader" %>

<div id="header">
	<div class="main"> 
		<%-- <div class="header">
	    	<ghs:header layoutType="FULL" categoryId="${categoryId}" jsSlot="page-js" cssSlot="head-css" />	    
		</div> --%>
		<a href="http://www.ebay.com" class="logo"><res:img
			value="/img/ebay.png" alt="ebay logo" width="250" height="200"
			style="clip: rect(47px, 118px, 95px, 0px); position: absolute;"></res:img></a>
		<h1>賣家中心</h1>
		<p class="nav">歡迎您，<bdi>${unm}</bdi> &nbsp;&nbsp; <a href="http://www.ebay.cn/auth/?action=logout" style="font-weight: 400;">退出</a></p>
	</div>
	
	<jsp:include page="breadcrumb.jsp"></jsp:include>
</div>

<div class="top-nav clr">
	<ul class="links-nav clr">
		<li><a href="${sdurl}">買家體驗報告</a><small><a class="icon help" href="http://community.ebay.cn/portal.php?mod=view&aid=205#sell01" target="_blank"></a></small></li>
		<li class="separator">|</li>
		<li><a href="">業務分析報告</a><small><a class="icon help" href="#" target="_blank"></a></small></li>
		<li class="separator">|</li>
		<li class="active"><a href="">活動促銷</a></li>
	</ul>
</div>