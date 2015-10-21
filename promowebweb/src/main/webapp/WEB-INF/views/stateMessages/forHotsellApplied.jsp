<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="promo-state-message success">
	<div class="message-content">
		<h3>您已成功提交报名！请耐心等待活动开始。</h3>
		<p class="desc">
			<c:choose>
				<c:when test="${ expired eq true }">已超过报名有效期，您无法再修改报名刊登。</c:when>
				<c:otherwise>在报名截止时间前您可以随时修改您选择的刊登。</c:otherwise>
			</c:choose>
		</p>
	</div>

	<menu><li><a href="index" class="btn">返回活动列表</a></li></menu>
</div>
<!-- active status box end -->