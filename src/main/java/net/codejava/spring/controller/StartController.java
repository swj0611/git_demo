package net.codejava.spring.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.codejava.spring.dao.ContactDAO;
import net.codejava.spring.model.Contact;
@Controller
public class StartController {
	@RequestMapping(value="/")
	public ModelAndView listContact(ModelAndView model) throws IOException{
		
		model.setViewName("index");
		
		return model;
	}
}
	