package com.capgemini.mmbank.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.mmbank.account.CurrentAccount;
import com.capgemini.mmbank.dao.CurrentAccountDAO;
import com.capgemini.mmbank.dao.CurrentAccountDAOImpl;
import com.capgemini.mmbank.exception.AccountNotFoundException;
import com.capgemini.mmbank.exception.InsufficientFundsException;
import com.capgemini.mmbank.exception.InvalidInputException;
import com.capgemini.mmbank.factory.AccountFactory;
import com.capgemini.mmbank.util.DBUtil;

@Service
public class CurrentAccountServiceImpl implements CurrentAccountService {

	private AccountFactory factory;
	@Autowired
	private CurrentAccountDAO CurrentAccountDAO;

	public CurrentAccountServiceImpl() {
		factory = AccountFactory.getInstance();
//		CurrentAccountDAO = new CurrentAccountDAOImpl();
	}

	 
	public CurrentAccount createNewAccount(String accountHolderName, double accountBalance, double odLimit)
			throws ClassNotFoundException, SQLException, AccountNotFoundException {
		CurrentAccount account = factory.createNewCurrentAccount(accountHolderName, accountBalance, odLimit);
		int accountNO= CurrentAccountDAO.createNewAccount(account);

		return getAccountById(accountNO);
	}

	 
	public List<CurrentAccount> getAllCurrentAccount() throws ClassNotFoundException, SQLException {
		return CurrentAccountDAO.getAllCurrentAccount();
	}

	 
	public void deposit(CurrentAccount account, double amount) throws ClassNotFoundException, SQLException {
		if (amount > 0) {
			double currentBalance = account.getBankAccount().getAccountBalance();
			currentBalance += amount;
			CurrentAccountDAO.updateBalance(account.getBankAccount().getAccountNumber(), currentBalance);
			//CurrentAccountDAO.commit();
		}else {
			throw new InvalidInputException("Invalid Input Amount!");
		}
	}
	 
	public void withdraw(CurrentAccount account, double amount) throws ClassNotFoundException, SQLException {
		double currentBalance = account.getBankAccount().getAccountBalance();
		if (amount > 0 && currentBalance+account.getOdLimit() >= amount) {
			currentBalance -= amount;
			CurrentAccountDAO.updateBalance(account.getBankAccount().getAccountNumber(), currentBalance);
			//CurrentAccountDAO.commit();
		} else {
			throw new InsufficientFundsException("Invalid Input or Insufficient Funds!");
		}
	}

	 
	public void fundTransfer(CurrentAccount sender, CurrentAccount receiver, double amount)
			throws ClassNotFoundException, SQLException {
		try {
			withdraw(sender, amount);
			deposit(receiver, amount);
			DBUtil.commit();
		} catch (InvalidInputException | InsufficientFundsException e) {
			e.printStackTrace();
			DBUtil.rollback();
		} catch(Exception e) {
			e.printStackTrace();
			DBUtil.rollback();
		}
	}

	

	 
	public CurrentAccount getAccountById(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException {
		return CurrentAccountDAO.getAccountById(accountNumber);
	}

	 
	public boolean deleteAccount(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException {
		 
		return CurrentAccountDAO.deleteAccount(accountNumber);
	}

	 
	public double getAccountBalance(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException {
		
 		return CurrentAccountDAO.getAccountBalance(accountNumber);
	}

	 
	public CurrentAccount getAccountByName(String accountHolderName) throws ClassNotFoundException, AccountNotFoundException, SQLException {

		return CurrentAccountDAO.getAccountByName(accountHolderName);
 
	}

	

	 
	public List<CurrentAccount> sortAllAccount(int option, int sortBy) throws ClassNotFoundException, SQLException {
		List<CurrentAccount> inputAccountList = new ArrayList<CurrentAccount>();
		inputAccountList = getAllCurrentAccount();
		switch(option)
		{
		case 1:sortAccountsByAccountNumber(inputAccountList,sortBy);
	 			break;
		case 2:sortAccountsByAccountHolderName(inputAccountList,sortBy);
			break;
		case 3:sortAccountsByAccountBalance(inputAccountList,sortBy);
		break;
		}
		return inputAccountList;
	}
	
	

	private List<CurrentAccount> sortAccountsByAccountBalance(List<CurrentAccount> inputAccountList, int sortBy) {
		if(sortBy == 1)
		{
			Collections.sort(inputAccountList, new Comparator<CurrentAccount>()
				{
					public int compare(CurrentAccount accountOne, CurrentAccount accountTwo) {
					if(accountOne.getBankAccount().getAccountBalance() < accountTwo.getBankAccount().getAccountBalance())
						return -1;
					else if(accountOne.getBankAccount().getAccountBalance() == accountTwo.getBankAccount().getAccountBalance())
						return 0;
					else
					return  1;
					}
			
				}
			);
			return inputAccountList;
		}
		else if(sortBy == 2)
		{
			Collections.sort(inputAccountList, new Comparator<CurrentAccount>()
		 	{

						 
						public int compare(CurrentAccount accountOne, CurrentAccount accountTwo) {
							if(accountOne.getBankAccount().getAccountBalance() < accountTwo.getBankAccount().getAccountBalance())
								return 1;
							else if(accountOne.getBankAccount().getAccountBalance() == accountTwo.getBankAccount().getAccountBalance())
								return 0;
							else
							return  -1;
						}
					 
				
		 	});
		}
		return inputAccountList;
		
	}

	public List<CurrentAccount> sortAccountsByAccountNumber( List<CurrentAccount> inputAccountList ,int sortBy )
	{
		if(sortBy == 1)
		{
			Collections.sort(inputAccountList, new Comparator<CurrentAccount>()
				{
					 
					public int compare(CurrentAccount accountOne, CurrentAccount accountTwo) {
					if(accountOne.getBankAccount().getAccountNumber() < accountTwo.getBankAccount().getAccountNumber())
						return -1;
					else if(accountOne.getBankAccount().getAccountNumber() == accountTwo.getBankAccount().getAccountNumber())
						return 0;
					else
					return  1;
					}
			
				}
			);
			return inputAccountList;
		}
		else if(sortBy == 2)
		{
			Collections.sort(inputAccountList, new Comparator<CurrentAccount>()
		 	{

						 
						public int compare(CurrentAccount accountOne, CurrentAccount accountTwo) {
							if(accountOne.getBankAccount().getAccountNumber() < accountTwo.getBankAccount().getAccountNumber())
								return 1;
							else if(accountOne.getBankAccount().getAccountNumber() == accountTwo.getBankAccount().getAccountNumber())
								return 0;
							else
							return  -1;
						}
					 
				
		 	});
		}
		return inputAccountList;
	}
	
	private List<CurrentAccount> sortAccountsByAccountHolderName(List<CurrentAccount> inputAccountList, int sortBy) {
		if(sortBy == 1)
		{
			Collections.sort(inputAccountList, new Comparator<CurrentAccount>()
				{
					 
					public int compare(CurrentAccount accountOne, CurrentAccount accountTwo) {
					return  accountOne.getBankAccount().getAccountHolderName().compareToIgnoreCase(accountTwo.getBankAccount().getAccountHolderName());
					}
			
				}
			);
			return inputAccountList;
		}
		else if(sortBy == 2)
		{
			Collections.sort(inputAccountList, new Comparator<CurrentAccount>()
			{
				 
				public int compare(CurrentAccount accountOne, CurrentAccount accountTwo) {
				return  -accountOne.getBankAccount().getAccountHolderName().compareToIgnoreCase(accountTwo.getBankAccount().getAccountHolderName());
				}
			});
		}
		return inputAccountList;
	}

	 
	public CurrentAccount updateAccountInfo(CurrentAccount CurrentAccount) throws ClassNotFoundException, SQLException, AccountNotFoundException {

		return CurrentAccountDAO.updateAccountInfo(CurrentAccount);
	}

	 
	public List<CurrentAccount> getAccountByBalRange(double minimumBalance,
			double maxBalance) throws ClassNotFoundException, SQLException {
		
		return CurrentAccountDAO.getAccountByBalRange( minimumBalance, maxBalance);
	}

	
}
