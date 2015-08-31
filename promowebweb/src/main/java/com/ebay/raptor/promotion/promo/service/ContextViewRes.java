package com.ebay.raptor.promotion.promo.service;

import java.util.Map;

public class ContextViewRes {

	private ViewResource view;

	private Map<String, Object> context;

	public ViewResource getView() {
		return view;
	}

	public void setView(ViewResource view) {
		this.view = view;
	}

	public Map<String, Object> getContext() {
		return context;
	}

	public void setContext(Map<String, Object> context) {
		this.context = context;
	}

}
