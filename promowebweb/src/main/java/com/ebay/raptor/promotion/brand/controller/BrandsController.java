package com.ebay.raptor.promotion.brand.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebay.app.raptor.promocommon.MissingArgumentException;
import com.ebay.cbt.common.constant.pm.PMPromoTabType;
import com.ebay.raptor.promotion.AuthNeed;
import com.ebay.raptor.promotion.brand.service.BrandService;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.pojo.BrandPerformance;
import com.ebay.raptor.promotion.pojo.UserData;
import com.ebay.raptor.promotion.pojo.web.resp.ListDataWebResponse;
import com.ebay.raptor.promotion.service.LoginService;

@Controller
@RequestMapping("brands")
public class BrandsController {
	@Autowired LoginService loginService;
	@Autowired BrandService brandService;
	@Autowired 
	
	@AuthNeed
	@GET
	@RequestMapping("/passed")
	@ResponseBody
	public ListDataWebResponse<BrandPerformance> getBrandRegPromotions(HttpServletRequest request) throws MissingArgumentException {
		ListDataWebResponse<BrandPerformance> resp = new ListDataWebResponse<BrandPerformance>();
		UserData userData = loginService.getUserDataFromCookie(request);
//			resp.setData(service.getEndPromotions(userData.getUserId()));
		
		BrandPerformance p1 = new BrandPerformance();
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
		resp.setData(list);
		return resp;
	}
	
	
	@AuthNeed
	@GET
	@RequestMapping("/brandPromo")
	@ResponseBody
	public void brandPromo(HttpServletRequest request) throws MissingArgumentException {
		UserData userData = loginService.getUserDataFromCookie(request);
		try {
			List<BrandPerformance> brands = brandService.getBrandPerformance(userData.getUserId());
		} catch (PromoException e) {
			e.printStackTrace();
		}
		
	}
}
