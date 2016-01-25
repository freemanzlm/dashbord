package com.ebay.raptor.promotion.excel;

import java.util.List;
import java.util.Set;

import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import com.ebay.app.raptor.promocommon.excel.InvalidCellValueException;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.list.service.DealsListingService;
import com.ebay.raptor.promotion.pojo.business.GBHDealsListing;
import com.ebay.raptor.promotion.pojo.business.Sku;
import com.ebay.raptor.promotion.validation.ValidationUtil;

public class GBHDealsListingSheetHandler extends SiteDealsListingSheetHandler<GBHDealsListing> {

	public GBHDealsListingSheetHandler(SpringValidatorAdapter validator,DealsListingService dealsListingService,
			String promoId, Long userId) {
		this.validator = validator;
		this.dealsListingService = dealsListingService;
		this.promoId = promoId;
		this.userId = userId;
		this.clazz = GBHDealsListing.class;
	}

	@Override
	protected boolean validateSKU(GBHDealsListing listing, int rowIndex) throws InvalidCellValueException, PromoException {
		List<Sku> skus = dealsListingService.getSkusByPromotionId(promoId, userId);
		return ValidationUtil.validateSKU(listing.getSkuId(), listing.getSkuName(), skus, rowIndex);
	}

	@Override
	protected boolean validateItem(GBHDealsListing listing, Set<Long> itemIds, int rowIndex)
			throws InvalidCellValueException {
		return ValidationUtil.validateItem(listing.getItemId(), itemIds, rowIndex);
	}
	
	@Override
	protected void updateDealsListing(List<GBHDealsListing> listings)
			throws PromoException {
		if (listings.size() > 0) {
			dealsListingService.uploadDealsListings(listings, promoId, userId);
		}
	}

	protected DealsListingService dealsListingService;
	private String promoId;
	private Long userId;
}
