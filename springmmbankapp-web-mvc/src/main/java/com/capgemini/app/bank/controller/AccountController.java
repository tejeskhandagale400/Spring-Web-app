package com.capgemini.app.bank.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.capgemini.mmbank.account.SavingsAccount;
import com.capgemini.mmbank.exception.AccountNotFoundException;
import com.capgemini.mmbank.service.SavingsAccountService;

@Controller
@RequestMapping("/account") // all bank goes here or entering into controller
public class AccountController {
	private boolean sort = false;

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
		model.addAttribute("accounts", savingsAccounts);
		return "getAllAccounts";
	}

	@RequestMapping("/addAccount")
	public String createAccount() {
		return "newSa";
	}

	@RequestMapping("/addSa")
	public String createSavingAccount(@RequestParam("name") String accountHolderName,
			@RequestParam("accountBalance") double accountBalance, @RequestParam("rdSalary") String sal, Model model) {
		boolean salary = sal.equalsIgnoreCase("true") ? true : false;
		SavingsAccount savingAccount = savingsAccountService.createNewAccount(accountHolderName, accountBalance,
				salary);
		model.addAttribute("account", savingAccount);
		return "redirect:getAll";
	}

	@RequestMapping("/searchForm")
	public String searchAccount() {
		return "searchBy";
	}

	@RequestMapping("/searchByNumber")
	public String searchAccountByNo() {
		return "searchByNumber";
	}

	@RequestMapping("/searchAccount")
	public String getAccountByNumber(@RequestParam("txtAccountNumber") int accountNumber, Model model)
			throws AccountNotFoundException {
		SavingsAccount savingsAccount = savingsAccountService.getAccountById(accountNumber);
		model.addAttribute("account", savingsAccount);
		return "getAllAccounts";
	}

	@RequestMapping("/searchByName")
	public String searchAccountByName() {
		return "searchByName";
	}

	@RequestMapping("/searchAccountByName")
	public String getAccountByname(@RequestParam("accountHolderName") String accountHolderName, Model model)
			throws AccountNotFoundException {
		SavingsAccount savingsAccount = savingsAccountService.getAccountByName(accountHolderName);
		model.addAttribute("account", savingsAccount);
		return "getAllAccounts";
	}

	@RequestMapping("/searchByBalance")
	public String searchAccountByBalance() {
		return "searchByBalance";
	}

	@RequestMapping("/searchAccountByBalRange")
	public String getAccountByBalRange(@RequestParam("miniBalance") double miniBalance,
			@RequestParam("maxBalance") double maxBalance, Model model) throws AccountNotFoundException {
		List<SavingsAccount> savingsAccount = savingsAccountService.getAccountByBalRange(miniBalance, maxBalance);
		System.out.println(savingsAccount);
		model.addAttribute("accounts", savingsAccount);
		return "getAllAccounts";
	}

	@RequestMapping("/updateAccount")
	public String updateAccount() {
		return "UpdateForm";
	}

	@RequestMapping("/update")
	public String update(@RequestParam("txtAccountNumber") int accountNumber, Model model)
			throws AccountNotFoundException {
		SavingsAccount savingsAccount = savingsAccountService.getAccountById(accountNumber);
		model.addAttribute("account", savingsAccount);
		return "updateDetails";
	}

	@RequestMapping("/updateInDB")
	public String updateInDB(@RequestParam("accountNumber") int accountNumber,
			@RequestParam("accountHolderName") String accountHolderName,
			@RequestParam("accountBalance") double accountBalance, @RequestParam("salary") String sal, Model model)
			throws AccountNotFoundException {

		boolean salary = sal.equalsIgnoreCase("salaryTrue") ? true : false;
		SavingsAccount savingsAccount = savingsAccountService.getAccountById(accountNumber);
		savingsAccount.getBankAccount().setAccountHolderName(accountHolderName);
		savingsAccount.setSalary(salary);
		savingsAccount = savingsAccountService.updateAccountInfo(savingsAccount);
		model.addAttribute("account", savingsAccount);
		return "getAllAccounts";
	}

	@RequestMapping("/closeAccount")
	public String deleteAccount() {
		return "CloseAccount";
	}

	@RequestMapping("/closeAccDb")
	public String closeAccDb(@RequestParam("accountNumber") int accountNumber) throws AccountNotFoundException {
		savingsAccountService.deleteAccount(accountNumber);
		return "closeSuccess";
	}

	@RequestMapping("/depositeAmount")
	public String deposite() {
		return "deposite";
	}

	@RequestMapping("/deposite")
	public String depositeDb(@RequestParam("accountNumber") int accountNumber, @RequestParam("amount") double amount)
			throws AccountNotFoundException {
		SavingsAccount savingsAccount = savingsAccountService.getAccountById(accountNumber);
		savingsAccountService.deposit(savingsAccount, amount);
		return "depositeSuccess";
	}

	@RequestMapping("/withdrawAmount")
	public String withdraw() {
		return "withdraw";
	}

	@RequestMapping("/withdraw")
	public String withdrawDb(@RequestParam("accountNumber") int accountNumber, @RequestParam("amount") double amount)
			throws AccountNotFoundException {
		SavingsAccount savingsAccount = savingsAccountService.getAccountById(accountNumber);
		savingsAccountService.withdraw(savingsAccount, amount);
		return "withdrawSuccess";
	}

	@RequestMapping("/fundTransfer")
	public String fundTransfer() {
		return "fundTransfer";
	}

	@RequestMapping("/fundTransferDB")
	public String fundTransferDb(@RequestParam("senderAccountNumber") int senderAccountNumber,
			@RequestParam("recieverAccountNumber") int recieverAccountNumber, @RequestParam("amount") double amount)
			throws AccountNotFoundException {
		SavingsAccount sender = savingsAccountService.getAccountById(senderAccountNumber);
		SavingsAccount receiver = savingsAccountService.getAccountById(recieverAccountNumber);
		savingsAccountService.fundTransfer(sender, receiver, amount);
		return "fundTransferSuccess";
	}

	@RequestMapping("/sortByName")
	public String sortByNAme(Model model) {
		sort = !sort;
		int result = sort ? 1 : -1;
		ArrayList<SavingsAccount> inputAccountList = (ArrayList<SavingsAccount>) savingsAccountService
				.getAllSavingsAccount();
		Collections.sort(inputAccountList, new Comparator<SavingsAccount>() {
			@Override
			public int compare(SavingsAccount accountOne, SavingsAccount accountTwo) {
				return result * accountOne.getBankAccount().getAccountHolderName()
						.compareToIgnoreCase(accountTwo.getBankAccount().getAccountHolderName());
			}

		});
		model.addAttribute("accounts", inputAccountList);
		return "getAllAccounts";

	}

	/*
	 * @RequestMapping("/sortByAccNo") public String sortByAccNo(Model model) { sort
	 * = !sort; System.out.println(sort); int result = sort ? 1 : -1; try {
	 * ArrayList<SavingsAccount> inputAccountList = (ArrayList<SavingsAccount>)
	 * savingsAccountService .getAllSavingsAccount();
	 * Collections.sort(inputAccountList, new Comparator<SavingsAccount>() {
	 * 
	 * @Override public int compare(SavingsAccount accountOne, SavingsAccount
	 * accountTwo) { if (accountOne.getBankAccount() .getAccountNumber() <
	 * accountTwo .getBankAccount().getAccountNumber()) return -1 * result; else if
	 * (accountOne.getBankAccount() .getAccountNumber() == accountTwo
	 * .getBankAccount().getAccountNumber()) return 0; else return 1 * result; }
	 * 
	 * }); model.addAttribute("accounts", inputAccountList); return
	 * "getAllAccounts";
	 * 
	 * }
	 * 
	 */
}
