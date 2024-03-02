package com.equipsafelog.core.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AboutController {

	@GetMapping
	public String about() throws Exception {
		return "Service OK";
	}
}
