package com.ebay.raptor.promotion.index.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebay.app.raptor.promocommon.MissingArgumentException;
import com.ebay.kernel.calwrapper.CalEventHelper;
import com.ebay.kernel.logger.LogLevel;
import com.ebay.kernel.logger.Logger;
import com.ebay.raptor.promotion.pojo.Notification;
import com.ebay.raptor.promotion.pojo.UserData;
import com.ebay.raptor.promotion.pojo.web.resp.BaseWebResponse;
import com.ebay.raptor.promotion.pojo.web.resp.DataWebResponse;
import com.ebay.raptor.promotion.pojo.web.resp.ListDataWebResponse;
import com.ebay.raptor.promotion.sd.service.SDDataService;
import com.ebay.raptor.promotion.service.LoginService;
import com.ebay.raptor.promotion.service.NotificationService;

/**
 * Default JSON response request is written in this controller.
 * 
 * @author lyan2
 */
@Controller()
@RequestMapping("/")
public class DataController {
	private static Logger logger = Logger.getInstance(IndexController.class);
	
	@Autowired LoginService loginService;
	@Autowired SDDataService sdDataService;
	@Autowired NotificationService notificationService;
	
	@RequestMapping(value = "/setSDNotifiStatus", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> setSDNotifiStatus(HttpServletRequest request, HttpServletResponse rsp) throws MissingArgumentException {
		UserData userData = loginService.getUserDataFromCookie(request);
		String resultJson = null;
		try {
			resultJson = sdDataService.setSDNotifiStatus(userData.getUserId());
		} catch (HttpException e) {
			logger.log(LogLevel.ERROR, "Unable to get setSDNotifiStatus for " + userData.getUserId(), e);
		}
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("status", true);
		jsonMap.put("data", resultJson);
		return jsonMap;
	}
	
	@RequestMapping(value = "/hasnotifications", method = RequestMethod.GET)
	public @ResponseBody DataWebResponse<Boolean> hasNotifications(HttpServletRequest request, HttpServletResponse rsp, Locale locale) throws MissingArgumentException {
		DataWebResponse<Boolean> result = new DataWebResponse<Boolean>();
		result.setStatus(false);
		
		UserData userData = loginService.getUserDataFromCookie(request);
		Boolean flag = notificationService.hasNotifications(locale, userData.getUserId());
		
		result.setStatus(true);
		result.setData(flag);
		
		return result;
	}
	
	@RequestMapping(value = "/notifications", method = RequestMethod.GET)
	public @ResponseBody ListDataWebResponse<Notification> getNotifications(HttpServletRequest request, HttpServletResponse rsp, Locale locale) throws MissingArgumentException {
		ListDataWebResponse<Notification> result = new ListDataWebResponse<Notification>();
		result.setStatus(false);
		
		UserData userData = loginService.getUserDataFromCookie(request);
		List<Notification> list = notificationService.getNotifications(locale, userData.getUserId());
		
		result.setStatus(true);
		result.setData(list);
		
		return result;
	}
	
	@ExceptionHandler(Exception.class)
	public BaseWebResponse handleException(Exception exception, HttpServletRequest request) {
		CalEventHelper.writeException("Exception", exception, true);
		BaseWebResponse resp = new BaseWebResponse();
		resp.setStatus(false);
		resp.setMessage(exception.getMessage());
		return resp;
	}
}
