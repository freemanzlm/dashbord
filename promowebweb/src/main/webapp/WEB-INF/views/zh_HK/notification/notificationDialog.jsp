<%@ page trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="res" uri="http://www.ebay.com/webres"%>

<res:cssSlot id="head-css" />
<res:useCss value="${res.css.local.css.notification_css}" target="head-css" />

<div style="display:none;">
	<div id="notification-dialog" class="notification-notice">
		<div id="notification-title"></div>
		<hr />
		<div class="notification-content">
			<div id="images"></div>
		</div>
		<div class="actions" >
			<b><a id="known" class="cancel">知道了</a></b>
		</div>		
	</div>
</div>

<script type="text/javascript">
	$(function() {
		var isFirstLoad = true;
		$.ajax({
			url : "getNotification"+"?userId="+"${userId}", 
			type : 'GET',
			contentType : 'application/json',
			dataType : 'json',
			success : function(data) {
			if(!isFirstLoad) return; 
				if (data.status == true && data.data.length > 0) {
					
					/*check if display latestnotification icon*/
					var displayLatestnoti = data.data.split('\"')[3];
					
					switch (displayLatestnoti){
					case "No notification" :
						$(".latestNotification").hide();
						break;
					default:
						$(".latestNotification").css('display', 'block');
					break;
					};
					
					var dataNull = data.data.replace(/\\/g, '');
					var dataNullJson = eval("(" + dataNull + ")");
					if (dataNullJson.data.length > 0) {
						
						/* get notification title from db */
						var notificationWhole = data.data.split('contentBoundary')[1];
						var simplifiedNoti = notificationWhole.split('traditional')[0].replace(/\\/g, '').split('boundary').slice(0,2);
						var tradtionalNoti = notificationWhole.split('traditional')[1].replace(/\\/g, '').split('boundary').slice(0,2);
						
						/* if traditional or simplified chinese */
						if (tradtionalNoti[0] == "") {
								notification = simplifiedNoti
						} else {
							var notification = tradtionalNoti;
						};
						
						var notificationTitle = notification[0];
						$('#notification-title').html(notificationTitle);
						
						/*get notification content from db*/
						var notificationImage = notification[1];
						if (notificationImage) {
							$('#images').html(notificationImage); 
							$("#notification-dialog").on('show', function() {
							}).on('close', function(){
								  $.ajax({
									url : "setSDNotifiStatus?userId="+"${userId}", 
									type : 'GET',
									contentType : 'application/json',
									dataType : 'json',
								});   
							}).dialog({
								 clazz : 'overlay'
							}).dialog();
						}
						isFirstLoad = false;
					}
				} 
			}
		});
		
		$("#known").click(function () {
			$("#notification-dialog").on('close', function(){
				  $("body").removeAttr("style");
				  $.ajax({
					url : "setSDNotifiStatus"+"?userId="+"${userId}", 
					type : 'GET',
					contentType : 'application/json',
					dataType : 'json',
				});   
			}).dialog({
				 clazz : 'overlay'
			}).dialog();
		});
	});
	
	$(".latestNotification").click(function () {
		var isFirstLoad  = true;
		$('#notification-title').html("");
		$('#images').html(""); 
		$("#notification-dialog").on('show', function() { 
			$.ajax({
				url : "getNotiIgnoreSatus"+"?userId="+"${userId}", 
				type : 'GET',
				contentType : 'application/json',
				dataType : 'json',
				success : function(data) {
				if(!isFirstLoad) return; 
					if (data.status == true && data.data.length > 0) {
						
						/* get notification title from db */
						var notificationWhole = data.data.split('contentBoundary')[1];
						var simplifiedNoti = notificationWhole.split('traditional')[0].replace(/\\/g, '').split('boundary').slice(0,2);
						var tradtionalNoti = notificationWhole.split('traditional')[1].replace(/\\/g, '').split('boundary').slice(0,2);
						
						/* if traditional or simplified chinese */
						var lang = $('#lang').val();
						if (lang == 'zh_HK' ) {
							var notification = tradtionalNoti;
							if (tradtionalNoti[0] == "") {
								notification = simplifiedNoti
							} 
						} else {
							var notification = simplifiedNoti;
						};
						
						var notificationTitle = notification[0];
						$('#notification-title').html(notificationTitle);
						
						/* get notification content from db */
						var notificationImage = notification[1];
						$('#images').html(notificationImage); 
							$("#notification-dialog").on('show', function() {
							}).on('close', function(){
								  $.ajax({
									url : "setSDNotifiStatus"+"?userId="+"${userId}", 
									type : 'GET',
									contentType : 'application/json',
									dataType : 'json',
								});   
							}).dialog({
								 clazz : 'overlay'
							}).dialog();
						}
						isFirstLoad = false;
				},
				error : function() {
				}
			});
			$("body").height($(window).height()).css({
				  "overflow-y": "hidden"
				});
		}).on('close', function(){ 
			$("body").removeAttr("style");
		}).dialog({
			 clazz : 'overlay'
		}).dialog();
	});
</script>