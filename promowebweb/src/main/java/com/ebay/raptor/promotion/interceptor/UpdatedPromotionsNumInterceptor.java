package com.ebay.raptor.promotion.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ebay.app.raptor.promocommon.CommonLogger;
import com.ebay.raptor.promotion.pojo.UserData;
import com.ebay.raptor.promotion.pojo.business.Promotion;
import com.ebay.raptor.promotion.promo.service.PromotionService;
import com.ebay.raptor.promotion.promo.service.ViewContext;
import com.ebay.raptor.promotion.util.CookieUtil;

public class UpdatedPromotionsNumInterceptor extends HandlerInterceptorAdapter {
	
	private static final CommonLogger _logger = CommonLogger.getInstance(UpdatedPromotionsNumInterceptor.class);
	
	@Autowired private PromotionService service;

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView model) throws Exception {
		super.postHandle(request, response, handler, model);
		if(null == model){
			return;
		}
		long start = System.currentTimeMillis();
		UserData userDt = CookieUtil.getUserDataFromCookieOverrideLang(request);
		List<Promotion> promos = service.getUpdatedPromotions(userDt.getUserId());
		if(null != promos){
			int num = 0;
			StringBuffer ids = new StringBuffer();
			for(Promotion promo : promos){
				num++;
				ids.append(promo.getPromoId())
					.append(" : ")
					.append(promo.getState())
					.append(", ");
			}
			System.out.println(ids.toString());
			model.addObject(ViewContext.PromoUpdatedNum.getAttr(), num);
			model.addObject(ViewContext.PromoUpdatedDetail.getAttr(), ids.toString());
		}
		_logger.error(String.format("Finish setting the updated promotions number, used %s ms.", (System.currentTimeMillis()-start)));
	}
	
	
}