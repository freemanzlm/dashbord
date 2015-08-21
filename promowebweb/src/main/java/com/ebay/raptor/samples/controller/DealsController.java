package com.ebay.raptor.samples.controller;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="deals")
public class DealsController {

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
		model.put("expired", "false");
		return model;
	}
	
	@RequestMapping(value = "listing", method = RequestMethod.GET)
	public HashMap<String, String> handleListingRequest() {
		HashMap<String, String> model = new HashMap<String, String>();
		model.put("state", "inquiry");
		model.put("expired", "false");
		return model;
	}
	
	@RequestMapping(value = "applicationConfirm", method = RequestMethod.GET)
	public HashMap<String, String> handleapplicationConfirmRequest() {
		HashMap<String, String> model = new HashMap<String, String>();
		String helloRaptor = "Say hello to Raptor!";
		model.put("greeting", helloRaptor);
		return model;
	}
	
	@RequestMapping(value = "applyFail", method = RequestMethod.GET)
	public HashMap<String, String> handleAuditFailRequest() {
		HashMap<String, String> model = new HashMap<String, String>();
		String helloRaptor = "Say hello to Raptor!";
		model.put("greeting", helloRaptor);
		return model;
	}
	
	@RequestMapping(value = "state", method = RequestMethod.GET)
	public HashMap<String, String> handleStateRequest() {
		HashMap<String, String> model = new HashMap<String, String>();
		model.put("state", "ongoing");
		return model;
	}
	
	@RequestMapping(value = "end", method = RequestMethod.GET)
	public HashMap<String, String> handleEndRequest() {
		HashMap<String, String> model = new HashMap<String, String>();
		String helloRaptor = "Say hello to Raptor!";
		model.put("greeting", helloRaptor);
		return model;
	}
	
	@RequestMapping(value = "submit", method = RequestMethod.POST)
	public ModelAndView handleSubmitRequest() {
		ModelAndView mav = new ModelAndView("deals/applied");
		mav.addObject("formUrl", "upload");
		
		return mav;
	}
	
	@RequestMapping(value = "confirm", method = RequestMethod.POST)
	public ModelAndView handleConfirmRequest() {
		ModelAndView mav = new ModelAndView("deals/applied");
		mav.addObject("formUrl", "upload");
		
		return mav;
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public ModelAndView handleUploadRequest() {
		ModelAndView mav = new ModelAndView("deals/listingPreview");
		mav.addObject("formUrl", "submit");
		
		return mav;
	}
	
}
