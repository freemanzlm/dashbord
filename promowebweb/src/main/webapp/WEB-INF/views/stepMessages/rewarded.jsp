<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:choose>
	<c:when test="${isAwardEnd eq true}">
		<div class="promo-state-message">
			<div class="message-content">
				<h3>恭喜！您将获得等值 ${reward} ${promo.currency} 的奖励！</h3>
				<p class="desc">${ subsidyTerm.successInfo }</p> <br />
			</div>
			<menu>
				<li><a href="index" class="btn">返回活动列表</a></li>
			</menu>
		</div>
	</c:when>
	<c:otherwise>		
		
		<div class="promo-state-message success">
			<div class="message-content">
				<h3>恭喜！您将获得等值 ${reward} ${promo.currency} 的奖励！</h3>

				<c:if test="${ subsidyTerm.ovFlag != 1 }">
					<p class="desc">${ subsidyTerm.successInfo }</p>
					<br />
				</c:if>
			</div>
			
			<menu>
				<li>
					<c:if test="${ subsidyTerm.ovFlag == 1 }">
						<a class="btn" href="subsidy/acknowledgment?promoId=${promo.promoId }">填写奖励申领确认函</a>
						<br /><br />
					</c:if> 
					<a href="index">返回活动列表</a>
				</li>
			</menu>

		</div>
	</c:otherwise>
</c:choose>