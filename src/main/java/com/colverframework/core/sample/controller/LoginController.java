package com.colverframework.core.sample.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.colverframework.core.sample.pojo.User;
import com.colverframework.core.sample.service.user.UserService;

@Controller
public class LoginController {
	@Resource
	private UserService userService;

	@RequestMapping(value = { "/login" }, method = { RequestMethod.POST })
	public ModelAndView login(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute User user) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setHeader("Charset", "UTF-8");
		PrintWriter pw = new PrintWriter(response.getOutputStream());
		if(checkUser(user)){
			pw.write("{'error':'user param is necessary'}");
		}
		User existsUser = userService.getUser(user);
		if (existsUser == null) {
			pw.write("{'error':'have not find the user!'}");
		}else{
			JSONObject fromObject = JSONObject.fromObject(existsUser);
			pw.write(fromObject.toString());
		}
		pw.flush();
		return null;
	}

	@RequestMapping(value = { "/login" }, method = { RequestMethod.GET })
	public String login() throws IOException {
		return "login";
	}
	
	
	@RequestMapping(value = { "/save" }, method = { RequestMethod.POST })
	public String saveUser(@ModelAttribute User user, Model model) {
		if(checkUser(user)){
			return "redirect:exception";
		}
		userService.saveUser(user);
		return "index";
	}

	private boolean checkUser(User user) {
		return user == null || StringUtils.isEmpty(user.getName())
				|| StringUtils.isEmpty(user.getPassword());

	}

}
