package com.capgemini.mmbank.dao;

import java.sql.SQLException;
import java.util.List;

import com.capgemini.mmbank.account.CurrentAccount;
import com.capgemini.mmbank.exception.AccountNotFoundException;

public interface CurrentAccountDAO {
	
	CurrentAccount createNewAccount(CurrentAccount account);
	CurrentAccount getAccountById(int accountNumber) throws  AccountNotFoundException;
	boolean deleteAccount(int accountNumber) throws AccountNotFoundException;
	List<CurrentAccount> getAllCurrentAccount();
	void updateBalance(int accountNumber, double currentBalance) ;
	double getAccountBalance(int accountNumber) throws AccountNotFoundException;
	CurrentAccount getAccountByName(String accountHolderName) throws AccountNotFoundException;
	CurrentAccount updateAccountInfo(CurrentAccount CurrentAccount) throws AccountNotFoundException;
	List<CurrentAccount> getAccountByBalRange(double minimumBalance,	double maxBalance);
	
	
 
}
