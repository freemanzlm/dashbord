package com.ebay.raptor.promotion.promo.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.nio.CharBuffer;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ebay.app.raptor.promocommon.MissingArgumentException;
import com.ebay.app.raptor.promocommon.excel.ExcelReader;
import com.ebay.app.raptor.promocommon.excel.InvalidCellValueException;
import com.ebay.raptor.promotion.excel.UploadListingSheetHandler;
import com.ebay.raptor.promotion.list.service.DealsListingService;
import com.ebay.raptor.promotion.pojo.UserData;
import com.ebay.raptor.promotion.util.CookieUtil;

@Controller
public class DealsUpLoadController {
	
	@Autowired DealsListingService dealsListingService;

	@RequestMapping(value = "promotion/upload", method = RequestMethod.POST)
	public ModelAndView handleUploadRequest(HttpServletRequest request,
            HttpServletResponse response, @RequestPart("file") MultipartFile xmlFile,
            @RequestParam String promoId) throws MissingArgumentException {
		ModelAndView mav = new ModelAndView("deals/listingPreview");
		
		UserData userData = CookieUtil.getUserDataFromCookie(request);
		
		XSSFWorkbook workbook = null;

		try {
			workbook = new XSSFWorkbook(xmlFile.getInputStream());
			ExcelReader.readWorkbook(workbook, 0,
					new UploadListingSheetHandler(dealsListingService,
							promoId, userData.getUserId()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			mav.setViewName("deals/listingPreview"); // TODO - upload failed...
		}
		
		mav.setViewName("deals/listingPreview");
		mav.addObject("formUrl", "submit");
		return mav;
	}
}
