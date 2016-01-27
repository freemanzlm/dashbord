package com.ebay.raptor.promotion.excel.writer;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.support.ResourceBundleMessageSource;

import com.ebay.app.raptor.promocommon.CommonLogger;
import com.ebay.app.raptor.promocommon.excel.header.HeaderConfiguration;
import com.ebay.app.raptor.promocommon.excel.header.HeaderConfigurationManager;
import com.ebay.app.raptor.promocommon.util.DateUtil;
import com.ebay.raptor.promotion.list.controller.DealsListingController;
import com.ebay.raptor.promotion.pojo.business.APACDealsListing;
import com.ebay.raptor.promotion.pojo.business.Currency;
import com.ebay.raptor.promotion.pojo.business.DeliveryTime;
import com.ebay.raptor.promotion.pojo.business.FRESDealsListing;
import com.ebay.raptor.promotion.pojo.business.IDescription;
import com.ebay.raptor.promotion.pojo.business.Location;
import com.ebay.raptor.promotion.pojo.business.ProductCategory;
import com.ebay.raptor.promotion.pojo.business.ShipOption;
import com.ebay.raptor.promotion.pojo.business.Site;

public class ExcelSheetWriter <T> extends Writer<T>{
	private static CommonLogger logger = CommonLogger.getInstance(ExcelSheetWriter.class);

	private XSSFWorkbook workBook;
	private String sheetName;
	private CellStyle headerStyle;
	private CellStyle bodyStyle;
	private CellStyle unlockedHeaderStyle;
	private CellStyle unlockedBodyStyle;
	private CellStyle dateCellStyle;
	private List<List<String>> params;
	private ResourceBundleMessageSource messageSource;

	public ExcelSheetWriter(Class<?> clazz, XSSFWorkbook workBook, String sheetName, ResourceBundleMessageSource messageSource){
		super(clazz);
		this.workBook = workBook;
		this.sheetName = sheetName;
		this.messageSource = messageSource;
		buildMetadata(workBook);
	}

	public ExcelSheetWriter(Class<?> clazz) {
		super(clazz);
		this.workBook = new XSSFWorkbook();
		this.sheetName = "Default";
		buildMetadata(workBook);
	}

	@Override
	public void resetHeaders() {
		HeaderConfigurationManager.reorderHeaders(this.configurations);
	}

	public void build (List<T> elements, int freezeRows, int freezeCols,
			int freezeVisibleRow, int freezeVisibleCol,int hiddenCol, String password) {
		Sheet sheet = workBook.createSheet(sheetName);
		buildHeader(sheet);
		initSelectedValue();
		buildContent(elements, sheet);
		autoSizeColumn(sheet, false);
		updateStyle(sheet, freezeRows, freezeCols, freezeVisibleRow, freezeVisibleCol, hiddenCol, password);
	}
	
	public void initSelectedValue() {
		Sheet hiddenSheet = workBook.createSheet("hiddenSheet");
		this.params = new ArrayList<>();
		for(int i=0;i<configurations.size();i++) {
			params.add(null);
		}
		for(int i=0; i<configurations.size(); i++) {
			Class<?> dataType = configurations.get(i).getDataType();
			if(Enum.class.isAssignableFrom(dataType)) {
				List<String> list = new ArrayList<>();
				if(Site.class.equals(dataType)) {
					for(Site site : EnumSet.allOf(Site.class)) {
						list.add(site.getDescription());
					}
				} else if(ProductCategory.class.equals(dataType)) {
					for(ProductCategory category : EnumSet.allOf(ProductCategory.class)) {
						list.add(category.getDescription());
					}
				} else if(Currency.class.equals(dataType)) {
					if(this.clazz.equals(FRESDealsListing.class)) {
						list.add(Currency.USD.getDescription());
						list.add(Currency.AUD.getDescription());
						list.add(Currency.EUR.getDescription());
						list.add(Currency.GBP.getDescription());
					} else if(this.clazz.equals(APACDealsListing.class)) {
						list.add(Currency.USD.getDescription());
						list.add(Currency.AUD.getDescription());
						list.add(Currency.CAD.getDescription());
					} else {
						for(Currency currency : EnumSet.allOf(Currency.class)) {
							list.add(currency.getDescription());
						}
					} 
				} else if(DeliveryTime.class.equals(dataType)) {
					for(DeliveryTime time : EnumSet.allOf(DeliveryTime.class)) {
						list.add(time.getDescription());
					}
				} else if(Location.class.equals(dataType)) {
					for(Location location : EnumSet.allOf(Location.class)) {
						list.add(location.getDescription());
					}
				} else if(ShipOption.class.equals(dataType)) {
					for(ShipOption ship : EnumSet.allOf(ShipOption.class)) {
						list.add(ship.getDescription());
					}
				}
				params.add(i, list);
			}
		}
		
		for (int i = 0; i < params.size(); i++) {
			Row row = hiddenSheet.createRow(i);
			if(params.get(i)==null) {
				continue;
			}
			for(int j=0; j<params.get(i).size(); j++) {
				String name = params.get(i).get(j);
				Cell cell = row.createCell(j);
				cell.setCellValue(name);
			}
		}
		workBook.setSheetHidden(1, true);
	}
	
	public void buildHeader (Sheet sheet) {
		Row header = sheet.createRow(0);
		int num = 0;
		Locale locale = Locale.SIMPLIFIED_CHINESE;
		for (HeaderConfiguration headerConfig : configurations) {
			String headerText = messageSource.getMessage(headerConfig.getTitle(), null, locale);
			addCell(headerText, String.class, num++, header, true, false);
		}
		header = sheet.createRow(1);
		num = 0;
		locale = Locale.ENGLISH;
		for (HeaderConfiguration headerConfig : configurations) {
			String headerText = messageSource.getMessage(headerConfig.getTitle(), null, locale);
			addCell(headerText, String.class, num++, header, true, false);
		}
	}
	
	public void buildContent (List<T> elements, Sheet sheet) {
		if (elements != null && elements.size() > 0) {
			int rowIndex = 2;
			for (T ele : elements) {
				Row row = sheet.createRow(rowIndex++);
				addRow(ele, row, false);
			}
		}
	}

	protected void addRow(T obj, Row row, boolean isHeader){
		int num = 0;

		for (int i=0; i<configurations.size(); i++) {
			HeaderConfiguration headerConfig = configurations.get(i);
			String fieldName = headerConfig.getPropertyName();

			try {
				Object fieldVal = PropertyUtils.getProperty(obj, fieldName);
				Class <?> type = PropertyUtils.getPropertyType(obj, fieldName);
				addCell(fieldVal, type, num++, row, false, headerConfig.getWritale());
			} catch (IllegalAccessException | InvocationTargetException
					| NoSuchMethodException e) {
				// ignore...
			}
		}
	}

	protected void addCell(Object cellData, Class <?> type, int cellIndex, Row row, boolean isHeader, boolean writable){
		Cell cl = row.createCell(cellIndex);
		CellStyle celStyle = null;
		Name namedCell = null;
		DataValidationHelper dvHelper = null;
		DataValidationConstraint constraint = null;
		CellRangeAddressList addressList = null;
		
		if (Number.class.isAssignableFrom(type)) {
			cl.setCellType(Cell.CELL_TYPE_NUMERIC);
			if (cellData != null) {
				cl.setCellValue(Double.parseDouble(cellData.toString()));
			}
		} else if (Boolean.class.isAssignableFrom(type)) {
			cl.setCellType(Cell.CELL_TYPE_BOOLEAN);
			if (cellData != null) {
				cl.setCellValue(Boolean.parseBoolean(cellData.toString()));
			}
		} else if (Date.class.isAssignableFrom(type)) {
			celStyle = this.dateCellStyle;

			try {
				if(cellData != null) {
					cl.setCellValue(DateUtil.parseSimpleDateWithDash(cellData.toString()));
				}
			} catch (ParseException e) {
				// ignore...
			}
		} else if(Enum.class.isAssignableFrom(type)) {
			Sheet hiddenSheet = workBook.getSheet("hiddenSheet");
			char endCh = (char) ('A'+params.get(cellIndex).size()-1);
			namedCell = workBook.getName("hidden"+cellIndex);
			if(namedCell==null) {
				namedCell = workBook.createName();
			}
			namedCell.setNameName("hidden"+cellIndex);
			namedCell.setRefersToFormula(hiddenSheet.getSheetName()+"!$A$"+(cellIndex+1)+":$"+endCh+"$" + (cellIndex+1));
			dvHelper = hiddenSheet.getDataValidationHelper();
			constraint = dvHelper.createFormulaListConstraint("hidden"+cellIndex);
			addressList = new CellRangeAddressList(row.getRowNum(), row.getRowNum(), cellIndex, cellIndex);
			DataValidation validation = dvHelper.createValidation(constraint, addressList);
			workBook.getSheet(this.sheetName).addValidationData(validation);

			try {
				cl.setCellValue(((IDescription)cellData).getDescription());
			} catch (Exception e) {
//				logger.error(String.format("Unable to add cell value [%s], type [%s] ", cellData, type), e);
			}
		} else {
			cl.setCellType(Cell.CELL_TYPE_STRING);
			if (cellData != null) {
				cl.setCellValue((String) cellData);
			}
		}
		
		if (celStyle == null) {
			if(isHeader){
				celStyle = writable ? this.unlockedHeaderStyle : this.headerStyle;
			} else {
				celStyle = writable ? this.unlockedBodyStyle : this.bodyStyle;
			}
		}

		cl.setCellStyle(celStyle);
	}

	protected void autoSizeColumn(Sheet st, boolean isAutoFilter){
		Row firstRow = st.getRow(0);
		int colNum = 0;
		if(null != firstRow){
			colNum = firstRow.getLastCellNum();
		}
		if(isAutoFilter){
			CellRangeAddress cra = new CellRangeAddress(0, 0, 0, colNum-1);
			st.setAutoFilter(cra);
		}
		
		for(;colNum > 0; colNum--){
//			st.autoSizeColumn(colNum-1, true);
			st.setColumnWidth(colNum-1, 10 * 512 );
		}
	}
	
	protected void buildMetadata (XSSFWorkbook workBook) {
		this.headerStyle = workBook.createCellStyle();
		//Set cell style
		this.headerStyle.setAlignment(CellStyle.ALIGN_CENTER);
		this.headerStyle.setFillForegroundColor(IndexedColors.LIME.getIndex());
		this.headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		this.headerStyle.setWrapText(true);
		
		this.unlockedHeaderStyle = workBook.createCellStyle();
		unlockedHeaderStyle.cloneStyleFrom(headerStyle);
		unlockedHeaderStyle.setLocked(false);

		Font ft = workBook.createFont();
		ft.setFontName("Arial");
		ft.setBoldweight(Font.BOLDWEIGHT_BOLD);
		this.headerStyle.setFont(ft);

		this.bodyStyle = workBook.createCellStyle();
		this.bodyStyle.setAlignment(CellStyle.ALIGN_CENTER);
		Font ft1 = workBook.createFont();
		ft1.setFontName("Arial");
		this.bodyStyle.setFont(ft1);
		
		this.unlockedBodyStyle = workBook.createCellStyle();
		unlockedBodyStyle.cloneStyleFrom(bodyStyle);
		unlockedBodyStyle.setLocked(false);
		
		dateCellStyle = workBook.createCellStyle();
		dateCellStyle.cloneStyleFrom(unlockedBodyStyle);
		CreationHelper createHelper = workBook.getCreationHelper();
		dateCellStyle.setDataFormat(
			    createHelper.createDataFormat().getFormat(DateUtil.simple_date_format_dash));
	}
	
	private void updateStyle (Sheet sheet, int freezeRows, int freezeCols,
			int freezeVisibleRow, int freezeVisibleCol,int hiddenCol, String password) {
		sheet.createFreezePane(freezeCols, freezeRows, freezeVisibleCol, freezeVisibleRow);
		sheet.setColumnHidden(hiddenCol, true);
		sheet.protectSheet(password);
	}
}
