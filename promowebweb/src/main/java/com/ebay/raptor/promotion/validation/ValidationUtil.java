package com.ebay.raptor.promotion.validation;

import java.util.List;
import java.util.Set;

import com.ebay.app.raptor.promocommon.error.ErrorType;
import com.ebay.app.raptor.promocommon.excel.InvalidCellValueException;
import com.ebay.app.raptor.promocommon.util.StringUtil;
import com.ebay.cbt.raptor.promotion.po.Sku;

public class ValidationUtil {

	public static boolean validateSKU(String skuId, String skuName,
			List<Sku> skus, int rowIndex) throws InvalidCellValueException {
		if (StringUtil.isEmpty(skuId) || StringUtil.isEmpty(skuName)
				|| skus == null || skus.size() <= 0) {
			return false;
		}
		
		for (Sku sku : skus) {
			String storedSkuId = sku.getSkuId();
			String storedSkuName = sku.getName();
			if (skuId.equalsIgnoreCase(storedSkuId)
					&& skuName.equalsIgnoreCase(storedSkuName)) {
				return true;
			}
		}

		throw new InvalidCellValueException(ErrorType.InvalidSkuCellValue,
				rowIndex + 1, 0, skuName);
	}
	
	public static boolean validateItem(Long itemId, Set<Long> itemIds,
			int rowIndex) throws InvalidCellValueException {
		if (itemId == null || itemIds == null) {
			return false;
		}

		if (itemIds.add(itemId)) {
			return true;
		}
		
		throw new InvalidCellValueException(ErrorType.DuplicateItemFound,
				rowIndex + 1, 0, itemId + "");		
	}
}
