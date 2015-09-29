package com.ebay.raptor.promotion.promo.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ebay.app.raptor.promocommon.businesstype.PMPromotionStatus;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.pojo.business.Promotion;

@Component
public class PromotionViewService {
	
	@Autowired
	private PromotionService service;
	
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
					view = ViewResource.DU_APPLICABLE;
					break;
				case SUBMITTED:
					context.put(ViewContext.TermsAccept.getAttr(), service.isAcceptAgreement(pro.getPromoId(), uid));
					view = ViewResource.DU_APPLIED;
					break;
				case VERIFY_FAILED:
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
