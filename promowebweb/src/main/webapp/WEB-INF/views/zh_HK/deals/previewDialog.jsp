<%@ page trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="promoSubType" value="${ promo.promoSubType }" />

<div id="listing-preview-dialog" class="dialog">
	<a class="close"></a>
	<div class="dialog-header">
		<h2>已選擇的刊登預覽</h2>
	</div>
	<div class="dialog-pane">
		<div class="dialog-body">
			<div class="mt20">
				<div class="dataTable-container">
					<c:choose>
						<c:when test="${promoSubType eq 'GBH'}">
							<!-- china, brazil -->
							<table id="listing-preview-table" class="dataTable header-no-wrap">
								<thead>
									<tr>
										<th class="check"><input type="checkbox" class="check-all" /></th>
										<th class="item-id">刊登編號<br/>Item ID</th>
										<th class="sku-name">招募SKU名称<br />SKU</th>
										<th class="category">产品品类<br />Product Category</th>
										<th class="last-price">原价<br/>List Price (MSRP)</th>
										<th class="deal-price">活动价<br/>Deal Price</th>
										<th class="quantity">数量<br/>Quantity</th>
										<th class="site">刊登站点<br/>Listing Site</th>
										<th class="worldwide">是否寄送全球<br/>Worldwide</th>
										<th class="worldwide-charge">寄送全球运费<br/>Worldwide Shipping Charge</th>
										<th class="russia">是否寄送俄罗斯<br/>Russia</th>
										<th class="russia-charge">寄送全球运费<br/>Russia Shipping Charge</th>
										<th class="china">是否寄送中国<br/>China</th>
										<th class="china-charge">寄送中国运费<br/>China Shipping Charge</th>
										<th class="latin">是否寄送拉美<br/>Latin</th>
										<th class="latin-charge">寄送拉美运费<br/>Latin America Shipping Charge</th>
										<th class="mexico">是否寄送墨西哥<br/>Mexico</th>
										<th class="mexico-charge">寄送墨西哥运费<br/>Mexico Shipping Charge</th>
										<th class="brazil">是否寄送巴西<br/>Brazil</th>
										<th class="brazil-charge">寄送巴西运费<br/>Brazil Shipping Charge</th>
										<th class="israel">是否寄送以色列<br/>Israel</th>
										<th class="israel-charge">寄送以色列运费<br/>Israel Shipping Charge</th>
										<th class="currency">刊登币种<br/>Currency</th>
									</tr>
								</thead>
								<tbody>
									
								</tbody>
							</table>
						</c:when>
						<c:when test="${promoSubType eq 'FRES'}">
							<!-- French and spain -->
							<table id="listing-preview-table" class="dataTable header-no-wrap">
								<thead>
									<tr>
										<th class="check"><input type="checkbox" class="check-all" /></th>
										<th class="item-id">刊登編號<br/>Item ID</th>
										<th class="sku-name">招募SKU名称<br />SKU</th>
										<th class="category">产品品类<br />Product Category</th>
										<th class="fvf">成交费率<br/>FvF%</th>
										<th class="last-price">原价<br/>List Price(MSRP)</th>
										<th class="deal-price">活动价<br/>Deal Price</th>
										<th class="quantity">数量<br/>Quantity</th>
										<th class="location">仓储地址<br/>Item Location</th>
										<th class="delivery">妥投时间<br/>Delivery Time</th>
										<th class="ship-price">运费<br/>Shipping Price</th>
										<th class="rrp-link">产品全网最低价<br/>RRP Link</th>
										<th class="currency">刊登币种<br/>Currency</th>
									</tr>
								</thead>
								<tbody>
									
								</tbody>
							</table>
						</c:when>
						<c:when test="${promoSubType eq 'APAC'}">
							<!-- US -->
							<table id="listing-preview-table" class="dataTable header-no-wrap">
								<thead>
									<tr>
										<th class="check"><input type="checkbox" class="check-all" /></th>
										<th class="item-id">刊登編號<br/>Item ID</th>
										<th class="sku-name">招募SKU名称<br />SKU</th>
										<th class="category">产品品类<br />Product Category</th>
										<th class="last-price">原价<br/>List Price(MSRP)</th>
										<th class="deal-price">活动价<br/>Deal Price</th>
										<th class="quantity">数量<br/>Quantity</th>
										<th class="rrp-link">产品全网最低价<br/>RRP Link</th>
										<th class="currency">刊登币种<br/>Currency</th>
									</tr>
								</thead>
								<tbody>
									
								</tbody>
							</table>
						</c:when>
						<c:otherwise>
							<table id="listing-preview-table" class="dataTable">
								<thead>
									<tr>
										<th class="check"><input type="checkbox" class="check-all" /></th>
										<th class="item-id">刊登編號</th>
										<th class="name">SKU名稱</th>
										<th class="price">當前刊登單價</th>
										<th class="activity-price">建議活動單價</th>
										<th class="inventory">刊登庫存量</th>
										<th class="state">狀態</th>
										<th class="currency"></th>
									</tr>
								</thead>
								<tbody>
									
								</tbody>
							</table>
						</c:otherwise>
					</c:choose>
					
				</div>
			</div>
		</div>

		<div class="page-bottom-actions">
			<a class="cancel" href="javascript:void(0)">返回修改</a>
			<button type="button" class="btn btn-s btn-prim ok">提交正式報名</button>
		</div>
	</div>
</div>