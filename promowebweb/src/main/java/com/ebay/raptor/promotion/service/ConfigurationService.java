package com.ebay.raptor.promotion.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebay.kernel.logger.LogLevel;
import com.ebay.kernel.logger.Logger;
import com.ebay.raptor.promotion.pojo.Notification;
import com.ebay.raptor.promotion.sd.service.SDDataService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ConfigurationService {
	private static Logger logger = Logger.getInstance(ConfigurationService.class);
	@Autowired SDDataService sdDataService;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	// @TODO this service
	public List<Notification> getPromotionNotifications(Locale locale, Long userId) {
		List<Notification> list = new ArrayList<Notification>();
		
		Notification n = new Notification();
		n.setTitle("Title");
		n.setPriority(1);
		n.setContent("Notificaiton Content");
		n.setId("promotion");
		
		list.add(n);
		
		try {
			Notification sdN = getSDNotification(locale, 22222222L);
			if (sdN != null) {
				list.add(sdN);
			}
		} catch (HttpException e) {
			logger.log(LogLevel.ERROR, e.getMessage(), e);
		}
		
		return list;
	}
	
	public Notification getSDNotification(Locale locale, Long userId) throws HttpException {
		Notification n = null;
		ObjectMapper mapper = new ObjectMapper();
		String resultJson = sdDataService.getNotification(userId);
		
		try {
			JsonNode json = mapper.readTree(resultJson);
			if (json.get("isSuccess").asBoolean() && !json.get("data").isNull()) {
				String notification = json.get("data").asText();
				String[] notifications = notification.split("boundarytraditional");
				
				if (notifications != null && notifications.length > 1) {
					n = new Notification();
					if (locale == Locale.TRADITIONAL_CHINESE) {
						notification = notifications[1];
					} else {
						notification = notifications[0];
					}
					
					String[] contents = notification.split("boundary");
					String title = contents[0];
					String content = contents[1];

					n.setTitle(title.replaceAll("contentBoundary", ""));
					n.setContent(content);
					n.setNeedsFeedback(true);
					n.setFeedbackURL("/promotion/setSDNotifiStatus");
					n.setPriority(0);
					n.setId("dashboard");
				}
			}
		} catch (IOException e) {
			logger.log(LogLevel.ERROR, e.getMessage(), e);
		}
		
		return n;
	}
	
	public static void main(String[] args) {
		
	}
}
