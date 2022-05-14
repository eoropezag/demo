package com.example.demo.controller;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Suggestion;
import com.example.demo.service.SuggestionsService;

@RestController
@RequestMapping("/demo/")
public class SuggestionsRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	private SuggestionsService suggestionsService;

	@RequestMapping(value = "/suggestions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Iterable<Suggestion>> getSuggestionsByParams(@RequestParam("q") String q,
			@RequestParam(value = "latitude", required = false) String latitude,
			@RequestParam(value = "longitude", required = false) String longitude) throws Exception {
		try {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("q", q);
			if (latitude != null)
				map.put("latitude", latitude);
			if (longitude != null)
				map.put("longitude", longitude);
			
			LOGGER.info("::: - Get Suggestions by params: ", q + " " + latitude + " " + longitude );

			return new ResponseEntity<Iterable<Suggestion>>(suggestionsService.getSuggestions(map), HttpStatus.OK);
		} catch (Exception e) {
			throw new Exception("Error getting data", e);
		}
	}

}
