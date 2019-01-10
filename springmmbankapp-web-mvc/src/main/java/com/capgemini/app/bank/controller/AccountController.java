package com.capgemini.app.bank.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.capgemini.app.bank.pojo.Employee;
import com.capgemini.mmbank.account.SavingsAccount;
import com.capgemini.mmbank.service.SavingsAccountService;

@Controller
@RequestMapping("/account") // all bank goes here or entering into controller
public class AccountController {
	
	@Autowired
	private SavingsAccountService savingsAccountService;
	@RequestMapping("/home")
	public String askDetails() {
		return "index";
	}

	/*
	 * @RequestMapping("/save") public String save(@RequestParam("empId") int
	 * empId, @RequestParam("empName") String empName,
	 * 
	 * @RequestParam("salary") double salary , Model model) { Employee employee =
	 * new Employee(empId , empName , salary); model.addAttribute("employee",
	 * employee); return "display"; }
	 */

	@RequestMapping("/getAll")
	public String getAllAccount(Model model) { 
		List<SavingsAccount> savingsAccounts;
		savingsAccounts = savingsAccountService.getAllSavingsAccount();
		model.addAttribute("account",savingsAccounts);
		return "getAll";
	}
}
