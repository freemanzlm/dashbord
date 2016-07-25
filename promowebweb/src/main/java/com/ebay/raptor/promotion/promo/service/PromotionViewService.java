package com.ebay.raptor.promotion.promo.service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ebay.app.raptor.promocommon.CommonLogger;
import com.ebay.cbt.common.constant.pm.PMPromotionStatus;
import com.ebay.raptor.promotion.enums.PromotionStep;
import com.ebay.raptor.promotion.excel.ColumnConfiguration;
import com.ebay.raptor.promotion.excel.util.ExcelUtil;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.list.controller.ListingController;
import com.ebay.raptor.promotion.locale.LocaleUtil;
import com.ebay.raptor.promotion.pojo.business.Promotion;
import com.ebay.raptor.promotion.util.DateUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

@Component
public class PromotionViewService {
	private static CommonLogger logger = CommonLogger.getInstance(ListingController.class);
	private final ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private PromotionService service;
	
	public PromotionViewService() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ContextViewRes handleView(Promotion pro, long uid) throws PromoException{
		ContextViewRes res = new ContextViewRes();
		Map<String, Object> context = new HashMap<String, Object>();
		context.put(ViewContext.Agreement.getAttr(), ViewResource.HV_AGGREMENT.getPath());
		
		Date now = new Date();
		
		// whether nomination end date has expired
		Date nominationEndDate = pro.getRegEndDate();
		if (nominationEndDate != null) {
			nominationEndDate = DateUtil.convertToSystemTime(nominationEndDate, DateUtil.BEIJING_TIMEZONE);
			context.put(ViewContext.IS_NOMINATION_END.getAttr(), nominationEndDate.before(now));
		}
		
		// whether promotion has stopped
		Date endDate = DateUtil.convertToSystemTime(pro.getPromoEdt(), DateUtil.BEIJING_TIMEZONE);
		context.put(ViewContext.IS_PROMOTION_STOP.getAttr(), endDate.before(now));
		
		// whether promotion reward deadline has expired
		Date awardEndDate = pro.getRewardDlDt();
		if (awardEndDate != null) {
			awardEndDate = DateUtil.convertToSystemTime(pro.getRewardDlDt(), DateUtil.BEIJING_TIMEZONE);
			context.put(ViewContext.IS_AWARD_END.getAttr(), awardEndDate.before(now));
		}
		
		handleInvalidSteps(pro);
		
		handleListingFields(pro, context);
		
		res.setContext(context);
		
		if (pro.getIsPreview()) {
			res.setView(ViewResource.CAMPAIGN_PREVIEW);
		} else {
			res.setView(ViewResource.CAMPAIGN);
		}
		
		return res;
	}
	
	/**
	 * Get listing fields definition form promotion and store them in context.
	 * @param promo
	 * @param context
	 */
	public void handleListingFields(Promotion promo, Map<String, Object> context){
		String fieldsDefinitions = promo.getListingFields();
		// TODO remove this test string
		fieldsDefinitions =	"[{\"sample\":null,\"required\":false,\"labelName\":\"SKU_deal\",\"isUnique\":false,\"input\":true,\"fieldtype\":{\"typeName\":\"STRING\",\"picklistEntry\":\"\",\"percision\":0,\"length\":32768,\"digits\":0},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"SKU编号\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"SKU编号\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"產SKU编号\",\"isDefault\":true}],\"display\":false,\"currencyType\":\"\",\"attachmentType\":null,\"api_Name\":\"skuId\"},{\"sample\":\"2015-12-24 24:38:56\",\"required\":false,\"labelName\":\"Stock ready Time\",\"isUnique\":false,\"input\":false,\"fieldtype\":{\"typeName\":\"STRING\",\"picklistEntry\":\"\",\"percision\":0,\"length\":100,\"digits\":0},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"SKU名称\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"SKU名称\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"SKU名称\",\"isDefault\":true}],\"display\":true,\"currencyType\":\"\",\"attachmentType\":null,\"api_Name\":\"skuName\"},{\"sample\":null,\"required\":false,\"labelName\":\"Item ID\",\"isUnique\":false,\"input\":false,\"fieldtype\":{\"typeName\":\"STRING\",\"picklistEntry\":\"\",\"percision\":0,\"length\":200,\"digits\":0},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"刊登编号\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"刊登編號\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"刊登編號\",\"isDefault\":true}],\"display\":true,\"currencyType\":\"\",\"attachmentType\":null,\"api_Name\":\"itemId\"},{\"sample\":null,\"required\":false,\"labelName\":\"Qty Available\",\"isUnique\":false,\"input\":false,\"fieldtype\":{\"typeName\":\"DOUBLE\",\"picklistEntry\":\"\",\"percision\":10,\"length\":0,\"digits\":0},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"当前价\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"当前价\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"總量\",\"isDefault\":true}],\"display\":true,\"currencyType\":\"\",\"attachmentType\":null,\"api_Name\":\"currPrice\"},{\"sample\":null,\"required\":false,\"labelName\":\"Site\",\"isUnique\":false,\"input\":false,\"fieldtype\":{\"typeName\":\"DOUBLE\",\"picklistEntry\":\"\",\"percision\":0,\"length\":100,\"digits\":0},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"活动价\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"活动价\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"活動網站\",\"isDefault\":true}],\"display\":true,\"currencyType\":\"\",\"attachmentType\":null,\"api_Name\":\"dealsPrice\"},{\"sample\":null,\"required\":false,\"labelName\":\"Listing Local Currency\",\"isUnique\":false,\"input\":false,\"fieldtype\":{\"typeName\":\"STRING\",\"picklistEntry\":\"\",\"percision\":0,\"length\":10,\"digits\":0},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"货币单位\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"貨幣單位\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"貨幣單位\",\"isDefault\":true}],\"display\":true,\"currencyType\":\"\",\"attachmentType\":null,\"api_Name\":\"currency\"},{\"sample\":null,\"required\":false,\"labelName\":\"Propose Price_Local Currency\",\"isUnique\":false,\"input\":false,\"fieldtype\":{\"typeName\":\"DOUBLE\",\"picklistEntry\":\"\",\"percision\":10,\"length\":0,\"digits\":2},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"现价\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"現價\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"現價\",\"isDefault\":true}],\"display\":true,\"currencyType\":\"Local Currency\",\"attachmentType\":null,\"api_Name\":\"proposePrice\"},{\"sample\":null,\"required\":false,\"labelName\":\"FVF take rate\",\"isUnique\":false,\"input\":false,\"fieldtype\":{\"typeName\":\"INTEGER\",\"picklistEntry\":\"\",\"percision\":10,\"length\":0,\"digits\":0},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"库存量\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"库存量\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"成交費\",\"isDefault\":true}],\"display\":true,\"currencyType\":\"\",\"attachmentType\":null,\"api_Name\":\"stockNum\"},{\"sample\":null,\"required\":false,\"labelName\":\"Final Subsidy %\",\"isUnique\":false,\"input\":false,\"fieldtype\":{\"typeName\":\"DATE\",\"picklistEntry\":\"\",\"percision\":17,\"length\":0,\"digits\":2},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"备货日期\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"最終補貼金額\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"最終補貼金額\",\"isDefault\":true}],\"display\":true,\"currencyType\":\"\",\"attachmentType\":null,\"api_Name\":\"stockReadyDate\"}]";
		
		if (fieldsDefinitions != null) {
			JsonNode tree;
			try {
				tree = mapper.readTree(fieldsDefinitions);
				if (tree != null && tree.isArray()) {
					List<ColumnConfiguration> columnConfigs = ExcelUtil.getColumnConfigurations((ArrayNode)tree, LocaleUtil.getCurrentLocale());
					context.put(ViewContext.FIELDS_DEFINITIONS.getAttr(), columnConfigs);
				}
			} catch (IOException e) {
				logger.error("ObjectMapper can't read listing fields definition from promotion.");
			}
		}
	}
	
	/**
	 * There are four invalid steps: Nomination eDM in approve flow, Nomination eDM approved, Notification eDM in approve flow, Notification eDM approved.
	 * 
	 * @param step
	 * @return if step is one of the invalid step, return false.
	 */
	private boolean isValidStep(String step) {
		return !((PromotionStep.NOMINATION_EDM_IN_APPROVE_FLOW.getName().equalsIgnoreCase(step) 
				|| PromotionStep.NOMINATION_EDM_APPROVED.getName().equalsIgnoreCase(step)
				|| PromotionStep.PROMOTION_APPROVED.getName().equalsIgnoreCase(step)
				|| PromotionStep.NOTIFICATION_EDM_IN_APPROVE_FLOW.getName().equalsIgnoreCase(step)
				|| PromotionStep.NOTIFICATION_EDM_APPROVED.getName().equalsIgnoreCase(step)));
	}
	
	/**
	 * If step list contains invalid steps, we need to filter them out and fix the current step.
	 * 
	 * @param promo
	 */
	private void handleInvalidSteps(Promotion promo) {
		/* stepList comes from SalesForce */
//		String stepList = promo.getStepList();
//		String currentStep = promo.getCurrentStep();
		String stepList = "Draft>Nomination eDM in approve flow>Nomination eDM approved>Seller nomination_Need approve>Promotion Submitted>Promotion Approved>Notification eDM in approve flow>Notification eDM approved>Seller Feedback>Promotion in progress>Promotion in validation>Promotion validated";
		String currentStep = PromotionStep.PROMOTION_APPROVED.getName();
		String[] steps = stepList.split(">");
		stepList = "";
		for (String step : steps) {
			// filter out useless steps
			if (isValidStep(step)){
				stepList += ">" + step;
			}
		}
		
		if (stepList.startsWith(">")) {
			// remove first ">"
			promo.setStepList(stepList.substring(1));
		}
		
		if (! isValidStep(currentStep)){
			boolean found = false;
			// currentStep is an invalid promotion step, we'll adjust currentStep to a former valid step.
			for (int i = steps.length - 1; i > 0; i--) {
				String step = steps[i];
				if (!currentStep.equalsIgnoreCase(step) && !found) {
					// find current step's position in original step list.
					continue;
				} else {
					found = true;
					currentStep = step;
					if (isValidStep(currentStep)) {
						break;
					} else {
						continue;
					}
				}
			}
//			promo.setCurrentStep(currentStep);
			promo.setAdjustedCurrentStep(currentStep);
			promo.setHasValidCurrentStep(isValidStep(currentStep));
		}
	}
	
	public ContextViewRes highVelocityView(Promotion pro, long uid) throws PromoException{
		ContextViewRes res = new ContextViewRes();
		Map<String, Object> context = new HashMap<String, Object>();
		context.put(ViewContext.Agreement.getAttr(), ViewResource.HV_AGGREMENT.getPath());
		res.setContext(context);
		ViewResource view = ViewResource.HV_DETAIL;
		context.put(ViewContext.Expired.getAttr(), pro.getRegEnded());
		try {
			switch(PMPromotionStatus.getByName(pro.getState())){
				case CREATED:
					context.put(ViewContext.TermsAccept.getAttr(), service.isAcceptAgreement(pro.getPromoId(), uid));
					view = ViewResource.HV_APPLICABLE;
					break;
				case APPLIED:
					context.put(ViewContext.TermsAccept.getAttr(), service.isAcceptAgreement(pro.getPromoId(), uid));
					view = ViewResource.HV_APPLIED;
					break;
				case STARTED:
				case SUBSIDY_COUNTING:
				case SUBSIDY_WAITING:
				case SUBSIDY_ACCESSED:
				case SUBSIDY_SUBMITTED:
				case SUBSIDY_UPLOADED:
				case SUBSIDY_RETRIEVABLE:
				case SUBSIDY_RESUBMITTABLE:
				case SUBSIDY_EXPIRED:
				case SUBSIDY_RETRIEVE_FAILED:
					view = ViewResource.HV_DETAIL;
					break;
				case SUBSIDY_RETRIEVED:
					view = ViewResource.HV_COMPLETED;
					break;
				case CANCELLED:
					view = ViewResource.HV_CANCELLED;
					break;
				case END:
					view = ViewResource.HV_END;
					break;
				default:
					throw new PromoException("Status " + pro.getState() + " is not supportted for this type of promotion.");
			}
		} catch (Throwable e) {
			throw new PromoException("Promotion status not valid!");
		}
		res.setView(view);
		return res;
	}
	
	public ContextViewRes dealsPresetView(Promotion pro, long uid) throws PromoException{
		ContextViewRes res = new ContextViewRes();
		Map<String, Object> context = new HashMap<String, Object>();
		context.put(ViewContext.Agreement.getAttr(), ViewResource.DP_AGGREMENT.getPath());
		context.put(ViewContext.Expired.getAttr(), pro.getRegEnded());
		ViewResource view = ViewResource.DP_DETAIL;
		try {
			switch(PMPromotionStatus.getByName(pro.getState())){
				case CREATED:
					context.put(ViewContext.FormURL.getAttr(), ViewResource.DU_CONFIRM_LISTING.getPath());
					context.put(ViewContext.TermsAccept.getAttr(), service.isAcceptAgreement(pro.getPromoId(), uid));
					view = ViewResource.DP_APPLICABLE;
					break;
				case APPLIED:
					context.put(ViewContext.FormURL.getAttr(), ViewResource.DU_CONFIRM_LISTING.getPath());
					context.put(ViewContext.TermsAccept.getAttr(), service.isAcceptAgreement(pro.getPromoId(), uid));
					view = ViewResource.DP_APPLIED;
					break;
				case STARTED:
				case SUBSIDY_COUNTING:
				case SUBSIDY_WAITING:
				case SUBSIDY_ACCESSED:
				case SUBSIDY_SUBMITTED:
				case SUBSIDY_UPLOADED:
				case SUBSIDY_RETRIEVABLE:
				case SUBSIDY_RESUBMITTABLE:
				case SUBSIDY_EXPIRED:
				case SUBSIDY_RETRIEVE_FAILED:
					view = ViewResource.DP_DETAIL;
					break;
				case SUBSIDY_RETRIEVED:
					view = ViewResource.DP_COMPLETED;
					break;
				case CANCELLED:
					view = ViewResource.DP_CANCELLED;
					break;
				case END:
					view = ViewResource.DP_END;
					break;
				default:
					throw new PromoException("Status " + pro.getState() + " is not supportted for this type of promotion.");
			}
		} catch (Throwable e) {
			throw new PromoException("Promotion status not valid!");
		}
		res.setContext(context);
		res.setView(view);
		return res;
	}
	
	public ContextViewRes dealsUpload(Promotion pro, long uid) throws PromoException{
		ContextViewRes res = new ContextViewRes();
		Map<String, Object> context = new HashMap<String, Object>();
		context.put(ViewContext.Agreement.getAttr(), ViewResource.DU_AGGREMENT.getPath());
		context.put(ViewContext.Expired.getAttr(), pro.getRegEnded());
		res.setContext(context);
		ViewResource view = ViewResource.DU_DETAIL;
		try {
			switch(PMPromotionStatus.getByName(pro.getState())){
				case CREATED:
					context.put(ViewContext.TermsAccept.getAttr(), service.isAcceptAgreement(pro.getPromoId(), uid));
					if (pro.getRegEnded()) {
						view = ViewResource.DU_END;
					} else {
						view = ViewResource.DU_APPLICABLE;
					}
					break;
				case SUBMITTED:
					context.put(ViewContext.TermsAccept.getAttr(), service.isAcceptAgreement(pro.getPromoId(), uid));
					view = ViewResource.DU_APPLIED;
					break;
				case VERIFYING:
				case PROMOTION_APPROVED:
				case APPLIED:
					context.put(ViewContext.TermsAccept.getAttr(), service.isAcceptAgreement(pro.getPromoId(), uid));
					view = ViewResource.DU_LISTING;
					break;
				case STARTED:
				case SUBSIDY_COUNTING:
				case SUBSIDY_WAITING:
				case SUBSIDY_ACCESSED:
				case SUBSIDY_SUBMITTED:
				case SUBSIDY_UPLOADED:
				case SUBSIDY_RETRIEVABLE:
				case SUBSIDY_RESUBMITTABLE:
				case SUBSIDY_EXPIRED:
				case SUBSIDY_RETRIEVE_FAILED:
					view = ViewResource.DU_DETAIL;
					break;
				case SUBSIDY_RETRIEVED:
					view = ViewResource.DU_COMPLETED;
					break;
				case CANCELLED:
					view = ViewResource.DU_CANCELLED;
					break;
				case VERIFY_FAILED:
				case END:
					view = ViewResource.DU_END;
					break;
				default:
					throw new PromoException("Status " + pro.getState() + " is not supportted for this type of promotion.");
			}
		} catch (Throwable e) {
			throw new PromoException("Promotion status not valid!");
		}
		res.setView(view);
		return res;
	}
	
	public ContextViewRes standard(Promotion pro) throws PromoException{
		ContextViewRes res = new ContextViewRes();
		Map<String, Object> context = new HashMap<String, Object>();
		context.put(ViewContext.Agreement.getAttr(), ViewResource.OTHER_AGGREMENT.getPath());
		res.setContext(context);
		ViewResource view = ViewResource.DU_APPLIED;
		try {
			switch(PMPromotionStatus.getByName(pro.getState())){
				case CREATED:
				case APPLIED:
				case STARTED:
				case SUBSIDY_COUNTING:
				case SUBSIDY_WAITING:
				case SUBSIDY_ACCESSED:
				case SUBSIDY_SUBMITTED:
				case SUBSIDY_UPLOADED:
				case SUBSIDY_RETRIEVABLE:
				case SUBSIDY_RESUBMITTABLE:
				case SUBSIDY_EXPIRED:
				case SUBSIDY_RETRIEVE_FAILED:
					view = ViewResource.OTHER_DETAIL;
					break;
				case SUBSIDY_RETRIEVED:
					view = ViewResource.OTHER_COMPLETED;
					break;
				case CANCELLED:
					view = ViewResource.OTHER_CANCELLED;
					break;
				case END:
					view = ViewResource.OTHER_END;
					break;
				default:
					throw new PromoException("Status " + pro.getState() + " is not supportted for this type of promotion.");
			}
		} catch (Throwable e) {
			throw new PromoException("Promotion status not valid!");
		}
		res.setView(view);
		return res;
	}

}
