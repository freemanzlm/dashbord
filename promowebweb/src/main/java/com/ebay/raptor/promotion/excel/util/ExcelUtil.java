package com.ebay.raptor.promotion.excel.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;

import com.ebay.raptor.promotion.excel.ColumnConfiguration;
import com.ebay.raptor.promotion.excel.annotation.Header;

public class ExcelUtil {

	@SuppressWarnings("unused")
	public static ArrayList<ColumnConfiguration> getClassColumnConfiguration(
			Class<?> clazz) throws IntrospectionException {

		BeanInfo info = Introspector.getBeanInfo(clazz);
		PropertyDescriptor[] descriptors = info.getPropertyDescriptors();
		ArrayList<ColumnConfiguration> headerConfigs = new ArrayList<ColumnConfiguration>();

		for (int i = 0; i < descriptors.length; i++) {
			PropertyDescriptor descriptor = descriptors[i];
			Method getter = descriptor.getReadMethod();
			// "Boolean" type property doesn't support "is" getter. Only "boolean" supports. 
			if (getter != null) {
				Header h = descriptor.getReadMethod().getAnnotation(Header.class);
				if (h != null) {
					ColumnConfiguration headerConfig = new ColumnConfiguration();
					headerConfig.setTitle(h.title());
					headerConfig.setReadOrder(h.readOrder());
					headerConfig.setWriteOrder(h.writeOrder());
					headerConfig.setWritable(h.writable());
					headerConfig.setPropertyDescriptor(descriptor);
					headerConfigs.add(headerConfig);
				}
			}
		}

		return headerConfigs;
	}
}
