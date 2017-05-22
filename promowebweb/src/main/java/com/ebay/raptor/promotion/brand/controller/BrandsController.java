package com.ebay.raptor.promotion.brand.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebay.app.raptor.promocommon.MissingArgumentException;
import com.ebay.cbt.raptor.promotion.po.BrandPerformance;
import com.ebay.kernel.logger.LogLevel;
import com.ebay.kernel.logger.Logger;
import com.ebay.raptor.promotion.AuthNeed;
import com.ebay.raptor.promotion.Router;
import com.ebay.raptor.promotion.brand.service.BrandService;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.pojo.UserData;
import com.ebay.raptor.promotion.pojo.web.resp.BaseWebResponse;
import com.ebay.raptor.promotion.pojo.web.resp.ListDataWebResponse;
import com.ebay.raptor.promotion.service.LoginService;

@Controller
@RequestMapping(Router.Brand.base)
public class BrandsController {
	private static final Logger logger = Logger.getInstance(BrandsController.class);
	
	@Autowired LoginService loginService;
	@Autowired BrandService brandService;
	
	@AuthNeed
	@GET
	@RequestMapping(Router.Brand.passed)
	@ResponseBody
	public ListDataWebResponse<BrandPerformance> getBrandRegPromotions(HttpServletRequest request) throws MissingArgumentException, PromoException {
		ListDataWebResponse<BrandPerformance> resp = new ListDataWebResponse<BrandPerformance>();
		UserData userData = loginService.getUserDataFromCookie(request);
		
		List<BrandPerformance> brands = brandService.getBrandPerformance(userData.getUserId());
		resp.setData(brands);
		
		/*BrandPerformance p1 = new BrandPerformance();
		p1.setName("hello world");
		p1.setLastAuditDt(new Date());
		p1.setNextAuditDt(new Date());
		p1.setState("1");
		p1.setDefectRateNCompliantAmount(3);
		
		BrandPerformance p2 = new BrandPerformance();
		p2.setName("hello world 2");
		p2.setLastAuditDt(new Date());
		p2.setNextAuditDt(new Date());
		p2.setState("0");
		p2.setDefectRateNCompliantAmount(5);
		
		List<BrandPerformance> list = new ArrayList<BrandPerformance>();
		list.add(p1);
		list.add(p2);
		resp.setData(list);*/
		
		return resp;
	}
	
	@ExceptionHandler(Exception.class)
	public BaseWebResponse handleException(Exception exception, HttpServletRequest request) {
		logger.log(LogLevel.ERROR, exception.getMessage(), exception);
		BaseWebResponse resp = new BaseWebResponse();
		resp.setStatus(false);
		resp.setMessage(exception.getMessage());
		return resp;
	}
}
