<%@ page trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
		$("#y").click(function(){
			alert(111);
			var x = $("#x").val();
			$.ajax({
				url:"",
				data:{"test":x},
				type:"get",
				success:function(data){
					if(data){
						alert(data);
					}
				}
			});
		});
</script>
<div class="tabbable">
    a test page
    <p>${ha}</p>
    <p>${ha.id}</p>
    <p>${ha.oracleId}</p>
    <p>${ha.userId}</p>
    <p>${ha.ebayId}</p>
    <input id="x" name="accountName" type="text"></input>
    <input id="y" type="button" value="submit"></input>
    
</div>