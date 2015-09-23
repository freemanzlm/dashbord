package com.ebay.raptor.promotion.init;

import com.ebay.kernel.initialization.BaseInitializationManager;
import com.ebay.kernel.initialization.ModuleInterface;

public class InitializationManager extends BaseInitializationManager {

	 final static ModuleInterface [] DEPENDENT_MODULES = {
     	com.ebay.integ.user.common.Module.getInstance()
     };
	 
	public InitializationManager() {
		super(DEPENDENT_MODULES);
	}

}