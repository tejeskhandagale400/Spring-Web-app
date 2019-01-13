package com.capgemini.mmbank.ui;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capgemini.mmbank.account.CurrentAccount;
import com.capgemini.mmbank.account.SavingsAccount;
import com.capgemini.mmbank.exception.AccountNotFoundException;
import com.capgemini.mmbank.exception.InsufficientFundsException;
import com.capgemini.mmbank.service.CurrentAccountService;
import com.capgemini.mmbank.service.CurrentAccountServiceImpl;
import com.capgemini.mmbank.service.SavingsAccountService;
import com.capgemini.mmbank.util.DBUtil;

@Component
public class AccountCUI {
	private Scanner scanner = new Scanner(System.in);
	@Autowired
	private SavingsAccountService savingsAccountService;
	@Autowired
	private CurrentAccountService currentAccountService;

	public void start() {

		do {
			System.out.println("****** Welcome to Money Money Bank********");
			System.out.println("1. Open New Account");
			System.out.println("2. Update Account");
			System.out.println("3. Close Account");
			System.out.println("4. Search Account");
			System.out.println("5. Withdraw");
			System.out.println("6. Deposit");
			System.out.println("7. FundTransfer");
			System.out.println("8. Check Current Balance");
			System.out.println("9. Get All Savings Account Details");
			System.out.println("10. Sort Accounts");
			System.out.println("11. Exit");
			System.out.println();
			System.out.println("Make your choice: ");

			int choice = scanner.nextInt();

			performOperation(choice);

		} while (true);
	}

	private void performOperation(int choice) {
		switch (choice) {
		case 1:
			acceptInput();
			break;
		case 2:
			updateAccountInfo();
			break;
		case 3:
			closeAccount();
			break;
		case 4:
			searchAccount();
			break;
		case 5:
			withdraw();
			break;
		case 6:
			deposit();
			break;
		case 7:
			fundTransfer();
			break;
		case 8:
			checkBalance();
			break;
		case 9:
			showAllAccounts();
			break;
		/*
		 * case 10: sortAccounts();
		 */
		case 11:
			try {
				DBUtil.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.exit(0);
			break;
		default:
			System.err.println("Invalid Choice!");
			break;
		}

	}

	private void acceptInput() {
		System.out.println("1 .Saving Account");
		System.out.println("2 .Current Account");
		int option = scanner.nextInt();
		if (option == 1) {
			acceptInput("SA");
		}
		if (option == 2) {
			acceptInput("CA");
		}
	}

	/*
	 * private void sortAccounts() { int sortBy = 0; do {
	 * System.out.println("+++++Ways of Sorting+++++++");
	 * System.out.println("1. Account Number ");
	 * System.out.println("2. Account Holder Name ");
	 * System.out.println("3. Account Balance");
	 * System.out.println("4. Exit From Sort Menu Balance"); int option =
	 * scanner.nextInt(); if (option <= 3) { System.out.println("Sort By  ");
	 * System.out.println("1. Ascending "); System.out.println("2. Descending ");
	 * sortBy = scanner.nextInt(); } switch (option) { case 1:
	 * 
	 * System.out.println(savingsAccountService.sortAllAccount(option, sortBy));
	 * 
	 * 
	 * case 2: try { System.out.println(savingsAccountService.sortAllAccount(option,
	 * sortBy).toString()); } catch (ClassNotFoundException | SQLException e) {
	 * e.printStackTrace(); } break; case 3: try {
	 * System.out.println(savingsAccountService.sortAllAccount(option, sortBy)); }
	 * catch (ClassNotFoundException | SQLException e) { e.printStackTrace(); }
	 * break; case 4: start();
	 * 
	 * }
	 * 
	 * } while (true); }
	 */
	private void closeAccount() {
		System.out.println("Enter a Account number ");
		int accountNumber = scanner.nextInt();
		try {
			boolean result = savingsAccountService.deleteAccount(accountNumber);
			String output = result ? "Successful " : "Failde";
			System.out.println("Deletion Of account is " + output);
		} catch (AccountNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void searchAccount() {
		System.out.println("Search Account by ");
		System.out.println("1. Account Number ");
		System.out.println("2. Account Holder name ");
		System.out.println("3. Account Balance Range");
		int input = scanner.nextInt();
		SavingsAccount savingsAccount = null;
		switch (input) {
		case 1:
			System.out.println("Enter a Account Number ");
			int accountNumber = scanner.nextInt();
			try {
				savingsAccount = savingsAccountService.getAccountById(accountNumber);
				System.out.println(savingsAccount);
			} catch (AccountNotFoundException e) {
				e.printStackTrace();
			}
			break;
		case 2:
			System.out.println("Enter Account Holder Full name  ");
			String accountHolderName = scanner.nextLine();
			accountHolderName = scanner.nextLine();

			try {
				savingsAccount = savingsAccountService.getAccountByName(accountHolderName);
				System.out.println(savingsAccount);
			} catch (AccountNotFoundException e) {
				e.printStackTrace();
			}
			break;
		case 3:
			System.out.println("Enter Minimum Balance ");
			double minimumBalance = scanner.nextInt();
			System.out.println("Enter Maximum Balance ");
			double maxBalance = scanner.nextInt();
			List<SavingsAccount> savingsAccounts;

			savingsAccounts = savingsAccountService.getAccountByBalRange(minimumBalance, maxBalance);
			System.out.println(savingsAccounts.toString());
			break;
		}

	}

	private void checkBalance() {
		System.out.println("Enter Account Number: ");
		int accountNumber = scanner.nextInt();
		double balance;
		 
				try {
					balance = savingsAccountService.getAccountBalance(accountNumber);
					System.out.println("Account balance is " + balance);
				} catch (AccountNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			 
		 
	}

	private void fundTransfer() {
		System.out.println("Enter Account Sender's Number: ");
		int senderAccountNumber = scanner.nextInt();
		System.out.println("Enter Account Receiver's Number: ");
		int receiverAccountNumber = scanner.nextInt();
		System.out.println("Enter Amount: ");
		double amount = scanner.nextDouble();
		 
			SavingsAccount senderSavingsAccount;
			try {
				senderSavingsAccount = savingsAccountService.getAccountById(senderAccountNumber);
				SavingsAccount receiverSavingsAccount = savingsAccountService.getAccountById(receiverAccountNumber);
				savingsAccountService.fundTransfer(senderSavingsAccount, receiverSavingsAccount, amount);
			} catch (AccountNotFoundException | InsufficientFundsException e) {
 				e.printStackTrace();
			}
		
		 
	}

	private void deposit() {
		System.out.println("Enter Account Number: ");
		int accountNumber = scanner.nextInt();
		System.out.println("Enter Amount: ");
		double amount = scanner.nextDouble();
		SavingsAccount savingsAccount = null;

		try {
			savingsAccount = savingsAccountService.getAccountById(accountNumber);
			savingsAccountService.deposit(savingsAccount, amount);

		} catch ( AccountNotFoundException e1) {
 			e1.printStackTrace();
		}

	}

	private void withdraw() {
		System.out.println("Enter Account Number: ");
		int accountNumber = scanner.nextInt();
		System.out.println("Enter Amount: ");
		double amount = scanner.nextDouble();
		SavingsAccount savingsAccount = null;
		try {
			savingsAccount = savingsAccountService.getAccountById(accountNumber);
			savingsAccountService.withdraw(savingsAccount, amount);
			DBUtil.commit();
		} catch (  AccountNotFoundException e) {

		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	private void sortMenu(String sortWay) {
		do {
			System.out.println("+++++Ways of Sorting+++++++");
			System.out.println("1. Account Number");
			System.out.println("2. Account Holder Name");
			System.out.println("3. Account Balance");
			System.out.println("4. Exit from Sorting");

			int choice = scanner.nextInt();

		} while (true);

	}

	private void showAllAccounts() {
		List<SavingsAccount> savingsAccounts;
		 		savingsAccounts = savingsAccountService.getAllSavingsAccount();
			for (SavingsAccount savingsAccount : savingsAccounts) {
				System.out.println(savingsAccount);
			}
		 

	}

	private void acceptInput(String type) {
		if (type.equalsIgnoreCase("SA")) {
			System.out.println("Enter your Full Name: ");
			String accountHolderName = scanner.nextLine();
			accountHolderName = scanner.nextLine();
			System.out.println("Enter Initial Balance(type na for Zero Balance): ");
			String accountBalanceStr = scanner.next();
			double accountBalance = 0.0;
			if (!accountBalanceStr.equalsIgnoreCase("na")) {
				accountBalance = Double.parseDouble(accountBalanceStr);
			}
			System.out.println("Salaried?(y/n): ");
			boolean salary = scanner.next().equalsIgnoreCase("n") ? false : true;
			createSavingsAccount(accountHolderName, accountBalance, salary);
		}

		if (type.equalsIgnoreCase("CA")) {
			System.out.println("Enter your Full Name: ");
			String accountHolderName = scanner.nextLine();
			accountHolderName = scanner.nextLine();
			System.out.println("Enter Initial Balance(type na for Zero Balance): ");
			String accountBalanceStr = scanner.next();
			double accountBalance = 0.0;
			if (!accountBalanceStr.equalsIgnoreCase("na")) {
				accountBalance = Double.parseDouble(accountBalanceStr);
			}
			System.out.println("Enter Over draft Limit ");
			double odLimit = scanner.nextDouble();
			createCurrentAccount(accountHolderName, accountBalance, odLimit);
		}

	}

	private void createCurrentAccount(String accountHolderName, double accountBalance, double odLimit) {
			CurrentAccount newAccount;
 				try {
					newAccount = currentAccountService.createNewAccount(accountHolderName, accountBalance, odLimit);
					System.out.println("New Current Account is successfully created account details are :");
					System.out.println(newAccount);
				} catch (AccountNotFoundException e) {
 					e.printStackTrace();
				}
			
			 
	}

	private void createSavingsAccount(String accountHolderName, double accountBalance, boolean salary) {
		 
			SavingsAccount newAccount = savingsAccountService.createNewAccount(accountHolderName, accountBalance,
					salary);
			System.out.println("New Saving is successfully created account details are :");
			System.out.println(newAccount);
		 
	}

	private void updateAccountInfo() {

		System.out.println("Enter a Account number ");
		int accountNumber = scanner.nextInt();
		SavingsAccount savingsAccount = null;
		try {
			savingsAccount = savingsAccountService.getAccountById(accountNumber);
		} catch (AccountNotFoundException e1) {
			e1.printStackTrace();
		}
		System.out.println("Select a field to update ");
		System.out.println("1. Update Name ");
		System.out.println("2. Update salary");
		System.out.println("3. Update Both Name and Salary ");
		int input = scanner.nextInt();

		switch (input) {
		case 1:
			System.out.println("Enter a new name: ");
			String newName = scanner.nextLine();
			newName = scanner.nextLine();
			savingsAccount.getBankAccount().setAccountHolderName(newName);
			try {
				savingsAccount = savingsAccountService.updateAccountInfo(savingsAccount);
				System.out.println(savingsAccount);
			} catch ( AccountNotFoundException e) {
				e.printStackTrace();
			}
			break;

		case 2:
			System.out.println("Change Salaried?(y/n): ");
			boolean salary = scanner.next().equalsIgnoreCase("n") ? false : true;
			savingsAccount.setSalary(salary);
			try {
				savingsAccount = savingsAccountService.updateAccountInfo(savingsAccount);
				System.out.println(savingsAccount);
			} catch (  AccountNotFoundException e) {
				e.printStackTrace();
			}
			break;
		case 3:
			System.out.println("Enter a new name: ");
			newName = scanner.nextLine();
			newName = scanner.nextLine();
			System.out.println("Change Salaried?(y/n): ");
			salary = scanner.next().equalsIgnoreCase("n") ? false : true;
			savingsAccount.getBankAccount().setAccountHolderName(newName);
			savingsAccount.setSalary(salary);
			try {
				savingsAccount = savingsAccountService.updateAccountInfo(savingsAccount);
				System.out.println(savingsAccount);
			} catch (  AccountNotFoundException e) {
				e.printStackTrace();
			}
			break;
		}

	}

}
