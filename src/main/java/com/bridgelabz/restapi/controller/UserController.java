package com.bridgelabz.restapi.controller;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.bridgelabz.restapi.model.User;
import com.bridgelabz.restapi.services.UserService;

@Slf4j 
@RestController
@RequestMapping("/api")
public class UserController {
	@Autowired
    private UserService userService ;
   
	@RequestMapping(value= {"/","/login"},method = RequestMethod.GET)
    public ModelAndView login()
    {
    	ModelAndView model=new ModelAndView();
    	
    	model.setViewName("user/logn");
    	return model;
    }
	
	@RequestMapping(value = {"/sigup"}, method =RequestMethod.GET)
    public ModelAndView signup()
    {
		ModelAndView model=new  ModelAndView();
		
		User user=new User();
		model.addObject("user", user);
		model.setViewName("user/signup");
		
		return model;
    }
	
	@RequestMapping(value = {"/signup"},method = RequestMethod.POST)
	
	public ModelAndView createUser(@Valid User user,BindingResult result)
	{
		ModelAndView model=new ModelAndView();
		User userExist=userService.findUserByEmail(user.getEmail());
		if(userExist!=null)
		{
			result.rejectValue("email", "error.user","This Email Alrady Exists");
		}
		if(result.hasErrors())
		{
			model.setViewName("user/signup");
		}
		else
		{
			userService.saveUser(user);
			model.addObject("msg","User has been Registered successfully");
			model.addObject("user",new User());
			model.setViewName("user/signup");
		}
		return model;
	}
}