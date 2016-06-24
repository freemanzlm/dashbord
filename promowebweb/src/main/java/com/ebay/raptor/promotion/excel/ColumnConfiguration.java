package com.ebay.raptor.promotion.excel;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

import com.ebay.raptor.promotion.excel.validation.ColumnConstraint;

/**
 * Store each column's configuration information.
 * @author lyan2
 */
public class ColumnConfiguration {
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean isWritable() {
		return writable;
	}
	public void setWritable(boolean writable) {
		this.writable = writable;
	}
	public PropertyDescriptor getPropertyDescriptor() {
		return propertyDescriptor;
	}
	public void setPropertyDescriptor(PropertyDescriptor propertyDescriptor) {
		this.propertyDescriptor = propertyDescriptor;
	}

	public int getReadOrder() {
		return readOrder;
	}
	public void setReadOrder(int readOrder) {
		this.readOrder = readOrder;
	}
	public int getWriteOrder() {
		return writeOrder;
	}
	public void setWriteOrder(int writeOrder) {
		this.writeOrder = writeOrder;
	}

	public String getRawType() {
		return rawType;
	}
	public void setRawType(String rawType) {
		this.rawType = rawType;
	}

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public List<ColumnConstraint> getConstraints() {
		return constraints;
	}
	public void setConstraints(List<ColumnConstraint> constraints) {
		this.constraints = constraints;
	}

	public Boolean getDisplay() {
		return display;
	}
	public void setDisplay(Boolean display) {
		this.display = display;
	}

	public Class<?> getType() {
		return type;
	}
	public void setType(Class<?> type) {
		this.type = type;
	}

	/**
	 * Excel column header title.
	 */
	private String title;
	
	/**
	 * Which excel column to read data.
	 */
	private int readOrder;
	
	/**
	 * Which excel row to read data.
	 */
	private int writeOrder;
	
	/**
	 * Whether user can change the default value in the cell.
	 */
	private boolean writable;
	
	/**
	 * The PropertyDescriptor object of the property which the cell maps to.
	 */
	private PropertyDescriptor propertyDescriptor;
	
	private String rawType;
	
	private Class<?> type;
	
	// for JSON and XML conversion
	private String key;
	
	private List<ColumnConstraint> constraints = new ArrayList<ColumnConstraint>();
	
	private Boolean display;
}
