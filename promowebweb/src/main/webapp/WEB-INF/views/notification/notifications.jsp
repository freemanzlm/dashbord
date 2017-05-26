<%@ page trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="res" uri="http://www.ebay.com/webres"%>

<res:useCss value="${res.css.local.css.notification_css}" target="head-css" />

<div style="display:none;">
	<div id="notification" class="notification-notice">
		<div class="notification-title"></div>
		<hr />
		<div class="notification-content">
		</div>
		<div class="actions" >
			<b><a class="cancel" href="javascript:void(0)">知道了</a></b>
		</div>		
	</div>
</div>

<script type="text/javascript">
$(function(){
	var notifications = [], currentNotificationIndex = 0;
	var $dialog = $("#notification"), $dialogTitle = $dialog.find(".notification-title");
	var $dialogContent = $dialog.find(".notification-content");
	
	function showNotification(notification) {
		// no notifation or notification is read, return.
		if (!notification || (!notification.always && notification.read)) return;
		
		$dialogTitle.html(notification.title);
		$dialogContent.html(notification.content);
		
		$dialog.dialog({
			 clazz : 'overlay'
		}).on('close', function(){
			if (pageData && !pageData.admin) {
				sendFeedback(notification);
			}			
			
			// display next notification.
			if (currentNotificationIndex < notification.length - 1) {
				showNotification(notifications[++currentNotificationIndex]);
			}
		});
	}
	
	function sendFeedback(notification) {
		if (!notification || !notification.needsFeedback) return;
		
		$.ajax({
			url : notification.feedbackURL, 
			type : notification.feedbackMethod,
			contentType : notification.feedbackRequestContentType,
			dataType : 'json'
		});
	}
	
	$.ajax("/promotion/notifications", {
		type : 'GET',
		dataType : 'json',
		data: {timestamp:Date.now()},
		headers: {'Cache-Control': 'no-cache', 'Pragma': 'no-cache'},
		context : this,
		success : function(data) {
			if (data && data.status === true && data.data) {
				  notifications = data.data || [];
				  if (notifications.length > 0) {
					  showNotification(notifications[0]);
				  }
			}
		}
	});
});
</script>