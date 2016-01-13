package com.ebay.raptor.promotion.excel;

import java.util.List;
import java.util.Set;

import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import com.ebay.app.raptor.promocommon.excel.InvalidCellValueException;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.list.service.DealsListingService;
import com.ebay.raptor.promotion.pojo.business.FRESDealsListing;
import com.ebay.raptor.promotion.pojo.business.Sku;
import com.ebay.raptor.promotion.validation.ValidationUtil;

public class FRESDealsListingSheetHandler extends SiteDealsListingSheetHandler<FRESDealsListing> {

	public FRESDealsListingSheetHandler(SpringValidatorAdapter validator,DealsListingService dealsListingService,
			String promoId, Long userId) {
		this.validator = validator;
		this.dealsListingService = dealsListingService;
		this.promoId = promoId;
		this.userId = userId;
		this.clazz = FRESDealsListing.class;
	}

	@Override
	protected boolean validateSKU(FRESDealsListing listing, int rowIndex) throws InvalidCellValueException, PromoException {
		List<Sku> skus = dealsListingService.getSkusByPromotionId(promoId, userId);
		return ValidationUtil.validateSKU(listing.getSkuId(), listing.getSkuName(), skus, rowIndex);
	}

	@Override
	protected boolean validateItem(FRESDealsListing listing, Set<Long> itemIds, int rowIndex)
			throws InvalidCellValueException {
		return ValidationUtil.validateItem(listing.getItemId(), itemIds, rowIndex);
	}
	
	@Override
	protected void updateDealsListing(List<FRESDealsListing> listings)
			throws PromoException {
		if (listings.size() > 0) {
//			dealsListingService.uploadDealsListings(listings, promoId, userId);
		}
	}

	protected DealsListingService dealsListingService;
	private String promoId;
	private Long userId;
}
