<%@ page trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="dialog" id="terms-dialog">
	<a class="close"></a>
	<div class="dialog-header">
		<h2>法律协议</h2>
	</div>
	<div class="dialog-pane">
		<div class="dialog-body">
			<div class="pretty-text">
				<c:choose>
					<c:when test="${promo.region eq 'HK' or promo.region eq 'TW' }">
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
					</c:when>
					<c:otherwise>
						<c:if test="${promo.type eq 0}">
							<ul>
								<li>除非另有规定，此推广不可与eBay其他的推广活动同时使用。</li>
								<li>参加者的参加活动eBay账户及其其他eBay账户均必须保持良好运作，于活动期间避免被暂停使用或被冻结，或逾期缴费，否则eBay有 权取消参加资格或作其他决定。</li>
								<li>参加者的eBay账户不能于活动时间期间被暂停使用或被冻结。</li>
								<li>参加者及其刊登货品，均须遵守eBay相关网站的所有条款及政策，包括但不限于刊登规则、违禁品及管制物品政策、保护知识产权方
									案等。如有违反，即丧失参加活动资格，并不得获得活动奖励；。参加者于活动期间将受所有eBay相关网站的使用条款及细则监管，
									并根据eBay正常环境运作。eBay并不对参加者的交易额作出保证亦不对其刊登及交易作出任何特别处理。参加者必须理解网上交易的 风险及自行承担一切后果。</li>
								<li>所有参加者于eBay.com.hk 网站上所登记或提供予eBay之个人资料必须为真实及正确。若有任何欺诈、冒用或盗用第三者之资 料，eBay有权取消其参加本次活动资格。</li>
								<li>如果eBay认为参加者1)存在任何电子或其他形式的可疑或有实际证据表明滥用本活动的任何行为，包括但是不限于操纵活动或成交费
									的计算；或2)因为计算机病毒、程序漏洞、未经授权的干预、欺诈或技术故障,违背、破坏或影响了本次活动的管理、完整、安全、公
									平；或3)因为任何原因本次活动不能正常运行或按计划继续，则eBay有权自行决定：取消任何有关个人、嫌疑人或在活动中涉及上述 行为的相关人员、卖家及/或账户的资格，并有权修改本次活动包括条款、或立即取消本次活动资格，而无需另行通知。</li>
								<li>输入信息的接受时间以eBay数据库的接收时间为准。eBay不会对在传送中因为卖家操作或疏忽或其他任何失败、错误或错漏引起的输
									入信息未收到或延迟、不正确、不准确或不完整承担责任。eBay不会对任何电话、通讯或计算机网络、线路、服务、电话或互联网供
									货商、电话或计算机网络阻塞或任何以上原因的综合结果产生的问题或技术故障承担责任,包括对卖家或其他与参加活动相关、或由于 参加或发送或接受任何通信或任何数据的手持设备或计算机的任何损伤或损坏。</li>
								<li>eBay International AG公司(「eBay」)及其相关关联公司、分支机构的雇员及相关人员，包括其直系亲属(配偶、父母、子女、兄弟姐 妹及其配偶)及与这些雇员共同居住的个人不能参加此次活动。</li>
								<li>有限责任：eBay提醒参与者了解互联网的特性及限制，eBay不会为通讯网络、在线系统或计算机硬件或软件故障引起的,可能影响推广 或达到特定目标的任何问题或技术故障承担责任。</li>
								<li>本次活动受这些条款及细则的约束，参加者须细阅各项条款及细则。如参加本活动，所有参与者均将视为已经接受并同意接受这些条 款及条件。</li>
								<li>本次活动不能合并或转让。本次活动和由此产生的任何补助不能出售、兑换、交换或与其他优惠、折扣或推广优惠合并适用。</li>
								<li>管辖法律：这些条款和细则所约定的所有条款应受香港特别行政区法律之管辖，并依其解释。eBay各网站的运营仍然适用于该网站 所在地的法律之管辖，并依其解释。</li>
							</ul>
						</c:if>
						
						<c:if test="${ promo.type eq 1 or promo.type eq 2 }">
							<ul>
								<li>除非另有规定，本活动的参加者不可同时参加eBay组织的其他活动。</li>
								<li>参加者的参加活动eBay账户及其其他eBay账户均必须保持良好运作，于活动期间必需维持eBay全球卖家的最低标准， 避免被暂停使用或被冻结，或逾期缴费，否则eBay有权取消参加资格或作其他决定。</li>
								<li>参加者及其刊登货品，均须遵守eBay相关网站的所有条款及政策，包括但不限于刊登规则、违禁品及管制物品政策、保护知识产权方案等。如有违反，即丧失参加活动资格，并不得获得可能产生的活动奖励（如有）。参加者于活动期间将受所有eBay相关网站的使用条款及细则监管，并根据eBay正常环境运作。eBay并不对参加者的交易额作出保证亦不对其刊登及交易作出任何特别处理。参加者必须理解网上交易的风险及自行承担一切后果。</li>
								<li>所有参加者于eBay.com.hk 网站上所登记或提供予eBay之个人资料必须为真实及正确。若有任何欺诈、冒用或盗用第三者之资料，eBay有权取消其参加本次活动资格。</li>
								<li>如果eBay认为参加者1)存在任何电子或其他形式的可疑或有实际证据表明滥用本活动的任何行为，包括但是不限于操纵活动或成交费的计算；或2)因为计算机病毒、程序漏洞、未经授权的干预、欺诈或技术故障,违背、破坏或影响了本次活动的管理、完整、安全、公平；或3)因为任何原因本次活动不能正常运行或按计划继续，则eBay有权自行决定：取消任何有关个人、嫌疑人或在活动中涉及上述行为的相关人员、卖家及/或账户的资格，并有权修改本次活动包括条款、或立即取消本次活动资格，而无需另行通知。</li>
								<li>输入信息的接受时间以eBay数据库的接收时间为准。eBay不会对在传送中因为卖家操作或疏忽或其他任何失败、错误或错漏引起的输入信息未收到或延迟、不正确、不准确或不完整承担责任。eBay不会对任何电话、通讯或计算机网络、线路、服务、电话或互联网供货商、电话或计算机网络阻塞或任何以上原因的综合结果产生的问题或技术故障承担责任,包括对卖家或其他与参加活动相关、或由于参加或发送或接受任何通信或任何数据的手持设备或计算机的任何损伤或损坏。</li>
								<li>eBay International AG公司(「eBay」)及其相关关联公司、分支机构的雇员及相关人员，包括其直系亲属(配偶、父母、子女、兄弟姐妹及其配偶)及与这些雇员共同居住的个人不能参加此次活动。</li>
								<li>有限责任：eBay提醒参与者了解互联网的特性及限制，eBay不会为通讯网络、在线系统或计算机硬件或软件故障引起的,可能影响推广或达到特定目标的任何问题或技术故障承担责任。</li>
								<li>本次活动受这些条款及细则的约束，参加者须细阅各项条款及细则。如参加本活动，所有参与者均将视为已经接受并同意接受这些条款及条件。</li>
								<li>本次活动不能合并或转让。本次活动和由此产生的任何补助不能出售、兑换、交换或与其他优惠、折扣或推广优惠合并适用。</li>
								<li>管辖法律：本活动所有条款应受香港特别行政区法律之管辖，并依其解释。eBay各网站的运营仍然适用于该网站所在地的法律之管辖，并依其解释。</li>
							</ul>
						</c:if>
					</c:otherwise>
				</c:choose>
				
			</div>
			<%-- <iframe src="${agreement}" frameborder="0" width="810" style="overflow: hidden;"></iframe> --%>
		</div>

		<div class="btns clz">
			<button class="btn btn-s btn-prim ok" style="margin-right: 10px;">确认</button>
		</div>
	</div>
</div>