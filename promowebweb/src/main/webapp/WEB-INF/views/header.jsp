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
		<h1>卖家中心</h1>
		<p class="nav">欢迎您，<bdi>${unm}</bdi> &nbsp;&nbsp; <a href="http://www.ebay.cn/auth/?action=logout" style="font-weight: 400;">退出</a></p>
	</div>
	
	<jsp:include page="breadcrumb.jsp"></jsp:include>
</div>

<div class="top-nav clr">
	<button class='btn white' id="btn-lang" type='button' lang='zh_HK'>切換至繁體中文</button>
	<ul class="links-nav clr">
		<li><a href="${sdurl}">买家体验报告</a><small><a class="icon help" href="http://community.ebay.cn/portal.php?mod=view&aid=205#sell01" target="_blank"></a></small></li>
		<li class="separator">|</li>
		<li><a href="">业务分析报告</a><small><a class="icon help" href="#" target="_blank"></a></small></li>
		<li class="separator">|</li>
		<li class="active"><a href="">活动促销</a></li>
	</ul>
</div>

<script type="text/javascript">
function updateLocationParameter (l, a, p) {
	if (!a || !p) return e;

	var href = l.href;
	var search = l.search ? (l.search.indexOf('?') > 0 && l.search.substr(1)) : '';

	var hasSearch = !!search, parameters = [], found;
	href = hasSearch ? href.substr(href.indexOf('?')) : (href + '?');

	if (hasSearch){
		parameters = search.split('&');

		for (var i = 0; i < parameters.length ; i++){
			var parameter = parameters[i].split('=');
			var key = parameter[0];
			if (key == a){
				parameters[i] = a + '=' + encodeURIComponent(p);
				found = true;
			}
		}
	}
	
	if (!found){
		parameters.push(a + '=' + encodeURIComponent(p));
	}

	return href + parameters.join('&');
}

/* language switch begin */
if (document.addEventListener) {
	document.getElementById('btn-lang').addEventListener('click', function(){
		location.href = updateLocationParameter(location, 'lang', this.getAttribute('lang'));
	});
} else {
	document.getElementById('btn-lang').attachEvent('onclick', function(){
		location.href = updateLocationParameter(location, 'lang', this.getAttribute('lang'));
	});
}
/* language switch end */
</script>