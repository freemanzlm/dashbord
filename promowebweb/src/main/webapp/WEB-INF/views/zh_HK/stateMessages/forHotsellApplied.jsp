<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="promo-state-message success">
	<div class="message-content">
		<h3>您已成功提交報名！請耐心等待活動開始。</h3>
		<p class="desc">
			<c:choose>
				<c:when test="${ expired eq true }">已超過報名有效期，您無法再修改報名刊登。</c:when>
				<c:otherwise>在報名截止時間前您可以隨時修改您選擇的刊登。</c:otherwise>
			</c:choose>
		</p>
	</div>

	<menu>
		<li><a href="index" class="btn">返回活動清單</a></li>
	</menu>
</div>
<!-- active status box end -->