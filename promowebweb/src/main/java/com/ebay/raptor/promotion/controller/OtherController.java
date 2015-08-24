package com.ebay.raptor.promotion.controller;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="other")
public class OtherController {

	@RequestMapping(value = "state", method = RequestMethod.GET)
	public HashMap<String, String> handleStateRequest() {
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
	
    @RequestMapping(value = "cancel", method = RequestMethod.GET)
    public ModelAndView handleCancelRequest() {
		ModelAndView mav = new ModelAndView("other/cancel");
		mav.addObject("state", "cancel");
		return mav;
	}
	
}
