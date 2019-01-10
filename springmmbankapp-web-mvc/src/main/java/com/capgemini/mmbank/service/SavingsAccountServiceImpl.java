package com.capgemini.mmbank.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.mmbank.account.SavingsAccount;
import com.capgemini.mmbank.dao.SavingsAccountDAO;
import com.capgemini.mmbank.exception.AccountNotFoundException;
import com.capgemini.mmbank.exception.InsufficientFundsException;
import com.capgemini.mmbank.exception.InvalidInputException;
import com.capgemini.mmbank.factory.AccountFactory;
import com.capgemini.mmbank.validation.MMBankappValidation;

@Service
public class SavingsAccountServiceImpl implements SavingsAccountService {

	private AccountFactory factory;
	@Autowired
	private SavingsAccountDAO savingsAccountDAO;

	public SavingsAccountServiceImpl() {
		factory = AccountFactory.getInstance();
	}

	public SavingsAccount createNewAccount(String accountHolderName, double accountBalance, boolean salary) {
		SavingsAccount account = factory.createNewSavingsAccount(accountHolderName, accountBalance, salary);
		account = savingsAccountDAO.createNewAccount(account);
		/*
		 * account=new
		 * SavingsAccount(accountId,account.getBankAccount().getAccountHolderName(),
		 * account.getBankAccount().getAccountBalance(),account.isSalary());
		 */
		return account;
	}

	public List<SavingsAccount> getAllSavingsAccount() {
		return savingsAccountDAO.getAllSavingsAccount();
	}

	@Transactional
	public void deposit(SavingsAccount account, double amount) {
		double currentBalance = account.getBankAccount().getAccountBalance();
		currentBalance += amount;
		savingsAccountDAO.updateBalance(account.getBankAccount().getAccountNumber(), currentBalance);

	}

	@Transactional
	public void withdraw(SavingsAccount account, double amount) {
		double currentBalance = account.getBankAccount().getAccountBalance();
		if (currentBalance >= amount) {
			currentBalance -= amount;
			savingsAccountDAO.updateBalance(account.getBankAccount().getAccountNumber(), currentBalance);
		} else
			throw new InsufficientFundsException("Insufficient Funds!");
	}

	@Transactional
	public void fundTransfer(SavingsAccount sender, SavingsAccount receiver, double amount) throws InsufficientFundsException{

		this.deposit(receiver, amount);
		this.withdraw(sender, amount);

	}

	public SavingsAccount getAccountById(int accountNumber) throws AccountNotFoundException {
		return savingsAccountDAO.getAccountById(accountNumber);
	}

	public boolean deleteAccount(int accountNumber) throws AccountNotFoundException {

		return savingsAccountDAO.deleteAccount(accountNumber);
	}

	public double getAccountBalance(int accountNumber) throws AccountNotFoundException {

		return savingsAccountDAO.getAccountBalance(accountNumber);
	}

	public SavingsAccount getAccountByName(String accountHolderName) throws AccountNotFoundException {

		return savingsAccountDAO.getAccountByName(accountHolderName);

	}

	public List<SavingsAccount> sortAllAccount(int option, int sortBy) {
		List<SavingsAccount> inputAccountList = new ArrayList<SavingsAccount>();
		inputAccountList = getAllSavingsAccount();
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

	private List<SavingsAccount> sortAccountsByAccountBalance(List<SavingsAccount> inputAccountList, int sortBy) {
		if (sortBy == 1) {
			Collections.sort(inputAccountList, new Comparator<SavingsAccount>() {
				public int compare(SavingsAccount accountOne, SavingsAccount accountTwo) {
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
			Collections.sort(inputAccountList, new Comparator<SavingsAccount>() {

				public int compare(SavingsAccount accountOne, SavingsAccount accountTwo) {
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

	public List<SavingsAccount> sortAccountsByAccountNumber(List<SavingsAccount> inputAccountList, int sortBy) {
		if (sortBy == 1) {
			Collections.sort(inputAccountList, new Comparator<SavingsAccount>() {

				public int compare(SavingsAccount accountOne, SavingsAccount accountTwo) {
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
			Collections.sort(inputAccountList, new Comparator<SavingsAccount>() {

				public int compare(SavingsAccount accountOne, SavingsAccount accountTwo) {
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

	private List<SavingsAccount> sortAccountsByAccountHolderName(List<SavingsAccount> inputAccountList, int sortBy) {
		if (sortBy == 1) {
			Collections.sort(inputAccountList, new Comparator<SavingsAccount>() {
				public int compare(SavingsAccount accountOne, SavingsAccount accountTwo) {
					return accountOne.getBankAccount().getAccountHolderName()
							.compareToIgnoreCase(accountTwo.getBankAccount().getAccountHolderName());
				}

			});
			return inputAccountList;
		} else if (sortBy == 2) {
			Collections.sort(inputAccountList, new Comparator<SavingsAccount>() {

				public int compare(SavingsAccount accountOne, SavingsAccount accountTwo) {
					return -accountOne.getBankAccount().getAccountHolderName()
							.compareToIgnoreCase(accountTwo.getBankAccount().getAccountHolderName());
				}
			});
		}
		return inputAccountList;
	}

	public SavingsAccount updateAccountInfo(SavingsAccount savingsAccount) throws AccountNotFoundException {

		return savingsAccountDAO.updateAccountInfo(savingsAccount);
	}

	public List<SavingsAccount> getAccountByBalRange(double minimumBalance, double maxBalance) {

		return savingsAccountDAO.getAccountByBalRange(minimumBalance, maxBalance);
	}

}
