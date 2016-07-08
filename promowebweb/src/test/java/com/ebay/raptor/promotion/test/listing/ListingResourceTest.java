package com.ebay.raptor.promotion.test.listing;

import java.util.Map;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.ebayopensource.ginger.client.GingerClient;
import org.ebayopensource.ginger.client.GingerClientResponse;
import org.junit.Before;
import org.junit.Test;

import com.ebay.raptor.promotion.excel.service.ExcelService;
import com.ebay.raptor.promotion.list.service.ListingService;
import com.ebay.raptor.promotion.pojo.service.resp.ListDataServiceResponse;
import com.ebay.raptor.promotion.service.PromoClient;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Unit test is based on production.
 * @author lyan2
 *
 */
public class ListingResourceTest {
	private	GingerClient c = PromoClient.getClient();
	private String authorization = "Bearer v^1.1#i^1#f^0#p^1#d^2017-11-21T23:45:08.220Z#r^0#I^2#t^H4sIAAAAAAAAAOVVT2gcVRjf2d2krGlMEU2kaNmOtdbozLyZ2Z3ZHbqDm9TgQtJUN61a1PL2zdtk7O7MMO8tm6U9rCvUCoL1IBT/QIq5eCgqqEGwiIUWPVmCh4LgoQeJHrSHSg7+wTeT3WSzxRQkN/eyvO/93vf95vf93vtAsz8xeuapM6uD3I7oQhM0oxwnD4BEf99jd8eiu/sioAvALTT3NeOt2MpBAqsVz3gGE891CE7OVysOMcJgjq/5juFCYhPDgVVMDIqMYn5q0lBEYHi+S13kVvhk4VCOT+kYoBTSEFCUDCinWdTp5JxxczzWLKCpSIUWSluwnGL7hNRwwSEUOjTHK0DWBJAWFHUGKEYqbYCMqCjgOJ88hn1iuw6DiIA3Q7pGeNbv4ro1VUgI9ilLwpuF/MR48aDUlcVsK1CkkNbI5tW4a+HkMVip4a0LkBBtFGsIYUJ4yVyrsDmpke/Q+A/EQ5EzUC9biqaX03oJQwVui4gTrl+FdGseQcS2hHIINbBDbdr4dy2ZDqWXMaLt1WF2uHAoGfw9XYMVu2xjP8c/OZZ/Pn/kCG8GdXEJNoQq9E9i6lUgwgJivqlVsW9bBoI4m9VVJOhZpAop1coKWVTWBVXJ6pqWKcl6KdsmsVapLX4Pi3HXsexASpI87NIxzL4F9yqmdinGQNPOtJ8v04Atw+mCLAuKPKOo3cpKnd7W6JwTdBtXmTzJcHnnvnSMsmGN7bJKoA2UM7KWSpU0xcry8Vb0z22wi9num7SNbfvfe4dS3y7VKF73T+9GcsK3sWNVGgHLHF9Erof55NqChIveE+Gj1TbPPMnxc5R6hiTV63WxroquPyspAMjSc1OTRTSHq+wp6WDtO4MFO7QQYhwY3qANj/GYZy5lxZ1Z3gzOE5YAerYYtEdEblVyIbsTUshWQkzAJ6DnVWwEA107t2ATe7M3elugS6/brpG0ea6ZkfAntzgdtDiVjUaggAPyfrCvP3Y0Htv5ILEpFm1YFhERiT3rsHfbx+JJ3PCg7Uf7uSmpfj3dNUwXXgT3r4/TREwe6Jqt4IGNnT55aGRQ1kBaUYGSSoPMcfDQxm5cHo7fu3zfuVOL/M0V6dKtVh/K3XPh7Mo0GFwHcVxfJP5K0/jmp/O7J6+nC2989PqBP44Of/f70t9XVriB82/9Yi6+Mzy6A35BvvokXtrzyK13Pxw6vbc8tnRj9fEro5+f+PHZvdnIp7/d+PjrHxIvXOWvjYwMXTsx+eXD7/+MP1va9fbZl76/q7a86J67vPxmwt752l+/Dt98b89l7tWLETD1Qf3qxVOnH/320uqagP8AKwvLCGUIAAA=";
	private ObjectMapper mapper = new ObjectMapper();
	
	ListingService listingService = new ListingService();
	ExcelService excelService = new ExcelService();
	
	@Before
	public void before() {
		mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void getSKUListingsByPromotionId() throws JsonProcessingException {
		WebTarget target = c.target("/promoser/listings/getSKUListingsByPromotionId/promoId/701O0000000WAUcIAO/uid/1413178537");
		Invocation.Builder builder = target.request(MediaType.APPLICATION_JSON_TYPE).header("Authorization", authorization);
		
		ListDataServiceResponse<Map<String, Object>> data;
		
		System.out.println("URL: " + target.getUri());
		
		GingerClientResponse response = (GingerClientResponse)builder.get();
		if (response.getStatus() == Status.OK.getStatusCode()) {
			
			data = (ListDataServiceResponse<Map<String, Object>>)response.getEntity(ListDataServiceResponse.class);
			
			System.out.println(mapper.writeValueAsString(data));
		}
	}
	
	@Test
	public void getSKUsByPromotionId() throws JsonProcessingException {
		WebTarget target = c.target("/promoser/listings/getSKUsByPromotionId/promoId/701O0000000WAUcIAO/uid/1413178537");
		Invocation.Builder builder = target.request(MediaType.APPLICATION_JSON_TYPE).header("Authorization", authorization);
		
		ListDataServiceResponse<Map<String, Object>> data;
		
		System.out.println("URL: " + target.getUri());
		
		GingerClientResponse response = (GingerClientResponse)builder.get();
		if (response.getStatus() == Status.OK.getStatusCode()) {
			data = (ListDataServiceResponse<Map<String, Object>>)response.getEntity(ListDataServiceResponse.class);
			
			System.out.println(mapper.writeValueAsString(data));
		}
	}
	

}
