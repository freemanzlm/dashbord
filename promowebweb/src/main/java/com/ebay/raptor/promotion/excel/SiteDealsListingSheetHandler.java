package com.ebay.raptor.promotion.excel;

import java.lang.reflect.Field;
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
import com.ebay.app.raptor.promocommon.excel.header.HeaderConfiguration;
import com.ebay.app.raptor.promocommon.excel.header.HeaderConfigurationManager;
import com.ebay.app.raptor.promocommon.util.DateUtil;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.pojo.business.Currency;
import com.ebay.raptor.promotion.pojo.business.DeliveryTime;
import com.ebay.raptor.promotion.pojo.business.Location;
import com.ebay.raptor.promotion.pojo.business.ProductCategory;
import com.ebay.raptor.promotion.pojo.business.ShipOption;
import com.ebay.raptor.promotion.pojo.business.Site;

public abstract class SiteDealsListingSheetHandler <T> extends AbstractListingSheetHandler{
	private static CommonLogger logger =
            CommonLogger.getInstance(UploadListingSheetHandler.class);

	@Override
	public void handleSheet(XSSFSheet sheet) throws CommonException {
		List<HeaderConfiguration> headerConfigs =  HeaderConfigurationManager.getHeaderConfigurations(clazz);

		parseAndValidateHeader(sheet, headerConfigs);

		List<T> listings = parseAndValidateContent(sheet, headerConfigs);
		
		if (listings != null && listings.size() > 0) {
			updateDealsListing(listings);
		} else {
			throw new InvalidCellValueException(ErrorType.EmptyListingInExcel, 0, 0, "");
		}
	}
	
	protected int parseAndValidateHeader (XSSFSheet sheet, List<HeaderConfiguration> headerConfigs) throws InvalidCellValueException {
		Row row = sheet.getRow(0);
		int cellNum = row.getPhysicalNumberOfCells();
		int headerCount = headerConfigs.size();
		
		if (cellNum != headerCount) {
			throw new InvalidCellValueException(ErrorType.InvalidHeaderCellValue,
					0, 0, "");
		}
		
		return headerCount;
	}
	
	protected List<T> parseAndValidateContent(XSSFSheet sheet,
			List<HeaderConfiguration> headerConfigs)
			throws InvalidCellValueException, PromoException,
			InvalidCellDataException {
		List<T> uploadedListings = new ArrayList<T>();
		Set<Long> itemIds = new TreeSet<Long>();
		int rowNum = sheet.getPhysicalNumberOfRows();
		String presetHeaders = getPresetColumnsString(headerConfigs);

		try {
			for (int i = 2; i < rowNum; i++) {
				Row row = sheet.getRow(i);
				List<Cell> cells = getRowCells(row, headerConfigs.size());
				
				T listing = clazz.newInstance();
				StringBuilder sb = new StringBuilder();
				
				
				for (int j = 0; j < headerConfigs.size(); j++) {
					HeaderConfiguration hc = headerConfigs.get(j);

					Field field = clazz.getDeclaredField(hc.getPropertyName());
					Cell cell = cells.get(j);
					
					setValue(cell, field, listing, sb);
				}

				validateSKU(listing, i);
				
				if (!presetHeaders.equals(sb.toString())) {
					validateObj(listing, i);
					validateItem(listing, itemIds, i);
					uploadedListings.add(listing);
				}			

			}
		} catch (InstantiationException | IllegalAccessException
				| NoSuchFieldException | SecurityException
				| UnsupportFieldDataTypeException
				| UnsupportExcelDataTypeException  e) {
			throw new PromoException(String.format("The class [%s] definition is not qualified.", clazz), e);
		}

		return uploadedListings;
	}
	
	protected abstract boolean validateSKU (T listing, int rowIndex) throws InvalidCellValueException, PromoException;
	
	protected abstract boolean validateItem(T listing, Set<Long> itemIds, int rowIndex) throws InvalidCellValueException;
	
	protected abstract void updateDealsListing(List<T> listings) throws PromoException;
	
	private String getPresetColumnsString (List<HeaderConfiguration> headerConfigs) {
		StringBuilder properties = new StringBuilder();

		for (HeaderConfiguration hc : headerConfigs) {
			if (!hc.getWritale()) {
				properties.append(hc.getPropertyName());
			}
		}
		
		return properties.toString();
	}
	
	private void setValue(Cell cell, Field field, Object obj,
			StringBuilder notNullCells) throws UnsupportFieldDataTypeException,
			UnsupportExcelDataTypeException {
		if (cell == null) {
			return;
		}
		
		int cellType = cell.getCellType();
		Class <?> fldClazz = field.getType();
		field.setAccessible(true);

		try {
			if (cellType == Cell.CELL_TYPE_BLANK) {
				return;
			} else if (cellType == Cell.CELL_TYPE_STRING) {
				String cellValue = cell.getStringCellValue();
				
				if (cellValue == null || cellValue.isEmpty()) {
					return;
				}

				if (String.class.isAssignableFrom(fldClazz)) {
					field.set(obj, cellValue);
				} else if (Integer.class.isAssignableFrom(fldClazz)) {
					field.set(obj, Integer.parseInt(cellValue));
				} else if (Long.class.isAssignableFrom(fldClazz)) {
					field.set(obj, Long.parseLong(cellValue));
				} else if (Float.class.isAssignableFrom(fldClazz)) {
					field.set(obj, Float.parseFloat(cellValue));
				} else if (Double.class.isAssignableFrom(fldClazz)) {
					field.set(obj, Double.parseDouble(cellValue));
				} else if (Boolean.class.isAssignableFrom(fldClazz)) {
					field.set(obj, Boolean.parseBoolean(cellValue));
				} else if (Date.class.isAssignableFrom(fldClazz)) {
					try {
						field.set(obj, DateUtil.parseSimpleDateWithDash(cellValue));
					} catch (ParseException e) {
						logger.error(String.format("The cell value [%s] does not match the format [%s].",
								cellValue, DateUtil.simple_date_format_dash), e);
					}
				} else if (Currency.class.isAssignableFrom(fldClazz)) {
					field.set(obj, Currency.descriptionOf(cellValue));
				} else if (DeliveryTime.class.isAssignableFrom(fldClazz)) {
					field.set(obj, DeliveryTime.descriptionOf(cellValue)); // TODO use long key
				} else if (Location.class.isAssignableFrom(fldClazz)) {
					field.set(obj, Location.descriptionOf(cellValue));
				} else if (ProductCategory.class.isAssignableFrom(fldClazz)) {
					field.set(obj, ProductCategory.descriptionOf(cellValue));
				} else if (ShipOption.class.isAssignableFrom(fldClazz)) {
					field.set(obj, ShipOption.descriptionOf(cellValue));
					// ShipOption data always has default value, thus no need to check if its null
					return;
				} else if (Site.class.isAssignableFrom(fldClazz)) {
					field.set(obj, Site.descriptionOf(cellValue));
				} else {
					throw new UnsupportFieldDataTypeException(fldClazz.getSimpleName());
				}
			} else if (cellType == Cell.CELL_TYPE_NUMERIC) {
				Double cellValue = cell.getNumericCellValue();

				if (String.class.isAssignableFrom(fldClazz)) {
					field.set(obj, String.valueOf(cellValue));
				} else if (Integer.class.isAssignableFrom(fldClazz)) {
					field.set(obj, cellValue.intValue());
				} else if (Long.class.isAssignableFrom(fldClazz)) {
					field.set(obj, cellValue.longValue());
				} else if (Float.class.isAssignableFrom(fldClazz)) {
					field.set(obj, cellValue.floatValue());
				} else if (Double.class.isAssignableFrom(fldClazz)) {
					field.set(obj, cellValue);
				} else if (Boolean.class.isAssignableFrom(fldClazz)) {
					field.set(obj, cellValue == 1 ? true : false);
				} else if (Date.class.isAssignableFrom(fldClazz)) {
					field.set(obj, new Date(cellValue.longValue()));
				} else {
					throw new UnsupportFieldDataTypeException(fldClazz.getSimpleName());
				}
			} else {
				throw new UnsupportExcelDataTypeException(cellType + "");
			}
		} catch (RuntimeException e) {
			logger.error("The field value is invalid.", e);
		} catch (IllegalAccessException e) {
			throw new UnsupportFieldDataTypeException(e, fldClazz.getSimpleName());
		}

		notNullCells.append(field.getName());
	}

	private List<Cell> getRowCells (Row row, int headerCnt) {
		List <Cell> cells = new ArrayList<Cell>();

		for (int i = 0; i < headerCnt; i++) {
			cells.add(row.getCell(i));
		}
		
		return cells;
	}

	private boolean validateObj(T obj, int rowIndex) throws InvalidCellDataException {
		Errors errors = new BeanPropertyBindingResult(obj, obj.getClass().getName());
		validator.validate(obj, errors);
        if (errors == null || errors.getAllErrors().isEmpty()) {
        	return true;
        } else {
            throw new InvalidCellDataException(errors, rowIndex + 1);
        }
	}

	protected SpringValidatorAdapter validator;
	protected Class<T> clazz;
}
