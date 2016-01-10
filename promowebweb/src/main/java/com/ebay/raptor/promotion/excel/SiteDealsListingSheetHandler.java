package com.ebay.raptor.promotion.excel;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import com.ebay.app.raptor.promocommon.CommonException;
import com.ebay.app.raptor.promocommon.CommonLogger;
import com.ebay.app.raptor.promocommon.error.ErrorType;
import com.ebay.app.raptor.promocommon.excel.InvalidCellValueException;
import com.ebay.app.raptor.promocommon.excel.header.Header;
import com.ebay.app.raptor.promocommon.excel.header.HeaderConfiguration;
import com.ebay.app.raptor.promocommon.excel.header.HeaderConfigurationManager;
import com.ebay.app.raptor.promocommon.export.HeaderProcessor;
import com.ebay.app.raptor.promocommon.util.DateUtil;
import com.ebay.app.raptor.promocommon.util.StringUtil;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.list.service.DealsListingService;
import com.ebay.raptor.promotion.pojo.business.APACDealsListing;
import com.ebay.raptor.promotion.pojo.business.Currency;
import com.ebay.raptor.promotion.pojo.business.DealsListing;
import com.ebay.raptor.promotion.pojo.business.DeliveryTime;
import com.ebay.raptor.promotion.pojo.business.FRESDealsListing;
import com.ebay.raptor.promotion.pojo.business.GBHDealsListing;
import com.ebay.raptor.promotion.pojo.business.Location;
import com.ebay.raptor.promotion.pojo.business.ProductCategory;
import com.ebay.raptor.promotion.pojo.business.PromotionSubType;
import com.ebay.raptor.promotion.pojo.business.ShipOption;
import com.ebay.raptor.promotion.pojo.business.Site;
import com.ebay.raptor.promotion.pojo.business.SiteDealsListing;
import com.ebay.raptor.promotion.pojo.business.Sku;

public class SiteDealsListingSheetHandler extends AbstractListingSheetHandler{
	private static CommonLogger logger =
            CommonLogger.getInstance(UploadListingSheetHandler.class);
	
	public SiteDealsListingSheetHandler(DealsListingService dealsListingService,
			String promoId, Long userId) {
		this.dealsListingService = dealsListingService;
		this.promoId = promoId;
		this.userId = userId;
	}

	@Override
	public void handleSheet(XSSFSheet sheet) throws CommonException {
		Class<? extends SiteDealsListing> clazz = null;
		
		if (proSubType == PromotionSubType.GBH) {
			clazz = GBHDealsListing.class;
		} else if (proSubType == PromotionSubType.FRES) {
//			clazz = FRESDealsListing.class;
		} else if (proSubType == PromotionSubType.APAC) {
//			clazz = APACDealsListing.class;
		} else {
			// TODO - 
		}

		readHeader(sheet, clazz);
		readContent(sheet, clazz);
	}
	
	private void readHeader (XSSFSheet sheet, Class<?> clazz) throws InvalidCellValueException {
		
		
		List<HeaderConfiguration> headerConfigs =  HeaderConfigurationManager.getHeaderConfigurations(clazz);
		
		int headerCount = headerConfigs.size();
		
		Row row = sheet.getRow(0);
		int cellNum = row.getPhysicalNumberOfCells();
		
		if (cellNum != headerCount) {
			throw new InvalidCellValueException(ErrorType.InvalidHeaderCellValue,
					0, 0, "");
		}
	}
	
	private void readContent (XSSFSheet sheet, Class<? extends SiteDealsListing> clazz)
			throws InvalidCellValueException, PromoException, InvalidCellDataException {
		try {
			validateListing(sheet, clazz);
		} catch (InstantiationException
				| IllegalAccessException | NoSuchFieldException
				| SecurityException | IllegalArgumentException
				| InvocationTargetException | IntrospectionException e) {
			throw new PromoException(String.format("The class [%s] definition is not qualified.", clazz), e);
		}
	}
	
	private void validateListing (XSSFSheet sheet, Class<? extends SiteDealsListing> clazz) throws PromoException, IntrospectionException, InvalidCellValueException, InvalidCellDataException, InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException, InvocationTargetException {
		List<Sku> skus = dealsListingService.getSkusByPromotionId(promoId, userId);
		
		List<SiteDealsListing> uploadedListings = new ArrayList<SiteDealsListing>();
		Set<Long> itemIds = new TreeSet<Long>();
		int rowNum = sheet.getPhysicalNumberOfRows();
		BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
		PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
		
		Field [] fields = clazz.getDeclaredFields();
		System.out.println(String.format("Fields: %s", fields.toString()));

		for (int i = 1; i < rowNum; i++) {
			Row row = sheet.getRow(i);
			List<Cell> cells = getRowCells(row);
			
			SiteDealsListing listing = clazz.newInstance();
			StringBuilder sb = new StringBuilder();
			System.out.println("=======================");
			
			for (PropertyDescriptor pd : pds) {
				String pdName = pd.getDisplayName();
				Method setter = pd.getWriteMethod();
				
				
				System.out.println(String.format("property name: %s", pdName));
				

				Field f = clazz.getDeclaredField(pdName);
				Header h = f.getAnnotation(Header.class);
				
					
				if (h != null) {
					setValue(cells.get(h.order()), f.getType(), setter, listing, sb);
				}
			}
			
			System.out.println("=======================");
			
//			validateSKU(listing, skus, i);
			
			if (!"01".equals(sb)) {
				validateObj(listing, i);
				validateItem(listing, itemIds, i);
				uploadedListings.add(listing);
			}			

		}
		
		if (uploadedListings.size() > 0) {
//			dealsListingService.uploadDealsListings(uploadedListings, promoId, userId);
		} else {
			throw new InvalidCellValueException(ErrorType.EmptyListingInExcel, 0, 0, "");
		}
	}
	
	private void setValue(Cell cell, Class<?> fldClazz, Method setter, Object obj, StringBuilder notNullCells)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, UnsupportExcelDataTypeException, UnsupportFieldDataTypeException {
		int cellType = cell.getCellType();

		if (cellType == Cell.CELL_TYPE_BLANK) {
			return;
		} else if (cellType == Cell.CELL_TYPE_STRING) {
			String cellValue = cell.getStringCellValue();

			if (String.class.isAssignableFrom(fldClazz)) {
				setter.invoke(obj, cellValue);
			} else if (Integer.class.isAssignableFrom(fldClazz)) {
				setter.invoke(obj, Integer.parseInt(cellValue));
			} else if (Long.class.isAssignableFrom(fldClazz)) {
				setter.invoke(obj, Long.parseLong(cellValue));
			} else if (Float.class.isAssignableFrom(fldClazz)) {
				setter.invoke(obj, Float.parseFloat(cellValue));
			} else if (Double.class.isAssignableFrom(fldClazz)) {
				setter.invoke(obj, Double.parseDouble(cellValue));
			} else if (Boolean.class.isAssignableFrom(fldClazz)) {
				setter.invoke(obj, Boolean.parseBoolean(cellValue));
			} else if (Date.class.isAssignableFrom(fldClazz)) {
				try {
					setter.invoke(obj, DateUtil.parseSimpleDateWithDash(cellValue));
				} catch (ParseException e) {
					logger.error(String.format("The cell value [%s] does not match the format [%s].",
							cellValue, DateUtil.simple_date_format_dash), e);
				}
			} else if (Currency.class.isAssignableFrom(fldClazz)) {
				setter.invoke(obj, Currency.valueOf(cellValue));
			} else if (DeliveryTime.class.isAssignableFrom(fldClazz)) {
				setter.invoke(obj, DeliveryTime.valueOf(cellValue)); // TODO use long key
			} else if (Location.class.isAssignableFrom(fldClazz)) {
				setter.invoke(obj, Location.valueOf(cellValue));
			} else if (ProductCategory.class.isAssignableFrom(fldClazz)) {
				setter.invoke(obj, ProductCategory.valueOf(cellValue));
			} else if (ShipOption.class.isAssignableFrom(fldClazz)) {
				setter.invoke(obj, ShipOption.valueOf(cellValue));
			} else if (Site.class.isAssignableFrom(fldClazz)) {
				setter.invoke(obj, Site.valueOf(cellValue));
			} else {
				throw new UnsupportFieldDataTypeException(fldClazz.getSimpleName());
			}
		} else if (cellType == Cell.CELL_TYPE_NUMERIC) {
			Double cellValue = cell.getNumericCellValue();

			if (String.class.isAssignableFrom(fldClazz)) {
				setter.invoke(obj, String.valueOf(cellValue));
			} else if (Integer.class.isAssignableFrom(fldClazz)) {
				setter.invoke(obj, cellValue.intValue());
			} else if (Long.class.isAssignableFrom(fldClazz)) {
				setter.invoke(obj, cellValue.longValue());
			} else if (Float.class.isAssignableFrom(fldClazz)) {
				setter.invoke(obj, cellValue.floatValue());
			} else if (Double.class.isAssignableFrom(fldClazz)) {
				setter.invoke(obj, cellValue);
			} else if (Boolean.class.isAssignableFrom(fldClazz)) {
				setter.invoke(obj, cellValue == 1 ? true : false);
			} else if (Date.class.isAssignableFrom(fldClazz)) {
				setter.invoke(obj, new Date(cellValue.longValue()));
			} else {
				throw new UnsupportFieldDataTypeException(fldClazz.getSimpleName());
			}
		} else {
			throw new UnsupportExcelDataTypeException(cellType + "");
		}
		
		notNullCells.append(cell.getColumnIndex());
	}
	
	private List<Cell> getRowCells (Row row) {
		List <Cell> cells = new ArrayList<Cell>();

		int cellNum = row.getPhysicalNumberOfCells(); // TODO - verify if cell is empty

		for (int i = 0; i < cellNum; i++) {
			cells.add(row.getCell(i));
		}
		
		return cells;
	}
	
	private boolean validateSKU (SiteDealsListing obj, List<Sku> skus, int rowIndex) throws InvalidCellValueException {
		String skuId = obj.getSkuId();
		String skuName = obj.getSkuName();
		boolean foundSku = false;

		for (Sku sku : skus) {
			String storedSkuId = sku.getSkuId();
			String storedSkuName = sku.getName();
			if (skuId.equalsIgnoreCase(storedSkuId)
					&& skuName.equalsIgnoreCase(storedSkuName)) {
				foundSku = true;
				break;
			}
		}

		if (!foundSku) {
			// sku id is for internal using only, and user cares about sku name actually.
			throw new InvalidCellValueException(ErrorType.InvalidSkuCellValue,
					rowIndex, 0, skuName);
		}

		return foundSku;
	}
	
	private boolean validateItem(SiteDealsListing obj, Set<Long> itemIds,
			int rowIndex) throws InvalidCellValueException {
		Long itemId = obj.getItemId();

		if (!itemIds.add(itemId)) {
			throw new InvalidCellValueException(ErrorType.DuplicateItemFound,
					rowIndex, 0, itemId + "");
		}

		return true;
	}
	
	private <T> T validateObj (T obj, int rowIndex) throws InvalidCellDataException {
		Errors errors = new BeanPropertyBindingResult(obj, obj.getClass().getName());
		validator.validate(obj, errors);
        if (errors == null || errors.getAllErrors().isEmpty())
            return obj;
        else {
        	logger.error(errors.toString());
            throw new InvalidCellDataException(errors, rowIndex);
        }
	}

	private DealsListingService dealsListingService;
	private String promoId;
	private Long userId;
	private PromotionSubType proSubType;
	private List<HeaderConfiguration> headerConfigs;
	private SpringValidatorAdapter validator;
}
