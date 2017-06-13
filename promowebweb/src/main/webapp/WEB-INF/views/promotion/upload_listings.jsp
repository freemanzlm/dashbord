<%@ page trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:choose>
	<c:when test="${hasListingsNominated eq true}">
		<div class="listings-upload">
			<h3>重新提交刊登</h3>

			<div class="body mt20" style="width: 490px;">
				<p class="mt10">
					您可以通过下载<a class="template" href="/promotion/listings/downloadTemplate?promoId=${promo.promoId}" target="_self">已提交的刊登物品</a>修改并重新上传。
				</p>
				<p class="mt10">请注意：您新上传的刊登物品将完全替换之前的列表，并需要重新进行预审且接受活动条款。</p>

				<div class="mt10">
					<span style="float: left; font-weight: bold;">注：</span>
					<ul>
						<li>通过"确认报名"列标记参加活动的刊登或信息。标记为N的数据行不会被提交。</li>
						<li>请勿修改下载模板的文件格式。</li>
						<li>请勿修改、增减模板中的原有信息,包括每个单元格的属性.</li>
						<li>报名的信息请填写完整，不可留空。除非标记为选填项，或参照模板标题说明。</li>
						<li><a href="http://community.ebay.cn/portal.php?mod=view&aid=250" target="_blank">详细请查阅使用指南</a></li>
					</ul>
				</div>

				<div id="request-error" class="errors-summary mt10 hide">
					<p class="mb3"></p>
				</div>
				
				<div id="excel-errors" class="errors-summary mt10 hide">
					<p class="mb3">请注意，您提交的文件存在填写错误，请检查<span class="color-red">第{row}行</span>的信息。建议您再次提交前检查有没有类似的填写错误？避免再次提交失败。详细错误信息如下：</p>
					<ul>
						
					</ul>
				</div>

				<form id="upload-form" action="/promotion/listings/uploadListings" class="mt30" method="post"
					enctype="multipart/form-data" target="uploadIframe">
					<input type="hidden" name="promoId" value="${promo.promoId}" />
					选择上传您的刊登列表 <span class="file-input"><input
						type="text" style="height: 22px;" placeholder="选择文件" /> <input type="file" name="uploadFile"
						accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" />
						<button type="button" class="btn" style="margin-left: 3px;">选择</button></span>
				</form>
				<iframe name="uploadIframe" src="about:blank" style="display: none;"></iframe>
			</div>
		</div>
	</c:when>
	<c:otherwise>
		<div class="listings-upload">
			<h3>批量上传刊登</h3>
			
			<div class="body mt20" style="width: 545px;">
				<p>您可以通过下载<a class="template" href="/promotion/listings/downloadTemplate?promoId=${promo.promoId}" target="_self">批量提交模板</a>按格式填写并上传您的刊登参与本活动。</p>
				
				<div id="upload-error-msg" class="error-msg hide">
					<table>
						<tr>
							<td><span class="icon error"></span></td>
							<td><b></b></td>
						</tr>
					</table>
				</div>
				<!-- <p id="upload-error-msg" class="error-msg hide"><span class="icon error"></span><b ></b></p> -->
				
				<form id="upload-form" action="/promotion/listings/uploadListings" class="mt20" method="post" target="uploadIframe" enctype="multipart/form-data">
					选择上传您的刊登列表 
					<span class="file-input"><input type="text" style="height: 22px;" placeholder="选择文件" /> <input type="file" name="uploadFile" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" /> <button class="btn" style="margin-left: 3px;" type="button">选择</button></span>
					<input type="hidden" name="promoId" value="${promo.promoId}"/>
				</form>
				<iframe name="uploadIframe" src="about:blank" style="display: none;"></iframe>
				
				<div class="clr" style="margin-top: 30px; ">
					<span style="float: left; font-weight: bold;">注：</span>
					<ul>
						<li>通过"确认报名"列标记参加活动的刊登或信息。标记为N的数据行不会被提交。</li>
						<li>请勿修改下载模板的文件格式。</li>
						<li>请勿修改、增减模板中的原有信息,包括每个单元格的属性.</li>
						<li>报名的信息请填写完整，不可留空。除非标记为选填项，或参照模板标题说明。</li>
						<li><a href="http://community.ebay.cn/portal.php?mod=view&aid=250" target="_blank">详细请查阅使用指南</a></li>
					</ul>
				</div>
				
			</div>	
		</div>
	</c:otherwise>
</c:choose>
