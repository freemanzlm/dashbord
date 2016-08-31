package com.ebay.raptor.promotion.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ebay.app.raptor.promocommon.CommonLogger;
import com.ebay.app.raptor.promocommon.MissingArgumentException;
import com.ebay.app.raptor.promocommon.export.ColumnFormat;
import com.ebay.app.raptor.promocommon.export.HeaderConfiguration;
import com.ebay.app.raptor.promocommon.export.write.ExcelSheetWriter;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.list.service.DealsListingService;
import com.ebay.raptor.promotion.pojo.RequestParameter;
import com.ebay.raptor.promotion.pojo.UserData;
import com.ebay.raptor.promotion.pojo.business.DealsListing;
import com.ebay.raptor.promotion.service.LoginService;

@Controller
public class DownloadController {
	private static CommonLogger logger =
            CommonLogger.getInstance(DownloadController.class);
	
	@Autowired LoginService loginService;
	@Autowired DealsListingService dealsListingService;
	@Autowired ResourceBundleMessageSource msgResource;

    @RequestMapping(value = "/downloadTemplet", method = RequestMethod.GET)
    public void handleTempletRequest(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute RequestParameter param) throws MissingArgumentException {
        String prefixFileName = "Deals_listing_templet";

        try {
        	response.setContentType("application/x-msdownload;");
        	response.setHeader("Content-disposition", "attachment; filename=" + prefixFileName + ".xlsx");
        	
        	UserData userData = loginService.getUserDataFromCookie(request);
        	
        	List<DealsListing> skuListings = dealsListingService.getSkuListingsByPromotionId(param.getPromoId(), userData.getUserId(), null);

        	List<HeaderConfiguration> preCfgs = new ArrayList<HeaderConfiguration>();
        	preCfgs.add(new HeaderConfiguration(20, "itemId", resource("itemId") , ColumnFormat.String));
        	preCfgs.add(new HeaderConfiguration(30, "itemTitle", resource("itemTitle") , ColumnFormat.String));
        	preCfgs.add(new HeaderConfiguration(40, "currPrice", resource("currPrice") , ColumnFormat.FltNum));
        	preCfgs.add(new HeaderConfiguration(50, "targetPrice", resource("targetPrice") , ColumnFormat.FltNum));
        	preCfgs.add(new HeaderConfiguration(60, "stockedNum", resource("stockedNum") , ColumnFormat.FltNum));

        	XSSFWorkbook workBook = new XSSFWorkbook();
        	ExcelSheetWriter<DealsListing> writer = new ExcelSheetWriter<DealsListing>(DealsListing.class, workBook, prefixFileName);
            writer.setPreConfiguration(preCfgs);
            writer.resetHeaders();
            writer.build(skuListings);

            workBook.write(response.getOutputStream());
        } catch (IOException | PromoException e) {
        	logger.warn("Unable to download data.", e);
        }
    }
    
    private String resource(String key){
		return msgResource.getMessage(key, null, Locale.SIMPLIFIED_CHINESE);
	}
}
