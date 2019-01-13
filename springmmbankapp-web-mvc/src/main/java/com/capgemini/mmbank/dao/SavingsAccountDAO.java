package com.capgemini.mmbank.dao;

import java.sql.SQLException;
import java.util.List;

import com.capgemini.mmbank.account.SavingsAccount;
import com.capgemini.mmbank.exception.AccountNotFoundException;

public interface SavingsAccountDAO {
	
	SavingsAccount createNewAccount(SavingsAccount account);
	SavingsAccount getAccountById(int accountNumber) throws   AccountNotFoundException;
	boolean deleteAccount(int accountNumber) throws  AccountNotFoundException;
	List<SavingsAccount> getAllSavingsAccount();
	void updateBalance(int accountNumber, double currentBalance)  ;
	double getAccountBalance(int accountNumber) throws  AccountNotFoundException;
	SavingsAccount getAccountByName(String accountHolderName) throws AccountNotFoundException;
	SavingsAccount updateAccountInfo(SavingsAccount savingsAccount) throws AccountNotFoundException;
	List<SavingsAccount> getAccountByBalRange(double minimumBalance, double maxBalance) ;
	
	
}
