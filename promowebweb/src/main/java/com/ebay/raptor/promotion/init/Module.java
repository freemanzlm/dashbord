package com.ebay.raptor.promotion.init;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.ebay.kernel.initialization.BaseInitializationManager;
import com.ebay.kernel.initialization.InitializationContext;
import com.ebay.kernel.initialization.ModuleInterface;
import com.ebay.raptor.kernel.init.BaseDIAwareModule;
import com.ebay.raptor.kernel.lifecycle.DependencyInjectionInitializer;

@Singleton @DependencyInjectionInitializer
public class Module extends BaseDIAwareModule {
	private static Module s_Module = new Module();

	private Module() {
		super(new InitializationManager());
	}

	public static ModuleInterface getInstance() {
		return s_Module;
	}
	
	public static class InitializationManager extends BaseInitializationManager {
		@Inject Module userModule; 

		// Moved to @Inject statement
		final static ModuleInterface[] DEPENDENT_MODULES = { 
//			com.ebay.integ.user.common.Module.getInstance() 
		};

		public InitializationManager() {
			super(DEPENDENT_MODULES);
		}
		
		@Override
	    protected void initialize(InitializationContext context) {
	        super.initialize(context);
	        System.out.println(">>> " + this.getClass().getName() + " called" );
	    }

	}
}