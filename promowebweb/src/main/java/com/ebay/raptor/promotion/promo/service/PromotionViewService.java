package com.ebay.raptor.promotion.promo.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ebay.app.raptor.promocommon.CommonException;
import com.ebay.app.raptor.promocommon.businesstype.PMPromotionStatus;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.pojo.business.Promotion;

@Component
public class PromotionViewService {
	
	public ContextViewRes highVelocityView(Promotion pro) throws PromoException{
		ContextViewRes res = new ContextViewRes();
		Map<String, Object> context = new HashMap<String, Object>();
		res.setContext(context);
		ViewResource view = ViewResource.HV_DETAIL;
		try {
			switch(PMPromotionStatus.getById(pro.getState())){
				case CREATED:
					view = ViewResource.HV_APPLICABLE;
					break;
				case APPLIED:
					view = ViewResource.HV_APPLIED;
					break;
				case STARTED:
				case SUBSIDY_COUNTING:
				case SUBSIDY_WAITING:
				case NEED_AGREEMENT:
				case SUBSIDY_VREIFYING:
				case COMPLETED:
				case CLAIM_FAIL:
					view = ViewResource.HV_DETAIL;
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
		} catch (CommonException e) {
			throw new PromoException("Promotion status not valid!");
		}
		res.setView(view);
		return res;
	}
	
	public ContextViewRes dealsPresetView(Promotion pro) throws PromoException{
		ContextViewRes res = new ContextViewRes();
		Map<String, Object> context = new HashMap<String, Object>();
		res.setContext(context);
		ViewResource view = ViewResource.HV_DETAIL;
		try {
			switch(PMPromotionStatus.getById(pro.getState())){
				case CREATED:
					view = ViewResource.DP_APPLICABLE;
					break;
				case APPLIED:
					view = ViewResource.DP_APPLIED;
					break;
				case STARTED:
				case SUBSIDY_COUNTING:
				case SUBSIDY_WAITING:
				case NEED_AGREEMENT:
				case SUBSIDY_VREIFYING:
				case COMPLETED:
				case CLAIM_FAIL:
					view = ViewResource.DP_DETAIL;
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
		} catch (CommonException e) {
			throw new PromoException("Promotion status not valid!");
		}
		res.setView(view);
		return res;
	}
	
	public ContextViewRes dealsUpload(Promotion pro) throws PromoException{
		ContextViewRes res = new ContextViewRes();
		Map<String, Object> context = new HashMap<String, Object>();
		res.setContext(context);
		ViewResource view = ViewResource.DU_DETAIL;
		try {
			switch(PMPromotionStatus.getById(pro.getState())){
				case CREATED:
					view = ViewResource.DU_APPLICABLE;
					break;
				case VERIFYING:
				case PROMOTION_APPROVED:
					view = ViewResource.DU_LISTING_PREVIEW;
					break;
				case APPLIED:
					view = ViewResource.DU_APPLIED;
				case SUBMITTED:
				case VERIFY_FAILED:
				case STARTED:
				case SUBSIDY_COUNTING:
				case SUBSIDY_WAITING:
				case NEED_AGREEMENT:
				case SUBSIDY_VREIFYING:
				case COMPLETED:
				case CLAIM_FAIL:
					view = ViewResource.DU_DETAIL;
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
		} catch (CommonException e) {
			throw new PromoException("Promotion status not valid!");
		}
		res.setView(view);
		return res;
	}
	
	public ContextViewRes standard(Promotion pro) throws PromoException{
		ContextViewRes res = new ContextViewRes();
		Map<String, Object> context = new HashMap<String, Object>();
		res.setContext(context);
		ViewResource view = ViewResource.DU_APPLIED;
		try {
			switch(PMPromotionStatus.getById(pro.getState())){
				case CREATED:
				case APPLIED:
				case STARTED:
				case SUBSIDY_COUNTING:
				case SUBSIDY_WAITING:
				case NEED_AGREEMENT:
				case SUBSIDY_VREIFYING:
				case COMPLETED:
				case CLAIM_FAIL:
					view = ViewResource.OTHER_DETAIL;
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
		} catch (CommonException e) {
			throw new PromoException("Promotion status not valid!");
		}
		res.setView(view);
		return res;
	}

}
