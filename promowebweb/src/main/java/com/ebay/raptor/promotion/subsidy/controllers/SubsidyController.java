package com.ebay.raptor.promotion.subsidy.controllers;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ebay.app.raptor.promocommon.MissingArgumentException;
import com.ebay.cbt.common.constant.pm.PMSubsidyStatus;
import com.ebay.cbt.raptor.promotion.enumcode.subsidyStatusCode;
import com.ebay.cbt.raptor.promotion.po.Subsidy;
import com.ebay.cbt.raptor.promotion.po.SubsidyAttachment;
import com.ebay.cbt.raptor.promotion.po.SubsidyCustomField;
import com.ebay.cbt.raptor.promotion.po.SubsidyLegalTerm;
import com.ebay.cbt.raptor.promotion.po.SubsidySubmission;
import com.ebay.cbt.raptor.promotion.po.WLTAccount;
import com.ebay.kernel.calwrapper.CalEventHelper;
import com.ebay.kernel.logger.LogLevel;
import com.ebay.kernel.logger.Logger;
import com.ebay.raptor.promotion.excep.AttachmentUploadException;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.locale.LocaleUtil;
import com.ebay.raptor.promotion.pojo.ResponseData;
import com.ebay.raptor.promotion.pojo.UserData;
import com.ebay.raptor.promotion.pojo.business.Promotion;
import com.ebay.raptor.promotion.promo.service.PromotionService;
import com.ebay.raptor.promotion.promo.service.PromotionViewService;
import com.ebay.raptor.promotion.promo.service.ViewContext;
import com.ebay.raptor.promotion.promo.service.ViewResource;
import com.ebay.raptor.promotion.service.CSApiService;
import com.ebay.raptor.promotion.service.LoginService;
import com.ebay.raptor.promotion.subsidy.service.SubsidyService;
import com.ebay.raptor.promotion.util.JsonUtils;
import com.ebay.raptor.promotion.util.MyXMLWorkerHelper;
import com.ebay.raptor.promotion.util.PojoConvertor;
import com.ebay.raptor.promotion.validation.AttachmentFileValidator;
import com.ebay.res.core.handler.out.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.ElementList;


/**
 * 
 * @author lyan2 2017-3-17
 */
@Controller
@RequestMapping("subsidy")
public class SubsidyController {
	private static final Logger logger = Logger.getInstance(SubsidyController.class);

	@Autowired LoginService loginService;
	@Autowired PromotionService promoService;
	@Autowired PromotionViewService view;
	@Autowired SubsidyService subsidyService;
	@Autowired CSApiService csApiService;

	@RequestMapping(value = "/acknowledgment", method = RequestMethod.GET)
	public ModelAndView handleRequest(@RequestParam("promoId") String promoId, HttpServletRequest request,
			HttpServletResponse response) throws MissingArgumentException, IOException {
		ModelAndView model = new ModelAndView();
		UserData userData = loginService.getUserDataFromCookie(request);
		Long userID = userData.getUserId();
		Date now = new Date();
		Promotion promo = null;
		SubsidyLegalTerm term = null;
		WLTAccount wltAccount = null;
		String wltBindURL = null;
		String wltBoundBackURL = request.getRequestURL() + "?isWltFirstBound=true";

		try {
			promo = promoService.getPromotionById(promoId, userID, userData.getAdmin());

			if (promo != null) {
				Subsidy subsidy = subsidyService.getSubsidy(promoId, userID);
				String status = subsidy.getStatus();
				term = subsidyService.getSubsidyLegalTerm(promo.getRewardType(), promo.getRegion());
				
				if(status.equals(PMSubsidyStatus.PM_UNKNOWN_STATUS.getAVStatus())||status.equals(PMSubsidyStatus.REWARD_VISITED.getAVStatus())){
					SubsidySubmission subsidySubmission = subsidyService.getSubsidySubmission(promoId,userID);
					if (subsidySubmission != null) {
						term = subsidyService.convertSubmissionToLegalTerm(term, subsidySubmission);
					}
					
					model.addObject("hasSubmitFields", subsidySubmission != null);
				}
				
				ArrayList<SubsidyCustomField>[] fields = subsidyService.splitCustomFields(term);
				view.calcualteCurentStep(promo);
				view.appendPromoEndCheck(model.getModel(), promo, now);
				view.appendPromoAwardEndCheck(model.getModel(), promo, now);
				
				if (wltAccount == null) {
					model.addObject("wltBindURL", wltBindURL);
				}

				model.addObject("subsidyTerm", term);
				model.addObject("wltAccount", wltAccount);
				model.addObject("nonuploadFields", fields[0]);
				model.addObject("uploadFields", fields[1]);

				model.addObject(ViewContext.Promotion.getAttr(), promo);
				model.addObject(ViewContext.IsAdmin.getAttr(), userData.getAdmin());
				model.setViewName("subsidy_acknowledgment");
			} else {
				model.setViewName(ViewResource.UNKNOW_CAMPAIGN.getPath());
			}
		} catch (PromoException e) {
			String message = "Failed to get promotion: promoId=" + promoId + ",userId:" + userData.getUserId();
			CalEventHelper.writeException("SubsidyError", e, message);
			logger.log(LogLevel.ERROR, message, e);
		}

		if (promo == null) {
			model.setViewName(ViewResource.ERROR.getPath());
		}

		return model;
	}

	@RequestMapping(value = "/acknowledgment", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData<String> saveSellerCustomFields(@RequestParam("promoId") String promoId,
			HttpServletRequest request, HttpServletResponse response) throws MissingArgumentException, IOException,
			PromoException {
		UserData userData = loginService.getUserDataFromCookie(request);
		Long userID = userData.getUserId();
		Promotion promo = null;
		SubsidyLegalTerm term = null;
		ResponseData<String> responseData = new ResponseData<String>();
		HashMap<String, String> map = new HashMap<String, String>();

		promo = promoService.getPromotionById(promoId, userData.getUserId(), userData.getAdmin());
		term = subsidyService.getSubsidyLegalTerm(promo.getRewardType(), promo.getRegion());
		List<SubsidyCustomField> fields = term.getSubsidyCustomFields();
		for (SubsidyCustomField field : fields) {
			if(!field.isUpload){
				map.put(field.getKey(), request.getParameter(field.getKey()));
			}
		}
		
		String ret = JsonUtils.objectToJsonString(map);
		SubsidySubmission subsidySubmission = subsidyService.getSubsidySubmission(promoId,userID);
		if(null==subsidySubmission){
			subsidySubmission = new SubsidySubmission();
			subsidySubmission.setOracleId(userID);
			subsidySubmission.setSfId(promoId);
		}
		subsidySubmission.setContent(ret);
		
		boolean flag = subsidyService.updateSubsidySubmission(subsidySubmission);

		//generate pdf START
		Document document = null;
		BaseFont bf = null;
		Font fontChinese = null;
		try {
			bf = BaseFont.createFont("D:\\simsun.ttf", BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
			/** create the right font for CHINESE **/
			fontChinese = new Font(bf, 10);
			document = new Document(PageSize.A4);
			String out = "d:\\HAHAHA.PDF";
			File file = new File(out);
			OutputStream outputStream = new FileOutputStream(file);
			/** get the html content from javabean and convert to string **/
			PdfWriter pdfWriter = PdfWriter.getInstance(document,outputStream);
			document.open();
			
			/** add the head of the PDF **/
			Paragraph head = new Paragraph("卖家确认函", new Font(bf, 12));
			head.setAlignment(1); // 0 align to the left , 1 align to the center
			document.add(head);
			
			/** add the fill term of the PDF **/
			for (String key : map.keySet()) {
				document.add(new Paragraph(key+":"+map.get(key),fontChinese));
			}
			
			/** add the content of the PDF**/
			Paragraph context = new Paragraph();
			String pdfContent = new String(term.getContent());
	        ElementList elementList =MyXMLWorkerHelper.parseToElementList(pdfContent, null);
	        for (Element element : elementList) {
	            context.add(element);
	        }
	        document.add(context);
	        
	        /** add the content of the PDF **/
			document.add(new Paragraph("   "));
			for (String key : map.keySet()) {
				document.add(new Paragraph(key+":",fontChinese));
			}
			document.add(new Paragraph("卖家（印刷体）：qichi（543290854326423）",fontChinese));
			document.add(new Paragraph("亲笔签名/公司公章： _______________________________", fontChinese));
			document.add(new Paragraph("日期： ", fontChinese));
			document.close();
			System.out.println("-----------ok--------------");
		} catch (Exception e) {
			logger.log(LogLevel.ERROR,"error occur while create PDF");
		}
//		response.setContentType("application/pdf");  
//		response.setHeader("Content-Disposition", "attachment; filename=Contract.pdf");  
		responseData.setStatus(flag);
		responseData.setData(map.toString());

		return responseData;
	}
	
	@GET
	@RequestMapping(value="/downloadLetter", method=RequestMethod.GET)
	public void createConfirmLetter(HttpServletRequest req, HttpServletResponse resp,
			 @RequestParam String promoId) throws MissingArgumentException, IOException, PromoException {
		resp.setContentType("application/pdf");
		UserData userData = loginService.getUserDataFromCookie(req);
		InputStream inputStream = null;
		OutputStream outStream = null;
		SubsidyAttachment attachment = null;
		String attachmentName = "";
		String attachmentType = "";
		
		resp.setHeader("Content-disposition", "attachment; filename=\""+attachmentName+"."+attachmentType+"\"");
		System.out.println(resp.getHeaders("Content-disposition"));
		outStream = resp.getOutputStream();
		int len = 0;
		byte[] buffer = new byte[4096];
		while((len = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
		if (inputStream != null) {
			inputStream.close();
			outStream.flush();
			outStream.close();
		}
	}

	/**
	 * Upload subsidy attachments.
	 * 
	 * @param req
	 * @param resp
	 * @param uploadFile
	 * @param skuId
	 * @param promoId
	 * @param key
	 * @return
	 * @throws MissingArgumentException
	 */
	@POST
	@RequestMapping(value = "/uploadAttachment")
	public ModelAndView uploadAttachment(HttpServletRequest req, HttpServletResponse resp,
			@RequestPart MultipartFile uploadFile, @RequestParam String promoId,
			@RequestParam String key) throws MissingArgumentException {
		ModelAndView mav = new ModelAndView(ViewResource.UPLOAD_RESPONSE.getPath());
		ResponseData<String> responseData = new ResponseData<String>();
		UserData userData = loginService.getUserDataFromCookie(req);
		AttachmentFileValidator attachmentFileValidator = AttachmentFileValidator.getInstance();
		attachmentFileValidator.setLocale(LocaleUtil.getCurrentLocale());
		try {
			if (attachmentFileValidator.isValidate(uploadFile)) {
				try {
					String fileType = attachmentFileValidator.getType(uploadFile).toString();
					String downloadUrl = subsidyService.uploadSubsidyAttachment(promoId, userData.getUserId(), key,
							uploadFile, fileType);
					responseData.setStatus(true);
					responseData.setMessage(downloadUrl);
				} catch (Exception e) {
					responseData.setStatus(false);
					responseData.setMessage(e.getMessage());
					e.printStackTrace();
				}
			}
		} catch (AttachmentUploadException e) {
			logger.log(LogLevel.ERROR, "Failed to upload attachment", e);
			responseData.setStatus(false);
			responseData.setMessage(e.getMessage());
		}
		mav.addObject("response", PojoConvertor.convertToJson(responseData));
		return mav;
	}

	/**
	 * Download subsidy attachments.
	 * 
	 * @param req
	 * @param resp
	 * @param promoId
	 * @param userId
	 * @param skuId
	 * @param key
	 * @throws Exception
	 */
	@GET
	@RequestMapping(value = "/downloadAttachment")
	public void downloadAttachment(HttpServletRequest req, HttpServletResponse resp,
			@PathVariable("promoId") String promoId, @PathVariable("userId") Long userId,
			@PathVariable("key") String key) throws Exception {

		resp.setContentType("application/x-msdownload;");
		UserData userData = loginService.getUserDataFromCookie(req);

		InputStream inputStream = null;
		OutputStream outStream = null;
		SubsidyAttachment attachment = null;
		String attachmentName = "";
		String attachmentType = "";

		try {
			attachment = subsidyService.downloadSubsidyAttachment(promoId, userData.getUserId(), key);
			if (attachment != null) {
				inputStream = new ByteArrayInputStream(attachment.getFileContent());
				attachmentName = attachment.getFileName();
				attachmentType = attachment.getFileType();
			}
			resp.setHeader("Content-disposition", "attachment; filename=\"" + attachmentName + "." + attachmentType
					+ "\"");
			outStream = resp.getOutputStream();
			int len = 0;
			byte[] buffer = new byte[4096];
			while ((len = inputStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
			}
		} catch (Exception e) {
			logger.log(LogLevel.ERROR, "Failed to downlaod attachment", e);
			CalEventHelper.writeException("ERROR", e, "Failed to download attachment: " + e.getMessage());
		} finally {
			if (inputStream != null) {
				inputStream.close();
				outStream.flush();
				outStream.close();
			}
		}
	}

	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(MissingArgumentException exception, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("error");
		CalEventHelper.writeException(exception.getErrorType().name(), exception);
		return mav;
	}
}
