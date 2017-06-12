<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="promo-state-message success">
	<div class="message-content">
		<h3>恭喜！您將獲得等值 ${reward}&nbsp;${promo.currency} 的獎勵！</h3>
		
		<p>由於您是第一次領取<a href="http://www.ebay.cn/mkt/leadsform/efu/11183.html" target="_blank">eBay萬裏通積分</a>形式的獎勵，請點擊以下綁定按鈕進入萬裏通網站進行綁定，待綁定的eBay帳號為${unm}。</p><br />
		<p>如您已完成帳號綁定，請嘗試點擊“<a href="javascript:location.reload()">刷新</a>”更新本頁面。</p><br />
	</div>
	<menu>
		<li><a href="${ wltBindURL }" class="btn">萬裏通帳號綁定</a><br /></li> 
		<li><a href="index">返回活動清單</a></li>
	</menu>
</div>