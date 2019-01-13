package com.capgemini.mmbank.account;

public class SavingsAccount {
	private boolean salary;
	private BankAccount bankAccount;
	public SavingsAccount(String accountHolderName, double accountBalance, boolean salary) {
		bankAccount = new BankAccount(accountHolderName, accountBalance);
		this.salary = salary;
	}

	public SavingsAccount(String accountHolderName, boolean salary) {
		bankAccount = new BankAccount(accountHolderName);
		this.salary = salary;
	}

	public SavingsAccount(int accountNumber, String accountHolderName, double accountBalance, boolean salary) {
		bankAccount = new BankAccount(accountNumber, accountHolderName, accountBalance);
		this.salary = salary;
	}

	public SavingsAccount(int accountNumber, String accountHolderName, double accountBalance,boolean salary, String type) {
		bankAccount = new BankAccount(accountNumber, accountBalance, accountHolderName, type);
		this.salary = salary;

 	}
	public boolean isSalary() {
		return salary;
	}

	public void setSalary(boolean salary) {
		this.salary = salary;
	}

	public BankAccount getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}

	public int getTypeOfAccount() {
		return 1;
	}
	@Override
	public String toString() {
		return "SavingsAccount [salary=" + salary + "," + bankAccount + "\n";
	}
}
