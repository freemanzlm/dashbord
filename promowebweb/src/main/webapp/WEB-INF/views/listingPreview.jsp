<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="res" uri="http://www.ebay.com/webres"%>
<%@ taglib prefix="rui" uri="http://ebay.com/uicomponents"%>
<%@ taglib prefix="r" uri="http://ebay.com/raptor"%>

<r:includeJquery jsSlot="head" />
<r:client />
	
<!DOCTYPE html>
<html>
<head>

	<title>已选择的刊登预览</title>
	<meta name="description" content="Deals招募">
	<meta name="author" content="eBay: Apps">
	<res:cssSlot id="head" />
	<res:cssSlot id="head-css" />
	
	<script type="text/javascript">
		var BizReport = BizReport || {};
	</script>
	<res:jsSlot id="head" />	
	<res:jsSlot id="head-js" />
	
	
	
	<%--module "ebay.page" add Resets and Global css --%>
	<res:useCss value="${res.css.local.css['normalize.css']}" target="head-css"/>
	<res:useCss value="${res.css.local.css['font.awesome.min.css']}" target="head-css"/>
	<res:useCss value="${res.css.local.css['jquery.dataTables.1.10.css']}" target="head-css"/>
	<res:useCss value="${res.css.local.css['dataTables.override.css']}" target="head-css"/>
	<res:useCss value="${res.css.local.css.reset_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.icon_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.button_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.dropdown_css}" target="head-css"/>
	<res:useCss value="${res.css.local.less.module_less}" target="head-css" />
	<res:useCss value="${res.css.local.less.form_less}" target="head-css" />
	<res:useCss value="${res.css.local.css.dialog_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.popup_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.layout_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.error_css}" target="head-css" />
	<res:useCss value="${res.css.local.css.header_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.topNavigation_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.promotion_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.base_css}" target="head-css"/>
	
	<res:useJs value="${res.js.local.js['extension.js']}" target="head"></res:useJs>
	<res:useJs value="${res.js.local.js['util.js']}" target="head"></res:useJs>
	<res:useJs value="${res.js.local.js['local_zh_CN.js']}" target="head"></res:useJs>
	<res:useJs value="${res.js.local.js['cookie.js']}" target="head"></res:useJs>
	<res:useJs value="${res.js.local.js.lib['widget.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.lib['mask.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.lib['posManager.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js['dropdown.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.jquery['jquery.dataTables.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.jquery['jquery.isloading.js']}" target="page-js"></res:useJs>
	
	<res:useJs value="${res.js.local.js.dialog['dialog.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.dialog['alert.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.jquery['DataTable.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.table['ListingTable.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js['popup.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.page['listing_preview.js']}" target="page-js2"></res:useJs>	

</head>

<body>
	<div class="container">
		<!--  Global Header -->
		<jsp:include page="header.jsp"></jsp:include>
		<!-- end: Global Header -->

		<div id="page">
			<div id="page-pane">
				<div class="pane">
					<h2>已选择的刊登预览:如需上传相关报名所需材料,请补充</h2>

					<div class="mt20">
						<%@ include file="table/listings_no_id_no_state.jsp" %>
					</div>
					
					<div id="attachments-errors" class="errors-summary mt10 hide">
						<p class="mb3">请注意，您提交的<span class="color-red">第{row}行</span>附件存在错误，允许的附件格式为：PDF，doc, docx,xls,xlsx,JPG,ZIP,RAR。重复上传按最终版本，每个文件不超过3MB。建议您再次提交前检查有没有类似的填写错误？避免再次提交失败。</p>
					</div>

					<div class="mt20 page-bottom-actions">
						<div id="submit-form">
							<a href="/promotion/${promo.promoId}">返回修改</a>
							<c:choose>
								<c:when test="${ promo.type eq 2 }">
									<button id="submit-btn" class="btn">提交认证</button>
								</c:when>
								<c:otherwise>
									<button id="submit-btn" class="btn">提交预审</button>
								</c:otherwise>
							</c:choose>							
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- Global Footer -->
		<jsp:include page="footer.jsp"></jsp:include>
		<!-- End: Global Footer -->
	</div>

	<%@ include file="dialog/alert.jsp"%>
	<%@ include file="dialog/terms.jsp"%>

	<script type="text/javascript">
		var pageData = {
			promoId : '${promo.promoId}',
			columns: JSON.parse('${not empty columns ? columns : "{}"}'),
			isListingPreview: true
		};
	</script>

	<res:jsSlot id="body" />
	<res:jsSlot id="page-js" />
	<res:jsSlot id="page-js2" />
	<res:jsSlot id="exec-js" />
</body>
</html>