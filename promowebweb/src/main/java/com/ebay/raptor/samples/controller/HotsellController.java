package com.ebay.raptor.samples.controller;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="hotsell")
public class HotsellController {

	@RequestMapping(value = "applicable", method = RequestMethod.GET)
	public HashMap<String, String> handleRequest() {
		HashMap<String, String> model = new HashMap<String, String>();
		String helloRaptor = "Say hello to Raptor!";
		model.put("greeting", helloRaptor);
		return model;
	}

	@RequestMapping(value = "applied", method = RequestMethod.GET)
	public HashMap<String, String> handleAppliedRequest() {
		HashMap<String, String> model = new HashMap<String, String>();
		String helloRaptor = "Say hello to Raptor!";
		model.put("greeting", helloRaptor);
		return model;
	}
	
	@RequestMapping(value = "ongoing", method = RequestMethod.GET)
	public HashMap<String, String> handleOnGoingRequest() {
		HashMap<String, String> model = new HashMap<String, String>();
		String helloRaptor = "Say hello to Raptor!";
		model.put("greeting", helloRaptor);
		return model;
	}
	
	@RequestMapping(value = "auditFail", method = RequestMethod.GET)
	public HashMap<String, String> handleAuditFailRequest() {
		HashMap<String, String> model = new HashMap<String, String>();
		String helloRaptor = "Say hello to Raptor!";
		model.put("greeting", helloRaptor);
		return model;
	}
	
	@RequestMapping(value = "rewardConfirming", method = RequestMethod.GET)
	public HashMap<String, String> handleRewardConfirmingRequest() {
		HashMap<String, String> model = new HashMap<String, String>();
		String helloRaptor = "Say hello to Raptor!";
		model.put("greeting", helloRaptor);
		return model;
	}
	
	@RequestMapping(value = "rewardClaimable", method = RequestMethod.GET)
	public HashMap<String, String> handleRewardClaimableRequest() {
		HashMap<String, String> model = new HashMap<String, String>();
		String helloRaptor = "Say hello to Raptor!";
		model.put("greeting", helloRaptor);
		return model;
	}
	
	@RequestMapping(value = "end", method = RequestMethod.GET)
	public HashMap<String, String> handleEndRequest() {
		HashMap<String, String> model = new HashMap<String, String>();
		String helloRaptor = "Say hello to Raptor!";
		model.put("greeting", helloRaptor);
		return model;
	}
	
}
