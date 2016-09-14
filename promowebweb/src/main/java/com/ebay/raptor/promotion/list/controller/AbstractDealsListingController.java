package com.ebay.raptor.promotion.list.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ebay.app.raptor.promocommon.CommonLogger;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.list.service.DealsListingService;
import com.ebay.raptor.promotion.pojo.business.PromotionSubType;
import com.ebay.raptor.promotion.pojo.web.resp.ListDataWebResponse;

@Deprecated
public abstract class AbstractDealsListingController extends AbstractListingController {
	private static CommonLogger logger = CommonLogger.getInstance(AbstractDealsListingController.class);

	@Autowired
	protected DealsListingService service;

	// TODO - If there is no particular business logic on the meta data,
	// it's no need to convert to an object, and add the json string in response.
	protected <T> ListDataWebResponse<T> getListings (String promoId,
			Long userId, PromotionSubType promoSubType, DealsListingType listType) {
		ListDataWebResponse<T> resp = new ListDataWebResponse<T>();

		try {
			if (listType != null) {
				List<T> listings = null;

				switch (listType) {
					case Confirmed :
						listings = service.getPromotionListings(promoId, userId, promoSubType);
						break;
					case Uploaded :
						listings = service.getUploadedListings(promoId, userId, promoSubType);
						break;
					case Submitted :
						listings = service.getSubmitedListings(promoId, userId, promoSubType);
						break;
					case Applied :
						listings = service.getAppliedListings(promoId, userId, promoSubType);
						break;
					case Approved :
						listings = service.getApprovedListings(promoId, userId, promoSubType);
						break;
					default :
						resp.setStatus(Boolean.FALSE);
				}

				
				if (listings != null && listings.size() > 0) {
					resp.setData(listings);
				} else {
					logger.error("No listings found for the type: " + listType);
				}
			}
		} catch (PromoException e) {
			logger.error("Unable to get listings for type : " + listType + ", with error", e);
			resp.setStatus(Boolean.FALSE);
		}

		return resp;
	} 
	
	protected enum DealsListingType {
		Confirmed,      // PMListingVersion.VERSION_CONFIRMED, NOT PMListingStatus.Uploaded.
		Uploaded, 		// PMListingStatus.Uploaded
		Submitted,		// PMListingStatus.PRETRIAL
		Applied,		// PMListingStatus.APPLIED, PMListingStatus.PRETRIAL_PASS, PMListingStatus.PRITRIAL_FAILED
		Approved,		// PMListingStatus.AUDIT_SUCCESS, PMListingStatus.AUDIT_FAILED, PMListingStatus.PRETRIAL_PASS, PMListingStatus.PRITRIAL_FAILED
	}
}
