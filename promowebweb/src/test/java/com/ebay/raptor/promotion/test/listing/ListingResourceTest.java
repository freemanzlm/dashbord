package com.ebay.raptor.promotion.test.listing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ebayopensource.ginger.client.GingerClient;
import org.ebayopensource.ginger.client.GingerClientResponse;
import org.junit.Before;

import com.ebay.cbt.raptor.promotion.po.Listing;
import com.ebay.raptor.promotion.enums.ListingState;
import com.ebay.raptor.promotion.excel.ColumnConfiguration;
import com.ebay.raptor.promotion.excel.ISheetReader;
import com.ebay.raptor.promotion.excel.SheetReader;
import com.ebay.raptor.promotion.excel.service.ExcelService;
import com.ebay.raptor.promotion.excel.util.ExcelUtil;
import com.ebay.raptor.promotion.list.service.ListingService;
import com.ebay.raptor.promotion.locale.LocaleUtil;
import com.ebay.raptor.promotion.pojo.service.req.UploadListingRequest;
import com.ebay.raptor.promotion.pojo.service.resp.ListDataServiceResponse;
import com.ebay.raptor.promotion.service.PromoClient;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

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
//	@Test
	public void getSKUListingsByPromotionId() throws JsonProcessingException {
		WebTarget target = c.target("/promoser/listings/getSKUListingsByPromotionId/promoId/701N00000003aqSIAQ/uid/1413178537");
		Invocation.Builder builder = target.request(MediaType.APPLICATION_JSON_TYPE).header("Authorization", authorization);
		
		System.out.println("URL: " + target.getUri());
		
		GingerClientResponse response = (GingerClientResponse)builder.get();
		if (response.getStatus() == Status.OK.getStatusCode()) {
			
			ListDataServiceResponse<Listing> data;
//			data = (ListDataServiceResponse<Map<String, Object>>)response.getEntity(ListDataServiceResponse.class);
			data = (ListDataServiceResponse<Listing>)response.getEntity(ListDataServiceResponse.class);
			
			System.out.println(mapper.writeValueAsString(data));
		}
	}
	
//	@Test
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
	
//	@Test
	public void uploadListings() throws IOException {
		WebTarget target = c.target("/promoser/listings/uploadListings");
		Invocation.Builder builder = target.request(MediaType.APPLICATION_JSON_TYPE).header("Authorization", authorization);
		
		ListDataServiceResponse<Map<String, Object>> data;
		
		System.out.println("URL: " + target.getUri());
		
		File uploadedFile = new File(ListingResourceTest.class.getResource("Listing_Template.xlsx").getFile());
		FileInputStream fis = new FileInputStream(uploadedFile);
		Workbook workbook = new XSSFWorkbook(fis);			
		Sheet sheet = workbook.getSheetAt(0);
		
		Set<ConstraintViolation<Object>> violations = new HashSet<ConstraintViolation<Object>>();
		ISheetReader reader = new SheetReader();
		String fieldsDefinitions =	"[{\"sample\":null,\"required\":false,\"labelName\":\"SKU_deal\",\"isUnique\":false,\"input\":true,\"fieldtype\":{\"typeName\":\"STRING\",\"picklistEntry\":\"\",\"percision\":0,\"length\":32768,\"digits\":0},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"SKU编号\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"SKU编号\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"產SKU编号\",\"isDefault\":true}],\"display\":false,\"currencyType\":\"\",\"attachmentType\":null,\"api_Name\":\"skuId\"},{\"sample\":\"2015-12-24 24:38:56\",\"required\":false,\"labelName\":\"Stock ready Time\",\"isUnique\":false,\"input\":false,\"fieldtype\":{\"typeName\":\"STRING\",\"picklistEntry\":\"\",\"percision\":0,\"length\":100,\"digits\":0},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"SKU名称\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"SKU名称\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"SKU名称\",\"isDefault\":true}],\"display\":true,\"currencyType\":\"\",\"attachmentType\":null,\"api_Name\":\"skuName\"},{\"sample\":null,\"required\":false,\"labelName\":\"Item ID\",\"isUnique\":false,\"input\":false,\"fieldtype\":{\"typeName\":\"STRING\",\"picklistEntry\":\"\",\"percision\":0,\"length\":200,\"digits\":0},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"刊登编号\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"刊登編號\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"刊登編號\",\"isDefault\":true}],\"display\":true,\"currencyType\":\"\",\"attachmentType\":null,\"api_Name\":\"itemId\"},{\"sample\":null,\"required\":false,\"labelName\":\"Qty Available\",\"isUnique\":false,\"input\":false,\"fieldtype\":{\"typeName\":\"DOUBLE\",\"picklistEntry\":\"\",\"percision\":10,\"length\":0,\"digits\":0},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"当前价\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"当前价\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"總量\",\"isDefault\":true}],\"display\":true,\"currencyType\":\"\",\"attachmentType\":null,\"api_Name\":\"currPrice\"},{\"sample\":null,\"required\":false,\"labelName\":\"Site\",\"isUnique\":false,\"input\":false,\"fieldtype\":{\"typeName\":\"DOUBLE\",\"picklistEntry\":\"\",\"percision\":0,\"length\":100,\"digits\":0},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"活动价\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"活动价\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"活動網站\",\"isDefault\":true}],\"display\":true,\"currencyType\":\"\",\"attachmentType\":null,\"api_Name\":\"proposePrice\"},{\"sample\":null,\"required\":false,\"labelName\":\"Listing Local Currency\",\"isUnique\":false,\"input\":false,\"fieldtype\":{\"typeName\":\"STRING\",\"picklistEntry\":\"\",\"percision\":0,\"length\":10,\"digits\":0},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"货币单位\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"貨幣單位\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"貨幣單位\",\"isDefault\":true}],\"display\":true,\"currencyType\":\"\",\"attachmentType\":null,\"api_Name\":\"currency\"},{\"sample\":null,\"required\":false,\"labelName\":\"Propose Price_Local Currency\",\"isUnique\":false,\"input\":false,\"fieldtype\":{\"typeName\":\"DOUBLE\",\"picklistEntry\":\"\",\"percision\":10,\"length\":0,\"digits\":2},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"现价\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"現價\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"現價\",\"isDefault\":true}],\"display\":true,\"currencyType\":\"Local Currency\",\"attachmentType\":null,\"api_Name\":\"proposePrice\"},{\"sample\":null,\"required\":false,\"labelName\":\"FVF take rate\",\"isUnique\":false,\"input\":false,\"fieldtype\":{\"typeName\":\"INTEGER\",\"picklistEntry\":\"\",\"percision\":10,\"length\":0,\"digits\":0},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"库存量\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"库存量\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"成交費\",\"isDefault\":true}],\"display\":true,\"currencyType\":\"\",\"attachmentType\":null,\"api_Name\":\"stockNum\"},{\"sample\":null,\"required\":false,\"labelName\":\"Final Subsidy %\",\"isUnique\":false,\"input\":false,\"fieldtype\":{\"typeName\":\"DATE\",\"picklistEntry\":\"\",\"percision\":17,\"length\":0,\"digits\":2},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"备货日期\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"最終補貼金額\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"最終補貼金額\",\"isDefault\":true}],\"display\":true,\"currencyType\":\"\",\"attachmentType\":null,\"api_Name\":\"stockReadyDate\"}]";
		JsonNode tree = mapper.readTree(fieldsDefinitions);
		List<Map<String, Object>> list = null;
		
		if (tree.isArray()) {
			List<ColumnConfiguration> columnConfigs = ExcelUtil.getColumnConfigurations((ArrayNode)tree, LocaleUtil.getCurrentLocale());
			excelService.adjustColumnConfigurations(columnConfigs, LocaleUtil.getCurrentLocale(), "701N00000003aqSIAQ");
			list = reader.readSheet(sheet, columnConfigs, 3, violations);
		}
		
		List<Listing> listings = new ArrayList<Listing>();
		
		if (list != null) {
			for (Map<String, Object> row : list) {
				Listing listing = new Listing();
				listing.setSkuId(row.remove("skuId").toString());
				listing.setCurrency(row.remove("currency").toString());
				listing.setState(ListingState.Uploaded.getName());
				try {
					listing.setNominationValues(mapper.writeValueAsString(row));
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
				listings.add(listing);
			}
			
		}
		
		UploadListingRequest<Listing> req = new UploadListingRequest<Listing>();
		req.setListings(listings);
		req.setPromoId("701N00000003aqSIAQ");
		req.setUid(1413178537l);
		
		GingerClientResponse response = (GingerClientResponse)builder.post(Entity.json(req));
		if (response.getStatus() == Status.OK.getStatusCode()) {
			data = (ListDataServiceResponse<Map<String, Object>>)response.getEntity(ListDataServiceResponse.class);
			
			System.out.println(mapper.writeValueAsString(data));
		}
		
		workbook.close();
	}
	
	public void uploadFile() throws JsonProcessingException {
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
