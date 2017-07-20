package com.ebay.raptor.promotion.list.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebay.app.raptor.promocommon.MissingArgumentException;
import com.ebay.cbt.raptor.promotion.po.Listing;
import com.ebay.kernel.logger.LogLevel;
import com.ebay.kernel.logger.Logger;
import com.ebay.raptor.promotion.Router;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.list.req.ListingWebParam;
import com.ebay.raptor.promotion.list.req.SelectableListing;
import com.ebay.raptor.promotion.list.req.UploadListingForm;
import com.ebay.raptor.promotion.list.service.ListingService;
import com.ebay.raptor.promotion.pojo.ResponseData;
import com.ebay.raptor.promotion.pojo.UserData;
import com.ebay.raptor.promotion.pojo.web.resp.BaseWebResponse;
import com.ebay.raptor.promotion.pojo.web.resp.ListDataWebResponse;
import com.ebay.raptor.promotion.service.LoginService;
import com.ebay.raptor.promotion.util.PojoConvertor;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author lyan2
 */
@Controller
@RequestMapping(Router.Listing.base)
public class ListingDataController extends AbstractListingController {
	private static Logger logger = Logger.getInstance(ListingDataController.class);
	private final ObjectMapper mapper = new ObjectMapper();
	
	@Autowired LoginService loginService;
	@Autowired ListingService listingService;
	
	@GET
	@RequestMapping(Router.Listing.getPromotionListings)
	@ResponseBody
	public ListDataWebResponse<?> getPromotionListings(HttpServletRequest req,
			@ModelAttribute ListingWebParam param) throws MissingArgumentException, JsonParseException, JsonMappingException, PromoException, IOException  {
		
		return getListings(req, param, false);
	}
	
	@GET
	@RequestMapping(Router.Listing.getUploadedListings)
	@ResponseBody
	public ListDataWebResponse<?> getUploadListings(HttpServletRequest req,
			@ModelAttribute ListingWebParam param) throws JsonParseException, JsonMappingException, MissingArgumentException, PromoException, IOException  {
		
		return getListings(req, param, true);
	}
	
	@POST
	@RequestMapping(Router.Listing.confirmListings)
	@ResponseBody
	public ResponseData <String> confirmListings(HttpServletRequest req, @ModelAttribute("listings") UploadListingForm listings) throws MissingArgumentException, PromoException {
		ResponseData <String> responseData = new ResponseData <String>();

		if(null != listings){
			SelectableListing[] listingAry = PojoConvertor.convertToObject(listings.getListings(), SelectableListing[].class);
			UserData userData = loginService.getUserDataFromCookie(req);
			boolean result = listingService.confirmListings(listingAry, listings.getPromoId(), userData.getUserId());
			responseData.setStatus(result);
		}
		
		return responseData;
	}
	

	/**
	 * After user confirm his listing, submit all of his listings to SalesForce.
	 * @param req
	 * @param listings
	 * @return
	 * @throws PromoException 
	 */
	@POST
	@RequestMapping(Router.Listing.submitListings)
	public @ResponseBody ResponseData <String> submitDealsListings(HttpServletRequest req, HttpServletResponse resp) throws MissingArgumentException, PromoException{
		ResponseData <String> responseData = new ResponseData <String>();
		String promoId = req.getParameter("promoId");

		UserData userData = loginService.getUserDataFromCookie(req);
		boolean result = listingService.submitListings(promoId, userData.getUserId());
		responseData.setStatus(result);
		return responseData;
	}
	
	/**
	 * In phase1, there are several kinds of getListings() by state. We keep this method for future usage.
	 * 
	 * @param req
	 * @param param
	 * @return
	 * @throws MissingArgumentException 
	 * @throws IOException 
	 * @throws PromoException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	protected ListDataWebResponse<?> getListings (HttpServletRequest req,
			ListingWebParam param, boolean isUploaded) throws MissingArgumentException, JsonParseException, JsonMappingException, PromoException, IOException {
		UserData userData = loginService.getUserDataFromCookie(req);

		ListDataWebResponse<?> resp = getListings(param.getPromoId(), userData.getUserId(), isUploaded);
		
		return resp;
	}
	
	/**
	 * Get listings of specified user and promotion. If "isUploaded" is true, it will only get user uploaded listings by excel.
	 * @param promoId
	 * @param userId
	 * @param isUploaded
	 * @return
	 * @throws PromoException 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	protected ListDataWebResponse<?> getListings (String promoId, Long userId, boolean isUploaded) throws PromoException, JsonParseException, JsonMappingException, IOException {
		ListDataWebResponse<Map<String, Object>> resp = new ListDataWebResponse<Map<String, Object>>();
		List<Listing> listings = listingService.getListingsByPromotionId(promoId, userId, isUploaded);
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		
		for (Listing listing : listings) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("skuId", listing.getSkuId());
			map.put("state", listing.getState());
			map.put("currency", listing.getCurrency());
			map.put("lock", listing.getLocked());
			
			if (listing.getNominationValues() != null) {
				@SuppressWarnings("unchecked")
				Map<String, Object> fields = mapper.readValue(listing.getNominationValues(), HashMap.class);
				map.putAll(fields);
			}
			
			List<Map<String, Object>> attachments = listing.getAttachments(); 
			if (attachments != null) {
				for (Map<String, Object> attachment : attachments) {
					String key = attachment.get("key").toString();
					Object filename = attachment.get("filename");
					if (key != null && filename != null) {
						String attachmentUrl = "/attachment/promoId/"+promoId+"/userId/"+userId+"/skuId/"+listing.getSkuId() + "/key/" + key;
						map.put(key, attachmentUrl);
					}
				}
			}
			
			data.add(map);
		}
		
		resp.setData(data);
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
