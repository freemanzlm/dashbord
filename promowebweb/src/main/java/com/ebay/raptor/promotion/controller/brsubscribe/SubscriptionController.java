/**
 * 
 */
package com.ebay.raptor.promotion.controller.brsubscribe;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebay.app.raptor.promocommon.CommonLogger;
import com.ebay.app.raptor.promocommon.MissingArgumentException;
import com.ebay.app.raptor.promocommon.httpRequest.HttpRequestException;
import com.ebay.raptor.promotion.service.BaseDataService;
import com.ebay.soaframework.common.config.RequestParam;

/**
 * @author binkang
 * @date 2016-8-30
 */
@RequestMapping("/subscription")
@Controller
public class SubscriptionController
{
	private static CommonLogger logger = CommonLogger.getInstance(SubscriptionController.class);

	@Autowired
	BaseDataService baseService;

	@RequestMapping(value = "/subscribe")
	@ResponseBody
	public Map<String, Object> subscribe(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute RequestParam param) throws MissingArgumentException
	{
		long userId = Long.valueOf(request.getParameter("userId"));
		int whitelistType = Integer.valueOf(request.getParameter("whitelistType"));// FIXME, param.getWhitelistType)(),1 as conversion,2 as DDS，3 as both

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		boolean result = false;

		try
		{
			result = baseService.addWhitelist(userId, whitelistType);
		}
		catch (HttpRequestException e)
		{
			logger.error(String.format("Can't add whitelist with info[userId=%d, whitelistType=%d]",userId, whitelistType), e);
		}

		jsonMap.put("status", result);
		return jsonMap;
	}

	@RequestMapping(value = "/subscribeDialogClosed")
	@ResponseBody
	public Map<String, Object> subscribeDialogClosed(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute RequestParam param) throws MissingArgumentException
	{
		long userId = Long.valueOf(request.getParameter("userId"));
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		boolean result = false;
		try
		{
			result = baseService.subscribeDialogClosed(userId);
		}
		catch(HttpRequestException e)
		{
			logger.error(String.format("Can't persist subscription dialog closed to DB with info[userId=%d]",userId), e);
		}
		jsonMap.put("status", result);
		return jsonMap;
	}
}
