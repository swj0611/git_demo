package net.codejava.spring.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class StartController {
	@RequestMapping(value="/")
	public ModelAndView listContact(ModelAndView model) throws IOException{
		
		model.setViewName("index");
		
		return model;
	}
}
	