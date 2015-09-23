package com.ebay.raptor.promotion.init;

import com.ebay.kernel.initialization.BaseModule;
import com.ebay.kernel.initialization.ModuleInterface;
import com.ebay.raptor.kernel.lifecycle.ModuleInitializer;

@ModuleInitializer
public class Module extends BaseModule {
	private static Module s_Module = new Module();

	private Module() {
		super(new InitializationManager());
	}

	public static ModuleInterface getInstance() {
		return s_Module;
	}
}