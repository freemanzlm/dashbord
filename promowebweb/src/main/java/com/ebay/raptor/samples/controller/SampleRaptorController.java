package com.ebay.raptor.samples.controller;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ebay.raptor.kernel.context.IRaptorContext;
import com.ebay.raptor.samples.resource.Main;
import com.sforce.soap.enterprise.Connector;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.sobject.Contact;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

@Controller
public class SampleRaptorController {

	@Inject
	IRaptorContext raptorCtx;

	@Autowired
	Main main;

	static final String USERNAME = "dl-ebay-cbt_sales_force_group@ebay.com.test";
	static final String PASSWORD = "cbtsalesforce000";

	@RequestMapping(value = "index", method = RequestMethod.GET)
	public HashMap<String, String> handleRequest() {
		HashMap<String, String> model = new HashMap<String, String>();
		String helloRaptor = "Say hello to Raptor!";
		model.put("greeting", helloRaptor);
		return model;
	}

	@RequestMapping(value = "account", method = RequestMethod.GET)
	public HashMap<String, String> handleAccount() {
		HashMap<String, String> model = new HashMap<String, String>();
		List<Contact> contacts = null;
		// run the different examples
		contacts = main.queryContacts();

		// String helloRaptor = "Say hello to Raptor!";
		model.put("account", contacts.get(0).getId());
		return model;
	}

}
