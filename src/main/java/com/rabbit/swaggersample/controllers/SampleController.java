package com.rabbit.swaggersample.controllers;

import com.rabbit.swaggersample.dtos.SampleDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@Slf4j
@Controller("sampleController")
@RequestMapping("/sample")
public class SampleController {

	@GetMapping
	@ResponseBody
	public String get() {

		log.info("SAMPLE-GET method called");

		return "SAMPLE";
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	@ResponseBody
	public SampleDto post(@RequestBody final SampleDto sampleDto) {

		log.info("SAMPLE-POST method called: {}", sampleDto.toString());

		return SampleDto.builder()
				.name("John Doe")
				.age(42)
				.build();
	}

	@PutMapping
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public void put(@RequestBody final SampleDto sampleDto) {

		log.info("SAMPLE-PUT method called: {}", sampleDto.toString());
	}

}
