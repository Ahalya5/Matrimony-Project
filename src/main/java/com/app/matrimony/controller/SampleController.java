package com.app.matrimony.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {
	
	@PostMapping("/create")
	 String create() {
		return "hello world";
	}

}
