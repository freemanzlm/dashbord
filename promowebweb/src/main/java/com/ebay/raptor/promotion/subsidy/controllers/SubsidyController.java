package com.ebay.raptor.promotion.subsidy.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
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
import com.ebay.raptor.promotion.enums.PromoError;
import com.ebay.raptor.promotion.excep.AttachmentUploadException;
import com.ebay.raptor.promotion.excep.PromoException;
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
import com.ebay.raptor.promotion.util.EncryptUtil;
import com.ebay.raptor.promotion.util.JsonUtils;
import com.ebay.raptor.promotion.util.LocaleUtil;
import com.ebay.raptor.promotion.util.MyXMLWorkerHelper;
import com.ebay.raptor.promotion.util.PojoConvertor;
import com.ebay.raptor.promotion.validation.SubsidyAttachmentFileValidator;
import com.itextpdf.text.Chunk;
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

	@Autowired
	LoginService loginService;
	@Autowired
	PromotionService promoService;
	@Autowired
	PromotionViewService view;
	@Autowired
	SubsidyService subsidyService;
	@Autowired
	CSApiService csApiService;
	@Autowired
	WltApiService wltApiService;
	@Autowired
	ResourceBundleMessageSource msgResource;

	@RequestMapping(value = "/acknowledgment", method = RequestMethod.GET)
	public ModelAndView subsidyStepOne(@RequestParam("promoId") String promoId, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		Date now = new Date();
		try {
			UserData userData = loginService.getUserDataFromCookie(request);
			Long userID = userData.getUserId();
			Promotion promo = promoService.getPromotionById(promoId, userID, userData.getAdmin());
			Subsidy subsidy = subsidyService.getSubsidy(promoId, userID);
			SubsidyLegalTerm term = subsidyService.getSubsidyLegalTerm(promo.getRewardType(), promo.getRegion());
			String pdfContent = URLDecoder.decode(new String(term.getContent()), "UTF-8");
			
			Integer subsidyStatus = subsidy.getStatus();
			if (PMSubsidyStatus.PM_UNKNOWN_STATUS.getPmStatus() == subsidyStatus||PMSubsidyStatus.REWARD_APPLIABLE_AGAIN.getPmStatus()==subsidyStatus) { // 初次访问 需要更新状态
				subsidy.setStatus(PMSubsidyStatus.REWARD_VISITED.getPmStatus());
				boolean ret = subsidyService.updateSubsidy(subsidy);
			} else if (PMSubsidyStatus.REWARD_VISITED.getPmStatus() != subsidyStatus||PMSubsidyStatus.REWARD_APPLIABLE_AGAIN.getPmStatus()==subsidyStatus) {// 不是第一次访问了 不需要更新状态为已访问 同时需要拿出用户填写的数据
				SubsidySubmission subsidySubmission = subsidyService.getSubsidySubmission(promoId, userID);
				term = subsidyService.convertSubmissionToLegalTerm(term, subsidySubmission);
			}
			
			if (null != subsidyStatus && PMSubsidyStatus.REWARD_VISITED.getPmStatus() != subsidyStatus) { 
				List<SubsidyAttachment> subsidyAttachmentList = subsidyService.getSubsidyAttachment(promoId, userID);
				term = subsidyService.convertSubmissionToLegalTerm(term, subsidyAttachmentList);
			}
			
			if (term.getSubsidyType() == 2) { // 奖励类型为wlt积分
				String backURL = getBindWltURL(request, userData.getUserName());
				putWltAccountInfo(model, userData.getUserName(), backURL);
			}
			
			ArrayList<SubsidyCustomField>[] fields = subsidyService.splitCustomFields(term);
			view.calcualteCurentStep(promo);
			view.appendPromoEndCheck(model.getModel(), promo, now);
			view.appendPromoAwardEndCheck(model.getModel(), promo, now);
			model.addObject("nonuploadFields", fields[0]);
			model.addObject("uploadFields", fields[1]);
			model.addObject("pdfContent", pdfContent);
			model.addObject("subsidy", subsidy);
			model.addObject("subsidyTerm", term);
			model.addObject(ViewContext.Promotion.getAttr(), promo);
			model.addObject(ViewContext.IsAdmin.getAttr(), userData.getAdmin());
			model.setViewName("subsidy_acknowledgment");
		} catch (Exception e) {
			logger.log(LogLevel.ERROR, String.format("Subsidy not found for promotion:%s", promoId), e);
			e.printStackTrace();
		}
		return model;

	}

	@RequestMapping(value = "/subsidyStepTwo", method = RequestMethod.GET)
	public ModelAndView subsidyStepTwo(@RequestParam("promoId") String promoId, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		Date now = new Date();
		try {
			UserData userData = loginService.getUserDataFromCookie(request);
			Long userID = userData.getUserId();
			Promotion promo = promoService.getPromotionById(promoId, userID, userData.getAdmin());
			SubsidyLegalTerm term = subsidyService.getSubsidyLegalTerm(promo.getRewardType(), promo.getRegion());
			Subsidy subsidy = subsidyService.getSubsidy(promoId, userID);
			Integer subsidyStatus = subsidy.getStatus();
			if (null != subsidyStatus || PMSubsidyStatus.REWARD_VISITED.getPmStatus() != subsidyStatus) { 
				List<SubsidyAttachment> subsidyAttachmentList = subsidyService.getSubsidyAttachment(promoId, userID);
				term = subsidyService.convertSubmissionToLegalTerm(term, subsidyAttachmentList);
			}
			ArrayList<SubsidyCustomField>[] fields = subsidyService.splitCustomFields(term);
			view.calcualteCurentStep(promo);
			view.appendPromoEndCheck(model.getModel(), promo, now);
			view.appendPromoAwardEndCheck(model.getModel(), promo, now);
			model.addObject("uploadFields", fields[1]);
			model.addObject("subsidy", subsidy);
			model.addObject(ViewContext.Promotion.getAttr(), promo);
			model.addObject(ViewContext.IsAdmin.getAttr(), userData.getAdmin());
			model.setViewName("subsidy/subsidy_step2");
		} catch (Exception e) {
			logger.log(LogLevel.ERROR, String.format("Subsidy not found for promotion:%s", promoId), e);
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/acknowledgment", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData<String> subsidyStepTwot(HttpServletRequest request, HttpServletResponse response) {
		ResponseData<String> responseData = new ResponseData<String>();
		HashMap<String, String> map = new HashMap<String, String>();
		String promoId = request.getParameter("promoId");
		try {
			UserData userData = loginService.getUserDataFromCookie(request);
			Long userID = userData.getUserId();
			Promotion promo = promoService.getPromotionById(promoId, userData.getUserId(), userData.getAdmin());
			SubsidyLegalTerm term = subsidyService.getSubsidyLegalTerm(promo.getRewardType(), promo.getRegion());
			Subsidy subsidy = subsidyService.getSubsidy(promoId, userID);
			List<SubsidyCustomField> fields = term.getSubsidyCustomFields();
			for (SubsidyCustomField field : fields) {
				if (!field.isUpload) {
					map.put(field.getKey(), request.getParameter(field.getKey()));
				}
			}
			String ret = JsonUtils.objectToJsonString(map);
			SubsidySubmission subsidySubmission = subsidyService.getSubsidySubmission(promoId, userID);
			if (null == subsidySubmission) {
				subsidySubmission = new SubsidySubmission();
				subsidySubmission.setOracleId(userID);
				subsidySubmission.setSfId(promoId);
			}
			subsidySubmission.setContent(ret);
			boolean flag = subsidyService.updateSubsidySubmission(subsidySubmission);
			if (flag) {
				int orginalStatus = subsidy.getStatus();
				if(orginalStatus<PMSubsidyStatus.REWARD_COMMITED.getPmStatus()){// 如果现阶段状态小于已提交 则更新 否则不更新状态
					subsidy.setStatus(PMSubsidyStatus.REWARD_COMMITED.getPmStatus());
					subsidyService.updateSubsidy(subsidy);
				}
			}
			responseData.setStatus(flag);
		} catch (Exception e) {
			responseData.setStatus(false);
			logger.log(LogLevel.ERROR, String.format("Subsidy not found for promotion:%s", promoId), e);
			e.printStackTrace();
		}
		return responseData;

	}

	@RequestMapping(value = "/downloadLetter", method = RequestMethod.GET)
	public void createConfirmLetter(HttpServletRequest req, HttpServletResponse resp, @RequestParam String promoId) throws MissingArgumentException,
			IOException, PromoException {
		resp.setContentType("application/pdf");
		resp.setHeader("Content-disposition", "attachment; filename=" + "contract.pdf");
		UserData userData = loginService.getUserDataFromCookie(req);
		Promotion promo = promoService.getPromotionById(promoId, userData.getUserId(), userData.getAdmin());
		SubsidyLegalTerm term = subsidyService.getSubsidyLegalTerm(promo.getRewardType(), promo.getRegion());
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> retMap = new HashMap<String, Object>();
		SubsidySubmission subsidySubmission = subsidyService.getSubsidySubmission(promoId, userData.getUserId());
		String fillItems = subsidySubmission.getContent();
		List<SubsidyCustomField> termList = term.getSubsidyCustomFields();
		map = JsonUtils.parseJson(fillItems);
		for (String key : map.keySet()) {
			for (SubsidyCustomField subsidyCustomField : termList) {
				if ((!key.equals("_sellerCode")) && (!key.equals("_sellerName")) && key.equals(subsidyCustomField.getKey())) {
					retMap.put(subsidyCustomField.getDisplayLabel(), map.get(key));
				}
			}
		}
		// generate pdf START
		Document document = null;
		BaseFont bf = null;
		Font fontChinese = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			bf = BaseFont.createFont("msYaHei.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			fontChinese = new Font(bf, 10);
			Locale locale = LocaleContextHolder.getLocale();
			String v_title = msgResource.getMessage("subsidy.pdf.title", null, locale);
			String v_sellerinfo = msgResource.getMessage("subsidy.pdf.sellerinfo", null, locale);
			String v_sellerNamequote = msgResource.getMessage("subsidy.pdf.sellernamequote", null, locale);
			String v_ebayid = msgResource.getMessage("subsidy.pdf.ebayid", null, locale);
			String v_promotion = msgResource.getMessage("subsidy.pdf.promotion", null, locale);
			String v_promotionquote = msgResource.getMessage("subsidy.pdf.promotionquote", null, locale);
			String v_bonus = msgResource.getMessage("subsidy.pdf.bonus", null, locale);
			String v_bonusquote = msgResource.getMessage("subsidy.pdf.bonusquote", null, locale);
			String v_applytime = msgResource.getMessage("subsidy.pdf.applytime", null, locale);
			String v_applytimequote = msgResource.getMessage("subsidy.pdf.applytimequote", null, locale);
			String v_seller = msgResource.getMessage("subsidy.pdf.seller", null, locale);
			String v_signtext = msgResource.getMessage("subsidy.pdf.signature", null, locale);
			String v_datetext = msgResource.getMessage("subsidy.pdf.date", null, locale);

			/** create the right font for CHINESE **/
			document = new Document(PageSize.A4);
			document.addHeader("charset", "utf-8");
			/** get the html content from javabean and convert to string **/
			PdfWriter pdfWriter = PdfWriter.getInstance(document, resp.getOutputStream());
			document.open();

			/** add the head of the PDF **/
			Paragraph head = new Paragraph(v_title, new Font(bf, 14));
			head.setAlignment(1); // 0 align to the left , 1 align to the center
			document.add(head);
			document.add(new Paragraph("   "));

			/** add the fill term of the PDF **/
			/** add seller basic info **/
			Paragraph p_sellerinfo = new Paragraph(v_sellerinfo + "：", fontChinese);
			document.add(p_sellerinfo);

			/** add seller name **/
			String v_sellerName = (String) map.get("_sellerName");
			String v_sellerNameKey = "";
			for (SubsidyCustomField field : termList) {
				if ("_sellerName".equals(field.getKey())) {
					v_sellerNameKey = field.getDisplayLabel();
				}
			}
			Paragraph p1 = new Paragraph(v_sellerNameKey + "：" + v_sellerName + v_sellerNamequote, fontChinese);
			document.add(p1);

			/** add seller code **/
			String v_sellerCode = (String) map.get("_sellerCode");
			String v_sellerCodeKey = "";
			for (SubsidyCustomField field : termList) {
				if ("_sellerCode".equals(field.getKey())) {
					v_sellerCodeKey = field.getDisplayLabel();
				}
			}
			Paragraph p2 = new Paragraph(v_sellerCodeKey + "：" + v_sellerCode, fontChinese);
			document.add(p2);

			/** add the extra info **/
			for (String key : retMap.keySet()) {
				document.add(new Paragraph(key + "：" + retMap.get(key), fontChinese));
			}

			/** add ebayid info **/
			Paragraph p_ebayid = new Paragraph(v_ebayid + "：" + userData.getUserName(), fontChinese);
			document.add(p_ebayid);

			/** add promotion basic info **/
			Paragraph p_promotion = new Paragraph(v_promotion + "：" + promo.getName() + v_promotionquote, fontChinese);
			document.add(p_promotion);

			/** add bonus basic info **/
			Paragraph p_bonus = new Paragraph(v_bonus + "：" + promo.getReward() + promo.getCurrency() + v_bonusquote, fontChinese);
			document.add(p_bonus);

			/** add bonus basic info **/
			Paragraph p_applytime = new Paragraph(v_applytime + "：" + sdf.format(promo.getRewardDlDt()) + v_applytimequote, fontChinese);
			document.add(p_applytime);

			document.add(new Paragraph("   "));

			/** add the content of the PDF **/
			Paragraph context = new Paragraph();
			context.setFont(fontChinese);

			String pdfContent = URLDecoder.decode(new String(term.getContent()),"UTF-8");
			ElementList elementList = MyXMLWorkerHelper.parseToElementList(pdfContent, null);
			for (Element element : elementList) {
				context.add(element);
			}
			document.add(context);
			document.add(new Paragraph("   "));

			/** add the end of the PDF **/
			document.add(new Paragraph(v_seller + "：" + map.get("_sellerName") + "（" + map.get("_sellerCode") + "）", fontChinese));
			/** add the extra info **/
			for (String key : retMap.keySet()) {
				document.add(new Paragraph(key + "：" + retMap.get(key), fontChinese));
			}
			document.add(new Paragraph(v_signtext + "：_____________________", fontChinese));
			document.add(new Paragraph(v_datetext + "：_____________________", fontChinese));
			document.close();
		} catch (Exception e) {
			logger.log(LogLevel.ERROR, "error occur while create PDF");
		}
	}

	@POST
	@RequestMapping(value = "/upload")
	public String upload(HttpServletRequest req, HttpServletResponse resp,ModelMap modelMap){
		String promoId = req.getParameter("promoId");
		try {
			UserData userData = loginService.getUserDataFromCookie(req);
			MultipartHttpServletRequest request = (MultipartHttpServletRequest) req;
			SubsidyAttachmentFileValidator fileValidator = SubsidyAttachmentFileValidator.getInstance();
			fileValidator.setLocale(LocaleUtil.getCurrentLocale());
			Map<String,MultipartFile> fileMap = request.getFileMap();
			Map<String, String> errorMsg = new HashMap<String, String>();
			for (String key : fileMap.keySet()) {
				MultipartFile file = fileMap.get(key);
				if(!file.isEmpty()){
					if (fileValidator.validate(file)) {//check whether the file type is legal pdf jpg zip;
						String downloadUrl = subsidyService.uploadSubsidyAttachment(promoId, userData.getUserName(), userData.getUserId(), key, file);
					}else{
						errorMsg.put(key, "file type error");
					}
				}
			}
			//update the subsidy status 
			Promotion promo = promoService.getPromotionById(promoId, userData.getUserId(), userData.getAdmin());
			SubsidyLegalTerm term = subsidyService.getSubsidyLegalTerm(promo.getRewardType(), promo.getRegion());
			Subsidy subsidy = subsidyService.getSubsidy(promoId, userData.getUserId());
			int orginalStatus = subsidy.getStatus();
			if(orginalStatus<PMSubsidyStatus.REWARD_UPLOADED.getPmStatus()){
				List<String> needtouploadList = new ArrayList<String>();
				List<SubsidyCustomField> fields = term.getSubsidyCustomFields();
				List<SubsidyAttachment> subsidyAttachments = subsidyService.getSubsidyAttachment(promo.getPromoId(),subsidy.getOracleId());
				for (SubsidyCustomField field : fields) {
					if(field.isUpload){
						needtouploadList.add(field.getKey());
					}
				}
				for (SubsidyAttachment subsidyAttachment : subsidyAttachments) {
					needtouploadList.remove(subsidyAttachment.getKey());
				}
				if(needtouploadList.size()==0){
					subsidy.setStatus(PMSubsidyStatus.REWARD_UPLOADED.getPmStatus());
					subsidyService.updateSubsidy(subsidy);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		return modelMap;
		return "redirect: subsidyStepTwo?promoId="+promoId;
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
	 * @throws PromoException
	 */
	@POST
	@RequestMapping(value = "/uploadAttachment")
	public ModelAndView uploadAttachment(HttpServletRequest req, HttpServletResponse resp, @RequestPart MultipartFile uploadFile, @RequestParam String promoId,
			@RequestParam String key) throws MissingArgumentException, PromoException {
		ModelAndView mav = new ModelAndView(ViewResource.UPLOAD_RESPONSE.getPath());
		ResponseData<String> responseData = new ResponseData<String>();
		UserData userData = loginService.getUserDataFromCookie(req);
		SubsidyAttachmentFileValidator attachmentFileValidator = SubsidyAttachmentFileValidator.getInstance();
		attachmentFileValidator.setLocale(LocaleUtil.getCurrentLocale());
		List<String> fileList = new ArrayList<String>();
		Promotion promo = promoService.getPromotionById(promoId, userData.getUserId(), userData.getAdmin());
		SubsidyLegalTerm term = subsidyService.getSubsidyLegalTerm(promo.getRewardType(), promo.getRegion());
		Subsidy subsidy = subsidyService.getSubsidy(promoId, userData.getUserId());

		try {
			if (attachmentFileValidator.validate(uploadFile)) {
				try {
					String downloadUrl = subsidyService.uploadSubsidyAttachment(promoId, userData.getUserName(), userData.getUserId(), key, uploadFile);
					String fileId = URLEncoder.encode(EncryptUtil.encrypt(downloadUrl), "UTF-8");
					List<SubsidyAttachment> subsidyAttachmentList = null;
					try {
						subsidyAttachmentList = subsidyService.getSubsidyAttachment(promoId, userData.getUserId());
					} catch (PromoException e) {
						logger.log(LogLevel.ERROR, String.format("Subsidy attachment not found for promotion:%s, user:%s", promoId, userData.getUserId()), e);
					}
					if (subsidyAttachmentList != null) {
						mav.addObject("hasSubmitAttachments", true);
						term = subsidyService.convertSubmissionToLegalTerm(term, subsidyAttachmentList);
					}
					ArrayList<SubsidyCustomField>[] fields = subsidyService.splitCustomFields(term);
					mav.addObject("uploadFields", fields[1]);
					responseData.setStatus(true);
					responseData.setMessage(fileId);
					// judge whether the user has upload all the file required;
					subsidyService.updateSubsidy(promo, subsidy, PMSubsidyStatus.REWARD_UPLOADED.getPmStatus());
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
	public void downloadAttachment(HttpServletRequest req, HttpServletResponse resp, @RequestParam("promoId") String promoId, @RequestParam("key") String key)
			throws Exception {
		UserData userData = loginService.getUserDataFromCookie(req);

		InputStream inputStream = null;
		OutputStream outStream = null;
		SubsidyAttachment attachment = null;
		String attachmentName = "";
		String attachmentType = "";

		try {
			attachment = subsidyService.downloadSubsidyAttachment(promoId, userData.getUserId(), key);
			if (attachment != null && null != attachment.getFileContent()) {
				resp.setContentType("application/x-msdownload;");
				inputStream = new ByteArrayInputStream(attachment.getFileContent());
				// avoid messy code of the chinese
				attachmentName = URLEncoder.encode(attachment.getFileName(), "utf-8");
				attachmentType = attachment.getFileType();
				resp.setHeader("Content-disposition", "attachment; filename=\"" + attachmentName + "." + attachmentType + "\"");
				outStream = resp.getOutputStream();
				int len = 0;
				byte[] buffer = new byte[4096];
				while ((len = inputStream.read(buffer)) != -1) {
					outStream.write(buffer, 0, len);
				}
			} else {
				resp.sendError(HttpServletResponse.SC_NOT_FOUND, getMessage(PromoError.SUBSIDY_ATTACHMENT_NOT_FOUND.getKey()));
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
	 * 
	 * @param req
	 * @param resp
	 * @param id
	 * @throws Exception
	 */
	@GET
	@RequestMapping(value = "/downloadAttachmentById")
	public void downloadAttachmentById(HttpServletRequest req, HttpServletResponse resp, @RequestParam("id") String id) throws Exception {

		InputStream inputStream = null;
		OutputStream outStream = null;
		SubsidyAttachment attachment = null;
		String attachmentName = "";
		String attachmentType = "";
		Long fileId = null;

		try {
			fileId = Long.parseLong(EncryptUtil.decrypt(id));
		} catch (Exception e) {
			resp.sendError(404, String.format("File attachment id is not valid: %s", id));
		}

		if (fileId != null) {
			attachment = subsidyService.downloadSubsidyAttachment(fileId);
			if (attachment == null) {
				resp.sendError(404, String.format("Subsidy attachment doesn't exist!"));
			} else {
				inputStream = new ByteArrayInputStream(attachment.getFileContent());
				attachmentName = URLEncoder.encode(attachment.getFileName(), "utf-8");
				attachmentType = attachment.getFileType();
				resp.setHeader("Content-disposition", "attachment; filename=\"" + attachmentName + "." + attachmentType + "\"");
				outStream = resp.getOutputStream();
				int len = 0;
				byte[] buffer = new byte[4096];
				while ((len = inputStream.read(buffer)) != -1) {
					outStream.write(buffer, 0, len);
				}

				if (inputStream != null) {
					inputStream.close();
					outStream.flush();
					outStream.close();
				}
			}
		}

	}

	/**
	 * Called by WLT service when binding WLT account succeeds.
	 * 
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
			WltResponse<SearchBindAck> wltResponse = wltApiService.searchIsBind(userName);
			SearchBindAck data = wltResponse.getData();
			if (data != null && "00".equals(data.getCode())) {
				subsidyService.saveWLTAccount(userName, mobile);
				response.sendRedirect("acknowledgment?isWltFirstBound=true&promoId=" + promoId);
			}
		} else {
			subsidyService.saveWLTAccount(userName, mobile);
			response.sendRedirect("acknowledgment?isWltFirstBound=true&promoId=" + promoId);
		}
	}

	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(Exception exception, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("errors/500");
		CalEventHelper.writeException("Exception", exception, true);
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
	 * 
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
