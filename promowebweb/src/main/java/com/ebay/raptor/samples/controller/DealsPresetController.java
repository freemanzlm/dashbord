package com.ebay.raptor.samples.controller;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="dealspreset")
public class DealsPresetController {

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
	
	@RequestMapping(value = "state", method = RequestMethod.GET)
	public HashMap<String, String> handleOnGoingRequest() {
		HashMap<String, String> model = new HashMap<String, String>();
		model.put("state", "ongoing");
		return model;
	}
	
	@RequestMapping(value = "applyFail", method = RequestMethod.GET)
	public HashMap<String, String> handleAuditFailRequest() {
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
	
	@RequestMapping(value = "preview", method = RequestMethod.POST)
	public ModelAndView handlePreviewRequest() {
		ModelAndView mav = new ModelAndView("hotsell/listingPreview");
		return mav;
	}
	
	@RequestMapping(value = "submit", method = RequestMethod.POST)
	public ModelAndView handleSubmitRequest() {
		ModelAndView mav = new ModelAndView("hotsell/applied");
		mav.addObject("formUrl", "preview");
		return mav;
	}
	
}
