package  com.capgemini.mmbank.service;

import java.sql.SQLException;
import java.util.List;

import com.capgemini.mmbank.account.CurrentAccount;
import com.capgemini.mmbank.exception.AccountNotFoundException;

public interface CurrentAccountService {

	CurrentAccount createNewAccount(String accountHolderName, double accountBalance, double odLimit) throws AccountNotFoundException;

	CurrentAccount getAccountById(int accountNumber) throws  AccountNotFoundException;

	boolean deleteAccount(int accountNumber) throws  AccountNotFoundException;
	
	List<CurrentAccount> getAllCurrentAccount() ;

	void fundTransfer(CurrentAccount sender, CurrentAccount receiver, double amount);
	void deposit(CurrentAccount account, double amount);
	void withdraw(CurrentAccount account, double amount);
	
	double getAccountBalance(int accountNumber) throws AccountNotFoundException;

	CurrentAccount getAccountByName(String accountHolderName) throws   AccountNotFoundException;
	
	List<CurrentAccount> sortAllAccount(int option, int sortBy)  ;

	CurrentAccount updateAccountInfo(CurrentAccount CurrentAccount) throws AccountNotFoundException;

	List<CurrentAccount> getAccountByBalRange(double minimumBalance,
			double maxBalance);
}