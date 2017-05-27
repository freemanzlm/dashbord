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
	var NOTIFICATIONSCOOKIENAME = "notices", notifications = [], currentNotificationIndex = 0, isLoadingNotifications=false;
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
			$dialog.off('close'); // prevent multiple call
			if (pageData && !pageData.admin) {
				logReadNotification(notification);
				sendFeedback(notification);
			}
			
			// display next notification.
			if (currentNotificationIndex < notifications.length - 1) {
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
		var notificationRead = cookieUtil.read(NOTIFICATIONSCOOKIENAME);
		return notificationRead ? notificationRead.split(",") : [];
	}
	
	function logReadNotification(notification) {
		// log the read notification id in cookie
		var notificationRead = cookieUtil.read(NOTIFICATIONSCOOKIENAME);
		if (!notificationRead || notificationRead.indexOf(notification.id) < 0) {
			notificationRead = notificationRead ? (notificationRead +  "," + notification.id) : notification.id;
			cookieUtil.create(NOTIFICATIONSCOOKIENAME, notificationRead);
		}
	}
	
	function clearNotificationLogs() {
		cookieUtil.remove(NOTIFICATIONSCOOKIENAME);
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
	
	function checkIfHasNotifications() {
		$.ajax("/promotion/hasnotifications", {
			type : 'GET',
			dataType : 'json',
			data: {timestamp:Date.now()},
			headers: {'Cache-Control': 'no-cache', 'Pragma': 'no-cache'},
			context : this,
			success : function(data) {
				// check if there is notification, then send the request to get notificaitons.
				if (data && data.status === true && data.data === true) {
					$(".latestNotification").show();
					getLatestNotifications();
				} else {
					isLoadingNotifications = false;
					$(".latestNotification").hide();
				}
			},
			error: function(){
				isLoadingNotifications = false;
			}
		});
	}
	
	function getLatestNotifications() {
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
			},
			complete: function() {
				isLoadingNotifications = false;
			}
		});
	}
	
	checkIfHasNotifications();
	
	$(".latestNotification a").click(function(){
		if (isLoadingNotifications) return;
		isLoadingNotifications = true;
		clearNotificationLogs();
		getLatestNotifications();
	});
});
</script>