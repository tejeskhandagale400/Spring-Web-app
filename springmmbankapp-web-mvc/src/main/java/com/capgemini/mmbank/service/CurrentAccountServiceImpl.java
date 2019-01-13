package com.capgemini.mmbank.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.mmbank.account.CurrentAccount;
import com.capgemini.mmbank.dao.CurrentAccountDAO;
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
			throws AccountNotFoundException {
		CurrentAccount account = factory.createNewCurrentAccount(accountHolderName, accountBalance, odLimit);
		account = CurrentAccountDAO.createNewAccount(account);

		return account;
	}

	public List<CurrentAccount> getAllCurrentAccount() {
		return CurrentAccountDAO.getAllCurrentAccount();
	}

	@Transactional
	public void deposit(CurrentAccount account, double amount) {
		if (amount > 0) {
			double currentBalance = account.getBankAccount().getAccountBalance();
			currentBalance += amount;
			CurrentAccountDAO.updateBalance(account.getBankAccount().getAccountNumber(), currentBalance);
			// CurrentAccountDAO.commit();
		} else {
			throw new InvalidInputException("Invalid Input Amount!");
		}
	}

	@Transactional
	public void withdraw(CurrentAccount account, double amount) {
		double currentBalance = account.getBankAccount().getAccountBalance();
		currentBalance -= amount;
		CurrentAccountDAO.updateBalance(account.getBankAccount().getAccountNumber(), currentBalance);

	}

	@Transactional
	public void fundTransfer(CurrentAccount sender, CurrentAccount receiver, double amount) {
		withdraw(sender, amount);
		deposit(receiver, amount);

	}

	public CurrentAccount getAccountById(int accountNumber) throws AccountNotFoundException {
		return CurrentAccountDAO.getAccountById(accountNumber);
	}

	public boolean deleteAccount(int accountNumber) throws AccountNotFoundException {

		return CurrentAccountDAO.deleteAccount(accountNumber);
	}

	public double getAccountBalance(int accountNumber) throws AccountNotFoundException {

		return CurrentAccountDAO.getAccountBalance(accountNumber);
	}

	public CurrentAccount getAccountByName(String accountHolderName) throws AccountNotFoundException {

		return CurrentAccountDAO.getAccountByName(accountHolderName);

	}

	public List<CurrentAccount> sortAllAccount(int option, int sortBy) {
		List<CurrentAccount> inputAccountList = new ArrayList<CurrentAccount>();
		inputAccountList = getAllCurrentAccount();
		switch (option) {
		case 1:
			sortAccountsByAccountNumber(inputAccountList, sortBy);
			break;
		case 2:
			sortAccountsByAccountHolderName(inputAccountList, sortBy);
			break;
		case 3:
			sortAccountsByAccountBalance(inputAccountList, sortBy);
			break;
		}
		return inputAccountList;
	}

	private List<CurrentAccount> sortAccountsByAccountBalance(List<CurrentAccount> inputAccountList, int sortBy) {
		if (sortBy == 1) {
			Collections.sort(inputAccountList, new Comparator<CurrentAccount>() {
				public int compare(CurrentAccount accountOne, CurrentAccount accountTwo) {
					if (accountOne.getBankAccount().getAccountBalance() < accountTwo.getBankAccount()
							.getAccountBalance())
						return -1;
					else if (accountOne.getBankAccount().getAccountBalance() == accountTwo.getBankAccount()
							.getAccountBalance())
						return 0;
					else
						return 1;
				}

			});
			return inputAccountList;
		} else if (sortBy == 2) {
			Collections.sort(inputAccountList, new Comparator<CurrentAccount>() {

				public int compare(CurrentAccount accountOne, CurrentAccount accountTwo) {
					if (accountOne.getBankAccount().getAccountBalance() < accountTwo.getBankAccount()
							.getAccountBalance())
						return 1;
					else if (accountOne.getBankAccount().getAccountBalance() == accountTwo.getBankAccount()
							.getAccountBalance())
						return 0;
					else
						return -1;
				}

			});
		}
		return inputAccountList;

	}

	public List<CurrentAccount> sortAccountsByAccountNumber(List<CurrentAccount> inputAccountList, int sortBy) {
		if (sortBy == 1) {
			Collections.sort(inputAccountList, new Comparator<CurrentAccount>() {

				public int compare(CurrentAccount accountOne, CurrentAccount accountTwo) {
					if (accountOne.getBankAccount().getAccountNumber() < accountTwo.getBankAccount().getAccountNumber())
						return -1;
					else if (accountOne.getBankAccount().getAccountNumber() == accountTwo.getBankAccount()
							.getAccountNumber())
						return 0;
					else
						return 1;
				}

			});
			return inputAccountList;
		} else if (sortBy == 2) {
			Collections.sort(inputAccountList, new Comparator<CurrentAccount>() {

				public int compare(CurrentAccount accountOne, CurrentAccount accountTwo) {
					if (accountOne.getBankAccount().getAccountNumber() < accountTwo.getBankAccount().getAccountNumber())
						return 1;
					else if (accountOne.getBankAccount().getAccountNumber() == accountTwo.getBankAccount()
							.getAccountNumber())
						return 0;
					else
						return -1;
				}

			});
		}
		return inputAccountList;
	}

	private List<CurrentAccount> sortAccountsByAccountHolderName(List<CurrentAccount> inputAccountList, int sortBy) {
		if (sortBy == 1) {
			Collections.sort(inputAccountList, new Comparator<CurrentAccount>() {

				public int compare(CurrentAccount accountOne, CurrentAccount accountTwo) {
					return accountOne.getBankAccount().getAccountHolderName()
							.compareToIgnoreCase(accountTwo.getBankAccount().getAccountHolderName());
				}

			});
			return inputAccountList;
		} else if (sortBy == 2) {
			Collections.sort(inputAccountList, new Comparator<CurrentAccount>() {

				public int compare(CurrentAccount accountOne, CurrentAccount accountTwo) {
					return -accountOne.getBankAccount().getAccountHolderName()
							.compareToIgnoreCase(accountTwo.getBankAccount().getAccountHolderName());
				}
			});
		}
		return inputAccountList;
	}

	public CurrentAccount updateAccountInfo(CurrentAccount CurrentAccount) throws AccountNotFoundException {

		return CurrentAccountDAO.updateAccountInfo(CurrentAccount);
	}

	public List<CurrentAccount> getAccountByBalRange(double minimumBalance, double maxBalance) {

		return CurrentAccountDAO.getAccountByBalRange(minimumBalance, maxBalance);
	}

}
