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
			url : "getNotification?userId="+"${userId}", 
			type : 'GET',
			contentType : 'application/json',
			dataType : 'json',
			success : function(data) {
			if(!isFirstLoad) return; 
			
			/* enhancement */
			var resData = eval("(" + data.data.replace(/\\/g, '') + ")");
			
				/*check if display latestnotification icon*/
				switch (resData.errormsg){
				case "No notification" :
					$(".latestNotification").hide();
					break;
				default:
					$(".latestNotification").css('display', 'block');
				break;
				};
				
				if (data.status == true && resData.data !== "") {
					
					/*get notification title from db*/
					var notification = resData.data.split('boundary');
					var notificationTitle = notification[0].replace('contentBoundary', '');
					
					$('#notification-title').html(notificationTitle);
					
					/*get notification content from db*/
					var notificationImage = notification[1];
					if (notificationImage) {
						$('#images').html(notificationImage); 
						$("#notification-dialog").on('show', function() {
						}).on('close', function(){
							$("body").removeAttr("style");
							$.ajax({
								url : "/dashboard/setSDNotifiStatus?userId="+"${userId}", 
								type : 'GET',
								contentType : 'application/json',
								dataType : 'json',
							});   
						}).dialog({
							 clazz : 'overlay'
						}).dialog();
					}
					isFirstLoad = false;
					$("body").height($(window).height()).css({
						  "overflow-y": "hidden"
					});
				} 
			}
		});
		
		$("#known").click(function () {
			$("#notification-dialog").on('close', function(){
				  $("body").removeAttr("style");
				  $.ajax({
					url : "setSDNotifiStatus?userId="+"${userId}", 
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
				url : "/dashboard/getNotiIgnoreSatus?userId="+"${userId}", 
				type : 'GET',
				contentType : 'application/json',
				dataType : 'json',
				success : function(data) {
				if(!isFirstLoad) return; 
				var resData = eval("(" + data.data.replace(/\\/g, '') + ")");
				
					if (data.status == true && resData.data !== "") {
						
						/* get notification title from db */
						var notificationWhole = resData.data.replace('contentBoundary', '').split('traditional');
						var simplifiedNoti = notificationWhole[0].split('boundary');
						var tradtionalNoti = notificationWhole[1].split('boundary');
						
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
									url : "/dashboard/setSDNotifiStatus?userId="+"${userId}", 
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