<%@ page trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="r" uri="http://ebay.com/raptor"%>
<%@ taglib prefix="res" uri="http://www.ebay.com/webres"%>

<div id="header">
    <div class="main"> 
        <a href="http://www.ebay.com" class="logo"><res:img
            value="/img/ebay.png" alt="ebay logo" width="250" height="200"
            style="clip: rect(47px, 118px, 95px, 0px); position: absolute;"></res:img></a>
        
        <div class="head-nav">
			歡迎您，<bdi>${unm}</bdi> &nbsp;&nbsp; <a href="http://www.ebay.cn/auth/?action=logout" style="font-weight: 400;">退出</a>
			<i class="fa fa-globe"></i>
			<span id="lang-switch" class="select-control">
				<select name="lang" id="lang">
					<option value="zh_CN" ${lang eq 'zh_CN' ? 'selected' : '' }>简体中文</option>
					<option value="zh_HK" ${lang eq 'zh_HK' ? 'selected' : '' }>繁體中文</option>
				</select>
			</span>
		</div>
    </div>
    
</div>

<script type="text/javascript">
function updateLocationParameter (l, a, p) {
	if (!a || !p) return e;

	var href = l.href.substring(0, l.href.indexOf('?')) + "?";
	var search = l.search ? (l.search.indexOf('?') == 0 && l.search.substr(1)) : '';

	var hasSearch = !!search, parameters = [], found = false;

	if (hasSearch){
		parameters = search.split('&');

		for (var i = 0; i < parameters.length ; i++){
			var parameter = parameters[i].split('=');
			var key = parameter[0];
			if (key == a){
				parameters[i] = a + '=' + encodeURIComponent(p);
				found = true;
				break;
			}
		}
	}
	
	if (!found){
		parameters.push(a + '=' + encodeURIComponent(p));
	}

	return href + parameters.join('&');
}

/* language switch begin */
$(function(){
	$('#lang-switch').dropdown('select', cbt.cookie.read('eBayCBTLang')).change(function(e, data) {
		window.location.href = updateLocationParameter(location, 'lang', data.value);
	});
});
/* language switch end */
</script>