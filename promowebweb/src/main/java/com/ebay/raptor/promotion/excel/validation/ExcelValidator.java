package com.ebay.raptor.promotion.excel.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.context.i18n.LocaleContextHolder;

import com.ebay.raptor.promotion.excel.ColumnConfiguration;
import com.ebay.raptor.promotion.excel.InvalidCellValueError;

/**
 * Validate data from a row of excel sheet. For each sheet reading, you need to create a new ExcelValidator object. 
 * 
 * @author lyan2
 */
public class ExcelValidator {
	private Logger logger = Logger.getLogger(ExcelValidator.class.getName());
	private String bundleBaseName = "ExcelValidationMessages";
	private Locale locale;
	private ResourceBundle bundle;
	
	/**
	 * Validate excel row data.
	 * @param rowIndex
	 * @param map
	 * @param configs
	 * @return
	 */
	public List<InvalidCellValueError> validate(int rowIndex, Map<String, Object> map, List<ColumnConfiguration> configs) {
		List<InvalidCellValueError> errors = new ArrayList<InvalidCellValueError>();
		
		if (configs != null && !configs.isEmpty() && map != null) {
			for (ColumnConfiguration config : configs) {
				Object value = map.get(config.getKey());
				List<ColumnConstraint> constraints = config.getConstraints();
				for (ColumnConstraint constraint : constraints) {
					if (!constraint.isValid(value)) {
						String message = getBundle().getString(constraint.getMessage());
						String errorMsg = config.getTitle() + ": " + constraint.resolveMessage(message);
						errors.add(new InvalidCellValueError(rowIndex, config.getReadOrder(), value, errorMsg));
					}
				}
			}
		}
		
		return errors;
	}
	
	/**
	 * Validate excel row data.
	 * @param rowIndex
	 * @param map
	 * @param configs
	 * @return
	 */
	public List<InvalidCellValueError> validate(int rowIndex, List<Object> list, List<ColumnConfiguration> configs) {
		List<InvalidCellValueError> errors = new ArrayList<InvalidCellValueError>();
		if (configs != null && !configs.isEmpty() && list != null) {
			for (ColumnConfiguration config : configs) {
				Object value = list.get(config.getReadOrder());
				List<ColumnConstraint> constraints = config.getConstraints();
				for (ColumnConstraint constraint : constraints) {
					try {
						if (!constraint.isValid(value)) {
							String message = getBundle().getString(constraint.getMessage());
							String errorMsg = config.getTitle() + ":" + constraint.resolveMessage(message);
							errors.add(new InvalidCellValueError(rowIndex, config.getReadOrder(), value, errorMsg));
						}
					} catch(ClassCastException e) {
						logger.log(Level.WARNING, e.getMessage());
					}
					
				}
			}
		}
		
		return errors;
	}
	
	public Locale getLocale() {
//		return locale == null ? Locale.getDefault() : locale;
		
		// in spring, use this code
		return locale == null ? LocaleContextHolder.getLocale() : locale;
	}
	
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	/**
	 * Default return "ExcelValidationMessages" resource bundle.
	 * @return
	 */
	public ResourceBundle getBundle() {
		return bundle == null ? bundle = ResourceBundle.getBundle(bundleBaseName) : bundle;
	}

	public void setBundle(ResourceBundle bundle) {
		this.bundle = bundle;
	}

	/**
	 *  Default return "ExcelValidationMessages" resource bundle.
	 * @return
	 */
	public String getBundleBaseName() {
		return bundleBaseName;
	}

	public void setBundleBaseName(String bundleBaseName) {
		this.bundleBaseName = bundleBaseName;
	}
	
}
