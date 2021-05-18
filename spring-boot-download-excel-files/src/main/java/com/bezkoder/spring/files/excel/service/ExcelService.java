package com.bezkoder.spring.files.excel.service;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bezkoder.spring.files.excel.helper.ExcelHelper;
import com.bezkoder.spring.files.excel.model.Tutorial;
import com.bezkoder.spring.files.excel.repository.TutorialRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Service
public class ExcelService {
  @Autowired
  TutorialRepository repository;

  public ByteArrayInputStream load() {
    List<Tutorial> tutorials = repository.findAll();
    
    ObjectMapper objectMapper = new ObjectMapper();
    //Set printing of json
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    
    List<Tutorial> jsonexcel = null;
	try {
		String arrayToJson = objectMapper.writeValueAsString(tutorials);
		//2. Convert JSON to List of Person objects
		//Define Custom Type reference for List<Tutorial> type
		TypeReference<List<Tutorial>> mapType = new TypeReference<List<Tutorial>>() {
		};
		jsonexcel = objectMapper.readValue(arrayToJson, mapType);
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}

    ByteArrayInputStream in = ExcelHelper.tutorialsToExcel(jsonexcel);
    return in;
  }

}
