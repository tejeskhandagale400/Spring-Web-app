package com.capgemini.mmbank.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.capgemini.mmbank.account.SavingsAccount;
import com.capgemini.mmbank.exception.AccountNotFoundException;


public interface SavingsAccountService {

	SavingsAccount createNewAccount(String accountHolderName, double accountBalance, boolean salary) ;

	SavingsAccount getAccountById(int accountNumber) throws AccountNotFoundException;

	boolean deleteAccount(int accountNumber) throws AccountNotFoundException;
	
	List<SavingsAccount> getAllSavingsAccount();

	void fundTransfer(SavingsAccount sender, SavingsAccount receiver, double amount) ;
	void deposit(SavingsAccount account, double amount);
	void withdraw(SavingsAccount account, double amount);
	
	double getAccountBalance(int accountNumber) throws  AccountNotFoundException;

	SavingsAccount getAccountByName(String accountHolderName) throws AccountNotFoundException;
	
	List<SavingsAccount> sortAllAccount(int option, int sortBy)  ;

	SavingsAccount updateAccountInfo(SavingsAccount savingsAccount) throws  AccountNotFoundException;

	List<SavingsAccount> getAccountByBalRange(double minimumBalance,
			double maxBalance);
}











