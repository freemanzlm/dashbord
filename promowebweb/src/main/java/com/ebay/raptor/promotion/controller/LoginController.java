package com.ebay.raptor.promotion.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ebay.app.raptor.promocommon.CommonLogger;
import com.ebay.raptor.promotion.interceptor.AuthInterceptor;
import com.ebay.raptor.promotion.service.TrackService;

@Controller
public class LoginController {
	private static final CommonLogger _logger = CommonLogger.getInstance(LoginController.class);

	@Autowired TrackService trackService;

	@RequestMapping(value="/redirectToBiz", method=RequestMethod.GET)
	public void directToBizReportRequest (HttpServletRequest req,
			HttpServletResponse rsp,
			@RequestParam("uid") long userId,
			@RequestParam("unm") String userName,
			@RequestParam("admin") Boolean admin,
			@RequestParam("lang") String language) {

	}
}
