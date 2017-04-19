package com.ebay.raptor.promotion.subsidy.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ebay.app.raptor.promocommon.MissingArgumentException;
import com.ebay.cbt.common.constant.pm.PMSubsidyStatus;
import com.ebay.cbt.raptor.promotion.po.Subsidy;
import com.ebay.cbt.raptor.promotion.po.SubsidyAttachment;
import com.ebay.cbt.raptor.promotion.po.SubsidyCustomField;
import com.ebay.cbt.raptor.promotion.po.SubsidyLegalTerm;
import com.ebay.cbt.raptor.promotion.po.SubsidySubmission;
import com.ebay.cbt.raptor.promotion.po.WLTAccount;
import com.ebay.cbt.raptor.wltapi.pojo.SearchBindAck;
import com.ebay.cbt.raptor.wltapi.resp.WltResponse;
import com.ebay.cbt.raptor.wltapi.service.WltApiService;
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
	@Autowired WltApiService wltApiService;
	@Autowired ResourceBundleMessageSource msgResource;

	@RequestMapping(value = "/acknowledgment", method = RequestMethod.GET)
	public ModelAndView handleRequest(@RequestParam("promoId") String promoId, HttpServletRequest request,
			HttpServletResponse response) throws MissingArgumentException, IOException {
		ModelAndView model = new ModelAndView();
		UserData userData = loginService.getUserDataFromCookie(request);
		Long userID = userData.getUserId();
		Date now = new Date();
		Promotion promo = null;
		SubsidyLegalTerm term = null;
		
		String backURL = getBindWltURL(request, userData.getUserName());
		model.setViewName(ViewResource.ERROR.getPath());

		try {
			promo = promoService.getPromotionById(promoId, userID, userData.getAdmin());

			if (promo != null) {
				Subsidy subsidy = subsidyService.getSubsidy(promoId, userID);
				String status = subsidy.getStatus();
//				term = subsidyService.getSubsidyLegalTerm(promo.getRewardType(), "CN");
				term = subsidyService.getSubsidyLegalTerm(promo.getRewardType(), promo.getRegion());
				if (term != null) {
					if(status.equals(PMSubsidyStatus.PM_UNKNOWN_STATUS.getSfName())||status.equals(PMSubsidyStatus.REWARD_VISITED.getSfName())){
						SubsidySubmission subsidySubmission = subsidyService.getSubsidySubmission(promoId,userID);
						if (subsidySubmission != null) {
							model.addObject("hasSubmitFields", subsidySubmission != null);
							term = subsidyService.convertSubmissionToLegalTerm(term, subsidySubmission);
						}
					}
					
					if (term.getSubsidyType() == 2) {
						putWltAccountInfo(model, userData.getUserName(), backURL);
					}
					ArrayList<SubsidyCustomField>[] fields = subsidyService.splitCustomFields(term);
					model.addObject("nonuploadFields", fields[0]);
					model.addObject("uploadFields", fields[1]);
				}
				subsidyService.updateSubsidy(promoId, userID, PMSubsidyStatus.REWARD_VISITED.getAVStatus());
				
				view.calcualteCurentStep(promo);
				view.appendPromoEndCheck(model.getModel(), promo, now);
				view.appendPromoAwardEndCheck(model.getModel(), promo, now);
				model.addObject("subsidyTerm", term);

				model.addObject(ViewContext.Promotion.getAttr(), promo);
				model.addObject(ViewContext.IsAdmin.getAttr(), userData.getAdmin());
				model.setViewName("subsidy_acknowledgment");
			}
		} catch (PromoException e) {
			String message = "Failed to get promotion: promoId=" + promoId + ",userId:" + userData.getUserId();
			CalEventHelper.writeException("SubsidyError", e, message);
			logger.log(LogLevel.ERROR, message, e);
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
		subsidyService.updateSubsidy(promoId, userID, PMSubsidyStatus.REWARD_COMMITED.getAVStatus());
		responseData.setStatus(flag);
		responseData.setData(map.toString());
		return responseData;
	}
	
	@RequestMapping(value="/downloadLetter", method=RequestMethod.GET)
	public void createConfirmLetter(HttpServletRequest req, HttpServletResponse resp,
			 @RequestParam String promoId) throws MissingArgumentException, IOException, PromoException {
		resp.setContentType("application/pdf");
		resp.setHeader("Content-disposition", "attachment; filename="+"contract.pdf");
		UserData userData = loginService.getUserDataFromCookie(req);
		Promotion promo = promoService.getPromotionById(promoId, userData.getUserId(), userData.getAdmin());
		SubsidyLegalTerm term = subsidyService.getSubsidyLegalTerm(promo.getRewardType(), promo.getRegion());
		Map<String, Object> map = new HashMap<String, Object>();
		SubsidySubmission subsidySubmission = subsidyService.getSubsidySubmission(promoId, userData.getUserId());
		String fillItems = subsidySubmission.getContent();
		map = JsonUtils.parseJson(fillItems);
		//generate pdf START
		Document document = null;
		BaseFont bf = null;
		Font fontChinese = null;
		try {
			bf = BaseFont.createFont("simsun.ttf", BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
			Locale locale = LocaleContextHolder.getLocale();
			String title = msgResource.getMessage("subsidy.pdf.title",null, locale);
			String sellertext = msgResource.getMessage("subsidy.pdf.seller",null, locale);
			String signtext = msgResource.getMessage("subsidy.pdf.signature",null, locale);
			String datetext = msgResource.getMessage("subsidy.pdf.date",null, locale);
			
			/** create the right font for CHINESE **/
			fontChinese = new Font(bf, 10);
			document = new Document(PageSize.A4);
			/** get the html content from javabean and convert to string **/
			PdfWriter pdfWriter = PdfWriter.getInstance(document,resp.getOutputStream());
			document.open();
			
			/** add the head of the PDF **/
			Paragraph head = new Paragraph(title, new Font(bf, 12));
			head.setAlignment(1); // 0 align to the left , 1 align to the center
			document.add(head);
			
			/** add the fill term of the PDF **/
			for (String key : map.keySet()) {
				document.add(new Paragraph(key+":"+map.get(key),fontChinese));
			}
			
			/** add the content of the PDF**/
			Paragraph context = new Paragraph();
			String pdfContent = URLDecoder.decode(new String(term.getContent()));
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
			document.add(new Paragraph(sellertext+"：_____________________",fontChinese));
			document.add(new Paragraph(signtext+"：_____________________", fontChinese));
			document.add(new Paragraph(datetext+"：", fontChinese));
			document.close();
			System.out.println("-----------ok--------------");
		} catch (Exception e) {
			logger.log(LogLevel.ERROR,"error occur while create PDF");
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
					subsidyService.updateSubsidy(promoId, userData.getUserId(), PMSubsidyStatus.REWARD_UPLOADED.getAVStatus());
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
			@RequestParam("promoId") String promoId,
			@RequestParam("key") String key) throws Exception {

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
	
	/**
	 * download subsidy attachments with id 
	 * @param req
	 * @param resp
	 * @param id
	 * @throws Exception
	 */
	@GET
	@RequestMapping(value = "/downloadAttachmentById")
	public void downloadAttachmentById(HttpServletRequest req, HttpServletResponse resp,
			@RequestParam("id") Long id) throws Exception {

		resp.setContentType("application/x-msdownload;");
		UserData userData = loginService.getUserDataFromCookie(req);

		InputStream inputStream = null;
		OutputStream outStream = null;
		SubsidyAttachment attachment = null;
		String attachmentName = "";
		String attachmentType = "";

		try {
			attachment = subsidyService.downloadSubsidyAttachment(id);
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
	
	/**
	 * Called by WLT service when binding WLT account succeeds.
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value = "/bindWlt", method = RequestMethod.POST)
	public void bindWltAccount(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// returned by WLT
		String mobile = request.getParameter("mobile");
		
		// returned in backURL as query parameters.
		String userName = request.getParameter("ebayId");
		String promoId = request.getParameter("promoId");
		
		if (mobile == null || mobile.isEmpty()) {
			WltResponse<SearchBindAck>  wltResponse = wltApiService.searchIsBind(userName);
			SearchBindAck data = wltResponse.getData();
			if (data != null && "00".equals(data.getCode())) {
				subsidyService.saveWLTAccount(userName,mobile);
				response.sendRedirect("acknowledgment?isWltFirstBound=true&promoId=" + promoId);
			}
		} else {
			subsidyService.saveWLTAccount(userName,mobile);
			response.sendRedirect("acknowledgment?isWltFirstBound=true&promoId=" + promoId);
		}
	}

	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(MissingArgumentException exception, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("error");
		CalEventHelper.writeException(exception.getErrorType().name(), exception);
		return mav;
	}
	
	/**
	 * Put WLT account information into Model.
	 * @param mav
	 * @param userName
	 * @param backURL
	 */
	private void putWltAccountInfo(ModelAndView mav, String userName, String backURL) {
		WLTAccount wltAccount = null;
		try {
			wltAccount = subsidyService.getWLTAccount(userName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (wltAccount == null) {
			String bindURL = wltApiService.bindWltAccount(userName, backURL);
			mav.addObject("wltBindURL", bindURL);
		}
		
		mav.addObject("wltAccount", wltAccount);
	}
	
	/**
	 * Return http://host/promotion/subisdy/bindWlt?queryString&ebayId=userName.
	 * @param request
	 * @param userName
	 * @return
	 */
	private String getBindWltURL(HttpServletRequest request, String userName) {
		StringBuffer backUrl = request.getRequestURL();
		int lastSlash = backUrl.lastIndexOf("/");
		backUrl = backUrl.replace(lastSlash, backUrl.length(), "/bindWlt");
		backUrl.append("?").append(request.getQueryString());
		backUrl.append("&ebayId=").append(userName);
		
		return backUrl.toString();
	}
	
	private String getMessage(String key) {
		return msgResource.getMessage(key, null, LocaleContextHolder.getLocale());
	}
}
