package com.ebay.raptor.samples.controller;

import java.util.HashMap;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ebay.raptor.kernel.context.IRaptorContext;

@Controller
public class SampleRaptorController {
	
	@Inject IRaptorContext raptorCtx;
	
 	@RequestMapping(value="index", method=RequestMethod.GET)
	public HashMap<String, String> handleRequest() {
		HashMap<String, String> model = new HashMap<String, String>();
		String helloRaptor = "Say hello to Raptor!" ;
		model.put("greeting", helloRaptor);
		return model;
	}  	
}
