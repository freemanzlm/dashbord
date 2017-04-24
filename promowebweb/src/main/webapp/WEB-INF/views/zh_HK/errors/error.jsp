<%@ page trimDirectiveWhitespaces="true" isErrorPage="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="res" uri="http://www.ebay.com/webres"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<title>錯誤</title>
	<meta name="author" content="eBay: Apps">
	
	<link href="/promotion/css/normalize.css" rel="stylesheet" type="text/css" />
	<link href="/promotion/css/font.awesome.min.css" rel="stylesheet" type="text/css" />
	<link href="/promotion/css/reset.css" rel="stylesheet" type="text/css" />
	<link href="/promotion/css/base.css" rel="stylesheet" type="text/css" />
	
	<link href="/promotion/css/error.css" rel="stylesheet" type="text/css" />
	
	<style>
		html,body {
			height: 100%;
		}
		
		.error-box {
			margin: 0 auto;
			
			border: 2px solid #f00;
			border-radius: 5px;
			
			padding: 20px;
			width: 400px;
			
			text-align: center;
		}
	</style>
</head>

<body>
	
	<div style="display:table;height:100%;width:100%;">
		
		<div style="display:table-row" >
			<div style="display:table-cell; vertical-align:middle;" >
				<div class="error-box text-center mb15 clr">
					<h2 class="mb20"><i class="fa fa-times"></i>未知錯誤</h2>
					<p>很抱歉，請求發生异常，請稍後再試或聯系客戶經理。</p>
					<c:if test="${ not empty RaptorErrorData }">
						<c:choose>
							<c:when test="${ not empty RaptorErrorData.exception }">
								<br /><p>${RaptorErrorData.exception.message }</p>
							</c:when>
							<c:otherwise>
								<br /><p>${RaptorErrorData.errorMessage }</p>
							</c:otherwise>
						</c:choose>
					</c:if>
				</div>
			</div>
		</div>	
	
	</div>

</body>
</html>