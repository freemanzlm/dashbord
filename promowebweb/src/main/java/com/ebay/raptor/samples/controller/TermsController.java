package com.ebay.raptor.samples.controller;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TermsController {
	@RequestMapping(value = "applicable", method = RequestMethod.GET)
	public HashMap<String, String> handleRequest() {
		HashMap<String, String> model = new HashMap<String, String>();
		String helloRaptor = "Say hello to Raptor!";
		model.put("greeting", helloRaptor);
		return model;
	}
}
