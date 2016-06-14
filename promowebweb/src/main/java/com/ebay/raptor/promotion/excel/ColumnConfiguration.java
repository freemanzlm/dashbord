package com.ebay.raptor.promotion.excel;

import java.beans.PropertyDescriptor;

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
}
