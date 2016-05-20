package com.ebay.raptor.promotion.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebay.raptor.kernel.context.RaptorContextFactory;

@Controller
@RequestMapping(value="front/back")
public class BackendController {
	@RequestMapping(value = "IAFToken", method = RequestMethod.GET)
	@ResponseBody
	public String handleRequest() {
		return RaptorContextFactory.getCtx().getAppCtx().getAppConfig().getIAFToken();
	}
}
