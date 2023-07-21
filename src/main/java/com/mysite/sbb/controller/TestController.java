package com.mysite.sbb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

//	http://localhost:9696/test
	@GetMapping("/test")
	@ResponseBody
	public String testGet() {

		return "Hello World";
	}
	
//	http://localhost:9696/main2
	@GetMapping("/main2")
	public String testmain2() {
		return "test/main2";
	}
}