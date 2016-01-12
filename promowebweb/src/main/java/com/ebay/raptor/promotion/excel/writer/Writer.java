package com.ebay.raptor.promotion.excel.writer;

import java.util.List;

import com.ebay.app.raptor.promocommon.excel.header.HeaderConfiguration;
import com.ebay.app.raptor.promocommon.excel.header.HeaderConfigurationManager;
import com.ebay.app.raptor.promocommon.export.exception.NoTitleConfigurationException;


public abstract class Writer<E>{

	protected List<HeaderConfiguration> configurations;
	
	protected Class<?> clazz;
	 
	public Writer(Class<?> clazz){
		this.clazz = clazz;
		initTitleConfiguration();
	}
	
	public Writer(List<HeaderConfiguration> configurations){
		this.configurations = configurations;
	}
	
	public void initTitleConfiguration(){
		if(null != this.clazz){
			this.configurations = HeaderConfigurationManager.getHeaderConfigurations(this.clazz);
		}
		if(null == this.configurations || (null != this.configurations && this.configurations.size() == 0)){
			throw new NoTitleConfigurationException("No title configuration found, please check.");
		}
	}
	
	public abstract void resetHeaders();
	
	public List<HeaderConfiguration> getConfigurations() {
		return configurations;
	}

	public void setConfigurations(List<HeaderConfiguration> configurations) {
		this.configurations = configurations;
	}

}
