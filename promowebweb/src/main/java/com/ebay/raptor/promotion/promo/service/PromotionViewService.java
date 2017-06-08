package com.ebay.raptor.promotion.promo.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ebay.app.raptor.promocommon.CommonLogger;
import com.ebay.cbt.raptor.promotion.po.Promotion;
import com.ebay.raptor.promotion.enums.PromotionStep;
import com.ebay.raptor.promotion.excel.ColumnConfiguration;
import com.ebay.raptor.promotion.excel.util.ExcelUtil;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.list.controller.ListingController;
import com.ebay.raptor.promotion.util.DateUtil;
import com.ebay.raptor.promotion.util.LocaleUtil;
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
		Date now = new Date();
		
		appendRegEndCheck(context, pro, now, uid);
		appendPromoEndCheck(context, pro, now);
		appendPromoAwardEndCheck(context, pro, now);
		
		context.put(ViewContext.HAS_LISTINGS_NOMINATED.getAttr(), service.hasListingNominated(pro.getPromoId(), uid));
		
		String fieldsDefinitions = pro.getListingFields(); 
		handleListingFields(fieldsDefinitions, context, pro.getRegion());
		
		calcualteCurentStep(pro);
		
		res.setContext(context);
		
		res.setView(ViewResource.CAMPAIGN);
		
		return res;
	}
	
	/**
	 * Add a flag if promotion meets registration deadline.
	 * @param pro
	 * @param context
	 * @param now
	 * @param uid
	 */
	public void appendRegEndCheck(Map<String, Object> context, Promotion pro, Date now, long uid) {
		Date regEndDate = pro.getPromoDlDt();
		boolean isRegEnded = false;
		if (regEndDate != null) {
			regEndDate = DateUtil.convertToSystemTime(regEndDate, DateUtil.BEIJING_TIMEZONE);
			context.put(ViewContext.IS_REG_END.getAttr(), isRegEnded = regEndDate.before(now));
		}
		
		if (!isRegEnded) {
			// Enroll and confirm need to check if user has accept the terms. 
			context.put(ViewContext.TermsAccept.getAttr(), service.isAcceptAgreement(pro.getPromoId(), uid));
		}
	}
	
	/**
	 * Add a flag if promotion has ended.
	 * @param context
	 * @param pro
	 * @param now
	 * @param uid
	 */
	public void appendPromoEndCheck(Map<String, Object> context, Promotion pro, Date now) {
		// whether promotion has stopped
		Date endDate = DateUtil.convertToSystemTime(pro.getPromoEdt(), DateUtil.BEIJING_TIMEZONE);
		context.put(ViewContext.IS_PROMOTION_STOP.getAttr(), endDate.before(now));
	}
	
	/**
	 * Calculate if promotion meet award deadline.
	 * @param pro
	 * @param context
	 * @param uid
	 */
	public void appendPromoAwardEndCheck(Map<String, Object> context, Promotion pro, Date now) {

		// whether promotion reward deadline has expired
		Date awardEndDate = pro.getRewardDlDt();
		if (awardEndDate != null) {
			awardEndDate = DateUtil.convertToSystemTime(pro.getRewardDlDt(), DateUtil.BEIJING_TIMEZONE);
			context.put(ViewContext.IS_AWARD_END.getAttr(), awardEndDate.before(now));
		}
	}
	
	public void calcualteCurentStep(Promotion pro) {
		// Promotion current step may be not an visible step, we need to adjust it. 
		handleCurrentStep(pro, pro.getCurrentStep());
		
		// if promotion is in draft step, it may be a preview-able promotion.
		handleDraftPromotion(pro);
		
		// We only leave visible step list for promotion display.
		String visibleStepList = getVisibleStepList(pro.getStepList());
		pro.setStepList(visibleStepList.toUpperCase());
	}
	
	/**
	 * Get listing fields definition form promotion and store them in context.
	 * @param promo
	 * @param context
	 */
	public void handleListingFields(String fieldsDefinitions, Map<String, Object> context, String region){
		if (fieldsDefinitions != null) {
			JsonNode tree;
			try {
				tree = mapper.readTree(fieldsDefinitions);
				if (tree != null && tree.isArray()) {
					List<ColumnConfiguration> columnConfigs = ExcelUtil.getColumnConfigurations((ArrayNode)tree, LocaleUtil.getLocale(region));
					context.put(ViewContext.FIELDS_DEFINITIONS.getAttr(), columnConfigs);
				}
			} catch (IOException e) {
				logger.error("ObjectMapper can't read listing fields definition from promotion.");
			}
		}
	}
	
	/**
	 * CurrentStep may is not a visible step in Dashboard, so we need to deduce the visible step forward.
	 * 
	 * @param promo
	 */
	public String getVisibleCurrentStep(String stepList, String currentStep) {
		if (stepList != null) {
			String[] steps = stepList.split(">");
			
			if (! isVisibleStep(currentStep)){
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
						if (isVisibleStep(currentStep)) {
							break;
						} else {
							continue;
						}
					}
				}
			}
		}
		return currentStep;
	}
	
	public int getIndexOfStep(String step, String promoSteps) {
		String[] steps = promoSteps.split(">");
		for(int i=0;i<steps.length;i++) {
			if(step.equalsIgnoreCase(steps[i])) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Get visible step list from promotion step list.
	 * @param stepList
	 * @return
	 */
	public String getVisibleStepList(String stepList) {
		String visibleStepList = "";
		
		if (stepList != null) {
			String[] steps = stepList.split(">");
			stepList = "";
			for (String step : steps) {
				// filter out invisible steps
				if (isVisibleStep(step)){
					stepList += ">" + step;
				}
			}
			
			if (stepList.startsWith(">")) {
				// remove first ">"
				visibleStepList = stepList.substring(1);
			}
		}
		
		return filterStepList(visibleStepList);
	}
	
	/**
	 * filter steplist.
	 * @return
	 */
	public String filterStepList(String stepList) {
		List<String> list = new LinkedList(Arrays.asList(stepList.split(">")));
		if(!list.contains(PromotionStep.SELLER_NOMINATION_NEED_APPROVE.getName())
				&&list.contains(PromotionStep.PROMOTION_SUBMITTED.getName())) {
			list.remove(PromotionStep.PROMOTION_SUBMITTED.getName());
		}
		return StringUtils.join(list, ">");
	}
	
	
	/**
	 * There are four not visible steps: Nomination eDM in approve flow, Nomination eDM approved, Notification eDM in approve flow, Notification eDM approved.
	 * 
	 * @param step
	 * @return if step is one of the invalid step, return false.
	 */
	private boolean isVisibleStep(String step) {
		return !((PromotionStep.NOMINATION_EDM_IN_APPROVE_FLOW.getName().equalsIgnoreCase(step) 
				|| PromotionStep.NOMINATION_EDM_APPROVED.getName().equalsIgnoreCase(step)
				|| PromotionStep.PROMOTION_APPROVED.getName().equalsIgnoreCase(step)
				|| PromotionStep.NOTIFICATION_EDM_IN_APPROVE_FLOW.getName().equalsIgnoreCase(step)
				|| PromotionStep.NOTIFICATION_EDM_APPROVED.getName().equalsIgnoreCase(step)));
	}
	
	/**
	 * Only promotion in draft step can have preview state.
	 * @param promo
	 */
	private void handleDraftPromotion(Promotion promo) {
		//if (PromotionStep.DRAFT.getName().equalsIgnoreCase(promo.getCurrentStep())) {
			if (promo.getIsPreview() != null && promo.getIsPreview() == true) {
				// use preview step as the current step if this promotion is in draft state.
				handleCurrentStep(promo, promo.getDraftPreviewStep());
			}
		//}
	}
	
	/**
	 * If current step is not a visible state, adjust it to a visible state.
	 * @param promo
	 * @param currentStep
	 */
	private void handleCurrentStep(Promotion promo, String currentStep) {
		String visibleCurrentStep = getVisibleCurrentStep(promo.getStepList(), currentStep);
		if (isVisibleStep(visibleCurrentStep)) {
			promo.setVisibleCurrentStep(visibleCurrentStep.toUpperCase());
			promo.setHasValidCurrentStep(true);
		} else {
			promo.setHasValidCurrentStep(false);
		}
	}
	
}
