<%@ page trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<fmt:formatNumber var="reward" value="${promo.reward }" minFractionDigits="2"></fmt:formatNumber>
<fmt:formatDate   var="rewardDeadline" value="${promo.rewardDlDt}" pattern="yyyy-MM-dd" type="date" />

<div class="tabbable confirm-letter-steps">
	<div class="tab-list-container clr" v-cloak>
		<ul class="tab-list clr" role="tablist">
			<li role="tab" aria-controls="pane1" v-bind:class="{active: !hasSubmitFields}" v-bind:disabled="hasApproved"><span class="label">
				<a href="#pane1">第一步：填写确认函</a></span></li>
			<li role="tab" aria-controls="pane2" v-if="hasSubmitFields" v-bind:class="{active: hasSubmitFields && !hasApproved}" v-bind:disabled="hasApproved"><span class="label">
				<a href="#pane2">第二步：上传确认函</a></span></li>
			<li role="tab" aria-controls="pane3" v-if="hasApproved" v-bind:class="{active: hasApproved}"><span class="label">
				<a href="#pane3">第三步：领取奖励</a></span></li>
		</ul>
	</div>
	
	<div id="pane1" class="tab-pane confirm-letter-pane" v-bind:class="{active: !hasSubmitFields}" role="tabpanel">
		
		<c:if test="${promo.rewardType eq 2 and not empty wltAccount}">
			<c:choose>
				<c:when test="${not empty param.isWltFirstBound}"> <!-- Parameter 'isWltFirstBound' comes from bound backURL parameter -->
					<div class="pane wlt-binding">
						恭喜！您已完成eBay万里通账号的绑定。绑定的eBay账号为：${unm}，对应的万里通账号为：${wltAccount.wltUserId}。
					</div>
				</c:when>
				<c:otherwise>
					<div class="pane wlt-binding">
						请注意：您绑定的<a target="_blank" href="http://www.ebay.cn/mkt/leadsform/efu/11183.html">万里通</a>账号是：${wltAccount.wltUserId}，
						<a href="http://www.wanlitong.com/myPoint/brandPointSch.do?fromType=avail&pageNo=1&brandPointNo=h5mg&dateType=0&sortFlag=ddd">查积分，积分当钱花。</a>
					</div>
				</c:otherwise>
			</c:choose>
		</c:if>
		
		<h3 class="mt20 mb5 text-center">活动奖励确认函</h3>
		<hr />
		<h4 class="mt20 ml20">卖家基本信息：</h4>
		<form id="custom-fields-form" action="acknowledgment" class="form-horizontal" method="post">
			<input type="hidden" value="${promo.promoId }" name="promoId"/>
			
			<c:forEach items="${ nonuploadFields }" var="field">
				<c:if test="${ not empty field}">
					<div class="form-group">
						<div class="control-label">${field.displayLabel }：</div>
						<div class="form-field">
							<input type="text" name="${field.key }" value="${field.value }"/>
							<c:if test="${field.key eq '_sellerName' }">
								&nbsp;<span>(以下称为“我/本公司”或“卖家”)</span>
							</c:if>
						</div>
					</div>
				</c:if>
			</c:forEach>
			
			<div class="form-group">
				<div class="control-label">eBay账号：</div>
				<div class="form-field">
					${unm}（不允许修改）
				</div>
			</div>
			<div class="form-group">
				<div class="control-label">参加的活动：</div>
				<div class="form-field">
					${promo.name }（不允许修改）（以下简称“活动”）
				</div>
			</div>
			<div class="form-group">
				<div class="control-label">奖励金额：</div>
				<div class="form-field">
					${reward} ${promo.currency}（不允许修改）（以下简称“活动奖励”）
				</div>
			</div>
			<div class="form-group">
				<div class="control-label">奖励申领截止时间：</div>
				<div class="form-field">
					北京时间 ${rewardDeadline}（不允许修改）（以下简称“奖励申领时间”）
				</div>
			</div>
			<div class="form-group">
				<div class="control-label"><input v-model="hasAcceptLetter" type="checkbox" /></div>
				<div class="form-field">
					我已阅读并接受以下确认函内容
				</div>
			</div>
			<div class="text-center">
				<button type="button" class="btn" v-bind:disabled="!hasAcceptLetter" v-on:click="sendSellerCustomFields">点击生成PDF供签署</button>
				<template v-if="hasSubmitFields">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<button type="button" class="btn" v-on:click="gotoSecondStep">下一步：上传确认函</button>
				</template>
			</div>
		</form>
		<hr />
		
		<div class="pretty-text">
			${promo.desc}
		</div>
	</div>
	
	<div id="pane2" class="tab-pane confirm-letter-submission-pane" v-bind:class="{active: (hasSubmitFields && !hasApproved)}" role="tabpanel">
		<div class="hint">
			<p>为了方便核实您的上传信息，确保您能尽快领取相关奖励，请仔细阅读以下内容：</p>
			<ol>
				<li>上传文件格式为“.jpg”或“ZIP”（多文件情况），单个上传文件不超过5M；</li>
				<li>上传文件内容须清晰，可识别且应完整齐全；</li>
				<li>不得在同一文件栏重复上传多个文件，一旦在同一上传文件栏重复上传文件时，我们将以提交前最后上传的那份为准；</li>
				<li>若未能按照上述要求操作的，我们将作“审核未通过”处理；</li>
				<li>上传截止日期为${rewardDeadline}，未在该规定时间内上传文件的卖家将被视为自动放弃本活动奖励。</li>
			</ol>
		</div>
		
		<c:forEach items="${ uploadFields }" var="field">
			<c:if test="${ not empty field}">
				<form target="uploadIframe_${field.key}" ${ field.required ? 'required':'' } data-hasuploaded="${not empty field.value}" action="uploadAttachment" class="form-horizontal attachment-form" method="post" enctype="multipart/form-data">
					<input type="hidden" name="promoId" value="${promo.promoId }"/>
					<input type="hidden" name="key" value="${field.key }"/>
					<div class="form-group">
						<div class="control-label">${field.displayLabel }：</div>
						<div class="form-field">
							<span class="file-input" v-if="!isAwardEnd">
								<input type="text" style="height: 20px;" placeholder="选择文件" />
								<input type="file" name="uploadFile" accept="image/jpeg, application/pdf, application/zip"/>
								<button type="button" class="btn" style="margin-left: 3px;">选择</button>
							</span> <br v-if="!isAwardEnd"/>
							<span class="font-bold msg">
								<c:if test="${ not empty field.value }">
									<a href="/promotion/subsidy/downloadAttachmentById?id=${field.value}">下载附件</a>
								</c:if>
							</span>
						</div>
					</div>
				</form>
				
				<iframe name="uploadIframe_${field.key}" src="about:blank" frameborder="0" class="hidden" ></iframe>
			</c:if>
		</c:forEach>
		
		<div class="text-center" style="margin-top: 40px;" v-if="!isAwardEnd">
			<button id="upload-form-btn" type="submit" class="btn" style="width:120px;">上传</button>
		</div>
		
	</div>
	
	<div id="pane3" class="tab-pane" v-bind:class="{active: hasApproved}" role="tabpanel">
		<div class="promo-state-message">
			<div class="message-content">
				
				<c:choose>
					<c:when test="${ subsidyTerm.subsidyType eq 2 }">
						<h3 class="color-green text-center">您已成功领取等值 ${reward} ${promo.currency} 的奖励。</h3>
					</c:when>
					<c:otherwise>
						<p class="desc">${ subsidyTerm.successInfo }</p> <br />
					</c:otherwise>
				</c:choose>
				
				<menu>
					<li><a href="../index" class="btn">返回活动列表</a></li>
				</menu>
			</div>
		</div>
		
	</div>
</div>