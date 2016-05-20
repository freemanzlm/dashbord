package com.ebay.raptor.promotion.controller.front;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Only for front-end usage.
 * @author lyan2
 *
 */
@Controller
@RequestMapping(value="front/other")
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
	
    @RequestMapping(value = "canceled", method = RequestMethod.GET)
    public ModelAndView handleCancelRequest() {
		ModelAndView mav = new ModelAndView("other/canceled");
		mav.addObject("state", "canceled");
		return mav;
	}
    
    @RequestMapping(value = "terms", method = RequestMethod.GET)
    public ModelAndView handleTermsRequest() {
        ModelAndView mav = new ModelAndView("terms/other");
        return mav;
    }
	
}
