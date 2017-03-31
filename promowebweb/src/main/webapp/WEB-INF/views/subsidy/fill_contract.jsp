<%@ page trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<fmt:formatNumber value="${promo.reward }" var="reward" minFractionDigits="2"></fmt:formatNumber>

<div class="tabbable confirm-letter-steps">
	<div class="tab-list-container clr">
		<ul class="tab-list clr" role="tablist">
			<li role="tab" aria-controls="pane1"  class="active"><span class="label"><a
					href="#pane1">第一步：填写确认函</a></span></li>
			<li role="tab" aria-controls="pane2"><span class="label"><a
					href="#pane2">第二步：上传确认函</a></span></li>
			<li role="tab" aria-controls="pane3"><span
				class="label"><a href="#pane3">第三步：领取奖励</a></span></li>
		</ul>
	</div>
	<div id="pane1" class="tab-pane active confirm-letter-pane" role="tabpanel">
		<div class="pane wlt-binding">
			请注意：您绑定的<a target="_blank" href="http://www.ebay.cn/mkt/leadsform/efu/11183.html">万里通</a>账号是：xxx.
			<a href="http://www.wanlitong.com/myPoint/brandPointSch.do?fromType=avail&pageNo=1&brandPointNo=h5mg&dateType=0&sortFlag=ddd">查积分，积分当钱花。</a>
		</div>
		
		<h3 class="mt20 mb5 text-center">活动奖励确认函</h3>
		<hr />
		<h4 class="mt20 ml20">卖家基本信息：</h4>
		<form action="" class="form-horizontal">
		
			<c:forEach items="${ subsidyTerm.sellerFillingFields }" var="field">
				<c:if test="${ not empty field}">
					<div class="form-group">
						<div class="control-label">${field.label }：</div>
						<div class="form-field">
							<input type="text" name="${field.key }" value="${field.value }"/>
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
				<div class="control-label"><input type="checkbox" /></div>
				<div class="form-field">
					我已阅读并接受以下确认函内容
				</div>
			</div>
			<div class="text-center">
				<button type="button" class="btn">点击生成PDF共签署</button>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<button type="button" class="btn">下一步：上传确认函</button>
			</div>
		</form>
		<hr />
		
		<div class="pretty-text">
			${promo.desc}
		</div>
	</div>
	
	<div id="pane2" class="tab-pane confirm-letter-submission-pane" role="tabpanel">
		<div class="hint">
			<p>为了方便核实您的上传信息，确保您能尽快领取相关奖励，请仔细阅读以下内容：</p>
			<ol>
				<li>上传文件格式为“.jpg”或“ZIP”（多文件情况），单个上传文件不超过5M；</li>
				<li>上传文件内容须清晰，可识别且应完整齐全；</li>
				<li>不得在同一文件栏重复上传多个文件，一旦在同一上传文件栏重复上传文件时，我们将以提交前最后上传的那份为准；</li>
				<li>若未能按照上述要求操作的，我们将作“审核未通过”处理；</li>
				<li>上传截止日期为XXXX年XX月XX日（调用领取截止时间），未在该规定时间内上传文件的卖家将被视为自动放弃本活动奖励。</li>
			</ol>
		</div>
		<form action="" class="form-horizontal">
			<div class="form-group">
				<div class="control-label">上传已签署的确认函：</div>
				<div class="form-field">
					<span class="file-input">
						<input type="text" style="height: 22px;" placeholder="选择文件" />
						<input type="file" name="uploadFile" accept="application/pdf" />
						<button type="button" class="btn" style="margin-left: 3px;">选择</button>
					</span>
				</div>
			</div>
			
			<div class="text-center" style="margin-top: 40px;">
				<button type="submit" class="btn" style="width:120px;">上传</button>
			</div>
		</form>
	</div>
	<div id="pane3" class="tab-pane " role="tabpanel"
		aria-live="polite" aria-relevant="text">Tab Pane 3</div>
</div>