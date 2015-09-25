<%@ page trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8"%>

<div class="dialog" id="terms-dialog">
	<a class="close"></a>
	<div class="dialog-header">
		<h2>法律協定</h2>
	</div>
	<div class="dialog-pane">
		<div class="dialog-body">
			<div class="pretty-text">
				<c:if test="${promo.type eq 0}">
					<ul>
						<li>參加者的參加活動eBay帳戶及其其他eBay帳戶均必須保持良好運作，於活動期間避免被暫停使用或被凍結，或逾期繳費，否則eBay有 權取消參加資格或作其他決定。</li>
						<li>參加者的eBay帳戶不能於活動時間期間被暫停使用或被凍結。</li>
						<li>參加者及其刊登貨品，均須遵守eBay相關網站的所有條款及政策，包括但不限於刊登規則、違禁品及管制物品政策、保護知識財產權
							方案等。如有違反，參加者即喪失參加活動資格，並不得獲得活動補貼；。參加者於活動期間將受所有eBay相關網站的使用條款及細
							則監管，並根據eBay正常環境運作。eBay並不對參加者的交易額作出保證亦不對其刊登及交易作出任何特別處理。參加者必須理解網 上交易的風險及自行承擔一切後果。</li>
						<li>所有參加者於eBay.com.hk 網站上所登記或提供予eBay之個人資料必須為真實及正確。若有任何欺詐、冒用或盜用第三者之資 料，eBay有權取消其參加本次活動資格。</li>
						<li>如果eBay認為參加者1)存在任何電子或其他形式的可疑或有實際證據表明濫用本活動的任何行為，包括但是不限於操縱活動或成交費
							的計算；或2)因為電腦病毒、程式漏洞、未經授權的干預、欺詐或技術故障,違背、破壞或影響了本次活動的管理、完整、安全、公
							平；或3)因為任何原因本次活動不能正常運行或按計劃繼續，則eBay有權自行決定：取消任何有關個人、嫌疑人或在活動中涉及上述 行為的相關人員、賣家及/或帳戶的資格，並有權修改本次活動包括條款、或立即取消本次活動資格，而無需另行通知。</li>
						<li>輸入資訊的接受時間以eBay資料庫的接收時間為准。eBay不會對在傳送中因為賣家操作或疏忽或其他任何失敗、錯誤或錯漏引起的輸
							入資訊未收到或延遲、不正確、不準確或不完整承擔責任。eBay不會對任何電話、通訊或電腦網路、線路、服務、電話或互聯網供
							應商、電話或電腦網路阻塞或任何以上原因的綜合結果產生的問題或技術故障承擔責任,包括對賣家或其他與參加活動相關、或由於參 加或發送或接受任何通信或任何資料的手持設備或電腦的任何損傷或損壞。</li>
						<li>eBay International AG公司(「eBay」)及其相關關聯公司、分支機搆的雇員及相關人員，包括其直系親屬(配偶、父母、子女、兄弟姐 妹及其配偶)及與這些雇員共同居住的個人不能參加此次活動。</li>
						<li>有限責任：eBay提醒參與者瞭解互聯網的特性及限制，eBay不會為通訊網路、線上系統或電腦硬體或軟體故障引起的,可能影響推廣或 達到特定目標的任何問題或技術故障承擔責任。</li>
						<li>本次活動受這些條款及細則的約束，參加者須細閱各項條款及細則。如參加本活動，所有參與者均將視為已經接受並同意接受這些條 款及條件。</li>
						<li>本次活動不能合併或轉讓。本次活動和由此產生的任何補助不能出售、兌換、交換或與其他優惠、折扣或推廣優惠合併適用。</li>
						<li>管轄法律：這些條款和細則所約定的所有條款應受香港特別行政區法例約束，並依其解釋。eBay各網站的運營仍然適用於該網站所在 地的法律之管轄，並依其解釋。</li>
					</ul>
				</c:if>
				
				<c:if test="${ promo.type eq 1 or promo.type eq 2 }">
					<ul>
						<li>除非另有規定，本活動的參加者不可同時參加eBay組織的其他活動。</li>
						<li>參加者的參加活動eBay帳戶及其其他eBay帳戶均必須保持良好運作，於活動期間必需維持eBay全球賣家的最低標準，避免被暫停使用或被凍結，或逾期繳費，否則eBay有權取消參加資格或作其他决定。</li>
						<li>參加者及其刊登貨品，均須遵守eBay相關網站的所有條款及政策，包括但不限於刊登規則、違禁品及管制物品政策、保護智慧財產權方案等。如有違反，即喪失參加活動資格，並不得獲得可能產生的活動獎勵（如有）。參加者於活動期間將受所有eBay相關網站的使用條款及細則監管，並根據eBay正常環境運作。eBay並不對參加者的交易額作出保證亦不對其刊登及交易作出任何特別處理。參加者必須理解網上交易的風險及自行承擔一切後果。</li>

						<li>所有參加者於eBay.com.hk網站上所登記或提供予eBay之個人資料必須為真實及正確。若有任何欺詐、冒用或盜用第三者之資料，eBay有權取消其參加本次活動資格。</li>
						
						<li>如果eBay認為參加者1）存在任何電子或其他形式的可疑或有實際證據表明濫用本活動的任何行為，包括但是不限於操縱活動或成交費的計算；或2）因為電腦病毒、程式漏洞、未經授權的幹預、欺詐或科技故障，違背、破壞或影響了本次活動的管理、完整、安全、公平；或3）因為任何原因本次活動不能正常運行或按計畫繼續，則eBay有權自行决定：取消任何有關個人、嫌疑人或在活動中涉及上述行為的相關人員、賣家及/或帳戶的資格，並有權修改本次活動包括條款、或立即取消本次活動資格，而無需另行通知。</li>
						
						<li>輸入資訊的接受時間以eBay資料庫的接收時間為准。eBay不會對在傳送中因為賣家操作或疏忽或其他任何失敗、錯誤或錯漏引起的輸入資訊未收到或延遲、不正確、不準確或不完整承擔責任。eBay不會對任何電話、通訊或電腦網絡、線路、服務、電話或互聯網供應商、電話或電腦網絡阻塞或任何以上原因的綜合結果產生的問題或科技故障承擔責任，包括對賣家或其他與參加活動相關、或由於參加或發送或接受任何通信或任何數據的手持設備或電腦的任何損傷或損壞。</li>
						
						<li>eBay International AG公司（「eBay」）及其相關關聯公司、分支機搆的雇員及相關人員，包括其直系親屬（配偶、父母、子女、兄弟姐妹及其配偶）及與這些雇員共同居住的個人不能參加此次活動。</li>
						
						<li>有限責任：eBay提醒參與者瞭解互聯網的特性及限制，eBay不會為通訊網絡、線上系統或電腦硬體或軟件故障引起的，可能影響推廣或達到特定目標的任何問題或科技故障承擔責任。</li>
						
						<li>本次活動受這些條款及細則的約束，參加者須細閱各項條款及細則。如參加本活動，所有參與者均將視為已經接受並同意接受這些條款及條件。</li>
						
						<li>本次活動不能合併或轉讓。本次活動和由此產生的任何補助不能出售、兌換、交換或與其他優惠、折扣或推廣優惠合併適用。</li>
						
						<li>管轄法律：本活動所有條款應受香港特別行政區法律之管轄，並依其解釋。eBay各網站的運營仍然適用於該網站所在地的法律之管轄，並依其解釋。</li>
					</ul>
				</c:if>
			</div>
			<%-- <iframe src="${agreement}" frameborder="0" width="810" style="overflow: hidden;"></iframe> --%>
		</div>

		<div class="btns clz">
			<button class="btn btn-s btn-prim ok" style="margin-right: 10px;">確認</button>
		</div>
	</div>
</div>