package com.example.controller;

import java.io.InputStream;
import java.sql.SQLException;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.jaas.LoginExceptionResolver;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.entity.Resource;
import com.example.repository.LoginRepository;
import com.example.repository.UserRepository;
import com.example.requestmodel.Login;
import com.example.requestmodel.UserRequest;
import com.example.response.LoginResponse;
import com.example.service.ResourceLeaveService;
import com.example.service.ResourceService;
import com.example.service.TimesheetDetailService;
import com.example.service.UserServiceImpl;

import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@RestController
public class UserController {

	@Autowired
	UserServiceImpl userServiceImpl;

	@Autowired
	private LoginRepository userRepository;

	@RequestMapping("/register")
	private ModelAndView meth() {
		ModelAndView andView = new ModelAndView();
		andView.setViewName("home");
		return andView;
	}

	@RequestMapping("/result")
	private ModelAndView meth1(@ModelAttribute UserRequest request, ModelAndView andView) {
		String id = userServiceImpl.register(request);
		System.out.println(id);
		andView.addObject("id", id);
		andView.setViewName("result");
		return andView;
	}

	@RequestMapping("/login")
	private ModelAndView meth2() {
		ModelAndView andView = new ModelAndView();
		andView.setViewName("login");
		return andView;
	}

	@RequestMapping(value = "/generateData", method = RequestMethod.GET)
	public ModelAndView generateData() {
		System.out.println("Generating data...");
		return new ModelAndView("login"); 
	}

	private final ResourceService resourceService;
	

	public UserController(ResourceService resourceService) {
	        this.resourceService = resourceService;
	    }
	
	@Autowired
	ResourceLeaveService resourceLeaveService;
	
	@Autowired
	TimesheetDetailService timeSheetService;

	@PostMapping("/uploadData")
	public void convertExcelToListOfResource(@RequestParam("monthSelect") String month,
			@RequestParam("templateSelect") String template, @RequestParam("fileInput") MultipartFile file) throws IOException {
		 System.out.println("Month: " + month);
		    System.out.println("Template: " + template);
		    System.out.println("File: " + file.getOriginalFilename());

		    if (template.equalsIgnoreCase("resource_leave")) {
		        resourceLeaveService.saveResourcesFromExcel(file);
		    } else if (template.equalsIgnoreCase("Resource")) {
		        resourceService.saveResourcesFromExcel(file);
		    } else if (template.equalsIgnoreCase("timesheet_details")) {
		        timeSheetService.saveTimesheetDetailsFromExcel(file,month);
		    } else {
		        System.out.println("Invalid template selected.");
		    }
	}

	@PostMapping("/loginresult")
	public ModelAndView login(@RequestParam("username") String username, @RequestParam("password") String password)
			throws ClassNotFoundException, SQLException {
		System.out.println(username);
		System.out.println(password);
		ModelAndView andView = new ModelAndView();

		if (userRepository.isValidUser(username, password)) {
			String userType = userRepository.getUserType(username, password);
			System.out.println(userType);

			if ("admin".equals(userType)) {
				andView.setViewName("result");
			} else if ("local".equals(userType)) {
				andView.setViewName("generateData");
			}
		} else {
			System.out.println("dfghjk");
			andView.addObject("error", "Invalid username or password");
			andView.setViewName("loginError");
		}
		return andView;
	}

}
