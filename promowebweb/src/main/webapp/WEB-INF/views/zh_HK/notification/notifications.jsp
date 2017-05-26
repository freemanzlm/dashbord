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
	var notificationsCookieName = "notices", notifications = [], currentNotificationIndex = 0;
	var cookieUtil = cbt && cbt.cookie;
	var $dialog = $("#notification"), $dialogTitle = $dialog.find(".notification-title");
	var $dialogContent = $dialog.find(".notification-content");
	
	function showNotification(notification) {
		// no notifation or notification is read, return.
		if (!notification) return;
		
		$dialogTitle.html(notification.title);
		$dialogContent.html(notification.content);
		
		$dialog.dialog({
			 clazz : 'overlay'
		}).on('close', function(){
			if (pageData && !pageData.admin) {
				logReadNotification(notification);
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
	
	function loadReadNotificationIds() {
		var notificationRead = cookieUtil.read(notificationsCookieName);
		return notificationRead ? notificationRead.split(",") : [];
	}
	
	function logReadNotification(notification) {
		// log the read notification id in cookie
		var notificationRead = cookieUtil.read(notificationsCookieName);
		if (!notificationRead || notificationRead.indexOf(notification.id) < 0) {
			notificationRead = notificationRead ? (notificationRead +  "," + notification.id) : notification.id;
			cookieUtil.create(notificationsCookieName, notificationRead);
		}
	}
	
	function sortNotifications(notifications) {
		// remove read notification from queue.
		var readNotificationIds = loadReadNotificationIds();
		for (var i = 0; i < readNotificationIds.length; i++) {
			for (var j = 0; j< notifications.length; j++) {
				var notification = notifications[j];
				if (readNotificationIds[i] == notification.id && !notification.always) {
					notifications.splict(j, 1);
					break;
				}				
			}
		}
		
		// high priority notification displays firstly.
		notifications.sort(function(a, b){
			return a.priority < b.priority ? 1 : (a.priority == b.priority ? 0 : -1);
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
				  sortNotifications(notifications);
				  if (notifications.length > 0) {
					  showNotification(notifications[0]);
				  }
			}
		}
	});
});
</script>