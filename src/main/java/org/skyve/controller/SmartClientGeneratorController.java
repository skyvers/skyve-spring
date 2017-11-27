package org.skyve.controller;

import javax.servlet.http.HttpServletRequest;

import org.skyve.impl.util.UtilImpl;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/smartgen", produces = MediaType.APPLICATION_JSON_VALUE)
public class SmartClientGeneratorController {

	// @Autowired
	// private SmartClientGeneratorServlet servlet;

	@RequestMapping(method = RequestMethod.GET)
	public void doGet(HttpServletRequest request) {
		UtilImpl.LOGGER.info("SmartClient Generate - get....");
	}

	@RequestMapping(method = RequestMethod.POST)
	public void doPost(HttpServletRequest request) {
		UtilImpl.LOGGER.info("SmartClient Generate - post....");
	}

}
