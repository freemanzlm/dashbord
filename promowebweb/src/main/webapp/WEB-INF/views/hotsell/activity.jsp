<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="deadline" value="2015.02.05"></c:set>
<c:set var="timeSlot" value="2015.02.06 - 2015.03.06"></c:set>
<c:set var="activityContent" value='
<p>1.此活动仅限eBay美国网站（ebay.com）、eBay英国网站（ebay.co.uk）、eBay德国网站（ebay.de）、
		eBay澳洲网站（ebay.com.au）及eBay汽车网站（ebay.com/motors）（“活动网站”）</p>
<p>2.卖家必须在中国时间2015年2月1日00:00之前登陆eBay热卖品牌降价促销活动列表，勾选及确认要参加活动的刊登物品，并确保与中国时间2015年2月5日15：00前将在活动网站刊登物品
		的售价控制在eBay指定售价之内直至活动结束或者确保于中国时间2015年2月5日15：00前将在活动网站刊登物品的售价控制在eBay指定售价之内直至销量超过eBay指定最低销量的两倍以上。</p>
<p>3.卖家必须在中国时间2015年2月1日00:00之前登陆eBay热卖品牌降价促销活动列表，勾选及确认要参加活动的刊登物品，并确保与中国时间2015年2月5日15：00前将在活动网站刊登物品
		的售价控制在eBay指定售价之内直至活动结束或者确保于中国时间2015年2月5日15：00前将在活动网站刊登物品的售价控制在eBay指定售价之内直至销量超过eBay指定最低销量的两倍以上。</p>
<p>4.卖家必须在中国时间2015年2月1日00:00之前登陆eBay热卖品牌降价促销活动列表，勾选及确认要参加活动的刊登物品，并确保与中国时间2015年2月5日15：00前将在活动网站刊登物品
		的售价控制在eBay指定售价之内直至活动结束或者确保于中国时间2015年2月5日15：00前将在活动网站刊登物品的售价控制在eBay指定售价之内直至销量超过eBay指定最低销量的两倍以上。</p>
<p>5.卖家必须在中国时间2015年2月1日00:00之前登陆eBay热卖品牌降价促销活动列表，勾选及确认要参加活动的刊登物品，并确保与中国时间2015年2月5日15：00前将在活动网站刊登物品
		的售价控制在eBay指定售价之内直至活动结束或者确保于中国时间2015年2月5日15：00前将在活动网站刊登物品的售价控制在eBay指定售价之内直至销量超过eBay指定最低销量的两倍以上。</p>'>
</c:set>

<div class="activity-detail">
	<div class="activity-time">
		<strong>报名截止时间：${ deadline }</strong>
		<strong style="margin-left: 90px;">活动时间：${ timeSlot }</strong>
	</div>
	<div class="table activity-brief">
		<div class="table-row">
			<div class="table-cell" style="width: 64px;">活动简介：</div>
			<div class="table-cell">
				${ activityContent }
			</div>
		</div>
	</div>
	<div class="activity-law">
		<strong>法律协议：点击查看 <a href="javascript:void(0)" class="terms-conditions">法律协议</a></strong>
	</div>
</div>			