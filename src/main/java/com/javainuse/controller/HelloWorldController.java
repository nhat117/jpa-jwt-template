package com.javainuse.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	@RequestMapping({ "/hello" })
	public String firstPage() {
		return "This is a sample response";
	}

	@RequestMapping({"/go"})
	public String secondPage() {
		return "<h2>This is Go End point</h2>";
	}

}