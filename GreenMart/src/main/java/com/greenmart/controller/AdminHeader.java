package com.greenmart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminHeader {
@RequestMapping("/adminPanel")
	public String AdminPanel() {
		return "adminPanel";
			}
}
