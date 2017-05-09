<%@ page trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<fmt:formatNumber var="reward" value="${promo.reward }" minFractionDigits="2"></fmt:formatNumber>
<fmt:formatDate   var="rewardDeadline" value="${promo.rewardDlDt}" pattern="yyyy-MM-dd" type="date" />

<div class="tabbable confirm-letter-steps">
	<div class="tab-list-container clr" v-cloak>
		<ul class="tab-list clr" role="tablist">
			<li role="tab" aria-controls="pane1" v-bind:class="{active: !hasSubmitFields}" v-bind:disabled="hasApproved"><span class="label">
				<a href="#pane1">第一步：填寫確認函</a></span></li>
			<li role="tab" aria-controls="pane2" v-if="hasSubmitFields" v-bind:class="{active: hasSubmitFields && !hasApproved}" v-bind:disabled="hasApproved"><span class="label">
				<a href="#pane2">第二步：上傳確認函</a></span></li>
			<li role="tab" aria-controls="pane3" v-if="hasApproved" v-bind:class="{active: hasApproved}"><span class="label">
				<a href="#pane3">第三步：領取獎勵</a></span></li>
		</ul>
		<a class="fr mt10" href="/promotion/${promo.promoId}">查看活動詳情</a>
	</div>
	
	<div id="pane1" class="tab-pane confirm-letter-pane" v-bind:class="{active: !hasSubmitFields}" role="tabpanel">
		
		<c:if test="${promo.rewardType eq 2 and not empty wltAccount}">
			<c:choose>
				<c:when test="${not empty param.isWltFirstBound}"> <!-- Parameter 'isWltFirstBound' comes from bound backURL parameter -->
					<div class="pane wlt-binding">
						恭喜！您已完成eBay萬裏通帳號的綁定。綁定的eBay帳號為：${unm}，對應的萬裏通帳號為：${wltAccount.wltUserId}。
					</div>
				</c:when>
				<c:otherwise>
					<div class="pane wlt-binding">
						請注意：您綁定的<a target="_blank" href="http://www.ebay.cn/mkt/leadsform/efu/11183.html">萬裏通</a>帳號是：${wltAccount.wltUserId}，
						<a href="http://www.wanlitong.com/myPoint/brandPointSch.do?fromType=avail&pageNo=1&brandPointNo=h5mg&dateType=0&sortFlag=ddd">查積分，積分當錢花。</a>
					</div>
				</c:otherwise>
			</c:choose>
		</c:if>
		
		<h3 class="mt20 mb5 text-center">活動獎勵確認函</h3>
		<hr />
		<h4 class="mt20 ml20">賣家基本資訊：</h4>
		<form id="custom-fields-form" action="acknowledgment" class="form-horizontal" method="post">
			<input type="hidden" value="${promo.promoId }" name="promoId"/>
			
			<c:forEach items="${ nonuploadFields }" var="field">
				<c:if test="${ not empty field}">
					<div class="form-group">
						<div class="control-label">${field.displayLabel }：</div>
						<div class="form-field">
							<input type="text" name="${field.key }" value="${field.value }"/>
							<c:if test="${field.key eq '_sellerName' }">
								&nbsp;<span>（以下稱為“我/本公司”或“賣家”）</span>
							</c:if>
						</div>
					</div>
				</c:if>
			</c:forEach>
			
			<div class="form-group">
				<div class="control-label">eBay帳號：</div>
				<div class="form-field">
					${unm}（不允許修改）
				</div>
			</div>
			<div class="form-group">
				<div class="control-label">參加的活動：</div>
				<div class="form-field">
					${promo.name }（不允許修改）（以下簡稱“活動”）
				</div>
			</div>
			<div class="form-group">
				<div class="control-label">獎勵金額：</div>
				<div class="form-field">
					${reward} ${promo.currency}（不允許修改）（以下簡稱“活動獎勵”）
				</div>
			</div>
			<div class="form-group">
				<div class="control-label">奖励申领截止时间：</div>
				<div class="form-field">
					北京时间 ${rewardDeadline}（不允許修改）（以下簡稱“獎勵申領時間”）
				</div>
			</div>
			<div class="form-group">
				<div class="control-label"><input v-model="hasAcceptLetter" type="checkbox" /></div>
				<div class="form-field">
					我已閱讀並接受以下確認函內容
				</div>
			</div>
			<div class="text-center">
				<button type="button" class="btn" v-bind:disabled="!hasAcceptLetter" v-on:click="sendSellerCustomFields">點擊生成PDF供簽署</button>
				<template v-if="hasSubmitFields">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<button type="button" class="btn" v-on:click="gotoSecondStep">下一步：上傳確認函</button>
				</template>
			</div>
		</form>
		<hr />
		
		<div class="pretty-text">
			${promo.desc}
		</div>
	</div>
	
	<div id="pane2" class="tab-pane confirm-letter-submission-pane" v-bind:class="{active: (hasSubmitFields && !hasApproved)}" role="tabpanel">
		<c:if test="${ subsidy.status eq 3 }">
			<div style="background:#e8ecaf;padding:10px;">您的檔案已上傳成功！請耐心等待我們的稽核結果，由於數據量龐大，更新稽核狀態可能需要10個工作日。<br />您也可以隨時返回<a href="/promotion/index">活動清單</a>頁查看最新的稽核狀態。</div>
		</c:if>
		
		<div class="hint">
			<p>為了方便核實您的上傳資訊，確保您能儘快領取相關獎勵，請仔細閱讀以下內容：</p>
			<ol>
				<li>上傳檔案格式為“.jpg”、“.pdf”或“ZIP”（多檔案情况），單個上傳文件不超過5M；</li>
				<li>上傳文件內容須清晰，可識別且應完整齊全；</li>
				<li>不得在同一檔案欄重複上傳多個檔案，一旦在同一上傳文件欄重複上傳文件時，我們將以提交前最後上傳的那份為准；</li>
				<li>若未能按照上述要求操作的，我們將作“稽核未通過”處理；</li>
				<li>上傳截止日期為${rewardDeadline}，未在該規定時間內上傳文件的賣家將被視為自動放弃本活動獎勵。</li>
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
								<input type="text" style="height: 20px;" placeholder="選擇檔案" />
								<input type="file" name="uploadFile" v-on:change="validateFileType"/>
								<button type="button" class="btn" style="margin-left: 3px;">選擇</button>
							</span> <br v-if="!isAwardEnd"/>
							<span class="font-bold msg">
								<c:if test="${ not empty field.value }">
									<a href="/promotion/subsidy/downloadAttachmentById?id=${field.value}">查看</a>
								</c:if>
							</span>
						</div>
					</div>
				</form>
				
				<iframe name="uploadIframe_${field.key}" src="about:blank" frameborder="0" class="hidden" ></iframe>
			</c:if>
		</c:forEach>
		
		<div class="text-center" style="margin-top: 40px;" v-if="!isAwardEnd">
			<button id="upload-form-btn" type="submit" class="btn" style="width:120px;">上傳</button>
		</div>
		
	</div>
	
	<div id="pane3" class="tab-pane" v-bind:class="{active: hasApproved}" role="tabpanel">
		<c:choose>
			<c:when test="${ subsidyTerm.subsidyType eq 2 }">
				<div class="promo-state-message">
					<div class="message-content">
						<h3 class="color-green text-center">您已成功領取等值${reward} ${promo.currency}的ebay萬裏通積分！</h3>
					</div>
					<menu>
						<li><a href="../index" class="btn">返回活動清單</a></li>
					</menu>
				</div>
			</c:when>
			<c:otherwise>
				<div class="promo-state-message">
					<div class="message-content">
						<div class="pretty-text">${ subsidyTerm.successInfo }</div> <br />
					</div>
					<menu>
						<li><a href="../index" class="btn">返回活動清單</a></li>
					</menu>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
</div>