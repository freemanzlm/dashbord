package com.ebay.raptor.promotion.service;

import java.util.Locale;

import org.springframework.stereotype.Service;

import com.ebay.raptor.promotion.pojo.Notification;

@Service
public class ConfigurationService {
	
	// @TODO this service
	public Notification getPromotionNotification(Locale locale) {
		Notification n = new Notification();
		n.setTitle("Title");
		n.setPriority(1);
		n.setContent("Notificaiton Content");
		n.setId("promotion");
		
		return n;
	}
	
	public static void main(String[] args) {
		
	}
}
